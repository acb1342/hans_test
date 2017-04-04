package com.hans.sses.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(value="dataSourceTransactionManager")
public interface AdminGroupDaoMybatis {
	int count(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> search(@Param("param") Map<String, Object> param);
	
	Map<String, Object> get(@Param("id") Integer id);
	
	int create(@Param("param") Map<String, Object> param);
	
	int update(@Param("param") Map<String, Object> param);
	
	int delete(@Param("id") Integer id);
	
	List<Map<String, Object>> getAllGroupAuth(@Param("id") Integer id);
	
	Map<String, Object> getGroupAuth(@Param("param") Map<String, Object> param); 
	
	int createGroupAuth(@Param("param") Map<String, Object> param);
	
	int updateGroupAuth(@Param("param") Map<String, Object> param);
	
	int deleteGroupAuth(@Param("groupId") Integer groupId);
}
