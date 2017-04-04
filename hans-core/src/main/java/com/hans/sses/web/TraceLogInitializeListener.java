package com.hans.sses.web;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.web
 * @Filename     : TraceLogInitializeListener.java
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
public class TraceLogInitializeListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		String loggerName = event.getServletContext().getInitParameter("loggerName");
		TraceLog.startCheckAndConfigure();
		TraceLog.openLogger(loggerName);
		TraceLog.info("open logger name. [logger:%s]", loggerName);
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		TraceLog.stopCheckAndConfigure();
		TraceLog.info(this.getClass().getSimpleName() + " destroy and call TraceLog.stopCheckAndConfigure().");
	}
}
