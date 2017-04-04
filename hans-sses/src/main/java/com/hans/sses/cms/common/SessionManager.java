package com.hans.sses.cms.common;

import javax.servlet.http.HttpServletRequest;

import com.hans.sses.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.util.WebUtils;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.common
 * @Filename     : SessionManager.java
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
public class SessionManager {
	public static boolean isLogin(HttpServletRequest request) {
		String memberId = (String) WebUtils.getSessionAttribute(request, SessionAttrName.USER_ID);
		if (!StringUtils.isEmpty(memberId)) {
			return true;
		}

		return false;
	}
}
