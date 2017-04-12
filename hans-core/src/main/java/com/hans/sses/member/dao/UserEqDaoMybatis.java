package com.hans.sses.member.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.member.model.Equipment;
import com.hans.sses.member.model.UserEq;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface UserEqDaoMybatis {
	// 목록 로우 수
	int count(@Param("param") Map<String, Object> param);
	// 목록 검색
	List<UserEq> search(@Param("param") Map<String, Object> param);
	
	Map<String, Object> get(@Param("id") Long id);
	
	void create(@Param("userEq") UserEq userEq);
	
	void update(@Param("param") Map<String, Object> param);
	
	int delete(@Param("id") Long id);
	// 회사 및 부서 리스트
	List<Map<String, Object>> getCompanyList(@Param("param") Map<String, Object> param);
	// 부서에 속한 사용자 리스트
	List<Map<String, Object>> getUserList(@Param("param") Map<String, Object> param);
	// 할당 가능 장비 리스트
	List<Equipment> getEquipmentList();
	
	List<Map<String, Object>> getUserSeq(@Param("id") String id);
	
}