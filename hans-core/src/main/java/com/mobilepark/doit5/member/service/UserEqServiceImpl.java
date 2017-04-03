package com.mobilepark.doit5.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.member.dao.UserEqDaoMybatis;

@Transactional
public class UserEqServiceImpl implements UserEqService {
	
	@Autowired
	private UserEqDaoMybatis userEqDaoMybatis;

	
}
