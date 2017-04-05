package com.hans.sses.admin.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(value="dataSourceTransactionManager")
public interface EnergyDaoMybatis {


	int getCount(@Param("param") Map<String,Object> param);

	List<Map<String, String>> getEnergyList(@Param("param") Map<String,Object> param);
	
	void EnergyCreate(@Param("param") Map<String,Object> param);
	
	/*List<Map<String, String>> getDayEnergyList(@Param("beforeday") String beforday, @Param("afterday") String afterday);*/
	List<Map<String, Object>> getDayEnergyList(@Param("param") Map<String,Object> param);
	
	List<Map<String, Object>> getMonEnergyList(@Param("param") Map<String,Object> param);

}
