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
public interface EnergyDaoMybatis {


	int getCount(@Param("param") Map<String,Object> param);

	List<Map<String, String>> getEnergyList(@Param("param") Map<String,Object> param);
	
	void EnergyCreate(@Param("param") Map<String,Object> param);
	
	/*
	Map<String, Object> getEnergyDetail(@Param("id") String id);
	
	void EnergyUpdate(@Param("param") Map<String,Object> param);

	int EnergyDelete(@Param("id") String id);
	*/
}
