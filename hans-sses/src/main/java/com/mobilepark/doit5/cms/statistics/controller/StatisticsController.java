package com.mobilepark.doit5.cms.statistics.controller;

import java.util.ArrayList;
import java.util.Date;
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

import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.customer.service.MemberService;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.elcg.service.ChargerGroupService;
import com.mobilepark.doit5.history.model.HistCharge;
import com.mobilepark.doit5.statistics.dao.StatChargeDaoMybatis;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

@Controller
@SessionAttributes("period")
public class StatisticsController {
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private StatChargeDaoMybatis statChargeDaoMybatis;
	
	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private BdService bdService;
	
	@Autowired
	private ChargerGroupService chargerGroupService;
	
	
	/**
	 * 기간별 충전통계
	 */
	@RequestMapping("/statistics/stat/period.htm")
	public ModelAndView period(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s]", page, searchType, fromDate, toDate);

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
		if (StringUtils.equals(searchType, "daily")) {
			param.put("fromDate", changeFormat(fromDate, 8));			
			param.put("toDate", changeFormat(toDate, 8));
			count = statChargeDaoMybatis.getChargeDayCount(param);
			list = statChargeDaoMybatis.getChargeDayList(param);
		}
		else if (StringUtils.equals(searchType, "monthly")) {
			param.put("fromDate", changeFormat(fromDate, 6));			
			param.put("toDate", changeFormat(toDate, 6));
			count = statChargeDaoMybatis.getChargeMonthCount(param);
			list = statChargeDaoMybatis.getChargeMonthList(param);
		}
		// 충전시간 초 -> 시 : 분 : 초 로 변환
		setChargeTime(list);

		PaginatedList periodList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("statistics/stat/period");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("periodStat", periodList);

