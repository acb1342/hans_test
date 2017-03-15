package com.mobilepark.doit5.sender.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.sender.dao.SenderInfoDao;
import com.mobilepark.doit5.sender.model.SenderInfo;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : SenderInfoServiceImpl.java
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
public class SenderInfoServiceImpl extends AbstractGenericService<SenderInfo, String> implements SenderInfoService {
	@Autowired
	private SenderInfoDao senderInfoDao;

	@Override
	protected GenericDao<SenderInfo, String> getGenericDao() {
		return this.senderInfoDao;
	}
}
