package com.mobilepark.doit5.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.model.AdminGroupAuth;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface AdminGroupDaoMybatis {
	int count(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> search(@Param("param") Map<String, Object> param);
	
	Map<String, Object> get(@Param("id") Integer id);
	
	void create(@Param("param") Map<String, Object> param);
	
	void update(@Param("param") Map<String, Object> param);
	
	int delete(@Param("id") Long id);
	
	List<Map<String, Object>> getGroupAuth(@Param("id") Integer id);
}
