package com.hans.sses.energy.service;

import com.hans.sses.admin.dao.AdminDao;
import com.hans.sses.admin.dao.EnergyDaoMybatis;
import com.hans.sses.admin.model.Admin;
import com.hans.sses.admin.service.EnergyService;
import com.hans.sses.energy.dao.SimulationDaoMybatis;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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
public class SimulationServiceImpl implements SimulationService {


	@Autowired
	private SimulationDaoMybatis simulationDaoMybatis;

	@Override
	public List<Map<String, Object>> getEnergyList(Map<String, Object> params) {
		return simulationDaoMybatis.getEnergyList(params);

	}
}
