package com.mobilepark.doit5.subscriber.service;

import java.util.List;

import com.mobilepark.doit5.subscriber.model.EndUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.subscriber.dao.EndUserDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.service
 * @Filename     : EndUserServiceImpl.java
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
public class EndUserServiceImpl extends AbstractGenericService<EndUser, Integer> implements EndUserService {
	@Autowired
	private EndUserDao endUserDao;

	@Override
	protected GenericDao<EndUser, Integer> getGenericDao() {
		return this.endUserDao;
	}

	@Override
	public int getCountByAppId(String appId) {
		// TODO Auto-generated method stub
		return this.endUserDao.getCountByAppId(appId);
	}

	@Override
	public List<EndUser> searchByCondition(String type, String mdn, String pushToken, int pageNum, int rowPerPage) {
		return this.endUserDao.searchByCondition(type, mdn, pushToken, pageNum, rowPerPage);
	}

	@Override
	public int searchCountByCondition(String type, String mdn, String pushToken) {
		return this.endUserDao.searchCountByCondition(type, mdn, pushToken);
	}
}
