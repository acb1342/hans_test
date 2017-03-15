package com.mobilepark.doit5.board.dao;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.board.model.BoadNotice;
import com.uangel.platform.dao.HibernateGenericDao;
import org.hibernate.type.StringType;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao
 * @Filename     : BoadNoticeDaoHibernate.java
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

public class BoadNoticeDaoHibernate extends HibernateGenericDao<BoadNotice, Long> implements BoadNoticeDao {

	@Override
	public Criteria getCriteria(BoadNotice entity) {
		Criteria criteria = getCurrentSession().createCriteria(BoadNotice.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("url")
					.excludeProperty("description")
					.excludeProperty("createDate").excludeProperty("modifyDate");
			criteria.add(example);
		}

		return criteria;
	}

	@Override
	public List<BoadNotice> search(BoadNotice boadNotice, int page, int rowPerPage, String sortCriterion,
			String sortDirection, String fromDate, String toDate) {
		
			Criteria criteria = getCriteria(boadNotice);
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
