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
public interface AdminDaoMybatisTest {
	List<Map<String, Object>> searchByGroup(@Param("groupId") Integer groupId);
	
	Admin getAdmin(@Param("userId") String userId);
	
	AdminGroup getAdminGroup(@Param("groupId") int groupId);
	
	List<AdminGroupAuth> searchGroupAuth(@Param("groupId") int groupId);
	
	void createSession(@Param("param") Map<String, Object> param);
	
	List<Map<String, Object>> searchSession(@Param("param") Map<String, Object> param);

	void deleteSession(@Param("snId") int id);
	
	Integer clearSessionAll(@Param("chan") String chan);

	int getCount(@Param("param") Map<String,Object> param);

	List<Map<String, String>> getAdminList(@Param("param") Map<String,Object> param);
	
	Map<String, Object> getMemberDetail(@Param("id") String id);
	
	void MemberUpdate(@Param("param") Map<String,Object> param);
	
	void MemberPasswdUpdate(@Param("param") Map<String, Object> param);
	
	void MemberCreate(@Param("param") Map<String,Object> param);
	
	int MemberDelete(@Param("id") String id);
	
	List<Map<String, Object>> selectGroup();
}
