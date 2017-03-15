package com.mobilepark.doit5.statistics.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.statistics.dao.StatisticsDaoMybatis;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.statistics.service
 * @Filename     : StatisticsService.java
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
public class StatisticsService {
	
	
	@Autowired
	private StatisticsDaoMybatis statisticsDaoMybatis;


	public Map<String, Object> getDailyStatistics(Integer page, Integer size, String adminId, String searchStartDt, String searchEndDt)  throws Exception{
		return statisticsDaoMybatis.selectDailyStatistics(page, size, adminId, searchStartDt, searchEndDt);
	}
	
	
	public Map<String, Object> getMonthlyStatistics(Integer page, Integer size, String adminId, String searchStartYear, String searchStartMonth, String searchEndYear, String searchEndMonth)  throws Exception{
		return statisticsDaoMybatis.selectMonthlyStatistics(page, size, adminId, searchStartYear, searchStartMonth, searchEndYear, searchEndMonth);
	}
	

	
}
