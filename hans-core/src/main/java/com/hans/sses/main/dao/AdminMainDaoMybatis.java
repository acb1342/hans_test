package com.hans.sses.main.dao;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.main.service
 * @Filename     : AdminMainDaoMybatis.java
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
public class AdminMainDaoMybatis {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectOwnerMain(String adminId)  throws Exception {
		return (Map<String, Object>) sqlSessionTemplate.selectOne("adminMain.selectOwnerMain", adminId);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectWorkerMain(String adminId)  throws Exception {
		
		Map<String, Object> resultMap = (Map<String, Object>) sqlSessionTemplate.selectOne("adminMain.selectWorkerMain", adminId);
		List<Object>  applList = (List<Object>) sqlSessionTemplate.selectList("adminMain.selectStationApplcation", adminId);
		List<Object>  brokList = (List<Object>) sqlSessionTemplate.selectList("adminMain.selectBrokenReport", adminId);
		
		resultMap.put("applList", applList);
		resultMap.put("brokList", brokList);
		
		return resultMap;
	}
}
