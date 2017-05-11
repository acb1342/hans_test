package com.hans.sses.member.service;

import com.hans.sses.member.model.Equipment;
import com.hans.sses.member.model.User;
import com.hans.sses.member.model.UserEq;

import java.util.List;
import java.util.Map;

public interface UserEqService {
	int count(Map<String, Object> param);
	
	List<UserEq> search(Map<String, Object> param);
	
	Map<String, Object> get(Long id);
	
	void create(UserEq userEq);
	
	void update(Map<String, Object> param);
	
	int delete(Long id);

	List<Map<String, Object>> getCompanyList(Map<String, Object> param);
	
	List<User> getUserList(Integer companySeq);
	
	List<Map<String, Object>> getUserSeq(String id);
	
	List<Equipment> getEquipmentList(String macAddr);
}