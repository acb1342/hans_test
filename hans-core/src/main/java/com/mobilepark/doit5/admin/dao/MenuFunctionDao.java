package com.mobilepark.doit5.admin.dao;

import java.util.List;

import com.mobilepark.doit5.admin.model.MenuFunc;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsMenuFunctionDao.java
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
public interface MenuFunctionDao extends GenericDao<MenuFunc, Integer> {
	MenuFunc get(String url);

	List<MenuFunc> searchByMenu(Integer menuId, int page, int rowPerPage);
}
