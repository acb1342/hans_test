package com.hans.sses.admin.dao;

import java.util.List;

import com.hans.sses.admin.model.AdminGroupAuth;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsGroupAuthDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 5.      최초 버전
 * =================================================================================
 */
public interface AdminGroupAuthDao extends GenericDao<AdminGroupAuth, AdminGroupAuth.ID> {
	AdminGroupAuth get(Integer groupId, Integer menuId);

	List<AdminGroupAuth> searchGroupAuth(Integer groupId);

	int deleteGroupAuth(Integer groupId);
}
