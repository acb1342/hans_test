package com.mobilepark.doit5.route.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.route.model.RouteRule;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.dao
 * @Filename     : RouteRuleDaoHibernate.java
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
public class RouteRuleDaoHibernate extends HibernateGenericDao<RouteRule, String> implements RouteRuleDao {
	@Override
	protected Criteria getCriteria(RouteRule entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(RouteRule.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("changeDate");
			criteria.add(example);

			if (entity.getId() != null) {
				criteria.add(Restrictions.like("id", entity.getId(), MatchMode.ANYWHERE));
			}
		}

		return criteria;
	}
}
