package com.mobilepark.doit5.push.service;

import com.mobilepark.doit5.push.model.PushButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.push.dao.PushButtonDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.service
 * @Filename     : PushButtonServiceImpl.java
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
public class PushButtonServiceImpl extends AbstractGenericService<PushButton, Integer> implements PushButtonService {
	@Autowired
	private PushButtonDao pushButtonDao;

	@Override
	protected GenericDao<PushButton, Integer> getGenericDao() {
		return this.pushButtonDao;
	}
}
