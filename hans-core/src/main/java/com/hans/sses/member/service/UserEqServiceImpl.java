package com.hans.sses.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.member.dao.UserEqDaoMybatis;

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
	public List<Map<String, String>> search(Map<String, Object> param) {
		return this.userEqDaoMybatis.search(param);
	}

	@Override
	public Map<String, Object> get(Long id) {
		return this.userEqDaoMybatis.get(id);
	}

	@Override
	public void create(Map<String, Object> param) {
		param.put("regDate", new Date());
		this.userEqDaoMybatis.create(param);
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
	
}