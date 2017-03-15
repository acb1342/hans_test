package com.mobilepark.doit5.cms.board.controller.cust;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

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
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.board.model.cust.BoadFaqCust;
import com.mobilepark.doit5.board.service.cust.BoadFaqCustService;
import com.mobilepark.doit5.cms.SessionAttrName;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller.cust
 * @Filename     : FaqCustController.java
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
public class FaqCustController {

	@Autowired
	private BoadFaqCustService boadFaqCustService;

	
	@RequestMapping("/board/faqCust/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "category", required = false) String category) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList boardList = null;
		BoadFaqCust boadFaq = new BoadFaqCust();
		
		// 카테고리별
		if( (StringUtils.isNotEmpty(category)) && !StringUtils.equals(category, "0") ) {
			boadFaq.setCategory(category);
		}
		
		// 사용자일때 공개글만
		if (user.getAdminGroup().getId() != 1) boadFaq.setDisplayYn("Y");
		
		// 검색
		if(StringUtils.isNotEmpty(searchValue)) boadFaq.setQuestion(searchValue);
		
		List<BoadFaqCust> list = this.boadFaqCustService.search(boadFaq, pageNum, rowPerPage, "fstRgDt", "desc");
		boardList = new PaginatedListImpl(list, pageNum, this.boadFaqCustService.searchCount(boadFaq), rowPerPage);
		
		ModelAndView mav = new ModelAndView("board/faqCust/search");
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		return mav;
	}
	
	
	@RequestMapping(value = "/board/faqCust/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("board/faqCust/create");
				
		mav.addObject("date", new Date());
		mav.addObject("boadFaq", new BoadFaqCust());
		
		return mav;
	}

	
	@RequestMapping(value = "/board/faqCust/create.htm", method = RequestMethod.POST)
	public ModelAndView create(BoadFaqCust boadFaqCust,HttpSession session,SessionStatus sessionStatus,
									@RequestParam(value = "category", required = false) String category,
									@RequestParam(value = "displayYn", required = false) String displayYn) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		boadFaqCust.setCategory(category);
		boadFaqCust.setDisplayYn(displayYn);
		boadFaqCust.setFstRgUsid(user.getId());
		boadFaqCust.setFstRgDt(new Date());
		boadFaqCust.setLstChUsid(user.getId());
		boadFaqCust.setLstChDt(new Date());
		
		this.boadFaqCustService.create(boadFaqCust);

		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/faqCust/search.htm");
	}
	
	
	@RequestMapping("/board/faqCust/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String snId) {
		
		int deleteCount = 0;
		String[] sequences = snId.split(";");
		
		for (String id : sequences) {
			if(Long.parseLong(id) > 0) {
				BoadFaqCust boadFaq = this.boadFaqCustService.get(Long.parseLong(id));
				if(boadFaq != null) {
					deleteCount = this.boadFaqCustService.delete(Long.parseLong(id));
				}
			}
		}

		return (deleteCount > 0);
	}

	
	@RequestMapping("/board/faqCust/detail.htm")
	public ModelAndView detail(
			HttpSession session,
			@RequestParam(value = "id", required = true) Long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/faqCust/detail");
		BoadFaqCust boadFaq = this.boadFaqCustService.get(snId);
			
		mav.addObject("boadFaq", boadFaq);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/board/faqCust/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(	@RequestParam("id") Long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/faqCust/update");

		BoadFaqCust boadFaq = this.boadFaqCustService.get(snId);
		mav.addObject("boadFaq", boadFaq);
		
		return mav;
	}

	
	@RequestMapping(value = "/board/faqCust/update.htm", method = RequestMethod.POST)
	public ModelAndView update(BoadFaqCust otherFaqCust, SessionStatus sessionStatus, HttpSession session,
									@RequestParam(value = "id", required = true) Long snId,
									@RequestParam(value = "displayYn", required = false) String displayYn,
									@RequestParam(value = "category", required = false) String category) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		BoadFaqCust boadFaq = this.boadFaqCustService.get(snId);
		boadFaq.setDisplayYn(displayYn);
		boadFaq.setCategory(category);
		boadFaq.setQuestion(otherFaqCust.getQuestion());
		boadFaq.setAnswer(otherFaqCust.getAnswer());
		boadFaq.setLstChDt(new Date());
		boadFaq.setLstChUsid(user.getId());
		
		this.boadFaqCustService.update(boadFaq);
	
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/faqCust/search.htm");

		return mav;
	}
	
}
