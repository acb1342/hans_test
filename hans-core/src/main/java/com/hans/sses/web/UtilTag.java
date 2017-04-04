package com.hans.sses.web;

import java.io.File;
import java.util.Collection;

import com.hans.sses.admin.model.AdminGroup;
import org.apache.commons.lang.StringUtils;

import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.web
 * @Filename     : UtilTag.java
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
public class UtilTag {
	public static boolean contains(Collection<?> coll, Object o) {
		return coll.contains(o);
	}

	public static String replaceAll(String source, String regex, String replacement) {
		return source.replaceAll(regex, replacement);
	}

	public static boolean fileExist(String pathname) {
		return new File(pathname).exists();
	}

	public static String envGet(String key) {
		return Env.get(key);
	}

	public static boolean isAdmin(String userType) {
		return (StringUtils.isNotEmpty(userType) && userType.equals(AdminGroup.ADMIN_GROUP_NAME));
	}

	public static boolean isCp(String userType) {
		return (StringUtils.isNotEmpty(userType) && userType.equals(AdminGroup.CP_GROUP_NAME));
	}

	public static boolean isMcp(String userType) {
		return (StringUtils.isNotEmpty(userType) && userType.equals(AdminGroup.MCP_GROUP_NAME));
	}

	public static boolean isCpMcp(String userType) {
		return (StringUtils.isNotEmpty(userType) && userType.equals(AdminGroup.CP_MCP_GROUP_NAME));
	}

	public static boolean isSeller(String userType) {
		return (StringUtils.isNotEmpty(userType) && userType.equals(AdminGroup.SELLER_GROUP_NAME));
	}

	public static String displayCommonStatus(String status) {
		if ("USE".equalsIgnoreCase(status)) {
			return "사용";
		}
		if ("STOP".equalsIgnoreCase(status)) {
			return "정지";
		}
		if ("WITHDRAWAL".equalsIgnoreCase(status)) {
			return "탈퇴";
		}
		return "기타";
	}

	public static String getEmailIdAsteriskMasked(String emailId, Integer changeCount) {
		String regex = "", result = "";

		if (emailId.length() > changeCount) {
			for (int i = 0; i < changeCount; i++) {
				regex += "*";
			}
		} else {
			for (int i = 0; i < emailId.length() - 1; i++) {
				regex += "*";
			}
		}

		if (emailId.length() > changeCount) {
			result = emailId.substring(0, emailId.length() - changeCount);
			result += regex;
		} else {
			result = emailId.substring(0, 1);
			result += regex;
		}

		return result;
	}

	public static String getMDNAsteriskMasked(String mdn) {
		if (mdn.length() == 11) {
			return mdn.substring(0, 3) + "-" + mdn.substring(mdn.length() - 8, mdn.length() - 6) + "**" + "-" + mdn.substring(mdn.length() - 4, mdn.length() - 1) + "*";
		} else if (mdn.length() == 10) {
			return mdn.substring(0, 3) + "-" + mdn.substring(mdn.length() - 7, mdn.length() - 5) + "*" + "-" + mdn.substring(mdn.length() - 4, mdn.length() - 1) + "*";
		} else {
			return mdn;
		}
	}
}
