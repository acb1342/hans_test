package com.mobilepark.doit5.elcg.dao;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.mobilepark.doit5.elcg.model.ChargerList;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ChargerListDaoHibernate.java
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

public class ChargerListDaoHibernate extends HibernateGenericDao<ChargerList, String> implements ChargerListDao {

	@Override
	public Criteria getCriteria(ChargerList entity) {
		Criteria criteria = getCurrentSession().createCriteria(ChargerList.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);
					
			criteria.add(example);
			
			if (entity.getMgmtNo() != null) {
				criteria.add(Restrictions.like("mgmtNo", entity.getMgmtNo(), MatchMode.EXACT));
			}
			if (entity.getSerialNo() != null) {
				criteria.add(Restrictions.like("serialNo", entity.getSerialNo(), MatchMode.ANYWHERE));
			}	
		}

		return criteria;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectChargerInfo(String serialNo) throws Exception {
		
		Criteria criteria = this.getCurrentSession().createCriteria(ChargerList.class).createAlias("model", "model");
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("chargerId"))
			.add(Projections.property("mgmtNo").as("mgmtNo"))
			.add(Projections.property("serialNo").as("serialNo"))
			.add(Projections.property("model.model").as("modelName"))
			.add(Projections.property("capacity").as("capacity"))
			.add(Projections.property("status").as("status"))
			.add(Projections.sqlProjection("CASE WHEN this_.CHARGE_RATE = '402201' THEN '완속' ELSE '급속' END AS chargeRate", new String[]{"chargeRate"}, new Type[]{Hibernate.STRING}));
		
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("serialNo", serialNo));
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		
		if(detail == null) {
			detail = new HashMap<String, Object>();
			detail.put("errorCd", "428007");
			detail.put("errorMsg", "일치하는 데이터가 없습니다.");
			return detail;
		}
		
		if(!"402101".equals((String)detail.get("status"))) {
			detail = new HashMap<String, Object>();
			detail.put("errorCd", "428008");
			detail.put("errorMsg", "이미 설치된 충전기의 S/N입니다.");
			return detail;
		}
		
		return detail;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectChargerSerialNo(String serialNo) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(ChargerList.class);
		
		ProjectionList pl = Projections.projectionList()
			.add(Projections.property("status").as("status"))
			.add(Projections.property("serialNo").as("serialNo"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("serialNo", serialNo));
		
		return (Map<String, Object>)criteria.uniqueResult();
	}
	
	@Override
	public int updateChargerStatus(ChargerList chargerList) {
		
		Session session = this.getCurrentSession();
	      
		ChargerList target = (ChargerList)session.get(ChargerList.class, chargerList.getChargerId());
		
		target.setStatus(chargerList.getStatus());
		target.setLstChUsid(chargerList.getLstChUsid());
		target.setLstChDt(chargerList.getLstChDt());
		
		return update(target);
	}
	
}
