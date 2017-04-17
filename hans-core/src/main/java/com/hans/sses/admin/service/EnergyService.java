package com.hans.sses.admin.service;

import java.util.List;
import java.util.Map;

import com.hans.sses.admin.model.Admin;
import org.springframework.stereotype.Service;

import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : CmsUserService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 5.      최초 버전
 * =================================================================================
 */
@Service
public interface EnergyService extends GenericService<Admin, String> {
	
	int getCount(Map<String, Object> param);
	
	List<Map<String, String>> getEnergyList(Map<String, Object> param);

	void EnergyCreate(Map<String, Object> param);
	
	List<Map<String, Object>> getDayEnergyList(Map<String, Object> param);
	
	List<Map<String, Object>> getMonEnergyList(Map<String, Object> param);

	Map<String, Object> getSavingEnergy(Map<String, Object> param);
}
