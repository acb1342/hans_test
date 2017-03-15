package com.mobilepark.doit5.client.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import com.mobilepark.doit5.client.model.NsuAppsLib;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.dao
 * @Filename     : NsuAppsLibDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
public class NsuAppsLibDaoHibernate extends HibernateGenericDao<NsuAppsLib, Integer> implements NsuAppsLibDao {
	@Override
	protected Criteria getCriteria(NsuAppsLib entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(NsuAppsLib.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("createDate")
					.excludeProperty("modifyDate");

			criteria.add(example);
		}

		return criteria;
	}
}
