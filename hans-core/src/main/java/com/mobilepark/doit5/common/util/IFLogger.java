package com.mobilepark.doit5.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.util
 * @Filename     : IFLogger.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
public class IFLogger {
	public static final String GCM_PUSH_LOGGER_NAME = "GCM";
	public static final String APNS_PUSH_LOGGER_NAME = "APNS";
	public static final String UPUSH_PUSH_LOGGER_NAME = "UPUSH";

	private Logger logger;

	private String prefix = "";

	public IFLogger() {
	}

	public IFLogger(String loggerName) {
		this.logger = LoggerFactory.getLogger(loggerName);
	}

	public IFLogger(String loggerName, String prefix) {
		this.logger = LoggerFactory.getLogger(loggerName);
		this.prefix = prefix;
	}

	public IFLogger(Class<?> cls) {
		this.logger = LoggerFactory.getLogger(cls);
	}

	public void trace(String msg) {
		if (this.logger != null) {
			this.logger.trace("{}", this.prefix + msg);
		}
	}

	public void trace(String format, Object... args) {
		if (this.logger != null) {
			this.logger.trace("{}", this.prefix + String.format(format, args));
		}
	}

	public void error(String msg) {
		if (this.logger != null) {
			this.logger.error("{}", this.prefix + msg);
		}
	}

	public void error(String format, Object... args) {
		if (this.logger != null) {
			this.logger.error("{}", this.prefix + String.format(format, args));
		}
	}

	public void warn(String msg) {
		if (this.logger != null) {
			this.logger.warn("{}", this.prefix + msg);
		}
	}

	public void warn(String format, Object... args) {
		if (this.logger != null) {
			this.logger.warn("{}", this.prefix + String.format(format, args));
		}
	}

	public void info(String msg) {
		if (this.logger != null) {
			this.logger.info("{}", this.prefix + msg);
		}
	}

	public void info(String format, Object... args) {
		if (this.logger != null) {
			this.logger.info("{}", this.prefix + String.format(format, args));
		}
	}

	public void debug(String msg) {
		if (this.logger != null) {
			this.logger.debug("{}", this.prefix + msg);
		}
	}

	public void debug(String format, Object... args) {
		if (this.logger != null) {
			this.logger.debug("{}", this.prefix + String.format(format, args));
		}
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public void stackTraceLog(Throwable e) {
		if (this.logger != null) {
			String msg = null;
			this.logger.error("{}", this.prefix + e.getMessage() + " : " + e.getCause());
			StackTraceElement[] stackTraceElem = e.getStackTrace();
			for (StackTraceElement element : stackTraceElem) {
				msg = "at " + element.getClassName() + "." + element.getMethodName() + "("
					+ element.getFileName() + ":" + element.getLineNumber() + ")";
				this.logger.error("{}", this.prefix + msg);
			}
		}
	}

	public Logger getLogger() {
		return this.logger;
	}
}
