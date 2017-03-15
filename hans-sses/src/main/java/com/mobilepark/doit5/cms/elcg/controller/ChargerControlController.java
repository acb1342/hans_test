package com.mobilepark.doit5.cms.elcg.controller;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.mobilepark.doit5.elcg.model.ChargerList;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.elcg.service.ChargerGroupService;
import com.mobilepark.doit5.elcg.service.ChargerListService;
import com.mobilepark.doit5.elcg.service.ChargerService;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.elcg.controller
 * @Filename     : ChargerControlController.java
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
public class ChargerControlController {

	@Autowired
	private ChargerListService chargerListService;
	
	@Autowired
	private ChargerService chargerService;
	
	@Autowired
	private ChargerGroupService chargerGroupService;
	
	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private BdService bdService;
	
	
	@RequestMapping("/elcg/controller/search.htm")
	public ModelAndView search(
			@RequestParam(value = "status", required = false) String type,
			@RequestParam(value = "mgmtNo", required = false) String mgmtNo,
			@RequestParam(value = "bdSelect", required = false) String bdSelect,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "chargerGroupSelect", required = false) String chargerGroupSelect) {
		
		Long chargerGroupId = 0L;
		try {
			chargerGroupId = Long.parseLong(chargerGroupSelect);
		} catch (Exception e) {
			chargerGroupId = 0L;
		}
		
		ModelAndView mav = new ModelAndView("elcg/controller/search");
		mav.addObject("type", type);
		
		if(StringUtils.isEmpty(type)) {
			mav.addObject("type", "default");
			return mav;
		}
		
		if(type.equals("C")) {
			ChargerList chargerList = new ChargerList();
			chargerList.setMgmtNo(mgmtNo);
			List<ChargerList> list = this.chargerListService.search(chargerList);
			
			if(list.size() == 0 || list == null) mav.addObject("type", "fail");
			else {
				Charger charger = this.chargerService.get(list.get(0).getChargerId());
				if (charger == null) mav.addObject("type", "fail");
				mav.addObject("charger", charger);
				mav.addObject("chargerList",list.get(0));
			} 
			
		} else if(type.equals("CG")) {
			ChargerGroup chargerGroup = this.chargerGroupService.get(chargerGroupId);
			
			mav.addObject("chargerGroup",chargerGroup);
			
			Charger charger = new Charger();
			charger.setChargerGroup(chargerGroup);
					
			mav.addObject("chargerList", this.chargerService.search(charger));
			
			// 화면에서 상세/동명 유지
			if (bdGroupId != null && bdGroupId > 0) {
				BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
				Bd selBd = new Bd();
				selBd.setBdGroup(selGroupList);
				List<Bd> selBdList = this.bdService.search(selBd);
				mav.addObject("selBdList", selBdList);
				mav.addObject("selBdGroupId", bdGroupId);
		
				ChargerGroup selCg = new ChargerGroup();
				selCg.setBdGroupId(bdGroupId);
				selCg.setBdId(Long.parseLong(bdSelect));
				List<ChargerGroup> selCgList = this.chargerGroupService.search(selCg);
				mav.addObject("selCgList", selCgList);
			}
			
		}
		
		return mav;
	}
	
	
	@RequestMapping("/elcg/controller/popup.htm")
	public ModelAndView popup(@RequestParam(value = "bdGroupName", required = false) String bdGroupName) {
		
			ModelAndView mav = new ModelAndView("elcg/controller/popup");
			BdGroup bdGroup = new BdGroup();
			List<BdGroup> list = this.bdGroupService.searchBdGroupName(bdGroup, bdGroupName);
			//TraceLog.debug(list.toString());
			mav.addObject("list", list);
			  
			return mav;
	}
	
	@RequestMapping(value = "/elcg/controller/searchPopup.json", method = RequestMethod.POST)
	public void searchPopup(HttpServletResponse response, HttpServletRequest request,
								@RequestParam(value = "search") String search) throws IOException {
		
		BdGroup bdGroup = new BdGroup();
		
		List<BdGroup> list = this.bdGroupService.searchBdGroupName(bdGroup, search);
		
		JSONArray jarr = new JSONArray();
		JSONObject jobj = null;
		
		for(BdGroup obj : list) {
			try {
				jobj = new JSONObject();
				jobj.put("name", obj.getName());
				jarr.put(jobj);
			} catch (JSONException e) {
				
				e.printStackTrace();
			}
		}
		response.setContentType("application/json ; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().write(jarr.toString());
		
	}
	
	@RequestMapping(value = "/elcg/controller/setBdSelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Bd> setBdSelect(@RequestParam(value = "bdGroupId", required = true) String bdGroupId)
											throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		BdGroup groupList = this.bdGroupService.get(Long.parseLong(bdGroupId));

		Bd bd = new Bd();
		bd.setBdGroup(groupList);
		List<Bd> bdList = this.bdService.search(bd);
			
		if(bdList.size() != 0) return bdList;
				
		return null;
	}
	
	
	@RequestMapping(value = "/elcg/controller/setChargerGroupSelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<ChargerGroup> setChargerGroupSelect(@RequestParam(value = "bdSelect", required = true) String bdId)
											throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		if(StringUtils.isNotEmpty(bdId)) {
			Bd bdList = this.bdService.get(Long.parseLong(bdId));
			ChargerGroup chargerGroup = new ChargerGroup();
			chargerGroup.setBdGroupId(bdList.getBdGroup().getBdGroupId());
			chargerGroup.setBd(bdList);
			List<ChargerGroup> list = this.chargerGroupService.search(chargerGroup);
		
			if (list == null || list.size() == 0) return null;
	
			return list;
		}
		return null;
	}
	
	@RequestMapping(value = "/elcg/controller/setChargerMgmtNoSelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Charger> setChargerMgmtNoSelect(@RequestParam(value = "chargerGroupSelect", required = true) String chargerGroupId)
											throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		ChargerGroup chargerGroup = this.chargerGroupService.get(Long.parseLong(chargerGroupId));
		Charger charger = new Charger();
		charger.setChargerGroup(chargerGroup);
		charger.setIsSelectBox("y");
		List<Charger> chargerList = this.chargerService.search(charger);
		
		if(chargerList.isEmpty()) return null;
		
		return chargerList;
	}
	
	
	// 충전기 재시작
	@RequestMapping(value = "/elcg/controller/restart.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean chargerRestart(@RequestParam(value = "chargerId", required = true) String chargerId)
									throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		
		try {
			this.chargerService.mgmtCmdControl(chargerId);
			return true;
		} catch(Exception e) {
			return false;
		}
		
	}
	
	
}
