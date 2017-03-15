package com.mobilepark.doit5.elcg.dao;

import com.mobilepark.doit5.elcg.model.ChargerModel;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.dao
 * @Filename     : ChargerModelDaoHibernate.java
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

public class ChargerModelDaoHibernate extends HibernateGenericDao<ChargerModel, Long> implements ChargerModelDao {

	@Override
	public Criteria getCriteria(ChargerModel entity) {
		Criteria criteria = getCurrentSession().createCriteria(ChargerModel.class);
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
	
}
