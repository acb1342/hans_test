package com.mobilepark.doit5.elcg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.dao.ChargerGroupDao;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.statistics.dao.StatAdjustDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.model.Pageable;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerGroupServiceImpl.java
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

@Transactional
public class ChargerGroupServiceImpl extends AbstractGenericService<ChargerGroup, Long> implements ChargerGroupService {
	
	@Autowired
	private ChargerGroupDao chargerGroupDao;
	
	@Autowired
	private StatAdjustDaoMybatis statAdjustDaoMybatis;

	@Override
	protected GenericDao<ChargerGroup, Long> getGenericDao() {
		return this.chargerGroupDao;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Long insertChargerGroup(Map<String, Object> map, String usid) throws Exception {
		
		List<Map<String, Object>> chargerGroupList = (List<Map<String, Object>>) map.get("chargerGroupList");
		
		Integer searchBdId = (Integer) map.get("bdId");
		Long cnt = 0L;
		
		
		for(Map<String, Object> m : chargerGroupList) {
			
			String name = (String) m.get("name");
			
			cnt = chargerGroupDao.selectChargerGroupName(name, searchBdId.longValue(), null);
			
			// 충전기그룹 등록
			if(cnt == 0) {
				
				Integer bdId = (Integer) map.get("bdId");
				Integer bdGroupId = (Integer) map.get("bdGroupId");
				String description = (String) m.get("description");
				Integer capacity = (Integer) m.get("capacity");
				
				
				Bd bd = new Bd();
				
				bd.setBdId(bdId.longValue());
				
				
				ChargerGroup chargerGroup = new ChargerGroup();
				
				chargerGroup.setBd(bd);
				chargerGroup.setBdGroupId(bdGroupId.longValue());
				chargerGroup.setName(name);
				chargerGroup.setDescription(description);
				chargerGroup.setCapacity(capacity.longValue());
				chargerGroup.setFstRgUsid(usid);
				chargerGroup.setFstRgDt(new Date());
				chargerGroup.setLstChUsid(usid);
				chargerGroup.setLstChDt(new Date());
				
				chargerGroupDao.insertChargerGroup(chargerGroup);
				
			}
			
		}
		
		return cnt;
	}
			
		
	@Override
	public Long updateChargerGroup(Map<String, Object> map, String usid) throws Exception {
		
		Integer chargerGroupId = (Integer) map.get("chargerGroupId");
		Integer bdId = (Integer) map.get("bdId");
		String name = (String) map.get("name");
		
		// 이미 등록되어 있는 충전기그룹명이 있는지 체크
		//Map<String, Object> bdGroupId = chargerGroupDao.selectBdGroupId(name, chargerGroupId.longValue());
		
		Long cnt = chargerGroupDao.selectChargerGroupName(name, bdId.longValue(), chargerGroupId.longValue());
		
		
		
		// 충전기그룹 수정
		if(cnt == 0) {
			
			String description = (String) map.get("description");
			Integer capacity = (Integer) map.get("capacity");
			
			
			ChargerGroup chargerGroup = new ChargerGroup();
			
			chargerGroup.setName(name);
			chargerGroup.setDescription(description);
			chargerGroup.setCapacity(capacity.longValue());
			chargerGroup.setLstChUsid(usid);
			chargerGroup.setLstChDt(new Date());
			chargerGroup.setChargerGroupId(chargerGroupId.longValue());
			
			chargerGroupDao.updateChargerGroup(chargerGroup, usid);
		
		}
		
		return cnt;
		
	}
	
	
	@Override
	public Map<String, Object> getGroupNameList(Long bdId) {
		return chargerGroupDao.selectGroupNameList(bdId);
	}
	
	
	@Override
	public Map<String, Object> getChargerGroupDetail(Long chargerGroupId) {
		return chargerGroupDao.selectChargerGroupDetail(chargerGroupId);
	}


	@Override
	public int getChargerGroupCount(String wkUsid) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("wkUsid", wkUsid);
		
		return statAdjustDaoMybatis.getForInstallerChargerGroupCount(param);
	}


	@Override
	public List<ChargerGroup> getChargerGroupList(String wkUsid, Pageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", pageable.getOffset());
		param.put("rowPerPage", pageable.getRowPerPage());
		param.put("wkUsid", wkUsid);
		
		List<Map<String, Object>> list = statAdjustDaoMybatis.getForInstallerChargerGroupList(param);
		List<ChargerGroup> chargerGroupList = new ArrayList<>();
		for (Map<String, Object> map : list) {
			ChargerGroup chargerGroup = new ChargerGroup();
			
			chargerGroup.setChargerGroupId(map.get("CHARGER_GROUP_ID") != null ? ((Long) map.get("CHARGER_GROUP_ID")) : null);
			chargerGroup.setFstRgDt(map.get("FST_RG_DT") != null ? ((Date) map.get("FST_RG_DT")) : null);
			chargerGroup.setName(map.get("NAME") != null ? map.get("NAME").toString() : null);
			
			BdGroup bdGroup = new BdGroup();
			bdGroup.setName(map.get("BD_GROUP_NAME") != null ? map.get("BD_GROUP_NAME").toString() : null);
			
			Bd bd = new Bd();
			bd.setName(map.get("BD_NAME") != null ? map.get("BD_NAME").toString() : null);
			bd.setBdGroup(bdGroup);
			
			chargerGroup.setBd(bd);
			chargerGroup.setCapacity(map.get("CNT") != null ? ((Long) map.get("CNT")) : null);
			
			chargerGroupList.add(chargerGroup);
			
		}
		
		return chargerGroupList;
	}
	

}
