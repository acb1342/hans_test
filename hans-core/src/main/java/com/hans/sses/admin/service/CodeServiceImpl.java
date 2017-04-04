package com.hans.sses.admin.service;

import java.util.List;
import java.util.Map;

import com.hans.sses.admin.dao.CodeDaoMybatis;
import com.hans.sses.admin.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.admin.dao.AdminDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : CmsUserServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 5.      최초 버전
 * =================================================================================
 */
@Transactional
public class CodeServiceImpl extends AbstractGenericService<Admin, String> implements CodeService {
	@Autowired
	private AdminDao cmsUserDao;

	@Autowired
	private CodeDaoMybatis codeDaoMybatis;
	
	@Override
	protected GenericDao<Admin, String> getGenericDao() {
		return this.cmsUserDao;
	}

	@Override
	public int getCount(Map<String, Object> param) {
		return codeDaoMybatis.getCount(param);
	}
	
	@Override
	public List<Map<String, String>> getCodeList(Map<String, Object> param) {
		return codeDaoMybatis.getCodeList(param);
	}
	
	@Override
	public Map<String, Object> getCodeDetail(String id) {
		return codeDaoMybatis.getCodeDetail(id);
	}
	
	@Override
	public void CodeUpdate(Map<String, Object> param) {
		this.codeDaoMybatis.CodeUpdate(param);
	}
	
	@Override
	public void CodeCreate(Map<String, Object> param) {
		this.codeDaoMybatis.CodeCreate(param);
	}
	
	@Override
	public int CodeDelete(String id) {
		return codeDaoMybatis.CodeDelete(id);
	}
	
}
