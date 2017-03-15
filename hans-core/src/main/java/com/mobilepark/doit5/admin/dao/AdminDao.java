package com.mobilepark.doit5.admin.dao;

import java.util.List;

import com.mobilepark.doit5.admin.model.Admin;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsUserDao.java
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
public interface AdminDao extends GenericDao<Admin, String> {
	List<Admin> searchByGroup(Integer groupId);

	List<Admin> searchByGroupName(String groupName);

	List<Admin> searchByGroupName(String groupName1, String groupName2);

	List<Admin> searchByMCPName(String mcpId);

	int searchCountByGroup(Integer groupId);

	int searchCountByGroupName(String name);

	int searchCountByGroupName(String groupName1, String groupName2);

	List<Admin> searchRelatedCp(String mcpId);

	Admin getById(String id);
}
