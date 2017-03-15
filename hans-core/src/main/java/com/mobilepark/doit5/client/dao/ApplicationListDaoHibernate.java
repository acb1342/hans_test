package com.mobilepark.doit5.client.dao;

import java.util.List;

import com.mobilepark.doit5.client.model.ApplicationList;
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
 * @Filename     : ApplicationListDaoHibernate.java
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
public class ApplicationListDaoHibernate extends HibernateGenericDao<ApplicationList, Integer> implements ApplicationListDao {
	@Override
	protected Criteria getCriteria(ApplicationList entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(ApplicationList.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example)
					.addOrder(Order.desc("id"));
		}

		return criteria;
	}

	@Override
	public List<ApplicationList> getAppListByOs(String os) {
		Criteria criteria = this.getCurrentSession().createCriteria(ApplicationList.class)
							.add(Restrictions.eq("os", os));
		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<ApplicationList> searchListByCondition(ApplicationList applicationList, String cpId, int page, int rowPerPage) {
		StringBuilder query = new StringBuilder(1024)
				.append("select distinct applist.* ")
				.append("  from tbl_app_list applist ")
				.append(" inner join tbl_app app on app.app_sn = applist.app_sn ")
				.append(" inner join tbl_cp cp on cp.cp_sn = app.cp_sn ")
				.append(" where 1=1 ");

		Integer applicationListId = null;
		String os = "";
		String appId = "";
		String appVer = "";

		if (applicationList != null && !EtcUtil.isNone(applicationList.getId())) {
			applicationListId = applicationList.getId();
			query.append(" and applist.app_list_sn = :applicationListId ");
		}
		if (StringUtils.isNotEmpty(applicationList.getOs())) {
			os = applicationList.getOs();
			query.append(" and applist.os like :os ");
		}
		if (StringUtils.isNotEmpty(applicationList.getAppId())) {
			appId = applicationList.getAppId();
			query.append(" and applist.app_id like :appId ");
		}
		if (StringUtils.isNotEmpty(applicationList.getAppVer())) {
			appVer = applicationList.getAppVer();
			query.append(" and applist.app_ver like :appVer ");
		}
		if (StringUtils.isNotEmpty(cpId)) {
			query.append(" and app.cp_id = :cpId ");
		}

		query.append(" order by applist.app_list_sn desc ");

		Query sqlQuery = this.getCurrentSession().createSQLQuery(query.toString())
				.addEntity("applist", ApplicationList.class);

		if (!EtcUtil.isNone(applicationListId)) {
			sqlQuery.setInteger("applicationListId", applicationListId);
		}
		if (StringUtils.isNotEmpty(os)) {
			sqlQuery.setString("os", "%" + os + "%");
		}
		if (StringUtils.isNotEmpty(appId)) {
			sqlQuery.setString("appId", "%" + appId + "%");
		}
		if (StringUtils.isNotEmpty(appVer)) {
			sqlQuery.setString("appVer", "%" + appVer + "%");
		}
		if (StringUtils.isNotEmpty(cpId)) {
			sqlQuery.setString("cpId", cpId);
		}

		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			sqlQuery.setFirstResult(startRow);
		}
		sqlQuery.setMaxResults(rowPerPage);

		return sqlQuery.list();
	}

	@Override
	public int searchCountByCondition(ApplicationList applicationList, String cpId) {
		StringBuilder query = new StringBuilder(1024)
				.append("select count(distinct applist.app_list_sn) ")
				.append("  from tbl_app_list applist ")
				.append(" inner join tbl_app app on app.app_sn = applist.app_sn ")
				.append(" inner join tbl_cp cp on cp.cp_sn = app.cp_sn ")
				.append(" where 1=1 ");

		Integer applicationListId = null;
		String os = "";
		String appId = "";
		String appVer = "";

		if (applicationList != null && !EtcUtil.isNone(applicationList.getId())) {
			applicationListId = applicationList.getId();
			query.append(" and applicationList.ID = :applicationListId ");
		}
		if (StringUtils.isNotEmpty(applicationList.getOs())) {
			os = applicationList.getOs();
			query.append(" and applist.os like :os ");
		}
		if (StringUtils.isNotEmpty(applicationList.getAppId())) {
			appId = applicationList.getAppId();
			query.append(" and applist.app_id like :appId ");
		}
		if (StringUtils.isNotEmpty(applicationList.getAppVer())) {
			appVer = applicationList.getAppVer();
			query.append(" and applist.app_ver like :appVer ");
		}
		if (StringUtils.isNotEmpty(cpId)) {
			query.append(" and app.cp_id = :cpId ");
		}

		Query sqlQuery = this.getCurrentSession().createSQLQuery(query.toString());

		if (!EtcUtil.isNone(applicationListId)) {
			sqlQuery.setInteger("applicationListId", applicationListId);
		}
		if (StringUtils.isNotEmpty(os)) {
			sqlQuery.setString("os", "%" + os + "%");
		}
		if (StringUtils.isNotEmpty(appId)) {
			sqlQuery.setString("appId", "%" + appId + "%");
		}
		if (StringUtils.isNotEmpty(appVer)) {
			sqlQuery.setString("appVer", "%" + appVer + "%");
		}
		if (StringUtils.isNotEmpty(cpId)) {
			sqlQuery.setString("cpId", cpId);
		}

		return ((Number) sqlQuery.uniqueResult()).intValue();
	}

	@Override
	public List<ApplicationList> getListByAppId(String appId) {
		Criteria criteria = this.getCurrentSession().createCriteria(ApplicationList.class)
				.add(Restrictions.eq("appId", appId));
		return criteria.list();
	}
}
