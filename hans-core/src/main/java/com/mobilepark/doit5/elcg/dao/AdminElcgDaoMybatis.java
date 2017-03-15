package com.mobilepark.doit5.elcg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : AdminElcgDaoMybatis.java
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

@Repository
public class AdminElcgDaoMybatis {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public Map<String, Object> selectOwnerDashboard(String adminId) {
		return sqlSessionTemplate.selectOne("adminElcg.selectOwnerDashboard", adminId);
	}
	
	public List<Object> selectOwnerBdList(String adminId, String searchStatus) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adminId", adminId);
		paramMap.put("searchStatus", searchStatus);
		
		return sqlSessionTemplate.selectList("adminElcg.selectOwnerBdList", paramMap);
	}
	
	public List<Object> selectChargerGroupList(String adminId, boolean isOwner){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("adminId", adminId);
		paramMap.put("isOwner", isOwner);
		
		return sqlSessionTemplate.selectList("adminElcg.selectBdListForChargerGroup", paramMap);
	}
	
	public int deleteCharger(String chargerId){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("chargerId", chargerId);
		paramMap.put("status", "402101");
		
		sqlSessionTemplate.update("adminElcg.updateChargerListStatus", paramMap);
		
		return sqlSessionTemplate.delete("adminElcg.deleteCharger", paramMap);
	}
	
	public int deleteChargerGroup(Integer chargerGroupId){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("chargerGroupId", chargerGroupId);
		paramMap.put("status", "402101");
		
		sqlSessionTemplate.update("adminElcg.updateChargerListStatus", paramMap);
		
		int result = sqlSessionTemplate.delete("adminElcg.deleteChargerAll", paramMap);
		
		result += sqlSessionTemplate.delete("adminElcg.deleteChargerGroup", paramMap);
		
		return result;
	}
	
	
	public Map<String, Object> selectWkBuildingList(String usid){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", usid);
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("adminElcg.selectWkBuildingList", paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bdList", list);
		
		return resultMap;
	}
	
	public int updateBdStatus(String status, Long bdId){
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("status", status);
		paramMap.put("bdId", bdId);
		
		
		return sqlSessionTemplate.update("adminElcg.updateBdStatus", paramMap);
	}

	public List<Map<String, Object>> getInstallerBdList(Map<String, Object> param){
		return sqlSessionTemplate.selectList("adminElcg.getInstallerBdList", param);
	}

	public List<Map<String, Object>> getInstallerBdGroupList(Map<String, Object> param){
		return sqlSessionTemplate.selectList("adminElcg.getInstallerBdGroupList", param);
	}

	public Map<String, Object> getRfidCard(Map<String, Object> param){
		return sqlSessionTemplate.selectOne("adminElcg.getRfidCard", param);
	}
}
