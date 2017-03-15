package com.mobilepark.doit5.admin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mobilepark.doit5.admin.dao.AdminGroupDao;
import com.mobilepark.doit5.admin.model.AdminGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.dao.AdminGroupAuthDao;
import com.mobilepark.doit5.admin.model.AdminGroupAuth;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : CmsGroupServiceImpl.java
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
public class AdminGroupServiceImpl extends AbstractGenericService<AdminGroup, Integer> implements AdminGroupService {
	@Autowired
	private AdminGroupDao adminGroupDao;

	@Autowired
	private AdminGroupAuthDao cmsGroupAuthDao;

	@Override
	protected GenericDao<AdminGroup, Integer> getGenericDao() {
		return this.adminGroupDao;
	}

	@Override
	public AdminGroupAuth getGroupAuth(Integer groupId, Integer menuId) {
		return this.cmsGroupAuthDao.get(groupId, menuId);
	}

	@Override
	public int updateAuth(Integer groupId, Map<Integer, String> groupAuthMap) {
		int updateCount = 0;

		Set<Integer> menuIds = groupAuthMap.keySet();
		for (Integer menuId : menuIds) {
			AdminGroupAuth groupAuth = this.cmsGroupAuthDao.get(groupId, menuId);
			String authority = groupAuthMap.get(menuId);
			if (groupAuth == null) {
				groupAuth = new AdminGroupAuth(groupId, menuId);
				groupAuth.setAuth(authority);
				groupAuth.setFstRgUsid("");
				groupAuth.setFstRgDt(new Date());
				this.cmsGroupAuthDao.create(groupAuth);
			} else {
				groupAuth.setAuth(authority);
				groupAuth.setLstChDt(new Date());
				this.cmsGroupAuthDao.update(groupAuth);
			}
			updateCount++;
		}

		return updateCount;
	}

	@Override
	public List<AdminGroupAuth> searchGroupAuth(Integer groupId) {
		return this.cmsGroupAuthDao.searchGroupAuth(groupId);
	}

	@Override
	public int deleteGroupAuth(Integer groupId) {
		return this.cmsGroupAuthDao.deleteGroupAuth(groupId);
	}

	@Override
	public AdminGroup getByName(String name) {
		return this.adminGroupDao.getByName(name);
	}
}
