package com.mobilepark.doit5.provider.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.provider.dao.ContentProviderDao;
import com.mobilepark.doit5.provider.model.ContentProvider;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.service
 * @Filename     : ContentProviderServiceImpl.java
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
public class ContentProviderServiceImpl extends AbstractGenericService<ContentProvider, Integer> implements ContentProviderService {
	@Autowired
	private ContentProviderDao contentProviderDao;

	@Override
	protected GenericDao<ContentProvider, Integer> getGenericDao() {
		return this.contentProviderDao;
	}

	@Override
	public ContentProvider getById(String id) {
		return this.contentProviderDao.getById(id);
	}
}