		return mav;
	}
	
	/**
	 * 건물별 충전통계
	 */
	@RequestMapping("/statistics/stat/building.htm")
	public ModelAndView building(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "bdSelect", required = false) Long bdId,
			@RequestParam(value = "searchBdgId", required = false) String searchBdgId) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [bdGroupId:%s] [bdId:%s] [searchBdgId:%s]",
				page, searchType, fromDate, toDate, bdGroupId, bdId, searchBdgId);

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
		
		if(searchBdgId != null) param.put("bdGroupId", searchBdgId);
		if(bdGroupId != null) param.put("bdGroupId", bdGroupId);
		if(bdId != null && bdId != 0) param.put("bdId", bdId);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "daily")) {
			param.put("fromDate", changeFormat(fromDate, 8));			
			param.put("toDate", changeFormat(toDate, 8));
			count = statChargeDaoMybatis.getChargeDayCount(param);
			list = statChargeDaoMybatis.getChargeDayList(param);
		}
		else if (StringUtils.equals(searchType, "monthly")) {
			param.put("fromDate", changeFormat(fromDate, 6));			
			param.put("toDate", changeFormat(toDate, 6));
			count = statChargeDaoMybatis.getChargeMonthCount(param);
			list = statChargeDaoMybatis.getChargeMonthList(param);
		}
		// 충전시간 초 -> 시 : 분 : 초 로 변환
		setChargeTime(list);
		
		PaginatedList buildingList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		

		ModelAndView mav = new ModelAndView("statistics/stat/building");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("buildingStat", buildingList);

		// 화면에서 상세/동명 유지
		if (bdGroupId != null && bdGroupId > 0) {
			BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
			Bd selBd = new Bd();
			selBd.setBdGroup(selGroupList);
			List<Bd> selBdList = this.bdService.search(selBd);
			mav.addObject("selBdList", selBdList);
			mav.addObject("selBdGroupId", bdGroupId);
		}		
		
		if(buildingList.getFullListSize() == 0) return mav;
		mav.addObject("searchBdgId", list.get(0).get("BD_GROUP_ID"));

		return mav;
	}
	
	/**
	 * 그룹별 충전통계
	 */
	@RequestMapping("/statistics/stat/group.htm")
	public ModelAndView group(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "bdSelect", required = false) Long bdId,
			@RequestParam(value = "chargerGroupSelect", required = false) Long chargerGroupId,
			@RequestParam(value = "searchBdgId", required = false) String searchBdgId) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [buildingCode:%s] [buildingDetail:%s] [group:%s] [searchBdgId:%s]",
				page, searchType, fromDate, toDate, bdGroupId, bdId, chargerGroupId, searchBdgId);

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
		
		if(searchBdgId != null) param.put("bdGroupId", searchBdgId);
		if(bdGroupId != null) param.put("bdGroupId", bdGroupId);
		if(bdId != null && bdId != 0) param.put("bdId", bdId);
		if(chargerGroupId != null && chargerGroupId != 0) param.put("chargerGroupId", chargerGroupId);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "daily")) {
			param.put("fromDate", changeFormat(fromDate, 8));			
			param.put("toDate", changeFormat(toDate, 8));
			count = statChargeDaoMybatis.getChargeDayCount(param);
			list = statChargeDaoMybatis.getChargeDayList(param);
		}
		else if (StringUtils.equals(searchType, "monthly")) {
			param.put("fromDate", changeFormat(fromDate, 6));			
			param.put("toDate", changeFormat(toDate, 6));
			count = statChargeDaoMybatis.getChargeMonthCount(param);
			list = statChargeDaoMybatis.getChargeMonthList(param);
		}
		// 충전시간 초 -> 시 : 분 : 초 로 변환
		setChargeTime(list);
		
		PaginatedList groupList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("statistics/stat/group");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("groupStat", groupList);

		// 화면에서 상세/동명 유지
		if (bdGroupId != null && bdGroupId > 0) {
			BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
			Bd selBd = new Bd();
			selBd.setBdGroup(selGroupList);
			List<Bd> selBdList = this.bdService.search(selBd);
			mav.addObject("selBdList", selBdList);
			mav.addObject("selBdGroupId", bdGroupId);
	
			ChargerGroup selCg = new ChargerGroup();
			selCg.setBdGroupId(bdGroupId);
			selCg.setBdId(bdId);
			List<ChargerGroup> selCgList = this.chargerGroupService.search(selCg);
			mav.addObject("selCgList", selCgList);
		}
		
		if(groupList.getFullListSize() == 0) return mav;
		mav.addObject("searchBdgId", list.get(0).get("BD_GROUP_ID"));
		
		return mav;
	}
	
	/**
	 * 충전기별 충전통계
	 */
	@RequestMapping("/statistics/stat/charger.htm")
	public ModelAndView charger(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "chargerNumber", required = false) String mgmtNo) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [chargerNumber:%s]",
				page, searchType, fromDate, toDate, mgmtNo);

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
		param.put("mgmtNo", mgmtNo);
		
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "daily")) {
			param.put("fromDate", changeFormat(fromDate, 8));			
			param.put("toDate", changeFormat(toDate, 8));
			count = statChargeDaoMybatis.getChargerDayCount(param);
			list = statChargeDaoMybatis.getChargerDayList(param);
		}
		else if (StringUtils.equals(searchType, "monthly")) {
			param.put("fromDate", changeFormat(fromDate, 6));			
			param.put("toDate", changeFormat(toDate, 6));
			count = statChargeDaoMybatis.getChargerMonthCount(param);
			list = statChargeDaoMybatis.getChargerMonthList(param);
		}

		// 충전시간 초 -> 시 : 분 : 초 로 변환
		setChargeTime(list);

		PaginatedList chargerList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("statistics/stat/charger");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("chargerStat", chargerList);

		return mav;
	}
	
	/**
	 * 후불과금 통계
	 */
	@RequestMapping("/statistics/stat/payment.htm")
	public ModelAndView payment(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "usid", required = false) String usid) throws Exception {
		
		TraceLog.info("[page:%s] [searchType:%s] [fromDate:%s] [toDate:%s] [usid:%s]",
				page, searchType, fromDate, toDate, usid);


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
		
		Map<String, Object> totalList = null;
		List<Map<String, Object>> list = null;
		if (StringUtils.equals(searchType, "user")) {
			param.put("usid", usid);
			count = statChargeDaoMybatis.getStatPaymentCount(param);
			list = statChargeDaoMybatis.getStatPaymentList(param);
			totalList = statChargeDaoMybatis.getTotalPaymentList(param);
		}
		else if (StringUtils.equals(searchType, "monthly")) {
			param.put("fromDate", changeFormat(fromDate, 6));			
			param.put("toDate", changeFormat(fromDate, 6));
			count = statChargeDaoMybatis.getStatPaymentCount(param);
			list = statChargeDaoMybatis.getStatPaymentList(param);
			totalList = statChargeDaoMybatis.getTotalPaymentList(param);
		}

		PaginatedList paymentList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		
		ModelAndView mav = new ModelAndView("statistics/stat/payment");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("paymentStat", paymentList);
		
		if(totalList != null) mav.addObject("totalPaymentStat", totalList);
		
		return mav;
	}
	
	/**
	 * 후불수납 현황
	 */
	@RequestMapping("/statistics/stat/paymentReceive.htm")
	public ModelAndView paymentReceive(@RequestParam(value = "page", required = false) String page) throws Exception {
		
		TraceLog.info("[page:%s]", page);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList paymentReceiveList = null;
		
		int forCnt = 1;
		List<HistCharge> list = new ArrayList<HistCharge>();
		for(int i=0; i<forCnt; i++) {
			HistCharge histCharge = new HistCharge();
			histCharge.setChargerName("KG이니시스");
			list.add(histCharge);
		}

		paymentReceiveList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("statistics/stat/paymentReceive");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("paymentReceiveStat", paymentReceiveList);

		return mav;
	}
	
	/**
	 * 건물조회 팝업
	 */
	@RequestMapping("/statistics/stat/buildingPopup.htm")
	public ModelAndView buildingPopup(@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		TraceLog.info("[searchValue:%s]", searchValue);
		
		List<HistCharge> buildingList = new ArrayList<HistCharge>();
		
		for(int i=0; i<11; i++) {
			HistCharge histCharge = new HistCharge();
			
			histCharge.setChargerName(String.valueOf(i+1));
			
			buildingList.add(histCharge);
		}
		
		ModelAndView mav = new ModelAndView("statistics/stat/buildingPopup");
		mav.addObject("buildingList", buildingList);
		return mav;
	}
	
	/**
	 * 사용자조회 팝업
	 */
	@RequestMapping("/statistics/stat/userPopup.htm")
	public ModelAndView popup(@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("statistics/stat/userPopup");
		
		List<Member> memberList = null;
		if (StringUtils.isEmpty(searchValue)) return mav;
		if (StringUtils.isNotEmpty(searchValue)) {
			Member member = new Member();
			member.setName(searchValue);
			member.setStatus("301101,301102");
			
			memberList = memberService.search(member);	
		}
		
		mav.addObject("memberList", memberList);
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
	
	private void setChargeTime(List<Map<String, Object>> list) {
		if (list == null) return;
		
		for(Map<String, Object> map : list) {
			if (map.get("CHARGE_TIME") == null) continue;
			
			Long diff = Long.valueOf(map.get("CHARGE_TIME").toString());
			
			String hour = String.valueOf((diff / 3600));
			String minute = String.valueOf(diff % 3600 / 60);
			String second = String.valueOf(diff % 3600 % 60);
			
			if (hour.length() == 1) hour = "0" + hour;
			if (minute.length() == 1) minute = "0" + minute;
			if (second.length() == 1) second = "0" + second;
			
			String diffStr = hour + ":" + minute + ":" + second;
			map.put("DIFF", diffStr);
		}
	}
}
