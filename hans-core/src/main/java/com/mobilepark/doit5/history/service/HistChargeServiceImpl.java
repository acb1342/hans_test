package com.mobilepark.doit5.history.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.history.dao.HistChargeDao;
import com.mobilepark.doit5.history.model.HistCharge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.history.service
 * @Filename     : HistChargeServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
@Service
@Transactional
public class HistChargeServiceImpl extends AbstractGenericService<HistCharge, Long> implements HistChargeService {
	@Autowired
	private HistChargeDao histChargeDao;
	
	@Override
	protected GenericDao<HistCharge, Long> getGenericDao() {
		return histChargeDao;
	}

	@Override
	public Long searchCountByMonthly(String usid, String fromDate, String toDate) {
		return histChargeDao.searchCountByMonthly(usid, fromDate, toDate);
	}

	@Override
	public List<Map<String, Object>> searchByMonthly(String usid, String fromDate, String toDate, int page, int rowPerPage) {
		return histChargeDao.searchByMonthly(usid, fromDate, toDate, page, rowPerPage);
	}

}
