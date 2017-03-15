package com.mobilepark.doit5.customer.dao;

import java.util.Map;

import com.mobilepark.doit5.customer.model.ChargeCond;
import com.uangel.platform.dao.GenericDao;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.dao
 * @Filename     : ChargeCondDao.java
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
public interface ChargeCondDao extends GenericDao<ChargeCond, Long>{
	public ChargeCond insertChargeCond(ChargeCond chargeCond);
	
	public Map<String, Object> selectChargeCondDetail(Long usid);
	
	public void updateChargeCond(ChargeCond chargeCond);
}
