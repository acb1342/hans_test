package com.mobilepark.doit5.auth.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.mobilepark.doit5.common.util.NfcTokenUtil;
import com.mobilepark.doit5.customer.dao.CustomerDaoMybatis;
import com.mobilepark.doit5.customer.model.Member;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.auth.service
 * @Filename     : AuthService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2017. 1. 17.       최초 버전
 * =================================================================================
 */

@Service
@Transactional
public class AuthService {


	@Autowired
	private CustomerDaoMybatis customerDaoMybatis;

	public Map<String, Object> login(Member member, String clientType, String deviceId, String pushToken) {
		
		Map<String, Object> memberMap = new HashMap<>();
		memberMap.put("subsId", member.getSubsId());
		Member selectedMember = customerDaoMybatis.selectMember(memberMap);
		
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
			
			Long usid = customerDaoMybatis.insertMember(member);
			
			Map<String, Object> usidMap = new HashMap<>();
			usidMap.put("usid", usid);
			selectedMember = customerDaoMybatis.selectMember(usidMap);
			
			// 처음 로그인 시 사용자 충전 조건 Default 완전충전으로 설정
			Map<String, Object> chargeCondMap = new HashMap<>();
			chargeCondMap.put("usid", selectedMember.getId());
			chargeCondMap.put("chargeCond", "309101");
			
			customerDaoMybatis.insertChargeCondition(chargeCondMap);
			
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

					}
				} else if (StringUtils.equals(originOs, "IOS")) {
					
					// 기존 IOS 사용자일 경우 현재 접속한 OS 및 푸쉬토큰 정보 저장
					selectedMember.setPushOs(clientType);
					selectedMember.setPushToken(pushToken);

					
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
			
			customerDaoMybatis.updateMember(selectedMember);
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
	
}
