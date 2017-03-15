package com.mobilepark.doit5.elcg.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.Charger;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.SQLQuery;
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
import com.uangel.platform.model.Pageable;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ChargerDaoHibernate.java
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

public class ChargerDaoHibernate extends HibernateGenericDao<Charger, String> implements ChargerDao {

	@Override
	public Criteria getCriteria(Charger entity) {
		Criteria criteria = getCurrentSession().createCriteria(Charger.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.excludeProperty("wkName")
					.excludeProperty("fstRgUsid")
					.enableLike(MatchMode.EXACT);
			criteria.add(example);
			
			// 날짜
			if(StringUtils.isNotEmpty(entity.getFromDate())) {
				criteria.add( Restrictions.sqlRestriction("? <= DATE_FORMAT({alias}.FST_RG_DT, '%Y%m%d')", entity.getFromDate(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("? >= DATE_FORMAT({alias}.FST_RG_DT, '%Y%m%d')", entity.getToDate(), Hibernate.STRING));
			}
		}
		
		// selectBox
		if(entity != null && entity.getIsSelectBox() != null && entity.getIsSelectBox().equals("y")) {
			criteria.add(Restrictions.eq("chargerGroup", entity.getChargerGroup()));
			return criteria;
		}
		
		// bdGroup, bd, chargerGroup join
		if (entity != null && 
				entity.getChargerGroup() != null &&
				entity.getChargerGroup().getBd() != null)
		{

			Criteria chargerGroupCriteria = criteria.createCriteria("chargerGroup", Criteria.INNER_JOIN);
			Criteria bdCriteria = chargerGroupCriteria.createCriteria("bd", Criteria.INNER_JOIN);
			bdCriteria.add(Restrictions.eq("bdId", entity.getChargerGroup().getBd().getBdId()));

			if (entity.getChargerGroup().getBd().getBdGroup() != null) {
				Criteria bdGroupCriteria = bdCriteria.createCriteria("bdGroup", Criteria.INNER_JOIN);
				bdGroupCriteria.add(Restrictions.eq("bdGroupId", entity.getChargerGroup().getBd().getBdGroup().getBdGroupId()));
			}
		}
		
		// 설치자
		if (StringUtils.isNotEmpty(entity.getFstRgUsid())) criteria.add(Restrictions.eq("fstRgUsid", entity.getFstRgUsid()));
		// 건물주
		/*
		if (entity != null && 
				entity.getChargerGroup() != null &&
				entity.getChargerGroup().getBd() != null &&
				StringUtils.isNotEmpty(entity.getChargerGroup().getBd().getAdminId())) {

			Criteria chargerGroupCriteria = criteria.createCriteria("chargerGroup", Criteria.INNER_JOIN);
			Criteria bdCriteria = chargerGroupCriteria.createCriteria("bd", Criteria.INNER_JOIN);
			bdCriteria.add(Restrictions.eq("adminId", entity.getChargerGroup().getBd().getAdminId()));
		}	
		*/	
		
		return criteria;
	}
	
	@Override
	public int getChargerCount(String adminId) {
        List<Charger> list = this.getChargerList(adminId, null);
        if (list == null) return 0;
        return list.size();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Charger> getChargerList(String adminId, Pageable pageable) {
		
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("SELECT ");
		strBuf.append(" TB_ELCG_CHARGER.CHARGER_ID AS CHARGER_ID, ");
//		strBuf.append(" TB_ELCG_CHARGER.MGMT_NO, ");
//		strBuf.append(" TB_ELCG_CHARGER.NAME, ");
//		strBuf.append(" TB_ELCG_CHARGER.CAPACITY, ");
//		strBuf.append(" TB_ELCG_CHARGER.DESCRIPTION, ");
//		strBuf.append(" TB_ELCG_CHARGER.CHARGE_RATE, ");
//		strBuf.append(" TB_ELCG_CHARGER.TP_PASSCODE, ");
		strBuf.append(" TB_ELCG_CHARGER.STATUS AS STATUS ");
		//strBuf.append(" TB_MGMT_ADMIN.NAME ");
		strBuf.append(" FROM ");
		strBuf.append("	TB_ELCG_CHARGER, TB_ELCG_CHARGER_GROUP, TB_ELCG_BD, TB_MGMT_ADMIN ");
		strBuf.append("WHERE ");
		strBuf.append("	TB_MGMT_ADMIN.ADMIN_ID = :adminId ");
		strBuf.append("AND ");
		strBuf.append("	TB_ELCG_CHARGER.CHARGER_GROUP_ID = TB_ELCG_CHARGER_GROUP.CHARGER_GROUP_ID ");
		strBuf.append("AND ");
		strBuf.append("	TB_ELCG_CHARGER_GROUP.BD_ID = TB_ELCG_BD.BD_ID ");
		strBuf.append("AND ");
		strBuf.append("	TB_ELCG_BD.ADMIN_ID = TB_MGMT_ADMIN.ADMIN_ID ");
		
		String query = strBuf.toString();
		
        SQLQuery criteria =  super.getCurrentSession().createSQLQuery(query).addEntity(Charger.class);
        criteria.setString("adminId", adminId);
        
        if (pageable != null)
        {
            criteria.setFirstResult(pageable.getOffset());
            criteria.setMaxResults(pageable.getRowPerPage());
        }
        
        return criteria.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectChargerList(Integer adminGroupId, String adminId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(Charger.class);
		
		// 리스트 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("chargerId"))
			.add(Projections.property("name").as("name"))
			.add(Projections.property("status").as("status"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("adminName").as("wkName"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
		
		
		String sql = "";
		
		// 건물주
		if(adminGroupId == 3) { 
			sql = "CHARGER_GROUP_ID IN "
					+ "(SELECT CHARGER_GROUP_ID FROM TB_ELCG_CHARGER_GROUP ecg WHERE ecg.BD_ID IN "
					+ "	(SELECT BD_ID FROM TB_ELCG_BD WHERE ADMIN_ID = '" + adminId + "'))";
					
			criteria.add(Restrictions.sqlRestriction(sql));
			
			
		// 설치자
		} else {
			sql = "CHARGER_GROUP_ID IN "
					+ "(SELECT CHARGER_GROUP_ID FROM TB_ELCG_CHARGER_GROUP ecg WHERE ecg.BD_ID IN "
					+ "	(SELECT BD_ID FROM TB_ELCG_STATION_APPLICATION WHERE WK_USID = '" + adminId + "') OR ecg.BD_ID IN "
					+ "	(SELECT BD_ID FROM TB_ELCG_BROKEN_REPORT  WHERE WK_USID = '" + adminId + "'))";
			
			criteria.add(Restrictions.sqlRestriction(sql));
			
		}
		
		criteria.addOrder(Order.desc("fstRgDt"));
		
		List<Map<String, Object>> list = criteria.list();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("chargerList", list);
		
		return resultMap;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectChargerDetail(String chargerId) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(Charger.class).createAlias("chargerGroup", "chargerGroup");
		
		// 상세 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("chargerId"))
			.add(Projections.property("name").as("name"))
			.add(Projections.property("mgmtNo").as("mgmtNo"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("serialNo").as("serialNo"))
			.add(Projections.property("modelName").as("modelName"))
			.add(Projections.sqlProjection("CASE WHEN CHARGE_RATE = '" + "402201" + "' THEN '완속' ELSE '급속' END AS chargeRate", new String[]{"chargeRate"}, new Type[]{Hibernate.STRING}))
			.add(Projections.property("capacity").as("capacity"))
			.add(Projections.property("chargerGroup.name").as("chargerGroupName"))
			.add(Projections.property("bdName").as("bdName"))
			.add(Projections.property("bdGroupName").as("bdGroupName"))
			.add(Projections.property("bdAddr").as("bdAddr"))
			.add(Projections.property("description").as("description"));
		
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(chargerId));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();

		
		if(detail == null) {
			detail = new HashMap<String, Object>();
		}
		
		return detail;
	}
	
	
	@Override
	public Charger insertCharger(Charger charger) {
		
		return create(charger);
	}
	
	
	@Override
	public int updateCharger(Charger charger, String usid) {
		
		Session session = this.getCurrentSession();
	      
		Charger target = (Charger)session.get(Charger.class, charger.getChargerId());
		
		target.setName(charger.getName());
		target.setCapacity(charger.getCapacity());
		target.setDescription(charger.getDescription());
		target.setLstChUsid(usid);
		target.setLstChDt(new Date());
		
		return update(target);
	}
	
	@Override
	public List<Charger> searchByDate(Charger charger, int page, int rowPerPage, String sortCriterion,
										String sortDirection, String fromDate, String toDate) {
		Criteria criteria = getCriteria(charger);
		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			criteria.setFirstResult(startRow);
		}
		criteria.addOrder(
				("asc".equalsIgnoreCase(sortDirection)) ? Order.asc(sortCriterion) : Order.desc(sortCriterion));
		criteria.setMaxResults(rowPerPage);

		if(StringUtils.isNotEmpty(fromDate)){
			if(StringUtils.isNotEmpty(toDate)) {
				criteria.add( Restrictions.sqlRestriction("? < DATE_FORMAT(FST_RG_DT, '%Y%m%d')", fromDate.trim(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("? >= DATE_FORMAT(FST_RG_DT, '%Y%m%d')", toDate.trim(), Hibernate.STRING));
			}
			else {
				criteria.add( Restrictions.sqlRestriction("? < DATE_FORMAT(FST_RG_DT, '%Y%m%d')", fromDate.trim(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("now() >= FST_RG_DT"));
			}
		}
		
		return criteria.list();
	}
	
}
