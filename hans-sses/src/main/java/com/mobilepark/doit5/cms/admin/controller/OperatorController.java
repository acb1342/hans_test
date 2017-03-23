package com.mobilepark.doit5.cms.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.service.AdminGroupService;
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.provider.model.ContentProvider;
import com.mobilepark.doit5.provider.service.ContentProviderService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.HexUtil;
import com.uangel.platform.web.PaginatedListImpl;

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

	@Autowired
	private AdminGroupService adminGroupService;

	@Autowired
	private ContentProviderService contentProviderService;

	/**
	 * 사용자 생성 폼
	 */
	@RequestMapping(value = "/admin/operator/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("operator/create");
		mav.addObject("adminGroupList", this.adminService.selectGroup());
		mav.addObject("admin", new Admin());

		return mav;
	}

	/**
	 * 사용자 생성
	 */
	@RequestMapping(value = "/admin/operator/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> params,Admin admin, HttpSession session,
			SessionStatus sessionStatus) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {

		
		System.out.println("create_admin = "+ params.toString());
		
		Admin adminSession =  (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		String password = (String) params.get("passwd");
		System.out.println("create_pass = "+ password);
		String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));

		params.put("passwd",encPass);
		params.put("ValidYn", "Y");
		params.put("PwErrCnt", 0);
		params.put("FstRgUsid", adminSession.getId());
		params.put("FstRgDt", new Date());
				
		this.adminService.MemberCreate(params);
		
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

		return new ModelAndView("redirect:/admin/operator/detail.htm?id=" + params.get("id"));
	}

	/**
	 * 사용자 삭제
	 */
	@RequestMapping("/admin/operator/delete.json")
	@ResponseBody
	public boolean delete(@RequestParam("id") String id) {
		int deleteCount = 0;
		Map<String, Object> memberDetail = this.adminService.getMemberDetail(id);
		if (memberDetail != null) {
			
			deleteCount = this.adminService.MemberDelete(id);
		
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

		Map<String, Object> memberDetail = this.adminService.getMemberDetail(id);
		
		if (memberDetail != null) {
			mav.addObject("admin", memberDetail);
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
		ModelAndView mav = new ModelAndView("operator/search1");
		
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
		
		System.out.println("Param = " + param.toString());
		
		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);
		
		int countAll = this.adminService.getCount(param);
		List<Map<String, String>> list = this.adminService.getAdminList(param);
		List<Map<String, Object>> groupList = this.adminService.selectGroup();
		
		mav.addObject("adminList", list);
		mav.addObject("groupList", groupList);
		mav.addObject("countAll", countAll);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	/*@RequestMapping("/admin/operator/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect,
			@RequestParam(value = "searchValid", required = false) String searchValid) {
		ModelAndView mav = new ModelAndView("operator/search1");
		
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

		// 운영자 검색 : 권한 코드 1
		AdminGroup group = this.adminGroupService.get(1);
		admin.setAdminGroup(group);
		
		
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
		
		int countAll = this.adminService.getCount(param);
		List<Map<String, String>> list = this.adminService.getAdminList(param);
		
		List<Map<String, Object>> groupList = this.adminService.selectGroup();
	
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
	}*/
	
	/**
	 * 사용자 수정 폼
	 */
	@RequestMapping(value = "/admin/operator/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam Map<String, Object> params, @RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("operator/update");
		
		Map<String, Object> memberDetail = this.adminService.getMemberDetail(id);
		
		// get list of group
		List<Map<String, Object>> adminGroups = this.adminService.selectGroup();

		mav.addObject("admin", memberDetail);
		mav.addObject("adminGroups", adminGroups);
		//mav.addObject("userType", admin.getAdminGroup().getName());

		return mav;
	}

	/**
	 * 사용자 수정
	 */
	@RequestMapping(value = "/admin/operator/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam Map<String, Object> params, @RequestParam(value = "password", required = false) String password,
			SessionStatus sessionStatus) {
		
		params.put("LstChDt", new Date());
		
		this.adminService.MemberUpdate(params);

		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/admin/operator/detail.htm?id=" + params.get("id"));
		return mav;
	}
   
	@RequestMapping(value = "/admin/operator/changePassword.json", method = RequestMethod.POST)
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

			// TODO
			// 그룹이 CP인 경우 TBL_CP에 CP User 수정
			AdminGroup adminGroup = this.adminGroupService.get(admin.getAdminGroup().getId());
			if ("CP".equalsIgnoreCase(adminGroup.getName())) {
				ContentProvider contentProvider = this.contentProviderService.getById(admin.getId());
				contentProvider.setCpPasswd(encPass);
				this.contentProviderService.update(contentProvider);
			}
		}

		return true;
	}

	@RequestMapping(value = "/admin/operator/resetPassword.json")
	@ResponseBody
	public Boolean resetPassword(
			@RequestParam(value = "id", required = true) String id,
			@RequestParam(value = "password", required = true) String password,
			@RequestParam(value = "tid", required = true) String tid) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		TraceLog.debug("[tid:%s]", tid);
		
		Admin admin = this.adminService.get(id);
		if (!StringUtils.isBlank(password)) {
			String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));
			admin.setPasswd(encPass);
			admin.setLstChDt(new Date());
			this.adminService.update(admin);

			// TODO
			// 그룹이 CP인 경우 TBL_CP에 CP User 수정
			AdminGroup adminGroup = this.adminGroupService.get(admin.getAdminGroup().getId());
			if ("CP".equalsIgnoreCase(adminGroup.getName())) {
				ContentProvider contentProvider = this.contentProviderService.getById(admin.getId());
				contentProvider.setCpPasswd(encPass);
				this.contentProviderService.update(contentProvider);
			}
		}

		return true;
	}

	/**
	 * 사용자 존재여부 확인
	 */
	@RequestMapping(value = "/admin/operator/checkid.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUserId(@RequestParam(value = "id", required = false) String id) {
		
		Map<String, Object> memberDetail = this.adminService.getMemberDetail(id);

		return (memberDetail == null);
	}
	
}
