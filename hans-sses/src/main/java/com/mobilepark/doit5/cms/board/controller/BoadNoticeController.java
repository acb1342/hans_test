package com.mobilepark.doit5.cms.board.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.board.service.BoadNoticeService;
import com.mobilepark.doit5.cms.SessionAttrName;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;


@Controller
public class BoadNoticeController {
	
	@Autowired
	private BoadNoticeService boadNoticeService;

	
	@RequestMapping("/board/notice/search.htm")
	public ModelAndView search( @RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType,
									@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		//AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		ModelAndView mav = new ModelAndView("notice/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("rowPerPage", rowPerPage);
		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);
		if ((StringUtils.isNotEmpty(searchType) && StringUtils.isNotEmpty(searchValue))) {
			param.put("searchType", searchType);
			param.put("searchValue", searchValue);
		}
		
		int countAll = this.boadNoticeService.count(param);
		List<Map<String, Object>> list = this.boadNoticeService.search(param);
		
		mav.addObject("noticeList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rownum", countAll-((pageNum-1)*10));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/board/notice/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session,
										@RequestParam(value = "page", required = false) String page,
										@RequestParam(value = "searchType", required = false) String searchType,
										@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("notice/create");
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		if (page != null && !page.equals("")) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		
		mav.addObject("date", new Date());
		mav.addObject("userId", user.getId());
		
		return mav;
	}

	
	@RequestMapping(value = "/board/notice/create.htm", method = RequestMethod.POST)
	public ModelAndView create(SessionStatus sessionStatus, @RequestParam Map<String, Object> notice) {
		
		this.boadNoticeService.create(notice);
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/notice/search.htm");
	}
	
	@RequestMapping("/board/notice/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String ids) {
	
		String[] arrIds = ids.split(";");
		int deleteCount = 0;
		Map<String, Object> notice = new HashMap<String, Object>();
		
		for (String id : arrIds) {
			notice = this.boadNoticeService.get(Long.parseLong(id));
			if (!notice.isEmpty()) {
				deleteCount += this.boadNoticeService.delete(Long.parseLong(id));
			}
			notice.clear();
		}
		
		return (deleteCount > 0);
	}

	/*
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
	}*/
		
	public String changeFormat(String date, int length) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, length);
			
			return date;
		}
		return null;
	}
	
	public void printMap(Map<String, Object> map) {
		TraceLog.debug("SIZE : " + map.size());
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			TraceLog.debug("key : %s - value : %s", key, map.get(key));
		}
	}
	
}
