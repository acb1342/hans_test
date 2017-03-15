package com.mobilepark.doit5.cms.elcg.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.common.util.ByteUtil;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.elcg.service.ChargerGroupService;
import com.mobilepark.doit5.elcg.service.ChargerService;
import com.mobilepark.doit5.statistics.dao.LogHistoryDaoMybatis;
import com.mobilepark.doit5.statistics.model.MessageType;
import com.mobilepark.doit5.statistics.model.TChargerMessage;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.elcg.controller
 * @Filename     : ErrorController.java
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
 * =================================================================================
 */

@Controller
public class ErrorController {

	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private BdService bdService;
	
	@Autowired
	private ChargerGroupService chargerGroupService;
	
	@Autowired
	private ChargerService chargerService;

	@Autowired
	private AdminService adminService;
	
	@Autowired
	private LogHistoryDaoMybatis logHistoryDaoMybaits;
	
	@RequestMapping("/elcg/error/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "status", required = false) String searchType,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "bdSelect", required = false) Long bdId,
			@RequestParam(value = "chargerGroupSelect", required = false) Long chargerGroupId,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate,
			@RequestParam(value = "errorType", required = false) String cmd,
			@RequestParam(value = "setChargerMgmtNoSelect", required = false) String mgmtNo) throws Exception {

		TraceLog.info("[page:%s] [status:%s] [bdGroupId:%d] [bdSelect:%d] [chargerGroupSelect:%d] [fromDate:%s] [toDate:%s] [errorType:%s] [setChargerMgmtNoSelect:%s]\n",
							page, searchType, bdGroupId,bdId, chargerGroupId, fromDate, toDate, cmd, mgmtNo);
		
		if (StringUtils.equals(mgmtNo, "0")) mgmtNo = "";
		
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);

		long count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		int startRow = (pageNum - 1) * rowPerPage;
		
		String[] arrayCmd = null;
		if (adminGroup.getId() == 2 || adminGroup.getId() == 3) {
			arrayCmd = new String[4];
			arrayCmd[0] = "C00108";
			arrayCmd[1] = "C00109";
			arrayCmd[2] = "C0010C";
			arrayCmd[3] = "C0010D";
		}
		if (StringUtils.isNotEmpty(cmd)) {
			if (cmd.indexOf(",") == -1) {
				arrayCmd = new String[1];
				arrayCmd[0] = cmd;
			}
			else arrayCmd = cmd.split(",");
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("startRow", startRow);
		param.put("rowPerPage", rowPerPage);
		param.put("cmd", arrayCmd);
		param.put("mgmtNo", mgmtNo);
		
		if (StringUtils.isNotEmpty(searchType)) {
			param.put("fromDate", changeFormat(fromDate, 8));
			param.put("toDate", changeFormat(toDate, 8));
		}
		
		// 2 - 설치자
		if (adminGroup.getId() == 2 ) 
			param.put("wkUsid", admin.getId());
		// 3 - 건물주 
		if (adminGroup.getId() == 3 ) 
			param.put("adminId", admin.getId());
		
		List<Map<String, Object>> list = null;
		
		count = logHistoryDaoMybaits.getHistChargeIfCount(param);
		list = logHistoryDaoMybaits.getHistChargeIfList(param);
		
		for (Map<String, Object> map : list) {
			String message = map.get("MSG").toString();
			TChargerMessage tChargerMessage = this.parse(message);
			map.put("RFID", tChargerMessage.getUserId());
			map.put("WATT", tChargerMessage.getWatt());
		}
		
		PaginatedList chargerIfList = new PaginatedListImpl(list, pageNum, count, rowPerPage);

		ModelAndView mav = new ModelAndView("elcg/error/search");
		mav.addObject("rownum", count);
		mav.addObject("page", pageNum);
		mav.addObject("type", searchType);
		mav.addObject("chargerIfList", chargerIfList);
		
		// 화면에서 셀렉트박스 유지
		if (bdGroupId != null && bdGroupId > 0) {
			// 건물
			BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
			Bd selBd = new Bd();
			selBd.setBdGroup(selGroupList);
			List<Bd> selBdList = this.bdService.search(selBd);
			mav.addObject("selBdList", selBdList);
			mav.addObject("selBdGroupId", bdGroupId);
	
			// 충전그룹
			ChargerGroup selCg = new ChargerGroup();
			selCg.setBdGroupId(bdGroupId);
			selCg.setBdId(bdId);
			List<ChargerGroup> selCgList = this.chargerGroupService.search(selCg);
			mav.addObject("selCgList", selCgList);
			
			// 충전기 관리번호
			Charger selCharger = new Charger();
			selCharger.setChargerGroupId(chargerGroupId);
			List<Charger> selChargerList = this.chargerService.search(selCharger);
			mav.addObject("selChargerList", selChargerList);
		}
	
		return mav;
	}
	
	
	@RequestMapping("/elcg/error/chargerInfoPopup.htm")
	public ModelAndView infoPopup(@RequestParam(value = "paramId", required = true) String chargerId) {
		
		ModelAndView mav = new ModelAndView("elcg/error/chargerInfoPopup");
		
		Charger charger = this.chargerService.get(chargerId);
		mav.addObject("charger", charger);
		
		if(charger == null) return mav;
		try {
			Admin admin = this.adminService.getById(charger.getChargerGroup().getBd().getAdminId());
			mav.addObject("owner", admin);
		} catch(Exception e) {
			
		}
		
		return mav;
	}
	
	private String changeFormat(String date, int strNumber) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, strNumber);
			
			return date;
		}
		return null;
	}

	private void setTChargerMessage(TChargerMessage message, byte type, byte[] body)
	{
		switch(type) {
		case MessageType.SERIAL_NUMBER :
			message.setSerialNumber(ByteUtil.hexString(body));
			break;
		case MessageType.CHARGER_STATUS :
			message.setChargerStatus(body[0]);
			message.setCmd(ByteUtil.hexString(MessageType.CHARGER_STATUS) + ByteUtil.hexString((byte)0x01) + ByteUtil.hexString(body[0]));
			break;
		case MessageType.TOTAL_WATT :
			message.setWatt(ByteUtil.getInt(body));
			break;
		case MessageType.USER_ID :
			message.setUserId(ByteUtil.hexString(body));
			break;
		case MessageType.USER_TYPE :
			message.setUserType(body[0]);
			break;
		}
	}

	private TChargerMessage parse(String message) throws Exception
	{
		byte[] buffer = ByteUtil.getBytes(message);
		if (buffer.length < 4) return null;
		
		TChargerMessage tChargerMessage = new TChargerMessage(message);

		int read = 0;
		while(true) {
			if (buffer.length <= read) break;
			
			byte type = buffer[read];
			read ++;
			
			byte length = buffer[read];
			read ++;
			
			byte[] body = new byte[length];
			System.arraycopy(buffer, read, body, 0, body.length);
			read += length;
					
			setTChargerMessage(tChargerMessage, type, body);
		}
		
		return tChargerMessage;
	}
}
