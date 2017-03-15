package com.mobilepark.doit5.board.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.board.dao.AppVerDaoMybatis;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service
 * @Filename     : AppVerServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 14.      최초 버전
 * =================================================================================*/


@Transactional
public class AppVerServiceImpl implements AppVerService {
	@Autowired
	private AppVerDaoMybatis appVerDaoMybatis;
	
	public int count(Map<String, Object> param) {
		return this.appVerDaoMybatis.count(param);
	}
	
	public List<Map<String, Object>> search(Map<String, Object> param) {
		return this.appVerDaoMybatis.search(param);
	}
	
	public Map<String, Object> get(Long id) {
		return this.appVerDaoMybatis.get(id);
	}
	
	public void create(Map<String, Object> param) {
		this.appVerDaoMybatis.create(param);
	}
	
	public void update(Map<String, Object> param) {
		this.appVerDaoMybatis.update(param);
	}
	
	public int delete(Long id) {
		return this.appVerDaoMybatis.delete(id);
	}

	public Map<String, Object> getAppVer_api(String ver, String clientType, String targetType) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>(); 
		
		if (StringUtils.isBlank(ver)) {
			resultMap.put("errorMsg", "필수 파라미터가 존재하지 않습니다.");
			return resultMap;
		}
		
		param.put("ver", ver);
		param.put("os", "ANDROID".equals(clientType)? "301401" : ("IOS".equals(clientType)? "301402" : "301403"));
		param.put("targetType", targetType);
		
		resultMap = appVerDaoMybatis.getAppVer_api(param);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
			
			resultMap.put("ver", ver);
			resultMap.put("verYn", "");
			resultMap.put("updateUrl", "");
		}
		
		return resultMap;
	}
	
}
