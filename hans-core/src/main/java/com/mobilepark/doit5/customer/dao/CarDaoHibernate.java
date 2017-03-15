package com.mobilepark.doit5.customer.dao;

import java.util.Date;
import java.util.Map;

import com.mobilepark.doit5.customer.model.Car;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.uangel.platform.dao.HibernateGenericDao;

public class CarDaoHibernate extends HibernateGenericDao<Car, Long> implements CarDao {
	
	@Override
	public Car insertCar(Car car) {
		return create(car);
	}
	
	@Override
	public void updateCarNumber(Car car) {
		Criteria criteria = this.getCurrentSession().createCriteria(Car.class);
		
		criteria.add(Restrictions.eq("member.id", car.getMember().getId()));
		
		Car target = (Car)criteria.uniqueResult();
		
		target.setCarNo(car.getCarNo());
		target.setLstChUsid(car.getLstChUsid());
		target.setLstChDt(new Date());
		
		update(target);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectCarDetail(Long usid) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(Car.class).createAlias("member", "member");
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.property("member.id").as("usid"))
			.add(Projections.property("carNo").as("carNo"))
			.add(Projections.property("fstRgUsid").as("fstRgUsid"))
			.add(Projections.property("fstRgDt").as("fstRgDt"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("member.id", usid));
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		return detail;
	}
	
	
	
	@Override
	public Criteria getCriteria(Car entity) {
		return null;
	}
}
