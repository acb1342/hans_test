package com.mobilepark.doit5.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.dao.AdminDao;
import com.mobilepark.doit5.admin.dao.EnergyDaoMybatis;
import com.mobilepark.doit5.admin.model.Admin;
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
public class EnergyServiceImpl extends AbstractGenericService<Admin, String> implements EnergyService {
	
	@Autowired
	private AdminDao cmsUserDao;
	
	@Autowired
	private EnergyDaoMybatis energyDaoMybatis;
	
	@Override
	protected GenericDao<Admin, String> getGenericDao() {
		return this.cmsUserDao;
	}

	@Override
	public int getCount(Map<String, Object> param) {
		return energyDaoMybatis.getCount(param);
	}
	
	@Override
	public List<Map<String, String>> getEnergyList(Map<String, Object> param) {
		return energyDaoMybatis.getEnergyList(param);
	}

	@Override
	public void EnergyCreate(Map<String, Object> param) {
		this.energyDaoMybatis.EnergyCreate(param);
	}
	
	/*@Override
	public List<Map<String, String>> getDayEnergyList(String beforday, String afterday) {
		return energyDaoMybatis.getDayEnergyList(beforday, afterday);
	}*/

	@Override
	public List<Map<String, String>> getDayEnergyList(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
	/*
	@Override
	public Map<String, Object> getEnergyDetail(String id) {
		return energyDaoMybatis.getEnergyDetail(id);
	}
	
	@Override
	public void EnergyUpdate(Map<String, Object> param) {
		this.energyDaoMybatis.EnergyUpdate(param);
	}
	
	@Override
	public int EnergyDelete(String id) {
		return energyDaoMybatis.EnergyDelete(id);
	}
	*/
}
