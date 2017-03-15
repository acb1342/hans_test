package com.mobilepark.doit5.elcg.dao;

import java.util.Map;

import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ChargerGroupDao.java
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

public interface ChargerGroupDao extends GenericDao<ChargerGroup, Long> {
	public Long selectChargerGroupName(String name, Long bdId, Long chargerGroupId);
	
	//public Map<String, Object> selectBdGroupId(String name, Long chargerGroupId);
	
	public ChargerGroup insertChargerGroup(ChargerGroup chargerGroup);
	
	public int updateChargerGroup(ChargerGroup chargerGroup, String usid);
	
	public Map<String, Object> selectGroupNameList(Long bdId);
	
	public Map<String, Object> selectChargerGroupDetail(Long chargerGroupId);
	
}
