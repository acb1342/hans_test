package com.mobilepark.doit5.client.service;

import java.util.List;

import com.mobilepark.doit5.client.dao.ApplicationDao;
import com.mobilepark.doit5.client.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.service
 * @Filename     : ApplicationServiceImpl.java
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 *
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
@Transactional
public class ApplicationServiceImpl extends AbstractGenericService<Application, Integer> implements ApplicationService {
	@Autowired
	private ApplicationDao applicationDao;

	@Override
	protected GenericDao<Application, Integer> getGenericDao() {
		return this.applicationDao;
	}

	@Override
	public Application getByAppName(String appName) {
		return this.applicationDao.getByAppName(appName);
	}

	@Override
	public Application getByAppId(String appId) {
		return this.applicationDao.getByAppId(appId);
	}

	@Override
	public List<Application> getApps(String appId) {
		return this.applicationDao.getApps(appId);
	}

	@Override
	public List<Application> getAppsByCpId(String cpId) {
		return this.applicationDao.getAppsByCpId(cpId);
	}

	@Override
	public Application getByCpId(String cpId) {
		return this.applicationDao.getByCpId(cpId);
	}

	@Override
	public Application getByCpIdAppId(String cpId, String appId) {
		return this.applicationDao.getByCpIdAppId(cpId, appId);
	}

	@Override
	public List<Application> searchByCondition(Application application, String cpId, String appId, String os) {
		return this.applicationDao.searchByCondition(application, cpId, appId, os);
	}

	@Override
	public int searchCountByCondition(Application application, String cpId, String appId, String os) {
		return this.applicationDao.searchCountByCondition(application, cpId, appId, os);
	}

	@Override
	public List<Application> getListByCpId(String cpId) {
		return this.applicationDao.getListByCpId(cpId);
	}
}
