package com.mobilepark.doit5.elcg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Bd;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : BdDaoHibernate.java
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

public class BdDaoHibernate extends HibernateGenericDao<Bd, Long> implements BdDao {

	@Override
	public Criteria getCriteria(Bd entity) {
		
		Criteria criteria = getCurrentSession().createCriteria(Bd.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example);

			// inner join
			Criteria joinCriteria = criteria.createCriteria("bdGroup", Criteria.INNER_JOIN);
			// search
			if (entity.getBdGroup() != null) {
				joinCriteria.add(Restrictions.like("name", entity.getBdGroup().getName(), MatchMode.ANYWHERE));
			}
			
			// 건물주가 보유한 건물만
			if(StringUtils.isNotEmpty(entity.getAdminId())) {
				criteria.add(Restrictions.like("adminId", entity.getAdminId(), MatchMode.EXACT));
			}
			
		}
		return criteria;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectFavoriteList(Long usId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Bd.class).createAlias("bdGroup", "bdGroup");
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("bdId"))
			.add(Projections.property("bdGroup.bdGroupId").as("bdGroupId"))
			.add(Projections.property("bdGroup.name").as("bdGroupName"))
			.add(Projections.property("name").as("name"))
			.add(Projections.property("addr").as("addr"))
			.add(Projections.property("latitude").as("latitude"))
			.add(Projections.property("longitude").as("longitude"))
			.add(Projections.property("status").as("status"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.sqlRestriction("BD_ID IN (SELECT BD_ID FROM TB_CUST_FAVORITES WHERE USID = " + usId + ")"));
		
		List<Map<String, Object>> list = criteria.list();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("favoriteList", list);
		
		return resultMap;
	}

	
	public List<Bd> searchBdName(Bd bd, String search) {
		Criteria criteria = getCriteria(bd);
				criteria.add(Restrictions.like("name", search, MatchMode.EXACT));
				criteria.addOrder(Order.desc("name"));		
						
				List<Bd> list = criteria.list();
				
		return list;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBuildingList(String searchKeyword, String usid, Integer adminGroupId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Bd.class).createAlias("bdGroup", "bdGroup");
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("bdId"))
			.add(Projections.property("bdGroup.name").as("bdGroupName"))
			.add(Projections.property("name").as("name"));
		
		
		// 건물주
		if(adminGroupId == 3) {
			
			pl.add(Projections.property("fstRgDt").as("fstRgDt"))
				.add(Projections.property("addr").as("addr"))
				.add(Projections.property("chargerGroupCnt").as("chargerGroupCnt"))
				.add(Projections.property("chargerCnt").as("chargerCnt"))
				.add(Projections.property("periodDay").as("periodDay"));
			
			criteria.setProjection(pl)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.add(Restrictions.eq("adminId", usid));
			
		} else {
			
			criteria.setProjection(pl).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			
			criteria.add(Restrictions.disjunction()
				.add(Restrictions.like("name", searchKeyword, MatchMode.ANYWHERE))
				.add(Restrictions.like("bdGroup.name", searchKeyword, MatchMode.ANYWHERE)));
			
		}
		
		criteria.addOrder(Order.asc("bdGroup.name"));
		
		
		List<Map<String, Object>> list = criteria.list();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bdList", list);
		
		return resultMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getBuildingDetail(Long bdId, String usid) {
		Criteria criteria = this.getCurrentSession().createCriteria(Bd.class).createAlias("bdGroup", "bdGroup");
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("bdId"))
			.add(Projections.property("name").as("name"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("adminName").as("adminName"))
			.add(Projections.property("addr").as("addr"))
			.add(Projections.property("latitude").as("latitude"))
			.add(Projections.property("longitude").as("longitude"))
			.add(Projections.property("chargerGroupCnt").as("chargerGroupCnt"))
			.add(Projections.property("chargerCnt").as("chargerCnt"))
			.add(Projections.property("periodDay").as("periodDay"))
			.add(Projections.property("bdGroup.name").as("bdGroupName"))
			.add(Projections.property("periodSnId").as("periodSnId"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(bdId));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		
		if(detail == null) {
			detail = new HashMap<String, Object>();
		}
		
		return detail;
	}
	
	/*@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectWkBuildingList(String userId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(Bd.class)
				.createAlias("brokenReportList", "brokenReportList")
				.createAlias("stationApplicationList", "stationApplicationList").createAlias("bdGroup", "bdGroup");
		
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("bdId"))
			.add(Projections.property("name").as("name"))
			.add(Projections.property("addr").as("addr"))
			.add(Projections.property("bdGroup.bdGroupId").as("bdGroupId"));
		
		
		criteria.setProjection(Projections.distinct(pl))
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.disjunction()
				.add(Restrictions.eq("brokenReportList.wkUsid", userId))
				.add(Restrictions.eq("stationApplicationList.wkUsid", userId)));
			
		
			
		List<Map<String, Object>> list = criteria.list();
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("bdList", list);
		
		
		return resultMap;
	}*/
}
