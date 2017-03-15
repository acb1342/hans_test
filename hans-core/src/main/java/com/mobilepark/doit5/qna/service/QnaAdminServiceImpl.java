package com.mobilepark.doit5.qna.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.qna.dao.QnaAdminDao;
import com.mobilepark.doit5.qna.model.QnaAdmin;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class QnaAdminServiceImpl extends AbstractGenericService<QnaAdmin, Long> implements QnaAdminService {
	
	@Autowired
	private QnaAdminDao qnaAdminDao;

	@Override
	protected GenericDao<QnaAdmin, Long> getGenericDao() {
		return qnaAdminDao;
	}
	
	@Override
	public Map<String, Object> getQnaList(Integer page, Integer size, String openYn, String searchField, String searchKeyword) {
		return qnaAdminDao.selectQnaList(page, size, openYn, searchField, searchKeyword);
	}

	@Override
	public Map<String, Object> getQnaDetail(Long snId, String usid) {
		return qnaAdminDao.selectQnaDetail(snId, usid);
	}
	
	@Override
	public QnaAdmin insertQna(QnaAdmin qnaAdmin) {
		return qnaAdminDao.insertQna(qnaAdmin);
	}
	
	@Override
	public int updateQna(QnaAdmin qnaAdmin) {
		return qnaAdminDao.updateQna(qnaAdmin);
	}
	
	@Override
	public void deleteQna(Long snId) {
		qnaAdminDao.deleteQna(snId);
	}
}
