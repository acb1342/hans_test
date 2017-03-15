package com.mobilepark.doit5.cms.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.mobilepark.doit5.common.Channel;
import com.mobilepark.doit5.login.service.AdminSessionService;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.listener
 * @Filename     : SessionClearListener.java
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
public class SessionClearListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
		AdminSessionService adminSessionService = (AdminSessionService) ctx.getBean("adminSessionService");

		adminSessionService.clearSessionAll(Channel.ADMIN);
		TraceLog.info("restarted container. clear all deactive sessions.");
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
	}
}
