package com.mobilepark.doit5.member.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.member.model.User;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.member.dao
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
	
	public User selectMember(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectOne("member.selectMember", paramMap);
	}
	
	public Long insertMember(User user) {
		sqlSessionTemplate.insert("user.insertMember", user);
		return user.getId();
	}
	
	public int updateMember(User user) {
		return sqlSessionTemplate.update("user.updateMember", user);
	}
	
	public Map<String, Object> selectChargeCondition(Long usid) {
		return sqlSessionTemplate.selectOne("member.selectChargeCondition", usid);
	}
	
	public int insertChargeCondition(Map<String, Object> paramMap) {
		return sqlSessionTemplate.insert("member.insertChargeCondition", paramMap);
	}
	
	public Map<String, Object> selectRfidCardList(Map<String, Object> paramMap) {
		
		List<Object> list = (List<Object>) sqlSessionTemplate.selectList("member.selectRfidCardList", paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("page", paramMap.get("page"));
		resultMap.put("size", paramMap.get("size"));
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", (Integer) sqlSessionTemplate.selectOne("member.selectRfidCardTotalCnt", paramMap));
		resultMap.put("rfidCardList", list);
		
		return resultMap;
	}
	
	public Map<String, Object> selectRfidCard(Map<String, Object> paramMap) {
		return sqlSessionTemplate.selectOne("member.selectRfidCard", paramMap);
	}
	
	public Long selectCardCount(Long usid){
		return sqlSessionTemplate.selectOne("member.selectCardCount", usid);
	}
	
	public int insertCustHist(Map<String, Object> map){
		return sqlSessionTemplate.insert("member.insertCustHist", map);
	}
	
	public int deleteCustClose(Long usid){
		return sqlSessionTemplate.delete("member.deleteCustClose", usid);
	}
	
	public List<Object> selectConformCardList(String id){
		Long usid = (Long) sqlSessionTemplate.selectOne("member.selectUsid", id);
		
		return sqlSessionTemplate.selectList("member.selectConformCardList", usid);
	}
	
	public int deleteCustCar(Long usid){
		return sqlSessionTemplate.delete("member.deleteCustCar", usid);
	}
	
	public int insertCustClose(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("member.insertCustClose", paramMap);
	}
	
	public int updateDeviceId(Long usid){
		return sqlSessionTemplate.update("member.updateDeviceId", usid);
	}
	
	public int updateStopCard(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateStopCard", paramMap);
	}
	
	public Boolean isUsedNfcToken(String certCardNo){
		return sqlSessionTemplate.selectOne("member.isUsedNfcToken", certCardNo);
	}
	
	public Map<String, Object> selectCreditCard(Long usid){
		return sqlSessionTemplate.selectOne("member.selectCreditCard", usid);
	}
	
	public int updateBillKey(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateBillKey", paramMap);
	}
	
	public int insertCreditCard(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("member.insertCreditCard", paramMap);
	}
	
	public int updateCreditCard(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateCreditCard", paramMap);
	}
	
	public Map<String, Object> selectUserDetail(Long usid){
		return sqlSessionTemplate.selectOne("member.selectUserDetail", usid);
	}
	
	public Map<String, Object> selectCarDetail(Long usid){
		return sqlSessionTemplate.selectOne("member.selectCarDetail", usid);
	}
	
	public int insertCar(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("member.insertCar", paramMap);
	}
	
	public int updateCarNumber(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateCarNumber", paramMap);
	}
	
	public int updateChargeCondition(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateChargeCondition", paramMap);
	}
	
	public int updateLoseDt(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateLoseDt", paramMap);
	}
	
	public int updateRfidApplStatus(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateRfidApplStatus", paramMap);
	}
	
	public int updateRfidStatus(Map<String, Object> paramMap){
		return sqlSessionTemplate.update("member.updateRfidStatus", paramMap);
	}
	
	public int isCardNo(Map<String, Object> paramMap){
		return sqlSessionTemplate.selectOne("member.isCardNo", paramMap);
	}
	
	public int isOverCardNo(Map<String, Object> paramMap){
		return sqlSessionTemplate.selectOne("member.isOverCardNo", paramMap);
	}
	
	public int isValidCardNo(Map<String, Object> paramMap){
		return sqlSessionTemplate.selectOne("member.isValidCardNo", paramMap);
	}
	
	public int insertCardReq(Map<String, Object> paramMap){
		return sqlSessionTemplate.insert("member.insertCardReq", paramMap);
	}
}
