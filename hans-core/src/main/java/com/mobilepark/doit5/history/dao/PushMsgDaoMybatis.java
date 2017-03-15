package com.mobilepark.doit5.history.dao;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mobilepark.doit5.common.Pagination;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.history.service
 * @Filename     : PushMsgDaoMybatis.java
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

@Repository
public class PushMsgDaoMybatis {
	
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	
	public Map<String, Object> selectUserMessageList(Integer page, Integer size, String usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Pagination pagination = new Pagination(page, size);
		
		
		paramMap.put("page", page);
		paramMap.put("size", size);
		paramMap.put("usid", usid);
		paramMap.put("pagination", pagination);
		
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("pushMsg.selectUserMessageList", paramMap);
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", (Integer) sqlSessionTemplate.selectOne("pushMsg.selectUserMessageTotalCnt", paramMap));
		resultMap.put("messageList", list);
		
		
		return resultMap;
	}
	
	public List<Map<String, Object>> selectPushQueueList(Integer limit) {
		return sqlSessionTemplate.selectList("pushMsg.selectPushQueueList", limit);
	}
	
	public int deletePushQueue(BigInteger snId) {
		return sqlSessionTemplate.delete("pushMsg.deletePushQueue", snId);
	}
	
	public int insertPushMessage(Map<String, Object> paramMap) {
		return sqlSessionTemplate.insert("pushMsg.insertPushMessage", paramMap);
	}
	
	public int insertPushQueue(Map<String, Object> paramMap) {
		return sqlSessionTemplate.insert("pushMsg.insertPushQueue", paramMap);
	}
	
}
