package com.mobilepark.doit5.bill.dao;

import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.mobilepark.doit5.bill.model.BdPeriod;
import com.uangel.platform.dao.HibernateGenericDao;

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

public class BdPeriodDaoHibernate extends HibernateGenericDao<BdPeriod, Long> implements BdPeriodDao {

	@Override
	public Criteria getCriteria(BdPeriod entity) {
		Criteria criteria = getCurrentSession().createCriteria(BdPeriod.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.EXACT);
					
			if (entity.getBdId() != null) {
				criteria.add(Restrictions.eq("bdId", entity.getBdId()));
			}
			
			criteria.add(example);
		}

		return criteria;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectBdCalcdayDetail(Long periodSnId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(BdPeriod.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.property("bdId").as("bdId"))
			.add(Projections.property("startYmd").as("startYmd"))
			.add(Projections.property("endYmd").as("endYmd"))
			.add(Projections.property("meterYmd").as("meterYmd"))
			.add(Projections.property("periodYmd").as("periodYmd"))
			.add(Projections.property("periodDay").as("periodDay"))
			.add(Projections.property("fstRgUsid").as("fstRgUsid"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("lstChUsid").as("lstChUsid"))
			.add(Projections.property("lstChDt").as("lstChDt"));
		
		criteria.setProjection(pl)
			.setMaxResults(1)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(periodSnId))
			.addOrder(Order.desc("fstRgDt"));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		return detail;
	}
	
	
	@Override
	public BdPeriod insertBdCalcday(BdPeriod bdCalcday) {
		return create(bdCalcday);
	}
	
	
	@Override
	public int updateBdCalcday(BdPeriod bdPeriod) {
		
		Session session = this.getCurrentSession();
		
		BdPeriod target = (BdPeriod)session.get(BdPeriod.class, bdPeriod.getSnId());
		
		
		target.setPeriodDay(bdPeriod.getPeriodDay());
		target.setLstChUsid(bdPeriod.getLstChUsid());
		target.setLstChDt(bdPeriod.getLstChDt());
		
		return update(target);
	}
	
}
