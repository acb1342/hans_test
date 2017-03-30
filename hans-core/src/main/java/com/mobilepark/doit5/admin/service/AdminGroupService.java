package com.mobilepark.doit5.admin.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.AdminGroupAuth;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : CmsGroupService.java
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
public interface AdminGroupService {//extends GenericService<AdminGroup, Integer> {
	
	List<Map<String, Object>> search(Map<String, Object> param);
	
	Map<String, Object> get(Integer id);
	
	int create(Map<String, Object> param);
	
	int update(Map<String, Object> param);
	
	int delete(Integer id);
	
	boolean updateAuth(Map<String, Object> param);

	List<AdminGroupAuth> searchGroupAuth(Integer groupId);

	int deleteGroupAuth(Integer groupId);

	AdminGroup getByName(String name);
	
	int count(Map<String, Object> param);
	
	List<Map<String, Object>> getAllGroupAuth(Integer id);
}
