package com.hans.sses.cms.admin.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hans.sses.admin.service.AdminGroupService;
import com.hans.sses.admin.service.AdminService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : AdminGroupController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 22.      최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("adminGroup")
public class AdminGroupController {
	
	@Autowired
	private AdminGroupService adminGroupService;

	@Autowired
	private AdminService adminService;

	/** 그룹 검색 */
	@RequestMapping("/admin/group/search.htm")
	public ModelAndView search(@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchValue", required = false) String searchValue) {
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rowPerPage", rowPerPage);
		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);
		if (StringUtils.isNotEmpty(searchValue)) param.put("searchValue", searchValue);

		int countAll = this.adminGroupService.count(param);
		List<Map<String, Object>> list = this.adminGroupService.search(param);
		
		ModelAndView mav = new ModelAndView("groupAuth/search");
		mav.addObject("groupList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	/** 그룹 생성 폼 */
	@RequestMapping(value = "/admin/group/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(@RequestParam(value = "page", required = false) String page,
										@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("groupAuth/create");
		
		List<Map<String, Object>> groupAuthList = this.adminGroupService.getAllGroupAuth(null);
		mav.addObject("groupAuthList", groupAuthList);
		mav.addObject("date", new Date());
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		
		return mav;
	}

	/** 그룹 생성 */
	@RequestMapping(value = "/admin/group/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> param) {
		
		int createCount = this.adminGroupService.create(param); 
		if (createCount > 0) {
			if (this.adminGroupService.updateAuth(param)) {
				return new ModelAndView("redirect:/admin/group/detail.htm?id=" + param.get("id"));
			}
		}
		
		return new ModelAndView("redirect:/admin/group/detail.htm?id=" + param.get("id"));
	}

	/** 그룹 삭제 */
	@RequestMapping("/admin/group/delete.json")
	@ResponseBody
	public Boolean multiDelete(@RequestParam("id") Integer groupId) {
		int deleteCount = 0;
		
		Map<String, Object> group = this.adminGroupService.get(groupId); 
		if (group != null) {

			// delete user
			List<Map<String, Object>> adminList = this.adminService.searchByGroup(groupId); 
			for (Map<String, Object> admin : adminList) {
				this.adminService.AdminDelete(admin.get("adminId").toString());
			}
			
			// delete auth
			this.adminGroupService.deleteGroupAuth(groupId);
			
			// delete group
			deleteCount = this.adminGroupService.delete(groupId);
		}
		else {
			TraceLog.info("not exist group [id: %d]", groupId);
		}
		
		return (deleteCount > 0);
	}

	/** 그룹 상세 */
	@RequestMapping("/admin/group/detail.htm")
	public ModelAndView detail(@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchValue", required = false) String searchValue,
									@RequestParam("id") Integer id) throws Exception {
		
		Map<String, Object> adminGroup = this.adminGroupService.get(id);
		ModelAndView mav = new ModelAndView("groupAuth/detail");
		
		if (adminGroup != null) {
			mav.addObject("adminGroup", adminGroup);
			
			List<Map<String, Object>> groupAuthList = this.adminGroupService.getAllGroupAuth(id);
			mav.addObject("groupAuthList", groupAuthList);
		}
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		
		return mav;
	}

	/** 그룹 수정 폼 */
	@RequestMapping(value = "/admin/group/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam(value = "page", required = false) String page,
										@RequestParam(value = "searchValue", required = false) String searchValue,
										@RequestParam("id") Integer id) throws Exception {
		
		ModelAndView mav = new ModelAndView("groupAuth/update");
		
		Map<String, Object> adminGroup = this.adminGroupService.get(id);
		if (adminGroup != null) {
			mav.addObject("adminGroup", adminGroup);
			
			List<Map<String, Object>> groupAuthList = this.adminGroupService.getAllGroupAuth(id);
			mav.addObject("groupAuthList", groupAuthList);
		}
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		mav.addObject("date", new Date());
		
		return mav;
	}

	/** 그룹 수정 */
	@RequestMapping(value = "/admin/group/update.htm", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam Map<String, Object> param) {
		
		int updateCount = this.adminGroupService.update(param);
		if (updateCount > 0) {
			if (this.adminGroupService.updateAuth(param)) {
				String makeParam="?id=" + param.get("id").toString();
				if (StringUtils.isNotEmpty(param.get("page").toString())) makeParam += "&page=" + param.get("page").toString();
				if (StringUtils.isNotEmpty(param.get("searchValue").toString())) makeParam += "&searchValue=" + param.get("searchValue").toString();
				
				return new ModelAndView("redirect:/admin/group/detail.htm" + makeParam);
			}
		}
		
		return new ModelAndView("redirect:/admin/group/search.htm");
	}
	
	void printMap(Map<String, Object> map) {
		TraceLog.info("===== Print Map =====");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			TraceLog.debug("[%s] : [%s]", key, map.get(key));
		}
		TraceLog.info("=====================");
	}
}
