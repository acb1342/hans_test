package com.hans.sses.admin.dao;

import java.util.List;

import com.hans.sses.admin.model.AdminGroupAuth;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsGroupAuthDaoHibernate.java
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
public class AdminGroupAuthDaoHibernate extends HibernateGenericDao<AdminGroupAuth, AdminGroupAuth.ID> implements AdminGroupAuthDao {
	@Override
	protected Criteria getCriteria(AdminGroupAuth entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(AdminGroupAuth.class);

		if (entity != null) {
			Example example = Example.create(entity);
			criteria.add(example);
		}

		return criteria;
	}

	@Override
	public AdminGroupAuth get(Integer groupId, Integer menuId) {
		Criteria criteria = this.getCurrentSession().createCriteria(AdminGroupAuth.class)
				.add(Restrictions.eq("id.adminGroupId", groupId))
				.add(Restrictions.eq("id.menuId", menuId));

		return (AdminGroupAuth) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<AdminGroupAuth> searchGroupAuth(Integer groupId) {
		Criteria criteria = this.getCurrentSession().createCriteria(AdminGroupAuth.class)
				.add(Restrictions.eq("id.adminGroupId", groupId));

		return criteria.list();
	}

	@Override
	public int deleteGroupAuth(Integer groupId) {
		String sqlStr = "delete from upush.TBL_ADMIN_GROUP_AUTH where ADMIN_GROUP_ID = :adminGroupId";
		Query query = this.getCurrentSession().createSQLQuery(sqlStr);
		query.setInteger("adminGroupId", groupId);

		return query.executeUpdate();
	}
}
