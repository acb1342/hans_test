package com.mobilepark.doit5.provider.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.provider.model.ContentProvider;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.dao
 * @Filename     : ContentProviderDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
public class ContentProviderDaoHibernate extends HibernateGenericDao<ContentProvider, Integer> implements ContentProviderDao {
	@Override
	protected Criteria getCriteria(ContentProvider entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(ContentProvider.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example)
					.addOrder(Order.desc("id"));
		}

		return criteria;
	}

	@Override
	public ContentProvider getById(String cpId) {
		Criteria criteria = this.getCurrentSession().createCriteria(ContentProvider.class)
							.add(Restrictions.eq("cpId", cpId))
							.setMaxResults(1);
		return (ContentProvider) criteria.uniqueResult();
	}
}
