package com.hans.sses.notice.service;

import java.util.HashMap;
import java.util.Map;

import com.hans.sses.notice.dao.NoticeAdminDao;
import com.hans.sses.notice.model.NoticeAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class NoticeAdminServiceImpl extends AbstractGenericService<NoticeAdmin, Long> implements NoticeAdminService {
	
	@Autowired
	private NoticeAdminDao noticeAdminDao;

	@Override
	protected GenericDao<NoticeAdmin, Long> getGenericDao() {
		return noticeAdminDao;
	}
	
	@Override
	public Map<String, Object> getNoticeList(Integer page, Integer size) {
		return noticeAdminDao.selectNoticeList(page, size);
	}

	@Override
	public Map<String, Object> getNoticeDetail(Long snId) {
		Map<String, Object> resultMap = noticeAdminDao.selectNoticeDetail(snId);
		
		if(resultMap == null) {
			resultMap = new HashMap<String, Object>();
		}
		return resultMap;
	}
}
