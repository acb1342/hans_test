package com.mobilepark.doit5.content.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.content.dao.ContentMsgEventDao;
import com.mobilepark.doit5.content.model.ContentMsgEvent;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.content.service
 * @Filename     : ContentMsgEventServiceImpl.java
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
public class ContentMsgEventServiceImpl extends AbstractGenericService<ContentMsgEvent, Integer> implements ContentMsgEventService {
	@Autowired
	private ContentMsgEventDao contentMsgEventDao;

	@Override
	protected GenericDao<ContentMsgEvent, Integer> getGenericDao() {
		return this.contentMsgEventDao;
	}
}
