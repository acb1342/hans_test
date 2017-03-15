package com.mobilepark.doit5.notice.service;

import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.notice.dao.NoticeDao;
import com.mobilepark.doit5.notice.model.NoticeCust;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class NoticeServiceImpl extends AbstractGenericService<NoticeCust, Long> implements NoticeService {
	
	@Autowired
	private NoticeDao noticeDao;

	@Override
	protected GenericDao<NoticeCust, Long> getGenericDao() {
		return noticeDao;
	}
	
	@Override
	public Map<String, Object> getNoticeList(Integer page, Integer size) {
		return noticeDao.selectNoticeList(page, size);
	}

	@Override
	public Map<String, Object> getNoticeDetail(Long snId) {
		Map<String, Object> resultMap = noticeDao.selectNoticeDetail(snId);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
		}
		return resultMap;
	}
}
