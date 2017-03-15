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
import com.mobilepark.doit5.board.model.cust.BoadQnaCust;
import com.mobilepark.doit5.board.service.cust.BoadQnaCustService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;


/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller.cust
 * @Filename     : QnaCustController.java
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
public class QnaCustController {

	@Autowired
	private BoadQnaCustService boadQnaCustService;
	
	
	@RequestMapping("/board/qnaCust/search.htm")
	public ModelAndView search(
									@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType,
									@RequestParam(value = "searchValue", required = false) String searchValue,
									@RequestParam(value = "searchStatus", required = false) String searchStatus,
									@RequestParam(value = "fromDate", required = false) String fromDate,
									@RequestParam(value = "toDate", required = false) String toDate) {
		
		ModelAndView mav = new ModelAndView("board/qnaCust/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList boardList = null;

		BoadQnaCust boadQna = new BoadQnaCust();
		// Type  -  602101 : 질문 , 602102 : 답변
		boadQna.setType("602101");
		
		// 키워드검색
		if(StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue)) {
			switch(searchType) {
				case "name"	: 	boadQna.setPenName(searchValue);		break;
				case "title"	: 	boadQna.setTitle(searchValue);			break;
				case "all"		: 	boadQna.setSearchKey(searchValue);		break;
			}
		}
		
		// 날짜검색
		if (StringUtils.isNotEmpty(fromDate) && StringUtils.isNotEmpty(toDate)) {
			boadQna.setFromDate(changeFormat(fromDate, 8));
			boadQna.setToDate(changeFormat(toDate, 8));
		}
		
		List<BoadQnaCust> list = this.boadQnaCustService.search(boadQna, pageNum, rowPerPage, "fstRgDt", "desc");
		
		boardList = new PaginatedListImpl(list, pageNum, this.boadQnaCustService.searchCount(boadQna), rowPerPage);
		
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		
		return mav;
	}


	// 목록에서 삭제
	@RequestMapping("/board/qnaCust/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String sn_ids) {
		String[] sequences = sn_ids.split(";");
		int deleteCount = 0;
		for (String snId : sequences) {
			// 질문 삭제
			BoadQnaCust boadQna = this.boadQnaCustService.get(Long.parseLong(snId));
			if (boadQna != null) {
				deleteCount = this.boadQnaCustService.delete(Long.parseLong(snId));
			}
			
			// 답변 삭제
			BoadQnaCust answer = new BoadQnaCust();
			answer.setQuestionId(Long.parseLong(snId));
			try{
			answer = this.boadQnaCustService.search(answer).get(0);
			if (answer != null) this.boadQnaCustService.delete(answer);
			} catch(Exception e) {
			}

		}

		return (deleteCount > 0);
	}

	
	// 답글 삭제
	@RequestMapping("/board/qnaCust/deleteRepl.json")
	@ResponseBody
	public Boolean deleteRepl(@RequestParam("id") Long snId) {
		// 답글 삭제
		BoadQnaCust reply = new BoadQnaCust();
		reply.setQuestionId(snId);
		
		int deleteCount = 0;
		try{
			BoadQnaCust replyM = this.boadQnaCustService.search(reply).get(0);
			if (replyM != null) deleteCount = this.boadQnaCustService.delete(replyM);
			
		} catch(Exception e) {
			
		}
		
		// 질문 상태변경
		BoadQnaCust question = this.boadQnaCustService.get(snId);
		question.setAnswerYn("N");
		question.setAnswerStatus("602201");
		this.boadQnaCustService.update(question);
		
		return (deleteCount > 0);
	}
	
	
	@RequestMapping("/board/qnaCust/detail.htm")
	public ModelAndView detail(HttpSession session,
								@RequestParam(value = "id", required = true) Long snId,
								@RequestParam(value = "page", required = false) String page) throws Exception {
		TraceLog.debug("[ID : %s] [Page : %s]", snId, page);
		ModelAndView mav = new ModelAndView("board/qnaCust/detail");
		BoadQnaCust boadQna = this.boadQnaCustService.get(snId);
		TraceLog.debug("writer Type : " + boadQna.getWriterType());
		mav.addObject("boadQna", boadQna);
		
		// 답변내용 조회
		BoadQnaCust replyM = new BoadQnaCust();
		replyM.setQuestionId(boadQna.getSnId());
		List<BoadQnaCust> list = this.boadQnaCustService.search(replyM);
		
		if(!list.isEmpty()) {
			mav.addObject("replyContent", list.get(0).getBody());
			mav.addObject("replyStatus", list.get(0).getAnswerYn());
			mav.addObject("replyTime", list.get(0).getFstRgDt());	
		}
		
		//답변달기
		mav.addObject("date", new Date());
		mav.addObject("replyM", new BoadQnaCust());
		
		mav.addObject("page", page);
		return mav;
	}


	// 질문 생성폼 - 설치자 / 건물주
	@RequestMapping(value = "/board/qnaCust/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		
		ModelAndView mav = new ModelAndView("board/qnaCust/create");
		
		mav.addObject("date", new Date());
		mav.addObject("boadQna", new BoadQnaCust());
		
		return mav;
	}

