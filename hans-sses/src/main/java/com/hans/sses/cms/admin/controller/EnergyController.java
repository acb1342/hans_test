package com.hans.sses.cms.admin.controller;

import com.hans.sses.admin.service.EnergyService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Date;

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
	public void create(@RequestParam Map<String, Object> params){

		System.out.println("Energy Param = " + params.toString());
		
		params.put("regDate", new Date());
								
		this.energyService.EnergyCreate(params);

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
		
		int countAll = this.energyService.getCount(param);
		
		List<Map<String, String>> list = this.energyService.getEnergyList(param);
		
		mav.addObject("energyList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	/**
	 * 에너지 계산
	 */
	
	@RequestMapping(value = "/dashboard/energy/status.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("energy/energy_stat");

		return mav;
	}

	
	/**
	 * 에너지 계산
	 */
	@RequestMapping(value = "/dashboard/energy/status.json", method = RequestMethod.GET)
	public JSONObject getEnergy(
			@RequestParam Map<String, Object> params) {
		
		JSONObject joStat =  new JSONObject();
		String[] dualWList;    // 그룹 별 총 전력량 배열
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		if(params.get("radioDate").equals("D")){
			list = this.energyService.getDayEnergyList(params);
		}
		else{
			list = this.energyService.getMonEnergyList(params);
		}	
		
		dualWList = getWattData(list);
		
		joStat.put("series", list);
		joStat.put("data", dualWList);
		joStat.put("searchType", params.get("searchType"));
		
		return joStat;
	}
	
	/**
	 * 에너지 계산식
	 */
	public String[] getWattData(List<Map<String, Object>> list){
		String[] dualWList = new String[list.size()];

		for(int i=0; i < list.size(); i++){

			double dualW = 0;			// 총 전력량
			String[] watt;			// watt 배열
			String[] uptime;			// uptime 배열
			String[] savingtime;		// savingtime 배열
			
			// 값이 하나인 경우 	
			if(String.valueOf(list.get(i).get("upTimeList")).indexOf(";") < 0){
				watt = new String[1]; uptime = new String[1]; savingtime = new String[1];  
		
				uptime[0] = String.valueOf(list.get(i).get("upTimeList"));
				savingtime[0] = String.valueOf(list.get(i).get("savingTimeList"));
				watt[0] = String.valueOf(list.get(i).get("wattList"));
			}
			else{
				uptime = String.valueOf(list.get(i).get("upTimeList")).split(";");
				savingtime = String.valueOf(list.get(i).get("savingTimeList")).split(";");
				watt = String.valueOf(list.get(i).get("wattList")).split(";");
			}
			
			for(int j=0;j<uptime.length;j++){
				
				int w = Integer.parseInt(watt[j]);
				int u = Integer.parseInt(uptime[j]);
				int s = Integer.parseInt(savingtime[j]);
				
				dualW += ((u-s)* w)/3600.0/1000.0;
			}
			
			dualW = Double.parseDouble(String.format("%.4f" , dualW));
			dualWList[i]=Double.toString(dualW);			
		}		
		return dualWList;		
	}
	
}
