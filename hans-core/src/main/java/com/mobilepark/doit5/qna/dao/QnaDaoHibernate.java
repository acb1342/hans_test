package com.mobilepark.doit5.qna.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.Transformers;
import org.hibernate.type.Type;

import com.mobilepark.doit5.qna.model.QnaCust;
import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.util.EtcUtil;

public class QnaDaoHibernate extends HibernateGenericDao<QnaCust, Long> implements QnaDao {
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectQnaList(Integer page, Integer size, String openYn, String searchField, String searchKeyword, boolean isUser, boolean isOd, boolean isWk) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(QnaCust.class);
		
		// 리스트 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.property("openYn").as("openYn"))
			.add(Projections.property("title").as("title"))
			.add(Projections.property("answerYn").as("answerYn"))
			.add(Projections.property("fstRgUsid").as("fstRgUsid"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("penName").as("lstChUsNm"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("type", "602101"))
			.setFirstResult(size * (page - 1))
			.setMaxResults(size)
			.addOrder(Order.desc("fstRgDt"));
		
		
		if(!EtcUtil.isBlank(searchField)) {
			criteria.add(Restrictions.like(searchField, !EtcUtil.isBlank(searchKeyword)? searchKeyword : "", MatchMode.ANYWHERE));
		}
		
		if(EtcUtil.isBlank(searchField) && !EtcUtil.isBlank(searchKeyword)) {
			criteria.add(Restrictions.disjunction()
	            .add(Restrictions.like("title", searchKeyword, MatchMode.ANYWHERE))
	            .add(Restrictions.like("body", searchKeyword, MatchMode.ANYWHERE))
	            .add(Restrictions.like("penName", searchKeyword, MatchMode.ANYWHERE)));
		}
		
		List<Map<String, Object>> list = criteria.list();
		
		
		// 카운트 조회
		criteria = this.getCurrentSession().createCriteria(QnaCust.class);
		criteria.setProjection(Projections.rowCount())
			.add(Restrictions.eq("type", "602101"));
		
		
		if(!EtcUtil.isBlank(searchField)) {
			criteria.add(Restrictions.like(searchField, !EtcUtil.isBlank(searchKeyword)? searchKeyword : "", MatchMode.ANYWHERE));
		}
		
		if(EtcUtil.isBlank(searchField) && !EtcUtil.isBlank(searchKeyword)) {
			criteria.add(Restrictions.disjunction()
	            .add(Restrictions.like("title", searchKeyword, MatchMode.ANYWHERE))
	            .add(Restrictions.like("body", searchKeyword, MatchMode.ANYWHERE))
	            .add(Restrictions.like("penName", searchKeyword, MatchMode.ANYWHERE)));
		}
		
		Long totalCnt = (Long)criteria.uniqueResult();
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", totalCnt);
		resultMap.put("qnaList", list);
		
		return resultMap;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectQnaDetail(Long snId, String usid) {
		
		Criteria criteria = this.getCurrentSession().createCriteria(QnaCust.class);
		
		// 상세 조회
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
			.add(Projections.property("title").as("title"))
			.add(Projections.property("body").as("body"))
			.add(Projections.property("openYn").as("openYn"))
			.add(Projections.property("fstRgDt").as("fstRgDt"))
			.add(Projections.property("penName").as("lstChUsNm"))
			.add(Projections.property("fstRgUsid").as("fstRgUsid"))
			.add(Projections.property("answerYn").as("answerYn"))
			.add(Projections.sqlProjection("CASE WHEN FST_RG_USID = '" + usid + "' THEN 'Y' ELSE 'N' END AS writerYn", new String[]{"writerYn"}, new Type[]{Hibernate.STRING}));
		
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
			.add(Restrictions.eq("type", "602101"))
			.add(Restrictions.idEq(snId));
		
		
		Map<String, Object> detail = (Map<String, Object>)criteria.uniqueResult();
		
		
		// 답변 리스트 조회
		if(detail != null && "Y".equals(detail.get("answerYn"))) {
			criteria = this.getCurrentSession().createCriteria(QnaCust.class);
			
			pl = Projections.projectionList();
			pl.add(Projections.id().as("snId"))
				.add(Projections.property("questionId").as("questionId"))
				.add(Projections.property("lstChDt").as("answerLstChDt"))
				.add(Projections.property("penName").as("answerLstChUsNm"))
				.add(Projections.property("body").as("answerBody"));
			
			criteria.setProjection(pl)
				.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP)
				.add(Restrictions.eq("type", "602102"))
				.add(Restrictions.eq("questionId", snId));
			
			List<Map<String, Object>> answerList = (List<Map<String, Object>>)criteria.list();
			
			detail.put("answerList", answerList);
		} 

		if(detail == null) {
			detail = new HashMap<String, Object>();
		}
		
		return detail;
	}
	
	
	@Override
	public QnaCust insertQna(QnaCust qnaCust) {
		return create(qnaCust);
	}
	
	@Override
	public int updateQna(QnaCust qnaCust) {
		
		Session session = this.getCurrentSession();
	      
		QnaCust target = (QnaCust)session.get(QnaCust.class, qnaCust.getSnId());
		
		int result = 0;
		
		if(target != null) {
			target.setTitle(qnaCust.getTitle());
			target.setBody(qnaCust.getBody());
			target.setOpenYn(qnaCust.getOpenYn());
			
			result = update(target);
		}
		
		return result; 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void deleteQna(Long snId) {
		Criteria criteria = this.getCurrentSession().createCriteria(QnaCust.class);
		
		ProjectionList pl = Projections.projectionList();
		pl.add(Projections.id().as("snId"))
        	.add(Projections.property("questionId").as("questionId"));
		
		criteria.setProjection(pl)
			.setResultTransformer(Transformers.aliasToBean(QnaCust.class))
			.add(Restrictions.eq("questionId", snId));
		
		// 질문 삭제
		delete(snId);
				
		// 답변 삭제
		List<QnaCust> answerList = (List<QnaCust>)criteria.list();
		
		for(QnaCust qnaCust : answerList) {
			delete(qnaCust);
		}
		
		
	}
	
	@Override
	public Criteria getCriteria(QnaCust entity) {
		return null;
	}
}
