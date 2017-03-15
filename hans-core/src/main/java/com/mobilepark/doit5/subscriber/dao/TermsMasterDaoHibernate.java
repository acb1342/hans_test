package com.mobilepark.doit5.subscriber.dao;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.common.DeviceType;
import com.mobilepark.doit5.common.ReleaseStatus;
import com.mobilepark.doit5.common.TermsType;
import com.mobilepark.doit5.common.UseFlag;
import com.mobilepark.doit5.subscriber.model.TermsMaster;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.dao
 * @Filename     : TermsMasterDaoHibernate.java
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
public class TermsMasterDaoHibernate extends HibernateGenericDao<TermsMaster, TermsMaster.TermsId> implements TermsMasterDao {
	@Override
	protected Criteria getCriteria(TermsMaster entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(TermsMaster.class).add(
				Restrictions.eq("useFlag", UseFlag.Y));

		if (entity != null) {
			Example example = Example.create(entity).enableLike(MatchMode.ANYWHERE).excludeProperty("id")
					.excludeProperty("useFlag").excludeProperty("createDate").excludeProperty("modifyDate");
			criteria.add(example);

			if (entity.getDeviceType() != null) {
				criteria.add(Restrictions.eq("id.deviceType", entity.getDeviceType()));
			}
			if (entity.getTermsType() != null) {
				criteria.add(Restrictions.eq("id.termsType", entity.getTermsType()));
			}
			if (StringUtils.isNotEmpty(entity.getVersion())) {
				criteria.add(Restrictions.like("id.termsVerId", entity.getVersion(), MatchMode.ANYWHERE));
			}
		}

		return criteria;
	}

	@Override
	public TermsMaster get(DeviceType deviceType, TermsType termsType, String termsVerId) {
		Criteria criteria = this.getCurrentSession().createCriteria(TermsMaster.class)
				.add(Restrictions.eq("id.deviceType", deviceType)).add(Restrictions.eq("id.termsType", termsType))
				.add(Restrictions.eq("id.termsVerId", termsVerId));

		return (TermsMaster) criteria.uniqueResult();
	}

	@Override
	public TermsMaster getLastTerms(DeviceType deviceType, TermsType termsType, String termsVerId) {
		Criteria criteria = this.getCurrentSession().createCriteria(TermsMaster.class)
				.add(Restrictions.eq("id.deviceType", deviceType)).add(Restrictions.eq("id.termsType", termsType))
				.add(Restrictions.eq("useFlag", UseFlag.Y))
				.add(Restrictions.le("applyStartYmd", new Date(System.currentTimeMillis())))
				.add(Restrictions.eq("status", ReleaseStatus.RELEASED)).addOrder(Order.desc("applyStartYmd"))
				.setMaxResults(1);

		if (StringUtils.isNotBlank(termsVerId)) {
			criteria.add(Restrictions.lt("id.termsVerId", termsVerId));
		}

		return (TermsMaster) criteria.uniqueResult();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TermsMaster> searchByType(DeviceType deviceType, TermsType termsType) {
		Criteria criteria = this.getCurrentSession().createCriteria(TermsMaster.class)
				.add(Restrictions.eq("id.deviceType", deviceType)).add(Restrictions.eq("id.termsType", termsType))
				.add(Restrictions.eq("useFlag", UseFlag.Y));

		return criteria.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TermsMaster> searchByTerms(TermsMaster termsMaster, int page, int rowPerPage) {
		Criteria criteria = this.getCurrentSession().createCriteria(TermsMaster.class)
				.add(Restrictions.eq("useFlag", UseFlag.Y)).addOrder(Order.desc("id.termsVerId"));

		if (termsMaster.getDeviceType() != null) {
			criteria.add(Restrictions.eq("id.deviceType", termsMaster.getDeviceType()));
		}
		if (termsMaster.getTermsType() != null) {
			criteria.add(Restrictions.eq("id.termsType", termsMaster.getTermsType()));
		}
		if (StringUtils.isNotEmpty(termsMaster.getVersion())) {
			criteria.add(Restrictions.eq("id.termsVerId", termsMaster.getVersion()));
		}
		if (termsMaster.getStatus() != null) {
			criteria.add(Restrictions.eq("status", termsMaster.getStatus()));
		}

		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			criteria.setFirstResult(startRow);
			criteria.setMaxResults(rowPerPage);
		}

		return criteria.list();
	}

	@Override
	public long countByTerms(TermsMaster termsMaster) {
		Criteria criteria = this.getCurrentSession().createCriteria(TermsMaster.class)
				.add(Restrictions.eq("useFlag", UseFlag.Y)).setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

		if (termsMaster.getDeviceType() != null) {
			criteria.add(Restrictions.eq("id.deviceType", termsMaster.getDeviceType()));
		}
		if (termsMaster.getTermsType() != null) {
			criteria.add(Restrictions.eq("id.termsType", termsMaster.getTermsType()));
		}
		if (StringUtils.isNotEmpty(termsMaster.getVersion())) {
			criteria.add(Restrictions.eq("id.termsVerId", termsMaster.getVersion()));
		}
		if (termsMaster.getStatus() != null) {
			criteria.add(Restrictions.eq("status", termsMaster.getStatus()));
		}

		return (Long) criteria.setProjection(Projections.rowCount()).list().get(0);
	}

	@Override
	public int delete(String version, DeviceType deviceType, TermsType type) {
		TermsMaster terms = this.get(deviceType, type, version);
		if (terms != null) {
			return this.delete(terms);
		} else {
			return 0;
		}
	}

	@Override
	public String maxVersion(DeviceType deviceType, TermsType type, String today) {
		String sql = "select max(TERMS_VER_ID) from TBL_TERMS_MASTER where TERMS_VER_ID like ? and DEVICE_TYPE = ? and TERMS_TYPE = ?";
		SQLQuery query = this.getCurrentSession().createSQLQuery(sql);
		query.setString(0, today + "%");
		query.setString(1, deviceType.name());
		query.setString(2, type.name());

		try {
			return (String) query.uniqueResult();
		} catch (NonUniqueResultException e) {
			return null;
		}
	}
}
