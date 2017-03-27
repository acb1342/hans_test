package com.mobilepark.doit5.admin.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.admin.model.Menu;
import com.mobilepark.doit5.admin.model.MenuFunc;

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

	List<Map<String, Object>> getChildMenus(Integer id);

	List<Map<String, Object>> getChildMenus4Tree(Integer id);

	void moveMenu(Integer id, Integer oldParentId, Integer newParentId, int index);

	Menu createMenu(Menu cmsMenu);

	Map<String, Object> getMenu(Integer id);

	int deleteMenu(Integer id);

	int createFunction(Map<String, Object> Param);

	List<Map<String, Object>> getFuncList(int param);

	MenuFunc getFunctionMenu(Integer id);

	int deleteFunction(Integer id);

	int updateMenuFunc(Map<String, Object> param);

	Map<String, Object> getRootMenu();

	int updateMenu(Map<String, Object> param);

	List<Map<String, Object>> getRootMenu1();

	List<Map<String, Object>> getChildMenus1(Integer id);

	int orderUpdate(Map<String, Object> param);

	int orderInsert(Map<String, Object> param);

	int checkMenu(String id);

	int deleteMenu_re(int id);

    int funcUpdate(Map<String, Object> param);
}
