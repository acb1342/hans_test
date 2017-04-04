package com.hans.sses.route.controller;

import java.util.List;

import com.hans.sses.common.util.TimeUtilz;
import com.hans.sses.route.model.RouteRule;
import com.hans.sses.route.service.RouteRuleService;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : RouteRuleController.java
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
public class RouteRuleController {
	@Autowired
	private RouteRuleService routeRuleService;

	/**
	 * RouteRule 생성
	 */
	@RequestMapping(value = "/route/rule/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		PaginatedList routeRules = null;

		RouteRule condition = new RouteRule();
		if (!EtcUtil.isBlank(searchValue)) {
			if ("id".equals(searchType)) {
				condition.setId(searchValue);
			}
		}

		List<RouteRule> list = this.routeRuleService.search(condition, page, rowPerPage);
		routeRules = new PaginatedListImpl(list, page, this.routeRuleService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("route/rule/search");
		mav.addObject("routeRules", routeRules);

		return mav;
	}

	/**
	 * RouteRule 생성 Form
	 */
	@RequestMapping(value = "/route/rule/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("route/rule/create");
		mav.addObject("routeRule", new RouteRule());
		return mav;
	}

	/**
	 * RouteRule 생성
	 */
	@RequestMapping(value = "/route/rule/create.htm", method = RequestMethod.POST)
	public ModelAndView create(RouteRule routeRule,
			@RequestParam("id") String id,
			@RequestParam("route1") String route1,
			@RequestParam("route2") String route2,
			@RequestParam("route3") String route3,
			@RequestParam("description") String description,
			SessionStatus sessionStatus) {
		routeRule.setId(id);
		routeRule.setRoute1(route1);
		routeRule.setRoute2(route2);
		routeRule.setRoute3(route3);
		routeRule.setChangeDate(TimeUtilz.getCurrentTimeAs14Format());
		routeRule.setDescription(description);
		this.routeRuleService.create(routeRule);
		sessionStatus.setComplete();

		TraceLog.info("create route rule [id:%s, desc:%s]", routeRule.getId(), routeRule.getDescription());

		return new ModelAndView("redirect:/route/rule/detail.htm?id=" + routeRule.getId());
	}

	/**
	 * RouteRule 삭제
	 */
	@RequestMapping("/route/rule/delete.json")
	@ResponseBody
	public Boolean multidelete(@RequestParam("id") String selected) {
		String[] delArray = selected.split(";");
		int deleteCount = 0;
		for (String token : delArray) {
			RouteRule routeRule = this.routeRuleService.get(token);
			if (routeRule != null) {
				deleteCount = this.routeRuleService.delete(token);
			} else {
				TraceLog.info("not exist route rule! [id: %s]", token);
			}
		}

		return (deleteCount > 0);
	}

	/**
	 * RouteRule 상세
	 */
	@RequestMapping("/route/rule/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		RouteRule routeRule = this.routeRuleService.get(id);
		ModelAndView mav = new ModelAndView("route/rule/detail");
		if (routeRule != null) {
			mav.addObject("routeRule", routeRule);
		} else {
			TraceLog.info("not exist route rule! [key: %s]", id);
		}

		return mav;
	}

	/**
	 * RouteRule 수정 Form
	 */
	@RequestMapping(value = "/route/rule/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") String id) throws Exception {
		RouteRule routeRule = this.routeRuleService.get(id);

		ModelAndView mav = new ModelAndView("route/rule/update");
		mav.addObject("routeRule", routeRule);

		return mav;
	}

	/**
	 * RouteRule 수정
	 */
	@RequestMapping(value = "/route/rule/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam("id") String id,
			@RequestParam("route1") String route1,
			@RequestParam("route2") String route2,
			@RequestParam("route3") String route3,
			@RequestParam("description") String description,
			SessionStatus sessionStatus) {
		RouteRule routeRule = this.routeRuleService.get(id);
		routeRule.setRoute1(route1);
		routeRule.setRoute2(route2);
		routeRule.setRoute3(route3);
		routeRule.setChangeDate(TimeUtilz.getCurrentTimeAs14Format());
		routeRule.setDescription(description);
		this.routeRuleService.update(routeRule);
		sessionStatus.setComplete();
		TraceLog.info("update route rule [id:%s, desc:%s]", routeRule.getId(), routeRule.getDescription());

		ModelAndView mav = new ModelAndView("redirect:/route/rule/detail.htm?id=" + routeRule.getId());

		return mav;
	}

	/**
	 * RouteRule 중복 체크
	 */
	@RequestMapping(value = "/route/rule/checkRuleId.json")
	@ResponseBody
	public Boolean checkRuleId(
			@RequestParam("id") String id) {
		RouteRule routeRule = this.routeRuleService.get(id);
		boolean result = (routeRule == null);

		TraceLog.info("duplicate check [id:%s]", id);

		return result;
	}
}
