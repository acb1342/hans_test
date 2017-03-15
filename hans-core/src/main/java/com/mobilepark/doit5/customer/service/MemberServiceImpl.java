package com.mobilepark.doit5.customer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.common.tid.TidAPI;
import com.mobilepark.doit5.common.util.NfcTokenUtil;
import com.mobilepark.doit5.customer.dao.CustomerDaoMybatis;
import com.mobilepark.doit5.customer.dao.MemberDao;
import com.mobilepark.doit5.customer.model.Car;
import com.mobilepark.doit5.customer.model.ChargeCond;
import com.mobilepark.doit5.customer.model.Member;
import com.mobilepark.doit5.history.dao.PushMsgDaoMybatis;
import com.mobilepark.doit5.statistics.dao.LogHistoryDaoMybatis;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.customer.dao.CarDao;
import com.mobilepark.doit5.customer.dao.ChargeCondDao;
import com.mobilepark.doit5.customer.model.Close;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : MemberSerivceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
@Transactional
public class MemberServiceImpl extends AbstractGenericService<Member, Long> implements MemberService {

	@Autowired
	private MemberDao memberDao;
	
	@Autowired
	private CarDao carDao;
	
	@Autowired
	private ChargeCondDao chargeCondDao;
	
	@Autowired
	private PushMsgDaoMybatis pushMsgDaoMybatis;
	
	@Autowired
	private CustomerDaoMybatis customerDaoMybatis;
	
	@Autowired
	private LogHistoryDaoMybatis logHistoryDaoMybaits;

	@Override
	protected GenericDao<Member, Long> getGenericDao() {
		return memberDao;
	}


	@Override
	public Map<String, Object> getUserDetail(Long usid) {
		return memberDao.selectUserDetail(usid);
	}
	
	@Override
	public Car insertCar(Car car) {
		return carDao.insertCar(car);
	}
	
	@Override
	public int updatePayment(Member member) {
		return memberDao.updatePayment(member);
	}
	
	@Override
	public void insertCustClose(Close custClose, Member member) throws Exception {
		
		String retCd = TidAPI.withdraw(member.getSub());
		
		if (StringUtils.isBlank(retCd) || !StringUtils.equals(retCd, "IFH_1001")) {
			throw new Exception("TIDAPI_ERROR");
		}
		
		// 탈퇴정보 등록
		//customerDaoMybatis.insertCustClose(custClose);
		
		// 차량정보 삭제
		customerDaoMybatis.deleteCustCar(member.getId());
		
		// DeviceID 삭제
		customerDaoMybatis.updateDeviceId(member.getId());
		
		// RFID 카드 중지처리
		//customerDaoMybatis.updateStopCard(member.getId());
		
	}
	
	@Override
	public void updateCarNumber(Car car) {
		carDao.updateCarNumber(car);
	}
	
	@Override
	public Map<String, Object> getCarDetail(Long usid) {
		return carDao.selectCarDetail(usid);
	}
	
	@Override
	public ChargeCond insertChargeCond(ChargeCond chargeCond) {
		return chargeCondDao.insertChargeCond(chargeCond);
	}
	
	@Override
	public Map<String, Object> selectChargeCondDetail(Long usid) {
		return chargeCondDao.selectChargeCondDetail(usid);
	}
	
	@Override
	public void updateChargeCond(ChargeCond chargeCond) {
		chargeCondDao.updateChargeCond(chargeCond);
	}
	
