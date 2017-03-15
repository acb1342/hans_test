package com.mobilepark.doit5.cms.home;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import com.mobilepark.doit5.cms.auth.Authentication;
import com.mobilepark.doit5.cms.auth.AuthenticationManager;
import com.mobilepark.doit5.cms.auth.exception.AuthenticationException;
import com.mobilepark.doit5.cms.common.SessionManager;
import org.springframework.ui.Model;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.dao.AdminDaoMybatisTest;
import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.common.Channel;
import com.mobilepark.doit5.common.SessionCode;
import com.mobilepark.doit5.login.service.AdminSessionService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.HexUtil;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.home
 * @Filename     : LoginController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 22.       최초 버전
 * =================================================================================
 */
@Controller
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private AdminSessionService adminSessionService;
	
	@Autowired
	private AdminDaoMybatisTest adminDaoMy;

	/**
	 * 로그인 페이지로 이동 
	 */
	@RequestMapping(value = "/home1/login", method = RequestMethod.GET)
	protected String loginForm(Model model) {

		return "home1/login";
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/home1/welcome")
	public ModelAndView test(){


			String message = "Hello World 한글";

			System.out.println("/home1/welcome --> 실행됨");


			return new ModelAndView("/home1/welcome", "message", message);

	}
	
	/**
	 * 로그인 페이지로 이동 
	 */
	@RequestMapping(value = "/home/login.htm", method = RequestMethod.GET)
	protected String loginForm() {
		return "home/login";
	}

	/**
	 * 로그인(세션에 attribut 저장, DB에 로그인 정보 저장)
	 */
	@RequestMapping(value = "/home/login.json", method = RequestMethod.POST)
	@ResponseBody
	protected Map<Object, Object> login(
			@RequestParam(value = SessionAttrName.USER_ID, required = false) String userId,
			@RequestParam(value = "password", required = false) String password,
			@RequestParam(value = "forceFlag", required = false) String forceFlag,
			HttpSession session,
			HttpServletRequest request) throws Exception {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> errors = new HashMap<Object, Object>();
		String encPassword = "";
		if (forceFlag.isEmpty()) {
			forceFlag = null;
		}

		try {
			if (StringUtils.isNotEmpty(password)) {
				encPassword = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));
			}
			Authentication authentication = this.authenticationManager.authenticate(userId, encPassword);
			Admin cmsUser = authentication.getUser();
			AdminGroup cmsGroup = cmsUser.getAdminGroup();
			
			if (cmsUser.getPwErrCnt() >= 5) {
				errors.put("reason", "loginCount");
				result.put("errors", errors);
				result.put("success", false);
				return result;
			}

			SessionCode resultFlag = this.adminSessionService.setSession(request, Channel.ADMIN, userId.toString(), cmsGroup.getName(), forceFlag);
			if (resultFlag.equals(SessionCode.RET_FORCE)) {
				errors.put("reason", "loginDuple");
				result.put("errors", errors);
				result.put("success", false);
				return result;
			}

			TraceLog.debug("group:%s", cmsGroup.getName());
			if ("ADMIN".equalsIgnoreCase(cmsGroup.getName())) {
				session.setAttribute(SessionAttrName.IS_ADMIN, true);
				session.setAttribute(SessionAttrName.IS_CP, false);
				session.setAttribute(SessionAttrName.IS_USER, false);
			} else if ("CP".equalsIgnoreCase(cmsGroup.getName())) {
				session.setAttribute(SessionAttrName.IS_ADMIN, false);
				session.setAttribute(SessionAttrName.IS_CP, true);
				session.setAttribute(SessionAttrName.IS_USER, false);
			} else {
				session.setAttribute(SessionAttrName.IS_ADMIN, false);
				session.setAttribute(SessionAttrName.IS_CP, false);
				session.setAttribute(SessionAttrName.IS_USER, true);
			}
			session.setAttribute(SessionAttrName.AUTHENTICATION, authentication);
			session.setAttribute(SessionAttrName.USER_ID, userId);
			session.setAttribute(SessionAttrName.LOGIN_USER, cmsUser);
			session.setAttribute(SessionAttrName.LOGIN_GROUP, cmsGroup);
			result.put("success", true);
		} catch (AuthenticationException e) {
			result.put("success", false);
			errors.put("reason", "Login failed. Try again.");
			result.put("errors", errors);
		}

		return result;
	}

	/**
	 * 로그아웃
	 */
	@RequestMapping("/home/logout.htm")
	protected ModelAndView handleRequestInternal(HttpSession session) throws Exception {
		session.invalidate();
		return new ModelAndView("home/login");
	}

	/**
	 * redirection after ajax call doesn't work,
	 * calling loginSessionChk() does solve problem
	 */
	@RequestMapping(value = "/session.chk", method = RequestMethod.POST)
	@ResponseBody
	protected Map<Object, Object> loginSessionChk(HttpServletRequest request) {
		Map<Object, Object> result = new HashMap<Object, Object>();

		// 로그인 세션 체크
		if (!SessionManager.isLogin(request)) {
			result.put("isLogin", false);
			result.put("isDupLogin", false);
		} else {
			result.put("isLogin", true);
			result.put("isDupLogin", false);

			HttpSession session = request.getSession(false);
			if (session != null) {
				// 중복 로그인 체크
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("sessionId", session.getId());
				List<Map<String, Object>> list = this.adminDaoMy.searchSession(param);
			
				if (list == null || (list != null && list.size() == 0)) {
					session.invalidate();
					TraceLog.info("already deprived session [sessionId:%s]", session.getId());
					result.put("isDupLogin", true);
				}
			}
		}

		return result;
	}
}
