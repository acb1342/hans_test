package com.mobilepark.doit5.elcg.dao;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.BdGroup;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : BdGroupDao.java
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

public interface BdGroupDao extends GenericDao<BdGroup, Long> {
	public List<BdGroup> selectBdGroupList(String searchKeyword, List<String> bdIdList);
	
	public BdGroup selectBdGroupDetail(Long bdGroupId);
	
	public Map<String, Object> selectOwnerMain(String adminId);

	List<BdGroup> searchBdGroupName(BdGroup bdGroup, String search);
}
