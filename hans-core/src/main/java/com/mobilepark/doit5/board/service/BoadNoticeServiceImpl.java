package com.mobilepark.doit5.board.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.board.dao.BoadNoticeDao;
import com.mobilepark.doit5.board.model.BoadNotice;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service
 * @Filename     : BoadNoticeServiceImpl.java
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
 

@Transactional
public class BoadNoticeServiceImpl extends AbstractGenericService<BoadNotice, Long> implements BoadNoticeService {
	
	@Autowired
	private BoadNoticeDao boadNoticeDao;

	@Override
	protected GenericDao<BoadNotice, Long> getGenericDao() {
		return this.boadNoticeDao;
	}

	@Override
	public List<BoadNotice> search(BoadNotice boadNotice, int page, int rowPerPage, String sortCriterion,
			String sortDirection, String fromDate, String toDate) {
		
		return this.boadNoticeDao.search(boadNotice, page, rowPerPage, sortCriterion, sortDirection, fromDate, toDate);
	}


}
