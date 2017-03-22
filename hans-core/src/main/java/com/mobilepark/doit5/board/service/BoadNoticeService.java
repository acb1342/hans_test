package com.mobilepark.doit5.board.service;

import java.util.List;
import java.util.Map;

public interface BoadNoticeService {
	int count(Map<String, Object> param);
	
	List<Map<String, Object>> search(Map<String, Object> param);
	
	Map<String, Object> get(Long id);
	
	void create(Map<String, Object> param);
	
	void update(Map<String, Object> param);
	
	int delete(Long id);
}
