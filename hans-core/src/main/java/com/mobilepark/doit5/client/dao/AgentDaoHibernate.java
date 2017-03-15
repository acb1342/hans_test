package com.mobilepark.doit5.client.dao;

import com.mobilepark.doit5.client.model.Agent;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.dao
 * @Filename     : AgentDaoHibernate.java
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
public class AgentDaoHibernate extends HibernateGenericDao<Agent, Integer> implements AgentDao {
	@Override
	protected Criteria getCriteria(Agent entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(Agent.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("regDate")
					.excludeProperty("updateDate");

			criteria.add(example);
		}

		return criteria;
	}
}
