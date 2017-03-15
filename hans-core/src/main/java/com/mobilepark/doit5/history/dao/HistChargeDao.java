package com.mobilepark.doit5.history.dao;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.history.model.HistCharge;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.history.dao
 * @Filename     : HistChargeDao.java
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
 * =================================================================================
 */
public interface HistChargeDao extends GenericDao<HistCharge, Long> {
	public Long searchCountByMonthly(String usid, String fromDate, String toDate);
	
	public List<Map<String, Object>> searchByMonthly(String usid, String fromDate, String toDate, int page, int rowPerPage);
}
