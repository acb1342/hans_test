package com.hans.sses.cms.admin.controller;

import java.util.ArrayList;
import java.util.Arrays;
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
 * @Filename     : DashBoardController.java
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
		String[] onSsesWList;    // sses 사용 전력 배열
		String[] offSsesWList;    // sses 미사용 전력 배열
		String[] co2List;    // sses 미사용 전력 배열
		
		int[] category = new int[24];    //카테고리명
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		
		list = this.dashboardService.getEnergyList();
				
		/*onSsesWList = getWattData(list, "on");
		offSsesWList = getWattData(list, "off");*/
		onSsesWList = getWattData(list, "on");
		offSsesWList = getWattData(list, "off");
		co2List = getWattData(list, "co2");
		
		
		for(int i=0;i<category.length;i++){
			category[i] = i;
		}
		
		joStat.put("onData", onSsesWList);
		joStat.put("offData", offSsesWList);
		joStat.put("co2Data", co2List);
		joStat.put("category", category);
		
		return joStat;
	}
	
	/**
	 * 등록 장비 현황
	 */
	@RequestMapping(value = "/admin/dashboard/equip.json", method = RequestMethod.GET)
	public JSONObject equip() {
		JSONObject joStat =  new JSONObject();
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		list = this.dashboardService.getEnergyByEquipment();
		
		joStat.put("equip", list);
		
		return joStat;
	}
	
	
	
	/**
	 * 에너지 계산
	 */
	
	/*public String[] getWattData(List<Map<String, Object>> list, String type){
		String[] dualWList = new String[24];
		Arrays.fill(dualWList, "");		

		for(int i=0; i < list.size(); i++){
			double dualW;
			
			if(type.equals("on")){
				dualW = Double.parseDouble(String.valueOf(list.get(i).get("sumTotOnWatt")))/3600.0/1000.0;  // 총 전력량
			}
			else if(type.equals("off")){
				dualW = Double.parseDouble(String.valueOf(list.get(i).get("sumTotOffWatt")))/3600.0/1000.0;
			}
			else{
				dualW = (Double.parseDouble(String.valueOf(list.get(i).get("sumTotOnWatt")))/3600.0/1000.0)*0.4836;
			}
	
			
			int hour;			
			
			if (String.valueOf(list.get(i).get("hour")).substring(0, 1).equals("0")){
				hour = Integer.parseInt(((String)list.get(i).get("hour")).substring(1));
			}
			else{
				hour = Integer.parseInt((String) list.get(i).get("hour"));
			}
			
			
			
			dualW = Double.parseDouble(String.format("%.4f" , dualW));
			dualWList[hour]=Double.toString(dualW);			
		}		
		return dualWList;		
	}*/
	
	
	
	
	public String[] getWattData(List<Map<String, Object>> list, String type){
		String[] dualWList = new String[24];
		Arrays.fill(dualWList, "");

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
			
			int hour;			
			
			if (String.valueOf(list.get(i).get("hour")).substring(0, 1).equals("0")){
				hour = Integer.parseInt(((String)list.get(i).get("hour")).substring(1));
			}
			else{
				hour = Integer.parseInt((String) list.get(i).get("hour"));
			}
			
			for(int j=0;j<uptime.length;j++){
				
				int w = Integer.parseInt(watt[j]);
				int u = Integer.parseInt(uptime[j]);
				int s = Integer.parseInt(savingtime[j]);
				
				
				if(type.equals("on")){
					dualW += ((u-s)* w)/3600.0/1000.0;					// (기동시간 - 절약시간) * 소비전력
				}
				else if(type.equals("off")){
					dualW += (u*w)/3600.0/1000.0;						// 기동시간 * 소비전력
				}
				else{
					dualW += (((u-s)* w)/3600.0/1000.0)*0.4836;		// (기동시간 - 절약시간) * 소비전력 * 0.4836
				}
				
				dualW = Double.parseDouble(String.format("%.4f" , dualW));
				dualWList[hour]=Double.toString(dualW);
								
			}
			
		}		
		return dualWList;		
	}
	
	/*
	*//**
	 * 에너지 계산
	 *//*
	
	public String[] getWattData(List<Map<String, Object>> list, String type){
		
		String[] dualWList = new String[24];

		for(int i=0; i < list.size(); i++){			
			double dualW = 0;   // 총 전력량
			String[] watt;      // watt 배열
			String[] event_type;     // event_type 배열
			
			// 값이 하나인 경우 	
			if(String.valueOf(list.get(i).get("wattList")).indexOf(";") < 0){
				watt = new String[1]; event_type = new String[1];
				watt[0] = String.valueOf(list.get(i).get("wattList"));
				event_type[0] = String.valueOf(list.get(i).get("eventList"));
			}
			else{
				watt = String.valueOf(list.get(i).get("wattList")).split(";");
				event_type = String.valueOf(list.get(i).get("eventList")).split(";");			
			}
			
			for(int j=0; j<watt.length;j++){
				
				double wattJ = Double.parseDouble(watt[j])/1000.0;
				
				// 0:전원 OFF,  1:전원 ON,  2:절약모드시작,  3:절약모드종료,  4:사용중
				
				if(event_type[j].equals("0")){
					dualW -= wattJ;
				}
				else if(event_type[j].equals("1")){
					dualW += wattJ;
				}
				else if(event_type[j].equals("2")){
					dualW -= wattJ-0.001;
				}
				else if(event_type[j].equals("3")){
					dualW += wattJ-0.001;
				}
			}
			
			int hour;			
			
			if (String.valueOf(list.get(i).get("hour")).substring(0, 1).equals("0")){
				hour = Integer.parseInt(((String)list.get(i).get("hour")).substring(1));
			}
			else{
				hour = Integer.parseInt((String) list.get(i).get("hour"));
			}
			
			if(type.equals("off")){
				dualW=dualW+150;
			}
			
			dualW = Double.parseDouble(String.format("%.4f" , dualW));
			
			dualWList[hour]=Double.toString(dualW);	
			
			System.out.println("============="+type+"=============");
			for(int x=0;i<dualWList.length;i++){
				System.out.println("dualWList = "+dualWList[x]);
			}
			
			
		}
		return dualWList;
		
	}
	  */
}
