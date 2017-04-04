package com.hans.sses.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hans.sses.admin.service.AdminService;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.HexUtil;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : OperatorController.java
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
@SessionAttributes("admin")
public class OperatorController {
	@Autowired
	private AdminService adminService;

	/**
	 * 사용자 생성 폼
	 */
	@RequestMapping(value = "/admin/operator/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("operator/create");
		mav.addObject("adminGroupList", this.adminService.selectAdminGroup());

		return mav;
	}

	/**
	 * 사용자 생성
	 */
	@RequestMapping(value = "/admin/operator/create.json", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> params, HttpSession session,
			SessionStatus sessionStatus) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {

		String password = (String) params.get("passwd");
		String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));

		params.put("passwd",encPass);
		params.put("FstRgDt", new Date());
				
		this.adminService.AdminCreate(params);
		
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/admin/operator/detail.htm?id=" + params.get("id"));
	}

	/**
	 * 사용자 삭제
	 */
	@RequestMapping("/admin/operator/delete.json")
	@ResponseBody
	public boolean delete(@RequestParam("id") String id) {
		int deleteCount = 0;
		Map<String, Object> adminDetail = this.adminService.getAdminDetail(id);
		if (adminDetail != null) {
			
			deleteCount = this.adminService.AdminDelete(id);
		
		} else {
			TraceLog.info("fail to delete. does not exist id [id:%s]", id);
		}
		return (deleteCount > 0);
	}

	/**
	 * 사용자 상세
	 */
	@RequestMapping("/admin/operator/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("operator/detail");

		Map<String, Object> adminDetail = this.adminService.getAdminDetail(id);
		
		if (adminDetail != null) {
			mav.addObject("admin", adminDetail);
		} 

		return mav;
	}

	/**
	 * 사용자 검색
	 */
		
	@RequestMapping("/admin/operator/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		ModelAndView mav = new ModelAndView("operator/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("searchType", searchType);
		param.put("searchValue", searchValue);		
		param.put("searchSelect", searchSelect);	
		//////////////////////////////////////////////////

		param.put("pageNum", pageNum);
		param.put("rowPerPage", rowPerPage);
		
		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);
		
		int countAll = this.adminService.getCount(param);
		List<Map<String, String>> list = this.adminService.getAdminList(param);
		List<Map<String, Object>> groupList = this.adminService.selectAdminGroup();
		
		mav.addObject("adminList", list);
		mav.addObject("groupList", groupList);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	/**
	 * 사용자 수정 폼
	 */
	@RequestMapping(value = "/admin/operator/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam Map<String, Object> params, @RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("operator/update");
		
		Map<String, Object> adminDetail = this.adminService.getAdminDetail(id);
		
		// get list of group
		List<Map<String, Object>> adminGroups = this.adminService.selectAdminGroup();

		mav.addObject("admin", adminDetail);
		mav.addObject("adminGroups", adminGroups);

		return mav;
	}

	/**
	 * 사용자 수정
	 */
	@RequestMapping(value = "/admin/operator/update.json", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam Map<String, Object> params, SessionStatus sessionStatus) {
		
		params.put("LstChDt", new Date());
		
		this.adminService.AdminUpdate(params);

		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/admin/operator/detail.htm?id=" + params.get("id"));
		
		
		return mav;
	}

	@RequestMapping(value = "/admin/operator/changePassword.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean changePassword(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "password", required = true) String password) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
	
		if (!StringUtils.isBlank(password)) {
			String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("id", id);
			param.put("passwd", encPass);
			param.put("modDate", new Date());
			
			this.adminService.AdminPasswdUpdate(param);

		}

		return true;
	}

	@RequestMapping(value = "/admin/operator/resetPassword.json")
	@ResponseBody
	public Boolean resetPassword(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "tid", required = true) String tid) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		if (!StringUtils.isBlank(password)) {
			String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));
			
			Map<String, Object> param = new HashMap<String, Object>();
			
			param.put("id", id);
			param.put("passwd", encPass);
			param.put("modDate", new Date());
			
			this.adminService.AdminPasswdUpdate(param);

		}

		return true;
	}

	/**
	 * 사용자 존재여부 확인
	 */
	@RequestMapping(value = "/admin/operator/checkid.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUserId(@RequestParam(value = "id", required = false) String id) {
		
		Map<String, Object> adminDetail = this.adminService.getAdminDetail(id);

		return (adminDetail == null);
	}
	
}
