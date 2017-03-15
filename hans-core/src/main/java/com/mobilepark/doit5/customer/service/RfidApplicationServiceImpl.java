package com.mobilepark.doit5.customer.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.dao.AdminElcgDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.customer.dao.RfidApplicationDao;
import com.mobilepark.doit5.customer.model.RfidApplication;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : RfidApplicationServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
@Transactional
public class RfidApplicationServiceImpl extends AbstractGenericService<RfidApplication, Long>
		implements RfidApplicationService {
	
	@Autowired
	private RfidApplicationDao rfidApplicationDao;
	
	@Autowired
	private AdminElcgDaoMybatis adminElcgDaoMybatis;
	

	@Override
	protected GenericDao<RfidApplication, Long> getGenericDao() {
		return rfidApplicationDao;
	}


	@Override
	public Long searchCountByMemberName(String name) {
		return rfidApplicationDao.searchCountByMemberName(name);
	}


	@Override
	public List<RfidApplication> searchByMemberName(String name, int page, int rowPerPage) {
		return rfidApplicationDao.searchByMemberName(name, page, rowPerPage);
	}
	
	@Override
	public Map<String, Object> getRfidCard(Map<String, Object> param) {
		return adminElcgDaoMybatis.getRfidCard(param);
	}
}
