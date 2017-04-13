package com.hans.sses.admin.service;

import java.util.List;
import java.util.Map;

import com.hans.sses.admin.model.MenuFunc;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : CmsMenuService.java
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
public interface MenuService {

	List<Map<String, Object>> getAllDescendantMenu(int menuId);

	MenuFunc getFunctionByUrl(String uri);

	List<Map<String, Object>> getRootMenu();

	List<Map<String, Object>> getAuthMenu(Integer groupSeq);

	Map<String, Object> getMenu(Integer id);

	int updateMenu(Map<String, Object> param);

	int deleteMenu(Integer id);

	int orderUpdate(Map<String, Object> param);

	int orderInsert(Map<String, Object> param);

	int checkMenu(String id);

	int createFunction(Map<String, Object> Param);

	List<Map<String, Object>> getFuncList(Integer param);

	MenuFunc getFunctionMenu(Integer id);

	int deleteFunction(Integer id);

	int updateMenuFunc(Map<String, Object> param);


}
