package com.hans.sses.cms.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hans.sses.admin.service.EnergyService;
import com.hans.sses.admin.model.Admin;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : SSES
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : EnergyController.java
 * @Description  : 에너지 관리
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 30.      최초 버전
 * =================================================================================
 */
@Controller
public class EnergyController {
	@Autowired
	private EnergyService energyService;

	/**
	 * 에너지 등록
	 */
	@RequestMapping(value = "/energy/energy/create.json", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> params, Admin admin, HttpSession session,
                               SessionStatus sessionStatus) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {

		params.put("regDate", new Date());
				
		this.energyService.EnergyCreate(params);
		
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/admin/energy/detail.htm?id=" + params.get("id"));
	}

	/**
	 * 에너지 목록
	 */
		
	@RequestMapping("/energy/energy/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		ModelAndView mav = new ModelAndView("energy/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		param.put("searchType", searchType);
		param.put("searchValue", searchValue);		
		//////////////////////////////////////////////////

		param.put("pageNum", pageNum);
		param.put("rowPerPage", rowPerPage);
		
		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);
		
		System.out.println("param = " + param);
		System.out.println("startRow = " + param.get("startRow") + " / rowPerPage = " + param.get("rowPerPage"));
		
		int countAll = this.energyService.getCount(param);
		
		List<Map<String, String>> list = this.energyService.getEnergyList(param);
		
		mav.addObject("energyList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/dashboard/energy/status.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("energy/energy_stat");

		return mav;
	}

	@RequestMapping(value = "/dashboard/energy/status.json", method = RequestMethod.GET)
	public JSONObject getEnergy(
			@RequestParam Map<String, Object> params) {
		JSONObject joStat =  new JSONObject();
		
		System.out.println("Energy Param = " + params.toString());
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if(params.get("radioDate").equals("D")){
			list = this.energyService.getDayEnergyList(params);
		}
		else{
			list = this.energyService.getMonEnergyList(params);
		}		
		
		System.out.println("Energy list = " + list.toString());
		
		double dualW = 0;
		String watt[];
		String event[];
		
		/*
		if(Util.getNvl(hash.get("part_code")).equals("29")) {
    		// 로그인
    		dualKw += Integer.parseInt(Util.getNvl(hash.get("electrical_power")));
    	} else if(Util.getNvl(hash.get("part_code")).equals("31")){
    		// 로그아웃
    		dualKw -= Integer.parseInt(Util.getNvl(hash.get("electrical_power")));
		} else if(Util.getNvl(hash.get("part_code")).equals("34")) {
			// 절전모드
			dualKw -= Integer.parseInt(Util.getNvl(hash.get("electrical_power"))) - 1;
		} else if(Util.getNvl(hash.get("part_code")).equals("35")) {
			// 절전모드해제
			dualKw += Integer.parseInt(Util.getNvl(hash.get("electrical_power"))) - 1;
		}
		*/
		
		
		System.out.println("==================================");
		for(int i=0; i < list.size(); i++){
			
			System.out.println("USER_SEQ = " +list.get(i).get("userSeq"));
			System.out.println("REG_DATE = " +list.get(i).get("regDate"));
			watt = String.valueOf(list.get(i).get("wattList")).split(";");
			event = ((String) list.get(i).get("eventList")).split(";");			
			if(watt.length > 0){
				for(int j=0; j<watt.length;j++){
					System.out.println("WATT ["+j+"] = " + Double.valueOf(watt[j]));
					
					if(event[j].equals("1")){
						dualW += Double.valueOf(watt[j]);
					}
					else if(event[j].equals("0")){
						dualW -= Double.valueOf(watt[j]);
					}
					else if(event[j].equals("2")){
						dualW -= Double.valueOf(watt[j])-1;					
					}
					else if(event[j].equals("3")){
						dualW += Double.valueOf(watt[j])-1;
					}
				}
				
			}
			else{
				System.out.println("WATT ["+i+"] = " + list.get(i).get("wattList"));

			}
			System.out.println("--------------------------------");
			System.out.println("DualW = " + dualW);
			
		}
		
		System.out.println("==================================");
		
		if(list.size()==0){
		}
		
		joStat.put("series", list);
		joStat.put("searchType", params.get("searchType"));
		
		System.out.println("JSON = " + joStat);
		
		return joStat;
	}
	
	
}
