package com.mobilepark.doit5.admin.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestParam;

import com.mobilepark.doit5.admin.model.Admin;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : CmsUserService.java
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
public interface AdminService extends GenericService<Admin, String> {
	List<Admin> searchByGroup(Integer groupId);

	int searchCountByGroup(Integer groupId);

	List<Admin> searchByGroupName(String name);

	List<Admin> searchByGroupName(String groupName1, String groupName2);

	List<Admin> searchByMCPName(String mcpId);

	int searchCountByGroupName(String name);

	int searchCountByGroupName(String groupName1, String groupName2);

	List<Admin> searchRelatedCp(String mcpId);

	Admin getById(String id);
	
	Admin getMybatis(String id);
	
	int getCount(Map<String, Object> param);
	
	List<Map<String, String>> getAdminList(Map<String, Object> param);
	
	Map<String, Object> getMemberDetail(String id);
	
	void MemberUpdate(Map<String, Object> param);
	
	void MemberCreate(Map<String, Object> param);
	
	int MemberDelete(String id);
	
	List<Map<String, Object>> selectGroup();
	
	
}
