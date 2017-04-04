package com.hans.sses.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.member.model.UserEq;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface UserEqDaoMybatis {
	int count(@Param("param") Map<String, Object> param);
	
	List<UserEq> search(@Param("param") Map<String, Object> param);
	
	Map<String, Object> get(@Param("id") Long id);
	
	void create(@Param("param") Map<String, Object> param);
	
	void update(@Param("param") Map<String, Object> param);
	
	int delete(@Param("id") Long id);
	
}