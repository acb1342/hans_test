package com.mobilepark.doit5.elcg.dao;

import java.util.Map;

import com.mobilepark.doit5.elcg.model.StationApplication;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : StationApplicationDao.java
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

public interface StationApplicationDao extends GenericDao<StationApplication, Long> {
	public Map<String, Object> selectReqReportList(Integer page, Integer size, String type, String usid, Integer adminGroupId);
	
	public Map<String, Object> selectReqReportAllList(Integer page, Integer size, String usid, Integer adminGroupId);
	
	public Map<String, Object> selectReqReportDetail(Long snId, String type);
	
	public StationApplication insertReqReport(StationApplication stationApplication);
	
	public int updateReqReport(StationApplication stationApplication);
	
	public int updateReqReportComplete(StationApplication stationApplication);
}
