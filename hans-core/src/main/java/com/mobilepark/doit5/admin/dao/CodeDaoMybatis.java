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

	Admin getAdmin(@Param("userId") String userId);
	
	AdminGroup getAdminGroup(@Param("groupId") int groupId);
	
	List<AdminGroupAuth> searchGroupAuth(@Param("groupId") int groupId);
	
	void createSession(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> searchSession(@Param("param") Map<String, Object> param);

	void deleteSession(@Param("snId") int id);
	
	Integer clearSessionAll(@Param("chan") String chan);
	

	int getCount(@Param("param") Map<String,Object> param);

	List<Map<String, String>> getCodeList(@Param("param") Map<String,Object> param);
	
	Map<String, Object> getCodeDetail(@Param("id") String id);
	
	void CodeUpdate(@Param("param") Map<String,Object> param);
	
	void CodeCreate(@Param("param") Map<String,Object> param);
	
	int CodeDelete(@Param("id") String id);
}
