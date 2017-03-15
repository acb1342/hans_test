package com.mobilepark.doit5.customer.dao;

import com.mobilepark.doit5.customer.model.Close;
import org.hibernate.Criteria;

import com.uangel.platform.dao.HibernateGenericDao;

public class CustCloseDaoHibernate extends HibernateGenericDao<Close, Long> implements CustCloseDao {
	
	@Override
	public Close insertCustClose(Close custClose) {
		return create(custClose);
	}
	
	
	@Override
	public Criteria getCriteria(Close entity) {
		return null;
	}
}
