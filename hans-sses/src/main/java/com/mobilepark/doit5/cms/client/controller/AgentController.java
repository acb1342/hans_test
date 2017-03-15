package com.mobilepark.doit5.cms.client.controller;

import java.util.ArrayList;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.client.model.Agent;
import com.mobilepark.doit5.client.service.AgentService;
import com.mobilepark.doit5.common.Flag;
import com.mobilepark.doit5.common.util.TimeUtilz;
import com.mobilepark.doit5.provider.service.ContentProviderService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.provider.controller
 * @Filename     : AgentController.java
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
@SessionAttributes("agent")
public class AgentController {
	@Autowired
	private AgentService agentService;

	@Autowired
	private ContentProviderService contentProviderService;

	/**
	 * 에이전트 검색
	 */
	@RequestMapping("/client/agent/search.htm")
	public ModelAndView searchCompany(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		List<Agent> list = new ArrayList<Agent>();
		Agent condition = new Agent();
		if (!EtcUtil.isBlank(searchValue)) {
			if ("agentId".equals(searchType)) {
				condition.setAgentId(searchValue);
			} else if ("mdn".equals(searchType)) {
				condition.setNotiSmsMdn(searchValue);
			}
		}

		// 검색
		list = this.agentService.search(condition, page, rowPerPage);
		PaginatedListImpl agents = new PaginatedListImpl(list, page, this.agentService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/client/agent/search");
		mav.addObject("agents", agents);

		return mav;
	}

	/**
	 * 에이전트 생성 폼
	 */
	@RequestMapping(value = "/client/agent/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("/client/agent/create");
		mav.addObject("usedYnList", Flag.values());
		mav.addObject("ipCheckYnList", Flag.values());
		mav.addObject("statusCheckYnList", Flag.values());
		mav.addObject("notiSmsYnList", Flag.values());
		mav.addObject("contentProviderList", this.contentProviderService.searchAll());
		mav.addObject("agent", new Agent());

		return mav;
	}

	/**
	 * 에이전트 생성
	 */
	@RequestMapping(value = "/client/agent/create.htm", method = RequestMethod.POST)
	public ModelAndView create(Agent agent,
			@RequestParam(value = "contentProviderId", required = true) int contentProviderId,
			@RequestParam("agentId") String agentId,
			@RequestParam("agentType") String agentType,
			@RequestParam(value = "usedYn", required = false) Flag usedYn,
			@RequestParam(value = "ipCheckYn", required = false) Flag ipCheckYn,
			@RequestParam(value = "statusCheckYn", required = false) Flag statusCheckYn,
			@RequestParam(value = "notiSmsYn", required = false) Flag notiSmsYn,
			@RequestParam("notiSmsMdn") String notiSmsMdn,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		agent.setContentProvider(this.contentProviderService.get(contentProviderId));
		agent.setAgentId(agentId);
		agent.setAgentType(agentType);
		agent.setUsedYn(usedYn);
		agent.setIpCheckYn(ipCheckYn);
		agent.setStatusCheckYn(statusCheckYn);
		agent.setNotiSmsYn(notiSmsYn);
		agent.setNotiSmsMdn(notiSmsMdn);
		agent.setRegDate(TimeUtilz.getCurrentTimeAs14Format());
		agent.setUpdateDate(TimeUtilz.getCurrentTimeAs14Format());
		this.agentService.create(agent);

		sessionStatus.setComplete();

		TraceLog.info("create agent [id:%d] by user [id:%s]", agent.getId(), user.getId());

		return new ModelAndView("redirect:/client/agent/detail.htm?id=" + agent.getId());
	}

	/**
	 * 에이전트 상세
	 */
	@RequestMapping("/client/agent/detail.htm")
	public ModelAndView detail(@RequestParam("id") Integer id) throws Exception {
		ModelAndView mav = new ModelAndView("/client/agent/detail");

		Agent agent = this.agentService.get(id);
		if (!EtcUtil.isNone(agent)) {
			mav.addObject("agent", agent);
		}

		return mav;
	}

	/**
	 * 에이전트 수정 폼
	 */
	@RequestMapping(value = "/client/agent/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Integer id) {
		Agent agent = this.agentService.get(id);

		ModelAndView mav = new ModelAndView("/client/agent/update");
		mav.addObject("usedYnList", Flag.values());
		mav.addObject("ipCheckYnList", Flag.values());
		mav.addObject("statusCheckYnList", Flag.values());
		mav.addObject("notiSmsYnList", Flag.values());
		mav.addObject("contentProviderList", this.contentProviderService.searchAll());
		mav.addObject("agent", agent);

		return mav;
	}

	/**
	 * 에이전트 수정
	 */
	@RequestMapping(value = "/client/agent/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam("id") Integer id,
			@RequestParam("agentId") String agentId,
			@RequestParam("agentType") String agentType,
			@RequestParam(value = "usedYn", required = false) Flag usedYn,
			@RequestParam(value = "ipCheckYn", required = false) Flag ipCheckYn,
			@RequestParam(value = "statusCheckYn", required = false) Flag statusCheckYn,
			@RequestParam(value = "notiSmsYn", required = false) Flag notiSmsYn,
			@RequestParam("notiSmsMdn") String notiSmsMdn,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		Agent agent = this.agentService.get(id);
		if (agent != null) {
			agent.setAgentId(agentId);
			agent.setAgentType(agentType);
			agent.setUsedYn(usedYn);
			agent.setIpCheckYn(ipCheckYn);
			agent.setStatusCheckYn(statusCheckYn);
			agent.setNotiSmsYn(notiSmsYn);
			agent.setNotiSmsMdn(notiSmsMdn);
			agent.setUpdateDate(TimeUtilz.getCurrentTimeAs14Format());
			this.agentService.update(agent);
		}
		sessionStatus.setComplete();

		TraceLog.info("update agent [id:%d] by user [id:%s]", agent.getId(), user.getId());

		return new ModelAndView(new RedirectView("/client/agent/detail.htm?id=" + agent.getId()));
	}

	/**
	 * 에이전트 삭제
	 */
	@RequestMapping("/client/agent/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		int deleteCount = 0;
		String[] ids = selected.split(";");

		for (String id : ids) {
			Integer tmp = new Integer(id);
			Agent agent = this.agentService.get(tmp);
			if (!EtcUtil.isNone(agent)) {
				deleteCount += this.agentService.delete(agent);
				TraceLog.info("delete agent [id:%d] by user [id:%s]", tmp, user.getId());
			} else {
				TraceLog.info("no item to delete agent [id:%d]", tmp);
			}
		}

		return (deleteCount > 0);
	}
}
