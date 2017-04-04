package com.hans.sses.login.service;

import com.hans.sses.login.dao.AdminLoginDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.login.service
 * @Filename     : AdminLoginService.java
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
public class AdminLoginService {
	
	
	@Autowired
	private AdminLoginDaoMybatis adminLoginDaoMybatis;


	public boolean resetPasswd(String currPasswd, String newPasswd, String adminId)  throws Exception{
		return adminLoginDaoMybatis.updateResetPasswd(currPasswd, newPasswd, adminId);
	}

	
}
