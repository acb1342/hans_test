package com.hans.sses.admin.dao;

import java.util.List;
import java.util.Map;

import com.hans.sses.admin.model.AdminGroup;
import com.hans.sses.admin.model.AdminGroupAuth;
import com.hans.sses.admin.model.Admin;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

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
	
	Map<String, Object> getAdminDetail(@Param("id") String id);
	
	void AdminUpdate(@Param("param") Map<String,Object> param);
	
	void AdminPasswdUpdate(@Param("param") Map<String, Object> param);
	
	void AdminCreate(@Param("param") Map<String,Object> param);
	
	int AdminDelete(@Param("id") String id);
	
	List<Map<String, Object>> selectAdminGroup();
}
