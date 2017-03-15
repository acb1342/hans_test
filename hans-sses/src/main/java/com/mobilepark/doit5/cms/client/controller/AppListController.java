package com.mobilepark.doit5.cms.client.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.client.model.Application;
import com.mobilepark.doit5.client.model.ApplicationList;
import com.mobilepark.doit5.client.service.ApplicationListService;
import com.mobilepark.doit5.client.service.ApplicationService;
import com.mobilepark.doit5.provider.model.ContentProvider;
import com.mobilepark.doit5.provider.service.ContentProviderService;
import com.mobilepark.doit5.route.service.RouteRuleService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.util.FileUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.provider.controller
 * @Filename     : ApplicationListController.java
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
@SessionAttributes("applicationList")
public class AppListController {
	@Autowired
	private ApplicationListService applicationListService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private RouteRuleService routeRuleService;

	@Autowired
	private ContentProviderService contentProviderService;

	/**
	 * ApplicationList 검색
	 */
	@RequestMapping("/client/appList/search.htm")
	public ModelAndView searchCompany(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ContentProvider cp = null;
		List<ApplicationList> list = new ArrayList<ApplicationList>();
		ApplicationList condition = new ApplicationList();
		Application application = null;
		String cpId = "";

		if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
			cp = this.contentProviderService.getById(user.getId());
			if (cp != null) {
				cpId = cp.getCpId();
			}
		}

		if (!EtcUtil.isBlank(searchValue)) {
			if ("os".equals(searchType)) {
				condition.setOs(searchValue);
			} else if ("appId".equals(searchType)) {
				condition.setAppId(searchValue);
			} else if ("appVer".equals(searchType)) {
				condition.setAppVer(searchValue);
			}
		}

		// 검색
		list = this.applicationListService.searchListByCondition(condition, cpId, page, rowPerPage);
		PaginatedListImpl applicationLists = new PaginatedListImpl(list, page, this.applicationListService.searchCountByCondition(condition, cpId), rowPerPage);

		ModelAndView mav = new ModelAndView("/client/appList/search");

		HashMap<String, String> appNameMap = new HashMap<String, String>();
		if (list != null) {
			for (ApplicationList applist : list) {
				application = this.applicationService.getByAppId(applist.getAppId());
				if (application != null) {
					appNameMap.put(applist.getAppId(), application.getAppName());
				}
			}

			mav.addObject("appNameMap", appNameMap);
		}

		mav.addObject("applicationLists", applicationLists);

