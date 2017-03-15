package com.mobilepark.doit5.board.dao.cust;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.board.model.cust.BoadQnaCust;
import com.uangel.platform.dao.HibernateGenericDao;
import org.hibernate.type.StringType;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao.cust
 * @Filename     : BoadQnaCustDaoHibernate.java
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


public class BoadQnaCustDaoHibernate extends HibernateGenericDao<BoadQnaCust, Long> implements BoadQnaCustDao {
	@Override
	public Criteria getCriteria(BoadQnaCust entity) {
		Criteria criteria = getCurrentSession().createCriteria(BoadQnaCust.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("TwriterType");
					
			criteria.add(example);
			
			// 날짜
			if (StringUtils.isNotEmpty(entity.getFromDate())) {
				criteria.add( Restrictions.sqlRestriction("? <= DATE_FORMAT({alias}.FST_RG_DT, '%Y%m%d')", entity.getFromDate(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("? >= DATE_FORMAT({alias}.FST_RG_DT, '%Y%m%d')", entity.getToDate(), Hibernate.STRING));
			}
			
			// 검색어
			if (StringUtils.isNotEmpty(entity.getSearchKey())) {
					criteria.add(Restrictions.or(Restrictions.like("title", entity.getSearchKey(), MatchMode.ANYWHERE),
									Restrictions.like("penName", entity.getSearchKey(), MatchMode.ANYWHERE)));
					return criteria;
			}
			
		}

		return criteria;
	}
	
	
}
