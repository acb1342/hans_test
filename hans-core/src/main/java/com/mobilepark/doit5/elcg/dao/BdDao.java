package com.mobilepark.doit5.elcg.dao;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Bd;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : BdDao.java
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

public interface BdDao extends GenericDao<Bd, Long> {
	public Map<String, Object> selectFavoriteList(Long usId);

	public Map<String, Object> getBuildingList(String searchKeyword, String usid, Integer adminGroupId);
	
	public Map<String, Object> getBuildingDetail(Long bdId, String usid);

	List<Bd> searchBdName(Bd bd, String search);
	
	//public Map<String, Object> selectWkBuildingList(String userId);
}
