package com.mobilepark.doit5.ifs.upns;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upns
 * @Filename     : UPNSLog.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 16.      최초 버전
 * =================================================================================
 */
public class UPNSLog {
	private final static String PREFIX_TR = "---TR ";
	private final static String LS = SystemUtils.LINE_SEPARATOR;
	private static Logger logger = Logger.getLogger("com.mobilepark.doit5.ifs.upns");

	public static void request(String method, String url, HttpHeaders httpHeaders, UPNSMessage message) {
		if (logger.isDebugEnabled()) {
			StringBuilder sbuilder = new StringBuilder();

			Iterator<Entry<String, String>> headers = httpHeaders != null ? httpHeaders.toSingleValueMap().entrySet()
					.iterator() : null;

			sbuilder.append(LS)
					.append(PREFIX_TR)
					.append("***********************************************************************************************")
					.append(LS)
					.append(PREFIX_TR)
					.append("[UPUSH ->> UPNS]")
					.append(LS)
					.append(PREFIX_TR)
					.append("***********************************************************************************************")
					.append(LS);
			while (headers != null && headers.hasNext()) {
				Map.Entry<String, String> entry = headers.next();
				sbuilder.append(PREFIX_TR).append(entry.getKey()).append(":").append(entry.getValue()).append(LS);
			}

			sbuilder.append(PREFIX_TR)
					.append(LS)
					.append(PREFIX_TR)
					.append(method)
					.append(" ")
					.append(url)
					.append(LS)
					.append(PREFIX_TR);

			sbuilder.append(LS)
					.append(PREFIX_TR)
					.append("***********************************************************************************************")
					.append(LS);

			if (message != null) {
				sbuilder.append(LS)
						.append(prefixFormatting(message.toString(), PREFIX_TR))
						.append(LS)
						.append("***********************************************************************************************")
						.append(LS);
			}

			logger.debug(sbuilder.toString());
		}
	}

	public static void response(String response) {
		if (logger.isDebugEnabled()) {
			StringBuilder sbuilder = new StringBuilder();

			sbuilder.append(LS)
					.append(PREFIX_TR)
					.append("***********************************************************************************************")
					.append(LS)
					.append(PREFIX_TR)
					.append("[UPUSH <<- UPNS]")
					.append(LS)
					.append(PREFIX_TR)
					.append("***********************************************************************************************")
					.append(LS)
					.append(prefixFormatting(response, PREFIX_TR))
					.append(LS)
					.append(PREFIX_TR)
					.append("***********************************************************************************************")
					.append(LS);

			logger.debug(sbuilder.toString());
		}
	}

	public static String prefixFormatting(String str, String prefix) {
		Pattern p = Pattern.compile("\n");
		Matcher m = p.matcher(str);

		StringBuffer sbuilder = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sbuilder, "\n" + prefix);
		}
		m.appendTail(sbuilder);

		return (prefix + sbuilder.toString());
	}
}
