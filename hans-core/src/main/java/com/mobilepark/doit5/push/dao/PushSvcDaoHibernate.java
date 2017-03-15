package com.mobilepark.doit5.push.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.common.PushSendType;
import com.mobilepark.doit5.common.UseFlag;
import com.mobilepark.doit5.push.model.PushSvc;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.dao
 * @Filename     : PushSvcDaoHibernate.java
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
public class PushSvcDaoHibernate extends HibernateGenericDao<PushSvc, Integer> implements PushSvcDao {
	@Override
	protected Criteria getCriteria(PushSvc entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(PushSvc.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example)
					.addOrder(Order.desc("id"));
		}

		return criteria;
	}

	@Override
	public List<PushSvc> searchReservedSendList() {
		Criteria criteria = this.getCurrentSession().createCriteria(PushSvc.class)
				.add(Restrictions.eq("useFlag", UseFlag.Y))
				.add(Restrictions.eq("sendType", PushSendType.RESERVATION));

		return criteria.list();
	}
}
