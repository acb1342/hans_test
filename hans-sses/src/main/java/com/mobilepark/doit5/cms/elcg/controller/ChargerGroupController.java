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
import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.elcg.model.Bd;
import com.mobilepark.doit5.elcg.model.BdGroup;
import com.mobilepark.doit5.elcg.model.ChargerGroup;
import com.mobilepark.doit5.elcg.service.BdGroupService;
import com.mobilepark.doit5.elcg.service.BdService;
import com.mobilepark.doit5.elcg.service.ChargerGroupService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.model.Pageable;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.elcg.controller
 * @Filename     : ChargerGroupController.java
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
public class ChargerGroupController {
	
	@Autowired
	private BdGroupService bdGroupService;
	
	@Autowired
	private BdService bdService;
	
	@Autowired
	private ChargerGroupService chargerGroupService;
	
	
	@RequestMapping("/elcg/chargerGroup/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "status", required = false) String type,
			@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
			@RequestParam(value = "bdSelect", required = false) Long bdId,
			@RequestParam(value = "chargerGroupSelect", required = false) Long chargerGroupId) {
		TraceLog.debug("[BD : %s] [CG : %s]", bdId , chargerGroupId);
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView("elcg/chargerGroup/search");
		mav.addObject("type", type);
		
		// 디폴트 페이지
		if(StringUtils.isEmpty(type)) {
			mav.addObject("type", "default");
			return mav;
		}
		
		int count = 0;
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList chargerGroupList = null;
		ChargerGroup chargerGroup = new ChargerGroup();

		// 설치자
		if(adminGroup.getId() == 2) {
			chargerGroup.setFstRgUsid(admin.getId());
		}
		
		// 건물주
		if(adminGroup.getId() == 3) {
			Bd bd = new Bd();
			bd.setAdminId(admin.getId());
			chargerGroup.setBd(bd);
		}
		
		if(type.equals("CG")) {
			chargerGroup.setBdGroupId(bdGroupId);
			chargerGroup.setBdId(bdId);
			chargerGroup.setChargerGroupId(chargerGroupId);
			
			// 화면에서 상세/동명 유지
			if (bdGroupId != null && bdGroupId > 0) {
				BdGroup selGroupList = this.bdGroupService.get(bdGroupId);
				Bd selBd = new Bd();
				selBd.setBdGroup(selGroupList);
				
				List<Bd> selBdList = null;
				if (adminGroup.getId() == 2) selBdList = bdService.getInstallerBdList(bdGroupId.toString(), admin.getId());
				else selBdList = this.bdService.search(selBd);
				
				mav.addObject("selBdList", selBdList);
				mav.addObject("selBdGroupId", bdGroupId);
		
				ChargerGroup selCg = new ChargerGroup();
				selCg.setBdGroupId(bdGroupId);
				selCg.setBdId(bdId);
				List<ChargerGroup> selCgList = this.chargerGroupService.search(selCg);
				mav.addObject("selCgList", selCgList);
			}
		}
		
		count = this.chargerGroupService.searchCount(chargerGroup);
		List<ChargerGroup> list = this.chargerGroupService.search(chargerGroup, pageNum, rowPerPage, "fstRgDt", "desc");
		
		chargerGroupList = new PaginatedListImpl(list, pageNum, count, rowPerPage);
		
		mav.addObject("chargerGroupList", chargerGroupList);
		mav.addObject("rownum", chargerGroupList.getFullListSize());
		mav.addObject("page", pageNum); 
		
		return mav;
	}

	@RequestMapping(value = "/elcg/chargerGroup/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		
		ModelAndView mav = new ModelAndView("elcg/chargerGroup/create");
		
		mav.addObject("chargerGroup", new ChargerGroup());
		
		return mav;
	}

	@RequestMapping(value = "/elcg/chargerGroup/create.htm", method = RequestMethod.POST)
	public ModelAndView create(ChargerGroup chargerGroup, SessionStatus sessionStatus, HttpSession session,
								@RequestParam(value = "bdGroupId", required = false) Long bdGroupId,
								@RequestParam(value = "bdSelect", required = false) Long bdId) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		BdGroup bdGroup = this.bdGroupService.get(bdGroupId);
		Bd bd = this.bdService.get(bdId);
		
		chargerGroup.setBdGroupId(bdGroup.getBdGroupId());
		chargerGroup.setBd(bd);
		chargerGroup.setFstRgUsid(user.getId());
		chargerGroup.setFstRgDt(new Date());
	
		this.chargerGroupService.create(chargerGroup);
	
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/elcg/chargerGroup/search.htm");
	}
	
	
	@RequestMapping("/elcg/chargerGroup/detail.htm")
	public ModelAndView detail(
			HttpSession session,
			@RequestParam(value = "seq", required = true) Long chargerGroupId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/chargerGroup/detail");
		ChargerGroup chargerGroup = this.chargerGroupService.get(chargerGroupId);
			
		mav.addObject("chargerCnt", chargerGroup.getChargerSize());
		mav.addObject("chargerGroup", chargerGroup);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/elcg/chargerGroup/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("seq") Long chargerGroupId) throws Exception {
		
		ModelAndView mav = new ModelAndView("elcg/chargerGroup/update");

		ChargerGroup chargerGroup = this.chargerGroupService.get(chargerGroupId);
		mav.addObject("chargerGroup", chargerGroup);
		mav.addObject("chargerCnt", chargerGroup.getChargerSize());
		
		return mav;
	}

	
	@RequestMapping(value = "/elcg/chargerGroup/update.htm", method = RequestMethod.POST)
	public ModelAndView update(SessionStatus sessionStatus, HttpSession session, ChargerGroup otherChargerGroup,
								@RequestParam("seq") String chargerGroupId) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		ChargerGroup chargerGroup = this.chargerGroupService.get(Long.parseLong(chargerGroupId));
		chargerGroup.setLstChUsid(user.getId());
		chargerGroup.setLstChDt(new Date());
		
		chargerGroup.setName(otherChargerGroup.getName());
		chargerGroup.setCapacity(otherChargerGroup.getCapacity());
		chargerGroup.setDescription(otherChargerGroup.getDescription());
		
		this.chargerGroupService.update(chargerGroup);
		
		sessionStatus.setComplete();

		return new ModelAndView("redirect:/elcg/chargerGroup/search.htm");
	}
	
	
	@RequestMapping("/elcg/chargerGroup/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String chargerGroupId) {
		
		String[] sequences = chargerGroupId.split(";");
		int deleteCount = 0;
		for (String id : sequences) {
			ChargerGroup chargerGroup = this.chargerGroupService.get(Long.parseLong(id));
			if (chargerGroup != null) {
				deleteCount = this.chargerGroupService.delete(Long.parseLong(id));
			}
		}

		return (deleteCount > 0);
	}

}
