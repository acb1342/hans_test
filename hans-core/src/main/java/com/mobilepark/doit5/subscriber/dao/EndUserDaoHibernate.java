package com.mobilepark.doit5.subscriber.dao;

import java.util.List;

import com.mobilepark.doit5.subscriber.model.EndUser;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.dao
 * @Filename     : EndUserDaoHibernate.java
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
public class EndUserDaoHibernate extends HibernateGenericDao<EndUser, Integer> implements EndUserDao {
	@Override
	protected Criteria getCriteria(EndUser entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(EndUser.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example);
		}

		return criteria;
	}

	@Override
	public int getCountByAppId(String appId) {
		Criteria criteria = this.getCurrentSession().createCriteria(EndUser.class)
				.add(Restrictions.eq("appId", appId)).setProjection(Projections.rowCount());

		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	public List<EndUser> searchByCondition(String type, String mdn, String pushToken, int pageNum, int rowPerPage) {
		String sql = " SELECT * FROM upush.tbl_user A WHERE 1 = 1 ";
		if ("mdn".equalsIgnoreCase(type)) {
			if (StringUtils.isNotEmpty(mdn)) {
				sql += " AND A.mdn LIKE '%" + mdn + "%' ";
			}
			sql += " GROUP BY A.mdn ";
		} else if ("pushToken".equalsIgnoreCase(type)) {
			if (StringUtils.isNotEmpty(pushToken)) {
				sql += " AND A.push_token LIKE '%" + pushToken + "%' ";
			}
			sql += " GROUP BY A.push_token ";
		}

		sql += " LIMIT " + (pageNum * rowPerPage - rowPerPage) + "," + rowPerPage + " ";

		TraceLog.debug("QUERY : %s", sql);

		Query query = this.getCurrentSession()
				.createSQLQuery(sql)
				.addEntity(EndUser.class);
		return query.list();
	}

	@Override
	public int searchCountByCondition(String type, String mdn, String pushToken) {
		String sql = "SELECT COUNT(*) FROM ("
						+ " SELECT A.* FROM upush.tbl_user A WHERE 1 = 1 ";
		if ("mdn".equalsIgnoreCase(type)) {
			if (StringUtils.isNotEmpty(mdn)) {
				sql += " AND A.mdn LIKE '%" + mdn + "%' ";
			}
			sql += " GROUP BY A.mdn ";
		} else if ("pushToken".equalsIgnoreCase(type)) {
			if (StringUtils.isNotEmpty(pushToken)) {
				sql += " AND A.push_token LIKE '%" + pushToken + "%' ";
			}
			sql += " GROUP BY A.push_token ";
		}

		sql += " ) A";
		TraceLog.debug("QUERY : %s", sql);

		Query query = this.getCurrentSession()
				.createSQLQuery(sql);
		return ((Number) query.uniqueResult()).intValue();
	}
}
