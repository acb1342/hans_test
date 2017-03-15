package com.mobilepark.doit5.cms.board.controller;

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
import com.mobilepark.doit5.board.model.BoadNotice;
import com.mobilepark.doit5.board.service.BoadNoticeService;
import com.mobilepark.doit5.board.service.NoticeExcelView;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller
 * @Filename     : BoadNoticeController.java
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
public class BoadNoticeController {
	
	@Autowired
	private BoadNoticeService boadNoticeService;

	
	@RequestMapping("/board/notice/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView("board/notice/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		
		PaginatedList boardList = null;
		BoadNotice boadNotice = new BoadNotice();
		
		// 설치자
		if (adminGroup.getId() == 2) boadNotice.setInstaller_yn("Y");
		// 건물주
		if (adminGroup.getId() == 3) boadNotice.setOwner_yn("Y");
		
		// 검색
		if ((StringUtils.isNotEmpty(searchValue) || StringUtils.isNotEmpty(searchSelect))) {
				boadNotice.setTitle(searchValue);
		}
		 
		List<BoadNotice> list = this.boadNoticeService.search(boadNotice, pageNum, rowPerPage, "sn_id", "desc",
																changeFormat(fromDate), changeFormat(toDate));
		
		boardList = new PaginatedListImpl(list, pageNum, this.boadNoticeService.searchCount(boadNotice), rowPerPage);
		
		TraceLog.debug("boardList ===> " + boardList.getFullListSize());
		
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		return mav;
	}
	
	
	@RequestMapping(value = "/board/notice/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("board/notice/create");
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		mav.addObject("date", new Date());
		mav.addObject("boardNotice", new BoadNotice());
		mav.addObject("user_id", user.getId());
		
		return mav;
	}

	
	@RequestMapping(value = "/board/notice/create.htm", method = RequestMethod.POST)
	public ModelAndView create(BoadNotice boadNotice, HttpSession session, SessionStatus sessionStatus,
									@RequestParam(value = "display_yn", required = false) String display_yn,
									@RequestParam(value = "displayWho", required = false) String displayWho) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		// TB_MGMT_ADMIN_GROUP --- ADMIN_GROUP_ID
		boadNotice.setYN(displayWho);
		boadNotice.setDisplay_yn(display_yn);
		boadNotice.setFstRgUsid(user.getId());
		boadNotice.setFstRgDt(new Date());
		
		this.boadNoticeService.create(boadNotice);

		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/notice/search.htm");
	}
	
	
	@RequestMapping("/board/notice/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String sn_id) {
		
		String[] sequences = sn_id.split(";");
		int deleteCount = 0;
		for (String id : sequences) {
			BoadNotice boadNotice = this.boadNoticeService.get(Long.parseLong(id));
			if (boadNotice != null) {
				deleteCount = this.boadNoticeService.delete(Long.parseLong(id));
			}
		}

		return (deleteCount > 0);
	}

	
	@RequestMapping("/board/notice/detail.htm")
	public ModelAndView detail(
			HttpSession session,
			@RequestParam(value = "seq", required = true) Long sn_id) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/notice/detail");
		BoadNotice boadNotice = this.boadNoticeService.get(sn_id);
		
		//조회수 증가
		boadNotice.setRead_cnt(boadNotice.getRead_cnt()+1);
		boadNoticeService.update(boadNotice);
			
		mav.addObject("boadNotice", boadNotice);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/board/notice/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(	@RequestParam("seq") long sn_id) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/notice/update");

		BoadNotice boadNotice = this.boadNoticeService.get(sn_id);
		mav.addObject("boadNotice", boadNotice);
		
		return mav;
	}

	
	@RequestMapping(value = "/board/notice/update.htm", method = RequestMethod.POST)
	public ModelAndView update(BoadNotice otherBoadNotice, HttpSession session, SessionStatus sessionStatus,
									@RequestParam(value = "seq", required = false) long sn_id,
									@RequestParam(value = "displayWho", required = false) String displayWho) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		BoadNotice boadNotice = this.boadNoticeService.get(sn_id);
		boadNotice.setYN(displayWho);
		boadNotice.setTitle(otherBoadNotice.getTitle());
		boadNotice.setBody(otherBoadNotice.getBody());
		boadNotice.setDisplay_yn(otherBoadNotice.getDisplay_yn());
		boadNotice.setLstChUsid(user.getId());
		boadNotice.setLstChDt(new Date());
		
		this.boadNoticeService.update(boadNotice);
		
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/notice/search.htm");

		return mav;
	}
	
	@RequestMapping("/board/notice/excelDown.json")
	public ModelAndView excelDown(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		TraceLog.debug("[page:%s] [searchValue:%s] [searchSelect:%s] [fromDate:%s] [toDate:%s]",
				page, searchValue, searchSelect, fromDate, toDate);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView();
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		BoadNotice boadNotice = new BoadNotice();
		
		// 설치자
		if (adminGroup.getId() == 2) boadNotice.setInstaller_yn("Y");
		// 건물주
		if (adminGroup.getId() == 3) boadNotice.setOwner_yn("Y");
		
		// 검색
		if ((StringUtils.isNotEmpty(searchValue) || StringUtils.isNotEmpty(searchSelect))) {
				boadNotice.setTitle(searchValue);
		}
		 
		List<BoadNotice> list = this.boadNoticeService.search(boadNotice, pageNum, rowPerPage, "sn_id", "desc",
																changeFormat(fromDate), changeFormat(toDate));
		
		mav.addObject("list",list);
		mav.setView(new NoticeExcelView());
		
		return mav;
	}
		
	public String changeFormat(String date) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			
			return date.trim();
		}
		return null;
		
	}
}
