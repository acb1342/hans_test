package com.mobilepark.doit5.appver.service;

import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.appver.dao.AppVerDaoMybatis_org;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.appver.service
 * @Filename     : AppVerService.java
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
public class AppVerService {
	
	
	@Autowired
	private AppVerDaoMybatis_org appVerDaoMybatis;


	public Map<String, Object> getAppVer(String ver, String clientType, String targetType) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("ver", ver);
		paramMap.put("targetType", targetType);
		paramMap.put("os", "ANDROID".equals(clientType)? "301401" : "301402");
		
		Map<String, Object> resultMap = appVerDaoMybatis.selectAppVer(paramMap);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
			
			resultMap.put("ver", ver);
			resultMap.put("verYn", "");
			resultMap.put("updateUrl", "");
		}
		
		return appVerDaoMybatis.selectAppVer(paramMap);
	}

	
}
