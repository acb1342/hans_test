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
public interface DashboardService extends GenericService<Admin, String> {
	
	List<Map<String, Object>> getEnergyList();
	
	List<Map<String, String>> getEnergyByEquipment();
}
