package com.mobilepark.doit5.cms.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.member.service.UserEqService;
import com.uangel.platform.util.Env;


@Controller
public class UserEqController {
	
	@Autowired
	private UserEqService userEqService;

	
	@RequestMapping("/member/userEq/search.htm")
	public ModelAndView search( @RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType,
									@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		//AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		ModelAndView mav = new ModelAndView("userEq/search");
		
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
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1) * rowPerPage));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	/*
	@RequestMapping(value = "/board/notice/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session,
										@RequestParam(value = "page", required = false) String page,
										@RequestParam(value = "searchType", required = false) String searchType,
										@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("notice/create");
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		
		mav.addObject("date", new Date());
		mav.addObject("userId", user.getId());
		
		return mav;
	}

	@RequestMapping(value = "/board/notice/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> notice) {
		
		this.boadNoticeService.create(notice);
		
		ModelAndView mav = new ModelAndView("redirect:/board/notice/detail.htm?id=" + notice.get("id").toString());
		
		return mav;
	}
	
	@RequestMapping("/board/notice/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String ids) {
	
		String[] arrIds = ids.split(";");
		int deleteCount = 0;
		Map<String, Object> notice = new HashMap<String, Object>();
		
		for (String id : arrIds) {
			notice = this.boadNoticeService.get(Long.parseLong(id));
			if (notice != null) {
				deleteCount += this.boadNoticeService.delete(Long.parseLong(id));
			}
			notice.clear();
		}
		
		return (deleteCount > 0);
	}

	@RequestMapping("/board/notice/detail.htm")
	public ModelAndView detail(HttpSession session,
									@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType,
									@RequestParam(value = "searchValue", required = false) String searchValue, 
									@RequestParam(value = "id", required = true) Long id) throws Exception {
		
		ModelAndView mav = new ModelAndView("notice/detail");
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		
		Map<String, Object> notice = this.boadNoticeService.get(id);
		mav.addObject("notice", notice);
		
		return mav;
	}
	
	@RequestMapping(value = "/board/notice/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpSession session,
										@RequestParam(value = "page", required = false) String page,
										@RequestParam(value = "searchType", required = false) String searchType,
										@RequestParam(value = "searchValue", required = false) String searchValue, 
										@RequestParam("id") Long id) throws Exception {

		ModelAndView mav = new ModelAndView("notice/update");
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		
		Map<String, Object> notice = this.boadNoticeService.get(id);
		mav.addObject("notice", notice);

		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		mav.addObject("userId", user.getId());
		mav.addObject("date", new Date());

		return mav;
	}

	
	@RequestMapping(value = "/board/notice/update.htm", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam Map<String, Object> notice) {
		
		this.boadNoticeService.update(notice);

		String param="?id=" + notice.get("id").toString();
		if (StringUtils.isNotEmpty(notice.get("page").toString())) param += "&page=" + notice.get("page").toString();
		if (StringUtils.isNotEmpty(notice.get("searchType").toString())) param += "&searchType=" + notice.get("searchType").toString();
		if (StringUtils.isNotEmpty(notice.get("searchValue").toString())) param += "&searchValue=" + notice.get("searchValue").toString();

		ModelAndView mav = new ModelAndView("redirect:/board/notice/detail.htm" + param);

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
	
}
