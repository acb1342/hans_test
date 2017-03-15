package com.mobilepark.doit5.elcg.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.dao.AdminElcgDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : CustomerService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 12. 13.       최초 버전
 * =================================================================================
 */

@Service
@Transactional
public class AdminElcgService {

	@Autowired
	private AdminElcgDaoMybatis adminElcgDaoMybatis;
	
	public Map<String, Object> getOwnerDashboard(String adminId, String searchStatus) {
		Map<String, Object> dashboardMap = adminElcgDaoMybatis.selectOwnerDashboard(adminId);
		
		List<Object> bdList = adminElcgDaoMybatis.selectOwnerBdList(adminId, searchStatus);
		dashboardMap.put("bdList", bdList);
		
		return dashboardMap;
	}
	
	public List<Object> getChargerGroupList(String adminId, boolean isOwner) {
		return adminElcgDaoMybatis.selectChargerGroupList(adminId, isOwner);
	}
	
	public int deleteCharger(String chargerId) {
		return adminElcgDaoMybatis.deleteCharger(chargerId);
	}
	
	public int deleteChargerGroup(Integer chargerGroupId) {
		return adminElcgDaoMybatis.deleteChargerGroup(chargerGroupId);
	}
	
	public Map<String, Object> getWkBuildingList(String usid) {
		return adminElcgDaoMybatis.selectWkBuildingList(usid);
	}
	
}
