package com.mobilepark.doit5.cms.admin.controller;

import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.AdminGroupAuth;
import com.mobilepark.doit5.admin.model.Menu;
import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.service.AdminGroupService;
import com.mobilepark.doit5.admin.service.MenuService;
import com.mobilepark.doit5.admin.service.AdminService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

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

	@Autowired
	private MenuService menuService;

	/**
	 * 그룹 생성 폼
	 */
	@RequestMapping(value = "/admin/group/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		Map<Map<String, Object>, AdminGroupAuth> groupAuthMap = new LinkedHashMap<Map<String, Object>, AdminGroupAuth>();
		List<Map<String, Object>> cmsMenus = this.menuService.getAllDescendantMenu(1);
		for (Map<String, Object> cmsMenu : cmsMenus) {
			groupAuthMap.put(cmsMenu, null);
		}

		ModelAndView mav = new ModelAndView("admin/group/create");
		mav.addObject("cmsGroup", new AdminGroup());
		mav.addObject("groupAuthMap", groupAuthMap);
		return mav;
	}

	/**
	 * 그룹 생성 
	 */
	@RequestMapping(value = "/admin/group/create.htm", method = RequestMethod.POST)
	public ModelAndView create(AdminGroup adminGroup, HttpServletRequest request, SessionStatus sessionStatus) {
		adminGroup.setFstRgDt(new Date());
		this.adminGroupService.create(adminGroup);
		sessionStatus.setComplete();
		TraceLog.info("create cms group [id:%d, name:%s]", adminGroup.getId(), adminGroup.getName());

		Map<Integer, String> groupAuthMap = new HashMap<Integer, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			String parameterValue = request.getParameter(parameterName);
			if (StringUtils.isNumeric(parameterName)) {
				groupAuthMap.put(Integer.valueOf(parameterName), parameterValue);
			}
		}
		this.adminGroupService.updateAuth(adminGroup.getId(), groupAuthMap);

		return new ModelAndView("redirect:/admin/group/detail.htm?id=" + adminGroup.getId());
	}

	/**
	 * 그룹 삭제 
	 */
	@RequestMapping("/admin/group/delete.json")
	@ResponseBody
	public Boolean multiDelete(@RequestParam("id") String selected) {
		String[] delArray = selected.split(";");
		int deleteCount = 0;
		for (String element : delArray) {
			Integer id = Integer.parseInt(element);
			AdminGroup group = this.adminGroupService.get(id);
			if (group != null) {
				// delete user
				Admin filterUser = new Admin();
				filterUser.setAdminGroup(group);
				List<Admin> users = this.adminService.search(filterUser);
				for (Admin user : users) {
					this.adminService.delete(user.getId());
				}
				// delete auth
				this.adminGroupService.deleteGroupAuth(id);

				deleteCount = this.adminGroupService.delete(id);
				TraceLog.info("delete group [id:%d]", id);
			} else {
				TraceLog.info("not exist cms group [id: %d]", id);
			}
		}

		return (deleteCount > 0);
	}

	/**
	 * 그룹 상세 
	 */
	@RequestMapping("/admin/group/detail.htm")
	public ModelAndView detail(@RequestParam("id") Integer id) throws Exception {
		AdminGroup cmsGroup = this.adminGroupService.get(id);
		ModelAndView mav = new ModelAndView("admin/group/detail");
		if (cmsGroup != null) {
			mav.addObject("cmsGroup", cmsGroup);

			Map<Map<String, Object>, AdminGroupAuth> groupAuthMap = new LinkedHashMap<Map<String, Object>, AdminGroupAuth>();
			Map<String, Object> rootMenut = this.menuService.getRootMenu();
			List<Map<String, Object>> cmsMenus = this.menuService.getAllDescendantMenu(Integer.parseInt(rootMenut.get("id").toString()));
			for (Map<String, Object> cmsMenu : cmsMenus) {
				groupAuthMap.put(cmsMenu, this.adminGroupService.getGroupAuth(id, Integer.parseInt(cmsMenu.get("id").toString())));
			}

			mav.addObject("groupAuthMap", groupAuthMap);

			boolean delete = this.adminService.searchCountByGroup(id) == 0;
			mav.addObject("delete", delete);
		}
		return mav;
	}

	/**
	 * 그룹 검색 
	 */
	@RequestMapping("/admin/group/search.htm")
	public ModelAndView search(@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList cmsGroups = null;

		AdminGroup cmsGroup = new AdminGroup();
		if (StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
			TraceLog.debug("[searchType:%s, searchValue:%s]", searchType, searchValue);
			if (searchType.equals("name")) {
				cmsGroup.setName(searchValue);
			}
		}

		List<AdminGroup> list = this.adminGroupService.search(cmsGroup, pageNum, rowPerPage);
		cmsGroups = new PaginatedListImpl(list, pageNum, this.adminGroupService.searchCount(cmsGroup), rowPerPage);

		ModelAndView mav = new ModelAndView("admin/group/search");
		mav.addObject("cmsGroups", cmsGroups);
		return mav;
	}

	/**
	 * 그룹 수정 폼 
	 */
	@RequestMapping(value = "/admin/group/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Integer id) throws Exception {
		AdminGroup adminGroup = this.adminGroupService.get(id);

		Map<Map<String, Object>, AdminGroupAuth> groupAuthMap = new LinkedHashMap<Map<String, Object>, AdminGroupAuth>();
		Map<String, Object> rootMenut = this.menuService.getRootMenu();
		List<Map<String, Object>> cmsMenus = this.menuService.getAllDescendantMenu(Integer.parseInt(rootMenut.get("id").toString()));
		for (Map<String, Object> cmsMenu : cmsMenus) {
			groupAuthMap.put(cmsMenu, this.adminGroupService.getGroupAuth(id, Integer.parseInt(cmsMenu.get("id").toString())));
		}

		ModelAndView mav = new ModelAndView("admin/group/update");
		mav.addObject("adminGroup", adminGroup);
		mav.addObject("groupAuthMap", groupAuthMap);
		return mav;
	}

	/**
	 * 그룹 수정 
	 */
	@RequestMapping(value = "/admin/group/update.htm", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute AdminGroup adminGroup,
			HttpServletRequest request,
			SessionStatus sessionStatus) {
		Map<Integer, String> groupAuthMap = new HashMap<Integer, String>();
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String parameterName = parameterNames.nextElement();
			String parameterValue = request.getParameter(parameterName);
			if (StringUtils.isNumeric(parameterName)) {
				groupAuthMap.put(Integer.valueOf(parameterName), parameterValue);
			}
		}
		this.adminGroupService.updateAuth(adminGroup.getId(), groupAuthMap);

		adminGroup.setLstChDt(new Date());
		this.adminGroupService.update(adminGroup);
		sessionStatus.setComplete();
		TraceLog.info("update cms group [id:%d, name:%s]", adminGroup.getId(), adminGroup.getName());

		ModelAndView mav = new ModelAndView("redirect:/admin/group/detail.htm?id=" + adminGroup.getId());
		return mav;
	}
}
