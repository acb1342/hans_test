package com.mobilepark.doit5.member.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.member.dao.UserDaoMybatis;
import com.mobilepark.doit5.member.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project      : mobilepark admin
 * @Package      : com.mobilepark.doit5.member.service
 * @Filename     : MemberSerivceImpl.java
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2017. 03. 29.       최초 버전
 * =================================================================================
 */
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDaoMybatis userDaoMybatis;

	@Autowired
	public int count(Map<String, Object> param) {
		return this.userDaoMybatis.count(param);
	}

	@Override
	public List<Map<String, Object>> search(Map<String, Object> param) {
		return this.userDaoMybatis.search(param);
	}

	@Override
	public User get(Long id) { return this.userDaoMybatis.get(id); }

	@Override
	public void create(User user) { this.userDaoMybatis.create(user); }

	@Override
	public void update(User user) { this.userDaoMybatis.update(user); }

	@Override
	public int delete(Long id) {
		return this.userDaoMybatis.delete(id);
	}

}
