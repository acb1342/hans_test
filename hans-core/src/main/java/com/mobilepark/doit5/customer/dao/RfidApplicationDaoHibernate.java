package com.mobilepark.doit5.customer.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.customer.model.RfidApplication;
import com.uangel.platform.dao.HibernateGenericDao;
import com.uangel.platform.log.TraceLog;

public class RfidApplicationDaoHibernate extends HibernateGenericDao<RfidApplication, Long> implements RfidApplicationDao {
	
	@Override
	public Criteria getCriteria(RfidApplication entity) {
		Criteria criteria = super.getCurrentSession().createCriteria(RfidApplication.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("cardCnt");
			
			criteria.add(example);
			
			// inner join
			Criteria joinCtrteria = criteria.createCriteria("member");
			// ᅟsearch
			if (entity.getMember() != null) {
                TraceLog.debug("member is not null. name is [%s]", entity.getMember().getName());
                joinCtrteria.add(Restrictions.like("name", entity.getMember().getName().trim(), MatchMode.ANYWHERE));
			}
		}
		return criteria;
	}

	@Override
	public RfidApplication insertCardReq(RfidApplication rfidApplication) {
		return create(rfidApplication);
	}
	

	@Override
	public Long searchCountByMemberName(String name) {
		
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT count(*) ");
		sqlBuf.append("FROM TB_CUST_RFID_APPLICATION A, TB_CUST_MEMBER B ");
		sqlBuf.append("WHERE A.USID = B.USID ");
		sqlBuf.append("AND B.NAME LIKE '%?%' ");
		String sql = sqlBuf.toString();
		
		Query query = this.getCurrentSession().createSQLQuery(sql).setString(0, name);
		return ((Number) query.uniqueResult()).longValue();
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<RfidApplication> searchByMemberName(String name, int page, int rowPerPage) {

		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("SELECT count(*) ");
		sqlBuf.append("FROM TB_CUST_RFID_APPLICATION A, TB_CUST_MEMBER B ");
		sqlBuf.append("WHERE A.USID = B.USID ");
		sqlBuf.append("AND B.NAME LIKE '%?%' ");
		String sql = sqlBuf.toString();
		
		Query query = this.getCurrentSession().createSQLQuery(sql).addEntity(RfidApplication.class).setString(0, name);
		if (page > 0) {
			int startRow = (page - 1) * rowPerPage;
			query.setFirstResult(startRow);
		}
		query.setMaxResults(rowPerPage);

		return query.list();
	}
}
