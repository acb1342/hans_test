package com.hans.sses.attendance.service;

import java.util.List;
import java.util.Map;

public interface AttendanceService {

	int count(Map<String, Object> param);
	
	List<Map<String, Object>> search(Map<String, Object> param);
	
	Map<String, String> create(Map<String, Object> param);

}
