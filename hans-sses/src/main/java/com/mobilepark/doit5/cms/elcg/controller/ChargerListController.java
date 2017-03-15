package com.mobilepark.doit5.cms.elcg.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.elcg.model.Charger;
import com.mobilepark.doit5.elcg.model.ChargerList;
import com.mobilepark.doit5.elcg.model.ChargerModel;
import com.mobilepark.doit5.elcg.service.ChargerListService;
import com.mobilepark.doit5.elcg.service.ChargerModelService;
import com.mobilepark.doit5.elcg.service.ChargerService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.elcg.controller
 * @Filename     : ChargerListController.java
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
public class ChargerListController {

	@Autowired
	private ChargerListService chargerListService;
	
	@Autowired
	private ChargerService chargerService;
	
	@Autowired
	private ChargerModelService chargerModelService;
	
	@RequestMapping("/elcg/chargerList/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("elcg/chargerList/search");
		mav.addObject("type", searchType);
		
		PaginatedList chargerLists = null;
		ChargerList chargerList = new ChargerList();
		
		if(StringUtils.isEmpty(searchType)) {
			// 402101: 미설치 , 402102: 설치
			chargerList.setStatus("402102");
			
			int total = this.chargerListService.getTotalCount();
			int installed = this.chargerListService.searchCount(chargerList);
			int noInstalled = total - installed;
			
			mav.addObject("total", total);
			mav.addObject("installed", installed);
			mav.addObject("noInstalled", noInstalled);
			mav.addObject("type", "default");
			return mav;
		}
			
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		if (StringUtils.isNotEmpty(searchType) && !StringUtils.equals(searchType, "0")) chargerList.setStatus(searchType);
		
		if(StringUtils.isNotEmpty(searchValue)) {
			chargerList.setSerialNo(searchValue);
		}
		
		List<ChargerList> list = this.chargerListService.search(chargerList, pageNum, rowPerPage, "chargerId", "desc");
		chargerLists = new PaginatedListImpl(list, pageNum, this.chargerListService.searchCount(chargerList), rowPerPage);
		
		TraceLog.debug("chargerGroupList ===> " + chargerLists.getFullListSize());
		
		mav.addObject("chargerLists", chargerLists);
		mav.addObject("rownum", chargerLists.getFullListSize());
		mav.addObject("page", pageNum); 
		return mav;
	}
	

	@RequestMapping("/elcg/chargerList/detail.htm")
	public ModelAndView detail(@RequestParam(value = "seq", required = true) String chargerId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/chargerList/detail");
		
		ChargerList chargerList = this.chargerListService.get(chargerId);
		Charger charger = chargerService.get(chargerList.getChargerId());
		
		mav.addObject("chargerList", chargerList);
		mav.addObject("charger", charger);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/elcg/chargerList/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("seq") String chargerId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/chargerList/update");

		ChargerList chargerList = this.chargerListService.get(chargerId);
		Charger charger = chargerService.get(chargerList.getChargerId());
		
		mav.addObject("chargerList", chargerList);
		mav.addObject("charger", charger);
		
		return mav;
	}

	
	@RequestMapping(value = "/elcg/chargerList/update.htm", method = RequestMethod.POST)
	public ModelAndView update(ChargerList otherChargerList, SessionStatus sessionStatus, HttpSession session,
								@RequestParam(value = "searchType", required = false) String searchType,
								@RequestParam(value = "model.model", required = false) String model,
								@RequestParam("seq") String chargerId) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
	
		// 상태 업데이트
		ChargerList chargerList = this.chargerListService.get(chargerId);
		chargerList.setLstChUsid(user.getId());
		chargerList.setLstChDt(new Date());
		chargerList.setStatus(searchType);
		this.chargerListService.update(chargerList);
		
		if(searchType.equals("402101")) {
			this.chargerService.delete(chargerId);
		}
		
		// 모델명 업데이트
		ChargerModel chargerModel = this.chargerModelService.get(chargerList.getModel().getModelId());
		chargerModel.setModel(model);
		this.chargerModelService.update(chargerModel);
		
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/elcg/chargerList/search.htm");
	}
	
	
	@RequestMapping("/elcg/chargerList/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String chargerId) {
		
		String[] sequences = chargerId.split(";");
		int deleteCount = 0;
		for (String id : sequences) {
			ChargerList chargerList = this.chargerListService.get(id);
			if (chargerList != null) {
				deleteCount = this.chargerListService.delete(id);
			}
		}

		return (deleteCount > 0);
	}

}
