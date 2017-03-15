package com.mobilepark.doit5.push.dao;

import com.mobilepark.doit5.push.model.PushButton;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.dao
 * @Filename     : PushButtonDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
public class PushButtonDaoHibernate extends HibernateGenericDao<PushButton, Integer> implements PushButtonDao {
	@Override
	protected Criteria getCriteria(PushButton entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(PushButton.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example)
					.addOrder(Order.desc("id"));
		}

		return criteria;
	}
}
