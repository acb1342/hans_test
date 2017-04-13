package com.hans.sses.cms.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hans.sses.admin.dao.AdminDaoMybatis;
import com.hans.sses.admin.dao.AdminGroupAuthDao;
import com.hans.sses.admin.model.AdminGroup;
import com.hans.sses.admin.model.AdminGroupAuth;
import com.hans.sses.cms.auth.exception.AuthenticationException;
import com.hans.sses.cms.auth.exception.InvalidPasswordException;
import com.hans.sses.admin.dao.AdminDaoMybatisTest;
import com.hans.sses.admin.model.Admin;
import com.hans.sses.cms.auth.exception.InvalidUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth
 * @Filename     : CmsAuthenticationManager.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2014. 2. 6.      최초 버전
 * =================================================================================
 */
public class CmsAuthenticationManager implements AuthenticationManager {
	@Autowired
	private AdminDaoMybatisTest adminDaoMy;

	@Autowired
	private AdminGroupAuthDao adminGroupAuthDao;
	
	// 사용자의 권한 정보 셋팅
	@Override
	@Transactional
	public Authentication authenticate(String userId, String password) throws AuthenticationException {
		
		Admin user = this.adminDaoMy.getAdmin(userId);
		
		if (user == null) {
			throw new InvalidUserException(String.format("Not Exist User [UserId:%s]", userId));
		}
		
		/*if (user.getPwErrCnt() >= 5) {
			return new CmsAuthentication(user, null, null);
		}
		*/

		if (!user.getPasswd().equals(password)) {
			//user.setPwErrCnt(user.getPwErrCnt() == null ? 1 : user.getPwErrCnt()+1);
			throw new InvalidPasswordException(String.format("Invalid passowrd [UserId:%s]", userId));
		}
		//user.setPwErrCnt(0);

		AdminGroup group = this.adminDaoMy.getAdminGroup(user.getGroupId());
		user.setAdminGroup(group);
		
		Map<Integer, Authority> authorityMap = new HashMap<Integer, Authority>();
		List<Map<String, Object>> groupAuth = this.adminGroupAuthDao.searchGroupAuth(group.getId());

		//for (AdminGroupAuth groupAuth : groupAuths) {
		for(int i=0; i<groupAuth.size(); i++) {
			this.setAuthorityMap(authorityMap, Integer.parseInt(groupAuth.get(i).get("MENU_SEQ").toString()), groupAuth.get(i).get("AUTH").toString());
		}
		//}
		return new CmsAuthentication(user, group, authorityMap);
	}

	// 메뉴에 대한 권한 정보를 담은 맵 생성
	private void setAuthorityMap(Map<Integer, Authority> authorityMap, Integer cmsMenuId, String authLevel) {
		Authority authority = authorityMap.get(cmsMenuId);
		if (authority != null) {
			authority.setAuthLevel(authLevel);
			authorityMap.put(cmsMenuId, authority);
		} else {
			authorityMap.put(cmsMenuId, new CmsAuthority(authLevel));
		}
	}
}
