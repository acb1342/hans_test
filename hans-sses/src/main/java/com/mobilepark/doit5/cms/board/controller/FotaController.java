package com.mobilepark.doit5.cms.board.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.service.AdminGroupService;
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.board.model.BoardFota;
import com.mobilepark.doit5.statistics.dao.BoardFotaDaoMybatis;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.FileUtil;
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
public class FotaController {
	
	@Autowired
	private AdminService adminService;
	
	@Autowired
	private AdminGroupService adminGroupService;
	
	@Autowired
	private BoardFotaDaoMybatis boardFotaDaoMybatis;

	
	@RequestMapping("/board/fota/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("board/fota/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		
		PaginatedList boardList = null;
		BoardFota boadFota = new BoardFota();
		
		// 검색
		if (StringUtils.isNotEmpty(searchValue)) {
				boadFota.setFileName(searchValue);
		}
		
		boadFota.setPageNum(pageNum);
		boadFota.setRowPerPage(rowPerPage);
		if (pageNum > 0) {
			boadFota.setStartRow((pageNum - 1) * rowPerPage);
		}
		List<BoardFota> list = boardFotaDaoMybatis.selectSearchList(boadFota);
		
		boardList = new PaginatedListImpl(list, pageNum, boardFotaDaoMybatis.selectSearchCount(boadFota), rowPerPage);
		
		TraceLog.debug("boardList ===> " + boardList.getFullListSize());
		
		mav.addObject("boardList", boardList);
		mav.addObject("rownum", boardList.getFullListSize());
		mav.addObject("page", pageNum);
		return mav;
	}
	
	
	@RequestMapping(value = "/board/fota/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session) {
		ModelAndView mav = new ModelAndView("board/fota/create");
		
		return mav;
	}

	
	@RequestMapping(value = "/board/fota/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@ModelAttribute BoardFota boardFota, HttpSession session, SessionStatus sessionStatus) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		boardFota.setFstRgUsid(user.getId());
		
		if (boardFota.getFotaFile() != null && !boardFota.getFotaFile().isEmpty()) {
			boardFota = this.saveFotaFile(boardFota);
		}
		
		boardFotaDaoMybatis.create(boardFota);

		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/fota/search.htm");
	}
	
	private BoardFota saveFotaFile(BoardFota boardFota) {
		try {
			String uploadDir = Env.get("fota.file.uploadDir") + this.getFolderName();
			FileUtil.makeDirectory(uploadDir);

			File file = new File(uploadDir, boardFota.getFotaFile().getOriginalFilename());
			boardFota.getFotaFile().transferTo(file);

			TraceLog.debug("path:%s, fileName:%s", file.getPath(), file.getName());
			boardFota.setUrl(file.getPath());
//			boardFota.setFileName(file.getName());

		} catch (Exception e) {
			TraceLog.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
		
		return boardFota;
	}
	
	public String getFolderName(){
    	Calendar calendar = Calendar.getInstance();
    	SimpleDateFormat yyyy = new SimpleDateFormat("yyyy", Locale.KOREA);
    	SimpleDateFormat mm = new SimpleDateFormat("MM", Locale.KOREA);
    	
    	String newFolderName = yyyy.format(calendar.getTime()) + "/" + mm.format(calendar.getTime()) + "/";
    	
    	return newFolderName;
    }
	
	
	@RequestMapping("/board/fota/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String sn_id) {
		
		BoardFota boardFota = new BoardFota();
		String[] sequences = sn_id.split(";");
		int deleteCount = 0;
		List<String> deleteList = new ArrayList<String>();
		for (String id : sequences) {
			deleteList.add(id);
			
			boardFota.setSnId(Integer.parseInt(id));
			BoardFota boadFotaOld = boardFotaDaoMybatis.get(boardFota);
			try{
				String path = boadFotaOld.getUrl();
			    File file = new File(path);

			    file.delete();
			}catch (NullPointerException e){}
		}
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("deleteList", deleteList); 
		deleteCount = boardFotaDaoMybatis.delete(param);
		
		return (deleteCount > 0);
	}

	
	@RequestMapping("/board/fota/detail.htm")
	public ModelAndView detail(@ModelAttribute BoardFota boardFota) throws Exception {
		
		BoardFota boadFota= boardFotaDaoMybatis.get(boardFota);
		
		ModelAndView mav = new ModelAndView("board/fota/detail");
		mav.addObject("boadFota", boadFota);
		
		return mav;
	}
	
	@RequestMapping("/board/fota/down.htm")
	public ModelAndView down(@ModelAttribute BoardFota boardFota) throws Exception {
		
		BoardFota boadFota = boardFotaDaoMybatis.get(boardFota);

		ModelAndView mav = new ModelAndView("board/fota/detail");
		mav.addObject("downloadFile", boadFota.getUrl());
		mav.setViewName("fotaDownloadView");
		
		return mav;
	}
	
	@RequestMapping(value = "/board/fota/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@ModelAttribute BoardFota boardFota) throws Exception {
		
		ModelAndView mav = new ModelAndView("board/fota/update");

		BoardFota boadFota= boardFotaDaoMybatis.get(boardFota);
		mav.addObject("boadFota", boadFota);
		
		return mav;
	}

	
	@RequestMapping(value = "/board/fota/update.htm", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute BoardFota boardFota, HttpSession session, SessionStatus sessionStatus) {
		
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		boardFota.setLatChUsid(user.getId());
		
		if (boardFota.getFotaFile() != null && !boardFota.getFotaFile().isEmpty()) {
			BoardFota boadFotaOld = boardFotaDaoMybatis.get(boardFota);
			try{
				String path = boadFotaOld.getUrl();
			    File file = new File(path);

			    file.delete();
			}catch (NullPointerException e){}
			
			boardFota = this.saveFotaFile(boardFota);
		}
		
		boardFotaDaoMybatis.update(boardFota);
		
		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/board/fota/search.htm");

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
