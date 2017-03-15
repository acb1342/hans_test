package com.mobilepark.doit5.elcg.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.BrokenReport;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : BrokenReportDaoHibernate.java
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

public class BrokenReportDaoHibernate extends HibernateGenericDao<BrokenReport, Long> implements BrokenReportDao {

	@Override
	public Criteria getCriteria(BrokenReport entity) {
		Criteria criteria = getCurrentSession().createCriteria(BrokenReport.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.EXACT);
					
			criteria.add(example);
		}

		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectReqReportList(Integer page, Integer size, String type, String usid, Integer adminGroupId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(BrokenReport.class).createAlias("bd", "bd", Criteria.LEFT_JOIN);
		
		// 고장신청 리스트 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.sqlProjection("'brok' as type", new String[]{"type"}, new Type[]{Hibernate.STRING}))
			.add(Projections.property("title").as("title"))
			.add(Projections.property("bdGroupName").as("bdGroupName"))
			.add(Projections.property("status").as("status"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("lstChDt").as("lstChDt"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.setFirstResult(size * (page - 1))
			.setMaxResults(size);
		

		
		// 건물주
		if(adminGroupId == 3) {
			criteria.add(Restrictions.eq("rcUsid", usid)) // 접수자ID = 건물주ID
				.addOrder(Order.asc("status")).addOrder(Order.desc("lstChDt"));
			
		// 설치자
		} else if(adminGroupId == 2) {
			criteria.add(Restrictions.eq("wkUsid", usid)) // 조치자ID = 설치자ID 
				.addOrder(Order.asc("status")).addOrder(Order.asc("lstChDt"));
		}
		
		List<Map<String, Object>> brokList = criteria.list();
		
		
		// 카운트 조회
		criteria = this.getCurrentSession().createCriteria(BrokenReport.class);
		criteria.setProjection(Projections.rowCount());
		
		
		// 건물주
		if(adminGroupId == 3) {
			criteria.add(Restrictions.eq("rcUsid", usid)); // 접수자ID = 건물주ID 
			
		// 설치자
		} else if(adminGroupId == 2) {
			criteria.add(Restrictions.eq("wkUsid", usid)); // 조치자ID = 설치자ID 
		}
		
		
		Long totalCnt = (Long)criteria.uniqueResult();
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", brokList.size());
		resultMap.put("totalCnt", totalCnt);
		resultMap.put("reqReportList", brokList);
		
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectReqReportDetail(Long snId, String type) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(BrokenReport.class).createAlias("bd", "bd", Criteria.LEFT_JOIN);
		
		// 상세 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.sqlProjection("'appl' as type", new String[]{"type"}, new Type[]{Hibernate.STRING}))
			.add(Projections.property("body").as("body"))
			.add(Projections.property("status").as("status"))
			.add(Projections.property("rcDt").as("rcDt"))
			.add(Projections.property("odDt").as("odDt"))
			.add(Projections.property("wkDt").as("wkDt"))
			.add(Projections.property("wkUsNm").as("wkUsNm"))
			.add(Projections.property("bd.name").as("name"))
			.add(Projections.property("bd.addr").as("addr"))
			.add(Projections.property("mgmtNo").as("mgmtNo"))
			.add(Projections.property("bdGroupName").as("bdGroupName"))
			.add(Projections.property("chargerGroupNm").as("chargerGroupNm")) // 충전기 그룹명
			.add(Projections.property("chargerId").as("chargerId")) 	// 충전기ID
			.add(Projections.property("adminId").as("adminId"));
		
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(snId));
		
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		if(detail == null) {
			detail = new HashMap<String, Object>();
		}
		return detail;
	}
	
	
	@Override
	public BrokenReport insertReqReport(BrokenReport brokenReport) {
		return create(brokenReport);
	}
	
	
	@Override
	public int updateReqReport(BrokenReport brokenReport) {
		
		Session session = this.getCurrentSession();
	      
		BrokenReport target = (BrokenReport)session.get(BrokenReport.class, brokenReport.getSnId());
		
		int result = 0;
		
		if(target != null) {
			target.setBody(brokenReport.getBody());
			target.setLstChUsid(brokenReport.getLstChUsid());
			target.setLstChDt(brokenReport.getLstChDt());
			
			result = update(target);
		}
		
		return result;
	}
	
	
	@Override
	public int updateReqReportComplete(BrokenReport brokenReport) {
		
		Session session = this.getCurrentSession();
		
		BrokenReport target = (BrokenReport)session.get(BrokenReport.class, brokenReport.getSnId());
		
		int result = 0;
		
		if(target != null) {
			target.setStatus(brokenReport.getStatus());
			target.setWkDt(brokenReport.getWkDt());
			target.setLstChUsid(brokenReport.getLstChUsid());
			target.setLstChDt(brokenReport.getLstChDt());
			
			result = update(target);
		}
		
		return result;
	}
	
	
}
