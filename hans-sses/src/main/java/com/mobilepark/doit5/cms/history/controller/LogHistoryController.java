package com.mobilepark.doit5.cms.history.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.customer.service.MemberService;
import com.mobilepark.doit5.statistics.dao.LogHistoryDaoMybatis;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

@Controller
@SessionAttributes("logHistory")
public class LogHistoryController {
	
	@Autowired
	private LogHistoryDaoMybatis logHistoryDaoMybaits;
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private MemberService memberService;
	
	/**
	 * 사용자 이력 조회
	 */
	@RequestMapping("/history/log/user.htm")
	public ModelAndView user(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "usid", required = false) String usid) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [usid:%s]", page, searchType, fromDate, toDate, usid);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		String[] logTypes = {"313201", "313202"};
		String[] accessDevice = {"313101", "313104", "313105"};
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		param.put("fromDate", changeFormat(fromDate, 8));			
		param.put("toDate", changeFormat(toDate, 8));
		param.put("logTypes", logTypes);
		param.put("accessDevice", accessDevice);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "logIn")) {
			param.put("usid", usid);
			count = logHistoryDaoMybaits.getHistCustCount(param);
			list = logHistoryDaoMybaits.getHistCustList(param);
		}
		else if (StringUtils.equals(searchType, "pushSend")) {
			param.put("usid", usid);
			count = logHistoryDaoMybaits.getPushMsgCount(param);
			list = logHistoryDaoMybaits.getPushMsgList(param);
		}
		
		PaginatedList logUserList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("history/log/user");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logUserList", logUserList);

		return mav;
	}
	
	/**
	 * 충전기 이력 조회
	 */
	@RequestMapping("/history/log/charger.htm")
	public ModelAndView charger(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "chargerNumber", required = false) String mgmtNo) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [chargerNumber:%s]", page, searchType, fromDate, toDate, mgmtNo);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		param.put("fromDate", changeFormat(fromDate, 8));			
		param.put("toDate", changeFormat(toDate, 8));
		param.put("mgmtNo", mgmtNo);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "run")) {
			count = this.logHistoryDaoMybaits.getStationApplicationCount(param);
			list = this.logHistoryDaoMybaits.getStationApplicationList(param);
		}
		else if (StringUtils.equals(searchType, "stop")) {
			count = this.logHistoryDaoMybaits.getBrokenReportCount(param);
			list = this.logHistoryDaoMybaits.getBrokenReportList(param);
		}

		PaginatedList logChargerList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		ModelAndView mav = new ModelAndView("history/log/charger");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logChargerList", logChargerList);

		return mav;
	}
	
	/**
	 * 건물주 이력 조회
	 */
	@RequestMapping("/history/log/buildingOwner.htm")
	public ModelAndView buildingOwner(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "usid", required = false) String ownerId) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [usid:%s]", page, searchType, fromDate, toDate, ownerId);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		param.put("fromDate", changeFormat(fromDate, 8));			
		param.put("toDate", changeFormat(toDate, 8));
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "report")) {	
			param.put("rcUsid", ownerId);
			count = this.logHistoryDaoMybaits.getLandlordCount(param);
			list = this.logHistoryDaoMybaits.getLandlordList(param);
		} 
		else if (StringUtils.equals(searchType, "pushSend")) {
			param.put("wkUsid", ownerId);
			count = this.logHistoryDaoMybaits.getLandlordPushMsgCount(param);
			list = this.logHistoryDaoMybaits.getLandlordPushMsgList(param);
		}

		PaginatedList logBuildingOwnerList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("history/log/buildingOwner");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logBuildingOwnerList", logBuildingOwnerList);
		mav.addObject("viewType", searchType);

		return mav;
	}
	
	/**
	 * 설치자 이력 조회
	 */
	@RequestMapping("/history/log/installer.htm")
	public ModelAndView installer(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "usid", required = false) String ownerId) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [usid:%s]", page, searchType, fromDate, toDate, ownerId);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		param.put("fromDate", changeFormat(fromDate, 8));			
		param.put("toDate", changeFormat(toDate, 8));
		param.put("wkUsid", ownerId);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "install")) {	
			count = this.logHistoryDaoMybaits.getInstallerCount(param);
			list = this.logHistoryDaoMybaits.getInstallerList(param);
			
			for (Map<String, Object> map : list) {
				
				if (map.get("WK_TYPE") == null) continue;
				if (StringUtils.equals(map.get("WK_TYPE").toString(), "802102")) continue;
				
				param.put("bdId", (Long)map.get("BD_ID"));
				map.put("CHARGER_GROUP_NAME", logHistoryDaoMybaits.installerChargerGroupCount(param));
				map.put("MGMT_NO", logHistoryDaoMybaits.installerChargerCount(param));
			}
		} 
		else if (StringUtils.equals(searchType, "pushSend")) {
			count = this.logHistoryDaoMybaits.getLandlordPushMsgCount(param);
			list = this.logHistoryDaoMybaits.getLandlordPushMsgList(param);
		}

		PaginatedList logBuildingOwnerList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("history/log/installer");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logBuildingOwnerList", logBuildingOwnerList);
		mav.addObject("viewType", searchType);

		return mav;
	}
	
	/**
	 * 설치자 이력 조회
	 */
	@RequestMapping("/history/log/popupChargerList.htm")
	public ModelAndView installerPopup(
			@RequestParam(value = "bdId", required = false) String bdId,
			@RequestParam(value = "usid", required = false) String usid) throws Exception {
		
		TraceLog.info("[bdId:%s]  [usid:%s]", bdId, usid);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bdId", bdId);
		param.put("wkUsid", usid);
		
		List<Map<String, Object>> list = logHistoryDaoMybaits.installerChargerList(param);
		ModelAndView mav = new ModelAndView("history/log/popupChargerList");
		mav.addObject("list", list);
		return mav;
	}
		
	
	
	/**
	 * 한전 연동 이력 조회
	 */
	@RequestMapping("/history/log/kepco.htm")
	public ModelAndView kepco(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "historyDate", required = false) String historyDate) throws Exception {
		
		TraceLog.info("[page:%s] [historyDate:%s]", page, historyDate);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.isNotEmpty(historyDate)) {
			param.put("historyDate", this.changeFormat(historyDate, 8));

			count = logHistoryDaoMybaits.getLogKepcoCount(param);
			list = logHistoryDaoMybaits.getLogKepcoList(param);
		}
		PaginatedList logKepcoList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		ModelAndView mav = new ModelAndView("history/log/kepco");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logKepcoList", logKepcoList);

		return mav;
	}
	
	/**
	 * ThingPlug 연동 이력 조회
	 */
	@RequestMapping("/history/log/thingPlug.htm")
	public ModelAndView thingPlug(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "chargerNumber", required = false) String chargerNumber) throws Exception {
		
		TraceLog.info("[page:%s] [fromDate:%s] [toDate:%s] [chargerNumber:%s]", page, fromDate, toDate, chargerNumber);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.isNotEmpty(fromDate) &&
				StringUtils.isNotEmpty(toDate) &&
				StringUtils.isNotEmpty(chargerNumber)) {
			param.put("fromDate", this.changeFormat(fromDate, 8));
			param.put("toDate", this.changeFormat(toDate, 8));
			param.put("mgmtNo", chargerNumber);

			count = logHistoryDaoMybaits.getLogThingPlugCount(param);
			list = logHistoryDaoMybaits.getLogThingPlugList(param);
		}
		PaginatedList logThingPlugList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		ModelAndView mav = new ModelAndView("history/log/thingPlug");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logThingPlugList", logThingPlugList);

		return mav;
	}
	
	/**
	 * GCM/APNS 연동 이력 조회
	 */
	@RequestMapping("/history/log/gcmApns.htm")
	public ModelAndView gcmApns(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "usType", required = false) String usType,
			@RequestParam(value = "usid", required = false) String usid) throws Exception {
		
		TraceLog.info("[page:%s] [fromDate:%s] [toDate:%s] [os:%s] [usType:%s] [usid:%s]", page, fromDate, toDate, os, usType, usid);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.isNotEmpty(fromDate) &&
				StringUtils.isNotEmpty(toDate) &&
				StringUtils.isNotEmpty(usType) &&
				StringUtils.isNotEmpty(usid)) {
			param.put("fromDate", this.changeFormat(fromDate, 8));
			param.put("toDate", this.changeFormat(toDate, 8));
			param.put("os", os);
			param.put("custType", usType);
			param.put("usid", usid);

			count = logHistoryDaoMybaits.getLogGcmApnsCount(param);
			list = logHistoryDaoMybaits.getLogGcmApnsList(param);
		}

		PaginatedList logGcmApnsList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		ModelAndView mav = new ModelAndView("history/log/gcmApns");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logGcmApnsList", logGcmApnsList);

		return mav;
	}
	
	/**
	 * 후불 결제사 연동 이력 조회
	 */
	@RequestMapping("/history/log/payment.htm")
	public ModelAndView payment(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "historyDate", required = false) String historyDate,
			@RequestParam(value = "usid", required = false) String usid) throws Exception {
		
		TraceLog.info("[page:%s] [historyDate:%s] [usid:%s]", page, historyDate, usid);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.isNotEmpty(historyDate) && StringUtils.isNotEmpty(usid)) {
			param.put("historyDate", this.changeFormat(historyDate, 6));
			param.put("usid", usid);
			count = logHistoryDaoMybaits.getLogPaymentCount(param);
			list = logHistoryDaoMybaits.getLogPaymentList(param);
		}

		PaginatedList logPaymentList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		ModelAndView mav = new ModelAndView("history/log/payment");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("logPaymentList", logPaymentList);

		return mav;
	}
	
	/**
	 * 사용자조회 팝업
	 */
	@RequestMapping("/history/log/popup.htm")
	public ModelAndView userPopup(
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		TraceLog.info("[searchType:%s] [searchValue:%s]", searchType, searchValue);
		
		ModelAndView mav = new ModelAndView("history/log/popup");

		if (StringUtils.isEmpty(searchType) || StringUtils.isEmpty(searchValue)) return mav;
		
		if (StringUtils.equals(searchType, "101206")) {
			
			Member member = new Member();
			member.setName(searchValue);
			member.setStatus("301101,301102");

			List<Member> memberList = memberService.search(member);	
			mav.addObject("memberList", memberList);
		} else {
		
			AdminGroup adminGroup = new AdminGroup();
			if (StringUtils.equals(searchType, "101203")) adminGroup.setId(3); // 건물주
			if (StringUtils.equals(searchType, "101204")) adminGroup.setId(2); // 설치자
			
			Admin admin = new Admin();
			admin.setAdminGroup(adminGroup);
			admin.setName(searchValue);
			
			List<Admin> adminList = adminService.search(admin);
			mav.addObject("adminList", adminList);
		}
		return mav;
	}
	
	
	/**
	 * 건물주 정보 ( 이름 / 건물명 )
	 */
	@RequestMapping("/history/log/buildingOwnerPopup.htm")
	public ModelAndView buildingOwnerPopup(
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		TraceLog.info("[searchType:%s] [searchValue:%s]", searchType, searchValue);
		
		Map<String, Object> param = new HashMap<String, Object>();
		List<Map<String, Object>> list = null;
		
		if (StringUtils.equals(searchType, "buildingOwner")) {
			param.put("adminName", searchValue);
			list = this.logHistoryDaoMybaits.getAdminLandlordList(param);
		}
		
		if (StringUtils.equals(searchType, "installer")) {
			
		}
		
		if (StringUtils.equals(searchType, "user")) {
			// statisticsController 참조
		}
		
		
		ModelAndView mav = new ModelAndView("history/log/buildingOwnerPopup");
		mav.addObject("searchType", searchType);
		mav.addObject("searchList", list);
		return mav;
	}
	
	public String changeFormat(String date, int strNumber) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, strNumber);
			
			return date;
		}
		return null;
	}
}