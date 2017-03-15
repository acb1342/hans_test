package com.mobilepark.doit5.elcg.dao;

import java.util.Map;

import com.mobilepark.doit5.elcg.model.ChargerList;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ChargerListDao.java
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

public interface ChargerListDao extends GenericDao<ChargerList, String> {
	public Map<String, Object> selectChargerInfo(String serialNo) throws Exception;
	
	public Map<String, Object> selectChargerSerialNo(String serialNo);
	
	public int updateChargerStatus(ChargerList chargerList);
}