	// 질문 생성 - 설치자 / 건물주
	@RequestMapping(value = "/board/qnaCust/create.htm", method = RequestMethod.POST)
	public ModelAndView create(BoadQnaCust boadQna, HttpSession session, SessionStatus sessionStatus,
									@RequestParam(value = "status", required = false) String status) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		boadQna.setType("602101");
		boadQna.setOpenYn(status);
		boadQna.setAnswerYn("N");
		boadQna.setAnswerStatus("602201");
		boadQna.setPenName(user.getName());
		boadQna.setFstRgUsid(user.getId());
		boadQna.setFstRgDt(new Date());
		boadQna.setLstChUsid(user.getId());
		boadQna.setLstChDt(new Date());
		
		this.boadQnaCustService.create(boadQna);
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/qnaCust/search.htm");
	}

	
	// 답변 생성 및 수정폼 - 운영자
	@RequestMapping(value = "/board/qnaCust/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpSession session, 
										@RequestParam(value = "id", required = true) Long snId,
										@RequestParam(value = "wk", required = true) String wkType,
										@RequestParam(value = "page", required = false) String page) {

		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ModelAndView mav = new ModelAndView("board/qnaCust/update");

		BoadQnaCust boadQna = this.boadQnaCustService.get(snId);
		
		mav.addObject("user",user);
		mav.addObject("boadQna", boadQna);
		mav.addObject("wk", wkType);
		mav.addObject("page", page);
		mav.addObject("date", new Date());
		
		if (StringUtils.isEmpty(wkType) || StringUtils.equals(wkType, "create")) return mav;
		
		BoadQnaCust beforeReply = new BoadQnaCust();
		beforeReply.setQuestionId(snId);
		
		try{
			BoadQnaCust beforeReplyM = this.boadQnaCustService.search(beforeReply).get(0);
			mav.addObject("date", beforeReplyM.getFstRgDt());
			mav.addObject("beforeBody", beforeReplyM.getBody());
		} catch(Exception e) {
			
		}
		
		return mav;
	}

	// 답변 생성 및 수정 - 운영자
	@RequestMapping(value = "/board/qnaCust/update.htm", method = RequestMethod.POST)
	public ModelAndView update(SessionStatus sessionStatus, HttpSession session,
									@RequestParam(value = "id", required = true) Long snId,
									@RequestParam(value = "wk", required = true) String wkType,
									@RequestParam(value = "body", required = false) String body,
									@RequestParam(value = "page", required = false) String page) {
		
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		BoadQnaCust reply = new BoadQnaCust();

		// 답변 수정
		if(StringUtils.equals(wkType, "update")) {
			reply.setQuestionId(snId);
			List<BoadQnaCust> list = this.boadQnaCustService.search(reply);
			
			if (list.size() != 0) {
				BoadQnaCust replyM = list.get(0);
				replyM.setLstChDt(new Date());
				replyM.setLstChUsid(user.getId());
				replyM.setBody(body);
				this.boadQnaCustService.update(replyM);
			}
			
		}
		//답변 생성 및 질문 상태 변경
		else if(StringUtils.equals(wkType, "create")) {
			// 질문 상태 변경
			BoadQnaCust question = this.boadQnaCustService.get(snId);
			question.setAnswerYn("Y");
			question.setAnswerStatus("602202");
			this.boadQnaCustService.update(question);
			
			// 답변 생성
			reply.setAnswerYn("Y");
			reply.setQuestionId(snId);
			reply.setBody(body);
			reply.setType("602102");
			reply.setOpenYn(question.getOpenYn());
			reply.setPenName(user.getName());
			reply.setFstRgUsid(user.getId());
			reply.setFstRgDt(new Date());
			reply.setLstChUsid(user.getId());
			reply.setLstChDt(new Date());
			
			this.boadQnaCustService.create(reply);
		}
	
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/qnaCust/search.htm?page="+pageNum);

		return mav;
	}

	
	// 질문 수정폼 - 건물주 , 설치자
	@RequestMapping(value = "/board/qnaCust/updateQuestion.htm", method = RequestMethod.GET)
	public ModelAndView updateQuestionForm(@RequestParam(value = "id", required = true) Long snId,
												@RequestParam(value = "page", required = false) String page) {
		
		BoadQnaCust question = this.boadQnaCustService.get(snId);
		
		ModelAndView mav = new ModelAndView("board/qnaCust/update");
		mav.addObject("boadQna" ,question);
		mav.addObject("page", page);
		
		return mav;
	}
	
	// 질문 수정 - 건물주 , 설치자
	@RequestMapping(value = "/board/qnaCust/execUpdateQuestion.htm")
	public ModelAndView updateQuestion(HttpSession session,
											@RequestParam(value = "id", required = true) Long snId,
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
		
		ModelAndView mav = new ModelAndView("redirect:/board/qnaCust/search.htm?page="+pageNum);
		
		BoadQnaCust question = this.boadQnaCustService.get(snId);
		if (!StringUtils.equals(target, "question") || question == null) return mav;
		question.setBody(body);
		question.setOpenYn(openYn);
		question.setLstChDt(new Date());
		question.setLstChUsid(user.getId());
		this.boadQnaCustService.update(question);
		
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