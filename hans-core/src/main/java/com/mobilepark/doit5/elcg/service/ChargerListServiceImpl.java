package com.mobilepark.doit5.elcg.service;

import java.util.Map;

import com.mobilepark.doit5.elcg.dao.ChargerListDao;
import com.mobilepark.doit5.elcg.model.ChargerList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerListServiceImpl.java
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
public class ChargerListServiceImpl extends AbstractGenericService<ChargerList, String> implements ChargerListService {
	
	@Autowired
	private ChargerListDao chargerListDao;

	@Override
	protected GenericDao<ChargerList, String> getGenericDao() {
		return this.chargerListDao;
	}
	
	@Override
	public Map<String, Object> getChargerInfo(String serialNo) throws Exception {
		return chargerListDao.selectChargerInfo(serialNo);
	}

}
