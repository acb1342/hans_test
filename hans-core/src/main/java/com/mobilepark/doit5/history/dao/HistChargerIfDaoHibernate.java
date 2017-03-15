package com.mobilepark.doit5.history.dao;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Repository;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.history.model.HistChargerIf;
import com.uangel.platform.dao.HibernateGenericDao;

@Repository
public class HistChargerIfDaoHibernate extends HibernateGenericDao<HistChargerIf, Long> implements HistChargerIfDao {

	@SuppressWarnings("deprecation")
	public HistChargerIfDaoHibernate(SessionFactory sessionfactory){
	    super.setSessionFactory(sessionfactory);
	}

	@Override
	protected Criteria getCriteria(HistChargerIf entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(Admin.class);

		if (entity != null) {
			Example example = Example.create(entity)
							.enableLike(MatchMode.ANYWHERE);
			criteria.add(example);
		}
		return criteria;
	}
}
