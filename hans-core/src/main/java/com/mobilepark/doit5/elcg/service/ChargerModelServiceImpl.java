package com.mobilepark.doit5.elcg.service;

import com.mobilepark.doit5.elcg.dao.ChargerModelDao;
import com.mobilepark.doit5.elcg.model.ChargerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerModelServiceImpl.java
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
public class ChargerModelServiceImpl extends AbstractGenericService<ChargerModel, Long> implements ChargerModelService {
	
	@Autowired
	private ChargerModelDao chargerModelDao;

	@Override
	protected GenericDao<ChargerModel, Long> getGenericDao() {
		return this.chargerModelDao;
	}

}
