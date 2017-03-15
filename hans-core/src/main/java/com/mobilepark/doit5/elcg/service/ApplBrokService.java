package com.mobilepark.doit5.elcg.service;

import java.util.Map;

import com.mobilepark.doit5.elcg.model.StationApplication;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ApplBrokService.java
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

public interface ApplBrokService extends GenericService<StationApplication, Long>{
	public Map<String, Object> getReqReportList(Integer page, Integer size, String type, String usid, Integer adminGroupId);
	
	public Map<String, Object> getReqReportDetail(Long snId, String type);
	
	public Map<String, Object> insertReqReport(Map<String, Object> map, String usid, String clientType);
	
	public int updateReqReport(Map<String, Object> map, String usid);
	
	public int updateReqReportComplete(Map<String, Object> map, String usid);
	
}
