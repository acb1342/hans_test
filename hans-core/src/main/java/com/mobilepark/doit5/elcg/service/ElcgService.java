package com.mobilepark.doit5.elcg.service;

import java.util.Map;

import com.mobilepark.doit5.elcg.dao.ElcgDaoMybatis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ElcgService.java
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 *
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
@Service
@Transactional
public class ElcgService {
	
	
	@Autowired
	private ElcgDaoMybatis elcgDaoMybatis;


	public void saveChargeStatus(String status, Long usid, String chargerId) {
		elcgDaoMybatis.saveChargeStatus(status, usid, chargerId);
	}
	
	public Map<String, Object> getChargeStatusDetail(Long usid) {
		return elcgDaoMybatis.selectChargerStatusDetail(usid);
	}
	
	public Map<String, Object> getChargerStatus(Long usid) {
		return elcgDaoMybatis.selectChargerStatus(usid);
	}
	
	public Boolean isChargerChargeAvailable(Long chargerGroupId, Integer chargerWatt) {
		return elcgDaoMybatis.isChargerChargeAvailable(chargerGroupId, chargerWatt);
	}
	
	public int updateBdChargeAvailable(Long bdId, String usid) {
		return elcgDaoMybatis.updateBdChargeAvailable(bdId, usid);
	}
	
}
