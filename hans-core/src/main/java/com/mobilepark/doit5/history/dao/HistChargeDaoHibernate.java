package com.mobilepark.doit5.history.dao;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mobilepark.doit5.history.model.HistCharge;
import com.uangel.platform.dao.HibernateGenericDao;

@Repository
public class HistChargeDaoHibernate extends HibernateGenericDao<HistCharge, Long> implements HistChargeDao {

	@SuppressWarnings("deprecation")
	public HistChargeDaoHibernate(SessionFactory sessionfactory){
	    super.setSessionFactory(sessionfactory);
	}

	@Override
	protected Criteria getCriteria(HistCharge entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(HistCharge.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);

			criteria.add(example);
			
			if (entity.getId() != null) {
				criteria.add(Restrictions.eq("usid", entity.getUsid()))
						.add(Restrictions.ge("startDt", entity.getStartDt()))
						.add(Restrictions.le("startDt", entity.getEndDt()));
			}
		}

		return criteria;
	}

	@Override
	public Long searchCountByMonthly(String usid, String fromDate, String toDate) {
		Query query = super.getCurrentSession().getNamedQuery("searchCountByMonthly");
		query.setString("usid", usid);
		query.setString("fromDate", fromDate);
		query.setString("toDate", toDate);

		if (query.uniqueResult() == null) return 0L;
		
		return ((BigInteger) query.uniqueResult()).longValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> searchByMonthly(String usid, String fromDate, String toDate, int page, int rowPerPage) {
		Query query = super.getCurrentSession().getNamedQuery("searchByMonthly");
		
		query.setString("usid", usid);
		query.setString("fromDate", fromDate);
		query.setString("toDate", toDate);
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			query.setFirstResult(startRow);
		}
		query.setMaxResults(rowPerPage);

		return query.list();
	}

}
