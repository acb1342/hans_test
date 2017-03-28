package com.mobilepark.doit5.cms.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.List;

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
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.service.AdminGroupService;
import com.mobilepark.doit5.admin.service.AdminService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.HexUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : AdminController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 30.      최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("admin")
public class AdminController {
	@Autowired
	private AdminService adminService;

	@Autowired
	private AdminGroupService adminGroupService;

	/**
	 * 사용자 생성 폼
	 */
	@RequestMapping(value = "/admin/user/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("admin/user/create");
		mav.addObject("adminGroupList", this.adminGroupService.searchAll());
		mav.addObject("admin", new Admin());

		return mav;
	}

	/**
	 * 사용자 생성
	 */
	@RequestMapping(value = "/admin/user/create.htm", method = RequestMethod.POST)
	public ModelAndView create(Admin admin, HttpSession session,
			SessionStatus sessionStatus) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {

		Admin adminSession =  (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		String password = admin.getPasswd();
		String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));
		admin.setPasswd(encPass);

		admin.setFstRgDt(new Date());
		
		this.adminService.create(admin);

		sessionStatus.setComplete();

		return new ModelAndView("redirect:/admin/user/detail.htm?id=" + admin.getId());
	}

	/**
	 * 사용자 삭제
	 */
	@RequestMapping("/admin/user/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected) {
		String[] userIds = selected.split(";");
		int deleteCount = 0;
		for (String id : userIds) {
			Admin user = this.adminService.get(id);
			if (user != null) {
				deleteCount = this.adminService.delete(id);
			} else {
				TraceLog.info("fail to delete. does not exist id [id:%s]", id);
			}
		}

		return (deleteCount > 0);
	}

	/**
	 * 사용자 상세
	 */
	@RequestMapping("/admin/user/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("admin/user/detail");

		// get members
		Admin admin = new Admin();
		//cmsUser.setDefaultUserId(id);
		//cmsUser.setDefaultUseFlag(Flag.N);

		List<Admin> memberList = this.adminService.search(admin);
		mav.addObject("memberList", memberList);

		// get user
		admin = this.adminService.get(id);
		if (admin != null) {
			mav.addObject("admin", admin);
			mav.addObject("userType", admin.getAdminGroup().getName());
		}

		return mav;
	}

	/**
	 * 사용자 검색
	 */
	@RequestMapping("/admin/user/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		ModelAndView mav = new ModelAndView("admin/user/search");

		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList adminList = null;

		Admin admin = new Admin();
		if (StringUtils.isNotEmpty(searchType) && (StringUtils.isNotEmpty(searchValue) || StringUtils.isNotEmpty(searchSelect))) {
			if (searchType.equals("name")) {
				admin.setName(searchValue);
			} else if (searchType.equals("id")) {
				admin.setId(searchValue);
			} else if (searchType.equals("group")) {
				AdminGroup group = this.adminGroupService.get(Integer.parseInt(searchSelect));
				admin.setAdminGroup(group);
			}
		}
		
		List<Admin> list = this.adminService.search(admin, pageNum, rowPerPage);
		adminList = new PaginatedListImpl(list, pageNum, this.adminService.searchCount(admin), rowPerPage);

		List<AdminGroup> groupList = this.adminGroupService.searchAll();
		mav.addObject("adminList", adminList);
		mav.addObject("groupList", groupList);

		return mav;
	}

	/**
	 * 사용자 수정 폼
	 */
	@RequestMapping(value = "/admin/user/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("admin/user/update");

		// get user
		Admin admin = this.adminService.get(id);

		// get list of group
		List<AdminGroup> adminGroups = this.adminGroupService.searchAll();

		mav.addObject("admin", admin);
		mav.addObject("adminGroups", adminGroups);
		mav.addObject("userType", admin.getAdminGroup().getName());

		return mav;
	}

	/**
	 * 사용자 수정
	 */
	@RequestMapping(value = "/admin/user/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			Admin admin,
			@RequestParam(value = "password", required = false) String password,
			SessionStatus sessionStatus) {
		admin.setLstChDt(new Date());
		this.adminService.update(admin);

		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/admin/user/detail.htm?id=" + admin.getId());
		return mav;
	}

	@RequestMapping(value = "/admin/user/changePassword.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean changePassword(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "password", required = true) String password) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		Admin admin = this.adminService.get(id);
		if (!StringUtils.isBlank(password)) {
			String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));
			admin.setPasswd(encPass);
			admin.setLstChDt(new Date());
			this.adminService.update(admin);

		}

		return true;
	}

	/**
	 * 사용자 존재여부 확인
	 */
	@RequestMapping(value = "/admin/user/checkid.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUserId(@RequestParam(value = "id", required = false) String id) {
		Admin admin = this.adminService.get(id);

		return (admin == null);
	}
}
