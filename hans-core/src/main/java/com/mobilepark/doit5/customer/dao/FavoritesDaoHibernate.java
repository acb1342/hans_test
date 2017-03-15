package com.mobilepark.doit5.customer.dao;

import com.mobilepark.doit5.customer.model.Favorites;
import com.mobilepark.doit5.customer.model.Member;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;

import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.dao
 * @Filename     : MemberDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
public class FavoritesDaoHibernate extends HibernateGenericDao<Favorites, Long> implements FavoritesDao {

	@Override
	protected Criteria getCriteria(Favorites entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(Member.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);
			criteria.add(example);

		}
		return criteria;
	}

	@Override
	public Favorites insertFavorites(Favorites favorites) {
		return create(favorites);
	}
	
	@Override
	public int deleteFavorites(Long usid, Long bdId) {
		Query query = this.getCurrentSession().createQuery("DELETE FROM Favorites f WHERE f.member.id = :usid AND f.bd.bdId = :bdId");
		query.setParameter("usid", usid);
		query.setParameter("bdId", bdId);
		return query.executeUpdate();
	}
}
