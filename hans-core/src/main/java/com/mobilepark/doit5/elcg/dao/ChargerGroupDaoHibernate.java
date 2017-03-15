package com.mobilepark.doit5.elcg.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Charger;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ChargerGroupDaoHibernate.java
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

public class ChargerGroupDaoHibernate extends HibernateGenericDao<ChargerGroup, Long> implements ChargerGroupDao {

	@Override
	public Criteria getCriteria(ChargerGroup entity) {
		Criteria criteria = getCurrentSession().createCriteria(ChargerGroup.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.EXACT);
					
			criteria.add(example);
			
			Criteria joinCriteria = criteria.createCriteria("bd", Criteria.INNER_JOIN);
			// inner join
			if(entity.getBd() != null) {
				if (StringUtils.isNotEmpty(entity.getBd().getAdminId())) {
					joinCriteria.add(Restrictions.eq("adminId", entity.getBd().getAdminId()));
				}
				else {
					joinCriteria.add(Restrictions.eq("bdId", entity.getBd().getBdId()));
				}
				
			}
			
		}

		return criteria;
	}
	
	
	/*@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectBdGroupId(String name, Long chargerGroupId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(ChargerGroup.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("bdGroupId").as("bdGroupId"));
			
			
		criteria.setProjection(pl)
		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.add(Restrictions.eq("chargerGroupId", chargerGroupId));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		return detail;
	}*/
	
	@Override
	public Long selectChargerGroupName(String name, Long bdId, Long chargerGroupId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(ChargerGroup.class);
		criteria.setProjection(Projections.rowCount())
			.add(Restrictions.eq("name", name))
			.add(Restrictions.eq("bd.bdId", bdId));
		
		if(chargerGroupId != null) {
			criteria.add(Restrictions.ne("chargerGroupId", chargerGroupId));
		}
		
		Long result = (Long) criteria.uniqueResult();
		
		return result;
	}
	
	@Override
	public ChargerGroup insertChargerGroup(ChargerGroup chargerGroup) {
		
		return create(chargerGroup);
	}
	
	@Override
	public int updateChargerGroup(ChargerGroup chargerGroup, String usid) {
		
		Session session = this.getCurrentSession();
	      
		ChargerGroup target = (ChargerGroup)session.get(ChargerGroup.class, chargerGroup.getChargerGroupId());
		
		target.setName(chargerGroup.getName());
		target.setCapacity(chargerGroup.getCapacity());
		target.setDescription(chargerGroup.getDescription());
		target.setLstChUsid(usid); 
		target.setLstChDt(new Date());
		
		return update(target);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectGroupNameList(Long bdId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(ChargerGroup.class).createAlias("bd", "bd");
		
		// 리스트 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("chargerGroupId"))
			.add(Projections.property("name").as("name"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("bd.bdId", bdId))
			.addOrder(Order.asc("name"));
		
		List<Map<String, Object>> list = criteria.list();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("groupNameList", list);
		
		return resultMap;
		
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectChargerGroupDetail(Long chargerGroupId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(ChargerGroup.class).createAlias("bd", "bd");
		
		// 충전그 그룹 상세 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("chargerGroupId"))
			.add(Projections.property("name").as("name"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("capacity").as("capacity"))
			.add(Projections.property("description").as("description"))
			.add(Projections.property("bd.bdId").as("bdId")) 
			.add(Projections.property("bd.name").as("bdName"))
			.add(Projections.property("bdGroupName").as("bdGroupName"))
			.add(Projections.property("bd.addr").as("bdAddr"));
		
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(chargerGroupId));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		
		// 충전기 리스트 조회
		criteria = this.getCurrentSession().createCriteria(Charger.class).createAlias("chargerGroup", "chargerGroup");
		
		pl = Projections.projectionList();
		pl.add(Projections.property("name").as("name"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("adminName").as("wkName"));
		
		
		criteria.setProjection(pl)
		.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
		.add(Restrictions.eq("chargerGroup.chargerGroupId", chargerGroupId));
		
		
		List<Map<String, Object>> chargerList = (List<Map<String, Object>>)criteria.list();
		
		detail.put("chargerCnt", chargerList.size());
		detail.put("chargerList", chargerList);
		
		
		return detail;
	}
	
	
}
