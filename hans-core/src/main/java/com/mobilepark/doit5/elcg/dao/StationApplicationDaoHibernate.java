package com.mobilepark.doit5.elcg.dao;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.mobilepark.doit5.elcg.model.BrokenReport;
import com.mobilepark.doit5.elcg.model.StationApplication;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
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
 * @Filename     : StationApplicationDaoHibernate.java
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

public class StationApplicationDaoHibernate extends HibernateGenericDao<StationApplication, Long> implements StationApplicationDao {

	@Override
	public Criteria getCriteria(StationApplication entity) {
		Criteria criteria = getCurrentSession().createCriteria(StationApplication.class);
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
		
		Criteria criteria = this.getCurrentSession().createCriteria(StationApplication.class).createAlias("bd", "bd", Criteria.LEFT_JOIN);
		
		// 충전소신청 리스트 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.sqlProjection("'appl' as type", new String[]{"type"}, new Type[]{Hibernate.STRING}))
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
		
		List<Map<String, Object>> applList = criteria.list();
		
		
		// 카운트 조회
		criteria = this.getCurrentSession().createCriteria(StationApplication.class);
		criteria.setProjection(Projections.rowCount());
		
		// 건물주
		if(adminGroupId == 3) {
			criteria.add(Restrictions.eq("rcUsid", usid)); // 접수자ID = 건물주ID
			
		// 설치자
		} else {
			criteria.add(Restrictions.eq("wkUsid", usid)); // 조치자ID = 설치자ID
		}
		
		Long totalCnt = (Long)criteria.uniqueResult();
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", applList.size());
		resultMap.put("totalCnt", totalCnt);
		resultMap.put("reqReportList", applList);
		
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectReqReportAllList(Integer page, Integer size, String usid, Integer adminGroupId) {
	
		/*
		 * TB_ELCG_STATION_APPLICATION	충전소신청 테이블 리스트 조회
		 */
		String sql = "SELECT "
					   + "  snId as snId "
					   + ", title as title "
					   + ", bdGroupName as bdGroupName "
					   + ", status as status "
					   + ", 'appl' as type "
					   + ", fstRgDt as fstRgDt "
					   + ", lstChDt as lstChDt "
					   + "FROM StationApplication ";
		
		
		if(adminGroupId == 3) { // 건물주
			sql += "WHERE rcUsid = :usid ";
		} else if(adminGroupId == 2) { // 설치자
			sql += "WHERE wkUsid = :usid AND status IN ('407102', '407103')";
		}
	   
		
		Query query = getCurrentSession().createQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setParameter("usid", usid);

		
		List<Map<String, Object>> list = query.list();
		
		
		/*
		 * TB_ELCG_BROKEN_REPORT 고장신고 테이블 리스트 조회
		 */
		sql = "SELECT "
					   + "  snId as snId "
					   + ", title as title "
					   + ", bdGroupName as bdGroupName "
					   + ", status as status "
					   + ", 'brok' as type "
					   + ", fstRgDt as fstRgDt "
					   + ", lstChDt as lstChDt "
					   + "FROM BrokenReport  ";
		
		
		if(adminGroupId == 3) {
			sql += "WHERE rcUsid = :usid ";
		} else if(adminGroupId == 2) {
			sql += "WHERE wkUsid = :usid AND status IN ('409102', '409103')";
		}

		
		query = getCurrentSession().createQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setParameter("usid", usid);


		list.addAll(query.list());
		
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<String, Object> map1, Map<String, Object> map2) {
				
				String status1 = (String) map1.get("status");
				if (StringUtils.equals(status1, "409101")) {
					status1 = "407101";
				} else if (StringUtils.equals(status1, "409102")) {
					status1 = "407102";
				} else if (StringUtils.equals(status1, "409103")) {
					status1 = "407103";
				}
				
				String status2 = (String) map2.get("status");
				if (StringUtils.equals(status2, "409101")) {
					status2 = "407101";
				} else if (StringUtils.equals(status2, "409102")) {
					status2 = "407102";
				} else if (StringUtils.equals(status2, "409103")) {
					status2 = "407103";
				}
				
				if (StringUtils.equals(status1, status2)) {
					Date dt1 = null;
					Date dt2 = null;
					
					if(adminGroupId == 3) { // 건물주
						dt1 = (Date)map1.get("lstChDt");
						dt2 = (Date)map2.get("lstChDt");
						
						if (dt1.getTime() > dt2.getTime()) {
							return -1;
						} else {
							return 1;
						}
						
					} else if(adminGroupId == 2) { // 설치자
						dt1 = (Date)map1.get("lstChDt");
						dt2 = (Date)map2.get("lstChDt");
						
						if (dt1.getTime() < dt2.getTime()) {
							return -1;
						} else {
							return 1;
						}
						
					} else {
						return 0;
					}
					
				} else {
					if (Integer.parseInt(status1) < Integer.parseInt(status2)) {
						return -1;
					} else {
						return 1;
					}
				}
			}
		});
		
		list = list.stream()
				  .skip((page - 1) * size)
				  .limit(size)
				  .collect(Collectors.toCollection(ArrayList::new));

		Criteria criteria = this.getCurrentSession().createCriteria(StationApplication.class);
		criteria.setProjection(Projections.rowCount());
		
		// 건물주
		if(adminGroupId == 3) {
			criteria.add(Restrictions.eq("rcUsid", usid)); // 접수자ID = 건물주ID
			
		// 설치자
		} else if(adminGroupId == 2) {
			criteria.add(Restrictions.eq("wkUsid", usid)); // 조치자ID = 설치자ID
			criteria.add(Restrictions.in("status", new String[]{"407102", "407103"}));
		}
				
		Long totalCnt = (Long)criteria.uniqueResult();
		
		
		criteria = this.getCurrentSession().createCriteria(BrokenReport.class);
		criteria.setProjection(Projections.rowCount());
		
		// 건물주
		if(adminGroupId == 3) {
			criteria.add(Restrictions.eq("rcUsid", usid)); // 접수자ID = 건물주ID
			
		// 설치자
		} else if(adminGroupId == 2) {
			criteria.add(Restrictions.eq("wkUsid", usid)); // 조치자ID = 설치자ID
			criteria.add(Restrictions.in("status", new String[]{"409102", "409103"}));
		}
				
				
		totalCnt += (Long)criteria.uniqueResult();
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", totalCnt);
		resultMap.put("reqReportList", list);
		
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectReqReportDetail(Long snId, String type) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(StationApplication.class).createAlias("bd", "bd", Criteria.LEFT_JOIN);
		
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
			.add(Projections.property("addr").as("addr"))
			.add(Projections.property("bd.latitude").as("latitude"))
			.add(Projections.property("bd.longitude").as("longitude"))
			.add(Projections.property("bdGroupName").as("bdGroupName"))
			.add(Projections.property("chargerGroupCnt").as("chargerGroupCnt")) 		// 충전기 그룹개수
			.add(Projections.property("chargerCnt").as("chargerCnt")) 	// 충전기개수
			.add(Projections.property("calcDay").as("calcDay")) 				// 전기요금납기일
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
	
	public StationApplication insertReqReport(StationApplication stationApplication) {
		return create(stationApplication);
	}
	
	
	@Override
	public int updateReqReport(StationApplication stationApplication) {
		
		Session session = this.getCurrentSession();
	      
		StationApplication target = (StationApplication)session.get(StationApplication.class, stationApplication.getSnId());
		
		int result = 0;
		
		if(target != null) {
			target.setBody(stationApplication.getBody());
			target.setLstChUsid(stationApplication.getLstChUsid());
			target.setLstChDt(stationApplication.getLstChDt());
			
			result = update(target);
		}
		
		return result;
	}
	
	
	@Override
	public int updateReqReportComplete(StationApplication stationApplication) {
		
		Session session = this.getCurrentSession();
		
		StationApplication target = (StationApplication)session.get(StationApplication.class, stationApplication.getSnId());
		
		int result = 0;
		
		if(target != null) {
			target.setStatus(stationApplication.getStatus());
			target.setWkDt(stationApplication.getWkDt());
			target.setLstChUsid(stationApplication.getLstChUsid());
			target.setLstChDt(stationApplication.getLstChDt());
			
			result = update(target);
		}
		
		return result;
	}
}
