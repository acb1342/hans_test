package com.hans.sses.attendance.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;

import com.hans.sses.attendance.dao.AttendanceDaoMybatis;


@Transactional
public class AttendanceServiceImpl implements AttendanceService {
	
	@Autowired
	private AttendanceDaoMybatis attendanceDaoMybatis;
	
	@Override
	public int count(Map<String, Object> param) {
		return this.attendanceDaoMybatis.count(param);
	}
	
	@Override
	public List<Map<String, Object>> search(Map<String, Object> param) {
		return this.attendanceDaoMybatis.search(param);
	}
	
	@Override
	public Map<String, String> create(Map<String, Object> param) {
		int createCount = this.attendanceDaoMybatis.create(param);
		Map<String, String> resMap = new HashMap<String, String>();
		
		if (createCount > 0) resMap.put("errorMsg", "INSERT SUCC");
		else resMap.put("errorMsg", "INSERT FAIL");
		
		return resMap;
	}

	@Override
	public List<Map<String, String>> search_monthly() {
		return this.attendanceDaoMybatis.search_monthly();
	}
	
}
