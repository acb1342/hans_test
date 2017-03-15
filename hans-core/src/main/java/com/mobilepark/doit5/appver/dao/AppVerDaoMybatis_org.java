package com.mobilepark.doit5.appver.dao;

import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.appver.service
 * @Filename     : AppVerDaoMybatis.java
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
public class AppVerDaoMybatis_org {
	
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	
	public Map<String, Object> selectAppVer(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectOne("appVer.selectAppVer", paramMap);
	}
	
}
