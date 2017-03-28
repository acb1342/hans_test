package com.mobilepark.doit5.cms.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.service.EnergyService;
import com.mobilepark.doit5.cms.SessionAttrName;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.HexUtil;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : OperatorController.java
 * @Description  : 운영자 계정관리 - 권한 코드 1
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
@SessionAttributes("admin")
public class EnergyController {
	@Autowired
	private EnergyService energyService;

	/**
	 * 사용자 생성 폼
	 */
	@RequestMapping(value = "/admin/energy/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("energy/create");
		mav.addObject("admin", new Admin());

		return mav;
	}

	/**
	 * 사용자 생성
	 */
	@RequestMapping(value = "/admin/energy/create.json", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> params,Admin admin, HttpSession session,
			SessionStatus sessionStatus) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {

		params.put("regDate", new Date());
				
		this.energyService.EnergyCreate(params);
		
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/admin/energy/detail.htm?id=" + params.get("id"));
	}
/*
	*//**
	 * 사용자 삭제
	 *//*
	@RequestMapping("/admin/energy/delete.json")
	@ResponseBody
	public boolean delete(@RequestParam("id") String id) {
		int deleteCount = 0;
		Map<String, Object> equipDetail = this.energyService.getEnergyDetail(id);
		if (equipDetail != null) {
			
			deleteCount = this.energyService.EquipDelete(id);
		
		} else {
			TraceLog.info("fail to delete. does not exist id [id:%s]", id);
		}
		return (deleteCount > 0);
	}

	*//**
	 * 사용자 상세
	 *//*
	@RequestMapping("/admin/energy/detail.htm")
	public ModelAndView detail(@RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("energy/detail");

		Map<String, Object> equipDetail = this.energyService.getEnergyDetail(id);
		
		if (equipDetail != null) {
			mav.addObject("admin", equipDetail);
		} 

		return mav;
	}
	
	*/

	/**
	 * 사용자 검색
	 */
		
	@RequestMapping("/admin/energy/search.htm")
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
		System.out.println("countAll = " + countAll);
		
		List<Map<String, String>> list = this.energyService.getEnergyList(param);
		
		mav.addObject("energyList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	/*
	
	*//**
	 * 사용자 수정 폼
	 *//*
	@RequestMapping(value = "/admin/energy/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam Map<String, Object> params, @RequestParam("id") String id) throws Exception {
		ModelAndView mav = new ModelAndView("energy/update");
		
		Map<String, Object> equipDetail = this.energyService.getEnergyDetail(id);
		
		mav.addObject("admin", equipDetail);

		return mav;
	}

	*//**
	 * 사용자 수정
	 *//*
	@RequestMapping(value = "/admin/energy/update.json", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam Map<String, Object> params, @RequestParam(value = "password", required = false) String password,
			SessionStatus sessionStatus) {
		
		params.put("LstChDt", new Date());
		
		this.energyService.EnergyUpdate(params);

		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/admin/energy/detail.htm?id=" + params.get("id"));
	
		return mav;
	}
   

	*//**
	 * 사용자 존재여부 확인
	 *//*
	@RequestMapping(value = "/admin/energy/checkid.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkUserId(@RequestParam(value = "id", required = false) String id) {
		
		Map<String, Object> equipDetail = this.energyService.getEnergyDetail(id);

		return (equipDetail == null);
	}
	*/
}
