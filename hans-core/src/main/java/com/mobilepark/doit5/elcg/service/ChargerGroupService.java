package com.mobilepark.doit5.elcg.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.uangel.platform.model.Pageable;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerGroupService.java
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

public interface ChargerGroupService extends GenericService<ChargerGroup, Long>{
	public Long insertChargerGroup(Map<String, Object> map, String usid) throws Exception;
	
	public Long updateChargerGroup(Map<String, Object> map, String usid) throws Exception;
	
	public Map<String, Object> getGroupNameList(Long bdId);
	
	public Map<String, Object> getChargerGroupDetail(Long chargerGroupId);
	
	public int getChargerGroupCount(String wkUsid);
	public List<ChargerGroup> getChargerGroupList(String wkUsid, Pageable pageable);
}
