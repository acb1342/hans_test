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
import com.mobilepark.doit5.board.model.oper.BoadNoticeAdmin;
import com.mobilepark.doit5.board.service.oper.BoadNoticeAdminService;
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
public class NoticeAdminController {

	@Autowired
	private BoadNoticeAdminService boadNoticeAdminService;

	@RequestMapping("/board/noticeAdmin/search.htm")
	public ModelAndView search(
			HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {
		
		TraceLog.debug("[page:%s] [searchValue:%s] [searchSelect:%s]", page, searchValue, searchSelect);

		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		PaginatedList boardList = null;
		BoadNoticeAdmin boadNotice = new BoadNoticeAdmin();
		
		// 제목 검색
		if ((StringUtils.isNotEmpty(searchValue))) boadNotice.setTitle(searchValue);
		
		// 날짜검색
		if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
			boadNotice.setFromDate(changeFormat(fromDate, 8));
			boadNotice.setToDate(changeFormat(toDate, 8));
		}
		
		List<BoadNoticeAdmin> list = this.boadNoticeAdminService.search(boadNotice, pageNum, rowPerPage, "fstRgDt", "desc");
																
		boardList = new PaginatedListImpl(list, pageNum, this.boadNoticeAdminService.searchCount(boadNotice), rowPerPage);
		
		ModelAndView mav = new ModelAndView("board/noticeAdmin/search");
		
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		return mav;
	}
	
	// 등록 폼
	@RequestMapping(value = "/board/noticeAdmin/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("board/noticeAdmin/create");
		
		mav.addObject("date", new Date());
		mav.addObject("boardNotice", new BoadNoticeAdmin());
		
		return mav;
	}

	// 등록
	@RequestMapping(value = "/board/noticeAdmin/create.htm", method = RequestMethod.POST)
	public ModelAndView create(BoadNoticeAdmin boadNotice, HttpSession session,
				@RequestParam(value = "displayYn", required = false) String displayYn) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		boadNotice.setDisplayYn(displayYn);
		boadNotice.setFstRgUsid(user.getId());
		boadNotice.setFstRgDt(new Date());
		boadNotice.setLstChUsid(user.getId());
		boadNotice.setLstChDt(new Date());
		boadNotice.setReadCnt(0);
		
		this.boadNoticeAdminService.create(boadNotice);
		
		return new ModelAndView("redirect:/board/noticeAdmin/search.htm");
	}
	
	// 상세
	@RequestMapping("/board/noticeAdmin/detail.htm")
	public ModelAndView detail(HttpSession session,
				@RequestParam(value = "id", required = true) Long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/noticeAdmin/detail");
		BoadNoticeAdmin boadNotice = this.boadNoticeAdminService.get(snId);
			
		mav.addObject("boadNotice", boadNotice);
		
		return mav;
	}
	
	// 삭제
	@RequestMapping("/board/noticeAdmin/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String snId) {
		
		String[] sequences = snId.split(";");
		int deleteCount = 0;
		for (String id : sequences) {
			BoadNoticeAdmin boadNotice = this.boadNoticeAdminService.get(Long.parseLong(id));
			if (boadNotice != null) {
				deleteCount = this.boadNoticeAdminService.delete(Long.parseLong(id));
			}
		}

		return (deleteCount > 0);
	}

	// 수정 폼
	@RequestMapping(value = "/board/noticeAdmin/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(	@RequestParam("id") long snId) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/noticeAdmin/update");

		BoadNoticeAdmin boadNotice = this.boadNoticeAdminService.get(snId);
		mav.addObject("boadNotice", boadNotice);
		
		return mav;
	}

	// 수정
	@RequestMapping(value = "/board/noticeAdmin/update.htm", method = RequestMethod.POST)
	public ModelAndView update(BoadNoticeAdmin otherBoadNotice, HttpSession session, SessionStatus sessionStatus,
				@RequestParam(value = "id", required = false) Long snId,
				@RequestParam(value = "displayYn", required = false) String displayYn) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		BoadNoticeAdmin boadNotice = this.boadNoticeAdminService.get(snId);
		boadNotice.setTitle(otherBoadNotice.getTitle());
		boadNotice.setBody(otherBoadNotice.getBody());
		boadNotice.setDisplayYn(displayYn);
		boadNotice.setLstChUsid(user.getId());
		boadNotice.setLstChDt(new Date());
		
		this.boadNoticeAdminService.update(boadNotice);
		
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/noticeAdmin/search.htm");

		return mav;
	}
	
	
	public String changeFormat(String date, int strNumber) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, strNumber);
			
			return date;
		}
		return null;
	}
}
