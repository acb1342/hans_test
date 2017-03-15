package com.mobilepark.doit5.client.service;

import com.mobilepark.doit5.client.model.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.client.dao.AgentDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.service
 * @Filename     : AgentServiceImpl.java
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
public class AgentServiceImpl extends AbstractGenericService<Agent, Integer> implements AgentService {
	@Autowired
	private AgentDao agentDao;

	@Override
	protected GenericDao<Agent, Integer> getGenericDao() {
		return this.agentDao;
	}
}
