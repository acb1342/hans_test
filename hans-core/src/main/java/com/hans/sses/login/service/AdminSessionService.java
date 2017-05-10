package com.hans.sses.login.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hans.sses.common.Channel;
import com.hans.sses.common.SessionCode;

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
public interface AdminSessionService { 
	Integer clearSessionAll(Channel channel);

	SessionCode setSession(HttpServletRequest request, Channel channel, String userId, int adminGroupSeq, String forceFlag);

	void removeSession(Channel channel, String userId);

	void removeSession(Object param);
	
	Boolean isAlreadyLogined(Channel channel, String userId, HttpSession session);
	
	List<Map<String, Object>> searchSession(Map<String, Object> param);
	
}
