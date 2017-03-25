package com.mobilepark.doit5.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.AdminGroupAuth;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface CodeDaoMybatis {


	int getCount(@Param("param") Map<String,Object> param);

	List<Map<String, String>> getCodeList(@Param("param") Map<String,Object> param);
	
	Map<String, Object> getCodeDetail(@Param("id") String id);
	
	void CodeUpdate(@Param("param") Map<String,Object> param);
	
	void CodeCreate(@Param("param") Map<String,Object> param);
	
	int CodeDelete(@Param("id") String id);
}
