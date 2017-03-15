package com.mobilepark.doit5.elcg.service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.StationApplication;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.admin.dao.AdminDao;
import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.elcg.dao.BrokenReportDao;
import com.mobilepark.doit5.elcg.dao.ElcgDaoMybatis;
import com.mobilepark.doit5.elcg.dao.StationApplicationDao;
import com.mobilepark.doit5.elcg.model.BrokenReport;
import com.mobilepark.doit5.history.dao.PushMsgDaoMybatis;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ApplBrokServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 14.      최초 버전
 * =================================================================================*/

@Transactional
public class ApplBrokServiceImpl extends AbstractGenericService<StationApplication, Long> implements ApplBrokService {
	
	@Autowired
	private StationApplicationDao stationApplicationDao;
	
	@Autowired
	private BrokenReportDao brokenReportDao;
	
	@Autowired
	private AdminDao adminDao;
	
	@Autowired
	private PushMsgDaoMybatis pushMsgDaoMybatis;
	
	@Autowired
	private ElcgDaoMybatis elcgDaoMybatis;
	
	
	@Override
	protected GenericDao<StationApplication, Long> getGenericDao() {
		return stationApplicationDao;
	}
	
	@Override
	public Map<String, Object> getReqReportList(Integer page, Integer size, String type, String usid, Integer adminGroupId) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 충전기신청 리스트
		if("appl".equals(type)) {
			map = stationApplicationDao.selectReqReportList(page, size, type, usid, adminGroupId);
			
		// 고장신고 리스트
		} else if("brok".equals(type)) {
			map = brokenReportDao.selectReqReportList(page, size, type, usid, adminGroupId);
			
		// 충전기신청 리스트 + 고장신고 리스트
		} else {
			map = stationApplicationDao.selectReqReportAllList(page, size, usid, adminGroupId);
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> getReqReportDetail(Long snId, String type) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		// 충전기신청 상세
		if("appl".equals(type)) {
			map = stationApplicationDao.selectReqReportDetail(snId, type);
			
		// 고장신고 상세
		} else if("brok".equals(type)) {
			map = brokenReportDao.selectReqReportDetail(snId, type);
			
		}
		
		return map;
	}
	
	@Override
	public Map<String, Object> insertReqReport(Map<String, Object> map, String usid, String clientType) {
		String chan = "";
		
		if("ANDROID".equals(clientType)) {
			chan = "313104";
		} else if("IOS".equals(clientType)) {
			chan = "313105";
		} else {
			chan = "313101";
		}
		
		// 충전기신청 등록
		if("appl".equals(map.get("type"))) {
			StationApplication stationApplication = new StationApplication();
			stationApplication.setBody((String)map.get("body"));
			stationApplication.setStatus("407101");
			stationApplication.setRcUsid(usid);
			stationApplication.setRcDt(new Date()); 
			stationApplication.setFstRgUsid(usid);
			stationApplication.setFstRgDt(new Date());
			stationApplication.setLstChUsid(usid);
			stationApplication.setLstChDt(new Date());
			stationApplication.setChan(chan);
			
			stationApplication = stationApplicationDao.insertReqReport(stationApplication);
			
			
		// 고장신고 등록
		} else if("brok".equals(map.get("type"))) {
			BrokenReport brokenReport = new BrokenReport();
			brokenReport.setBody((String)map.get("body"));
			brokenReport.setStatus("409101");
			brokenReport.setRcUsid(usid); 
			brokenReport.setRcDt(new Date()); 
			brokenReport.setFstRgUsid(usid);
			brokenReport.setFstRgDt(new Date());
			brokenReport.setLstChUsid(usid);
			brokenReport.setLstChDt(new Date());
			brokenReport.setChan(chan);
			
			brokenReport = brokenReportDao.insertReqReport(brokenReport);
		}
		
		return map;
	}
	
	@Override
	public int updateReqReport(Map<String, Object> map, String usid) {
		
		Integer snId = (Integer) map.get("snId");
		int result = 0;
		
		// 충전기신청 수정
		if("appl".equals(map.get("type"))) {
			StationApplication stationApplication = new StationApplication();
			stationApplication.setSnId(snId.longValue());
			stationApplication.setBody((String)map.get("body"));
			stationApplication.setLstChUsid(usid);
			stationApplication.setLstChDt(new Date()); 
			
			result = stationApplicationDao.updateReqReport(stationApplication);
			

		// 고장신고 수정
		} else if("brok".equals(map.get("type"))) {
			BrokenReport brokenReport = new BrokenReport();
			brokenReport.setSnId(snId.longValue());
			brokenReport.setBody((String)map.get("body"));
			brokenReport.setLstChUsid(usid);
			brokenReport.setLstChDt(new Date());
			
			result = brokenReportDao.updateReqReport(brokenReport);
		}
		
		return result;
	}
	
