package com.mobilepark.doit5.elcg.dao;

import java.util.Map;

import com.mobilepark.doit5.elcg.model.BrokenReport;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : BrokenReportDao.java
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

public interface BrokenReportDao extends GenericDao<BrokenReport, Long> {
	public Map<String, Object> selectReqReportList(Integer page, Integer size, String type, String usId, Integer adminGroupId);
	
	public Map<String, Object> selectReqReportDetail(Long snId, String type);
	
	public BrokenReport insertReqReport(BrokenReport brokenReport);
	
	public int updateReqReport(BrokenReport brokenReport);
	
	public int updateReqReportComplete(BrokenReport brokenReport);
}
