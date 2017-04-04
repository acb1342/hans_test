package com.hans.sses.admin.dao;

import java.util.List;

import com.hans.sses.admin.model.Menu;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsMenuDao.java
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
public interface MenuDao extends GenericDao<Menu, Integer> {
	List<Menu> getChildMenus(Integer id);

	int getChildeMenuCount(Integer parentId);

	Menu getRootMenu();

}
