package com.mobilepark.doit5.cms.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import com.mobilepark.doit5.cms.auth.Authority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.MenuFunc;
import com.mobilepark.doit5.admin.service.MenuService;
import com.mobilepark.doit5.cms.auth.Authentication;
import com.mobilepark.doit5.cms.auth.CmsAuthority;
import com.mobilepark.doit5.login.model.AdminSession;
import com.mobilepark.doit5.login.service.AdminSessionService;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.interceptor
 * @Filename     : CmsAuthenticationInterceptor.java
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
public class CmsAuthenticationInterceptor implements HandlerInterceptor {
	@Autowired
	private MenuService menuService;

	private List<String> ignoreUris;

	private final AntPathMatcher matcher = new AntPathMatcher();

	public MenuService getmenuService() {
		return this.menuService;
	}

	public List<String> getIgnoreUris() {
		return this.ignoreUris;
	}

	public void setIgnoreUris(List<String> ignoreUris) {
		this.ignoreUris = ignoreUris;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		/**
		 * 세션 체크
		 */
		HttpSession session = request.getSession(true);
		Authentication authentication = (Authentication) session.getAttribute(SessionAttrName.AUTHENTICATION);

		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(session.getServletContext());
		AdminSessionService adminSessionService = (AdminSessionService) ctx.getBean("adminSessionService");

		AdminSession filter = new AdminSession();
		filter.setSessionId(session.getId());
		//filter.setEdt(null);
		List<AdminSession> list = adminSessionService.search(filter);

		// 다른 브라우저에서 로그인시, 해당 브라우저의 session를 종료시킨다.
//		if ((list == null || (list != null && list.size() == 0)) && authentication != null) {
//			session.invalidate();
//			TraceLog.info("already deprived session [sessionId:%s]", session.getId());
//			response.sendRedirect("/error/duplicateLogin.jsp");
//			return false;
//		}

		String uri = request.getRequestURI();
		if (this.ignoreUris != null && !this.ignoreUris.isEmpty()) {
			for (String excludeUrl : this.ignoreUris) {
				if (this.matcher.match(excludeUrl, uri)) {
					TraceLog.debug("ignore authentication [%s]", uri);
					return true;
				}
			}
		}
		TraceLog.debug("session 1111111");

		if (authentication == null) {
			TraceLog.info("session out!");
			response.sendRedirect("/error/sessionOut.jsp");
			return false;
		}

		TraceLog.debug("session 22222");

		/**
		 * 메뉴에 대한 권한 체크
		 */
		Authority authority = null;
		String userId = (String) session.getAttribute(SessionAttrName.USER_ID);
		if (userId.equals(Authentication.SUPER_USER_ID)) {
			// TraceLog.debug("request of super user [uri:%s]", uri);
			authority = new CmsAuthority("CRUDA");
			// authority = new CmsAuthority("CRUD");

		} else {
			/* leogon
			MenuFunc cmsMenuFunction = this.menuService.getFunctionByUrl(uri);
			if (cmsMenuFunction == null) {
				TraceLog.debug("eno20=================================");
				TraceLog.info("access none service page!!! [uri:%s]", uri);
				response.sendRedirect("/error/noneServicePage.jsp");
				return false;
			}

			authority = authentication.getAuthority(cmsMenuFunction.getMenu().getId());
			if (!this.isAuthenticate(cmsMenuFunction.getAuth(), authority)) {
				TraceLog.info("not authority request!!! [id:%s, uri:%s]", userId, uri);
				response.sendRedirect("/error/hasNotAuthority.jsp");
				return false;
			}
			*/
		}
		TraceLog.debug("session 33333=="+authority);

		request.setAttribute("authority", authority);

		return true;
	}

	// 사용자 권한 중 해당 기능의 권한 타입(C,R,U,D..)이 있는지 확인
	private boolean isAuthenticate(String cmsFunctionType, Authority authority) {
		if (cmsFunctionType.equalsIgnoreCase("ANY")) {
			return true;
		} else if (cmsFunctionType.equalsIgnoreCase("CREATE")) {
			return authority.isCreate();
		} else if (cmsFunctionType.equalsIgnoreCase("READ")) {
			return authority.isRead();
		} else if (cmsFunctionType.equalsIgnoreCase("UPDATE")) {
			return authority.isUpdate();
		} else if (cmsFunctionType.equalsIgnoreCase("DELETE")) {
			return authority.isDelete();
		} else if (cmsFunctionType.equalsIgnoreCase("APPROVE")) {
			return authority.isApprove();
		}

		return false;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object model, ModelAndView mav)
			throws Exception {

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object model,
			Exception exception) throws Exception {

	}
}
