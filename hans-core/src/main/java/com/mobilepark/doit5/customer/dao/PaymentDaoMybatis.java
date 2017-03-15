package com.mobilepark.doit5.customer.dao;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mobilepark.doit5.common.Pagination;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : PaymentDaoMybatis.java
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
public class PaymentDaoMybatis {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public Map<String, Object> selectPeriodHistList(String formYear, String fromMonth, String toYear, String toMonth, Integer page, Integer size, Long usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Pagination pagination = new Pagination(page, size);
		
		Calendar now = Calendar.getInstance();
		String currYear = String.valueOf(now.get(Calendar.YEAR));
		String currMonth = StringUtils.leftPad(String.valueOf(now.get(Calendar.MONTH) + 1), 2 , "0");
		
		if (Integer.parseInt(currYear + currMonth) <= Integer.parseInt(toYear + toMonth)) {
			paramMap.put("isCurrentMonth", true);
			paramMap.put("toYm", currYear+currMonth);
		} else {
			paramMap.put("toYm", toYear+toMonth);
		}
		
		paramMap.put("fromYm", formYear+fromMonth);
		paramMap.put("usid", usid);
		paramMap.put("pagination", pagination);
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("payment.selectPeriodHistList", paramMap);
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", (Integer) sqlSessionTemplate.selectOne("payment.selectPeriodHistTotalCnt", paramMap));
		resultMap.put("chargeList", list);
		
		
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> selectMonthlyHistory(String searchYear, String searchMonth, Long usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("searchYm", searchYear+searchMonth);
		paramMap.put("searchYmd", searchYear+searchMonth+"01");
		paramMap.put("usid", usid);

		
		// 월 이용금액 정보
		Map<String, Object> resultMap = (Map<String, Object>) sqlSessionTemplate.selectOne("payment.selectMonthlyHist", paramMap);
		
		
		// 월별 충전요금 상세내역
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("payment.selectMonthlyHistDetailList", paramMap);
		
		// 총 충전건수, 충전요금
		Map<String, Object> detailCnt = (Map<String, Object>) sqlSessionTemplate.selectOne("payment.selectMonthlyHistDetailCount", paramMap);
		
		// 최근 3개월간 월별 총 이용금액 그래프
		List<Object> graphList = (List<Object>) sqlSessionTemplate.selectList("payment.selectMonthlyHistGraph", paramMap);
		
		
		resultMap.put("monthChargeList", graphList);
		resultMap.put("totalChargeCount", detailCnt.get("totalChargeCount"));
		resultMap.put("totalChargeFee", detailCnt.get("totalChargeFee"));
		resultMap.put("resultCnt", list.size());
		resultMap.put("monthChargeDetailList", list);
		
		
		return resultMap;
	}
	
	public List<Object> selectMonthlyHistoryList(Long usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", usid);
		
		return sqlSessionTemplate.selectList("payment.selectMonthlyHistList", paramMap);
	}
	
	public List<Object> selectMonthlyHistGraph(Long usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", usid);
		
		Calendar c = Calendar.getInstance();
		String searchYmd = String.valueOf(c.get(Calendar.YEAR))
				+ StringUtils.leftPad(String.valueOf(c.get(Calendar.MONTH) + 1), 2, "0")
				+ c.get(Calendar.DATE);
		
		paramMap.put("searchYmd", searchYmd);
		
		return sqlSessionTemplate.selectList("payment.selectMonthlyHistGraph", paramMap);
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getDetailHistList(String searchYear, String searchMonth, Long usid) { 
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		
		paramMap.put("fromYm", searchYear+searchMonth);
		paramMap.put("usid", usid);
		
		
		Map<String, Object> resultMap = (Map<String, Object>) sqlSessionTemplate.selectOne("payment.selectDetailHist", paramMap);
		
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("payment.selectDetailHistList", paramMap);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
			
			resultMap.put("paymentFee", 0);
			resultMap.put("basicFee", 0);
			resultMap.put("cardIssuingCnt", 0);
			resultMap.put("cardIssuingCost", 0);
			
		}
		
		resultMap.put("monthChargeDetailList", list);
		
		
		return resultMap;
	}
	
	public int updateBillkey(String billkey, String validYn, Long usid) { 
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("billkey", billkey);
		paramMap.put("validYn", validYn);
		paramMap.put("usid", usid);
		
		return sqlSessionTemplate.update("payment.updateBillkey", paramMap);
	}
	
	public int updateMemberPayment(String paymentPlan, String paymentMethod, String status, Long usid) { 
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("paymentPlan", paymentPlan);
		paramMap.put("paymentMethod", paymentMethod);
		paramMap.put("status", status);
		paramMap.put("usid", usid);
		
		return sqlSessionTemplate.update("payment.updateMemberPayment", paramMap);
	}
	
	public String selectChargeCond(Long usid) { 
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", usid);
		
		return sqlSessionTemplate.selectOne("payment.selectChargeCond", paramMap);
	}
	
	public Map<String, Object> selectPaymentMonth(Long usid) {
		return sqlSessionTemplate.selectOne("payment.selectPaymentMonth", usid);
	}
	
	public int insertPaymentMonthFee(Map<String, Object> paramMap) {
		return sqlSessionTemplate.insert("payment.insertPaymentMonthFee", paramMap);
	}
	
	public int updatePaymentMonthFee(Map<String, Object> paramMap) {
		return sqlSessionTemplate.update("payment.updatePaymentMonthFee", paramMap);
	}
	
	public Map<String, Object> selectPostPayDetail(Long usid) {
		return sqlSessionTemplate.selectOne("payment.selectPostPayDetail", usid);
	}
	
	public Map<String, Object> selectPoint(Long usid) {
		return sqlSessionTemplate.selectOne("payment.selectPoint", usid);
	}
	
	public int insertPoint(Map<String, Object> paramMap) {
		return sqlSessionTemplate.insert("payment.insertPoint", paramMap);
	}
	
	public int updatePoint(Map<String, Object> paramMap) {
		return sqlSessionTemplate.update("payment.updatePoint", paramMap);
	}
	

}
