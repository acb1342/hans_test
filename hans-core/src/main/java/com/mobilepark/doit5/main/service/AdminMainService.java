package com.mobilepark.doit5.main.service;

import java.util.Map;

import com.mobilepark.doit5.main.dao.AdminMainDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.main.service
 * @Filename     : AdminMainService.java
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
public class AdminMainService {
	
	
	@Autowired
	private AdminMainDaoMybatis adminMainDaoMybatis;


	public Map<String, Object> selectOwnerMain(String adminId)  throws Exception{
		return adminMainDaoMybatis.selectOwnerMain(adminId);
	}
	
	public Map<String, Object> selectWorkerMain(String adminId)  throws Exception{
		return adminMainDaoMybatis.selectWorkerMain(adminId);
	}

	
}
