package com.mobilepark.doit5.board.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.board.dao.AppVerDaoMybatis;
import com.uangel.platform.log.TraceLog;

@Transactional
public class AppVerServiceImpl implements AppVerService {
	@Autowired
	private AppVerDaoMybatis appVerDaoMybatis;
	
	public int count(Map<String, Object> param) {
		return this.appVerDaoMybatis.count(param);
	}
	
	public List<Map<String, Object>> search(Map<String, Object> param) {
		return this.appVerDaoMybatis.search(param);
	}
	
	public Map<String, Object> get(Long id) {
		return this.appVerDaoMybatis.get(id);
	}
	
	public void create(Map<String, Object> param) {
		this.appVerDaoMybatis.create(param);
	}
	
	public void update(Map<String, Object> param) {
		this.appVerDaoMybatis.update(param);
	}
	
	public int delete(Long id) {
		return this.appVerDaoMybatis.delete(id);
	}

	public Map<String, Object> getAppVer_api(String ver, String clientType, String targetType) {
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (StringUtils.isEmpty(ver)) {
			resultMap.put("errorMsg", "필수 파라미터가 존재하지 않습니다.");
			printMap(resultMap);
			return resultMap;
		}
		
		try {
			if (StringUtils.isNotEmpty(ver) && ver.length() == 1 && Integer.parseInt(ver) >= 0) ver += ".0";
			if (ver.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣a-z]+.*")) throw new Exception();
			if (ver.startsWith(".") || ver.endsWith(".")) throw new Exception();
		} catch(Exception e) {
			resultMap.put("errorMsg", "파라미터 형식이 잘못되었습니다.");
			printMap(resultMap);
			return resultMap;
		}
		
		clientType = "ANDROID".equals(clientType)? "301401" : ("IOS".equals(clientType)? "301402" : "301403");
		param.put("ver", ver);
		param.put("os", clientType);
		param.put("targetType", targetType);
		
		resultMap = appVerDaoMybatis.getAppVer_api(param);
		
		if (resultMap == null) {
			Map<String, Object> errMap = new HashMap<String, Object>();
			errMap.put("errorMsg", "상위 업데이트 버전이 존재하지 않습니다.");
			printMap(errMap);
			return errMap;
		}
		
		if (resultMap != null && resultMap.get("ver").toString().equals(ver)) {
			resultMap.replace("verYn", "N");
			resultMap.replace("updateUrl", "정보 없음");
		}
		
		printMap(resultMap);
		return resultMap;
	}
	
	void printMap(Map<String, Object> map) {
		TraceLog.info("===== resultMap =====");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			TraceLog.debug("[%s] : [%s]", key, map.get(key));
		}
		TraceLog.info("=====================");
	}
}
