package com.mobilepark.doit5.elcg.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.dao.ChargerDao;
import com.mobilepark.doit5.elcg.dao.ChargerListDao;
import com.mobilepark.doit5.elcg.dao.ElcgDaoMybatis;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.ChargerList;
import com.mobilepark.doit5.statistics.dao.StatAdjustDaoMybatis;
import com.mobilepark.doit5.statistics.model.Mgc;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.mobilepark.doit5.statistics.model.MgmtCmdType;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.model.Pageable;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerServiceImpl.java
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
public class ChargerServiceImpl extends AbstractGenericService<Charger, String> implements ChargerService {
	
	@Autowired
	private ChargerDao chargerDao;
	
	@Autowired
	private ChargerListDao chargerListDao;
	
	@Autowired
	private StatAdjustDaoMybatis statAdjustDaoMybatis;
	
	@Autowired
	private ElcgDaoMybatis elcgDaoMybatis;

	@Autowired
	private JAXBMarshallerService jaxbMarshallerService;
	@Autowired
	private HttpClientService httpClientService;

	@Override
	protected GenericDao<Charger, String> getGenericDao() {
		return this.chargerDao;
	}

	@Override
	public Map<String, Object> getChargerList(Integer adminGroupId, String adminId) {
		return chargerDao.selectChargerList(adminGroupId, adminId);
	}
	
	@Override
	public Map<String, Object> getChargerDetail(String chargerId) {
		return chargerDao.selectChargerDetail(chargerId);
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> insertCharger(Map<String, Object> map, String usid) throws Exception {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		List<Map<String, Object>> chargerList = (List<Map<String, Object>>) map.get("chargerList");
		
		// 동일한 S/N 번호로 등록하는지 체크
		List<String> sn = new ArrayList<String>();
		
		for(Map<String, Object> m : chargerList) {
			String serialNo = (String)m.get("serialNo");
			
			if(sn.contains(serialNo)) {
				resultMap.put("errorCd", "428006");
				resultMap.put("errorMsg", "동일한 S/N로 중복 등록할 수 없습니다. 다시 확인해주세요.");
				
				return resultMap;
			}
			sn.add(serialNo);
		}
		
		
		for(Map<String, Object> m : chargerList) {
			
			String name = (String) m.get("name");
			String serialNo = (String) m.get("serialNo");
			
			// 이미 등록되어 있는 충전기S/N  있는지 체크
			Map<String, Object> chargerInfo = chargerListDao.selectChargerSerialNo(serialNo);
			
			if(chargerInfo == null) {
				resultMap.put("errorCd", "428007");
				resultMap.put("errorMsg", "등록되지 않는 충전기의 S/N입니다.");
				
				return resultMap;
			// 충전기 등록
			} else if("402101".equals(chargerInfo.get("status"))) {
				
				// 충전기목록 테이블에서 충전기 정보 조회
				Map<String, Object> detail = chargerListDao.selectChargerInfo(serialNo);
				
				Integer chargerGroupId = (Integer) map.get("chargerGroupId");
				Integer capacity = (Integer) m.get("capacity");
				//String description = (String) m.get("description");
				String chargerId = (String) detail.get("chargerId");
				String mgmtNo = (String)detail.get("mgmtNo");
				String chargeRate = (String)detail.get("chargeRate");
				
				ChargerGroup chargerGroup = new ChargerGroup();
				chargerGroup.setChargerGroupId(chargerGroupId.longValue());
				
				
				// 충전기 insert
				Charger charger = new Charger();
				
				charger.setChargerId(chargerId);
				charger.setChargerGroupId(chargerGroupId.longValue());
				charger.setName(name);
				charger.setMgmtNo(mgmtNo);
				charger.setChargeRate(chargeRate);
				//charger.setDescription(description);
				charger.setCapacity(capacity.longValue());
				charger.setFstRgUsid(usid);
				charger.setLstChUsid(usid);
				
				// 충전기 상태 수정
				if (elcgDaoMybatis.isChargerChargeAvailable(chargerGroupId.longValue(), capacity)) {
					charger.setStatus("406101");
			    } else {
			    	charger.setStatus("406102");
			    }
				
				charger.setDetailStatus("406201");
				
				elcgDaoMybatis.insertCharger(charger);
				
				// 충전기목록 상태(status) update
				ChargerList cl = new ChargerList();
				cl.setChargerId(chargerId);
				cl.setStatus("402102");
				//cl.setSerialNo((String)chargerInfo.get("serialNo"));
				cl.setLstChUsid(usid);
				
				elcgDaoMybatis.updateChargerListStatus(cl);
				
				// 빌딩 상태 수정
				Integer bdId = (Integer) map.get("bdId");
				elcgDaoMybatis.updateBdChargeAvailable(bdId.longValue(), String.valueOf(usid));
				
			} else {
				resultMap.put("errorCd", "428008");
				resultMap.put("errorMsg", "이미 설치된 충전기의 S/N입니다.");
				
				return resultMap;
			}
		}
		
		return resultMap;
		
	}
	
	@Override
	public void updateCharger(Map<String, Object> map, String usid) {
		
		// 충전기 수정
		String name = (String) map.get("name");
		String chargerId = (String) map.get("chargerId");
		//String description = (String) map.get("description");
		Integer capacity = (Integer) map.get("capacity");
		
		
		Charger charger = new Charger();
		
		charger.setName(name);
		//charger.setDescription(description);
		charger.setCapacity(capacity.longValue());
		charger.setLstChUsid(usid);
		charger.setLstChDt(new Date());
		charger.setChargerId(chargerId);
		
		chargerDao.updateCharger(charger, usid);
	}
		
	@Override
	public List<Charger> searchByDate(Charger charger, int page, int rowPerPage, String sortCriterion,
										String sortDirection, String fromDate, String toDate) {
		
		return chargerDao.searchByDate(charger, page, rowPerPage, sortCriterion, sortDirection, fromDate, toDate);
	}
	
	@Override
	public int getChargerCount(String adminId, String fromDate, String toDate, String bdId, Long bdGroupId) {
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("adminId", adminId);
		param.put("fromDate", fromDate);
		param.put("toDate", toDate);
		param.put("bdId", bdId);
		param.put("bdGroupId", bdGroupId);
		
		return statAdjustDaoMybatis.getChargerCountByLandlord(param);
	}
	
	@Override
	public List<Charger> getChargerList(String adminId, String fromDate, String toDate,
											String bdId, Long bdGroupId, Pageable pageable) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", pageable.getOffset());
		param.put("rowPerPage", pageable.getRowPerPage());
		param.put("adminId", adminId);
		param.put("fromDate", fromDate);
		param.put("toDate", toDate);
		param.put("bdId", bdId);
		param.put("bdGroupId", bdGroupId);
		
		List<Map<String, Object>> list = statAdjustDaoMybatis.getChargerListByLandlord(param);
		
		List<Charger> chargerList = new ArrayList<>();
		for (Map<String, Object> map : list) {
			Charger charger = new Charger();
			charger.setChargerId(map.get("CHARGER_ID").toString());
			charger.setMgmtNo(map.get("MGMT_NO") != null ? map.get("MGMT_NO").toString() : null);
			charger.setAdminName(map.get("ADMIN_NAME") != null ? map.get("ADMIN_NAME").toString() : null);
			charger.setFstRgDt(map.get("FST_RG_DT") != null ? ((Date) map.get("FST_RG_DT")) : null);
			charger.setStatus(map.get("STATUS") != null ? map.get("STATUS").toString() : null);
			charger.setFstRgUsid(map.get("FST_RG_USID") != null ? map.get("FST_RG_USID").toString() : null);
			
			BdGroup bdGroup = new BdGroup();
			bdGroup.setName(map.get("BD_GROUP_NAME") != null ? map.get("BD_GROUP_NAME").toString() : null);
			
			Bd bd = new Bd();
			bd.setAdminId(map.get("ADMIN_ID") != null ? map.get("ADMIN_ID").toString() : null);
			bd.setName(map.get("BD_NAME") != null ? map.get("BD_NAME").toString() : null);
			bd.setBdGroup(bdGroup);
			
			ChargerGroup chargerGroup = new ChargerGroup();
			chargerGroup.setName(map.get("CHARGER_GROUP_NAME") != null ? map.get("CHARGER_GROUP_NAME").toString() : null);
			chargerGroup.setBd(bd);
			
			charger.setChargerGroup(chargerGroup);
			chargerList.add(charger);
		}
		
		return chargerList;
	}
	
