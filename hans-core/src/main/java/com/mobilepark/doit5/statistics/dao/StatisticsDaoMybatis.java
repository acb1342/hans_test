package com.mobilepark.doit5.statistics.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.common.Pagination;
import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.statistics.service
 * @Filename     : StatisticsDaoMybatis.java
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

@Repository
public class StatisticsDaoMybatis {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	
	public Map<String, Object> selectDailyStatistics(Integer page, Integer size, String adminId, String searchStartDt, String searchEndDt)  throws Exception {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Pagination pagination = new Pagination(page, size);
		
		
		paramMap.put("page", page);
		paramMap.put("size", size);
		paramMap.put("adminId", adminId);
		paramMap.put("searchStartDt", searchStartDt);
		paramMap.put("searchEndDt", searchEndDt);
		paramMap.put("pagination", pagination);
		
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("statistics.selectDailyStatisticsList", paramMap);
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", (Integer) sqlSessionTemplate.selectOne("statistics.selectDailyStatisticsTotalCnt", paramMap));
		resultMap.put("statDailyList", list);
		
		
		return resultMap;
	}
	
	
	public Map<String, Object> selectMonthlyStatistics(Integer page, Integer size, String adminId, String searchStartYear, String searchStartMonth, String searchEndYear, String searchEndMonth)  throws Exception {
		
        Calendar startCal = Calendar.getInstance();
        startCal.set(Calendar.YEAR, Integer.parseInt(searchStartYear));
        startCal.set(Calendar.MONTH, Integer.parseInt(searchStartMonth) - 1);
        
        int endYear = Integer.parseInt(searchEndYear);
        int endMonth = Integer.parseInt(searchEndMonth) - 1;
        
        List<String> dateList = new ArrayList<String>();
        
        while (true) {
           
           Integer startYear = startCal.get(Calendar.YEAR);
           Integer startMonth = startCal.get(Calendar.MONTH);
           
           dateList.add(String.valueOf(startYear) + StringUtils.leftPad(String.valueOf(startMonth + 1), 2, "0")); 
           
           if (startYear == endYear && startMonth == endMonth) {
              break;
           }
           
           startCal.add(Calendar.MONTH, 1);
           
        }
		
        Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Pagination pagination = new Pagination(page, size);
		
        paramMap.put("page", page);
		paramMap.put("size", size);
		paramMap.put("adminId", adminId);
		paramMap.put("searchStartDt", searchStartYear+StringUtils.leftPad(searchStartMonth, 2, "0"));
		paramMap.put("searchEndDt", searchEndYear+StringUtils.leftPad(searchEndMonth, 2, "0"));
		paramMap.put("pagination", pagination);
		paramMap.put("dateList", dateList);
		
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("statistics.selectMonthlyStatisticsList", paramMap);
		
		boolean isExist = false;
		if (list != null && list.size() > 0) {
			isExist = true;
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", isExist ? list.size() : 0);
		resultMap.put("totalCnt", isExist ? dateList.size() : 0);
		resultMap.put("ymList", list);
		
		
		return resultMap;
	}
}
