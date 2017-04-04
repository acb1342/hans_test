package com.hans.sses.board.service.cust;


import com.hans.sses.board.model.cust.BoadNoticeCust;
import com.hans.sses.board.dao.cust.BoadNoticeCustDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
 

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service.cust
 * @Filename     : BoadNoticeCustServiceImpl.java
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
public class BoadNoticeCustServiceImpl extends AbstractGenericService<BoadNoticeCust, Long> implements BoadNoticeCustService {
	
	@Autowired
	private BoadNoticeCustDao boadNoticeCustDao;

	@Override
	protected GenericDao<BoadNoticeCust, Long> getGenericDao() {
		return this.boadNoticeCustDao;
	}

}
