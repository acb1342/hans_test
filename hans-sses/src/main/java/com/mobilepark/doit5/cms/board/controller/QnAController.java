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
import com.mobilepark.doit5.board.model.BoadQna;
import com.mobilepark.doit5.board.service.BoadQnaService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller
 * @Filename     : BoardController.java
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
public class QnAController {

	@Autowired
	private BoadQnaService boadQnaService;
	
	@RequestMapping("/board/QnA/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchStatus", required = false) String searchStatus,
			@RequestParam(value = "fromDate", required = false) String fromDate,
			@RequestParam(value = "toDate", required = false) String toDate) {

		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView("board/QnA/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList boardList = null;

		BoadQna boadQna = new BoadQna();
		boadQna.setType("602101");
		
		// 설치자
		if (adminGroup.getId() == 2) boadQna.setInstaller_yn("Y");
		// 건물주
		if (adminGroup.getId() == 3) boadQna.setOwner_yn("Y");
		
		// 공개(Y) , 비공개(N) 검색
		if(StringUtils.isNotEmpty(searchStatus)) {
			if(searchStatus.equals("Y") || searchStatus.equals("N")) {
				boadQna.setOpen_yn(searchStatus);
			}
		}
		
		// 검색
		if(StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
			switch(searchType) {
				case "name"	: boadQna.setPen_name(searchValue);	break;
				case "title"	: boadQna.setTitle(searchValue);		break;
				case "all"		: 
					if(!searchValue.trim().equals("")) {
						List<BoadQna> list = this.boadQnaService.searchQnaAllKeys(boadQna, searchValue, pageNum, rowPerPage,
																							changeFormat(fromDate, 8), changeFormat(toDate, 8));
						boardList = new PaginatedListImpl(list, pageNum, list.size(), rowPerPage);
						mav.addObject("boardList", boardList);
						mav.addObject("rownum", boardList.getFullListSize());
						return mav;
					}							
			}
		}
		
		List<BoadQna> list = this.boadQnaService.searchQna(boadQna, pageNum, rowPerPage, "sn_id", "desc",
																	changeFormat(fromDate, 8), changeFormat(toDate, 8));
		
		boardList = new PaginatedListImpl(list, pageNum, this.boadQnaService.searchCount(boadQna), rowPerPage);
		
		TraceLog.debug("boardList ===> " + boardList.getFullListSize());
	
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	// 목록에서 삭제
	@RequestMapping("/board/QnA/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String sn_ids) {
		String[] sequences = sn_ids.split(";");
		int deleteCount = 0;
		for (String sn_id : sequences) {
			// 질문 삭제
			BoadQna boadQna = this.boadQnaService.get(Long.parseLong(sn_id));
			if (boadQna != null) {
				deleteCount = this.boadQnaService.delete(Long.parseLong(sn_id));
			}
			
			// 답변 삭제
			BoadQna answer = new BoadQna();
			answer.setQuestion_id(Long.parseLong(sn_id));
			try{
			answer = this.boadQnaService.search(answer).get(0);
			if (answer != null) this.boadQnaService.delete(answer);
			} catch(Exception e) {
				
			}
			
		}

		return (deleteCount > 0);
	}

	
	// 답글 삭제
	@RequestMapping("/board/QnA/deleteRepl.json")
	@ResponseBody
	public Boolean deleteRepl(@RequestParam("id") Long snId) {
		// 답글 삭제
		BoadQna reply = new BoadQna();
		reply.setQuestion_id(snId);
		
		int deleteCount = 0;
		try{
			BoadQna replyM = this.boadQnaService.search(reply).get(0);
			if (replyM != null) deleteCount = this.boadQnaService.delete(replyM);
			
		} catch(Exception e) {
			
		}
		
		// 질문 상태변경
		BoadQna question = this.boadQnaService.get(snId);
		question.setAnswer_yn("N");
		question.setAnswer_status("602201");
		this.boadQnaService.update(question);
		
		return (deleteCount > 0);
	}
	
	
	@RequestMapping("/board/QnA/detail.htm")
	public ModelAndView detail(HttpSession session,
								@RequestParam(value = "seq", required = true) Long sn_id,
								@RequestParam(value = "page", required = false) String page) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/QnA/detail");
		BoadQna boadQna = this.boadQnaService.get(sn_id);
		TraceLog.debug("writer Type : " + boadQna.getWriterType());
		mav.addObject("boadQna", boadQna);
		
		//********* 답변내용조회 *********//
		BoadQna replyM = new BoadQna();
		replyM.setQuestion_id(boadQna.getSn_id());
		List<BoadQna> list = this.boadQnaService.search(replyM);
		
		if(!list.isEmpty()) {
			mav.addObject("replyContent", list.get(0).getBody());
			mav.addObject("replyStatus", list.get(0).getAnswer_yn());
			mav.addObject("replyTime", list.get(0).getFstRgDt());	
		}
		
		//답변달기
		mav.addObject("date",new Date());
		mav.addObject("replyM", new BoadQna());
		
		mav.addObject("page", page);
		return mav;
	}
	
	// 질문 생성폼 - 설치자 / 건물주
	@RequestMapping(value = "/board/QnA/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		
		ModelAndView mav = new ModelAndView("board/QnA/create");
		
		mav.addObject("date", new Date());
		mav.addObject("boadQna", new BoadQna());
		
		return mav;
	}

	// 질문 생성 - 설치자 / 건물주
	@RequestMapping(value = "/board/QnA/create.htm", method = RequestMethod.POST)
	public ModelAndView create(BoadQna boadQna, HttpSession session, SessionStatus sessionStatus,
									@RequestParam(value = "status", required = false) String status) {
		
		TraceLog.debug("boadQna : " + boadQna.toString() + " / " + status);
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup group = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		int groupId = group.getId();
		
		boadQna.setDefault();
		if (groupId == 2) boadQna.setInstaller_yn("Y");
		if (groupId == 3) boadQna.setOwner_yn("Y");
		boadQna.setType("602101");
		boadQna.setOpen_yn(status);
		boadQna.setAnswer_yn("N");
		boadQna.setPen_name(user.getName());
		boadQna.setFstRgUsid(user.getId());
		boadQna.setFstRgDt(new Date());
		this.boadQnaService.create(boadQna);
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/QnA/search.htm");
	}

	// 답변 수정 - 운영자
	@RequestMapping(value = "/board/QnA/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpSession session, @RequestParam("seq") Long sn_id,
										@RequestParam(value = "wk", required = true) String wkType,
										@RequestParam(value = "page", required = false) String page) {

		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ModelAndView mav = new ModelAndView("board/QnA/update");

		BoadQna boadQna = this.boadQnaService.get(sn_id);
		
		mav.addObject("user",user);
		mav.addObject("boadQna", boadQna);
		mav.addObject("wk", wkType);
		mav.addObject("page", page);
		mav.addObject("date", new Date());
		if (StringUtils.isEmpty(wkType) || StringUtils.equals(wkType, "create")) return mav;
		
		BoadQna beforeReply = new BoadQna();
		beforeReply.setQuestion_id(sn_id);
		
		try{
			BoadQna beforeReplyM = this.boadQnaService.search(beforeReply).get(0);
			mav.addObject("date", beforeReplyM.getFstRgDt());
			mav.addObject("beforeBody", beforeReplyM.getBody());
		} catch(Exception e) {
			
		}
		
		return mav;
	}

	// 답변 수정 - 운영자
	@RequestMapping(value = "/board/QnA/update.htm", method = RequestMethod.POST)
	public ModelAndView update(SessionStatus sessionStatus, HttpSession session,
									@RequestParam(value = "seq", required = true) Long snId,
									@RequestParam(value = "wk", required = true) String wkType,
									@RequestParam(value = "body", required = false) String body,
									@RequestParam(value = "page", required = false) String page) {
		TraceLog.debug(snId + " / " + wkType + " / " + body + " / " + page);
		
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		BoadQna reply = new BoadQna();
		if(StringUtils.equals(wkType, "update")) {
			reply.setQuestion_id(snId);
			List<BoadQna> list = this.boadQnaService.search(reply);
			BoadQna replyM = list.get(0);
			replyM.setLstChDt(new Date());
			replyM.setLstChUsid(user.getId());
			replyM.setBody(body);
			this.boadQnaService.update(replyM);
		}
		if(StringUtils.equals(wkType, "create")) {
			BoadQna question = this.boadQnaService.get(snId);
			question.setAnswer_yn("Y");
			question.setAnswer_status("602202");
			this.boadQnaService.update(question);
			
			reply.setAnswer_yn("Y");
			reply.setUserType(question);
			reply.setQuestion_id(snId);
			reply.setBody(body);
			reply.setType("601102");
			reply.setOpen_yn(question.getOpen_yn());
			reply.setPen_name(user.getId());
			reply.setFstRgUsid(user.getId());
			reply.setFstRgDt(new Date());
			reply.setRead_cnt(0);
			this.boadQnaService.create(reply);
		}
	
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/QnA/search.htm?page="+pageNum);

		return mav;
	}

	// 질문 수정폼 - 건물주 , 설치자
	@RequestMapping(value = "/board/QnA/updateQuestion.htm", method = RequestMethod.GET)
	public ModelAndView updateQuestionForm(@RequestParam(value = "seq", required = true) Long snId,
												@RequestParam(value = "page", required = false) String page) {
		
		ModelAndView mav = new ModelAndView("board/QnA/update");
		BoadQna question = this.boadQnaService.get(snId);
		
		mav.addObject("boadQna" ,question);
		mav.addObject("page", page);
		
		return mav;
	}
	
	// 질문 수정 - 건물주 , 설치자
	@RequestMapping(value = "/board/QnA/execUpdateQuestion.htm")
	public ModelAndView updateQuestion(HttpSession session,
											@RequestParam(value = "seq", required = true) Long snId,
											@RequestParam(value = "page", required = false) String page,
											@RequestParam(value = "body", required = false) String body,
											@RequestParam(value = "openYn", required = false) String openYn,
											@RequestParam(value = "target", required = false) String target) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		ModelAndView mav = new ModelAndView("redirect:/board/QnA/search.htm?page="+pageNum);
		
		BoadQna question = this.boadQnaService.get(snId);
		if (!StringUtils.equals(target, "question") || question == null) return mav;
		question.setBody(body);
		question.setOpen_yn(openYn);
		question.setLstChDt(new Date());
		question.setLstChUsid(user.getId());
		this.boadQnaService.update(question);
		
		return mav;
	}
	
	
	public String changeFormat(String date, int strNumber) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, strNumber);
			TraceLog.debug("date format : " + date);
			return date;
		}
		return null;
	}
}
