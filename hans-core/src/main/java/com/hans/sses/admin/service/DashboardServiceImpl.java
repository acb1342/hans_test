package com.hans.sses.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.admin.dao.AdminDao;
import com.hans.sses.admin.dao.DashboardDaoMybatis;
import com.hans.sses.admin.model.Admin;
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
public class DashboardServiceImpl extends AbstractGenericService<Admin, String> implements DashboardService {
	
	@Autowired
	private AdminDao cmsUserDao;
	
	@Autowired
	private DashboardDaoMybatis dashboardDaoMybatis;
	
	@Override
	protected GenericDao<Admin, String> getGenericDao() {
		return this.cmsUserDao;
	}

	@Override
	public List<Map<String, String>> getEnergyList() {
		return dashboardDaoMybatis.getEnergyList();
	}
	
	
}
