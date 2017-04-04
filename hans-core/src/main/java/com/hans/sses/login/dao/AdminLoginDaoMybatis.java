package com.hans.sses.login.dao;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.HexUtil;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.login.service
 * @Filename     : AdminLoginDaoMybatis.java
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
public class AdminLoginDaoMybatis {
	
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	
	public boolean updateResetPasswd(String currPasswd, String newPasswd, String adminId)  throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("adminId", adminId);
		
		String passwd = (String) sqlSessionTemplate.selectOne("adminLogin.selectPasswd", paramMap);
		
		String encCurrPassword = "";
		
		if (StringUtils.isNotEmpty(currPasswd)) {
			encCurrPassword = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, currPasswd.getBytes("utf-8")));
		}
		
		
		boolean isPasswd = false;
		
		
		if (StringUtils.isNotEmpty(currPasswd)) {
			paramMap.put("newPasswd", HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, newPasswd.getBytes("utf-8"))));
		} 
		
		if(encCurrPassword.equals(passwd)) {
			isPasswd = true;
			
			sqlSessionTemplate.update("adminLogin.updatePasswd", paramMap);
		}
		
		
		return isPasswd;
	}
	
}
