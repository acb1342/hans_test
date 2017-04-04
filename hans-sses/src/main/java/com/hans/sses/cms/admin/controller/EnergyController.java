package com.hans.sses.cms.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
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
	/*
	@RequestMapping(value = "/dashboard/energy/status.json", method = RequestMethod.GET)
	public JSONObject getEnergy(
			@RequestParam(value = "day", required = false) String day) {
		JSONObject joStat =  new JSONObject();
		
		List<Map<String, String>> list = this.energyService.getDayEnergyList(day);
		
		JSONArray seriesJA = new JSONArray();
		JSONArray legend = new JSONArray();
		for(int i=0; i<list.size();i++){
			
			JSONObject seriesJo = new JSONObject();
						
			JSONArray data = new JSONArray();
			data.add(list.get(i).get("sumPower"));			
			
			seriesJo.put("name", list.get(i).get("indentityCode"));
			seriesJo.put("type", "line");
			seriesJo.put("data", data);
						
			seriesJA.add(seriesJo);
			
			legend.add(list.get(i).get("indentityCode"));
			
		}
		joStat.put("series", seriesJA);
		joStat.put("legend", legend);
		
		
		
		System.out.println("JSON = " + joStat);
		
		return joStat;
	}*/

	@RequestMapping(value = "/dashboard/energy/status.json", method = RequestMethod.GET)
	public JSONObject getEnergy(
			@RequestParam(value = "beforeday", required = false) String beforeday,
			@RequestParam(value = "afterday", required = false) String afterday) {
		JSONObject joStat =  new JSONObject();
		
//		List<Map<String, String>> list = this.energyService.getDayEnergyList(beforeday,afterday);
//
//
//		joStat.put("series", list);
//
//		System.out.println("JSON = " + joStat);
		
		return joStat;
	}

	
	
}
