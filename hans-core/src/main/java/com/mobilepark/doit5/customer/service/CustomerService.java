package com.mobilepark.doit5.customer.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.common.util.NfcTokenUtil;
import com.mobilepark.doit5.customer.dao.CustomerDaoMybatis;
import com.mobilepark.doit5.customer.model.Member;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.common.Pagination;
import com.mobilepark.doit5.customer.dao.PaymentDaoMybatis;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : CustomerService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 12. 13.       최초 버전
 * =================================================================================
 */

@Service
@Transactional
public class CustomerService {

	@Autowired
	private CustomerDaoMybatis customerDaoMybatis;
	
	@Autowired
	private PaymentDaoMybatis paymentDaoMybatis;
	
	
	public Map<String, Object> deviceAuth(Integer userCardType, String authCode) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		if (StringUtils.isBlank(authCode)) {
			// 사용자정보 없음
			resultMap.put("resultCode", 100002);
			return resultMap;
		}
		
		// RFID 카드
		if (userCardType == 1) {
			paramMap.put("cardNo", authCode);
		// NFC
		} else {
			try {
				Long usid = NfcTokenUtil.getUsidFromNDEF(authCode);
				paramMap.put("usid", usid);
			} catch (Exception e) {
				TraceLog.error(e.getMessage(), e);
				// NFC 인증코드 오류
				resultMap.put("resultCode", 400001);
				return resultMap;
			}
		}
		
		Member member = customerDaoMybatis.selectMember(paramMap);
		
		// 사용자 정보 Not Found
		if (member == null) {
			resultMap.put("resultCode", 200001);
			return resultMap;
		}
		
		// RFID카드가 사용중이 아닌 경우
		if (userCardType == 1) {
			Map<String, Object> rfidParamMap = new HashMap<>();
			rfidParamMap.put("usid", member.getId());
			rfidParamMap.put("cardNo", authCode);
			
			Map<String, Object> rfidMap = customerDaoMybatis.selectRfidCard(rfidParamMap);
			if (!StringUtils.equals((String)rfidMap.get("STATUS"), "308107")) {
				resultMap.put("resultCode", 200004);
				return resultMap;
			}
		}
		
		String paymentPlan = member.getPaymentPlan();
		
		// 지불정보 Not Found
		if (StringUtils.isBlank(paymentPlan)) {
			resultMap.put("resultCode", 200002);
			return resultMap;
		}
		
		Map<String, Object> chargeCond = customerDaoMybatis.selectChargeCondition(member.getId());
		
		// 충전설정 Not Found
		if (chargeCond == null) {
			resultMap.put("resultCode", 200003);
			return resultMap;
		}
		
		// 이미 사용한 NFC 토큰을 재사용하는 경우
		if (userCardType == 2 && customerDaoMybatis.isUsedNfcToken(authCode)) {
			resultMap.put("resultCode", 200005);
			return resultMap;
		}
		
		// 정상
		resultMap.put("resultCode", 100001);
		resultMap.put("member", member);
		
