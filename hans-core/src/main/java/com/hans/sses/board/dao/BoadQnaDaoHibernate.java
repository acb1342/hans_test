package com.hans.sses.board.dao;

import java.util.List;

import com.hans.sses.board.model.BoadQna;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao
 * @Filename     : BoadQnaDaoHibernate.java
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

public class BoadQnaDaoHibernate extends HibernateGenericDao<BoadQna, Long> implements BoadQnaDao {

	@Override
	public Criteria getCriteria(BoadQna entity) {
		Criteria criteria = getCurrentSession().createCriteria(BoadQna.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("TwriterType");
					
			criteria.add(example);
		}

		return criteria;
	}

	@Override
	public List<BoadQna> searchQnaAllKeys(BoadQna boadQna, String searchValue, int pageNum, int rowPerPage, String fromDate, String toDate) {
		Criteria criteria = getCriteria(boadQna); 
		criteria.add((Restrictions.disjunction()
				.add(Restrictions.like("fstRgUsid",searchValue,MatchMode.ANYWHERE))
				.add(Restrictions.like("title",searchValue,MatchMode.ANYWHERE))
				));
		
		criteria.addOrder(Order.desc("sn_id"));
				
		if(StringUtils.isNotEmpty(fromDate)){
			if(StringUtils.isNotEmpty(toDate)) {
				criteria.add( Restrictions.sqlRestriction("? <= DATE_FORMAT(FST_RG_DT, '%Y%m%d')", fromDate.trim(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("? >= DATE_FORMAT(FST_RG_DT, '%Y%m%d')", toDate.trim(), Hibernate.STRING));
			}
			else {
				criteria.add( Restrictions.sqlRestriction("? <= DATE_FORMAT(FST_RG_DT, '%Y%m%d')", fromDate.trim(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("now() >= FST_RG_DT"));
			}
		}
		
		return criteria.list();
	}

	@Override
	public List<BoadQna> searchQna(BoadQna boadQna, int page, int rowPerPage, String sortCriterion,
										String sortDirection, String fromDate, String toDate) {
		Criteria criteria = getCriteria(boadQna);
		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			criteria.setFirstResult(startRow);
		}
		criteria.addOrder(
				("asc".equalsIgnoreCase(sortDirection)) ? Order.asc(sortCriterion) : Order.desc(sortCriterion));
		criteria.setMaxResults(rowPerPage);

		if(StringUtils.isNotEmpty(fromDate)){
			if(StringUtils.isNotEmpty(toDate)) {
				criteria.add( Restrictions.sqlRestriction("? <= DATE_FORMAT(FST_RG_DT, '%Y%m%d%H%i')", fromDate.trim(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("? >= DATE_FORMAT(FST_RG_DT, '%Y%m%d%H%i')", toDate.trim(), Hibernate.STRING));
			}
			else {
				criteria.add( Restrictions.sqlRestriction("? <= DATE_FORMAT(FST_RG_DT, '%Y%m%d%H%i')", fromDate.trim(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("now() >= FST_RG_DT"));
			}
		}
		
		return criteria.list();
	}
	
}
