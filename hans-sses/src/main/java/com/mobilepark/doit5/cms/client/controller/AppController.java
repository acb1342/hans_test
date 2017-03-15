package com.mobilepark.doit5.cms.client.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.client.model.Application;
import com.mobilepark.doit5.client.model.ApplicationList;
import com.mobilepark.doit5.client.service.ApplicationListService;
import com.mobilepark.doit5.client.service.ApplicationService;
import com.mobilepark.doit5.provider.model.ContentProvider;
import com.mobilepark.doit5.provider.service.ContentProviderService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.provider.controller
 * @Filename     : ApplicationController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE               Description
 * =================================================================================
 *  1.0	   2014. 2. 25.       최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("application")
public class AppController {
	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ApplicationListService applicationListService;

	@Autowired
	private ContentProviderService contentProviderService;

	/**
	 * App 검색
	 */
	@RequestMapping("/client/app/search.htm")
	public ModelAndView searchCompany(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ContentProvider cp = null;
		List<Application> list = new ArrayList<Application>();
		Application condition = new Application();

		if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
			cp = this.contentProviderService.getById(user.getId());
			if (cp != null) {
				condition.setCpId(cp.getCpId());
				if (!EtcUtil.isBlank(searchValue)) {
					if ("appName".equals(searchType)) {
						condition.setAppName(searchValue);
					}
				}
			}

		} else {
			if (!EtcUtil.isBlank(searchValue)) {
				if ("cpId".equals(searchType)) {
					condition.setCpId(searchValue);
				} else if ("appName".equals(searchType)) {
					condition.setAppName(searchValue);
				}
			}
		}

		// 검색
		list = this.applicationService.search(condition, page, rowPerPage);
		PaginatedListImpl applications = new PaginatedListImpl(list, page, this.applicationService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/client/app/search");
		mav.addObject("applications", applications);

		return mav;
	}

	/**
	 * App 생성 폼
	 */
	@RequestMapping(value = "/client/app/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("/client/app/create");
		mav.addObject("contentProviderList", this.contentProviderService.searchAll());
		mav.addObject("application", new Application());

		return mav;
	}

	/**
	 * App 생성
	 */
	@RequestMapping(value = "/client/app/create.htm", method = RequestMethod.POST)
	public ModelAndView create(Application application,
			@RequestParam(value = "contentProviderId", required = true) int contentProviderId,
			@RequestParam("appName") String appName,
			@RequestParam("pkgId") String pkgId,
			@RequestParam("agentId") String agentId,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		application.setContentProvider(this.contentProviderService.get(contentProviderId));
		application.setCpId(this.contentProviderService.get(contentProviderId).getCpId());
		application.setAppName(appName);
		application.setPkgId(pkgId);
		application.setAgentId(agentId);
		this.applicationService.create(application);

		sessionStatus.setComplete();

		TraceLog.info("create application [id:%d] by user [id:%s]", application.getId(), user.getId());

		return new ModelAndView("redirect:/client/app/detail.htm?id=" + application.getId());
	}

	/**
	 * App 상세
	 */
	@RequestMapping("/client/app/detail.htm")
	public ModelAndView detail(@RequestParam("id") Integer id) throws Exception {
		ModelAndView mav = new ModelAndView("/client/app/detail");

		Application application = this.applicationService.get(id);
		if (!EtcUtil.isNone(application)) {
			mav.addObject("application", application);
		}

		return mav;
	}

	/**
	 * App 수정 폼
	 */
	@RequestMapping(value = "/client/app/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Integer id) {
		Application application = this.applicationService.get(id);

		ModelAndView mav = new ModelAndView("/client/app/update");
		mav.addObject("contentProviderList", this.contentProviderService.searchAll());
		mav.addObject("application", application);

		return mav;
	}

	/**
	 * App 수정
	 */
	@RequestMapping(value = "/client/app/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam("id") Integer id,
			@RequestParam("appName") String appName,
			@RequestParam("pkgId") String pkgId,
			@RequestParam("agentId") String agentId,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		Application application = this.applicationService.get(id);
		if (application != null) {
			application.setAppName(appName);
			application.setPkgId(pkgId);
			application.setAgentId(agentId);
			this.applicationService.update(application);
		}
		sessionStatus.setComplete();

		TraceLog.info("update application [id:%d] by user [id:%s]", application.getId(), user.getId());

		return new ModelAndView(new RedirectView("/client/app/detail.htm?id=" + application.getId()));
	}

	/**
	 * App 삭제
	 */
	@RequestMapping("/client/app/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		int deleteCount = 0;
		String[] ids = selected.split(";");

		for (String id : ids) {
			Integer tmp = new Integer(id);
			Application application = this.applicationService.get(tmp);
			if (!EtcUtil.isNone(application)) {
				deleteCount += this.applicationService.delete(application);
				TraceLog.info("delete application [id:%d] by user [id:%s]", tmp, user.getId());
			} else {
				TraceLog.info("no item to delete application [id:%d]", tmp);
			}
		}

		return (deleteCount > 0);
	}

	@RequestMapping(value = "/client/app/checkUsed.json", method = RequestMethod.POST)
	@ResponseBody
	protected Map<Object, Object> checkUsed(
			@RequestParam(value = "appid", required = false) Integer id,
			HttpSession session,
			HttpServletRequest request) throws Exception {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> errors = new HashMap<Object, Object>();

		Application application = this.applicationService.get(id);
		if (application != null) {
			TraceLog.debug("appId:%s", application.getPkgId());
			List<ApplicationList> applists = this.applicationListService.getListByAppId(application.getPkgId());
			if (applists.size() > 0) {
				errors.put("reason", "isUsed");
				result.put("errors", errors);
				result.put("success", false);
				return result;
			}
		} else {
			errors.put("reason", "notExist");
			result.put("errors", errors);
			result.put("success", false);
			return result;
		}

		result.put("success", true);

		return result;
	}
}
