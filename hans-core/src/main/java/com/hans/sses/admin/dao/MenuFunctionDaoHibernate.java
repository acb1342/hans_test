package com.hans.sses.admin.dao;

import java.util.List;

import com.hans.sses.admin.model.MenuFunc;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsMenuFunctionDaoHibernate.java
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
public class MenuFunctionDaoHibernate extends HibernateGenericDao<MenuFunc, Integer> implements MenuFunctionDao {
	@Override
	protected Criteria getCriteria(MenuFunc entity) {
		Criteria criteria = getCurrentSession().createCriteria(MenuFunc.class);

		if (entity != null) {
			Example example = Example.create(entity).enableLike(MatchMode.ANYWHERE)
					.excludeProperty("fstRgDt").excludeProperty("lstChDt");
			criteria.add(example);
		}

		return criteria;
	}

	@Override
	public MenuFunc get(String url) {
		Criteria criteria = getCurrentSession().createCriteria(MenuFunc.class).add(Restrictions.eq("url", url))
				.setMaxResults(1);

		return (MenuFunc) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<MenuFunc> searchByMenu(Integer menuId, int page, int rowPerPage) {
		Criteria criteria = getCurrentSession().createCriteria(MenuFunc.class).add(
				Restrictions.eq("menuId", menuId));

		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			criteria.setFirstResult(startRow);
			criteria.setMaxResults(rowPerPage);
		}

		return criteria.list();
	}
}
