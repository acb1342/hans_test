package com.mobilepark.doit5.cms.customer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.customer.service.MemberService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.customer.controller
 * @Filename     : MemberController.java
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
@SessionAttributes("member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 
		상태 (STATUS)
		-. 준회원 301101
		-. 정회원 301102
		-. 중지(=탈퇴) 301103
	 */
	private List<Map<String, String>> getStatusList() {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "301101");
		map.put("value", "준회원");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("key", "301102");
		map.put("value", "정회원");
		list.add(map);
		
		map = new HashMap<String, String>();
		map.put("key", "301103");
		map.put("value", "중지(탈퇴)");
		list.add(map);

		return list;
	}

	/**
	 * 사용자 검색
	 */
	@RequestMapping("/customer/member/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		
		ModelAndView mav = new ModelAndView("customer/member/search");

		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		Member member = new Member();
		member.setNameVrfd(null);
		member.setRcptAgr(null);

		if (StringUtils.isNotEmpty(searchType) && (StringUtils.isNotEmpty(searchValue) || StringUtils.isNotEmpty(searchSelect))) {
			if (searchType.equals("name")) {
				member.setName(searchValue);
			} else if (searchType.equals("id")) {
				//member.setSktId(searchValue);
				member.setSubsId(searchValue);
			}
		}
		
		int totalCount = memberService.searchCount(member); 
		List<Member> list = memberService.search(member, pageNum, rowPerPage, "fstRgDt", "desc");
		PaginatedList members = new PaginatedListImpl(list, pageNum, totalCount, rowPerPage);

		mav.addObject("totalCount", totalCount);
		mav.addObject("page", pageNum);
		mav.addObject("members", members);
		mav.addObject("statusList", getStatusList());
		return mav;
	}

	/**
	 * 사용자 상세
	 */
	@RequestMapping("/customer/member/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		
		ModelAndView mav = new ModelAndView("customer/member/detail");

		// get member
		Member member = memberService.get(Long.parseLong(id));
		List<Map<String, Object>> nfcList = memberService.getHistCustNfcList(id);
		if (member != null) {
			mav.addObject("member", member);
			mav.addObject("nfcList", nfcList);
			mav.addObject("statusList", getStatusList());
		}

		return mav;
	}
	
	/**
	 * 카드현황 상세
	 */
	@RequestMapping("/customer/member/cardDetail.htm")
	public ModelAndView cardDetail(@RequestParam("id") String id) throws Exception {
		
		ModelAndView mav = new ModelAndView("customer/member/cardDetail");

		Member member = memberService.get(Long.parseLong(id));
		if (member != null) {
			mav.addObject("member", member);
		}
		return mav;
	}
	
	/**
	 * DeviceId 삭제
	 */
	@RequestMapping("/customer/member/cardDelete.htm")
	public ModelAndView cardDelete(@RequestParam(value="id") String id,
									@RequestParam(value="addInfo", required=false) String addInfo) throws Exception {
		
		ModelAndView mav = new ModelAndView("customer/member/cardDelete");

		Member member = memberService.get(Long.parseLong(id));
		if (addInfo != null && addInfo.length() > 0) {
			member.setDeviceId("");
			
			memberService.update(member);
			//memberService.insertCustHist(Long.parseLong(id), addInfo);
		}
		
		if (member != null) {
			mav.addObject("member", member);
		}
		return mav;
	}
	

	/**
	 * 사용자 수정 폼
	 */
	@RequestMapping(value = "/customer/member/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") String id) throws Exception {

		ModelAndView mav = new ModelAndView("customer/member/update");

		Member member = memberService.get(Long.parseLong(id));
		List<Map<String, Object>> nfcList = memberService.getHistCustNfcList(id);
		mav.addObject("member", member);
		mav.addObject("nfcList", nfcList);
		mav.addObject("statusList", getStatusList());

		return mav;
	}

	/**
	 * 사용자 수정
	 */
	@RequestMapping(value = "/customer/member/update.htm", method = RequestMethod.POST)
	public ModelAndView update(Member member, SessionStatus sessionStatus) {
		
		memberService.update(member);
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/customer/member/detail.htm?id=" + member.getId());
		return mav;
	}

	@RequestMapping(value = "/customer/member/cardDelete.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean resetPassword(
			@RequestParam(value = "id", required = false) String id,
			@RequestParam(value = "addInfo", required = false) String addInfo) {
		
		TraceLog.debug("[id : %s][addInfo : %s]", id, addInfo);
		
		if (StringUtils.isEmpty(id) || StringUtils.isEmpty(addInfo)) return false;

		Member member = memberService.get(Long.parseLong(id));
		if (addInfo != null && addInfo.length() > 0) {
			member.setDeviceId(null);
			member.setOs(null);
			member.setPushToken(null);
			
			memberService.update(member);
			memberService.insertCustHist(Long.parseLong(id), addInfo);
		}

		return true;
	}
}
