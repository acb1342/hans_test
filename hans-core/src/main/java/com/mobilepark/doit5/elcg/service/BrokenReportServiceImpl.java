package com.mobilepark.doit5.elcg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.elcg.dao.BrokenReportDao;
import com.mobilepark.doit5.elcg.model.BrokenReport;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerIftmpService.java
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
public class BrokenReportServiceImpl extends AbstractGenericService<BrokenReport, Long> implements BrokenReportService {

	@Autowired
	private BrokenReportDao brokenReportDao;
	
	@Override
	protected GenericDao<BrokenReport, Long> getGenericDao() {
		return this.brokenReportDao;
	}
}
