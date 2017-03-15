package com.mobilepark.doit5.cms.elcg.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
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
import com.mobilepark.doit5.elcg.model.BrokenReport;
import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.StationApplication;
import com.mobilepark.doit5.elcg.model.ViewCustCenter;
import com.mobilepark.doit5.elcg.service.ApplicationService;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.elcg.service.BrokenReportService;
import com.mobilepark.doit5.elcg.service.ChargerService;
import com.mobilepark.doit5.elcg.service.ViewService;
import com.mobilepark.doit5.history.dao.PushMsgDaoMybatis;
import com.uangel.platform.log.TraceLog;
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
public class ApplicationAndReportController {

	@Autowired
	private BdService bdService;
	
	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminGroupService adminGroupService;
	
	@Autowired
	private ApplicationService applicationService;
	
	@Autowired
	private BrokenReportService brokenReportService;
	
	@Autowired
	private ViewService viewService;
	
	@Autowired
	private ChargerService chargerService;
	
	@Autowired
	private PushMsgDaoMybatis pushMsgDaoMybatis;
	
	@Autowired
	private ElcgDaoMybatis elcgDaoMybatis;
	
	@RequestMapping("/elcg/applicationAndReport/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "bdSelect", required = false) Long bdId,
			@RequestParam(value = "content", required = false) String body,
			@RequestParam(value = "searchType", required = false) String searchType) {
		
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView("elcg/applicationAndReport/search");
		
		PaginatedList viewList = null;
		List<ViewCustCenter> list = null;
		ViewCustCenter view = new ViewCustCenter();
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		if(StringUtils.isNotEmpty(searchType)) view.setWkType(searchType);
		if(bdId != null && bdId != 0) view.setBdId(bdId);
		if(StringUtils.isNotEmpty(body)) view.setBody(body);

		// 설치자
		if(adminGroup.getId() == 2) view.setWkUsid(admin.getId());
		// 건물주
		if(adminGroup.getId() == 3) view.setRcUsid(admin.getId());

		list = this.viewService.search(view, pageNum, rowPerPage);

		viewList = new PaginatedListImpl(list, pageNum, viewService.searchCount(view), rowPerPage);
		
		// 화면에서 상세/동명 유지
		if (bdGroupId != null && bdGroupId > 0) {
			BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
			Bd selBd = new Bd();
			selBd.setBdGroup(selGroupList);
			
			List<Bd> selBdList = null;
			if (adminGroup.getId() == 2) selBdList = bdService.getInstallerBdList(bdGroupId.toString(), admin.getId());
			else selBdList = this.bdService.search(selBd);
			
			mav.addObject("bdList", selBdList);
			mav.addObject("selBdGroupId", bdGroupId);
		}
		
		mav.addObject("viewList",viewList);
		mav.addObject("rownum", viewList.getFullListSize());
		mav.addObject("page", pageNum);

		return mav;
	}
	
	// 운영자 등록 폼
	@RequestMapping(value = "/elcg/applicationAndReport/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session,
										@RequestParam(value = "from", required = false) String from,
										@RequestParam(value = "chargerId", required = false) String chargerId) {

		ModelAndView mav = new ModelAndView("elcg/applicationAndReport/create");
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		mav.addObject("userName", user.getName());
		mav.addObject("date", new Date());
		
		// 이벤트 조회에서 접수하기 버튼 클릭 시
		if (StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(chargerId)) {
			Charger charger = this.chargerService.get(chargerId);
			if (charger == null || charger.getChargerGroup() == null || charger.getChargerGroup().getBd() == null) return mav;
			Admin owner = this.adminService.getById(charger.getChargerGroup().getBd().getAdminId());
			
			mav.addObject("from", from);
			mav.addObject("charger", charger);
			mav.addObject("owner", owner);
		}
		
		return mav;
	}

