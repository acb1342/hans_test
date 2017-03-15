package com.mobilepark.doit5.elcg.service;

import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.bill.dao.BdPeriodDao;
import com.mobilepark.doit5.bill.model.BdPeriod;
import com.mobilepark.doit5.elcg.dao.AdminElcgDaoMybatis;
import com.mobilepark.doit5.elcg.dao.BdDao;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : BdServiceImpl.java
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
public class BdServiceImpl extends AbstractGenericService<Bd, Long> implements BdService {
	
	@Autowired
	private BdDao bdDao;

	@Autowired
	private BdPeriodDao bdPeriodDao;
	
	@Autowired
	private AdminElcgDaoMybatis adminElcgDaoMybatis;
	
	
	@Override
	protected GenericDao<Bd, Long> getGenericDao() {
		return this.bdDao;
	}
	
	public Map<String, Object> getFavoriteList(Long usid) {
		return bdDao.selectFavoriteList(usid);
	}

	public List<Bd> searchBdName(Bd bd, String search) {
		return bdDao.searchBdName(bd, search);
	}
	
	@Override
	public Map<String, Object> getBuildingList(String searchKeyword, String usid, Integer adminGroupId) {
		
		return bdDao.getBuildingList(searchKeyword, usid, adminGroupId);
	}
	
	@Override
	public Map<String, Object> getBuildingDetail(Long bdId, String usid) {
		
		return bdDao.getBuildingDetail(bdId, usid);
	}
	
	@Override
	public void updateBdCalcdayl(Map<String, Object> map, String usid) {
		
		Integer periodSnId = (Integer) map.get("periodSnId");
		Integer bdId = (Integer) map.get("bdId");
		
		Map<String, Object> detail = bdPeriodDao.selectBdCalcdayDetail(periodSnId.longValue());
		
		
		BdPeriod bdCalcday = new BdPeriod();

		bdCalcday.setSnId(periodSnId.longValue());
		bdCalcday.setBdId(bdId.longValue());
		bdCalcday.setPeriodDay((String)map.get("periodDay"));
		
		
		if(detail == null) {
			
			bdCalcday.setFstRgUsid(usid);
			bdCalcday.setFstRgDt(new Date());
			bdCalcday.setLstChUsid(usid);
			bdCalcday.setLstChDt(new Date());
			
			bdPeriodDao.insertBdCalcday(bdCalcday);
			
		} else {
			
			bdCalcday.setLstChUsid(usid);
			bdCalcday.setLstChDt(new Date());
			
			bdPeriodDao.updateBdCalcday(bdCalcday);
		}
	}
	
	public List<Bd> getInstallerBdList(String bdGroupId, String wkUsid) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bdGroupId", bdGroupId);
		param.put("wkUsid", wkUsid);
		
		List<Map<String, Object>> list =  adminElcgDaoMybatis.getInstallerBdList(param);
		
		List<Bd> bdList = new ArrayList<Bd>();
		for (Map<String, Object> map : list) {
			
			if (map.get("BD_ID") == null) continue;
			if (map.get("NAME") == null) continue;
			
			Bd bd = new Bd();
			bd.setBdId((Long)map.get("BD_ID"));
			bd.setName(map.get("NAME").toString());
			
			bdList.add(bd);
		}
		return bdList;
	}
	
	public List<BdGroup> getInstallerBdGroupList(String bdGroupName, String wkUsid) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("bdGroupName", bdGroupName);
		param.put("wkUsid", wkUsid);
		
		List<Map<String, Object>> list =  adminElcgDaoMybatis.getInstallerBdGroupList(param);
		
		List<BdGroup> bdGroupList = new ArrayList<BdGroup>();
		for (Map<String, Object> map : list) {
			
			if (map.get("BD_GROUP_ID") == null) continue;
			if (map.get("NAME") == null) continue;
			
			BdGroup bdGroup = new BdGroup();
			bdGroup.setBdGroupId((Long)map.get("BD_GROUP_ID"));
			bdGroup.setName(map.get("NAME").toString());
			bdGroup.setAddr(map.get("ADDR") != null ? map.get("ADDR").toString() : "");
			
			bdGroupList.add(bdGroup);
		}
		return bdGroupList;
	}
}