	@Override
	public Map<String, Object> login(Member member, String clientType, String deviceId, String pushToken) {
		Member selectedMember = memberDao.selectUserBySktId(member.getSubsId());
		
		boolean isCertToken = false;
		
		// 처음 로그인한 사용자(탈퇴한 사용자도 새로운 사용자로 생성)
		if (selectedMember == null || StringUtils.equals(selectedMember.getStatus(), "301103")) {
			
			member.setStatus("301101"); // 준회원
			member.setFstRgDt(new Date());
			member.setFstRgUsid(member.getSubsId());
			
			if (!StringUtils.equals(clientType, "WEB")) {
				
				// 앱으로 접속한 사용자일 경우 OS, Device ID, Push Token 정보 저장
				member.setOs(clientType);
				member.setDeviceId(deviceId);
				member.setPushToken(pushToken);
			}
			
			selectedMember = memberDao.insertUser(member);
			
			// 처음 로그인 시 사용자 충전 조건 Default 완전충전으로 설정
			ChargeCond chargeCond = new ChargeCond();
			chargeCond.setId(selectedMember.getId());
			chargeCond.setChargeCond("309101");
			chargeCond.setFstRgUsid(selectedMember.getId().toString());
			chargeCond.setFstRgDt(new Date());
			chargeCond.setLstChUsid(selectedMember.getId().toString());
			chargeCond.setLstChDt(new Date());
			
			chargeCondDao.insertChargeCond(chargeCond);
			
			if (StringUtils.equals(clientType, "ANDROID")) {
				
				// 안드로이드 사용자일 경우 인증토큰 정보 저장
				String certToken = NfcTokenUtil.getNfcCertToken(deviceId, selectedMember.getId());
				selectedMember.setCertToken(certToken);
				
				// history insert
				Map<String, Object> custHist = new HashMap<String, Object>();
				custHist.put("usid", selectedMember.getId());
				custHist.put("accessDevice", "313104");
				custHist.put("logType", "313207");
				custHist.put("resultCode", "101101");
				custHist.put("certCardNo", certToken);
				
				customerDaoMybatis.insertCustHist(custHist);
				
				isCertToken = true;
			}
			
		// 기존 사용자
		} else {
			
			String originOs = selectedMember.getOs();
			String originDeviceId = selectedMember.getDeviceId();
			
			if (!StringUtils.equals(clientType, "WEB")) {
				
				if (StringUtils.isBlank(originOs)) {
					
					// 앱으로 처음 접속한 기존사용자일 경우 OS, Device ID, Push Token 정보 저장
					selectedMember.setOs(clientType);
					selectedMember.setDeviceId(deviceId);
					selectedMember.setPushToken(pushToken);
					
					if (StringUtils.equals(clientType, "ANDROID")) {
						
						// 안드로이드 사용자일 경우 인증토큰 정보 저장
						String certToken = NfcTokenUtil.getNfcCertToken(deviceId, selectedMember.getId());
						selectedMember.setCertToken(certToken);
						
						// history insert
						Map<String, Object> custHist = new HashMap<String, Object>();
						custHist.put("usid", selectedMember.getId());
						custHist.put("accessDevice", "313104");
						custHist.put("logType", "313207");
						custHist.put("resultCode", "101101");
						custHist.put("certCardNo", certToken);
						
						customerDaoMybatis.insertCustHist(custHist);
						
						isCertToken = true;
					}
					
				} else if (StringUtils.equals(originOs, "ANDROID")) {
					
					if (StringUtils.equals(originDeviceId, deviceId)) {
						
						// 안드로이드 기존 휴대폰으로 로그인한 경우 NFC 토큰 갱신
						String certToken = NfcTokenUtil.getNfcCertToken(deviceId, selectedMember.getId());
						selectedMember.setCertToken(certToken);
						
						isCertToken = true;
						
						// history insert
						Map<String, Object> custHist = new HashMap<String, Object>();
						custHist.put("usid", selectedMember.getId());
						custHist.put("accessDevice", "313104");
						custHist.put("logType", "313209");
						custHist.put("resultCode", "101101");
						custHist.put("certCardNo", certToken);
						
						customerDaoMybatis.insertCustHist(custHist);
						
					} else {
						
						String msg = null;
						String os = null;
						String category = null;
						
						if (StringUtils.equals(clientType, "ANDROID")) {
							msg = "기존에 사용하시던 휴대폰이 아닌 다른 기기에서 로그인하셨습니다. 회원인증을 위한 NFC 태깅은 현재 로그인 중인 기기에서는 사용할 수 없습니다. NFC 태깅 사용을 원하시면 기존의 사용하시던 NFC 중지처리 및 현재 단말로의 신규 발급을 위해 고객센터로 문의하시기 바랍니다.";
							os = "301401";
							category = "901108";
						} else {
							msg = "새로운 기기에서 로그인하셨습니다. 회원인증을 위한 NFC 태깅은 아이폰에서는 사용할 수 없습니다. 기존에 발급된 NFC 중지처리를 위해 고객센터로 문의하시기 바랍니다.";
							os = "301402";
							category = "901110";
						}
						
						// 변경된 휴대폰으로 로그인한 경우 push 안내(IOS, ANDROID 모두)
						Map<String, Object> pushMap = new HashMap<>();
						pushMap.put("custType", "101206");
						pushMap.put("usid", selectedMember.getId());
						pushMap.put("custName", selectedMember.getName());
						pushMap.put("os", os);
						pushMap.put("mobile", selectedMember.getMdn());
						pushMap.put("title", "새로운 단말 로그인 안내");
						pushMap.put("msg", msg);
						pushMap.put("category", category);
						pushMap.put("pushToken", pushToken);
						
						pushMsgDaoMybatis.insertPushQueue(pushMap);
					}
				} else if (StringUtils.equals(originOs, "IOS")) {
					
					// 기존 IOS 사용자일 경우 현재 접속한 OS 및 푸쉬토큰 정보 저장
					selectedMember.setPushOs(clientType);
					selectedMember.setPushToken(pushToken);
					
					if (StringUtils.equals(clientType, "ANDROID")) {
						
						// IOS 사용자가 ANDROID로 접속한 경우 push 안내
						Map<String, Object> pushMap = new HashMap<>();
						pushMap.put("custType", "101206");
						pushMap.put("usid", selectedMember.getId());
						pushMap.put("custName", selectedMember.getName());
						pushMap.put("os", "301401");
						pushMap.put("mobile", selectedMember.getMdn());
						pushMap.put("title", "새로운 단말 로그인 안내");
						pushMap.put("msg", "기존에 사용하시던 아이폰이 아닌 Android 휴대폰에서 로그인하셨습니다. Android 단말에서는 NFC 태깅이 가능하므로, 기존의 아이폰을 더 이상 사용하지 않으신다면, NFC 신규 발급을 위해 고객센터로 문의하시기 바랍니다.");
						pushMap.put("category", "901109");
						pushMap.put("pushToken", pushToken);
						
						pushMsgDaoMybatis.insertPushQueue(pushMap);
					}
					
				}
			}
			
			// TID 관련 사용자 필수 정보 업데이트
			selectedMember.setTidToken(member.getTidToken());
			selectedMember.setPwModDate(member.getPwModDate());
			selectedMember.setSub(member.getSub());
			selectedMember.setExp(member.getExp());
			selectedMember.setIat(member.getIat());
			selectedMember.setIst(member.getIst());
			selectedMember.setSubsId(member.getSubsId());
			selectedMember.setRcptAgr(member.getRcptAgr());
			selectedMember.setRcptAgrDate(member.getRcptAgrDate());
			selectedMember.setLstChDt(new Date());
			selectedMember.setLstChUsid(String.valueOf(selectedMember.getId()));
			
			// TID 관련 사용자 옵션 정보는 값이 존재할 경우에만 업데이트
			if (StringUtils.isNotBlank(member.getMdn())) {
				selectedMember.setMdn(member.getMdn());
			}
			
			if (StringUtils.isNotBlank(member.getChnlSubsDate())) {
				selectedMember.setChnlSubsDate(member.getChnlSubsDate());
			}
			
			if (StringUtils.isNotBlank(member.getName())) {
				selectedMember.setName(member.getName());
			}
			
			if (StringUtils.isNotBlank(member.getBirth())) {
				selectedMember.setBirth(member.getBirth());
			}
			
			if (StringUtils.isNotBlank(member.getSex())) {
				selectedMember.setSex(member.getSex());
			}
			
			if (StringUtils.isNotBlank(member.getNameVrfd())) {
				selectedMember.setNameVrfd(member.getNameVrfd());
			}
			
			if (StringUtils.isNotBlank(member.getEmailAddr())) {
				selectedMember.setEmailAddr(member.getEmailAddr());
			}
		}
		
		// 사용자 로그인 이력 insert
		Map<String, Object> custHist = new HashMap<String, Object>();
		
		String clientTypeCd = "";
		
		if("IOS".equals(clientType)) {
			clientTypeCd = "313105";
		} else if("ANDROID".equals(clientType)) {
			clientTypeCd = "313104";
		} else {
			clientTypeCd = "313101";
		}
		
		custHist.put("usid", selectedMember.getId());
		custHist.put("accessDevice", clientTypeCd);
		custHist.put("logType", "313201");
		custHist.put("resultCode", "101101");
		
		customerDaoMybatis.insertCustHist(custHist);
		
		
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("member", selectedMember);
		resultMap.put("isCertToken", isCertToken);
		
		return resultMap;
	}

	@Override
	public List<Map<String, Object>> getHistCustNfcList(String usid) {
		String[] logTypes = {"313207", "313208"}; //, "313209"};
		Map<String, Object> param = new HashMap<>();
		param.put("logTypes", logTypes);
		param.put("usid", usid);
		param.put("startRow", 0);
		param.put("rowPerPage", 100);

		return logHistoryDaoMybaits.getHistCustList(param);
	}
	
	@Override
	public void insertCustHist(Long usid, String addInfo) {
		// history insert
		Map<String, Object> custHist = new HashMap<String, Object>();
		custHist.put("usid", usid);
		custHist.put("accessDevice", "313106");
		custHist.put("logType", "313208");
		custHist.put("resultCode", "101101");
		custHist.put("addInfo", addInfo);
		
		customerDaoMybatis.insertCustHist(custHist);
	}
}