	// 운영자 등록
	@RequestMapping(value = "/elcg/applicationAndReport/create.htm", method = RequestMethod.POST)
	public ModelAndView create(Charger charger, SessionStatus sessionStatus, HttpSession session,
								@RequestParam(value = "searchType", required = true) String searchType,
								@RequestParam(value = "content", required = true) String content,
								@RequestParam(value = "bdGroupId", required = true) Long bdGroupId,
								@RequestParam(value = "bdSelect", required = true) Long bdId,
								@RequestParam(value = "setChargerMgmtNoSelect", required = false) String chargerId,
								@RequestParam(value = "installerId", required = false) String installerId) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		// 설치신청
		if(searchType.equals("1")) {
			StationApplication stationApplication = new StationApplication();
			Bd bd = this.bdService.get(bdId);
			stationApplication.setChan("313106");
			stationApplication.setRcUsid(user.getId());
			stationApplication.setFstRgUsid(user.getId());
			stationApplication.setRcDt(new Date());
			stationApplication.setFstRgDt(new Date());
			stationApplication.setBody(content);
			stationApplication.setBdGroupId(bdGroupId);
			stationApplication.setBd(bd);
			stationApplication.setAddr(bd.getBdGroup().getAddr() + " " + bd.getName());
			if(StringUtils.isEmpty(installerId)) stationApplication.setStatus("407101");
			if(StringUtils.isNotEmpty(installerId)) {
				// 작업 지시자
				stationApplication.setStatus("407102");
				stationApplication.setOdUsid(user.getId());
				stationApplication.setOdDt(new Date());
				// 작업자
				stationApplication.setWkUsid(installerId);
			}
				
			this.applicationService.create(stationApplication);
			
			if (StringUtils.isNotEmpty(stationApplication.getWkUsid()) && StringUtils.isNotEmpty(stationApplication.getRcUsid())) {
				String title = "설치 배정 안내";
				String msg = pushTime() + "에 충전기 설치건이 배정되었습니다.";
				// 작업자에게 푸시 메세지 전송
				Admin installAdmin = this.adminService.get(installerId);
				this.sendPush(installAdmin, title, msg, "901161");
				
				// 건물주에게 푸시 메세지 전송
				msg = pushTime() + "부터 신청하신 충전기 설치를 처리중에있습니다.";
				Admin ownerAdmin = this.adminService.get(stationApplication.getRcUsid());
				this.sendPush(ownerAdmin, title, msg, "901131");
				
			}
		}
		// 고장신고
		if(searchType.equals("2")) {
			BrokenReport brokenReport = new BrokenReport();
			brokenReport.setChan("313106");
			brokenReport.setRcUsid(user.getId());
			brokenReport.setFstRgUsid(user.getId());
			brokenReport.setRcDt(new Date());
			brokenReport.setFstRgDt(new Date());
			brokenReport.setBody(content);
			brokenReport.setBdGroupId(bdGroupId);
			brokenReport.setBd(this.bdService.get(bdId));
			brokenReport.setChargerId(chargerId);
			
			if(StringUtils.isEmpty(installerId)) brokenReport.setStatus("409101");
			if(StringUtils.isNotEmpty(installerId) && StringUtils.isNotEmpty(brokenReport.getRcUsid())) {
				brokenReport.setStatus("409102");
				brokenReport.setOdUsid(user.getId());
				brokenReport.setOdDt(new Date());
				
				// 작업자에게 푸시 메세지 전송
				Admin installAdmin = this.adminService.get(installerId);
				String title = "충전기 고장수리 배정 안내";
				String msg = pushTime() + "에 충전기 고장처리건이 배정되었습니다.";
				this.sendPush(installAdmin, title, msg, "901162");
				
				// 건물주에게 푸시 메세지 전송
				title = "충전기 수리 안내";
				msg = pushTime() + "부터 신고하신 충전기 고장을 처리중에있습니다.";
				Admin ownerAdmin = this.adminService.get(brokenReport.getRcUsid());
				this.sendPush(ownerAdmin, title, msg, "901133");
				
				// 충전기 상태 오류로 변경
				if (StringUtils.isNotEmpty(chargerId) && StringUtils.isEmpty(brokenReport.getWkUsid())) {
					Charger udtCharger = this.chargerService.get(chargerId);
					udtCharger.setStatus("406104");
					udtCharger.setDetailStatus("406208");
					udtCharger.setLstChDt(new Date());
					udtCharger.setLstChUsid(user.getId());
					this.chargerService.update(udtCharger);
					TraceLog.info("[UPDATE] [CHARGER_ID : %s] [MGMT_NO : %s] [Status : %s] [Detail Status : %s]",
									udtCharger.getChargerId(), udtCharger.getMgmtNo(), udtCharger.getStatus(), udtCharger.getDetailStatus());
				}
				
				brokenReport.setWkUsid(installerId);
			}
			
			this.brokenReportService.create(brokenReport);
			
			// 건물정보입력시 건물상태변경
			if (bdId != null && bdId != 0) this.elcgDaoMybatis.updateBdChargeAvailable(bdId, user.getId());
		}
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/elcg/applicationAndReport/search.htm");
	}
	

	@RequestMapping("/elcg/applicationAndReport/detail.htm")
	public ModelAndView detail(@RequestParam(value = "seq", required = true) Long snId,
								 @RequestParam(value = "wkType", required = true) String wkType) throws Exception {
		TraceLog.debug("SNID : "+snId);
		ModelAndView mav = new ModelAndView("elcg/applicationAndReport/detail");
		mav.addObject("wkType", wkType);
		
		String ownerId = null;
		String installerId = null;
		if( StringUtils.equals(wkType, "802101") ) {
			StationApplication obj = this.applicationService.get(snId);
			
			// 접수자
			if (obj != null) {
				try {
					String rcName = this.adminService.getById(obj.getRcUsid()).getName();
					if (StringUtils.isNotEmpty(rcName)) mav.addObject("rcName", rcName);
				} catch(Exception e) {
				}
			}
			
			// 건물주
			try{
				ownerId = obj.getBd().getAdminId();
			} catch(Exception e) {
				
			}
			
			// 설치자
			installerId = obj.getWkUsid();
			mav.addObject("obj", obj);
		}
		else {
			BrokenReport obj = this.brokenReportService.get(snId);
			
			// 접수자
			if (obj != null) {
				try {
					String rcName = this.adminService.getById(obj.getRcUsid()).getName();
					if (StringUtils.isNotEmpty(rcName)) mav.addObject("rcName", rcName);
				} catch(Exception e) {
				}
			}
			
			try{
				ownerId = obj.getBd().getAdminId();
			} catch(Exception e) {
				
			}
			
			installerId = obj.getWkUsid();
			mav.addObject("obj", obj);
			
			if(StringUtils.isNotEmpty(obj.getChargerId())) {
			Charger charger = this.chargerService.get(obj.getChargerId());
			mav.addObject("charger", charger);
			}
		}

		if(StringUtils.isNotEmpty(ownerId)) {
			Admin owner = this.adminService.getById(ownerId);
			mav.addObject("owner", owner);
		}
		
		Admin installer = this.adminService.getById(installerId);
		mav.addObject("installer", installer);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/elcg/applicationAndReport/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam(value = "seq", required = true) Long snId,
									 @RequestParam(value = "wkType", required = true) String wkType) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/applicationAndReport/update");
		mav.addObject("wkType", wkType);
		
		String ownerId = null;
		String installerId = null;
		if( StringUtils.equals(wkType, "802101") ) {
			StationApplication obj = this.applicationService.get(snId);
			
			// 접수자
			if (obj != null) {
				try {
					String rcName = this.adminService.getById(obj.getRcUsid()).getName();
					if (StringUtils.isNotEmpty(rcName)) mav.addObject("rcName", rcName);
				} catch(Exception e) {
				}
			}
			
			try{
				ownerId = obj.getBd().getAdminId();
			} catch(Exception e) {
				
			}
			
			installerId = obj.getWkUsid();
			mav.addObject("obj", obj);
		}
		else {
			BrokenReport obj = this.brokenReportService.get(snId);
			
			// 접수자
			if (obj != null) {
				try {
					String rcName = this.adminService.getById(obj.getRcUsid()).getName();
					if (StringUtils.isNotEmpty(rcName)) mav.addObject("rcName", rcName);
				} catch(Exception e) {
				}
			}
			
			try{
				ownerId = obj.getBd().getAdminId();
			} catch(Exception e) {
				
			}
			
			installerId = obj.getWkUsid();
			mav.addObject("obj", obj);
			
			if(StringUtils.isNotEmpty(obj.getChargerId())) {
			Charger charger = this.chargerService.get(obj.getChargerId());
			mav.addObject("charger", charger);
			}
		}

		if(StringUtils.isNotEmpty(ownerId)) {
			Admin owner = this.adminService.getById(ownerId);
			mav.addObject("owner", owner);
		}
		
		Admin installer = this.adminService.getById(installerId);
		mav.addObject("installer", installer);
		
		return mav;
	}

	
	@RequestMapping(value = "/elcg/applicationAndReport/update.htm", method = RequestMethod.POST)
	public ModelAndView update(SessionStatus sessionStatus, HttpSession session,
								@RequestParam(value = "seq", required = false) Long snId,
								@RequestParam(value = "wkType", required = false) String wkType,
								@RequestParam(value = "installerId", required = false) String installerId,
								@RequestParam(value = "status", required = false) String status,
								@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
								@RequestParam(value = "bdSelect", required = false) Long bdId,
								@RequestParam(value = "setChargerMgmtNoSelect", required = false) String chargerId,
								@RequestParam(value = "content", required = false) String body) {
		
		TraceLog.debug("snId : " + snId + " / wkType : " + wkType + " / installerId : " + installerId + " / status : " + status);
		TraceLog.debug("bdGroupId : " + bdGroupId + " / bdId : " + bdId + " / body : " + body + " / chargerId : " + chargerId);
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		// 설치신청
		if(wkType.equals("802101")) {
			StationApplication stationApplication = this.applicationService.get(snId);
			stationApplication.setLstChUsid(user.getId());
			stationApplication.setLstChDt(new Date());
			stationApplication.setBody(body);
			
			// 운영자
			if (user.getAdminGroup().getId() == 1) {
				
				if (bdGroupId != null && bdGroupId != 0) stationApplication.setBdGroupId(bdGroupId);
				
				try{
					if (bdId != null && bdId != 0) {
						Bd bd = this.bdService.get(bdId);
						stationApplication.setBd(bd);
						stationApplication.setAddr(bd.getBdGroup().getAddr() + " " + bd.getName());
					}
				} catch(Exception e) {
					
				}
				
				if(StringUtils.isNotEmpty(installerId)) {
					// 작업 지시자
					stationApplication.setStatus("407102");
					stationApplication.setOdUsid(user.getId());
					stationApplication.setOdDt(new Date());
					// 작업자
					stationApplication.setWkUsid(installerId);
					// 작업자에게 푸시 메세지 전송
					Admin installAdmin = this.adminService.get(installerId);
					String title = "설치 배정 안내";
					String msg = pushTime() + "에 충전기 설치건이 배정되었습니다.";
					this.sendPush(installAdmin, title, msg, "901161");
					
					// 건물주에게 푸시 메세지 전송
					title = "충전기 설치 안내";
					msg = pushTime() + "부터 신청하신 충전기 설치를 처리중에있습니다.";
					Admin ownerAdmin = this.adminService.get(stationApplication.getRcUsid());
					this.sendPush(ownerAdmin, title, msg, "901131");
				}
			}
			
			this.applicationService.update(stationApplication);
		}
		// 고장신고
		if(wkType.equals("802102")) {
			BrokenReport brokenReport = this.brokenReportService.get(snId);
			brokenReport.setLstChUsid(user.getId());
			brokenReport.setLstChDt(new Date());
			brokenReport.setBody(body);
			
			if (user.getAdminGroup().getId() == 1) {
			
				if (bdGroupId != null && bdGroupId != 0) brokenReport.setBdGroupId(bdGroupId);
				if (bdId != null && bdId != 0) brokenReport.setBd(this.bdService.get(bdId));
				if (StringUtils.isNotEmpty(chargerId)) brokenReport.setChargerId(chargerId);
				
				if(StringUtils.isNotEmpty(installerId)) {
					brokenReport.setStatus("409102");
					brokenReport.setOdUsid(user.getId());
					brokenReport.setOdDt(new Date());
					
					// 작업자에게 푸시 메세지 전송
					Admin installAdmin = this.adminService.get(installerId);
					String title = "충전기 고장수리 배정 안내";
					String msg = pushTime() + "에 충전기 고장처리건이 배정되었습니다.";
					this.sendPush(installAdmin, title, msg, "901162");
					
					// 건물주에게 푸시 메세지 전송
					title = "충전기 수리 안내";
					msg = pushTime() + "부터 신고하신 충전기 고장을 처리중에있습니다.";
					Admin ownerAdmin = this.adminService.get(brokenReport.getRcUsid());
					this.sendPush(ownerAdmin, title, msg, "901133");
					
					// 충전기 상태 오류로 변경
					if (StringUtils.isNotEmpty(chargerId) && StringUtils.isEmpty(brokenReport.getWkUsid())) {
						Charger udtCharger = this.chargerService.get(chargerId);
						if (udtCharger != null) {
							udtCharger.setStatus("406104");
							udtCharger.setDetailStatus("406208");
							udtCharger.setLstChDt(new Date());
							udtCharger.setLstChUsid(user.getId());
							this.chargerService.update(udtCharger);
							TraceLog.info("[UPDATE] [CHARGER_ID : %s] [MGMT_NO : %s] [Status : %s] [Detail Status : %s]",
									udtCharger.getChargerId(), udtCharger.getMgmtNo(), udtCharger.getStatus(), udtCharger.getDetailStatus());
						}
					}
					
					brokenReport.setWkUsid(installerId);
				}
			}
			
			this.brokenReportService.update(brokenReport);

			// 건물정보입력시 건물상태변경
			if (bdId != null && bdId != 0) this.elcgDaoMybatis.updateBdChargeAvailable(bdId, user.getId());
		}
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/elcg/applicationAndReport/search.htm");
	}
	
	
	@RequestMapping("/elcg/applicationAndReport/delete.json")
	@ResponseBody
	public Boolean delete( @RequestParam(value = "id", required = false) String typeId) {
		String[] sequences = typeId.split(";");
		int deleteCount = 0;
		for(String id : sequences) {
			Long snId = Long.parseLong( id.substring( id.indexOf("_") + 1 ) );
			String type = id.substring( 0, id.indexOf("_") );
			
			if("802101".equals(type)) {
				StationApplication sa = this.applicationService.get(snId);
				if(sa != null) deleteCount = this.applicationService.delete(snId);
			}
			else {
				BrokenReport br = this.brokenReportService.get(snId);
				if(br != null) deleteCount = this.brokenReportService.delete(snId);
			}
		}
		
		return (deleteCount > 0);
	}
	
	// 건물정보 불러오기
	@RequestMapping(value = "/elcg/applicationAndReport/loadBdInfo.json", method = RequestMethod.POST)
	@ResponseBody
	public HashMap<String, Object> loadBdInfo(@RequestParam(value = "bdId", required = true) Long bdId)
											throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		Bd bd = this.bdService.get(bdId);
		
		if(bd == null || bd.getBdId() == null || StringUtils.isEmpty(bd.getAdminId())) return null;

		Admin admin = this.adminService.getById(bd.getAdminId());

		if(admin == null || StringUtils.isEmpty(admin.getId())) return null;
		

		String latitude = bd.getLatitude().toString() + " / " + bd.getLongitude().toString();
		HashMap<String, Object> resList = new HashMap<String, Object>();
		resList.put("ownerName", admin.getName());
		resList.put("ownerPhone", admin.getMobile());
		resList.put("addr", bd.getBdGroup().getAddr());
		resList.put("latitude",latitude);
			
		return resList;
	}
	
	// 설치자 배정
	@RequestMapping("/elcg/applicationAndReport/installerInfoPopup.htm")
	public ModelAndView popup(@RequestParam(value = "installerName", required = false) String installerName) {
		
			ModelAndView mav = new ModelAndView("elcg/applicationAndReport/installerInfoPopup");
			
			Admin admin = new Admin();
			admin.setName(installerName);
			admin.setAdminGroup(this.adminGroupService.get(2));
			
			List<Admin> list = this.adminService.search(admin);
			
			Collections.sort(list, new Comparator<Admin>() {
				@Override
				public int compare(Admin o1, Admin o2) {
					return (o1.getNoCompleWkCnt() < o2.getNoCompleWkCnt()) ? -1: (o1.getNoCompleWkCnt() > o2.getNoCompleWkCnt()) ? 1:0 ;
				}
			});
			
			mav.addObject("list", list);
			
			return mav;
	}
		
	// 건물주가 신청/신고 등록
	@RequestMapping("elcg/applicationAndReport/createCust.htm")
	public ModelAndView createCust(HttpSession session,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "content", required = false) String body) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		StationApplication stationApplication = new StationApplication();
		BrokenReport brokenReport = new BrokenReport();
		
		// 설치신청
		if (StringUtils.equals(type, "1")) {
			stationApplication.setRcUsid(user.getId());
			stationApplication.setRcDt(new Date());
			stationApplication.setFstRgUsid(user.getId());
			stationApplication.setFstRgDt(new Date());
			stationApplication.setBody(body);
			stationApplication.setStatus("407101");
			stationApplication.setChan("313106");
			
			this.applicationService.create(stationApplication);
		}
		
		// 고장신고
		if (StringUtils.equals(type, "2")) {
			brokenReport.setRcUsid(user.getId());
			brokenReport.setRcDt(new Date());
			brokenReport.setFstRgUsid(user.getId());
			brokenReport.setFstRgDt(new Date());
			brokenReport.setBody(body);
			brokenReport.setStatus("409101");
			brokenReport.setChan("313106");
			
			this.brokenReportService.create(brokenReport);
		}
		
		return new ModelAndView("redirect:/elcg/applicationAndReport/search.htm");
	}
	
	
	// 설치자가 완료처리
	@RequestMapping("/elcg/applicationAndReport/updateStatusComplete.htm")
	public ModelAndView updateStatusComplete( HttpSession session,
													@RequestParam(value = "seq", required = true) String snId,
													@RequestParam(value = "wkType", required = true) String wkType) {
		
		TraceLog.debug(snId + " / " + wkType);
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		//  설치
		if (StringUtils.equals(wkType, "802101")) {
			StationApplication stationApplication = this.applicationService.get(Long.parseLong(snId));
			stationApplication.setStatus("407103");
			stationApplication.setWkUsid(admin.getId());
			stationApplication.setWkDt(new Date());
			stationApplication.setLstChUsid(admin.getId());
			stationApplication.setLstChDt(new Date());
			this.applicationService.update(stationApplication);
			
			Admin owner = this.adminService.getById(stationApplication.getRcUsid());
			String title = "충전기 설치 완료 안내";
			String msg = pushTime() + "에 신청하신 충전기 설치가 완료되었습니다.";
			this.sendPush(owner, title, msg, "901132");
		}
		
		// 고장수리
		if (StringUtils.equals(wkType, "802102")) {
			BrokenReport brokenReport = this.brokenReportService.get(Long.parseLong(snId));
			brokenReport.setStatus("409103");
			brokenReport.setWkUsid(admin.getId());
			brokenReport.setWkDt(new Date());
			brokenReport.setLstChUsid(admin.getId());
			brokenReport.setLstChDt(new Date());
			this.brokenReportService.update(brokenReport);
			
			Admin owner = this.adminService.getById(brokenReport.getRcUsid());
			String title = "충전기 수리 완료 안내";
			String msg = pushTime() + "에 충전기 고장 수리가 완료되었습니다.";
			this.sendPush(owner, title, msg, "901134");
			
			// 고장수리 완료처리시 충전기상태 변경
			Charger charger = this.chargerService.get(brokenReport.getChargerId());
			charger.setStatus("406101");
			charger.setDetailStatus("406201");
			this.chargerService.update(charger);
			
			// 건물상태변경
			// added by kodaji
			this.elcgDaoMybatis.updateBdChargeAvailable(brokenReport.getBd().getBdId(), admin.getId());
		}
		
		return new ModelAndView("redirect:/elcg/applicationAndReport/search.htm");
	}
	
	// 푸시메세지
	private void sendPush(Admin admin, String title, String msg, String category) {
		Map<String, Object> pushMap = new HashMap<>();
		if (admin.getAdminGroup().getId() == 2) pushMap.put("custType", "101204");
		if (admin.getAdminGroup().getId() == 3) pushMap.put("custType", "101203");
		pushMap.put("usid", admin.getId());
		pushMap.put("custName", admin.getName());
		pushMap.put("os", "301401");
		pushMap.put("mobile", admin.getMobile());
		pushMap.put("title", title);
		pushMap.put("msg", msg);
		pushMap.put("category", category);
		if (StringUtils.isEmpty(admin.getPushToken())) pushMap.put("pushToken", "");
		else pushMap.put("pushToken", admin.getPushToken());
	
		pushMsgDaoMybatis.insertPushQueue(pushMap);
	}
	
	// 메세지 문구용 시간
	private String pushTime() {
		DateFormat format = new SimpleDateFormat("HH시 mm분 ss초");
		String pushTime = format.format(new Date());
		return pushTime;
	}
	
	
}
