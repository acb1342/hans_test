package com.mobilepark.doit5.customer.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.customer.model.RfidCard;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.customer.model.RfidApplication;
import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.log.TraceLog;

public class RfidCardDaoHibernate extends HibernateGenericDao<RfidCard, Long> implements RfidCardDao {
	
	/*@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> selectRfidCardList(Long usid, Integer page, Integer size) {
		
		
		 * TB_CUST_MEMBER 테이블과 TB_CUST_RFID_APPLICATION 테이블 join 하여
		 * TB_CUST_RFID_APPLICATION 테이블의 로그인한 회원의 회원카드신청 list 를 조회 (발급요청, 발급취소, 배송요청 상태 조회)
		 
		String sql = "SELECT "
					   + "  tca.id as snId "
					   + ", '' as cardNo "
					   + ", tca.rcDt as rcDt "
					   + ", '' as rgDt "
					   + ", tca.status as status "
					   + ", '' as stDt "
					   + ", CASE tca.status WHEN 308102 THEN '발급요청' WHEN 308103 THEN '발급취소' WHEN 308104 THEN '배송요청' ELSE '미사용' END as statusNm "
					   + "FROM Member tcm "
					   + "LEFT JOIN tcm.rfidApplication tca "
					   + "WHERE tcm.id = :usid " 
					   + "AND tca.status IN('308102', '308103', '308104') ";
		
		
		Query query = getCurrentSession().createQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setParameter("usid", usid);
		
		List<Map<String, Object>> list = query.list();
		
		
		 * TB_CUST_MEMBER 테이블과 TB_CUST_RFID_CARD 테이블 join 하여
		 * TB_CUST_RFID_CARD 테이블의 로그인한 회원의 카드 list 를 조회 (배송중, 배송완료, 사용중, 사용중지 상태 조회)
		 
		sql = "SELECT "
			   + "  tcc.id as snId "
			   + ", CONCAT('************', SUBSTR(tcc.cardNo, 13, 16)) as cardNo "
			   + ", ( SELECT tca.rcDt as rcDt FROM tcm.rfidApplication tca WHERE tcm.id = :usid AND tca.status IN('308102', '308103', '308104') ) as rcDt "
			   + ", tcc.rgDt as rgDt "
			   + ", tcc.status as status "
			   + ", tcc.stDt as stDt "
			   + ", CASE tcc.status WHEN 308105 THEN '배송중' WHEN 308106 THEN '배송완료' WHEN 308107 THEN '사용중' WHEN 308108 THEN '사용중지' ELSE '미사용' END as statusNm "
			   + "FROM Member tcm "
			   + "LEFT JOIN tcm.rfidCard tcc "
			   + "WHERE tcm.id = :usid "
			   + "AND tcc.status IN('308105', '308106', '308107', '308108') ";
		
		query = getCurrentSession().createQuery(sql);
		query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).setParameter("usid", usid);
		
		list.addAll(query.list());
		
		
		list = list.stream()
				  .skip((page - 1) * size)
				  .limit(size)
				  .collect(Collectors.toCollection(ArrayList::new));
		
		String[] userCardStatus = {"308102", "308103", "308104"};
		
		// 회원카드신청 카운트 조회
		Criteria criteria = this.getCurrentSession().createCriteria(RfidApplication.class);
		criteria.setProjection(Projections.rowCount())
			.add(Restrictions.eq("member.id", usid))
			.add(Restrictions.in("status", userCardStatus));
		
		Long totalCnt = (Long)criteria.uniqueResult();
		
		
		String[] cardStatus = {"308105", "308106", "308107", "308108"};
		
		// 카드 카운트 조회
		criteria = this.getCurrentSession().createCriteria(RfidCard.class);
		criteria.setProjection(Projections.rowCount())
			.add(Restrictions.eq("member.id", usid))
			.add(Restrictions.in("status",cardStatus));
		
		totalCnt += (Long)criteria.uniqueResult();
		
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("page", page);
		resultMap.put("size", size);
		resultMap.put("resultCnt", list.size());
		resultMap.put("totalCnt", totalCnt);
		
		
		resultMap.put("rfidCardList", list);
		
		return resultMap;
	}*/
	
	
	@Override
	public int updateLoseDt(Long snId) {
		
		Session session = this.getCurrentSession();
		
		RfidCard target = (RfidCard)session.get(RfidCard.class, snId);
		
		target.setStatus("308108");
		target.setStDt(new Date());
		
		return update(target);
	}
	
	
	@Override
	public Long isCardNo(RfidCard rfidCard) {
		
		// 유효한 카드번호 인지 체크
		Query query = this.getCurrentSession().createQuery("SELECT COUNT(*) FROM RfidCard rc WHERE rc.cardNo = :cardNo");
		query.setParameter("cardNo", rfidCard.getCardNo());
		
		Long snId = (Long) query.uniqueResult();
		
		return snId;
	}
	
	
	@Override
	public Long isOverCardNo(RfidCard rfidCard) {
		
		// 이미 사용중인 카드번호 인지 체크
		Query query = this.getCurrentSession().createQuery("SELECT COUNT(*) FROM RfidCard rc WHERE rc.cardNo = :cardNo AND  rc.status != '308101'");
		query.setParameter("cardNo", rfidCard.getCardNo());
		
		Long snId = (Long) query.uniqueResult();
		
		return snId;
	}
	
	
	@Override
	public Map<String, Object> isValidCardNo(RfidCard rfidCard) {
		Map<String, Object> valid = new HashMap<String, Object>();
		
		// 본인이 발급받은 카드번호 인지 체크
		Query query = this.getCurrentSession().createQuery("SELECT rc.id FROM RfidCard rc WHERE rc.member.id = :usid AND rc.cardNo = :cardNo");
		query.setParameter("usid", rfidCard.getMember().getId());
		query.setParameter("cardNo", rfidCard.getCardNo());
		
		
		Long snId = (Long) query.uniqueResult();
		
		
		if(snId == null) {
			valid.put("errorCd", "428003");
			valid.put("msg", "고객님께 발급된 카드가 아닙니다. 번호를 확인하시고 정확하게 다시 입력해 주세요.");
			
			
		// 카드 등록 (입력한 카드번호가 맞으면 상태값 사용중으로 변경)
		} else {
			Session session = this.getCurrentSession();
		      
			RfidCard target = (RfidCard)session.get(RfidCard.class, snId);
			
			target.setRgDt(new Date());
			target.setStatus("308107");
			target.setLstChUsid(rfidCard.getLstChUsid());
			target.setLstChDt(new Date());
			
			update(target);
			
			valid.put("validYn", "Y");
		}
		
		return valid;
	}
	
	
	@Override
	public Criteria getCriteria(RfidCard entity) {
		Criteria criteria = super.getCurrentSession().createCriteria(RfidApplication.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE);
			
			criteria.add(example);
		}
		return criteria;
	}
}
