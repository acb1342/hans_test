package com.mobilepark.doit5.customer.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.customer.model.Car;
import com.mobilepark.doit5.customer.model.ChargeCond;
import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.customer.model.Close;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : MemberService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
public interface MemberService extends GenericService<Member, Long> {
	public Map<String, Object> getUserDetail(Long usid);
	
	public int updatePayment(Member member);
	
	public Car insertCar(Car car);
	
	public void insertCustClose(Close custClose, Member member) throws Exception;
	
	public void updateCarNumber(Car car); 
	
	public Map<String, Object> getCarDetail(Long usid);
	
	public ChargeCond insertChargeCond(ChargeCond chargeCond);
	
	public Map<String, Object> selectChargeCondDetail(Long usid);
	
	public void updateChargeCond(ChargeCond chargeCond);
	
	public Map<String, Object> login(Member member, String clientType, String deviceId, String pushToken); 

	/**
	 * NFC 발급 이력 List
	 * 
	 * added by kodaji
	 * 2016.12.28
	 * 
	 * @param usid
	 * @return
	 */
	public List<Map<String, Object>> getHistCustNfcList(String usid);
	public void insertCustHist(Long usid, String addInfo); 
}
