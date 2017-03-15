package com.mobilepark.doit5.faq.service;

import java.util.Map;

import com.mobilepark.doit5.faq.dao.FaqAdminDao;
import com.mobilepark.doit5.faq.model.FaqAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class FaqAdminServiceImpl extends AbstractGenericService<FaqAdmin, Long> implements FaqAdminService {
	
	@Autowired
	private FaqAdminDao faqAdminDao;
	
	public Map<String, Object> getFaqList(Integer page, Integer size, String category) {
		return faqAdminDao.selectFaqList(page, size, category);
	}

	@Override
	protected GenericDao<FaqAdmin, Long> getGenericDao() {
		return this.faqAdminDao;
	}
}
