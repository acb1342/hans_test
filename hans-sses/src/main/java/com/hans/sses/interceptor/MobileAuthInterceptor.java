package com.hans.sses.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hans.sses.SessionAttrName;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.util.EtcUtil;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.interceptor
 * @Filename     : MobileAuthInterceptor.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 7.       최초 버전
 * =================================================================================
 */
public class MobileAuthInterceptor implements HandlerInterceptor {
	private List<String> ignoreUris;

	private final AntPathMatcher matcher = new AntPathMatcher();

	public List<String> getIgnoreUris() {
		return this.ignoreUris;
	}

	public void setIgnoreUris(List<String> ignoreUris) {
		this.ignoreUris = ignoreUris;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();

		if (!EtcUtil.isNone(this.ignoreUris)) {
			for (String excludeUrl : this.ignoreUris) {
				if (this.matcher.match(excludeUrl, uri)) {
					return true;
				}
			}
		}

		HttpSession session = request.getSession(false);

		if (session == null) {
			response.sendRedirect("/contentstore/content/contentMeta/loginview.mobile");
			return false;
		}

		if (session.getAttribute(SessionAttrName.USER_ID) == null) {
			response.sendRedirect("/contentstore/content/contentMeta/loginview.mobile");
			return false;
		}

		/*
		 * if (session.getAttribute(SessionAttrName.IS_ADMIN) == null) { 
		 * response.sendRedirect("/contentstore/content/contentMeta/loginview.mobile"); return false;
		 *  
		 * } if (!(Boolean)session.getAttribute(SessionAttrName.IS_ADMIN)) { 
		 * response.sendRedirect("/contentstore/content/contentMeta/loginview.mobile"); return false; 
		 * }
		 */

		return true;
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}
}
