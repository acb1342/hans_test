package com.mobilepark.doit5.bill.dao;

import java.util.Map;

import com.mobilepark.doit5.bill.model.BdPeriod;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      	 : evc-core
 * @Package      : com.mobilepark.doit5.bill.dao
 * @Filename     : BdCalcdayDao.java
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

public interface BdPeriodDao extends GenericDao<BdPeriod, Long> {
	public Map<String, Object> selectBdCalcdayDetail(Long bdId);
	
	public BdPeriod insertBdCalcday(BdPeriod bdCalcday);
	
	public int updateBdCalcday(BdPeriod bdCalcday);
	
}
