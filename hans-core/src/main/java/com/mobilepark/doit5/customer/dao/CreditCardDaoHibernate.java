package com.mobilepark.doit5.customer.dao;

import java.util.Date;
import java.util.Map;

import com.mobilepark.doit5.customer.model.CreditCard;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.uangel.platform.dao.HibernateGenericDao;

public class CreditCardDaoHibernate extends HibernateGenericDao<CreditCard, Long> implements CreditCardDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectPostPayDetail(Long usid) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(CreditCard.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("usid"))
			//.add(Projections.property("creditCardNo").as("creditCardNo"))
			//.add(Projections.sqlProjection("RIGHT(CREDIT_CARD_NO, 4) AS creditCardNo", new String[]{"creditCardNo"}, new Type[]{Hibernate.STRING}))
			//.add(Projections.property("validity").as("validity"))
			//.add(Projections.property("cvc").as("cvc"))
			//.add(Projections.property("passwd").as("passwd"))
			.add(Projections.property("companyNm").as("company"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(usid));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
			
		return detail;
	}
	
	
	@Override
	public CreditCard insertPostPay(CreditCard creditCard) {
		return create(creditCard);
	}
	
	
	@Override
	public int updatePostPay(CreditCard creditCard) {
		
		Session session = this.getCurrentSession();
	      
		CreditCard target = (CreditCard)session.get(CreditCard.class, creditCard.getId());
		
		target.setCreditCardNo(creditCard.getCreditCardNo());
		//target.setValidity(creditCard.getValidity());
		//target.setCvc(creditCard.getCvc());
		//target.setPasswd(creditCard.getPasswd());
		target.setLstChUsid(creditCard.getLstChUsid());
		target.setLstChDt(new Date());
		
		return update(target);
	}
	
	
	@Override
	public Criteria getCriteria(CreditCard entity) {
		return null;
	}
}
