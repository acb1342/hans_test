package com.hans.sses.cms.attendance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.hans.sses.attendance.service.AttendanceService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;


@Controller
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendaceService;

	/** 근태관리 일별 조회 */
	@RequestMapping("/attendance/daily/search.htm")
	public ModelAndView searchDaily(@RequestParam(value = "page", required = false) String page,
									@RequestParam(value = "searchType", required = false) String searchType,
									@RequestParam(value = "searchValue", required = false) String searchValue,
									@RequestParam(value = "beforeDay", required = false) String beforeDay,
									@RequestParam(value = "afterDay", required = false) String afterDay) {
		int pageNum = 1;
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		try {
			pageNum = Integer.parseInt(page);
		} catch (Exception e) {
			pageNum = 1;
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		// 페이징
		param.put("startRow", (pageNum - 1) * rowPerPage);
		param.put("rowPerPage", rowPerPage);
		
		// 검색
		if (StringUtils.isNotEmpty(searchType)) param.put("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) param.put("searchValue", searchValue);
		if (StringUtils.isNotEmpty(beforeDay)) param.put("beforeDay", changeFormat(beforeDay,8));
		if (StringUtils.isNotEmpty(afterDay)) param.put("afterDay", changeFormat(afterDay,8));
		
		int countAll = this.attendaceService.count(param);
		List<Map<String, Object>> list = this.attendaceService.search(param);
		
		ModelAndView mav = new ModelAndView("attendance/search_daily");
		mav.addObject("attendanceList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1) * rowPerPage));
		mav.addObject("page", pageNum);
		
		return mav;
	}

	/** 근태관리 월별 조회 */
	@RequestMapping("/attendance/monthly/search.htm")
	public ModelAndView searchMonthly() {
		
		ModelAndView mav = new ModelAndView("attendance/search_monthly");
		
		return mav;
	}
		
	@RequestMapping("/attendance/monthly/calendarData.json")
	public List<Map<String, String>> calendarData() {
		
		List<Map<String, String>> list = this.attendaceService.search_monthly();
		for (Map<String, String> map : list) {
			map.put("title", map.get("userName") + " " + map.get("strType") + " " + map.get("regTime"));
			map.put("start", map.get("regDate"));
			
			if (Integer.parseInt(map.get("type")) == 0) map.put("order", "1");
			else map.put("order", "0");
		}
		
		return list;
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
	
	void printMap(Map<String, ?> map) {
		TraceLog.info("========== Print Map ==========");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			if (key.equalsIgnoreCase("order") || key.equalsIgnoreCase("title") || key.equalsIgnoreCase("regDate")) TraceLog.debug("[%s]",map.get(key));
			//TraceLog.debug("[%s] : [%s]", key, map.get(key));
		}
		TraceLog.info("===============================");
	}
}
