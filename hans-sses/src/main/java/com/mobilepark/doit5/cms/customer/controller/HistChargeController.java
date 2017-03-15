package com.mobilepark.doit5.cms.customer.controller;

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
import com.mobilepark.doit5.statistics.dao.LogHistoryDaoMybatis;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.history.controller
 * @Filename     : HistChargeController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 28.      최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("histCharge")
public class HistChargeController {
	@Autowired
	private MemberService memberService;
	@Autowired
	private LogHistoryDaoMybatis logHistoryDaoMybatis;
	
	/**
	 * 충전 이력 조회 및 검색
	 */
	@RequestMapping("/customer/histCharge/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "usid", required = false) String usid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws Exception {
		
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
				StringUtils.isNotEmpty(usid)) {
			
			if (StringUtils.equals(searchType, "daily")) {
				param.put("fromDate", this.changeFormat(fromDate, 8));
				param.put("toDate", this.changeFormat(toDate, 8));
				param.put("usid", usid);

				count = logHistoryDaoMybatis.getChargeDayCount(param);
				list = logHistoryDaoMybatis.getChargeDayList(param);
			}
			else if (StringUtils.equals(searchType, "monthly")) {
				param.put("fromDate", this.changeFormat(fromDate, 6));
				param.put("toDate", this.changeFormat(toDate, 6));
				param.put("usid", usid);

				count = logHistoryDaoMybatis.getChargeMonthCount(param);
				list = logHistoryDaoMybatis.getChargeMonthList(param);
			}
			
			// 충전시간 초 -> 시 : 분 : 초 로 변환
			for(Map<String, Object> map : list) {
				Long diff = 0L;
				if (map.get("START_DT") != null && map.get("END_DT") != null) {
					Date endDt = (Date)map.get("END_DT");
					Date startDt = (Date)map.get("START_DT");
					diff = (endDt.getTime() - startDt.getTime())/1000L;
				}
				if (map.get("CHARGE_TIME") != null) {
					diff = Long.valueOf(map.get("CHARGE_TIME").toString());
					TraceLog.debug("diff = " + diff);
				}
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
		PaginatedList histChargeList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
				
		ModelAndView mav = new ModelAndView("customer/histCharge/search");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("histChargeList", histChargeList);

		return mav;
	}

	/**
	 * 인증 이력 조회 및 검색
	 */
	@RequestMapping("/customer/histCertify/search.htm")
	public ModelAndView histCertify(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "usid", required = false) String usid,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) throws Exception {
		
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
				StringUtils.isNotEmpty(usid)) {
			
			if (StringUtils.equals(searchType, "daily")) {
				param.put("fromDate", this.changeFormat(fromDate, 8));
				param.put("toDate", this.changeFormat(toDate, 8));
				param.put("usid", usid);

				count = logHistoryDaoMybatis.getCertCustDayCount(param);
				list = logHistoryDaoMybatis.getCertCustDayList(param);
			}
			else if (StringUtils.equals(searchType, "monthly")) {
				param.put("fromDate", this.changeFormat(fromDate, 6));
				param.put("toDate", this.changeFormat(toDate, 6));
				param.put("usid", usid);

				count = logHistoryDaoMybatis.getCertCustMonthCount(param);
				list = logHistoryDaoMybatis.getCertCustMonthList(param);
			}
		}
		PaginatedList histChargeList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
				
		ModelAndView mav = new ModelAndView("customer/histCertify/search");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("histChargeList", histChargeList);

		return mav;
	}

	@RequestMapping("/customer/histCharge/popup.htm")
	public ModelAndView popup(@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("customer/histCharge/popup");
		
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
}
