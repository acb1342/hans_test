package com.hans.sses.web;

import java.io.IOException;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.web
 * @Filename     : EnvLoaderListener.java
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
public class EnvLoaderListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		String configFile = event.getServletContext().getInitParameter("configFile");
		TraceLog.info("opening configuration file. [file:%s]", configFile);
		try {
			Env.startLoadAndWatch(configFile);
		} catch (IOException e) {
			throw new RuntimeException("configuration file loading error!!\n" + e.getMessage(), e);
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		Env.stopLoadAndWatch();
		TraceLog.info(this.getClass().getSimpleName() + " destroy and call Env.stopLoadAndWatch().");
	}
}