		return mav;
	}

	/**
	 * ApplicationList 생성 폼
	 */
	@RequestMapping(value = "/client/appList/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("/client/appList/create");
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ContentProvider cp = null;
		List<Application> list = new ArrayList<Application>();

		if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
			cp = this.contentProviderService.getById(user.getId());
			if (cp != null) {
				Application filter = new Application();
				filter.setCpId(cp.getCpId());
				list = this.applicationService.search(filter);
			}

		} else {
			list = this.applicationService.searchAll();
		}
		mav.addObject("applications", list);
		mav.addObject("applicationList", new ApplicationList());
		mav.addObject("routeRuleList", this.routeRuleService.searchAll());

		return mav;
	}

	/**
	 * ApplicationList 생성
	 */
	@RequestMapping(value = "/client/appList/create.htm", method = RequestMethod.POST)
	public ModelAndView create(ApplicationList applicationList,
			@RequestParam(value = "appId", required = true) Integer appId,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "appKey", required = false) String appKey,
			@RequestParam(value = "appVer", required = false) String appVer,
			@RequestParam(value = "certPath", required = false) String certPath,
			@RequestParam(value = "certFile", required = false) MultipartFile certFile,
			@RequestParam(value = "keyPath", required = false) String keyPath,
			@RequestParam(value = "keyFile", required = false) MultipartFile keyFile,
			@RequestParam(value = "routeSeq", required = false) String routeSeq,
			@RequestParam(value = "senderIndex", required = false, defaultValue = "0") Integer senderIndex,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ContentProvider cp = null;

		if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
			cp = this.contentProviderService.getById(user.getId());
			if (cp != null) {
				applicationList.setCpId(cp.getCpId());
			}
		}
		applicationList.setApplication(this.applicationService.get(appId));
		applicationList.setAppId(this.applicationService.get(appId).getPkgId());
		applicationList.setOs(os);
		applicationList.setAppKey(appKey);
		applicationList.setAppVer(appVer);

		if (os.equalsIgnoreCase("ios")) {
			try {
				applicationList.setCertPath(this.applicationListService.saveFileInTheDirectory(Env.get("certUploadDir"), applicationList.getAppId() + ".cert.pem", certFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				applicationList.setKeyPath(this.applicationListService.saveFileInTheDirectory(Env.get("keyUploadDir"), applicationList.getAppId() + ".key.pem", keyFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		applicationList.setRouteSeq(routeSeq);
		applicationList.setSenderIndex(senderIndex);
		this.applicationListService.create(applicationList);

		sessionStatus.setComplete();

		TraceLog.info("create applicationList [id:%d] by user [id:%s]", applicationList.getId(), user.getId());

		return new ModelAndView("redirect:/client/appList/detail.htm?id=" + applicationList.getId());
	}

	/**
	 * ApplicationList 상세
	 */
	@RequestMapping("/client/appList/detail.htm")
	public ModelAndView detail(@RequestParam("id") Integer id) throws Exception {
		ModelAndView mav = new ModelAndView("/client/appList/detail");

		ApplicationList applicationList = this.applicationListService.get(id);
		if (!EtcUtil.isNone(applicationList)) {
			mav.addObject("applicationList", applicationList);

			HashMap<String, String> appNameMap = new HashMap<String, String>();
			Application application = this.applicationService.getByAppId(applicationList.getAppId());
			if (application != null) {
				appNameMap.put(applicationList.getAppId(), application.getAppName());
			}

			mav.addObject("routeRule", this.routeRuleService.get(applicationList.getRouteSeq()));
			mav.addObject("appNameMap", appNameMap);
		}

		return mav;
	}

	/**
	 * ApplicationList 수정 폼
	 */
	@RequestMapping(value = "/client/appList/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Integer id,
			HttpSession session) {
		ApplicationList applicationList = this.applicationListService.get(id);

		ModelAndView mav = new ModelAndView("/client/appList/update");
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ContentProvider cp = null;
		List<Application> list = new ArrayList<Application>();

		if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
			cp = this.contentProviderService.getById(user.getId());
			if (cp != null) {
				Application filter = new Application();
				filter.setCpId(cp.getCpId());
				list = this.applicationService.search(filter);
			}

		} else {
			list = this.applicationService.searchAll();
		}

		mav.addObject("applications", list);
		mav.addObject("applicationList", applicationList);
		mav.addObject("routeRuleList", this.routeRuleService.searchAll());
		Application application = this.applicationService.getByAppId(applicationList.getAppId());
		if (application != null) {
			mav.addObject("appName", application.getAppName());
		}

		return mav;
	}

	/**
	 * ApplicationList 수정
	 */
	@RequestMapping(value = "/client/appList/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam(value = "id", required = false) Integer id,
			@RequestParam(value = "appId", required = false) Integer appId,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "appKey", required = false) String appKey,
			@RequestParam(value = "appVer", required = false) String appVer,
			@RequestParam(value = "certPath", required = false) String certPath,
			@RequestParam(value = "certFile", required = false) MultipartFile certFile,
			@RequestParam(value = "keyPath", required = false) String keyPath,
			@RequestParam(value = "keyFile", required = false) MultipartFile keyFile,
			@RequestParam(value = "routeSeq", required = false) String routeSeq,
			@RequestParam(value = "senderIndex", required = false, defaultValue = "0") Integer senderIndex,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		ApplicationList applicationList = this.applicationListService.get(id);
		if (applicationList != null) {
			ContentProvider cp = null;

			if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
				cp = this.contentProviderService.getById(user.getId());
				if (cp != null) {
					applicationList.setCpId(cp.getCpId());
				}
			}
			applicationList.setApplication(this.applicationService.get(appId));
			applicationList.setAppId(this.applicationService.get(appId).getPkgId());

			if (!os.equalsIgnoreCase("ios")) {
				if (applicationList.getOs().equals("ios")) {
					FileUtil.delete(new File(applicationList.getCertPath()));
					applicationList.setCertPath("");
					FileUtil.delete(new File(applicationList.getKeyPath()));
					applicationList.setKeyPath("");
				}
			} else {
				if (!certFile.isEmpty()) {
					if (!EtcUtil.isNone(applicationList.getCertPath())) {
						FileUtil.delete(new File(applicationList.getCertPath()));
					}
					try {
						applicationList.setCertPath(this.applicationListService.saveFileInTheDirectory(Env.get("certUploadDir"), applicationList.getAppId() + ".cert.pem", certFile));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				if (!keyFile.isEmpty()) {
					if (!EtcUtil.isNone(applicationList.getKeyPath())) {
						FileUtil.delete(new File(applicationList.getKeyPath()));
					}
					try {
						applicationList.setKeyPath(this.applicationListService.saveFileInTheDirectory(Env.get("keyUploadDir"), applicationList.getAppId() + ".key.pem", keyFile));
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			applicationList.setOs(os);
			applicationList.setAppKey(appKey);
			applicationList.setAppVer(appVer);

			applicationList.setRouteSeq(routeSeq);
			applicationList.setSenderIndex(senderIndex);
			this.applicationListService.update(applicationList);

			if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
				TraceLog.debug("cpId:%s", applicationList.getCpId());
			}
		}
		sessionStatus.setComplete();

		TraceLog.info("update applicationList [id:%d] by user [id:%s]", applicationList.getId(), user.getId());

		return new ModelAndView(new RedirectView("/client/appList/detail.htm?id=" + applicationList.getId()));
	}

	/**
	 * ApplicationList 삭제
	 */
	@RequestMapping("/client/appList/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		int deleteCount = 0;
		String[] ids = selected.split(";");

		for (String id : ids) {
			Integer tmp = new Integer(id);
			ApplicationList applicationList = this.applicationListService.get(tmp);
			if (!EtcUtil.isNone(applicationList)) {
				deleteCount += this.applicationListService.delete(applicationList);
				TraceLog.info("delete applicationList [id:%d] by user [id:%s]", tmp, user.getId());
			} else {
				TraceLog.info("no item to delete applicationList [id:%d]", tmp);
			}
		}

		return (deleteCount > 0);
	}
}
