package com.hans.sses.board.dao.oper;

import com.hans.sses.board.model.oper.BoadNoticeAdmin;
import org.apache.commons.lang.StringUtils;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;


/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao.oper
 * @Filename     : BoadNoticeAdminDaoHibernate.java
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


public class BoadNoticeAdminDaoHibernate extends HibernateGenericDao<BoadNoticeAdmin, Long> implements BoadNoticeAdminDao {

	@Override
	public Criteria getCriteria(BoadNoticeAdmin entity) {
		Criteria criteria = getCurrentSession().createCriteria(BoadNoticeAdmin.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);
					
			criteria.add(example);
			
			// 날짜
			if (StringUtils.isNotEmpty(entity.getFromDate())) {
				criteria.add( Restrictions.sqlRestriction("? <= DATE_FORMAT({alias}.FST_RG_DT, '%Y%m%d')", entity.getFromDate(), Hibernate.STRING));
				criteria.add( Restrictions.sqlRestriction("? >= DATE_FORMAT({alias}.FST_RG_DT, '%Y%m%d')", entity.getToDate(), Hibernate.STRING));
			}

		}

		return criteria;
	}
}
