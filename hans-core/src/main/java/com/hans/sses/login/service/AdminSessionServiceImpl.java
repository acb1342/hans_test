package com.hans.sses.login.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.hans.sses.admin.dao.AdminDaoMybatisTest;
import com.hans.sses.common.Channel;
import com.hans.sses.common.SessionCode;
import com.hans.sses.login.dao.AdminSessionDao;
import com.hans.sses.common.Flag;
import com.hans.sses.login.model.AdminSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.login.service
 * @Filename     : AdminSessionServiceImpl.java
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
@Transactional
public class AdminSessionServiceImpl extends AbstractGenericService<AdminSession, Long> implements AdminSessionService {
	@Autowired
    AdminSessionDao adminSessionDao;
	
	@Autowired
    AdminDaoMybatisTest adminDaoMy;

	@Override
	protected GenericDao<AdminSession, Long> getGenericDao() {
		return this.adminSessionDao;
	}

	@Override
	public Integer clearSessionAll(Channel channel) {
		return this.adminDaoMy.clearSessionAll(channel.toString());
	}

	@Override
	public SessionCode setSession(HttpServletRequest request, Channel channel, String userId, String userLevel, String forceFlag) {
		try {
			TraceLog.debug("userId[%s], userLevel[%s], forceFlag[%s]", userId, userLevel, forceFlag);
			HttpSession session = request.getSession();

			if (this.isAlreadyLogined(channel, userId, session) && forceFlag == null) {
				return SessionCode.RET_FORCE;
			} else if (this.isAlreadyLogined(channel, userId, session) && forceFlag.equals(Flag.Y.toString())) {
				this.removeSession(channel, userId);
				TraceLog.info("userId[%s] session deprived..", userId);
			}

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("adminId", userId);
			param.put("sessionId", session.getId());
			param.put("host", request.getRemoteAddr());
			param.put("port", request.getRemotePort());
			param.put("fstRgDt", new Date());
			this.adminDaoMy.createSession(param);
			
			TraceLog.info("userId[%s] logged in..", userId);
		} catch (Exception e) {
			TraceLog.printStackTrace(e);
			return SessionCode.RET_FAIL;
		}

		return SessionCode.RET_SUCCESS;
	}

	@Override
	public void removeSession(Object param) {
		int id = Integer.parseInt(param.toString());
		this.adminDaoMy.deleteSession(id);
	}
	
	@Override
	public void removeSession(Channel channel, String adminId) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("adminId", adminId);
		param.put("chan", channel);
		List<Map<String, Object>> sessionList = this.adminDaoMy.searchSession(param);
		
		for (Map<String, Object> session : sessionList) {
			int id = Integer.parseInt(session.get("id").toString());
			this.adminDaoMy.deleteSession(id);
		}
	}

	@Override
	public Boolean isAlreadyLogined(Channel channel, String adminId, HttpSession session) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("adminId", adminId);
		param.put("chan", channel);
		List<Map<String, Object>> list = this.adminDaoMy.searchSession(param);
		
		if (list != null && list.size() > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	@Override
	public List<Map<String, Object>> searchSession(Map<String, Object> param) {
		return this.adminDaoMy.searchSession(param);
	}

}
