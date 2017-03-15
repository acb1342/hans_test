package com.mobilepark.doit5.sender.dao;

import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.sender.model.SenderInfo;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.dao
 * @Filename     : SenderInfoDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
public class SenderInfoDaoHibernate extends HibernateGenericDao<SenderInfo, String> implements SenderInfoDao {
	@Override
	protected Criteria getCriteria(SenderInfo entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(SenderInfo.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);
			criteria.add(example);

			if (entity.getId() != null) {
				criteria.add(Restrictions.like("id", entity.getId(), MatchMode.ANYWHERE));
			}
		}

		return criteria;
	}
}
