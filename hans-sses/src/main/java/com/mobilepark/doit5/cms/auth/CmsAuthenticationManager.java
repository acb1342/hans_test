package com.mobilepark.doit5.cms.auth;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.cms.auth.exception.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.dao.AdminDaoMybatisTest;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.AdminGroupAuth;
import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.cms.auth.exception.InvalidPasswordException;
import com.mobilepark.doit5.cms.auth.exception.InvalidUserException;
import com.uangel.platform.log.TraceLog;

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
	
	// 사용자의 권한 정보 셋팅
	@Override
	@Transactional
	public Authentication authenticate(String userId, String password) throws AuthenticationException {
		
		Admin user = this.adminDaoMy.getAdmin(userId);
		
		if (user == null) {
			throw new InvalidUserException(String.format("Not Exist User [UserId:%s]", userId));
		}
		
		if (user.getPwErrCnt() >= 5) {
			return new CmsAuthentication(user, null, null);
		}

		if (!user.getPasswd().equals(password)) {
			user.setPwErrCnt(user.getPwErrCnt() == null ? 1 : user.getPwErrCnt()+1);
			throw new InvalidPasswordException(String.format("Invalid passowrd [UserId:%s]", userId));
		}
		user.setPwErrCnt(0);

		AdminGroup group = this.adminDaoMy.getAdminGroup(user.getGroupId());
		user.setAdminGroup(group);
		
		Map<Integer, Authority> authorityMap = new HashMap<Integer, Authority>();
		List<AdminGroupAuth> groupAuths = this.adminDaoMy.searchGroupAuth(group.getId());
		for (AdminGroupAuth groupAuth : groupAuths) {
			this.setAuthorityMap(authorityMap, groupAuth.getMenuId(), groupAuth.getAuth());
		}

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
