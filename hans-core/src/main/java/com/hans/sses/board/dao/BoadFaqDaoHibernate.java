package com.hans.sses.board.dao;

import com.hans.sses.board.model.BoadFaq;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao
 * @Filename     : BoadFaqDaoHibernate.java
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

public class BoadFaqDaoHibernate extends HibernateGenericDao<BoadFaq, Long> implements BoadFaqDao {

	@Override
	public Criteria getCriteria(BoadFaq entity) {
		Criteria criteria = getCurrentSession().createCriteria(BoadFaq.class);
		if (entity != null) {
		
			// 노출대상 별 검색
			Criterion custCrit = null;
			Criterion ownerCrit = null;
			Criterion instCrit = null;
			Disjunction disjunction = Restrictions.disjunction();
			if (entity.getCust_yn() != null || entity.getOwner_yn() != null || entity.getInstaller_yn() != null) {
				if (entity.getCust_yn() != null && entity.getCust_yn().equals("Y")) {
					custCrit = Restrictions.eq("cust_yn", entity.getCust_yn());
					disjunction.add(custCrit);
				}
				if (entity.getOwner_yn() != null && entity.getOwner_yn().equals("Y")) {
					ownerCrit = Restrictions.eq("owner_yn", entity.getOwner_yn());
					disjunction.add(ownerCrit);
				}
				if (entity.getInstaller_yn() != null && entity.getInstaller_yn().equals("Y")) {
					instCrit = Restrictions.eq("installer_yn", entity.getInstaller_yn());
					disjunction.add(instCrit);
				}
			}
			criteria.add(disjunction);
			
			// 제목 검색
			if (StringUtils.isNotEmpty(entity.getQuestion())) {
			criteria.add(Restrictions.like("question", entity.getQuestion(), MatchMode.ANYWHERE));
			}
			
			// 카테고리 별
			if (StringUtils.isNotEmpty(entity.getCategory())) {
				TraceLog.debug("CATEGORY : " + entity.getCategory());
				criteria.add(Restrictions.like("category", entity.getCategory(), MatchMode.EXACT));
			}
	
		}

		return criteria;
	}

	
}
