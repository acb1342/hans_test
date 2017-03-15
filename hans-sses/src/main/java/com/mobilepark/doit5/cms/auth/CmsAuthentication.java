package com.mobilepark.doit5.cms.auth;

import java.util.Map;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.Admin;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth
 * @Filename     : CmsAuthentication.java
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
public class CmsAuthentication implements Authentication {
	private Admin user;
	private AdminGroup group;
	private Map<Integer, Authority> authorityMap;

	public CmsAuthentication(Admin user, AdminGroup group, Map<Integer, Authority> authorityMap) {
		super();
		this.user = user;
		this.group = group;
		this.authorityMap = authorityMap;
	}

	@Override
	public Authority getAuthority(Integer menuId) {
		return this.authorityMap.get(menuId);
	}

	@Override
	public Admin getUser() {
		return this.user;
	}

	public void setUser(Admin user) {
		this.user = user;
	}

	@Override
	public AdminGroup getGroup() {
		return this.group;
	}

	public void setGroup(AdminGroup group) {
		this.group = group;
	}

	public Map<Integer, Authority> getAuthorityMap() {
		return this.authorityMap;
	}

	public void setAuthorityMap(Map<Integer, Authority> authorities) {
		this.authorityMap = authorities;
	}
}
