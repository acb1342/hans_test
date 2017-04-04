package com.hans.sses.admin.service;

import java.util.List;
import java.util.Map;

import com.hans.sses.admin.dao.AdminDao;
import com.hans.sses.admin.dao.AdminDaoMybatisTest;
import com.hans.sses.admin.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

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
public class AdminServiceImpl extends AbstractGenericService<Admin, String> implements AdminService {
	@Autowired
	private AdminDao cmsUserDao;

	@Autowired
	private AdminDaoMybatisTest adminDaoMybatisTest;
	
	@Override
	protected GenericDao<Admin, String> getGenericDao() {
		return this.cmsUserDao;
	}

	@Override
	public List<Map<String, Object>> searchByGroup(Integer groupId) {
		return this.adminDaoMybatisTest.searchByGroup(groupId);
	}

	@Override
	public int searchCountByGroup(Integer groupId) {
		return this.cmsUserDao.searchCountByGroup(groupId);
	}

	@Override
	public List<Admin> searchRelatedCp(String mcpId) {
		return this.cmsUserDao.searchRelatedCp(mcpId);
	}

	@Override
	public List<Admin> searchByGroupName(String name) {
		return this.cmsUserDao.searchByGroupName(name);
	}

	@Override
	public int searchCountByGroupName(String name) {
		return this.cmsUserDao.searchCountByGroupName(name);
	}

	@Override
	public List<Admin> searchByGroupName(String groupName1, String groupName2) {
		return this.cmsUserDao.searchByGroupName(groupName1, groupName2);
	}

	@Override
	public int searchCountByGroupName(String groupName1, String groupName2) {
		return this.cmsUserDao.searchCountByGroupName(groupName1, groupName2);
	}

	@Override
	public List<Admin> searchByMCPName(String mcpId) {
		return this.cmsUserDao.searchByMCPName(mcpId);
	}

	@Override
	public Admin getById(String id) {
		return this.cmsUserDao.getById(id);
	}

	@Override
	public Admin getMybatis(String id) {
		return adminDaoMybatisTest.getAdmin(id);
	}
	
	@Override
	public int getCount(Map<String, Object> param) {
		return adminDaoMybatisTest.getCount(param);
	}
	
	@Override
	public List<Map<String, String>> getAdminList(Map<String, Object> param) {
		return adminDaoMybatisTest.getAdminList(param);
	}
	
	@Override
	public Map<String, Object> getAdminDetail(String id) {
		return adminDaoMybatisTest.getAdminDetail(id);
	}
	
	@Override
	public void AdminUpdate(Map<String, Object> param) {
		this.adminDaoMybatisTest.AdminUpdate(param);
	}
	
	@Override
	public void AdminPasswdUpdate(Map<String, Object> param) {
		this.adminDaoMybatisTest.AdminPasswdUpdate(param);
	}
	
	@Override
	public void AdminCreate(Map<String, Object> param) {
		this.adminDaoMybatisTest.AdminCreate(param);
	}
	
	@Override
	public int AdminDelete(String id) {
		return adminDaoMybatisTest.AdminDelete(id);
	}
	
	@Override
	public List<Map<String, Object>> selectAdminGroup() {
		return adminDaoMybatisTest.selectAdminGroup();
	}
	
}
