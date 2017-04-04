package com.hans.sses.board.dao;

import java.util.List;

import com.hans.sses.board.model.Board;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : BoardDaoHibernate.java
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
 * =================================================================================
 */

public class BoardDaoHibernate extends HibernateGenericDao<Board, Long> implements BoardDao {

	@Override
	public Criteria getCriteria(Board entity) {
		Criteria criteria = getCurrentSession().createCriteria(Board.class);
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

	// QnA 전체로 검색
	@Override
	public List<Board> searchAll(Board board, String searchValue, int page, int rowPerPage) {
		Criteria criteria = getCriteria(board); 
		criteria.add((Restrictions.disjunction()
				.add(Restrictions.like("user_id",searchValue,MatchMode.ANYWHERE))
				.add(Restrictions.like("title",searchValue,MatchMode.ANYWHERE))
				.add(Restrictions.like("content",searchValue,MatchMode.ANYWHERE))
				));
		criteria.addOrder(Order.desc("sequence"));
				
		List<Board> boardlist = criteria.list();
		
		return boardlist;
	}
	
}
