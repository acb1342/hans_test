package com.mobilepark.doit5.board.dao;

import com.mobilepark.doit5.board.model.AppVer;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao
 * @Filename     : AppVerDaoHibernate.java
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


public class AppVerDaoHibernate extends HibernateGenericDao<AppVer, Long> implements AppVerDao {

	@Override
	public Criteria getCriteria(AppVer entity) {
		Criteria criteria = getCurrentSession().createCriteria(AppVer.class);
		if (entity != null) {
			
			if (entity.getTargetType() != null && (entity.getTargetType().equals("101203") || entity.getTargetType().equals("101204"))) {
				criteria.add(Restrictions.or(Restrictions.eq("targetType", "101203"), Restrictions.eq("targetType", "101204")));
				return criteria;
			}
			else {
				criteria.add(Restrictions.eq("targetType", "101206"));
			}
			
		}
		return criteria;
	}
	
}
