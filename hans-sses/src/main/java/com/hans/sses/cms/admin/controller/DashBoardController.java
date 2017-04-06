package com.hans.sses.cms.admin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hans.sses.admin.service.DashboardService;

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
public class DashBoardController {
	
	@Autowired
	private DashboardService dashboardService;
	
	@RequestMapping(value = "/admin/dashboard/mainView.htm", method = RequestMethod.GET)
	public ModelAndView mainForm() {
		ModelAndView mav = new ModelAndView("admin/dashboard/mainView");

		return mav;
	}

	@RequestMapping(value = "/admin/dashboard/energy.json", method = RequestMethod.GET)
	public JSONObject getEnergy() {
		JSONObject joStat =  new JSONObject();
		
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		list = this.dashboardService.getEnergyList();
		
		System.out.println("DASH LIST= " + list.toString());
				
		joStat.put("series", list);
		
		System.out.println("JSON = " + joStat);
		
		return joStat;
	}
	
}
