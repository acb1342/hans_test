package com.hans.sses.route.service;

import com.hans.sses.route.dao.RouteRuleDao;
import com.hans.sses.route.model.RouteRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : RouteRuleServiceImpl.java
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
public class RouteRuleServiceImpl extends AbstractGenericService<RouteRule, String> implements RouteRuleService {
	@Autowired
	private RouteRuleDao routeRuleDao;

	@Override
	protected GenericDao<RouteRule, String> getGenericDao() {
		return this.routeRuleDao;
	}
}
