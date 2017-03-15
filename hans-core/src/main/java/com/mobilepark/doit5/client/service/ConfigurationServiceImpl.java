package com.mobilepark.doit5.client.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.client.dao.ConfigurationDao;
import com.mobilepark.doit5.client.model.Configuration;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : SecurityKeyServiceImpl.java
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
public class ConfigurationServiceImpl extends AbstractGenericService<Configuration, String> implements ConfigurationService {

	@Autowired
	private ConfigurationDao configurationDao;

	@Override
	protected GenericDao<Configuration, String> getGenericDao() {
		return this.configurationDao;
	}

}
