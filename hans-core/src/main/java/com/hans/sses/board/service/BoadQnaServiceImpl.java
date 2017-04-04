package com.hans.sses.board.service;


import java.util.List;

import com.hans.sses.board.model.BoadQna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.board.dao.BoadQnaDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service
 * @Filename     : BoadQnaServiceImpl.java
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
public class BoadQnaServiceImpl extends AbstractGenericService<BoadQna, Long> implements BoadQnaService {
	
	@Autowired
	private BoadQnaDao boadQnaDao;

	@Override
	protected GenericDao<BoadQna, Long> getGenericDao() {
		return this.boadQnaDao;
	}

	@Override
	public List<BoadQna> searchQnaAllKeys(BoadQna boadQna, String searchValue, int pageNum, int rowPerPage, String fromDate, String toDate) {
		return this.boadQnaDao.searchQnaAllKeys(boadQna, searchValue, pageNum, rowPerPage, fromDate, toDate);
	}

	@Override
	public List<BoadQna> searchQna(BoadQna boadQna, int page, int rowPerPage, String sortCriterion,
										String sortDirection, String fromDate, String toDate) {
		return this.boadQnaDao.searchQna(boadQna, page, rowPerPage, sortCriterion, sortDirection, fromDate, toDate);
	}

	

}
