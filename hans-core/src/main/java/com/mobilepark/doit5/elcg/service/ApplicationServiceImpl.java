package com.mobilepark.doit5.elcg.service;

import com.mobilepark.doit5.elcg.model.StationApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.elcg.dao.StationApplicationDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerIftmpService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 14.      최초 버전
 * =================================================================================*/

@Transactional
public class ApplicationServiceImpl extends AbstractGenericService<StationApplication, Long> implements ApplicationService {

	@Autowired
	private StationApplicationDao stationApplicationDao;
	
	@Override
	protected GenericDao<StationApplication, Long> getGenericDao() {
		return this.stationApplicationDao;
	}
}
