package com.mobilepark.doit5.customer.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.customer.model.Member;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.dao
 * @Filename     : CustomerDaoMybatis.java
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

@Repository
public class CustomerDaoMybatis {
	
	@Autowired
	SqlSessionTemplate sqlSessionTemplate;
	
	public Member selectMember(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectOne("customer.selectMember", paramMap);
	}
	
	public Long insertMember(Member member) {
		sqlSessionTemplate.insert("customer.insertMember", member);
		return member.getId();
	}
	
	public int updateMember(Member member) {
		return sqlSessionTemplate.update("customer.updateMember", member);
	}
	
	public Map<String, Object> selectChargeCondition(Long usid) {
		return sqlSessionTemplate.selectOne("customer.selectChargeCondition", usid);
	}
	
	public int insertChargeCondition(Map<String, Object> paramMap) {
		return sqlSessionTemplate.insert("customer.insertChargeCondition", paramMap);
	}
	
	public Map<String, Object> selectRfidCardList(Map<String, Object> paramMap) {
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("customer.selectRfidCardList", paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("page", paramMap.get("page"));
		resultMap.put("size", paramMap.get("size"));
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", (Integer) sqlSessionTemplate.selectOne("customer.selectRfidCardTotalCnt", paramMap));
		resultMap.put("rfidCardList", list);
		
		return resultMap;
	}
	
	public Map<String, Object> selectRfidCard(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectOne("customer.selectRfidCard", paramMap);
	}
	
	public Long selectCardCount(Long usid){
		return sqlSessionTemplate.selectOne("customer.selectCardCount", usid);
	}
	
	public int insertCustHist(Map<String, Object> map){
		return sqlSessionTemplate.insert("customer.insertCustHist", map);
	}
	
	public int deleteCustClose(Long usid){
		return sqlSessionTemplate.delete("customer.deleteCustClose", usid);
	}
	
	public List<Object> selectConformCardList(String id){
		Long usid = (Long) sqlSessionTemplate.selectOne("customer.selectUsid", id);
		
		return sqlSessionTemplate.selectList("customer.selectConformCardList", usid);
	}
	
	public int deleteCustCar(Long usid){
		return sqlSessionTemplate.delete("customer.deleteCustCar", usid);
	}
	
	public int insertCustClose(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("customer.insertCustClose", paramMap);
	}
	
	public int updateDeviceId(Long usid){
		return sqlSessionTemplate.update("customer.updateDeviceId", usid);
	}
	
	public int updateStopCard(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateStopCard", paramMap);
	}
	
	public Boolean isUsedNfcToken(String certCardNo){
		return sqlSessionTemplate.selectOne("customer.isUsedNfcToken", certCardNo);
	}
	
	public Map<String, Object> selectCreditCard(Long usid){
		return sqlSessionTemplate.selectOne("customer.selectCreditCard", usid);
	}
	
	public int updateBillKey(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateBillKey", paramMap);
	}
	
	public int insertCreditCard(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("customer.insertCreditCard", paramMap);
	}
	
	public int updateCreditCard(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateCreditCard", paramMap);
	}
	
	public Map<String, Object> selectUserDetail(Long usid){
		return sqlSessionTemplate.selectOne("customer.selectUserDetail", usid);
	}
	
	public Map<String, Object> selectCarDetail(Long usid){
		return sqlSessionTemplate.selectOne("customer.selectCarDetail", usid);
	}
	
	public int insertCar(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("customer.insertCar", paramMap);
	}
	
	public int updateCarNumber(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateCarNumber", paramMap);
	}
	
	public int updateChargeCondition(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateChargeCondition", paramMap);
	}
	
	public int updateLoseDt(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateLoseDt", paramMap);
	}
	
	public int updateRfidApplStatus(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateRfidApplStatus", paramMap);
	}
	
	public int updateRfidStatus(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("customer.updateRfidStatus", paramMap);
	}
	
	public int isCardNo(Map<String, Object> paramMap){
		return sqlSessionTemplate.selectOne("customer.isCardNo", paramMap);
	}
	
	public int isOverCardNo(Map<String, Object> paramMap){
		return sqlSessionTemplate.selectOne("customer.isOverCardNo", paramMap);
	}
	
	public int isValidCardNo(Map<String, Object> paramMap){
		return sqlSessionTemplate.selectOne("customer.isValidCardNo", paramMap);
	}
	
	public int insertCardReq(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("customer.insertCardReq", paramMap);
	}
}
