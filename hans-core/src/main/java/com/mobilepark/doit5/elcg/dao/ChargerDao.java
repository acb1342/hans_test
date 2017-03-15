package com.mobilepark.doit5.elcg.dao;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Charger;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.model.Pageable;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ChargerDao.java
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

public interface ChargerDao extends GenericDao<Charger, String> {
	public Map<String, Object> selectChargerList(Integer adminGroupId, String adminId);
	
	public Map<String, Object> selectChargerDetail(String chargerId);
	
	public Charger insertCharger(Charger charger);

	public int updateCharger(Charger charger, String usid);

	List<Charger> searchByDate(Charger charger, int page, int rowPerPage, String sortCriterion, String sortDirection,
								String fromDate, String toDate);
	
	public int getChargerCount(String adminId);
	public List<Charger> getChargerList(String adminId, Pageable pageable);
}
