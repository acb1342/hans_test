package com.mobilepark.doit5.admin.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : AdminGroupDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 22.      최초 버전
 * =================================================================================
 */
public class AdminGroupDaoHibernate extends HibernateGenericDao<AdminGroup, Integer> implements AdminGroupDao {
	@Override
	protected Criteria getCriteria(AdminGroup entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(AdminGroup.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("cdt")
					.excludeProperty("udt");

			criteria.add(example);
		}

		return criteria;
	}

	@Override
	public int delete(AdminGroup entity) {
		this.getCurrentSession().createQuery("delete from AdminGroupAuth where id.adminGroupId = :adminGroupId")
				.setLong("adminGroupId", entity.getId()).executeUpdate();

		return super.delete(entity);
	}

	@Override
	public AdminGroup getByName(String name) {
		Criteria criteria = this.getCurrentSession().createCriteria(AdminGroup.class)
				.add(Restrictions.eq("name", name));
		return (AdminGroup) criteria.uniqueResult();
	}
}
