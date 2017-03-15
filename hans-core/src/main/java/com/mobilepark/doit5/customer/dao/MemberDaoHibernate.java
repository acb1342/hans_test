package com.mobilepark.doit5.customer.dao;

import java.util.Date;
import java.util.Map;

import com.mobilepark.doit5.customer.model.Member;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

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
public class MemberDaoHibernate extends HibernateGenericDao<Member, Long> implements MemberDao {

	@Override
	protected Criteria getCriteria(Member entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(Member.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("status")
					.excludeProperty("nameVrfd").excludeProperty("rcptAgr");
			criteria.add(example);
			
			if (StringUtils.isNotEmpty(entity.getStatus())) {
				String[] values = entity.getStatus().split(",");
				criteria.add(Restrictions.in("status", values));
			}
		}
		return criteria;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectUserDetail(Long usid) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(Member.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("usid"))
			.add(Projections.property("subsId").as("subsId"))
			.add(Projections.property("name").as("name"))
			.add(Projections.property("status").as("status"))
			.add(Projections.property("paymentPlan").as("paymentPlan"))
			.add(Projections.property("paymentMethod").as("paymentMethod"))
			.add(Projections.property("os").as("os"))
			.add(Projections.property("mdn").as("mdn"))
			.add(Projections.property("tel").as("tel"))
			.add(Projections.property("emailAddr").as("emailAddr"))
			.add(Projections.property("certToken").as("certToken"))
			.add(Projections.property("pushToken").as("pushToken"))
			.add(Projections.property("fstRgUsid").as("fstRgUsid"))
			.add(Projections.property("fstRgDt").as("fstRgDt"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(usid));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		return detail;
	}
	
	
	@Override
	public int updatePayment(Member member) {
		
		Session session = this.getCurrentSession();
	      
		Member target = (Member)session.get(Member.class, member.getId());
		
		
		target.setPaymentMethod(member.getPaymentMethod());
		target.setPaymentPlan(member.getPaymentPlan());
		target.setLstChUsid(member.getLstChUsid());
		target.setLstChDt(new Date());
		
		return update(target);
	}
	
	@Override
	public int updateUserStatus(Member member) {
		
		Session session = this.getCurrentSession();
		
		Member target = (Member)session.get(Member.class, member.getId());
		
		target.setStatus(member.getStatus());
		
		return update(target);
	}

	@Override
	public Member selectUserBySktId(String sktId) {
		Criteria criteria = this.getCurrentSession().createCriteria(Member.class);
		
		criteria.add(Restrictions.eq("subsId", sktId))
			.add(Restrictions.ne("status", "301103"));
		
		return (Member) criteria.uniqueResult();
	}

	@Override
	public Member insertUser(Member member) {
		return create(member);
	}
	
	@Override
	public int updateUser(Member member) {
		Session session = this.getCurrentSession();
	      
		Member target = (Member)session.get(Member.class, member.getId());
		
		/*target.setTitle(qna.getTitle());
		target.setBody(qna.getBody());
		target.setOpenYn(qna.getOpenYn());*/
		
		return update(target);
	}
	
	
}