		return resultMap;
	}
	
	public Map<String, Object> getRfidCardList(Integer page, Integer size, Long usid) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		Pagination pagination = new Pagination(page, size);
		
		paramMap.put("page", page);
		paramMap.put("size", size);
		paramMap.put("usid", usid);
		paramMap.put("pagination", pagination);
		
		return customerDaoMybatis.selectRfidCardList(paramMap);
	}
	
	public Long selectCardCount(Long usid) {
		return customerDaoMybatis.selectCardCount(usid);
	}
	
	public List<Object> getConformCardList(String id) {
		return customerDaoMybatis.selectConformCardList(id);
	}
	
	public Member getMember(Map<String, Object> paramMap) {
		return customerDaoMybatis.selectMember(paramMap);
	}
	
	public int updateBillKey(Map<String, Object> custParam){
		
		Long usid = (Long)custParam.get("usid");
		
		Map<String, Object> creditCard = customerDaoMybatis.selectCreditCard(usid);
		
		customerDaoMybatis.updateBillKey(custParam);
		
		if (creditCard == null) {
			return customerDaoMybatis.insertCreditCard(custParam);
		} else {
			return customerDaoMybatis.updateCreditCard(custParam);
		}
	}
	
	public Map<String, Object> getUserDetail(Long usid) {
		return customerDaoMybatis.selectUserDetail(usid);
	}
	
	public void insertCustClose(Map<String, Object> map, Long usid, String sub) throws Exception {
		if (StringUtils.isBlank(sub) || !StringUtils.equals(sub, "IFH_1001")) {
			throw new Exception("TIDAPI_ERROR");
		}
		
		// 탈퇴정보 등록
		Map<String, Object> custCloseParamMap = new HashMap<String, Object>();
		custCloseParamMap.put("usid", usid);
		custCloseParamMap.put("type", (String)map.get("type"));
		custCloseParamMap.put("etcContent", (String)map.get("etcContent"));

		customerDaoMybatis.insertCustClose(custCloseParamMap);
		
		
		// 사용자정보 수정
		Member member = new Member();
		member.setId(usid);
		member.setStatus("301103");
		member.setSub(sub);
		
		customerDaoMybatis.updateMember(member);
		
		
		// 차량정보 삭제
		customerDaoMybatis.deleteCustCar(usid);
		
		// DeviceID 삭제
		customerDaoMybatis.updateDeviceId(usid);
		
		
		// RFID 카드 중지처리
		Map<String, Object> cardParamMap = new HashMap<String, Object>();
		cardParamMap.put("usid", usid);
		cardParamMap.put("status", "308108");
		
		customerDaoMybatis.updateStopCard(cardParamMap);
	}

	public void saveCar(Long usid, Map<String, Object> map) {
		
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
	

	public Map<String, Object> getCarDetail(Long usid) {
		Map<String, Object> resultMap = customerDaoMybatis.selectCarDetail(usid);
				
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
			resultMap.put("carNo", "");
		} else {
			resultMap.put("carNo", (String)resultMap.get("carNo"));
		}
		
		resultMap.remove("snId");
		resultMap.remove("usid");
		resultMap.remove("fstRgUsid");
		resultMap.remove("fstRgDt");
		
		
		return resultMap;
	}

	public void saveChargeCondDetail(Long usid, Map<String, Object> map) {
		Map<String, Object> detail = customerDaoMybatis.selectChargeCondition(usid);
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("usid", usid);
		paramMap.put("chargeCond", (String)map.get("chargeCond"));
		
		if("309102".equals((String)map.get("chargeCond"))) {
			paramMap.put("watt", (Integer)map.get("watt")*1000);
		} else {
			paramMap.put("watt", 0);
		}
		
		if(detail == null) {
			customerDaoMybatis.insertChargeCondition(paramMap);
		} else {
			customerDaoMybatis.updateChargeCondition(paramMap);
		}
	}
	
	public Map<String, Object> selectChargeCondition(Long usid) {
		
		Map<String, Object> resultMap = customerDaoMybatis.selectChargeCondition(usid);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
		} else {
			if (StringUtils.equals((String)resultMap.get("chargeCond"), "309102")) {
				Integer watt = ((Long)resultMap.get("watt")).intValue();
				
				if(resultMap.get("watt") != null && watt != 0) {
					watt = watt / 1000;
					resultMap.put("watt", watt);
				}
			}
		}
		
		resultMap.remove("lstChDt");
		resultMap.remove("lstChUsid");
		resultMap.remove("usid");
		
		return resultMap;
	}
	
	public int updateLoseDt(Long snId, Long usid) {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("usid", usid);
		paramMap.put("snId", snId);
		paramMap.put("status", "308108");
		
		int result = customerDaoMybatis.updateLoseDt(paramMap); 
		result += customerDaoMybatis.updateRfidApplStatus(paramMap);
		
		return result;
	}
	
	public Map<String, Object> isValidCardNo(Map<String, Object> map, Long usid) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		
		paramMap.put("usid", usid);
		paramMap.put("cardNo", (String)map.get("cardNo"));
		
		// 유효한 카드번호 인지 체크
		int isCard = customerDaoMybatis.isCardNo(paramMap);	
		
		// 카드번호 미존재 에러
		if(isCard == 0) {
			map.put("errorCd", "428001");
			map.put("msg", "확인되지 않는 카드번호입니다. 번호를 확인하시고 정확하게 다시 입력해 주세요.");
			
			return map;
		}
		
		// 이미 사용중인 카드번호 인지 체크
		int isOverCard = customerDaoMybatis.isOverCardNo(paramMap);	

		// 이미 사용중인 카드번호 에러
		if(isOverCard > 0) {
			map.put("errorCd", "428002");
			map.put("msg", "다른 사용자에 의해 등록된 카드입니다. 본인이 수령한 카드번호를 입력해 주세요.");
			return map;
		}
		
		int result = customerDaoMybatis.isValidCardNo(paramMap);
		
		if(result <= 0) {
			map.put("errorCd", "428003");
			map.put("msg", "고객님께 발급된 카드가 아닙니다. 번호를 확인하시고 정확하게 다시 입력해 주세요.");
			
			return map;
			
		// 카드 등록 (입력한 카드번호가 맞으면 상태값 사용중으로 변경)
		} else {
			paramMap.put("status", "308107");
			
			customerDaoMybatis.updateRfidStatus(paramMap);
			customerDaoMybatis.updateRfidApplStatus(paramMap);
			
			resultMap.put("validYn", "Y");
		}
		
		return resultMap;
	}
	
	public int insertCardReq(Map<String, Object> paramMap) {
		Integer usid = (Integer) paramMap.get("usid"); 
		Map<String, Object> detail = paymentDaoMybatis.selectPaymentMonth(usid.longValue());
		
		if(detail == null) {
			paramMap.put("cardIssuingCnt", 1);
			paymentDaoMybatis.insertPaymentMonthFee(paramMap);
		} else {
			paramMap.put("useYm", detail.get("useYm"));
			paymentDaoMybatis.updatePaymentMonthFee(paramMap);
		}
		
		return customerDaoMybatis.insertCardReq(paramMap);
	}
	
}
