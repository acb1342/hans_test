package com.mobilepark.doit5.admin.dao;

import java.util.List;

import com.mobilepark.doit5.admin.model.Admin;
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
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsUserDaoHibernate.java
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
public class AdminDaoHibernate extends HibernateGenericDao<Admin, String> implements AdminDao {
	@Override
	protected Criteria getCriteria(Admin entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(Admin.class);

		if (entity != null) {
			Example example = Example.create(entity).enableLike(MatchMode.ANYWHERE)
					.excludeProperty("allWkCnt")
					.excludeProperty("noCompleWkCnt")
					.excludeProperty("passwd").excludeProperty("email").excludeProperty("area")
					.excludeProperty("fstRgDt").excludeProperty("lstChDt");
			criteria.add(example);

			if (entity.getId() != null) {
				TraceLog.debug("id is not null[%s]", entity.getId());
				criteria.add(Restrictions.like("id", entity.getId(), MatchMode.ANYWHERE));
			}
			if (entity.getEmail() != null) {
				TraceLog.debug("session id is not null[%s]", entity.getEmail());
				criteria.add(Restrictions.eq("id", entity.getEmail()));
			}
           if (entity.getAdminGroup() != null) {
                TraceLog.debug("group is not null[%d]", entity.getAdminGroup().getId());
                criteria.add(Restrictions.eq("adminGroup.id", entity.getAdminGroup().getId()));
            }
           /*
           if (entity.getName() != null) {
        	   criteria.add(Restrictions.like("name", entity.getName(), MatchMode.ANYWHERE));
           }
           */
		}
		return criteria;
	}

	@Override
	public List<Admin> searchByGroup(Integer groupId) {
		return this.searchByGroup(groupId, -1, -1);
	}

	@SuppressWarnings("unchecked")
	public List<Admin> searchByGroup(Integer groupId, int page, int rowPerPage) {
		Criteria criteria = this.getCurrentSession().createCriteria(Admin.class).add(
				Restrictions.eq("adminGroup.id", groupId));

		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			criteria.setFirstResult(startRow);
			criteria.setMaxResults(rowPerPage);
		}

		return criteria.list();
	}

	@Override
	public int searchCountByGroup(Integer groupId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Admin.class)
				.add(Restrictions.eq("adminGroup.id", groupId)).setProjection(Projections.rowCount());

		return ((Long) criteria.uniqueResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Admin> searchByGroupName(String groupName) {
		String sql = "select * from TBL_CMS_USER A, TBL_CMS_GROUP B " +
			" where A.GROUP_ID = B.ID and B.NAME = ? and A.DEFAULT_USER_FLAG = 'Y'";
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(Admin.class).setString(0, groupName);

		return query.list();
	}

	@Override
	public int searchCountByGroupName(String name) {
		String sql = "select count(*) from TBL_CMS_USER A, TBL_CMS_GROUP B " +
			" where A.GROUP_ID = B.ID and B.NAME = ? and A.DEFAULT_USER_FLAG = 'Y'";
		Query query = this.getCurrentSession().createSQLQuery(sql).setString(0, name);

		return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Admin> searchRelatedCp(String mcpId) {
		String sql = "select A.* from TBL_CMS_USER A, TBL_CP_MAP B " +
			" where B.MCP_ID = ? and A.ID = B.CP_ID and A.DEFAULT_USER_FLAG = 'Y'";

		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(Admin.class).setString(0, mcpId);

		return query.list();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Admin> searchByGroupName(String groupName1, String groupName2) {
		String sql = "select * from TBL_CMS_USER A, TBL_CMS_GROUP B " +
			" where A.GROUP_ID = B.ID and (B.NAME = ? OR B.NAME = ?) and A.DEFAULT_USER_FLAG = 'Y'";
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(Admin.class).setString(0, groupName1)
				.setString(1, groupName2);

		return query.list();

	}

	@Override
	public int searchCountByGroupName(String groupName1, String groupName2) {
		String sql = "select count(*) from TBL_CMS_USER A, TBL_CMS_GROUP B " +
			" where A.GROUP_ID = B.ID and (B.NAME = ? OR B.NAME = ?) and A.DEFAULT_USER_FLAG = 'Y'";
		Query query = this.getCurrentSession().createSQLQuery(sql).setString(0, groupName1).setString(1, groupName2);

		return ((Number) query.uniqueResult()).intValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Admin> searchByMCPName(String mcpId) {
		String sql = "select A.*  from TBL_CMS_USER A, TBL_CP_MAP B " +
			" where A.ID = B.CP_ID and B.MCP_ID = ?";
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(Admin.class).setString(0, mcpId);

		return query.list();
	}

	@Override
	public Admin getById(String id) {
		Criteria criteria = this.getCurrentSession().createCriteria(Admin.class)
				.add(Restrictions.eq("id", id));
		return (Admin) criteria.uniqueResult();
	}
}
