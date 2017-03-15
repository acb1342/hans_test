package com.mobilepark.doit5.faq.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.faq.model.FaqAdmin;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.util.EtcUtil;

public class FaqAdminDaoHibernate extends HibernateGenericDao<FaqAdmin, Long> implements FaqAdminDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectFaqList(Integer page, Integer size, String category) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(FaqAdmin.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
		   .add(Projections.property("category").as("category"))
		   .add(Projections.sqlProjection("CASE category WHEN 606102 THEN '건물/그룹' WHEN 606103 THEN '충전기정보' WHEN 606104 THEN '설치신청' WHEN 606105 THEN '고장신고' ELSE '기타' END as categoryNm", new String[]{"categoryNm"}, new Type[]{Hibernate.STRING}))
		   .add(Projections.property("question").as("question"))
		   .add(Projections.property("answer").as("answer"));

		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("displayYn", "Y"))
			.setFirstResult(size * (page - 1))
			.setMaxResults(size)
			.addOrder(Order.desc("lstChDt"));
		
		
		if(!EtcUtil.isBlank(category)) {
			criteria.add(Restrictions.eq("category", category));
		}

		List<Map<String, Object>> list = criteria.list();
		
		criteria = this.getCurrentSession().createCriteria(FaqAdmin.class);
		criteria.add(Restrictions.eq("displayYn", "Y"))
			.setProjection(Projections.rowCount());
		
		
		if(!EtcUtil.isBlank(category)) {
			criteria.add(Restrictions.eq("category", category));
		}

		Long totalCnt = (Long)criteria.uniqueResult();

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", totalCnt);
		resultMap.put("faqList", list);

		return resultMap;
	}

	@Override
	public Criteria getCriteria(FaqAdmin entity) {
		return null;
	}
}
