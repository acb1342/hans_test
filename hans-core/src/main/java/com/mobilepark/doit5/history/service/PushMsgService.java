package com.mobilepark.doit5.history.service;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.common.push.PushMessage;
import com.mobilepark.doit5.history.dao.PushMsgDaoMybatis;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.history.service
 * @Filename     : PushMsgService.java
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 *
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
@Service
@Transactional
public class PushMsgService {
	
	@Autowired
	private PushMsgDaoMybatis pushMsgDaoMybatis;


	public Map<String, Object> getUserMessageList(Integer page, Integer size, String usid) {
		return pushMsgDaoMybatis.selectUserMessageList(page, size, usid);
	}
	
	public int sendPushMessage(Integer limit) {
		
		String androidApiKey = Env.get("push.android.apikey");
		String iosKeyFilePath = Env.get("push.ios.keyfile.path");
		String iosKeyFilePassword = Env.get("push.ios.keyfile.password");
		Boolean iosKeyFileIsProduction = Env.getBoolean("push.ios.keyfile.production");
		
		TraceLog.debug("#### androidApiKey : " + androidApiKey);
		
		int sendCnt = 0;
		
		List<Map<String, Object>> pushQueueList = pushMsgDaoMybatis.selectPushQueueList(limit);
		
		for (Map<String, Object> pushQueue : pushQueueList) {
			try {
				String receiverMobileToken = (String) pushQueue.get("pushToken");
				String msg = (String) pushQueue.get("msg");
				String os = (String) pushQueue.get("os");
				String title = (String) pushQueue.get("title");
				
				if (StringUtils.equals(os, "301401")) {
					os = "ANDROID";
				} else if (StringUtils.equals(os, "301402")) {
					os = "IOS";
				}
				
				PushMessage pushMessage = new PushMessage(androidApiKey, iosKeyFilePath, iosKeyFilePassword, iosKeyFileIsProduction);
				Boolean sendResult = pushMessage.send(os, title, msg, receiverMobileToken);
				
				pushQueue.put("resultCode", sendResult ? "SUCC" : "FAIL");
				sendCnt += pushMsgDaoMybatis.insertPushMessage(pushQueue);
				
				pushMsgDaoMybatis.deletePushQueue((BigInteger) pushQueue.get("snId"));
			} catch(Exception e) {
				TraceLog.error("[PushSend ERROR!!! snId : " + pushQueue.get("snId") + "]");
				TraceLog.printStackTrace(e);
			}
		}
		
		return sendCnt;
	}
	
	public int insertPushQueue(Map<String, Object> paramMap) {
		return pushMsgDaoMybatis.insertPushQueue(paramMap);
	}
	
}
