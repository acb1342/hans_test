package com.mobilepark.doit5.faq.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.faq.dao.FaqDao;
import com.mobilepark.doit5.faq.model.FaqCust;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class FaqServiceImpl extends AbstractGenericService<FaqCust, Long> implements FaqService {
	
	@Autowired
	private FaqDao faqDao;
	
	public Map<String, Object> getFaqList(Integer page, Integer size, String category) {
		return faqDao.selectFaqList(page, size, category);
	}

	@Override
	protected GenericDao<FaqCust, Long> getGenericDao() {
		return this.faqDao;
	}
}
