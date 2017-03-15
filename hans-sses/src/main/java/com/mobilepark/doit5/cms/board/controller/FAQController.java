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
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.board.model.BoadFaq;
import com.mobilepark.doit5.board.service.BoadFaqService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : evc-admin
 * @Package      : com.mobilepark.doit5.cms.board.controller
 * @Filename     : FAQController.java
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
public class FAQController {

	@Autowired
	private BoadFaqService boadFaqService;

	
	@RequestMapping("/board/FAQ/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "groups", required = false) String groups,
			@RequestParam(value = "category", required = false) String category) {
		
		Admin admin = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		
		ModelAndView mav = new ModelAndView("board/FAQ/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		PaginatedList boardList = null;
		BoadFaq boadFaq = new BoadFaq();
		
		// 설치자
		if (adminGroup.getId() == 2) boadFaq.setInstaller_yn("Y");
		// 건물주
		if (adminGroup.getId() == 3) boadFaq.setOwner_yn("Y");
		
		// 카테고리별
		if( (StringUtils.isNotEmpty(category)) && !StringUtils.equals(category, "0") ) {
			boadFaq.setCategory(category);
		}
		
		// 검색
		String custYn = "N";
		String ownerYn = "N";
		String instYn = "N";
		if(StringUtils.isNotEmpty(searchValue)) boadFaq.setQuestion(searchValue);
		if(StringUtils.isNotEmpty(groups)) {
			boadFaq.setYN(groups);
			
			// 검색 유지
			try{
				String[] groupArr = groups.split(";");
				for (String group : groupArr) {
					switch(Integer.parseInt(group)) {
						case 0 : custYn = "Y";		break;
						case 1 : ownerYn = "Y";	break;
						case 2 : instYn = "Y";		break;
					}
				}
				
				mav.addObject("custYn", custYn);
				mav.addObject("ownerYn", ownerYn);
				mav.addObject("instYn", instYn);
			} catch(Exception e) {
				
			}
		}
		
		List<BoadFaq> list = this.boadFaqService.search(boadFaq, pageNum, rowPerPage, "sn_id", "desc");
		boardList = new PaginatedListImpl(list, pageNum, this.boadFaqService.searchCount(boadFaq), rowPerPage);
		
		TraceLog.debug("boardList ===> " + boardList.getFullListSize());
		
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		return mav;
	}
	
	
	@RequestMapping(value = "/board/FAQ/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("board/FAQ/create");
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		mav.addObject("date", new Date());
		mav.addObject("boadFaq", new BoadFaq());
		mav.addObject("user_id", user.getId());
		
		return mav;
	}

	
	@RequestMapping(value = "/board/FAQ/create.htm", method = RequestMethod.POST)
	public ModelAndView create(BoadFaq boadFaq,HttpSession session,SessionStatus sessionStatus,
									@RequestParam(value = "category", required = false) String category,
									@RequestParam(value = "display_yn", required = false) String display_yn,
									@RequestParam(value = "groups", required = false) String groups) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		boadFaq.setYN(groups);
		boadFaq.setDisply_yn(display_yn);
		boadFaq.setCategory(category.trim());
		boadFaq.setFstRgUsid(user.getId());
		boadFaq.setFstRgDt(new Date());
		
		this.boadFaqService.create(boadFaq);

		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/FAQ/search.htm");
	}
	
	
	@RequestMapping("/board/FAQ/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String sn_id) {
		
		int deleteCount = 0;
		String[] sequences = sn_id.split(";");
		
		for (String id : sequences) {
			try {
				if(Long.parseLong(id) > 0) {
					BoadFaq boadFaq = this.boadFaqService.get(Long.parseLong(id));
					if(boadFaq != null) {
						deleteCount = this.boadFaqService.delete(Long.parseLong(id));
					}
				}
			} catch(Exception e) {
				continue;
			}
		}

		return (deleteCount > 0);
	}

	
	@RequestMapping("/board/FAQ/detail.htm")
	public ModelAndView detail(
			HttpSession session,
			@RequestParam(value = "seq", required = true) Long sn_id) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/FAQ/detail");
		BoadFaq boadFaq = this.boadFaqService.get(sn_id);
			
		mav.addObject("boadFaq", boadFaq);
		
		return mav;
	}
	
	
	
	@RequestMapping(value = "/board/FAQ/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(	@RequestParam("seq") Long sn_id) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/FAQ/update");

		BoadFaq boadFaq = this.boadFaqService.get(sn_id);
		mav.addObject("boadFaq", boadFaq);
		
		return mav;
	}

	
	@RequestMapping(value = "/board/FAQ/update.htm", method = RequestMethod.POST)
	public ModelAndView update(BoadFaq otherBoard,
									SessionStatus sessionStatus, HttpSession session,
									@RequestParam(value = "seq", required = true) Long sn_id,
									@RequestParam(value = "displayWho", required = false) String displayWho,
									@RequestParam(value = "display_yn", required = false) String display_yn,
									@RequestParam(value = "category", required = false) String category,
									@RequestParam(value = "groups", required = false) String groups) {
		TraceLog.debug("[seq:%s] [displayWho:%s] [display_yn:%s] [category:%s] [groups:%s]" ,
				sn_id, displayWho, display_yn, category, groups);
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		BoadFaq boadFaq = this.boadFaqService.get(sn_id);
		boadFaq.setDisply_yn(display_yn);
		boadFaq.setCategory(category.trim());
		boadFaq.setQuestion(otherBoard.getQuestion());
		boadFaq.setAnswer(otherBoard.getAnswer());
		boadFaq.setYN(groups);
		boadFaq.setLstChDt(new Date());
		boadFaq.setLstChUsid(user.getId());
		
		this.boadFaqService.update(boadFaq);
	
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/FAQ/search.htm");

		return mav;
	}
	
}
