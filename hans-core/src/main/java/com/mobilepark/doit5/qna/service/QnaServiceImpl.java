package com.mobilepark.doit5.qna.service;

import java.util.Map;

import com.mobilepark.doit5.qna.dao.QnaDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.qna.model.QnaCust;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

@Transactional
public class QnaServiceImpl extends AbstractGenericService<QnaCust, Long> implements QnaService {
	
	@Autowired
	private QnaDao qnaDao;

	@Override
	protected GenericDao<QnaCust, Long> getGenericDao() {
		return qnaDao;
	}
	
	@Override
	public Map<String, Object> getQnaList(Integer page, Integer size, String openYn, String searchField, String searchKeyword, boolean isUser, boolean isOd, boolean isWk) {
		return qnaDao.selectQnaList(page, size, openYn, searchField, searchKeyword, isUser, isOd, isWk);
	}

	@Override
	public Map<String, Object> getQnaDetail(Long snId, String usid) {
		return qnaDao.selectQnaDetail(snId, usid);
	}
	
	@Override
	public QnaCust insertQna(QnaCust qnaCust) {
		return qnaDao.insertQna(qnaCust);
	}
	
	@Override
	public int updateQna(QnaCust qnaCust) {
		return qnaDao.updateQna(qnaCust);
	}
	
	@Override
	public void deleteQna(Long snId) {
		qnaDao.deleteQna(snId);
	}
}
