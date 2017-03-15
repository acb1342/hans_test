package com.mobilepark.doit5.client.service;

import com.mobilepark.doit5.client.dao.NsuAppsLibDao;
import com.mobilepark.doit5.client.model.NsuAppsLib;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : NsuAppsLibServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
@Transactional
public class NsuAppsLibServiceImpl extends AbstractGenericService<NsuAppsLib, Integer> implements NsuAppsLibService {
	@Autowired
	private NsuAppsLibDao nsuAppsLibDao;

	@Override
	protected GenericDao<NsuAppsLib, Integer> getGenericDao() {
		return this.nsuAppsLibDao;
	}
}
