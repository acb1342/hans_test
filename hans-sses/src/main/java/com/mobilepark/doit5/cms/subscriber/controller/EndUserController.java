package com.mobilepark.doit5.cms.subscriber.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
import com.mobilepark.doit5.client.service.ApplicationListService;
import com.mobilepark.doit5.cms.SessionAttrName;
import com.mobilepark.doit5.subscriber.model.EndUser;
import com.mobilepark.doit5.subscriber.service.EndUserService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.subscriber.controller
 * @Filename     : EndUserController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 10.      최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("endUser")
public class EndUserController {
	@Autowired
	private EndUserService endUserService;

	@Autowired
	private ApplicationListService applicationListService;

	/**
	 * 사용자 검색
	 */
	@RequestMapping("/subscriber/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		ModelAndView mav = new ModelAndView("/subscriber/search");
		EndUser condition = new EndUser();

		// 검색
		List<EndUser> list = new ArrayList<EndUser>();
		if (!EtcUtil.isBlank(searchValue)) {
			if ("appId".equals(searchType)) {
				condition.setAppId(searchValue);
			} else if ("pushToken".equals(searchType)) {
				condition.setPushToken(searchValue);
			} else if ("mdn".equals(searchType)) {
				condition.setMdn(searchValue);
			}

			list = this.endUserService.search(condition, page, rowPerPage);
			PaginatedListImpl endUsers = new PaginatedListImpl(list, page, this.endUserService.searchCount(condition), rowPerPage);

			mav.addObject("endUsers", endUsers);
		}

		return mav;
	}

	/**
	 * EndUser 생성 폼
	 */
	@RequestMapping(value = "/subscriber/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("/subscriber/create");
		mav.addObject("applicationLists", this.applicationListService.searchAll());
		mav.addObject("endUser", new EndUser());

		return mav;
	}

	/**
	 * EndUser 생성
	 */
	@RequestMapping(value = "/subscriber/create.htm", method = RequestMethod.POST)
	public ModelAndView create(EndUser endUser,
			@RequestParam(value = "appListId", required = true) Integer appListId,
			@RequestParam("pushToken") String pushToken,
			@RequestParam("os") String os,
			@RequestParam("osVer") String osVer,
			@RequestParam("libVer") String libVer,
			@RequestParam("deviceId") String deviceId,
			@RequestParam("deviceBrand") String deviceBrand,
			@RequestParam("deviceModel") String deviceModel,
			@RequestParam("height") Integer height,
			@RequestParam("width") Integer width,
			@RequestParam("locale") String locale,
			@RequestParam("market") String market,
			@RequestParam("mdn") String mdn,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		endUser.setAppId(this.applicationListService.get(appListId).getAppId());
		endUser.setAppVer(this.applicationListService.get(appListId).getAppVer());
		endUser.setPushToken(pushToken);
		endUser.setOs(os);
		endUser.setOsVer(osVer);
		endUser.setLibVer(libVer);
		endUser.setDeviceId(deviceId);
		endUser.setDeviceBrand(deviceBrand);
		endUser.setDeviceModel(deviceModel);
		endUser.setHeight(height);
		endUser.setWidth(width);
		endUser.setLocale(locale);
		endUser.setMarket(market);
		endUser.setMdn(mdn);
		this.endUserService.create(endUser);

		sessionStatus.setComplete();

		TraceLog.info("create endUser [id:%d] by user [id:%s]", endUser.getId(), user.getId());

		return new ModelAndView("redirect:/subscriber/detail.htm?id=" + endUser.getId());
	}

	/**
	 * 사용자 상세
	 */
	@RequestMapping("/subscriber/detail.htm")
	public ModelAndView detail(@RequestParam("id") Integer id) throws Exception {
		ModelAndView mav = new ModelAndView("/subscriber/detail");

		EndUser endUser = this.endUserService.get(id);
		if (!EtcUtil.isNone(endUser)) {
			mav.addObject("endUser", endUser);
		}

		return mav;
	}

	/**
	 * EndUser 수정 폼
	 */
	@RequestMapping(value = "/subscriber/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Integer id) {
		EndUser endUser = this.endUserService.get(id);

		ModelAndView mav = new ModelAndView("/subscriber/update");
		mav.addObject("applicationLists", this.applicationListService.searchAll());
		mav.addObject("endUser", endUser);

		return mav;
	}

	/**
	 * EndUser 수정
	 */
	@RequestMapping(value = "/subscriber/update.htm", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam("id") Integer id,
			@RequestParam(value = "appListId", required = true) Integer appListId,
			@RequestParam("pushToken") String pushToken,
			@RequestParam("os") String os,
			@RequestParam("osVer") String osVer,
			@RequestParam("libVer") String libVer,
			@RequestParam("deviceId") String deviceId,
			@RequestParam("deviceBrand") String deviceBrand,
			@RequestParam("deviceModel") String deviceModel,
			@RequestParam("height") Integer height,
			@RequestParam("width") Integer width,
			@RequestParam("locale") String locale,
			@RequestParam("market") String market,
			@RequestParam("mdn") String mdn,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		EndUser endUser = this.endUserService.get(id);
		if (endUser != null) {
			endUser.setAppId(this.applicationListService.get(appListId).getAppId());
			endUser.setAppVer(this.applicationListService.get(appListId).getAppVer());
			endUser.setPushToken(pushToken);
			endUser.setOs(os);
			endUser.setOsVer(osVer);
			endUser.setLibVer(libVer);
			endUser.setDeviceId(deviceId);
			endUser.setDeviceBrand(deviceBrand);
			endUser.setDeviceModel(deviceModel);
			endUser.setHeight(height);
			endUser.setWidth(width);
			endUser.setLocale(locale);
			endUser.setMarket(market);
			endUser.setMdn(mdn);
			this.endUserService.update(endUser);
		}
		sessionStatus.setComplete();

		TraceLog.info("update endUser [id:%d] by user [id:%s]", endUser.getId(), user.getId());

		return new ModelAndView(new RedirectView("/subscriber/detail.htm?id=" + endUser.getId()));
	}

	/**
	 * EndUser 삭제
	 */
	@RequestMapping("/subscriber/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		int deleteCount = 0;
		String[] ids = selected.split(";");

		for (String id : ids) {
			Integer tmp = new Integer(id);
			EndUser endUser = this.endUserService.get(tmp);
			if (!EtcUtil.isNone(endUser)) {
				deleteCount += this.endUserService.delete(endUser);
				TraceLog.info("delete endUser [id:%d] by user [id:%s]", tmp, user.getId());
			} else {
				TraceLog.info("no item to delete endUser [id:%d]", tmp);
			}
		}

		return (deleteCount > 0);
	}
}
