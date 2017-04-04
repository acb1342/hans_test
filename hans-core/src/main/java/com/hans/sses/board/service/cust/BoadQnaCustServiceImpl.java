package com.hans.sses.board.service.cust;

import com.hans.sses.board.dao.cust.BoadQnaCustDao;
import com.hans.sses.board.model.cust.BoadQnaCust;
import org.springframework.beans.factory.annotation.Autowired;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;


/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service.cust
 * @Filename     : BoadQnaCustServiceImpl.java
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


public class BoadQnaCustServiceImpl extends AbstractGenericService<BoadQnaCust, Long> implements BoadQnaCustService {

	@Autowired
	private BoadQnaCustDao boadQnaCustDao;
	
	@Override
	protected GenericDao<BoadQnaCust, Long> getGenericDao() {
		return this.boadQnaCustDao;
	}
	
}
