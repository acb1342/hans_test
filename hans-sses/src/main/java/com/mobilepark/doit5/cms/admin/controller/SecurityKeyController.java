package com.mobilepark.doit5.cms.admin.controller;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.client.model.SecurityKey;
import com.mobilepark.doit5.client.service.SecurityKeyService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : SecurityKeyController.java
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
public class SecurityKeyController {
	@Autowired
	private SecurityKeyService securityKeyService;

	/**
	 * SecurityKey 생성
	 */
	@RequestMapping(value = "/admin/securityKey/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "id", required = false) String id) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		PaginatedList securityKeys = null;

		SecurityKey securityKey = new SecurityKey();
		if (StringUtils.isNotEmpty(id)) {
			TraceLog.debug("id:%s", id);
			securityKey.setId(id);
		}

		List<SecurityKey> list = this.securityKeyService.search(securityKey, page, rowPerPage);
		securityKeys = new PaginatedListImpl(list, page, this.securityKeyService.searchCount(securityKey), rowPerPage);

		ModelAndView mav = new ModelAndView("admin/securityKey/search");
		mav.addObject("securityKeys", securityKeys);

		return mav;
	}

	/**
	 * SecurityKey 생성 Form
	 */
	@RequestMapping(value = "/admin/securityKey/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("admin/securityKey/create");
		mav.addObject("securityKey", new SecurityKey());
		return mav;
	}

	/**
	 * SecurityKey 생성
	 */
	@RequestMapping(value = "/admin/securityKey/create.htm", method = RequestMethod.POST)
	public ModelAndView create(SecurityKey securityKey, SessionStatus sessionStatus) {
		securityKey.setCreateDate(new Date());
		securityKey.setModifyDate(new Date());
		this.securityKeyService.create(securityKey);
		sessionStatus.setComplete();

		TraceLog.info("create security key [key:%s, value:%s]", securityKey.getId(), securityKey.getValue());

		return new ModelAndView("redirect:/admin/securityKey/detail.htm?id=" + securityKey.getId());
	}

	/**
	 * SecurityKey 삭제
	 */
	@RequestMapping("/admin/securityKey/delete.json")
	@ResponseBody
	public Boolean multidelete(@RequestParam("id") String selected) {
		String[] delArray = selected.split(";");
		int deleteCount = 0;
		for (String token : delArray) {
			SecurityKey securityKey = this.securityKeyService.get(token);
			if (securityKey != null) {
				deleteCount = this.securityKeyService.delete(token);
				TraceLog.info("delete security key [key:%s, value:%s]",
						securityKey.getId(), securityKey.getValue());
			} else {
				TraceLog.info("not exist security key! [key: %s]", token);
			}
		}

		return (deleteCount > 0);
	}

	/**
	 * SecurityKey 상세
	 */
	@RequestMapping("/admin/securityKey/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		SecurityKey securityKey = this.securityKeyService.get(id);
		ModelAndView mav = new ModelAndView("admin/securityKey/detail");
		if (securityKey != null) {
			mav.addObject("securityKey", securityKey);
		} else {
			TraceLog.info("not exist security key! [key: %s]", id);
		}

		return mav;
	}

	/**
	 * SecurityKey 수정 Form
	 */
	@RequestMapping(value = "/admin/securityKey/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") String id) throws Exception {
		SecurityKey securityKey = this.securityKeyService.get(id);

		ModelAndView mav = new ModelAndView("admin/securityKey/update");
		mav.addObject("securityKey", securityKey);

		return mav;
	}

	/**
	 * SecurityKey 수정
	 */
	@RequestMapping(value = "/admin/securityKey/update.htm", method = RequestMethod.POST)
	public ModelAndView update(SecurityKey securityKeyParam, SessionStatus sessionStatus) {
		SecurityKey securityKey = this.securityKeyService.get(securityKeyParam.getId());
		securityKey.setValue(securityKeyParam.getValue());
		securityKey.setModifyDate(new Date());
		this.securityKeyService.update(securityKey);
		sessionStatus.setComplete();
		TraceLog.info("update security key [key:%s, value:%s]", securityKey.getId(), securityKey.getValue());

		ModelAndView mav = new ModelAndView("redirect:/admin/securityKey/detail.htm?id=" + securityKey.getId());

		return mav;
	}

	/**
	 * SecurityKey 중복 체크
	 */
	@RequestMapping(value = "/admin/securityKey/checkKey.json")
	@ResponseBody
	public Boolean checkKey(
			@RequestParam("id") String id) {
		SecurityKey securityKey = this.securityKeyService.get(id);
		boolean result = (securityKey == null);

		TraceLog.info("duplicate check key [key:%s]", id);

		return result;
	}
}
