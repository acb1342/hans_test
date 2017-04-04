package com.hans.sses.board.service.oper;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service
 * @Filename     : BoadNoticeAdminServiceImpl.java
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

import com.hans.sses.board.dao.oper.BoadNoticeAdminDao;
import com.hans.sses.board.model.oper.BoadNoticeAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
 

@Transactional
public class BoadNoticeAdminServiceImpl extends AbstractGenericService<BoadNoticeAdmin, Long> implements BoadNoticeAdminService {
	
	@Autowired
	private BoadNoticeAdminDao noticeAdminDao;

	@Override
	protected GenericDao<BoadNoticeAdmin, Long> getGenericDao() {
		return this.noticeAdminDao;
	}	
}
