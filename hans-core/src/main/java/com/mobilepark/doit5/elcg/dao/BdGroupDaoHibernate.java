package com.mobilepark.doit5.elcg.dao;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.BdGroup;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : BdGroupDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 14.      최초 버전
 * =================================================================================*/

public class BdGroupDaoHibernate extends HibernateGenericDao<BdGroup, Long> implements BdGroupDao {

	@Override
	public Criteria getCriteria(BdGroup entity) {
		Criteria criteria = getCurrentSession().createCriteria(BdGroup.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.EXACT);
			criteria.add(example);
		}

		return criteria;
	}
	
	public List<BdGroup> searchBdGroupName(BdGroup bdGroup, String search) {
		Criteria criteria = getCriteria(bdGroup);
				criteria.add(Restrictions.like("name", search, MatchMode.ANYWHERE));
				criteria.addOrder(Order.desc("name"));		
						
				List<BdGroup> list = criteria.list();
				
		return list;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<BdGroup> selectBdGroupList(String searchKeyword, List<String> bdIdList) {
		String sql = "SELECT DISTINCT bg FROM BdGroup bg LEFT JOIN FETCH bg.bdList bdList ";
		
		boolean isWhere = false;
		
		if (StringUtils.isNotBlank(searchKeyword)) {
			sql += "WHERE bg.name LIKE CONCAT('%', :searchKeyword, '%') ";
			isWhere = true;
		}
		
		if (bdIdList != null && bdIdList.size() > 0) {
			String bdIdStr = String.join(",", bdIdList);
			
			sql += isWhere ? "AND " : "WHERE " + "bdList.bdId IN (" + bdIdStr + ")";
		}
		
		Query query = getCurrentSession().createQuery(sql);
		
		if (StringUtils.isNotBlank(searchKeyword)) {
			query.setParameter("searchKeyword", searchKeyword);
		}
		
		return query.list();
	}
	
	@Override
	public BdGroup selectBdGroupDetail(Long bdGroupId) {
		Query query = getCurrentSession().createQuery("FROM BdGroup bg LEFT JOIN FETCH bg.bdList WHERE bg.bdGroupId = :bdGroupId");
		query.setParameter("bdGroupId", bdGroupId);
		
		return (BdGroup) query.uniqueResult();
	}
	
	@Override
	public Map<String, Object> selectOwnerMain(String adminId) {
		
		String sql = "";
		
		return null;
	}
	
}