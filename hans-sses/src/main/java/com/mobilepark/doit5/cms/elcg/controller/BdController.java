package com.mobilepark.doit5.cms.elcg.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.service.AdminGroupService;
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.bill.dao.BdPeriodDao;
import com.mobilepark.doit5.bill.model.BdPeriod;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.elcg.service.ChargerGroupService;
import com.mobilepark.doit5.elcg.service.ChargerService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.elcg.controller
 * @Filename     : BdController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 14.      최초 버전
 * =================================================================================
 */

@Controller
public class BdController {

	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private BdService bdService;
	
	@Autowired
	private ChargerGroupService chargerGroupService;
	
	@Autowired
	private ChargerService chargerService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminGroupService adminGroupService;
	
	@Autowired
	private BdPeriodDao bdperiodDao;
	
	@RequestMapping("/elcg/building/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "status", required = false) String type,
			@RequestParam(value = "bdSelect", required = false) String bdId,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "isInst", required = false) String isInst) {
		TraceLog.debug("isInst : "+isInst);
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView("elcg/building/search");
		if ( adminGroup.getId() == 3 ) type = "ALL";
		mav.addObject("type",type);
		
		// 디폴트 페이지
		if(StringUtils.isEmpty(type)) {
			
			// 설치된 충전기수
			mav.addObject("chargerCount", this.chargerService.searchCount(new Charger()));
			mav.addObject("type", "default");
			
			// 충전기 설치된 건물수 
			List<Bd> bdList = this.bdService.search(new Bd());
			if(bdList.size() == 0 || bdList == null) return mav;
			
			int totalBdCnt = 0;
			for(Bd bds : bdList) {
				totalBdCnt += bds.getTotalBdCnt();
			}
			mav.addObject("totalBdCnt", totalBdCnt);
			
			return mav;
		}
		
		PaginatedList bdList = null;
		Bd bd = new Bd();
		BdGroup bdGroup = new BdGroup();
		
		if (type.equals("BD") && bdGroupId != null && bdGroupId != 0) {
			bd.setBdGroup(this.bdGroupService.get(bdGroupId));
		}
		
		if (StringUtils.isEmpty(bdId)) bdId = "0";
		
		// 건물명 검색
		if (type.equals("BD") && bdId != null && Long.parseLong(bdId) != 0) {
			mav.addObject("rownum", 1);
			mav.addObject("page", 1); 
			
			if(StringUtils.equals(bdId, "0")) return mav;
			Bd lastBd = this.bdService.get(Long.parseLong(bdId));
			lastBd.setLstInstall();
			mav.addObject("bdList", lastBd);
			
			// 화면에서 상세/동명 유지
			List<Bd> selBdList = null;
			if (bdGroupId != null && bdGroupId != 0) {
				if (StringUtils.isEmpty(isInst) && adminGroup.getId() == 2) {
					selBdList = bdService.getInstallerBdList(bdGroupId.toString(), admin.getId());
				} else {
					BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
					Bd selBd = new Bd();
					selBd.setBdGroup(selGroupList);
					selBdList = this.bdService.search(selBd);
				}
				mav.addObject("isInst", isInst);
				mav.addObject("selBdGroupId", bdGroupId);
				mav.addObject("selBdList", selBdList);
			}

			return mav;
		}
		
		// 전체 조회
		if(type.equals("ALL") || type.equals("BD")) {
			int pageNum = 1;
			int rowPerPage = Env.getInt("web.rowPerPage", 10);
			try {
				pageNum = Integer.parseInt(page);
			} catch (Exception e) {
				pageNum = 1;
			}
			
			// 건물주
			if(adminGroup.getId() == 3) bd.setAdminId(admin.getId());  
			
			List<Bd> list = this.bdService.search(bd, pageNum, rowPerPage, "bdId", "desc");
			for (Bd lastBd : list) lastBd.setLstInstall();
			
			bdList = new PaginatedListImpl(list, pageNum, this.bdService.searchCount(bd), rowPerPage);
			
			mav.addObject("bdList", bdList);
			mav.addObject("rownum", bdList.getFullListSize());
			mav.addObject("page", pageNum); 
			return mav;
		} 
		
		return mav;	
	}
	
	
	@RequestMapping(value = "/elcg/building/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("elcg/building/create");
		
		mav.addObject("date", new Date());
		mav.addObject("bd", new Bd());
		mav.addObject("bdGroup", new BdGroup());
		
		return mav;
	}

	@RequestMapping(value = "/elcg/building/create.htm", method = RequestMethod.POST)
	public ModelAndView create(Bd bd, HttpSession session, SessionStatus sessionStatus,
									@RequestParam(value = "bdGroupName", required = false) String bdGroupName,
									@RequestParam(value = "ownerId", required = false) String ownerId,
									@RequestParam(value = "payment", required = false) String periodDay) {
								
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		bd.setAdminId(ownerId);
		bd.setFstRgUsid(user.getId());
		bd.setFstRgDt(new Date());
		bd.setStatus("");
		
		BdGroup bdGroup = new BdGroup();
		bdGroup.setName(bdGroupName.trim());
		List<BdGroup> list = this.bdGroupService.search(bdGroup);
	
		if(list.size() != 0) {
			bd.setBdGroup(list.get(0));
		}
		else if(list.size() == 0) {
			bdGroup.setLatitude(bd.getLatitude());
			bdGroup.setLongitude(bd.getLongitude());
			bdGroup.setAddr(bd.getAddr());
			bdGroup.setFstRgUsid(bd.getFstRgUsid());
			bdGroup.setFstRgDt(new Date());
			
			this.bdGroupService.create(bdGroup);
			bd.setBdGroup(this.bdGroupService.search(bdGroup).get(0));
		}
		this.bdService.create(bd);
		
		BdPeriod bdPeriod = new BdPeriod();
		bdPeriod.setBdId(bd.getBdId());
		bdPeriod.setPeriodDay(periodDay);
		bdPeriod.setFstRgDt(new Date());
		bdPeriod.setFstRgUsid(user.getId());
		this.bdperiodDao.create(bdPeriod);
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/elcg/building/search.htm");
	}
	
	
	@RequestMapping("/elcg/building/detail.htm")
	public ModelAndView detail(HttpSession session,
								@RequestParam(value = "seq", required = true) Long bdId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/building/detail");
		Bd bd = this.bdService.get(bdId);
		mav.addObject("bd", bd);

		Admin owner = this.adminService.getById(bd.getAdminId());
		mav.addObject("owner", owner);
		
		BdPeriod bdPeriod = new BdPeriod();
		bdPeriod.setBdId(bdId);
		List<BdPeriod> bdPeriodList = this.bdperiodDao.search(bdPeriod);
		if(bdPeriodList.isEmpty()) return mav;
		mav.addObject("periodDay",bdPeriodList.get(0).getPeriodDay());
		
		bd.setLstInstall();
		return mav;
	}
	
	
	@RequestMapping(value = "/elcg/building/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("seq") Long bdId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/building/update");
		
		Bd bd = this.bdService.get(bdId);
		mav.addObject("bd", bd);
		
		BdPeriod bdPeriod = new BdPeriod();
		bdPeriod.setBdId(bdId);
		try {
			String periodDay = this.bdperiodDao.search(bdPeriod).get(0).getPeriodDay();
			mav.addObject("periodDay", periodDay);
		} catch(Exception e) {
			// 납기일 등록되어있지 않을때
			mav.addObject("periodDay", "9999");
		}
		
		
		Admin owner = this.adminService.getById(bd.getAdminId());
		mav.addObject("owner", owner);
		
		bd.setLstInstall();
		return mav;
	}

	
	@RequestMapping(value = "/elcg/building/update.htm", method = RequestMethod.POST)
	public ModelAndView update(Bd otherBd, HttpSession session, SessionStatus sessionStatus,
								@RequestParam(value = "seq", required = true) Long bdId,
								@RequestParam(value = "payment", required = false) String periodDay,
								@RequestParam(value = "bdGroupName", required =false) String bdGroupName,
								@RequestParam(value = "ownerId", required = false) String ownerId) {
		TraceLog.debug(bdId + " / " + bdGroupName + " / " + periodDay + " / " + ownerId); 
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup group = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		/*  ** BdPeriod 수정
		 *     bdPeriod : 업데이트할 객체 검색용
		 *     udtBdPeriod : 업데이트될 객체    */
		BdPeriod bdPeriod = new BdPeriod();
		bdPeriod.setBdId(bdId);
		try{
			BdPeriod udtBdPeriod = this.bdperiodDao.search(bdPeriod).get(0);
			udtBdPeriod.setPeriodDay(periodDay);
			udtBdPeriod.setLstChDt(new Date());
			udtBdPeriod.setLstChUsid(user.getId());
			this.bdperiodDao.update(udtBdPeriod);
		} catch(Exception e) {
			if (this.bdperiodDao.search(bdPeriod).size() == 0) {
				if (StringUtils.isNotEmpty(periodDay)) {
					bdPeriod.setPeriodDay(periodDay);
					bdPeriod.setFstRgUsid(user.getId());
					bdPeriod.setFstRgDt(new Date());
					this.bdperiodDao.create(bdPeriod);
				}
			}
		}
		
		// 운영자, 설치자 일때
		if (group.getId() == 1 || group.getId() == 2) {
		
			// Bd 수정
			Bd bd = this.bdService.get(bdId);
			bd.setAdminId(ownerId);
			bd.setLstChUsid(user.getId());
			bd.setLstChDt(new Date());
			bd.setName(otherBd.getName());
			bd.setAddr(otherBd.getAddr());
			bd.setLatitude(otherBd.getLatitude());
			bd.setLongitude(otherBd.getLongitude());
			
			BdGroup bdGroup = this.bdGroupService.get(bd.getBdGroup().getBdGroupId());
	
			if(StringUtils.isNotEmpty(bdGroupName)) bdGroup.setName(bdGroupName);
		
			if(bdGroup.getBdGroupId() != null && bdGroup.getBdGroupId() != 0) {
				bdGroup.setLstChUsid(user.getId());
				bdGroup.setLstChDt(new Date());
				bdGroup.setAddr(otherBd.getAddr());
				bdGroup.setLatitude(otherBd.getLatitude());
				bdGroup.setLongitude(otherBd.getLongitude());
				this.bdGroupService.update(bdGroup);
				
				bd.setBdGroup(bdGroup);
			}
			else {
				bdGroup.setAddr(bd.getAddr());
				bdGroup.setFstRgUsid(user.getId());
				bdGroup.setFstRgDt(new Date());
				this.bdGroupService.create(bdGroup);
				bd.setBdGroup(this.bdGroupService.search(bdGroup).get(0));
			}
					
			this.bdService.update(bd);
		}
		
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/elcg/building/search.htm");
	}
	
	
	@RequestMapping("/elcg/building/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String bdId) {
		String[] sequences = bdId.split(";");
		int deleteCount = 0;
		for (String id : sequences) {
			Bd bd = this.bdService.get(Long.parseLong(id));
			if (bd == null) continue;
			if (bd.getChargerGroupList().size() > 0) continue;

			deleteCount = this.bdService.delete(Long.parseLong(id));
		}

		return (deleteCount > 0);
	}

	
	// 건물그룹 검색 팝업
	@RequestMapping("/elcg/building/popup.htm")
	public ModelAndView buildingPopup(HttpSession session,
										  @RequestParam(value = "bdGroupName", required = false) String bdGroupName,
										  @RequestParam(value = "isInst", required = false) String isInst) {

		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		List<BdGroup> bdGroupList = new ArrayList<BdGroup>();
		
		if (StringUtils.isNotEmpty(bdGroupName)) {
			BdGroup bdGroup = new BdGroup();
			bdGroup.setName(bdGroupName);
			
			Bd bd = new Bd();
			bd.setBdGroup(bdGroup);
			if (adminGroup.getId() == 3) bd.setAdminId(admin.getId());
			
			List<Bd> list = bdService.search(bd);
			Map<Long, BdGroup> map = new HashMap<Long, BdGroup>();
			for (Bd anBd : list) {
				map.put(anBd.getBdGroup().getBdGroupId(), anBd.getBdGroup());
			}
			bdGroupList = new ArrayList<BdGroup>(map.values());
			if (StringUtils.isNotEmpty(isInst) && adminGroup.getId() == 2) bdGroupList = bdService.getInstallerBdGroupList(bdGroupName, admin.getId());
		}
		
		ModelAndView mav = new ModelAndView("elcg/building/popup");
		mav.addObject("bdGroupList", bdGroupList);
		return mav;
	}
	
	// 건물주 검색 팝업
	@RequestMapping("/elcg/building/ownerInfoPopup.htm")
	public ModelAndView popup(@RequestParam(value = "ownerName", required = false) String ownerName) {
		
			ModelAndView mav = new ModelAndView("elcg/building/ownerInfoPopup");
			
			Admin admin = new Admin();
			admin.setName(ownerName);
			admin.setAdminGroup(this.adminGroupService.get(3));
			
			List<Admin> list = this.adminService.search(admin);
			
			mav.addObject("list", list);
			
			return mav;
	}
	
	// 빌딩 셀렉트박스
	@RequestMapping(value = "/elcg/building/setBdSelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Bd> setBdSelect(HttpSession session,
									@RequestParam(value = "bdGroupId", required = true) String bdGroupId,
									@RequestParam(value = "isInst", required = false) String isInst)
									throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		TraceLog.debug("[bdGroupId : %s] [isInst : %s]", bdGroupId, isInst);
		
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		BdGroup groupList = this.bdGroupService.get(Long.parseLong(bdGroupId));

		Bd bd = new Bd();
		bd.setBdGroup(groupList);
		
		// 건물주
		if (adminGroup.getId() == 3) bd.setAdminId(admin.getId());
		
		List<Bd> bdList = new ArrayList<Bd>();
		
		if (StringUtils.isEmpty(isInst) && adminGroup.getId() == 2) bdList = bdService.getInstallerBdList(bdGroupId, admin.getId());
		else bdList = this.bdService.search(bd);
		
		if(bdList.size() != 0) return bdList;
		
		return null;
	}
	
	// 충전그룹 셀렉트박스
	@RequestMapping(value = "/elcg/building/setChargerGroupSelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<ChargerGroup> setChargerGroupSelect(@RequestParam(value = "bdSelect", required = true) String bdId)
										throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		TraceLog.debug("BD ID : " + bdId);
			if(bdId != null && Long.parseLong(bdId) > 0) {
				Bd bd = this.bdService.get(Long.parseLong(bdId));
				ChargerGroup chargerGroup = new ChargerGroup();
				chargerGroup.setBdGroupId(bd.getBdGroup().getBdGroupId());
				chargerGroup.setBdId(Long.parseLong(bdId));
				List<ChargerGroup> list = this.chargerGroupService.search(chargerGroup);
			
			if (list == null || list.size() == 0) return null;
		
			return list;
		}
		return null;
	}
	
	// 충전기 셀렉트박스
	@RequestMapping(value = "/elcg/building/setChargerMgmtNoSelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Charger> setChargerMgmtNoSelect(@RequestParam(value = "chargerGroupSelect", required = true) String chargerGroupId)
											throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		ChargerGroup chargerGroup = this.chargerGroupService.get(Long.parseLong(chargerGroupId));
		Charger charger = new Charger();
		charger.setChargerGroup(chargerGroup);
		charger.setIsSelectBox("y");
		List<Charger> chargerList = this.chargerService.search(charger);
		
		if(chargerList.isEmpty()) return null;
		
		return chargerList;
	}
}
