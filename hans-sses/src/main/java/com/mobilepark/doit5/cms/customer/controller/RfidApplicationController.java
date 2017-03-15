package com.mobilepark.doit5.cms.customer.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.common.util.TimeUtilz;
import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.customer.model.RfidApplication;
import com.mobilepark.doit5.customer.service.RfidApplicationService;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.customer.controller
 * @Filename     : RfidApplicationController.java
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
@SessionAttributes("rfidApplication")
public class RfidApplicationController {
	@Autowired
	private RfidApplicationService rfidApplicationService;
	
	/**
	 * 
		상태 (STATUS)
		-. 미사용 308101
		-. 발급요청 308102
		-. 발급취소 308103
		-. 배송요청 308104
		-. 배송중 308105
		-. 배송완료 308106
		-. 사용중 308107
		-. 사용중지 308108
	 */
	private List<Map<String, String>> getStatusList() {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "308101");
		map.put("value", "미사용");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("key", "308102");
		map.put("value", "발급요청");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("key", "308103");
		map.put("value", "발급취소");
		list.add(map);

		map = new HashMap<String, String>();
		map.put("key", "308104");
		map.put("value", "배송요청");
		list.add(map);

		map = new HashMap<String, String>();
		map.put("key", "308105");
		map.put("value", "배송중");
		list.add(map);

		map = new HashMap<String, String>();
		map.put("key", "308106");
		map.put("value", "배송완료");
		list.add(map);

		map = new HashMap<String, String>();
		map.put("key", "308107");
		map.put("value", "사용중");
		list.add(map);

		map = new HashMap<String, String>();
		map.put("key", "308108");
		map.put("value", "사용중지");
		list.add(map);

		return list;
	}

	/**
	 * 회원카드 (RFID) 검색
	 */
	@RequestMapping("/customer/rfidApplication/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		
		ModelAndView mav = new ModelAndView("customer/rfidApplication/search");

		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		int count = 0;
		RfidApplication model = new RfidApplication();

		List<RfidApplication> list = null;
		
		if (StringUtils.isNotEmpty(searchType) &&
				(StringUtils.isNotEmpty(searchValue) || StringUtils.isNotEmpty(searchSelect))) {
			if (searchType.equals("name")) {
				Member member = new Member();
				member.setName(searchValue);
				model.setMember(member);
			} else if (searchType.equals("status")) {
				model.setStatus(searchSelect);
			}
		}
		count = rfidApplicationService.searchCount(model);
		list = rfidApplicationService.search(model, pageNum, rowPerPage, "fstRgDt", "desc");
		
		for (RfidApplication rfidApplication : list) {
			Long usid = rfidApplication.getMember().getId();
			String cardNo = rfidApplication.getCardNo();
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("usid", usid);
			param.put("cardNo", cardNo);
			
			Map<String, Object> map = rfidApplicationService.getRfidCard(param);
			
			if (map == null) continue;
			if (map.get("ST_DT") == null) continue;
			
			rfidApplication.setStDt((Date)map.get("ST_DT"));
		}
		
		PaginatedList rfidApplicationList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		mav.addObject("rfidApplicationList", rfidApplicationList);
		mav.addObject("statusList", getStatusList());

		return mav;
	}
	
	/**
	 * 회원카드 상세
	 */
	@RequestMapping("/customer/rfidApplication/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		
		ModelAndView mav = new ModelAndView("customer/rfidApplication/detail");

		// get member
		RfidApplication rfidApplication = rfidApplicationService.get(Long.parseLong(id));
		if (rfidApplication != null) {
			Long usid = rfidApplication.getMember().getId();
			String cardNo = rfidApplication.getCardNo();
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("usid", usid);
			param.put("cardNo", cardNo);
			
			Map<String, Object> map = rfidApplicationService.getRfidCard(param);
			
			if (map != null && map.get("ST_DT") != null) {
				rfidApplication.setStDt((Date)map.get("ST_DT"));
			}

			mav.addObject("rfidApplication", rfidApplication);
			mav.addObject("statusList", getStatusList());
		}

		return mav;
	}

	/**
	 * 사용자 수정 폼
	 */
	@RequestMapping(value = "/customer/rfidApplication/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") String id) throws Exception {

		ModelAndView mav = new ModelAndView("customer/rfidApplication/update");

		RfidApplication rfidApplication = rfidApplicationService.get(Long.parseLong(id));
		
		if (rfidApplication != null) {
			Long usid = rfidApplication.getMember().getId();
			String cardNo = rfidApplication.getCardNo();
			
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("usid", usid);
			param.put("cardNo", cardNo);
			
			Map<String, Object> map = rfidApplicationService.getRfidCard(param);
			
			if (map != null && map.get("ST_DT") != null) {
				rfidApplication.setStDt((Date)map.get("ST_DT"));
			}

			mav.addObject("rfidApplication", rfidApplication);
			mav.addObject("statusList", getStatusList());
		}

		return mav;
	}

	/**
	 * 사용자 수정
	 */
	@RequestMapping(value = "/customer/rfidApplication/update.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> update(HttpSession session,
						RfidApplication rfidApplication,
						SessionStatus sessionStatus) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		rfidApplication.setLstChUsid(user.getId());
		rfidApplication.setLstChDt(new Date());

		rfidApplicationService.update(rfidApplication);

		Map<String, Object> resList = new HashMap<String, Object>();
		resList.put("status", rfidApplication.getStatus());
		return resList;
	}

	/**
	 * 배송 요청 (delivery)
	 */
	@RequestMapping(value = "/customer/rfidApplication/delivery.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delivery(HttpSession session,
										RfidApplication rfidApplication,
										SessionStatus sessionStatus) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		rfidApplication.setStatus("308104");
		rfidApplication.setOdUsid(user.getId());
		rfidApplication.setOdDt(new Date());
		rfidApplication.setLstChUsid(user.getId());
		rfidApplication.setLstChDt(new Date());
		
		rfidApplicationService.update(rfidApplication);
		
		Map<String, Object> resList = new HashMap<String, Object>();
		resList.put("status", rfidApplication.getStatus());
		resList.put("odDt", TimeUtilz.get8StrFormatFromTick(rfidApplication.getOdDt().getTime()));
		return resList;
	}

	@RequestMapping("/customer/rfidApplication/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String id) {
		
		int count = rfidApplicationService.delete(Long.parseLong(id));
		return (count > 0);
	}
}
