package com.mobilepark.doit5.cms.board.controller.cust;

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
import com.mobilepark.doit5.board.model.cust.BoadNoticeCust;
import com.mobilepark.doit5.board.service.cust.BoadNoticeCustService;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller.cust
 * @Filename     : NoticeCustController.java
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
 * =================================================================================*/
 

@Controller
public class NoticeCustController {
	
	@Autowired
	private BoadNoticeCustService noticeCustService;

	@RequestMapping("/board/noticeCust/search.htm")
	public ModelAndView search(HttpSession session,
								@RequestParam(value = "page", required = false) String page,
								@RequestParam(value = "searchValue", required = false) String searchValue,
								@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		PaginatedList boardList = null;
		BoadNoticeCust boadNotice = new BoadNoticeCust();
		
		// 제목 검색
		if ((StringUtils.isNotEmpty(searchValue))) boadNotice.setTitle(searchValue);
		
		// 사용자일때 공개글만
		if (user.getAdminGroup().getId() != 1) boadNotice.setDisplayYn("Y");
		
		List<BoadNoticeCust> list = this.noticeCustService.search(boadNotice, pageNum, rowPerPage, "snId", "desc");
																
		boardList = new PaginatedListImpl(list, pageNum, this.noticeCustService.searchCount(boadNotice), rowPerPage);
		
		ModelAndView mav = new ModelAndView("board/noticeCust/search");
		
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		return mav;
	}
	
	// 등록 폼
	@RequestMapping(value = "/board/noticeCust/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("board/noticeCust/create");
		
		mav.addObject("date", new Date());
		mav.addObject("boardNotice", new BoadNoticeCust());
		
		return mav;
	}

	// 등록
	@RequestMapping(value = "/board/noticeCust/create.htm", method = RequestMethod.POST)
	public ModelAndView create(BoadNoticeCust boadNotice, HttpSession session,
									@RequestParam(value = "displayYn", required = false) String displayYn) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		boadNotice.setDisplayYn(displayYn);
		boadNotice.setFstRgUsid(user.getId());
		boadNotice.setFstRgDt(new Date());
		boadNotice.setLstChUsid(user.getId());
		boadNotice.setLstChDt(new Date());
		
		this.noticeCustService.create(boadNotice);
		
		return new ModelAndView("redirect:/board/noticeCust/search.htm");
	}
	
	// 상세
	@RequestMapping("/board/noticeCust/detail.htm")
	public ModelAndView detail(HttpSession session,
									@RequestParam(value = "id", required = true) Long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/noticeCust/detail");
		BoadNoticeCust boadNotice = this.noticeCustService.get(snId);
			
		mav.addObject("boadNotice", boadNotice);
		
		return mav;
	}
	
	// 삭제
	@RequestMapping("/board/noticeCust/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String snId) {
		
		String[] sequences = snId.split(";");
		int deleteCount = 0;
		for (String id : sequences) {
			BoadNoticeCust boadNotice = this.noticeCustService.get(Long.parseLong(id));
			if (boadNotice != null) {
				deleteCount = this.noticeCustService.delete(Long.parseLong(id));
			}
		}

		return (deleteCount > 0);
	}

	// 수정 폼
	@RequestMapping(value = "/board/noticeCust/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(	@RequestParam("id") long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/noticeCust/update");

		BoadNoticeCust boadNotice = this.noticeCustService.get(snId);
		mav.addObject("boadNotice", boadNotice);
		
		return mav;
	}

	// 수정
	@RequestMapping(value = "/board/noticeCust/update.htm", method = RequestMethod.POST)
	public ModelAndView update(BoadNoticeCust otherBoadNotice, HttpSession session, SessionStatus sessionStatus,
									@RequestParam(value = "id", required = false) Long snId,
									@RequestParam(value = "displayYn", required = false) String displayYn) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		BoadNoticeCust boadNotice = this.noticeCustService.get(snId);
		boadNotice.setTitle(otherBoadNotice.getTitle());
		boadNotice.setBody(otherBoadNotice.getBody());
		boadNotice.setDisplayYn(displayYn);
		boadNotice.setLstChUsid(user.getId());
		boadNotice.setLstChDt(new Date());
		
		this.noticeCustService.update(boadNotice);
		
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/noticeCust/search.htm");

		return mav;
	}
	
}