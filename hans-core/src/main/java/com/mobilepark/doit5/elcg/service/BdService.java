package com.mobilepark.doit5.elcg.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : BdService.java
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

public interface BdService extends GenericService<Bd, Long>{
	public Map<String, Object> getFavoriteList(Long usid);

	public List<Bd> searchBdName(Bd bd, String search);
	
	public Map<String, Object> getBuildingList(String searchKeyword, String usid, Integer adminGroupId);
	
	public Map<String, Object> getBuildingDetail(Long bdId, String usid);
	
	public void updateBdCalcdayl(Map<String, Object> map, String usid);
	
	//public Map<String, Object> getWkBuildingList(String userId);
	
	public List<Bd> getInstallerBdList(String bdGroupId, String wkUsid);
	
	public List<BdGroup> getInstallerBdGroupList(String bdGroupName, String wkUsid);
}
