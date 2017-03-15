package com.mobilepark.doit5.cms.listener;

import java.util.List;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mobilepark.doit5.login.model.AdminSession;
import com.mobilepark.doit5.login.service.AdminSessionService;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.listener
 * @Filename     : LoginSessionListener.java
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
public class LoginSessionListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent event) {
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		try {
			ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getSession().getServletContext());
			AdminSessionService adminSessionService = (AdminSessionService) ctx.getBean("adminSessionService");

			String sessionId = event.getSession().getId();
			AdminSession filter = new AdminSession();
			filter.setSessionId(sessionId);
			//filter.setEdt(null);
			List<AdminSession> list = adminSessionService.search(filter);

			if (list != null && list.size() > 0) {
				for (AdminSession session : list) {
					adminSessionService.delete(session.getId());
					TraceLog.info("userId[%s] logged out..", session.getAdminId());
				}
			}
		} catch (Exception e) {
			TraceLog.printStackTrace(e);
		}
	}
}
