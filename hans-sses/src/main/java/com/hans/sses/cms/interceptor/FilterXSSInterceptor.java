package com.hans.sses.cms.interceptor;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.hans.sses.cms.SessionAttrName;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.EtcUtil;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.interceptor
 * @Filename     : FilterXSSInterceptor.java
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
public class FilterXSSInterceptor implements HandlerInterceptor {
	private List<String> ignoreUris;
	private final AntPathMatcher matcher = new AntPathMatcher();

	public List<String> getIgnoreUris() {
		return this.ignoreUris;
	}

	public void setIgnoreUris(List<String> ignoreUris) {
		this.ignoreUris = ignoreUris;
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object model,
			Exception exception) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object model, ModelAndView mav) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		String userId = "ANONYMOUS";

		// filtering 없이 통과 가능한 uri 체크
		TraceLog.info("URI:" + request.getRequestURI());
		if (this.ignoreUris != null && !this.ignoreUris.isEmpty()) {
			for (String excludeUrl : this.ignoreUris) {
				if (this.matcher.match(excludeUrl, uri)) {
					TraceLog.debug("ignore XSSfilter [%s]", uri);
					return true;
				}
			}
		}

		HttpSession session = request.getSession();
		if (session != null) {
			userId = (String) session.getAttribute(SessionAttrName.USER_ID);
		}

		// XSS Script Check
		if (this.filterXSSScript(request)) {
			TraceLog.info("wrong parameters are inputed(in XSS script checking)", userId, uri);
			if (uri.endsWith(".json")) {
				return false;
			} else {
				response.sendRedirect("/error/wrongParameter.jsp");
			}
			return false;
		}

		return true;
	}

	// 파라미터에 xss script가 존재하는지 필터링
	private boolean filterXSSScript(HttpServletRequest request) {
		Map<String, String[]> m = request.getParameterMap();
		Set<Entry<String, String[]>> s = m.entrySet();
		Iterator<Entry<String, String[]>> it = s.iterator();

		// TraceLog.debug("================ Parameters Start ================");
		while (it.hasNext()) {
			Map.Entry<String, String[]> entry = it.next();
			// String key = entry.getKey();
			String[] values = entry.getValue();

			String val = "";
			for (String value : values) {
				val = value.toString();
				// TraceLog.debug("%s=%s", key, val);
				if (val != null && EtcUtil.hasXSSChar(val)) {
					return true; // value include XSS script.
				}
			}
		}
		// TraceLog.debug("================ Parameters End ================");

		return false; // value is ok
	}
}
