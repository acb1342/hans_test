package com.mobilepark.doit5.notice.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;

import com.mobilepark.doit5.notice.model.NoticeCust;
import com.uangel.platform.dao.HibernateGenericDao;

@SuppressWarnings("unchecked")
public class NoticeDaoHibernate extends HibernateGenericDao<NoticeCust, Long> implements NoticeDao {
	
	@Override
	public Map<String, Object> selectNoticeList(Integer page, Integer size) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(NoticeCust.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.property("title").as("title"))
			.add(Projections.property("body").as("body"))
			.add(Projections.property("fstRgDt").as("fstRgDt"));
			// .add(Projections.sqlGroupProjection("DATE_FORMAT(create_time, '%Y.%m.%d') as create_time", "create_time", new String[] { "create_time" }, new Type[] { Hibernate.STRING }));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("displayYn", "Y"))
			.setFirstResult(size * (page - 1))
			.setMaxResults(size)
			.addOrder(Order.desc("fstRgDt"));
		
		
		List<Map<String, Object>> list = criteria.list();
		
		criteria = this.getCurrentSession().createCriteria(NoticeCust.class);
		criteria.add(Restrictions.eq("displayYn", "Y"))
			.setProjection(Projections.rowCount());
		
		
		Long totalCnt = (Long)criteria.uniqueResult();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", totalCnt);
		resultMap.put("noticeList", list);
		
		return resultMap;
	}
	
	@Override
	public Map<String, Object> selectNoticeDetail(Long snId) {
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.property("title").as("title"))
			.add(Projections.property("body").as("body"))
			.add(Projections.property("fstRgDt").as("fstRgDt"));
		
		Criteria criteria = this.getCurrentSession().createCriteria(NoticeCust.class);
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.idEq(snId));
		
		return (Map<String, Object>)criteria.uniqueResult();
	}
	
	@Override
	public Criteria getCriteria(NoticeCust entity) {
		return null;
	}
}
