package com.hans.sses.cms.member.controller;

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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hans.sses.admin.model.AdminGroup;
import com.hans.sses.cms.SessionAttrName;
import com.hans.sses.company.service.CompanyService;
import com.hans.sses.member.model.Equipment;
import com.hans.sses.member.model.User;
import com.hans.sses.member.model.UserEq;
import com.hans.sses.member.service.UserEqService;
import com.hans.sses.member.service.UserService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

@Controller
@SessionAttributes("member")
public class UserEqController {
	@Autowired
	private CompanyService companyService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserEqService userEqService;

	/** 사용자 장비 검색 */
	@RequestMapping("/member/userEq/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("userEq/search");
		
		int pageNum = Integer.parseInt(page);
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("searchType", searchType);
		param.put("searchValue", searchValue);
		param.put("rowPerPage", rowPerPage);
		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);

		int countAll = this.userEqService.count(param);
		List<UserEq> list = this.userEqService.search(param);
		
		mav.addObject("userEqList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);

		return mav;
	}

	/** 사용자 장비 생성 폼 */
	@RequestMapping(value = "/member/userEq/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(@RequestParam(value = "page", required = false, defaultValue = "1") String page,
										@RequestParam(value = "searchType", required = false) String searchType,
										@RequestParam(value = "searchValue", required = false) String searchValue,
										@RequestParam(value = "selectedUser", required = false) String selectedUser,
										@RequestParam(value= "searchEquipValue", required = false, defaultValue = "") String macAddr,
										HttpSession session) {
		
		ModelAndView mav = new ModelAndView("userEq/create");
		
		AdminGroup adminGroup = (AdminGroup) session.getAttribute(SessionAttrName.LOGIN_GROUP);
		List<User> userList = this.userEqService.getUserList(adminGroup.getId());
		mav.addObject("userList", userList);
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		if (StringUtils.isNotEmpty(selectedUser)) mav.addObject("selectedUser", selectedUser);
		if (StringUtils.isNotEmpty(macAddr)) mav.addObject("searchEquipValue", macAddr);
		
		List<Equipment> equipList = this.userEqService.getEquipmentList(macAddr);
		mav.addObject("equipList", equipList);
		
		return mav;
	}

	/** 사용자 장비 생성 */
	@RequestMapping(value = "/member/userEq/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@RequestParam Map<String, Object> param) {

		String [] ids = param.get("ids").toString().split(";");
		for (String macAddress : ids) {
			UserEq userEq = new UserEq();
			userEq.setUserSeq(Integer.parseInt(param.get("userSelect").toString()));
			userEq.setMacAddress(macAddress);
			
			this.userEqService.create(userEq);
		}
		
		return new ModelAndView("redirect:/member/userEq/search.htm");
	}

	
	/** 사용자 장비 삭제 */
	@RequestMapping(value = "/member/userEq/delete.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean delete(@RequestParam(value = "id", required = true) Long id) {
		int deleteCount = this.userEqService.delete(id);

		return (deleteCount > 0);
	}
	
	/** 부서 셀렉트박스 */ 
	@RequestMapping(value = "/member/userEq/getCompany.json", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> getCompany(@RequestParam(value = "userSeq", required = true) Long userSeq) {
		User user = this.userService.get(userSeq);
		
		String deptName = " - ";
		String companyName = " - ";
		Map<String, Object> company = this.companyService.getMenu(user.getCompany_seq());
		if (company != null) {
			int parentSeq = Integer.parseInt(company.get("parentId").toString());
			if (parentSeq != 1) {
				deptName = company.get("type").toString();
				Map<String, Object> parentCompany = this.companyService.getMenu(parentSeq);
				companyName = parentCompany.get("type").toString();
			}
			else {
				companyName = company.get("type").toString();
				deptName = " - ";
			}
		}
		
		Map<String, String> res = new HashMap<String, String>();
		res.put("deptName", deptName);
		res.put("companyName", companyName);
		
		return res;
	}
	
	void printMap(Map<String, Object> map) {
		TraceLog.info("===== resultMap =====");
		Iterator<String> iterator = map.keySet().iterator();
		while (iterator.hasNext()) {
			String key = (String) iterator.next();
			TraceLog.debug("[%s] : [%s]", key, map.get(key));
		}
		TraceLog.info("=====================");
	}
}