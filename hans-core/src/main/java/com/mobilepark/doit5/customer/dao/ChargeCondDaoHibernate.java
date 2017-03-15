package com.mobilepark.doit5.customer.dao;

import java.util.Date;
import java.util.Map;

import com.mobilepark.doit5.customer.model.ChargeCond;
import org.hibernate.Criteria;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.uangel.platform.dao.HibernateGenericDao;

public class ChargeCondDaoHibernate extends HibernateGenericDao<ChargeCond, Long> implements ChargeCondDao {
	
	@Override
	public ChargeCond insertChargeCond(ChargeCond chargeCond) {
		return create(chargeCond);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectChargeCondDetail(Long usid) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(ChargeCond.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("usid"))
			.add(Projections.property("chargeCond").as("chargeCond"))
			.add(Projections.property("watt").as("watt"))
			.add(Projections.property("fstRgUsid").as("fstRgUsid"))
			.add(Projections.property("fstRgDt").as("fstRgDt"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(usid));
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		return detail;
	}
	
	@Override
	public void updateChargeCond(ChargeCond chargeCond) {
		Criteria criteria = this.getCurrentSession().createCriteria(ChargeCond.class);
		
		criteria.add(Restrictions.eq("id", chargeCond.getId()));
		
		ChargeCond target = (ChargeCond)criteria.uniqueResult();
		
		target.setChargeCond(chargeCond.getChargeCond());
		target.setWatt(chargeCond.getWatt());
		target.setLstChUsid(chargeCond.getLstChUsid());
		target.setLstChDt(new Date());
		
		update(target);
	}
	
	@Override
	public Criteria getCriteria(ChargeCond entity) {
		return null;
	}
}
