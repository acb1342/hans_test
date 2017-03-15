package com.mobilepark.doit5.elcg.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.dao.BdGroupDao;
import com.mobilepark.doit5.elcg.dao.ElcgDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.elcg.model.BdGroup;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : BdGroupServiceImpl.java
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
public class BdGroupServiceImpl extends AbstractGenericService<BdGroup, Long> implements BdGroupService {
	
	@Autowired
	private BdGroupDao bdGroupDao;

	@Autowired
	private ElcgDaoMybatis elcgDaoMybatis;
	
	@Override
	protected GenericDao<BdGroup, Long> getGenericDao() {
		return this.bdGroupDao;
	}
	
	@Override
	public List<Object> getBdGroupList(String searchKeyword, Long usid) {
		return elcgDaoMybatis.selectBdGroupList(searchKeyword, usid);
	}
	
	@Override
	public List<Object> getFavoriteBdGroupList(Long usid) {
		return elcgDaoMybatis.selectFavoriteBdGroupList(usid);
	}

	@Override
	public Map<String, Object> getBdGroupDetail(Long bdGroupId, Long bdId) {
		Map<String, Object> bdGroupDetail = elcgDaoMybatis.selectBdGroupDetail(bdGroupId);
		
		bdGroupDetail.put("bdList", elcgDaoMybatis.selectBdGroupDetailBdList(bdGroupId, bdId));
		
		return bdGroupDetail;
	}

	public List<BdGroup> searchBdGroupName(BdGroup bdGroup, String search) {
		return bdGroupDao.searchBdGroupName(bdGroup, search);
	}
	
}
