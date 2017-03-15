package com.mobilepark.doit5.admin.dao;

import java.util.List;

import com.mobilepark.doit5.admin.model.Menu;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsMenuDaoHibernate.java
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
public class MenuDaoHibernate extends HibernateGenericDao<Menu, Integer> implements MenuDao {
	@Override
	protected Criteria getCriteria(Menu entity) {
		Criteria criteria = getCurrentSession().createCriteria(Menu.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("url")
					.excludeProperty("description")
					.excludeProperty("fstRgDt").excludeProperty("lstChDt");
			criteria.add(example);
		}

		return criteria;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Menu> getChildMenus(Integer parentId) {
		Criteria criteria = getCurrentSession().createCriteria(Menu.class)
				.add(Restrictions.eq("parentId", parentId)).addOrder(Order.asc("sort"));

		return criteria.list();
	}

	@Override
	public int getChildeMenuCount(Integer parentId) {
		Criteria criteria = getCurrentSession().createCriteria(Menu.class)
				.add(Restrictions.eq("parentId", parentId));
		criteria.setProjection(Projections.rowCount());
		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public Menu getRootMenu() {
		Criteria criteria = getCurrentSession().createCriteria(Menu.class).add(Restrictions.isNull("parentId"));
		return (Menu) criteria.uniqueResult();
	}

	@Override
	public int delete(Menu entity) {
		getCurrentSession().createQuery("delete from AdminGroupAuth where id.menuId = :menuId")
				.setLong("menuId", entity.getId()).executeUpdate();

		return super.delete(entity);
	}
}
