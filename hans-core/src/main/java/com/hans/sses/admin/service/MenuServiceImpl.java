package com.hans.sses.admin.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.hans.sses.admin.dao.MenuDaoMybatis;
import com.hans.sses.admin.model.MenuFunc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : MenuServiceImpl.java
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
@Transactional
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDaoMybatis menuDaoMybatis;

	@Override
	public List<Map<String, Object>> getAllDescendantMenu(int id) {
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> childMenus = this.getFuncList(id);
		if (childMenus.size() == 0) {
			return menus;
		} else {
			for (Map<String, Object> cmsMenu : childMenus) {
				menus.add(cmsMenu);
				List<Map<String, Object>> descendantMenus = this.getAllDescendantMenu(Integer.parseInt(cmsMenu.get("id").toString()));
				if (descendantMenus.size() > 0) {
					menus.addAll(descendantMenus);
				}
			}
		}

		return menus;
	}

	@Override
	public List<Map<String, Object>> getRootMenu() {
		return menuDaoMybatis.getRootMenu();
	}

	@Override
	public Map<String, Object> getMenu(Integer id) {
		return this.menuDaoMybatis.get(id);
	}

	@Override
	public int updateMenu(Map<String, Object> param) {
		return this.menuDaoMybatis.update(param);
	}

	@Override
	public int deleteMenu(Integer id) { return this.menuDaoMybatis.deleteMenu(id); }

	@Override
	public int orderUpdate(Map<String, Object> param) {
		return menuDaoMybatis.orderUpdate(param);
	}

	@Override
	public int orderInsert(Map<String, Object> param) { return menuDaoMybatis.orderInsert(param); }

	@Override
	public int checkMenu(String id) {
		return this.menuDaoMybatis.checkMenu(id);
	}

	@Override
	public int createFunction(Map<String, Object> param) {
		return this.menuDaoMybatis.createFunction(param);
	}

	@Override
	public List<Map<String, Object>> getFuncList(Integer param) {
		return this.menuDaoMybatis.getFuncList(param);
	}

	@Override
	public MenuFunc getFunctionMenu(Integer id) { return this.menuDaoMybatis.getFunc(id); }

	@Override
	public int deleteFunction(Integer id) {
		return this.menuDaoMybatis.deleteFunction(id);
	}

	@Override
	public int updateMenuFunc(Map<String, Object> param) {
		return this.menuDaoMybatis.updateFunction(param);
	}

	@Override
	public MenuFunc getFunctionByUrl(String uri) {return this.menuDaoMybatis.getFuncUrl(uri); }

}
