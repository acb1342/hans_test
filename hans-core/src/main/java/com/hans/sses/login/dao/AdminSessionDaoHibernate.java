package com.hans.sses.login.dao;

import com.hans.sses.common.Channel;
import com.hans.sses.login.model.AdminSession;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.login.dao
 * @Filename     : LoginSessionDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 7.       최초 버전
 * =================================================================================
 */
public class AdminSessionDaoHibernate extends HibernateGenericDao<AdminSession, Long> implements AdminSessionDao {
	@Override
	protected Criteria getCriteria(AdminSession entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(AdminSession.class);

		if (entity != null) {
			if (entity.getAdminId() != null) {
				criteria.add(Restrictions.eq("adminId", entity.getAdminId()));
			}
//			if (entity.getEdt() == null) {
//				criteria.add(Restrictions.isNull("edt"));
//			}
			if (entity.getSessionId() != null) {
				criteria.add(Restrictions.eq("sessionId", entity.getSessionId()));
			}
			if (entity != null) {
				Example example = Example.create(entity)
						.enableLike(MatchMode.ANYWHERE)
						.excludeProperty("fstRgDt");
				criteria.add(example);
			}
		}

		criteria.addOrder(Order.asc("fstRgDt"));

		return criteria;
	}

	@Override
	public Integer clearSessionAll(Channel chan) {
		String sqlstr = "delete from SKTEVC.TB_MGMT_ADMIN_SESSION where CHAN = :chan";
		Query query = this.getCurrentSession().createSQLQuery(sqlstr);
		query.setString("chan", chan.toString());

		return query.executeUpdate();
	}
}
