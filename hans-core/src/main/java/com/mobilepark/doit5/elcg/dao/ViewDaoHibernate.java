package com.mobilepark.doit5.elcg.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.elcg.model.ViewCustCenter;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ViewDaoHibernate.java
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


public class ViewDaoHibernate extends HibernateGenericDao<ViewCustCenter, Long> implements ViewDao {

	@Override
	public Criteria getCriteria(ViewCustCenter entity) {
		Criteria criteria = getCurrentSession().createCriteria(ViewCustCenter.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.excludeProperty("wkUsid")
					.excludeProperty("rcUsid")
					.excludeProperty("subStatus")
					.enableLike(MatchMode.ANYWHERE);
					
			criteria.add(example);
			criteria.addOrder(Order.asc("subStatus"));
			criteria.addOrder(Order.desc("lstChDt"));
	
			// 설치자
			if ( StringUtils.isNotEmpty(entity.getWkUsid())) criteria.add(Restrictions.eq("wkUsid", entity.getWkUsid()));
			// 건물주
			if ( StringUtils.isNotEmpty(entity.getRcUsid())) criteria.add(Restrictions.eq("rcUsid", entity.getRcUsid()));
			
			if ( entity.getWkType() != null && !("0".equals(entity.getWkType())) ) {
				criteria.add(Restrictions.eq("wkType", entity.getWkType()));
			}
		}
		
		return criteria;
	}
	

}
