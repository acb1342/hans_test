package com.mobilepark.doit5.elcg.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Charger;
import com.uangel.platform.model.Pageable;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerService.java
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

public interface ChargerService extends GenericService<Charger, String>{

	public Map<String, Object> getChargerList(Integer adminGroupId, String adminId);
	
	public Map<String, Object> getChargerDetail(String chargerId);
	
	public Map<String, Object> insertCharger(Map<String, Object> map, String usid) throws Exception ;
	
	public void updateCharger(Map<String, Object> map, String usid) throws Exception ;
	
	List<Charger> searchByDate(Charger charger, int page, int rowPerPage, String sortCriterion, String sortDirection,
								String fromDate, String toDate);
	
	public int getChargerCount(String adminId, String fromDate, String toDate, String bdId, Long bdGroupId);
	public List<Charger> getChargerList(String adminId, String fromDate, String toDate,
											String bdId, Long bdGroupId, Pageable pageable);
	
	/** DevReset 메시지 전송 */
	public void mgmtCmdControl(String chargerId);
}
