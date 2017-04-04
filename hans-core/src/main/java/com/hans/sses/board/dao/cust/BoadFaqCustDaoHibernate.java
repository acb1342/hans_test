package com.hans.sses.board.dao.cust;

import com.hans.sses.board.model.cust.BoadFaqCust;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.uangel.platform.dao.HibernateGenericDao;


public class BoadFaqCustDaoHibernate extends HibernateGenericDao<BoadFaqCust, Long> implements BoadFaqCustDao {

	@Override
	public Criteria getCriteria(BoadFaqCust entity) {
		Criteria criteria = getCurrentSession().createCriteria(BoadFaqCust.class);
		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("url")
					.excludeProperty("description");
			criteria.add(example);
			
			// 제목 검색
			if (StringUtils.isNotEmpty(entity.getQuestion())) {
			criteria.add(Restrictions.like("question", entity.getQuestion(), MatchMode.ANYWHERE));
			}
			
			// 카테고리 별
			if (StringUtils.isNotEmpty(entity.getCategory())) {
				criteria.add(Restrictions.like("category", entity.getCategory(), MatchMode.EXACT));
			}
	
		}

		return criteria;
	}

	
}
