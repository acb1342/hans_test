package com.mobilepark.doit5.cms.board.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import com.mobilepark.doit5.board.model.AppVer;
import com.mobilepark.doit5.board.service.AppVerService;
import com.mobilepark.doit5.cms.SessionAttrName;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

@Controller
public class AppVerController {
	
	@Autowired
	private AppVerService appVerService;
	
	@RequestMapping("/board/appVer/search.htm")
	public ModelAndView search( @RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType) {

		ModelAndView mav = new ModelAndView("appVer/search");
		
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("pageNum", pageNum);
		param.put("rowPerPage", rowPerPage);
		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);
		if (searchType != null && !searchType.equals("")) param.put("os", searchType);
		
		int countAll = this.appVerService.count(param);
		List<Map<String, Object>> list = this.appVerService.search(param);
		
		DateFormat date = new SimpleDateFormat("yyyyMMdd");
		
		mav.addObject("date", date.format(new Date()));
		mav.addObject("appVerList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rownum", countAll-((pageNum-1)*10));
		mav.addObject("page", pageNum);
		
		return mav;
	}
	
	
	@RequestMapping(value = "/board/appVer/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(@RequestParam(value = "page", required = false) String page,
										@RequestParam(value = "searchType", required = false) String searchType) {

		ModelAndView mav = new ModelAndView("appVer/create");
		
		if (page != null && !page.equals("")) mav.addObject("page", page);
		if (searchType != null && !searchType.equals("")) mav.addObject("searchType", searchType);
		mav.addObject("appVer", new AppVer());
		
		return mav;
	}

	
	@RequestMapping(value = "/board/appVer/create.htm", method = RequestMethod.POST)
	public ModelAndView create(AppVer appVer, HttpSession session, SessionStatus sessionStatus,
								@RequestParam(value = "os", required = false) String os,
								@RequestParam(value = "updateType", required = false) String updateType,
								@RequestParam(value = "selDate", required = false) String deployYmd,
								@RequestParam(value = "hour", required = false) String hour,
								@RequestParam(value = "minute", required = false) String minute) {

		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("targetType", "101206");
		param.put("os", os);
		param.put("ver", appVer.getVer());
		param.put("updateType", updateType);
		param.put("regDate", new Date());
		param.put("url", appVer.getUrl());
		param.put("content", appVer.getContent());
		param.put("deployYmd", changeFormat(deployYmd, 8));
		
		if (hour.length() == 1) hour = "0" + hour;
		if (minute.length() == 1) minute = "0" + minute;
		param.put("deployHhmi", hour+minute);
		TraceLog.debug(param.get("deployHhmi").toString() +" - "+ hour+minute);
		this.appVerService.create(param);
		
		sessionStatus.setComplete();
		
		return new ModelAndView("redirect:/board/appVer/search.htm");
	}
	
	@RequestMapping("/board/appVer/detail.htm")
	public ModelAndView detail(@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType,
									@RequestParam(value = "id", required = true) String id) throws Exception {

		ModelAndView mav = new ModelAndView("appVer/detail");
		
		if (page != null && !page.equals("")) mav.addObject("page", page);
		if (searchType != null && !searchType.equals("")) mav.addObject("searchType", searchType);
		
		Map<String, Object> appVer = new HashMap<String, Object>();
		if (id != null && !id.equals("")) {
			appVer = this.appVerService.get(Long.parseLong(id));
		}
		mav.addObject("appVer", appVer);
		
		return mav;
	}
	
	// 목록에서 삭제
	@RequestMapping("/board/appVer/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String ids) {
	
		String[] arrIds = ids.split(";");
		int deleteCount = 0;
		Map<String, Object> appVer = new HashMap<String, Object>();
		
		for (String id : arrIds) {
			appVer = this.appVerService.get(Long.parseLong(id));
			if (!appVer.isEmpty()) {
				deleteCount += this.appVerService.delete(Long.parseLong(id));
			}
			appVer.clear();
		}
		
		return (deleteCount > 0);
	}
	
	@RequestMapping(value = "/board/appVer/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam(value = "page", required = false) String page,
										@RequestParam(value = "searchType", required = false) String searchType,
										@RequestParam("id") Long id) throws Exception {
		
		ModelAndView mav = new ModelAndView("appVer/update");
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		
		Map<String, Object> appVer = this.appVerService.get(id);
		if (appVer.containsKey("deployHhmi")) {
			String hhmi = appVer.get("deployHhmi").toString();
			int beforeHour = Integer.parseInt(hhmi.substring(0, 2));
			int beforeMinute = Integer.parseInt(hhmi.substring(2));
			appVer.put("beforeHour", beforeHour);
			appVer.put("beforeMinute", beforeMinute);
		}
		
		mav.addObject("appVer", appVer);
		
		return mav;
	}

	@RequestMapping(value = "/board/appVer/update.htm", method = RequestMethod.POST)
	public ModelAndView update(HttpSession session, SessionStatus sessionStatus,
									@RequestParam Map<String, Object> appVer,
									@RequestParam(value="hour", required=false, defaultValue="") String hour,
									@RequestParam(value="minute", required=false, defaultValue="") String minute) {
		
		ModelAndView mav = new ModelAndView("redirect:/board/appVer/search.htm");
		
		if (StringUtils.isNotEmpty(hour) && hour.length() == 1) hour = "0" + hour;
		if (StringUtils.isNotEmpty(minute) && minute.length() == 1) minute = "0" + minute;
		
		appVer.put("deployHhmi", hour+minute);
		appVer.put("regDate", new Date());
		
		this.appVerService.update(appVer);
		
		sessionStatus.setComplete();

		return mav;
	}
	
	public String changeFormat(String date, int length) {
		if(StringUtils.isNotEmpty(date) && date.length() >= length) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, length);
			
			return date;
		}
		return null;
	}
}
