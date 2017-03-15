package com.mobilepark.doit5.client.dao;

import javax.persistence.NonUniqueResultException;

import com.mobilepark.doit5.client.model.NsuAgentLib;
import com.mobilepark.doit5.common.ReleaseStatus;
import com.mobilepark.doit5.common.UseFlag;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.dao
 * @Filename     : NsuAgentLibDaoHibernate.java
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
public class NsuAgentLibDaoHibernate extends HibernateGenericDao<NsuAgentLib, Integer> implements NsuAgentLibDao {
	@Override
	protected Criteria getCriteria(NsuAgentLib entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(NsuAgentLib.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("createDate")
					.excludeProperty("modifyDate");

			criteria.add(example)
					.addOrder(Order.desc("id"));
		}

		return criteria;
	}

	@Override
	public NsuAgentLib getAvailableLastNsu(String os) {
		Criteria criteria = this.getCurrentSession().createCriteria(NsuAgentLib.class)
				.add(Restrictions.eq("useFlag", UseFlag.Y))
				.add(Restrictions.eq("os", os))
				.add(Restrictions.eq("status", ReleaseStatus.RELEASED))
				.addOrder(Order.desc("version"))
				.setMaxResults(1);

		try {
			return (NsuAgentLib) criteria.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
	}

	@Override
	public NsuAgentLib getAvailableLastNsu(String os, String locale) {
		String sql = "SELECT A.* FROM TBL_NSU_AGENT_LIB A " +
			"WHERE 1 = 1 " +
			"AND A.LANG_CODE = :locale AND A.STATUS = 'RELEASED' AND A.USE_FLAG = 'Y' " +
			"AND DATE_FORMAT(A.APPLY_START_YMD, '%Y%m%d') <= DATE_FORMAT(CURDATE(), '%Y%m%d') " +
			"AND A.OS = :os " +
			"ORDER BY A.APPLY_START_YMD DESC LIMIT 1";
		Query query = this.getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(NsuAgentLib.class)
				.setParameter("os", os)
				.setParameter("locale", locale);

		return (NsuAgentLib) query.uniqueResult();
	}

	@Override
	public NsuAgentLib get(String version, String os, String langCode) {
		return (NsuAgentLib) this.getCurrentSession().createCriteria(NsuAgentLib.class)
				.add(Restrictions.eq("version", version))
				.add(Restrictions.eq("os", os))
				.add(Restrictions.eq("langCode", langCode))
				.uniqueResult();
	}
}
