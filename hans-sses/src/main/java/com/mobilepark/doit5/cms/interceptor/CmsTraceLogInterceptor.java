package com.mobilepark.doit5.cms.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.interceptor
 * @Filename     : CmsTraceLogInterceptor.java
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
public class CmsTraceLogInterceptor implements HandlerInterceptor {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		if (session != null) {
			String userId = (String) session.getAttribute("userId");
			if (userId != null) {
				TraceLog.newTraceSession(userId);
			}
		}

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object model, ModelAndView mav)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
			Object model, Exception exception) throws Exception {

		if (exception != null) {
			TraceLog.error(exception.getMessage(), exception);
		}

		TraceLog.removeTraceSession();
	}
}
