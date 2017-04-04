package com.hans.sses.member.dao;

import java.util.List;
import java.util.Map;

import com.hans.sses.member.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project      : mobilepark admin
 * @Package      : com.mobilepark.doit5.member.dao
 * @Filename     : UserDaoMybatis.java
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2017. 03. 29.       최초 버전
 * =================================================================================
 */
@Repository
@Transactional(value = "dataSourceTransactionManager")
public interface UserDaoMybatis {

	int count(@Param("param") Map<String, Object> param);

	List<Map<String, Object>> search(@Param("param") Map<String, Object> param);

	User get(@Param("id") Long id);

	void create(@Param("user")User user);

	void update(@Param("user")User user);

	int delete(@Param("id") Long id);
}
