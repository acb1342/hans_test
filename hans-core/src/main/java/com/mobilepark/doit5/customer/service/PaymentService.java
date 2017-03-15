package com.mobilepark.doit5.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.customer.dao.CustomerDaoMybatis;
import com.mobilepark.doit5.customer.dao.PaymentDaoMybatis;
import com.mobilepark.doit5.customer.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : PaymentService.java
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
public class PaymentService {
	
	@Autowired
	private PaymentDaoMybatis paymentDaoMybatis;
	
	@Autowired
	private CustomerDaoMybatis customerDaoMybatis;

	public Map<String, Object> getPeriodHistList(String formYear, String fromMonth, String toYear, String toMonth, Integer page, Integer size, Long usid) {
		return paymentDaoMybatis.selectPeriodHistList(formYear, fromMonth, toYear, toMonth, page, size, usid);
	}
	
	public Map<String, Object> getMonthlyHistory(String searchYear, String searchMonth, Long usid) {
		return paymentDaoMybatis.selectMonthlyHistory(searchYear, searchMonth, usid);
	}
	
	public List<Object> getMonthlyHistoryList(Long usid) {
		return paymentDaoMybatis.selectMonthlyHistoryList(usid);
	}
	
	public List<Object> getMonthlyHistGraph(Long usid) {
		return paymentDaoMybatis.selectMonthlyHistGraph(usid);
	}
	
	public Map<String, Object> getDetailHistList(String searchYear, String searchMonth, Long usid) {
		return paymentDaoMybatis.getDetailHistList(searchYear, searchMonth, usid);
	}
	
	public int updatePayment(String billkey, Long usid) {
		
		int result = paymentDaoMybatis.updateBillkey(billkey, "Y", usid);
		
		// 결제방식 : 후불(302202), 결제수단 : 신용카드(301301), 회원상태 : 정회원(301102)
		paymentDaoMybatis.updateMemberPayment("302202", "301301", "301102", usid);
		
		return result;
	}
	
	public String getChargeCond(Long usid) {
		return paymentDaoMybatis.selectChargeCond(usid);
	}
	
	public Map<String, Object> getPostPayDetail(Long usid) {
		return paymentDaoMybatis.selectPostPayDetail(usid);
	}
	
	public void setPrePay(Map<String, Object> map, Long usid) {
		
		// 결제수단 수정
		Member member = new Member();
		member.setId(usid);
		member.setStatus("301102");					// 가입상태 (정회원 : 301102)
		member.setPaymentPlan("301201"); 		// 결제방식
		member.setPaymentMethod("301302");  // 결제수단
		
		customerDaoMybatis.updateMember(member);
		
		// 포인트 조회
		Map<String, Object> pointDetail = paymentDaoMybatis.selectPoint(usid);
		
		Integer remainPoint = (Integer) map.get("remainPoint");
		
		Map<String, Object> pointParamMap = new HashMap<String, Object>();
		pointParamMap.put("usid", usid);
		pointParamMap.put("remainPoint", remainPoint.longValue());
		
		
		if(pointDetail == null) {
			paymentDaoMybatis.insertPoint(pointParamMap);
		} else {
			paymentDaoMybatis.updatePoint(pointParamMap);
		}
		
		// 차량정보 조회
		Map<String, Object> carDetail = customerDaoMybatis.selectCarDetail(usid);

		Map<String, Object> carParamMap = new HashMap<String, Object>();
		carParamMap.put("usid", usid);
		carParamMap.put("carNo", (String)map.get("carNo"));
		
		if(carDetail == null) {
			customerDaoMybatis.insertCar(carParamMap);
		} else {
			customerDaoMybatis.updateCarNumber(carParamMap);
		}
	}

	public Map<String, Object> getPoint(Long usid) {
		Map<String, Object> resultMap = paymentDaoMybatis.selectPoint(usid);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
			resultMap.put("remainPoint", 0);
		}
		
		return resultMap;
	}
	
}
