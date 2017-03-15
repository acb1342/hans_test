package com.mobilepark.doit5.cms.elcg.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.mobilepark.doit5.elcg.dao.ElcgDaoMybatis;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.mobilepark.doit5.elcg.model.ChargerList;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.elcg.service.ChargerGroupService;
import com.mobilepark.doit5.elcg.service.ChargerListService;
import com.mobilepark.doit5.elcg.service.ChargerService;
import com.mobilepark.doit5.statistics.dao.StatAdjustDaoMybatis;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.model.Pageable;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.elcg.controller
 * @Filename     : ChargerController.java
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
public class ChargerController {

	@Autowired
	private ChargerGroupService chargerGroupService;
	
	@Autowired
	private ChargerService chargerService;
	
	@Autowired
	private ChargerListService chargerListService;
	
	@Autowired
	private BdService bdService;
	
	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminGroupService adminGroupService;
	
	@Autowired
	private StatAdjustDaoMybatis statAdjustDaoMybatis;
	
	@Autowired
	private ElcgDaoMybatis elcgDaoMybatis;
	
	@RequestMapping("/elcg/charger/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "status", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bdGroup", required = false) String bdGroupName,
			@RequestParam(value = "bdSelect", required = false) String bdId,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId) {
		
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView("elcg/charger/search");
		mav.addObject("type",searchType);
		
		PaginatedList chargerList = null;
		Charger charger = new Charger();

		// 디폴트 페이지
		if(StringUtils.isEmpty(searchType)) {
			int chargerCnt = 0;
			// 사용자별 설치된 충전기 수 : 1.운영자 - 총 충전기수 / 2.설치자 - 등록한 총 충전기수 / 3.건물주 - 보유한 총 충전기수
			if (adminGroup.getId() == 3) {
				// 건물주가 보유한 건물 (Bd)
				Bd bd = new Bd();
				bd.setAdminId(admin.getId());
				List<Bd> bdList = this.bdService.search(bd);
				
				// 위의 건물에 속한 충전그룹 (ChargerGroup)
				if (bdList.size() > 0) {
					for (Bd bds : bdList) {
						ChargerGroup chargerGroup = new ChargerGroup();
						chargerGroup.setBdId(bds.getBdId());
						List<ChargerGroup> cgList = this.chargerGroupService.search(chargerGroup);
						
						// 위의 충전그룹에 속한 충전기 (Charger)
						if (cgList.size() > 0) {
							for (ChargerGroup cgs : cgList) {
								Charger c = new Charger();
								c.setChargerGroupId(cgs.getChargerGroupId());
								chargerCnt += this.chargerService.searchCount(c);
							}
						}
					}
				}
				mav.addObject("totalCount", chargerCnt);
			}
			else {
					if (adminGroup.getId() == 2) charger.setFstRgUsid(admin.getId());
					mav.addObject("totalCount", this.chargerService.searchCount(charger));
			}
			
			mav.addObject("type","default");
			return mav;
		}
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		List<Charger> list = null;
		int count = 0;
		// 건물별 검색
		if(searchType.equals("BD")) {

			if (StringUtils.isNotEmpty(bdId) && bdGroupId != null) { 
				BdGroup bdGroup = new BdGroup();
				bdGroup.setBdGroupId(bdGroupId);
				
				Bd bd = new Bd();
				bd.setBdId(Long.parseLong(bdId));
				bd.setBdGroup(bdGroup);
				
				ChargerGroup chargerGroup = new ChargerGroup();
				chargerGroup.setBd(bd);
				
				charger.setChargerGroup(chargerGroup);
				
				// 화면에서 상세/동명 유지
				if (bdGroupId != null && bdGroupId > 0) {
					BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
					Bd selBd = new Bd();
					selBd.setBdGroup(selGroupList);
					
					List<Bd> selBdList = null;
					if (adminGroup.getId() == 2) selBdList = bdService.getInstallerBdList(bdGroupId.toString(), admin.getId());
					else selBdList = this.bdService.search(selBd);
					
					mav.addObject("selBdList", selBdList);
					mav.addObject("selBdGroupId", bdGroupId);
				}
				
			}
			
		}
		// 기간별 검색
		else if(searchType.equals("DAY") && StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
			charger.setFromDate(fromDate);
			charger.setToDate(toDate);
		}
		
		// 설치자
		if(adminGroup.getId() == 2) charger.setFstRgUsid(admin.getId());
		// 건물주
		if(adminGroup.getId() == 3) {
			count = chargerService.getChargerCount(admin.getId(),
					changeFormat(fromDate), changeFormat(toDate), bdId, bdGroupId);
			list = chargerService.getChargerList(admin.getId(),
					changeFormat(fromDate), changeFormat(toDate), bdId, bdGroupId, new Pageable(pageNum, rowPerPage));
			
			// 설치자명 셋
			for (Charger chargers : list) {
				if (chargers == null || StringUtils.isEmpty(chargers.getFstRgUsid())) continue;
				if (this.adminService.getById(chargers.getFstRgUsid()) == null) continue;
				chargers.setWkName(this.adminService.getById(chargers.getFstRgUsid()).getName());
			}
		}
		else {
			count = this.chargerService.searchCount(charger);
			list = this.chargerService.search(charger, pageNum, rowPerPage, "fstRgDt", "desc");
		}
	
		chargerList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		TraceLog.debug("chargerList ===> " + chargerList.getFullListSize());
		
		mav.addObject("chargerList", chargerList);
		mav.addObject("rownum", chargerList.getFullListSize());
		mav.addObject("page", pageNum); 
		return mav;
	}
	
	@RequestMapping(value = "/elcg/charger/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		
		ModelAndView mav = new ModelAndView("elcg/charger/create");
		
		Admin inst = new Admin();
		inst.setAdminGroup(this.adminGroupService.get(2));
		
		List<Admin> installer = this.adminService.search(inst);
		
		mav.addObject("installer", installer);
		mav.addObject("charger", new Charger());
		mav.addObject("date", new Date());
		return mav;
	}

	
	@RequestMapping(value = "/elcg/charger/create.htm", method = RequestMethod.POST)
	public ModelAndView create(Charger charger, SessionStatus sessionStatus, HttpSession session,		
								@RequestParam(value = "chargerId", required = false) String chargerId,
								@RequestParam(value = "name", required = false) String chargerName,
								@RequestParam(value = "selInstaller", required = false) String selInstaller,
								@RequestParam(value = "chargerGroupSelect", required = false) Long chargerGroupId) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ChargerList chargerList = this.chargerListService.get(chargerId);
		
		if (user.getAdminGroup().getId() == 2) charger.setFstRgUsid(user.getId());
		else charger.setFstRgUsid(selInstaller); 
			
		charger.setFstRgDt(new Date());
		charger.setName(chargerName);
		charger.setMgmtNo(chargerList.getMgmtNo());
		charger.setCapacity(chargerList.getCapacity());
		charger.setChargerGroup(this.chargerGroupService.get(chargerGroupId));
		charger.setStatus("406101");
		charger.setDetailStatus("406201");
		charger.setChargeRate(chargerList.getChargeRate());
		this.chargerService.create(charger);
		TraceLog.info("[CREATE] [CHARGER_ID : %s] [MGMT_NO : %s] [Status : %s] [Detail Status : %s]",
				charger.getChargerId(), charger.getMgmtNo(), charger.getStatus(), charger.getDetailStatus());

		// 충전기 등록 완료시 빌딩 상태 업데이트
		this.elcgDaoMybatis.updateBdChargeAvailable(charger.getChargerGroup().getBd().getBdId(), user.getId());
		
		chargerList.setStatus("402102");
		this.chargerListService.update(chargerList);
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/elcg/charger/search.htm");
	}
	

	@RequestMapping("/elcg/charger/detail.htm")
	public ModelAndView detail(@RequestParam(value = "seq", required = true) String chargerId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/charger/detail");
		Charger charger = this.chargerService.get(chargerId);
		ChargerList chargerList = this.chargerListService.get(chargerId);
		
		try{
			Admin owner = adminService.getById(charger.getChargerGroup().getBd().getAdminId());
			mav.addObject("owner", owner);
		} catch(Exception e) {
			
		}
		List<Map<String, Object>> installer = this.statAdjustDaoMybatis.getInstallerInfo(chargerId);
		if(installer.size() > 0) mav.addObject("installer", installer.get(0).get("WK_NAME"));
		
		// 충전기별 통계페이지로 보낼 파라미터
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String toDate = format.format(new Date());
		int tmp = Integer.parseInt(toDate.substring(6));
		int fromDate = Integer.parseInt(toDate) - (tmp - 1);
		mav.addObject("fromDate", fromDate);
		mav.addObject("toDate", toDate);
		mav.addObject("charger", charger);
		mav.addObject("chargerList", chargerList);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/elcg/charger/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(	@RequestParam("seq") String chargerId,
										@RequestParam("bdId") Long bdId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/charger/update");
		
		Charger charger = this.chargerService.get(chargerId);
		mav.addObject("charger", charger);
		
		if (charger.getChargerGroup() != null && charger.getChargerGroup().getBd() != null) {
			Bd bd = this.bdService.get(charger.getChargerGroup().getBd().getId());
			Admin owner = adminService.getById(bd.getAdminId());
			mav.addObject("owner", owner);
		}
		
		ChargerGroup chargerGroup = new ChargerGroup();
		if (bdId != null) {
			chargerGroup.setBdGroupId(this.bdService.get(bdId).getBdGroup().getBdGroupId());
			chargerGroup.setBd(this.bdService.get(bdId));
			mav.addObject("chargerGroupList", this.chargerGroupService.search(chargerGroup));
		}
		
		List<Map<String, Object>> installer = this.statAdjustDaoMybatis.getInstallerInfo(chargerId);
		if(installer.size() > 0) mav.addObject("installer", installer.get(0).get("WK_NAME"));
		
		return mav;
	}

	
	@RequestMapping(value = "/elcg/charger/update.htm", method = RequestMethod.POST)
	public ModelAndView update(Charger otherCharger, SessionStatus sessionStatus, HttpSession session,
								@RequestParam(value = "seq", required = true) String chargerId,
								@RequestParam(value = "name", required = false) String chargerName,
								@RequestParam(value = "capacity", required = false) Long capacity,
								@RequestParam(value = "mgmtNo", required = false) String mgmtNo,
								@RequestParam(value = "listChargerId", required = false) String listChargerId,
								@RequestParam("chargerGroupId") Long chargerGroupId) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		Charger charger = this.chargerService.get(chargerId);
		ChargerList chargerList = this.chargerListService.get(chargerId);
		
		chargerList.setSerialNo(otherCharger.getSerialNo());
		chargerList.setMgmtNo(mgmtNo);
		chargerList.setCapacity(capacity);
		chargerList.setLstChUsid(user.getId());
		chargerList.setLstChDt(new Date());
		charger.setMgmtNo(mgmtNo);
		charger.setName(chargerName);
		charger.setCapacity(capacity);
		charger.setChargerGroup(this.chargerGroupService.get(chargerGroupId));
		charger.setLstChUsid(user.getId());
		charger.setLstChDt(new Date());
		
		this.chargerListService.update(chargerList);
		this.chargerService.update(charger);
		
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/elcg/charger/search.htm");
	}
	
	
	@RequestMapping("/elcg/charger/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String chargerId) {
		
		String[] sequences = chargerId.split(";");
		int deleteCount = 0;
		for (String id : sequences) {
			Charger charger = this.chargerService.get(id);
			ChargerList chargerList = this.chargerListService.get(id);
			if (charger != null) {
				deleteCount = this.chargerService.delete(id);
				
				if (chargerList != null) {
					chargerList.setStatus("402103");
					this.chargerListService.update(chargerList);
				}
				
			}
		}

		return (deleteCount > 0);
	}
	
	@RequestMapping(value = "/elcg/charger/serialCheck.json", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> serialValidationCheck(@RequestParam(value = "serial", required = true) String serial)
											throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		/* resList.result
		 *  0 - 존재하지않음
		 *  1 - 이미설치됨
		 *  2 - 등록 가능
		 */
		HashMap<String, Object> resList = new HashMap<String, Object>();
		ChargerList chargerList = new ChargerList();
		chargerList.setSerialNo(serial);
		
		List<ChargerList> list = this.chargerListService.search(chargerList);
		if(list.size() == 0) {
			resList.put("result", "0");
			return resList;
		}
		
		Charger charger = this.chargerService.get(list.get(0).getChargerId());
		if(charger != null) {
			resList.put("result", "1");
			return resList;
			
		}
		
		resList.put("result", "2");
		resList.put("chargerId", list.get(0).getChargerId());
		resList.put("serialNo", list.get(0).getSerialNo());
		resList.put("capacity", list.get(0).getCapacity());
		resList.put("mgmtNo", list.get(0).getMgmtNo());
		
		return resList;
	}
	

	@RequestMapping(value = "/elcg/charger/getOwner.json", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> getOwner(@RequestParam(value = "bdSelect", required = true) Long bdId)
											throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		if (bdId == null || bdId == 0) return null;
			
		Bd bd = this.bdService.get(bdId);
		Admin owner = this.adminService.getById(bd.getAdminId());
		
		if(owner == null) return null;
		
		HashMap<String, Object> resList = new HashMap<String, Object>();
		
		resList.put("ownerName", owner.getName());
		return resList;
	}

	// 상세보기 - 충전단말정보 팝업
	@RequestMapping("/elcg/charger/popup/infoSnPopup.htm")
	public ModelAndView infoSnPopup(@RequestParam(value = "chargerId", required = false) String chargerId) {
		
		ModelAndView mav = new ModelAndView("elcg/charger/popup/infoSnPopup");
		
		ChargerList charger = this.chargerListService.get(chargerId);
		
		if(charger == null) return mav;
		mav.addObject("charger", charger);
		
		return mav;
	}
	
	//상세보기 - 건물주정보 팝업
	@RequestMapping("/elcg/charger/popup/infoOwnerPopup.htm")
	public ModelAndView infoOwnerPopup(@RequestParam(value = "ownerId", required = true) String ownerId) {
		
		ModelAndView mav = new ModelAndView("elcg/charger/popup/infoOwnerPopup");
		
		Admin owner = this.adminService.getById(ownerId);
		
		if(owner == null) return mav;
		mav.addObject("owner", owner);
		
		Bd bd = new Bd();
		bd.setAdminId(ownerId);
		List<Bd> bdList = this.bdService.search(bd);
		mav.addObject("bdList", bdList);
		
		return mav;
	}
	
	//상세보기 - 충전그룹정보 팝업
	@RequestMapping("/elcg/charger/popup/infoGroupPopup.htm")
	public ModelAndView infoGroupPopup(@RequestParam(value = "chargerGroupId", required = true) Long chargerGroupId) {
		
		ModelAndView mav = new ModelAndView("elcg/charger/popup/infoGroupPopup");
		
		ChargerGroup group = this.chargerGroupService.get(chargerGroupId);
		
		if(group == null) return mav;
		mav.addObject("group", group);
		
		return mav;
	}
	
	
	public String changeFormat(String date) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, 8);
			TraceLog.debug("====change Date==="+date);
			return date;
		}
		return null;
	}
}
