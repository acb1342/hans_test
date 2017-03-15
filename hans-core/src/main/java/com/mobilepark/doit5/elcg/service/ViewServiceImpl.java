package com.mobilepark.doit5.elcg.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.mobilepark.doit5.elcg.dao.ViewDao;
import com.mobilepark.doit5.elcg.model.ViewCustCenter;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ViewServiceImpl.java
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

public class ViewServiceImpl extends AbstractGenericService<ViewCustCenter, Long> implements ViewService {
	
	@Autowired
	private ViewDao viewDao;

	@Override
	protected GenericDao<ViewCustCenter, Long> getGenericDao() {
		return this.viewDao;
	}
	
	
}
