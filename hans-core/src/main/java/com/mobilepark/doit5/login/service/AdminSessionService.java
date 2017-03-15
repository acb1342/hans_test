package com.mobilepark.doit5.login.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.common.Channel;
import com.mobilepark.doit5.common.SessionCode;
import com.mobilepark.doit5.login.model.AdminSession;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.login.service
 * @Filename     : AdminSessionService.java
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
public interface AdminSessionService extends GenericService<AdminSession, Long> {
	Integer clearSessionAll(Channel channel);

	SessionCode setSession(HttpServletRequest request, Channel channel, String userId, String userLevel, String forceFlag);

	void removeSession(Channel channel, String userId);

	Boolean isAlreadyLogined(Channel channel, String userId, HttpSession session);
}
