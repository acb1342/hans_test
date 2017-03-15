package com.mobilepark.doit5.cms.board.controller.oper;

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
import com.mobilepark.doit5.board.model.oper.BoadQnaAdmin;
import com.mobilepark.doit5.board.service.oper.BoadQnaAdminService;
import com.mobilepark.doit5.cms.SessionAttrName;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller.oper
 * @Filename     : QnaAdminController.java
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
public class QnaAdminController {

	@Autowired
	private BoadQnaAdminService boadQnaAdminService;
	
	@RequestMapping("/board/qnaAdmin/search.htm")
	public ModelAndView search(
									@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType,
									@RequestParam(value = "searchValue", required = false) String searchValue,
									@RequestParam(value = "searchStatus", required = false) String searchStatus,
									@RequestParam(value = "fromDate", required = false) String fromDate,
									@RequestParam(value = "toDate", required = false) String toDate) {

			ModelAndView mav = new ModelAndView("board/qnaAdmin/search");
			
			int pageNum = 1;
			int rowPerPage = Env.getInt("web.rowPerPage", 10);
			try {
			pageNum = Integer.parseInt(page);
			} catch (Exception e) {
			pageNum = 1;
			}
			
			PaginatedList boardList = null;
			
			BoadQnaAdmin boadQna = new BoadQnaAdmin();
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
			
			List<BoadQnaAdmin> list = this.boadQnaAdminService.search(boadQna, pageNum, rowPerPage, "fstRgDt", "desc");
			
			boardList = new PaginatedListImpl(list, pageNum, this.boadQnaAdminService.searchCount(boadQna), rowPerPage);
			
			mav.addObject("boardList", boardList);
			mav.addObject("rownum", boardList.getFullListSize());
			mav.addObject("page", pageNum);
			
			return mav;
		}
		
		
		// 목록에서 삭제
		@RequestMapping("/board/qnaAdmin/delete.json")
		@ResponseBody
		public Boolean delete(@RequestParam("id") String sn_ids) {
			String[] sequences = sn_ids.split(";");
			int deleteCount = 0;
			for (String snId : sequences) {
				// 질문 삭제
				BoadQnaAdmin boadQna = this.boadQnaAdminService.get(Long.parseLong(snId));
				if (boadQna != null) {
					deleteCount = this.boadQnaAdminService.delete(Long.parseLong(snId));
				}
				
				// 답변 삭제
				BoadQnaAdmin answer = new BoadQnaAdmin();
				answer.setQuestionId(Long.parseLong(snId));
				try {
					answer = this.boadQnaAdminService.search(answer).get(0);
					if (answer != null) this.boadQnaAdminService.delete(answer);
				} catch(Exception e) {
				}
			
			}
			
			return (deleteCount > 0);
		}
		
		
		// 답글 삭제
		@RequestMapping("/board/qnaAdmin/deleteRepl.json")
		@ResponseBody
		public Boolean deleteRepl(@RequestParam("id") Long snId) {
			// 답글 삭제
			BoadQnaAdmin reply = new BoadQnaAdmin();
			reply.setQuestionId(snId);
			
			int deleteCount = 0;
			try {
				BoadQnaAdmin replyM = this.boadQnaAdminService.search(reply).get(0);
				if (replyM != null) deleteCount = this.boadQnaAdminService.delete(replyM);
			} catch(Exception e) {
			}
			
			// 질문 상태변경
			BoadQnaAdmin question = this.boadQnaAdminService.get(snId);
			question.setAnswerYn("N");
			question.setAnswerStatus("602201");
			this.boadQnaAdminService.update(question);
			
			return (deleteCount > 0);
		}
		
		
		@RequestMapping("/board/qnaAdmin/detail.htm")
		public ModelAndView detail(HttpSession session,
										@RequestParam(value = "id", required = true) Long snId,
										@RequestParam(value = "page", required = false) String page) throws Exception {
		
			TraceLog.debug("[ID : %s] [Page : %s]", snId, page);
			ModelAndView mav = new ModelAndView("board/qnaAdmin/detail");
			BoadQnaAdmin boadQna = this.boadQnaAdminService.get(snId);
			TraceLog.debug("writer Type : " + boadQna.getWriterType());
			mav.addObject("boadQna", boadQna);
		
			// 답변내용 조회
			BoadQnaAdmin replyM = new BoadQnaAdmin();
			replyM.setQuestionId(boadQna.getSnId());
			List<BoadQnaAdmin> list = this.boadQnaAdminService.search(replyM);
			
			if(!list.isEmpty()) {
				mav.addObject("replyContent", list.get(0).getBody());
				mav.addObject("replyStatus", list.get(0).getAnswerYn());
				mav.addObject("replyTime", list.get(0).getFstRgDt());	
			}
			
			//답변달기
			mav.addObject("date", new Date());
			mav.addObject("replyM", new BoadQnaAdmin());
			
			mav.addObject("page", page);
			return mav;
		}
		
		
		// 질문 생성폼 - 설치자 / 건물주
		@RequestMapping(value = "/board/qnaAdmin/create.htm", method = RequestMethod.GET)
		public ModelAndView createForm() {
		
			ModelAndView mav = new ModelAndView("board/qnaAdmin/create");
			
			mav.addObject("date", new Date());
			mav.addObject("boadQna", new BoadQnaAdmin());
			
			return mav;
		}
		