	/** DevReset 메시지 전송 */
    public void mgmtCmdControl(String chargerId)
    {
    	Mgc mgc = new Mgc();
    	mgc.setExe("true");
    	mgc.setExra("0x8000");

    	try
        {
        	// onem2m.sktiot.com/61.250.21.54
            String LOG_PREFIX = ""; //this.getSubscriptionMgmtCmdControlLogPrefix(oid, mgc.getExra());
            String url = "http://onem2m.sktiot.com:9000/0000000000000004/v1_0/mgmtCmd-00000004" + chargerId + "_" + MgmtCmdType.DEV_RESET;
            
            List<Header> list = new ArrayList<>();
            list.add(new BasicHeader("Accept", "application/xml"));
            //list.add(new BasicHeader("locale", "ko"));
            list.add(new BasicHeader("uKey", "akdZS0NJMFVhVEJHcWdqYkkvbmJOM0hHcGxydTJ1MENNR2huUzN2VFE1TGhaVkx3d0pnckhsRThRcVRrdEdJUg=="));
            list.add(new BasicHeader("X-M2M-Origin", "EVC-API-Server"));
            list.add(new BasicHeader("X-M2M-RI", "1"));
            list.add(new BasicHeader("Content-Type", "application/vnd.onem2m-res.xml"));
            Header[] headers = list.toArray(new Header[list.size()]);

            String body = jaxbMarshallerService.marshal(mgc);
            String xml = httpClientService.httpPut(LOG_PREFIX, url, headers, body);

            int statusCode = httpClientService.getStatusLine().getStatusCode();
            TraceLog.info("[Http Status Code] %s", statusCode);
            if (statusCode != 200) return;

            TraceLog.info("[%s]", xml);
        	
        	}
        catch (Exception e)
        {
            TraceLog.error(e.getMessage(), e);
        }
    }
}
