package com.mobilepark.doit5.board.service;


import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.board.dao.BoadNoticeDaoMybatis;


@Transactional
public class BoadNoticeServiceImpl implements BoadNoticeService {
	
	@Autowired
	private BoadNoticeDaoMybatis boadNoticeDaoMybatis;

	@Autowired
	public int count(Map<String, Object> param) {
		return this.boadNoticeDaoMybatis.count(param);
	}
	
	@Override
	public List<Map<String, Object>> search(Map<String, Object> param) {
		return this.boadNoticeDaoMybatis.search(param);
	}

	@Override
	public Map<String, Object> get(Long id) {
		return this.boadNoticeDaoMybatis.get(id);
	}

	@Override
	public void create(Map<String, Object> param) {
		param.put("regDate", new Date());
		this.boadNoticeDaoMybatis.create(param);
	}
	
	@Override
	public void update(Map<String, Object> param) {
		param.put("modDate", new Date());
		this.boadNoticeDaoMybatis.update(param);
	}

	@Override
	public int delete(Long id) {
		return this.boadNoticeDaoMybatis.delete(id);
	}

}
