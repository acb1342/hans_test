package com.mobilepark.doit5.cms.auth;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.Admin;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth
 * @Filename     : Authentication.java
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
public interface Authentication {
	public static final String SUPER_USER_ID = "admin";

	public Authority getAuthority(Integer mainTaskId);

	public Admin getUser();

	public AdminGroup getGroup();
}
