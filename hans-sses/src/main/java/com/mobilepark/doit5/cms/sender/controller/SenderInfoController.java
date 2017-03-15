package com.mobilepark.doit5.cms.sender.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.client.model.Application;
import com.mobilepark.doit5.client.service.ApplicationService;
import com.mobilepark.doit5.provider.model.ContentProvider;
import com.mobilepark.doit5.provider.service.ContentProviderService;
import com.mobilepark.doit5.sender.model.SenderInfo;
import com.mobilepark.doit5.sender.service.SenderInfoService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : SenderInfoController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE               Description
 * =================================================================================
 *  1.0	   2014. 2. 24.       최초 버전
 * =================================================================================
 */
@Controller
public class SenderInfoController {
	@Autowired
	private SenderInfoService senderInfoService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private ContentProviderService contentProviderService;

	/**
	 * SenderInfo 생성
	 */
	@RequestMapping(value = "/sender/info/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		PaginatedList senderInfos = null;
		SenderInfo condition = new SenderInfo();
		if (!EtcUtil.isBlank(searchValue)) {
			if ("id".equals(searchType)) {
				condition.setId(searchValue);
			}
		}

		List<SenderInfo> list = this.senderInfoService.search(condition, page, rowPerPage);
		senderInfos = new PaginatedListImpl(list, page, this.senderInfoService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("sender/info/search");
		mav.addObject("senderInfos", senderInfos);

		return mav;
	}

	/**
	 * SenderInfo 생성 Form
	 */
	@RequestMapping(value = "/sender/info/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("sender/info/create");
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
		mav.addObject("senderInfo", new SenderInfo());
		return mav;
	}

	/**
	 * SenderInfo 생성
	 */
	@RequestMapping(value = "/sender/info/create.htm", method = RequestMethod.POST)
	public ModelAndView create(SenderInfo senderInfo,
			@RequestParam("id") String id,
			@RequestParam("senderName") String senderName,
			@RequestParam("senderIndex") Integer senderIndex,
			@RequestParam("senderTid") Integer senderTid,
			@RequestParam("senderId") String senderId,
			@RequestParam(value = "appId", required = true) Integer appId,
			@RequestParam("ip") String ip,
			@RequestParam("port") Integer port,
			@RequestParam("url") String url,
			@RequestParam("tps") Integer tps,
			SessionStatus sessionStatus) {
		senderInfo.setId(id);
		senderInfo.setSenderName(senderName);
		senderInfo.setSenderIndex(senderIndex);
		senderInfo.setSenderTid(senderTid);
		senderInfo.setSenderId(senderId);
		senderInfo.setAppId(this.applicationService.get(appId).getPkgId());
		senderInfo.setIp(ip);
		senderInfo.setPort(port);
		senderInfo.setUrl(url);
		senderInfo.setTps(tps);
		this.senderInfoService.create(senderInfo);
		sessionStatus.setComplete();

		TraceLog.info("create sender info [id:%s]", senderInfo.getId());

		return new ModelAndView("redirect:/sender/info/detail.htm?id=" + senderInfo.getId());
	}

	/**
	 * SenderInfo 삭제
	 */
	@RequestMapping("/sender/info/delete.json")
	@ResponseBody
	public Boolean multidelete(@RequestParam("id") String selected) {
		String[] delArray = selected.split(";");
		int deleteCount = 0;
		for (String token : delArray) {
			SenderInfo senderInfo = this.senderInfoService.get(token);
			if (senderInfo != null) {
				deleteCount = this.senderInfoService.delete(token);
			} else {
				TraceLog.info("not exist sender info! [key: %s]", token);
			}
		}

		return (deleteCount > 0);
	}

	/**
	 * SenderInfo 상세
	 */
	@RequestMapping("/sender/info/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		SenderInfo senderInfo = this.senderInfoService.get(id);
		ModelAndView mav = new ModelAndView("sender/info/detail");
		if (senderInfo != null) {
			mav.addObject("senderInfo", senderInfo);
		} else {
			TraceLog.info("not exist sender info! [id: %s]", id);
		}

		return mav;
	}

	/**
	 * SenderInfo 수정 Form
	 */
	@RequestMapping(value = "/sender/info/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") String id,
						HttpSession session) throws Exception {
		SenderInfo senderInfo = this.senderInfoService.get(id);

		ModelAndView mav = new ModelAndView("sender/info/update");
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
		mav.addObject("senderInfo", senderInfo);
		Application application = this.applicationService.getByAppId(senderInfo.getAppId());
		if (application != null) {
			mav.addObject("appName", application.getAppName());
		}

		return mav;
	}

	/**
	 * SenderInfo 수정
	 */
	@RequestMapping(value = "/sender/info/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam("id") String id,
			@RequestParam("senderName") String senderName,
			@RequestParam("senderIndex") Integer senderIndex,
			@RequestParam("senderTid") Integer senderTid,
			@RequestParam("senderId") String senderId,
			@RequestParam(value = "appId", required = false) Integer appId,
			@RequestParam("ip") String ip,
			@RequestParam("port") Integer port,
			@RequestParam("url") String url,
			@RequestParam("tps") Integer tps,
			SessionStatus sessionStatus) {
		SenderInfo senderInfo = this.senderInfoService.get(id);
		senderInfo.setSenderName(senderName);
		senderInfo.setSenderIndex(senderIndex);
		senderInfo.setSenderTid(senderTid);
		senderInfo.setSenderId(senderId);
		senderInfo.setAppId(this.applicationService.get(appId).getPkgId());
		senderInfo.setIp(ip);
		senderInfo.setPort(port);
		senderInfo.setUrl(url);
		senderInfo.setTps(tps);
		this.senderInfoService.update(senderInfo);
		sessionStatus.setComplete();
		TraceLog.info("update sender info [id:%s]", senderInfo.getId());

		ModelAndView mav = new ModelAndView("redirect:/sender/info/detail.htm?id=" + senderInfo.getId());

		return mav;
	}

	/**
	 * SenderInfo 중복 체크
	 */
	@RequestMapping(value = "/sender/info/checkSenderInfoId.json")
	@ResponseBody
	public Boolean checkSenderInfoId(
			@RequestParam("id") String id) {
		SenderInfo senderInfo = this.senderInfoService.get(id);
		boolean result = (senderInfo == null);

		TraceLog.info("duplicate check [id:%s]", id);

		return result;
	}
}
