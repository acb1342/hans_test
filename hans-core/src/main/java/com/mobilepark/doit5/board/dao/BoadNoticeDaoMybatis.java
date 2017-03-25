package com.mobilepark.doit5.board.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value = "dataSourceTransactionManager")
public interface BoadNoticeDaoMybatis {
	int count(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> search(@Param("param") Map<String, Object> param);
	
	Map<String, Object> get(@Param("id") Long id);
	
	void create(@Param("param") Map<String, Object> param);
	
	void update(@Param("param") Map<String, Object> param);
	
	int delete(@Param("id") Long id);
}
