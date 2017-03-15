package com.mobilepark.doit5.cms.board.controller.oper;

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
import com.mobilepark.doit5.board.model.oper.BoadFaqAdmin;
import com.mobilepark.doit5.board.service.oper.BoadFaqAdminService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller.oper
 * @Filename     : NoticeAdminController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2017 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2017. 01. 07.      최초 버전
 * =================================================================================*/

@Controller
public class FaqAdminController {

	@Autowired
	private BoadFaqAdminService boadFaqAdminService;

	@RequestMapping("/board/faqAdmin/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "category", required = false) String category) {
		
		TraceLog.debug("[page:%s] [searchValue:%s] [category:%s]", page, searchValue, category);

		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList boardList = null;
		BoadFaqAdmin boadFaq = new BoadFaqAdmin();
		
		// 카테고리별
		if ((StringUtils.isNotEmpty(category)) && !StringUtils.equals(category, "0")) boadFaq.setCategory(category);
		
		// 사용자일때 공개글만
		if (user.getAdminGroup().getId() != 1) boadFaq.setDisplayYn("Y");
		
		// 검색
		if(StringUtils.isNotEmpty(searchValue)) boadFaq.setQuestion(searchValue);
		
		List<BoadFaqAdmin> list = this.boadFaqAdminService.search(boadFaq, pageNum, rowPerPage, "fstRgDt", "desc");
		boardList = new PaginatedListImpl(list, pageNum, this.boadFaqAdminService.searchCount(boadFaq), rowPerPage);
		
		ModelAndView mav = new ModelAndView("board/faqAdmin/search");
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		return mav;
	}
	
	
	@RequestMapping(value = "/board/faqAdmin/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("board/faqAdmin/create");
				
		mav.addObject("date", new Date());
		mav.addObject("boadFaq", new BoadFaqAdmin());
		
		return mav;
	}

	@RequestMapping(value = "/board/faqAdmin/create.htm", method = RequestMethod.POST)
	public ModelAndView create(
			BoadFaqAdmin boadFaqAdmin,HttpSession session,SessionStatus sessionStatus,
			@RequestParam(value = "category", required = false) String category,
			@RequestParam(value = "displayYn", required = false) String displayYn) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		boadFaqAdmin.setCategory(category);
		boadFaqAdmin.setDisplayYn(displayYn);
		boadFaqAdmin.setFstRgUsid(user.getId());
		boadFaqAdmin.setFstRgDt(new Date());
		boadFaqAdmin.setLstChUsid(user.getId());
		boadFaqAdmin.setLstChDt(new Date());
		boadFaqAdmin.setReadCnt(0);
		
		this.boadFaqAdminService.create(boadFaqAdmin);

		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/faqAdmin/search.htm");
	}
	
	
	@RequestMapping("/board/faqAdmin/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String snId) {
		
		int deleteCount = 0;
		String[] sequences = snId.split(";");
		
		for (String id : sequences) {
			if(Long.parseLong(id) > 0) {
				BoadFaqAdmin boadFaq = this.boadFaqAdminService.get(Long.parseLong(id));
				if(boadFaq != null) {
					deleteCount = this.boadFaqAdminService.delete(Long.parseLong(id));
				}
			}
		}

		return (deleteCount > 0);
	}

	
	@RequestMapping("/board/faqAdmin/detail.htm")
	public ModelAndView detail(
			HttpSession session,
			@RequestParam(value = "id", required = true) Long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/faqAdmin/detail");
		BoadFaqAdmin boadFaq = this.boadFaqAdminService.get(snId);
			
		mav.addObject("boadFaq", boadFaq);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/board/faqAdmin/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(	@RequestParam("id") Long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/faqAdmin/update");

		BoadFaqAdmin boadFaq = this.boadFaqAdminService.get(snId);
		mav.addObject("boadFaq", boadFaq);
		
		return mav;
	}

	
	@RequestMapping(value = "/board/faqAdmin/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			BoadFaqAdmin otherFaqAdmin, SessionStatus sessionStatus, HttpSession session,
			@RequestParam(value = "id", required = true) Long snId,
			@RequestParam(value = "displayYn", required = false) String displayYn,
			@RequestParam(value = "category", required = false) String category) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		BoadFaqAdmin boadFaq = this.boadFaqAdminService.get(snId);
		boadFaq.setDisplayYn(displayYn);
		boadFaq.setCategory(category);
		boadFaq.setQuestion(otherFaqAdmin.getQuestion());
		boadFaq.setAnswer(otherFaqAdmin.getAnswer());
		boadFaq.setLstChDt(new Date());
		boadFaq.setLstChUsid(user.getId());
		
		this.boadFaqAdminService.update(boadFaq);
	
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/faqAdmin/search.htm");

		return mav;
	}
}