	@Override
	public int updateReqReportComplete(Map<String, Object> map, String usid) {
		
		Integer snId = (Integer) map.get("snId");
		int result = 0;
		
		Calendar c = Calendar.getInstance();
		String hour = StringUtils.leftPad(String.valueOf(c.get(Calendar.HOUR_OF_DAY)), 2, "0");
		String minute = StringUtils.leftPad(String.valueOf(c.get(Calendar.MINUTE)), 2, "0");
		String second = StringUtils.leftPad(String.valueOf(c.get(Calendar.SECOND)), 2, "0");
		
		String adminId = null;
		String title = null;
		String msg = null;
		String category = null;
		
		// 충전기신청 완료
		if("appl".equals(map.get("type"))) {
			
			StationApplication stationApplication = new StationApplication();
			stationApplication.setSnId(snId.longValue());
			stationApplication.setStatus("407103"); // 완료
			stationApplication.setWkDt(new Date());
			stationApplication.setLstChUsid(usid);
			stationApplication.setLstChDt(new Date());
			
			result = stationApplicationDao.updateReqReportComplete(stationApplication);
			
			Map<String, Object> reqReport = stationApplicationDao.selectReqReportDetail(snId.longValue(), "");
			adminId = (String)reqReport.get("adminId");
			
			title = "충전기 설치 완료 안내";
			msg = hour + "시 " + minute + "분 " + second + "초에 신청하신 충전기 설치가 완료되었습니다.";
			category = "901132";
			
		// 고장신고 완료
		} else if("brok".equals(map.get("type"))) {
			BrokenReport brokenReport = new BrokenReport();
			brokenReport.setSnId(snId.longValue());
			brokenReport.setStatus("409103"); // 완료
			brokenReport.setWkDt(new Date());
			brokenReport.setLstChUsid(usid);
			brokenReport.setLstChDt(new Date());
			
			result = brokenReportDao.updateReqReportComplete(brokenReport);
			
			Map<String, Object> reqReport = brokenReportDao.selectReqReportDetail(snId.longValue(), "");
			adminId = (String)reqReport.get("adminId");
			
			title = "충전기 수리 완료 안내";
			msg = hour + "시 " + minute + "분 " + second + "초에 충전기 고장 수리가 완료되었습니다.";
			category = "901134";
			
			// 고장신고 내역 조회
			Map<String, Object> brokenDetail = (Map<String, Object>) elcgDaoMybatis.selectBrokenReportDetail(snId.longValue());
			Integer capacity = (Integer) brokenDetail.get("capacity");
			Long bdId = (Long) brokenDetail.get("bdId");
			Long chargerGroupId = (Long) brokenDetail.get("chargerGroupId");
			String chargerId = (String) brokenDetail.get("chargerId");
			
			Map<String, Object> paramMap = new HashMap<String, Object>();
			paramMap.put("chargerId", chargerId);
			paramMap.put("detailStatus", "406201");
			
			// 충전기 상태 수정
			if (elcgDaoMybatis.isChargerChargeAvailable(chargerGroupId, capacity)) {
				paramMap.put("status", "406101");
		      } else {
		    	  paramMap.put("status", "406102");
		      }
			
			elcgDaoMybatis.updateChargeStatus(paramMap);
			
			
			// 빌딩 상태 수정
			elcgDaoMybatis.updateBdChargeAvailable(bdId, String.valueOf(usid));
		}
		
		Admin admin = adminDao.get(adminId);
		
		Map<String, Object> pushMap = new HashMap<>();
		pushMap.put("custType", "101203");
		pushMap.put("usid", admin.getId());
		pushMap.put("custName", admin.getName());
		pushMap.put("os", "301401");
		pushMap.put("mobile", admin.getMobile());
		pushMap.put("title", title);
		pushMap.put("msg", msg);
		pushMap.put("category", category);
		pushMap.put("pushToken", admin.getPushToken());
		
		pushMsgDaoMybatis.insertPushQueue(pushMap);
		
		return result;
	}

}
