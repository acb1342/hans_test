package com.mobilepark.doit5.cms.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.mobilepark.doit5.admin.service.CodeService;
import com.mobilepark.doit5.cms.SessionAttrName;
import com.mobilepark.doit5.provider.service.ContentProviderService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.HexUtil;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : CodeController.java
 * @Description  : 운영자 계정관리 - 권한 코드 1
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 30.      최초 버전
 * =================================================================================
 */
@Controller
public class CodeController {
	@Autowired
	private CodeService codeService;

	@Autowired
	private AdminGroupService adminGroupService;

	@Autowired
	private ContentProviderService contentProviderService;

	/**
	 * 사용자 생성 폼
	 */
	@RequestMapping(value = "/admin/code/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("code/create");
		mav.addObject("adminGroupList", this.adminGroupService.searchAll());
		mav.addObject("admin", new Admin());

		return mav;
	}

	/**
	 * 사용자 생성
	 */
	@RequestMapping(value = "/admin/code/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> params,Admin admin, HttpSession session,
			SessionStatus sessionStatus) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		params.put("regDate", new Date());
				
		this.codeService.CodeCreate(params);
		
		sessionStatus.setComplete();

		// TODO
		// 그룹이 CP인 경우 TBL_CP에 CP User 추가
		/*AdminGroup adminGroup = this.adminGroupService.get(admin.getAdminGroup().getId());
		if ("CP".equalsIgnoreCase(adminGroup.getName())) {
			ContentProvider contentProvider = new ContentProvider();
			contentProvider.setCpId(admin.getId());
			contentProvider.setCpPasswd(encPass);
			contentProvider.setCpName(admin.getName());
			this.contentProviderService.create(contentProvider);
		}*/

		return new ModelAndView("redirect:/admin/code/detail.htm?id=" + params.get("id"));
	}

	/**
	 * 사용자 삭제
	 */
	@RequestMapping("/admin/code/delete.json")
	@ResponseBody
	public boolean delete(@RequestParam("id") String id) {
		int deleteCount = 0;
		Map<String, Object> memberDetail = this.codeService.getCodeDetail(id);
		if (memberDetail != null) {
			
			deleteCount = this.codeService.CodeDelete(id);
		
		} else {
			TraceLog.info("fail to delete. does not exist id [id:%s]", id);
		}
		return (deleteCount > 0);
	}

	/**
	 * 사용자 상세
	 */
	@RequestMapping("/admin/code/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("code/detail");

		Map<String, Object> memberDetail = this.codeService.getCodeDetail(id);
		
		if (memberDetail != null) {
			mav.addObject("admin", memberDetail);
		} 

		return mav;
	}

	/**
	 * 사용자 검색
	 */
	@RequestMapping("/admin/code/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect,
			@RequestParam(value = "searchValid", required = false) String searchValid) {
		ModelAndView mav = new ModelAndView("code/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		Admin admin = new Admin();
		if (StringUtils.isNotEmpty(searchType) && (StringUtils.isNotEmpty(searchValue))) {
			if (searchType.equals("name")) {
				admin.setName(searchValue);
			} else if (searchType.equals("id")) {
				admin.setId(searchValue);
			}
		}
		
		admin.setValidYn(searchValid);

		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("searchType", searchType);
		param.put("searchValue", searchValue);
		param.put("searchSelect", searchSelect);
		param.put("searchValid", searchValid);
		
		
		//////////////////////////////////////////////////
		int PerPage = 10;                  // 리스트 갯수
		int startRow = (pageNum-1)*PerPage;  // 시작번호
		
		param.put("startRow", startRow);
		param.put("PerPage", PerPage);
		
		int countAll = this.codeService.getCount(param);
		
		System.out.println("COUNTALL = " + countAll);
		System.out.println("PARAM = " + param.toString());
		List<Map<String, String>> list = this.codeService.getCodeList(param);
		
		List<AdminGroup> groupList = this.adminGroupService.searchAll();
	
		int pageCount = countAll/PerPage;
		
		if (countAll % PerPage > 0) {
			pageCount++;
		}
		if(pageCount==0){
			pageCount = 1;
		}
		
		mav.addObject("adminList", list);
		mav.addObject("groupList", groupList);
		mav.addObject("rownum", countAll-((pageNum-1)*PerPage));
		mav.addObject("page", page);
		mav.addObject("startRow", startRow);
		mav.addObject("endRow", startRow+list.size()-1);
		mav.addObject("pageCount", pageCount);
		mav.addObject("totalCount", countAll);
		mav.addObject("PerPage", PerPage);
		return mav;
	}
	
	/**
	 * 사용자 수정 폼
	 */
	@RequestMapping(value = "/admin/code/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam Map<String, Object> params, @RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("code/update");
		
		Map<String, Object> memberDetail = this.codeService.getCodeDetail(id);
		
		// get list of group
		List<AdminGroup> adminGroups = this.adminGroupService.searchAll();

		mav.addObject("admin", memberDetail);
		mav.addObject("adminGroups", adminGroups);
		//mav.addObject("userType", admin.getAdminGroup().getName());

		return mav;
	}

	/**
	 * 사용자 수정
	 */
	@RequestMapping(value = "/admin/code/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam Map<String, Object> params, @RequestParam(value = "password", required = false) String password,
			SessionStatus sessionStatus) {
		
		params.put("updateDate", new Date());
		
		this.codeService.CodeUpdate(params);

		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/admin/code/detail.htm?id=" + params.get("id"));
		return mav;
	}
  
	/**
	 * 사용자 존재여부 확인
	 */
	@RequestMapping(value = "/admin/code/checkid.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUserId(@RequestParam(value = "id", required = false) String id) {
		Admin admin = this.codeService.get(id);

		return (admin == null);
	}
	
}
