package com.hans.sses.member.service;

import java.util.List;
import java.util.Map;

import com.hans.sses.member.model.User;

/*==================================================================================
 * @Project      : mobilepark admin
 * @Package      : com.mobilepark.doit5.member.service
 * @Filename     : UserService.java
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2017. 03. 29.       최초 버전
 * =================================================================================
 */
public interface UserService {

	//public Map<String, Object> getUserDetail(Long usid);

	int count(Map<String, Object> param);

	List<User> search(Map<String, Object> param);

	User get(Long id);

	void create(User user);

	void update(User user);

	int delete(Long id);

}
