package com.mobilepark.doit5.admin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.dao.AdminGroupAuthDao;
import com.mobilepark.doit5.admin.dao.AdminGroupDao;
import com.mobilepark.doit5.admin.dao.AdminGroupDaoMybatis;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.AdminGroupAuth;

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
public class AdminGroupServiceImpl implements AdminGroupService {//extends AbstractGenericService<AdminGroup, Integer> implements AdminGroupService {
	@Autowired
	private AdminGroupDao adminGroupDao;

	@Autowired
	private AdminGroupAuthDao cmsGroupAuthDao;
	
	@Autowired
	private AdminGroupDaoMybatis adminGroupDaoMybatis;

	/*@Override
	protected GenericDao<AdminGroup, Integer> getGenericDao() {
		return this.adminGroupDao;
	}*/
	
	@Override
	public int count(Map<String, Object> param) {
		return this.adminGroupDaoMybatis.count(param);
	}
	
	@Override
	public List<Map<String, Object>> search(Map<String, Object> param) {
		return this.adminGroupDaoMybatis.search(param);
	}
	
	@Override
	public Map<String, Object> get(Integer id) {
		return this.adminGroupDaoMybatis.get(id);
	}

	@Override
	public int create(Map<String, Object> param) {
		param.put("regDate", new Date());
		return this.adminGroupDaoMybatis.create(param);
	}
	
	@Override
	public int update(Map<String, Object> param) {
		param.put("modDate", new Date());
		return this.adminGroupDaoMybatis.update(param);
	}

	@Override
	public int delete(Integer id) {
		return this.adminGroupDaoMybatis.delete(id);
	}
	
	@Override
	public boolean updateAuth(Map<String, Object> param) {
		int updateCount = 0;
		int groupId = Integer.parseInt(param.get("id").toString());
		
		Map<String, Object> groupAuthParam = new HashMap<String, Object>();
		groupAuthParam.put("groupId", groupId);
		
		Set<String> menuIds = param.keySet();
		Iterator<String> iterator = menuIds.iterator();
		while (iterator.hasNext()) {
			try {
				int menuId = Integer.parseInt(iterator.next().toString());
				groupAuthParam.put("menuId", menuId);
				groupAuthParam.put("auth", param.get(String.valueOf(menuId)));
				
				Map<String, Object> groupAuth = this.getGroupAuth(groupAuthParam);
				if (groupAuth == null) {
					groupAuthParam.put("regDate", new Date());
					this.adminGroupDaoMybatis.createGroupAuth(groupAuthParam);
				}
				else {
					groupAuthParam.put("modDate", new Date());
					this.adminGroupDaoMybatis.updateGroupAuth(groupAuthParam);
				}
				updateCount++;
				//TraceLog.debug(" ======> [%s] : [%s]", menuId, param.get(menuId));
			} catch(Exception e) {
				//iterator.remove();
			}
		}
		
		return (updateCount > 0);
	}

	@Override
	public List<AdminGroupAuth> searchGroupAuth(Integer groupId) {
		return this.cmsGroupAuthDao.searchGroupAuth(groupId);
	}

	@Override
	public int deleteGroupAuth(Integer groupId) {
		return this.adminGroupDaoMybatis.deleteGroupAuth(groupId);
	}

	@Override
	public AdminGroup getByName(String name) {
		return this.adminGroupDao.getByName(name);
	}
	
	@Override
	public List<Map<String, Object>> getAllGroupAuth(Integer id) {
		return this.adminGroupDaoMybatis.getAllGroupAuth(id);
	}
	
	public Map<String, Object> getGroupAuth(Map<String, Object> param) {
		return this.adminGroupDaoMybatis.getGroupAuth(param);
		
	}
	
}
