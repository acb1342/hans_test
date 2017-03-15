package com.mobilepark.doit5.customer.dao;

import java.util.Date;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.mobilepark.doit5.customer.model.Point;
import com.uangel.platform.dao.HibernateGenericDao;

public class PointDaoHibernate extends HibernateGenericDao<Point, Long> implements PointDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectPoint(Long usid) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(Point.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.property("remainPoint").as("remainPoint"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(usid));
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		return detail;
	}
	
	@Override
	public int updatePoint(Point point) {
		Session session = this.getCurrentSession();
	      
		Point target = (Point)session.get(Point.class, point.getId());
		
		target.setRemainPoint(point.getRemainPoint());
		target.setPaymentDay(point.getPaymentDay());
		target.setLstChUsid(point.getLstChUsid());
		target.setLstChDt(new Date());
		
		return update(target);
	}
	
	@Override
	public Point insertPoint(Point point) {
		return create(point);
	}
	
	@Override
	public Criteria getCriteria(Point entity) {
		return null;
	}
}
