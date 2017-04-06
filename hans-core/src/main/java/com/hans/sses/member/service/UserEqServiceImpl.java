package com.hans.sses.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.member.dao.UserEqDaoMybatis;
import com.hans.sses.member.model.Equipment;
import com.hans.sses.member.model.UserEq;

@Service
@Transactional
public class UserEqServiceImpl implements UserEqService {
	
	@Autowired
	private UserEqDaoMybatis userEqDaoMybatis;

	@Override
	public int count(Map<String, Object> param) {
		return this.userEqDaoMybatis.count(param);
	}
	
	@Override
	public List<UserEq> search(Map<String, Object> param) {
		return this.userEqDaoMybatis.search(param);
	}

	@Override
	public Map<String, Object> get(Long id) {
		return this.userEqDaoMybatis.get(id);
	}

	@Override
	public void create(UserEq userEq) {
		userEq.setRegDate(new Date());
		this.userEqDaoMybatis.create(userEq);
	}
	
	@Override
	public void update(Map<String, Object> param) {
		param.put("modDate", new Date());
		this.userEqDaoMybatis.update(param);
	}

	@Override
	public int delete(Long id) {
		return this.userEqDaoMybatis.delete(id);
	}

	@Override
	public List<Map<String, Object>> getCompanyList(Map<String, Object> param) {
		return this.userEqDaoMybatis.getCompanyList(param);
	}

	@Override
	public List<Map<String, Object>> getUserList(Map<String, Object> param) {
		return this.userEqDaoMybatis.getUserList(param);
	}
	
	@Override
	public List<Equipment> getEquipmentList() {
		return this.userEqDaoMybatis.getEquipmentList();
	}
}