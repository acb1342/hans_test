package com.hans.sses.attendance.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional(value="dataSourceTransactionManager")
public interface AttendanceDaoMybatis {
	
	int count(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> search(@Param("param") Map<String, Object> param);
	
	int create(@Param("param") Map<String, Object> param);
	
}
