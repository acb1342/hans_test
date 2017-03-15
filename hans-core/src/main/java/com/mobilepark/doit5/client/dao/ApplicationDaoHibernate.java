package com.mobilepark.doit5.client.dao;

import java.util.List;

import com.mobilepark.doit5.client.model.Application;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.util.EtcUtil;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.dao
 * @Filename     : ApplicationDaoHibernate.java
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
public class ApplicationDaoHibernate extends HibernateGenericDao<Application, Integer> implements ApplicationDao {
	@Override
	protected Criteria getCriteria(Application entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(Application.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example)
					.addOrder(Order.desc("id"));
		}

		return criteria;
	}

	@Override
	public Application getByAppName(String appName) {
		Criteria criteria = this.getCurrentSession().createCriteria(Application.class)
				.add(Restrictions.eq("appName", appName));
		return (Application) criteria.uniqueResult();
	}

	@Override
	public Application getByAppId(String appId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Application.class)
				.add(Restrictions.eq("pkgId", appId))
				.setMaxResults(1);
		return (Application) criteria.uniqueResult();
	}

	@Override
	public List<Application> getApps(String appId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Application.class)
				.add(Restrictions.eq("pkgId", appId));
		return criteria.list();
	}

	@Override
	public List<Application> getAppsByCpId(String cpId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Application.class)
				.add(Restrictions.eq("cpId", cpId));
		return criteria.list();
	}

	@Override
	public Application getByCpId(String cpId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Application.class)
				.add(Restrictions.eq("cpId", cpId))
				.setMaxResults(1);
		return (Application) criteria.uniqueResult();
	}

	@Override
	public Application getByCpIdAppId(String cpId, String appId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Application.class)
				.add(Restrictions.eq("cpId", cpId))
				.add(Restrictions.eq("pkgId", appId))
				.setMaxResults(1);
		return (Application) criteria.uniqueResult();
	}

	@Override
	public List<Application> searchByCondition(Application application, String cpId, String appId, String os) {
		StringBuilder query = new StringBuilder(1024)
				.append("select distinct app.*, applist.* ")
				.append(" from tbl_app app ")
				.append(" inner join tbl_app_list applist on app.app_sn = applist.app_sn ")
				.append(" inner join tbl_cp cp on app.cp_sn = cp.cp_sn ")
				.append(" where 1=1 ");
		Integer appSn = null;

		if (application != null) {
			if (!EtcUtil.isNone(application.getId())) {
				appSn = application.getId();
				query.append(" and app.app_sn = :appSn ");
			}
		}

		if (StringUtils.isNotEmpty(cpId)) {
			query.append(" and app.cp_id = :cpId ");
		}

		if (StringUtils.isNotEmpty(appId)) {
			query.append(" and app.pkg_id = :appId and applist.app_id = :appId ");
		}

		if (StringUtils.isNotEmpty(os)) {
			if (!"both".equalsIgnoreCase(os)) {
				query.append(" and applist.os = :os ");
			}
		}

		query.append(" group by app.pkg_id order by app.app_sn desc ");

		Query sqlQuery = this.getCurrentSession().createSQLQuery(query.toString())
				.addEntity("app", Application.class);

		if (appSn != null) {
			sqlQuery.setInteger("appSn", appSn);
		}
		if (StringUtils.isNotEmpty(cpId)) {
			sqlQuery.setString("cpId", cpId);
		}
		if (StringUtils.isNotEmpty(appId)) {
			sqlQuery.setString("appId", appId);
		}
		if (StringUtils.isNotEmpty(os)) {
			sqlQuery.setString("os", os);
		}

		return sqlQuery.list();
	}

	@Override
	public int searchCountByCondition(Application application, String cpId, String appId, String os) {
		StringBuilder query = new StringBuilder(1024)
				.append("select count(distinct app.app_sn) ")
				.append(" from tbl_app app ")
				.append(" inner join tbl_app_list applist on app.app_sn = applist.app_sn ")
				.append(" inner join tbl_cp cp on app.cp_sn = cp.cp_sn ")
				.append(" where 1=1 ");
		Integer appSn = null;

		if (application != null) {
			if (!EtcUtil.isNone(application.getId())) {
				appSn = application.getId();
				query.append(" and app.app_sn = :appSn ");
			}
		}

		if (StringUtils.isNotEmpty(cpId)) {
			query.append(" and app.cp_id = :cpId ");
		}

		if (StringUtils.isNotEmpty(appId)) {
			query.append(" and app.pkg_id = :appId and applist.app_id = :appId ");
		}

		if (StringUtils.isNotEmpty(os)) {
			if (!"both".equalsIgnoreCase(os)) {
				query.append(" and applist.os = :os ");
			}
		}

		Query sqlQuery = this.getCurrentSession().createSQLQuery(query.toString());

		if (appSn != null) {
			sqlQuery.setInteger("appSn", appSn);
		}
		if (StringUtils.isNotEmpty(cpId)) {
			sqlQuery.setString("cpId", cpId);
		}
		if (StringUtils.isNotEmpty(appId)) {
			sqlQuery.setString("appId", appId);
		}
		if (StringUtils.isNotEmpty(os)) {
			sqlQuery.setString("os", os);
		}

		return ((Number) sqlQuery.uniqueResult()).intValue();
	}

	@Override
	public List<Application> getListByCpId(String cpId) {
		StringBuilder query = new StringBuilder(1024)
					.append("select distinct app.*  ")
					.append(" from tbl_app app ")
					.append(" where app.pkg_id ")
					.append(" in ( select al.app_id from tbl_app_list al ) ")
					.append(" and 1=1 ");

		if (StringUtils.isNotEmpty(cpId)) {
			query.append(" and app.cp_id = :cpId ");
		}

		query.append(" order by app.app_sn desc ");

		Query sqlQuery = this.getCurrentSession().createSQLQuery(query.toString())
					.addEntity("app", Application.class);

		if (StringUtils.isNotEmpty(cpId)) {
			sqlQuery.setString("cpId", cpId);
		}

		return sqlQuery.list();
	}
}
