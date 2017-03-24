package com.mobilepark.doit5.admin.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.admin.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.dao.MenuDao;
import com.mobilepark.doit5.admin.dao.MenuDaoMybatis;
import com.mobilepark.doit5.admin.dao.MenuFunctionDao;
import com.mobilepark.doit5.admin.model.MenuFunc;
import com.uangel.platform.log.TraceLog;

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
	private MenuDao menuDao;

	@Autowired
	private MenuFunctionDao menuFunctionDao;
	
	@Autowired
	private MenuDaoMybatis menuDaoMybatis;

	@Override
	public Menu createMenu(Menu entity) {
		int menuOrder = this.menuDao.getChildeMenuCount(entity.getParentId()) + 1;

		entity.setSort(menuOrder);
		entity.setFstRgDt(new Date());

		return this.menuDao.create(entity);
	}

	@Override
	public List<Map<String, Object>> getAllDescendantMenu(int id) {
		List<Map<String, Object>> menus = new ArrayList<Map<String, Object>>();

		List<Map<String, Object>> childMenus = this.getChildMenus(id);
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
	public List<Map<String, Object>> getChildMenus(Integer id) {
		return this.menuDaoMybatis.getChildMenus(id);
	}

	@Override
	public List<Map<String, Object>> getChildMenus4Tree(Integer id) {
		List<Map<String, Object>> chileMenus = new ArrayList<Map<String, Object>>();

		List<Menu> menuList = this.menuDao.getChildMenus(id);
		for (Menu cmsMenu : menuList) {
			chileMenus.add(this.cmsMenu2TreeNode(cmsMenu));
		}

		return chileMenus;
	}

	public Map<String, Object> cmsMenu2TreeNode(Menu menu) {
		Map<String, Object> jsonObj = new HashMap<String, Object>();

		jsonObj.put("id", Integer.toString(menu.getId()));
		jsonObj.put("text", menu.getTitle());
		jsonObj.put("url", menu.getUrl());

		String menuType = menu.getType();
		if ("LEAF".equals(menuType)) {
			jsonObj.put("leaf", true);
		}

		return jsonObj;
	}

	@Override
	public void moveMenu(Integer id, Integer oldParentId, Integer newParentId, int index) {
		Map<String, Object> menu = this.getMenu(id);

		int originOrder = Integer.parseInt(menu.get("sort").toString());
		if (oldParentId.equals(newParentId)) {
			List<Map<String, Object>> newSiblingMenus = this.menuDaoMybatis.getChildMenus(newParentId);
			for (Map<String, Object> newSiblingMenu : newSiblingMenus) {
				newSiblingMenu.put("fstRgUsid", "admin"); 
				int siblingOrder = Integer.parseInt(newSiblingMenu.get("sort").toString());
				if (originOrder > siblingOrder) {
					if (siblingOrder >= index && siblingOrder < originOrder) {
						newSiblingMenu.put("sort", ++siblingOrder);
						this.updateMenu(newSiblingMenu);
					}

				} else {
					if (siblingOrder <= index && siblingOrder > originOrder) {
						newSiblingMenu.put("sort", --siblingOrder);
						this.updateMenu(newSiblingMenu);
					}
				}
			}

		} else {
			List<Map<String, Object>> newSiblingMenus = this.menuDaoMybatis.getChildMenus(newParentId);
			for (Map<String, Object> newSiblingMenu : newSiblingMenus) {
				int siblingOrder = Integer.parseInt(newSiblingMenu.get("sort").toString());
				if (siblingOrder >= index) {
					newSiblingMenu.put("sort", ++siblingOrder);
					this.updateMenu(newSiblingMenu);
				}
			}

			List<Map<String, Object>> oldSiblingMenus = this.menuDaoMybatis.getChildMenus(oldParentId);
			for (Map<String, Object> oldSiblingMenu : oldSiblingMenus) {
				int siblingOrder = Integer.parseInt(oldSiblingMenu.get("sort").toString());
				if (siblingOrder >= originOrder) {
					oldSiblingMenu.put("sort", --siblingOrder);
					this.updateMenu(oldSiblingMenu);
				}
			}

			int newParentMenuDepth = 0;
			if (newParentId != 0) {
				Map<String, Object> newParentMenu = this.getMenu(newParentId);
				if (newParentMenu != null) {
					newParentMenuDepth = Integer.parseInt(newParentMenu.get("depth").toString());
				}
			}
			menu.put("depth", (newParentMenuDepth + 1));

			this.updateMenuDpethOfChild(Integer.parseInt(menu.get("id").toString()), newParentMenuDepth + 1);
		}

		menu.put("parentId", newParentId);
		menu.put("sort", index);
		menu.put("lstChDt", new Date());
		this.updateMenu(menu);

	}
	
	private void updateMenuDpethOfChild(int id, int newMenuDepth) {
		List<Map<String, Object>> childMenus = this.getChildMenus(id);
		if (childMenus.size() == 0) {
			return;
		} else {
			for (Map<String, Object> childMenu : childMenus) {
				childMenu.put("depth", newMenuDepth + 1);
				this.updateMenu(childMenu);

				this.updateMenuDpethOfChild(Integer.parseInt(childMenu.get("id").toString()), newMenuDepth + 1);
			}
		}
	}

	@Override
	public Map<String, Object> getMenu(Integer id) {
		return this.menuDaoMybatis.get(id);
	}

	@Override
	public int deleteMenu(Integer id) {
		int deleteCount = 0;

		List<Menu> children = this.menuDao.getChildMenus(id);
		for (Menu child : children) {
			deleteCount += this.deleteMenu(child.getId());
		}

		deleteCount += this.menuDao.delete(id);

		return deleteCount;
	}

	@Override
	public int updateMenu(Map<String, Object> param) {
		return this.menuDaoMybatis.update(param);
	}

	@Override
	public List<Map<String, Object>> getFuncList(int param) {
		return this.menuDaoMybatis.getFuncList(param);
	}

	@Override
	public Map<String, Object> getFunctionMenu(Integer id) { return this.menuDaoMybatis.getFunc(id); }

	@Override
	public int deleteFunction(Integer id) {
		return this.menuFunctionDao.delete(id);
	}

	@Override
	public int createFunction(Map<String, Object> param) {
		return this.menuDaoMybatis.createFunction(param);
	}

	@Override
	public Map<String, Object> getRootMenu() {
		return this.menuDaoMybatis.getRootMenu();
	}

	@Override
	public List<Map<String, Object>> getRootMenu1() {
		return menuDaoMybatis.getRootMenu1();
	}

	@Override
	public List<Map<String, Object>> getChildMenus1(Integer id) {
		return menuDaoMybatis.getChildMenus1(id);
	}

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
	public int deleteMenu_re(int id) { return this.menuDaoMybatis.deleteMenu(id); }

	@Override
	public int funcUpdate(Map<String, Object> param) { return this.menuDaoMybatis.funcUpdate(param); }
}
