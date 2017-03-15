package com.mobilepark.doit5.board.dao.cust;


import com.mobilepark.doit5.board.model.cust.BoadNoticeCust;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import com.uangel.platform.dao.HibernateGenericDao;


/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao.cust
 * @Filename     : BoadNoticeCustDaoHibernate.java
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

public class BoadNoticeCustDaoHibernate extends HibernateGenericDao<BoadNoticeCust, Long> implements BoadNoticeCustDao {

	@Override
	public Criteria getCriteria(BoadNoticeCust entity) {
		Criteria criteria = getCurrentSession().createCriteria(BoadNoticeCust.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("url")
					.excludeProperty("description");
			criteria.add(example);
		}

		return criteria;
	}

		
}
