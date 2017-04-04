package com.hans.sses.member.service;

import com.hans.sses.member.model.UserEq;

import java.util.List;
import java.util.Map;

public interface UserEqService {
	int count(Map<String, Object> param);
	
	List<UserEq> search(Map<String, Object> param);
	
	Map<String, Object> get(Long id);
	
	void create(Map<String, Object> param);
	
	void update(Map<String, Object> param);
	
	int delete(Long id);
	
}