		// 질문 생성 - 설치자 / 건물주
		@RequestMapping(value = "/board/qnaAdmin/create.htm", method = RequestMethod.POST)
		public ModelAndView create(BoadQnaAdmin boadQna, HttpSession session, SessionStatus sessionStatus,
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
			
			this.boadQnaAdminService.create(boadQna);
			
			sessionStatus.setComplete();
			
			return new ModelAndView("redirect:/board/qnaAdmin/search.htm");
		}
		
		
		// 답변 생성 및 수정폼 - 운영자
		@RequestMapping(value = "/board/qnaAdmin/update.htm", method = RequestMethod.GET)
		public ModelAndView updateForm(HttpSession session, 
											@RequestParam(value = "id", required = true) Long snId,
											@RequestParam(value = "wk", required = true) String wkType,
											@RequestParam(value = "page", required = false) String page) {
		
			Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
			ModelAndView mav = new ModelAndView("board/qnaAdmin/update");
			
			BoadQnaAdmin boadQna = this.boadQnaAdminService.get(snId);
			
			mav.addObject("user",user);
			mav.addObject("boadQna", boadQna);
			mav.addObject("wk", wkType);
			mav.addObject("page", page);
			mav.addObject("date", new Date());
			
			if (StringUtils.isEmpty(wkType) || StringUtils.equals(wkType, "create")) return mav;
			
			BoadQnaAdmin beforeReply = new BoadQnaAdmin();
			beforeReply.setQuestionId(snId);
			
			try{
				BoadQnaAdmin beforeReplyM = this.boadQnaAdminService.search(beforeReply).get(0);
				mav.addObject("date", beforeReplyM.getFstRgDt());
				mav.addObject("beforeBody", beforeReplyM.getBody());
			} catch(Exception e) {
			}
			
			return mav;
		}
		
		// 답변 생성 및 수정 - 운영자
		@RequestMapping(value = "/board/qnaAdmin/update.htm", method = RequestMethod.POST)
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
			BoadQnaAdmin reply = new BoadQnaAdmin();
			
			// 답변 수정
			if(StringUtils.equals(wkType, "update")) {
				reply.setQuestionId(snId);
				List<BoadQnaAdmin> list = this.boadQnaAdminService.search(reply);
				
				if (list.size() != 0) {
					BoadQnaAdmin replyM = list.get(0);
					replyM.setLstChDt(new Date());
					replyM.setLstChUsid(user.getId());
					replyM.setBody(body);
					this.boadQnaAdminService.update(replyM);
				}
			
			}
			//답변 생성 및 질문 상태 변경
			else if(StringUtils.equals(wkType, "create")) {
				// 질문 상태 변경
				BoadQnaAdmin question = this.boadQnaAdminService.get(snId);
				question.setAnswerYn("Y");
				question.setAnswerStatus("602202");
				this.boadQnaAdminService.update(question);
				
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
				
				this.boadQnaAdminService.create(reply);
			}
			
			sessionStatus.setComplete();
			
			ModelAndView mav = new ModelAndView("redirect:/board/qnaAdmin/search.htm?page="+pageNum);
			
			return mav;
		}
		
		
		// 질문 수정폼 - 건물주 , 설치자
		@RequestMapping(value = "/board/qnaAdmin/updateQuestion.htm", method = RequestMethod.GET)
		public ModelAndView updateQuestionForm(
													@RequestParam(value = "id", required = true) Long snId,
													@RequestParam(value = "page", required = false) String page) {
		
			BoadQnaAdmin question = this.boadQnaAdminService.get(snId);
			
			ModelAndView mav = new ModelAndView("board/qnaAdmin/update");
			mav.addObject("boadQna" ,question);
			mav.addObject("page", page);
			
			return mav;
		}
		
		// 질문 수정 - 건물주 , 설치자
		@RequestMapping(value = "/board/qnaAdmin/execUpdateQuestion.htm")
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
			
			ModelAndView mav = new ModelAndView("redirect:/board/qnaAdmin/search.htm?page="+pageNum);
			
			BoadQnaAdmin question = this.boadQnaAdminService.get(snId);
			if (!StringUtils.equals(target, "question") || question == null) return mav;
			question.setBody(body);
			question.setOpenYn(openYn);
			question.setLstChDt(new Date());
			question.setLstChUsid(user.getId());
			this.boadQnaAdminService.update(question);
			
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