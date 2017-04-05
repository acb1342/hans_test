package com.hans.sses.cms.member.controller;

import com.hans.sses.company.service.CompanyService;
import com.hans.sses.member.model.User;
import com.hans.sses.member.service.UserService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*==================================================================================
 * @Project      : mobilepark cms admin
 * @Package      : com.mobilepark.doit5.cms.admin.controller
 * @Filename     : UserController.java
 * @Description  :
 *
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2017. 03. 28.      최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("member")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private CompanyService companyService;

	/**
	 * 일반 사용자 검색
	 */
	@RequestMapping("/member/user/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false) String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue ) {


		ModelAndView mav = new ModelAndView("user/search");

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

		int countAll = this.userService.count(param);
		List<User> list = this.userService.search(param);

		mav.addObject("userList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1) * rowPerPage));
		mav.addObject("page", pageNum);
		return mav;
	}

	/**
	 * 일반 사용자 생성 폼
	 */
	@RequestMapping(value = "/member/user/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm(HttpSession session,
								   @RequestParam(value = "page", required = false) String page,
								   @RequestParam(value = "searchType", required = false) String searchType,
								   @RequestParam(value = "searchValue", required = false) String searchValue) {
		ModelAndView mav = new ModelAndView("user/create");

		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);

		mav.addObject("user", new User());

		return mav;
	}

	/**
	 * 일반 사용자 생성
	 */
	@RequestMapping(value = "/member/user/create.json", method = RequestMethod.POST)
	public ModelAndView create(@RequestBody User user, SessionStatus sessionStatus)  {


		TraceLog.debug("=============" + user.toString());

		this.userService.create(user);

		sessionStatus.setComplete();

		return new ModelAndView("redirect:/member/user/search.htm");
	}

	/**
	 * 일반 사용자 수정 폼
	 */
	@RequestMapping(value = "/member/user/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(HttpSession session,
								   @RequestParam(value = "page", required = false) String page,
								   @RequestParam(value = "searchType", required = false) String searchType,
								   @RequestParam(value = "searchValue", required = false) String searchValue,
								   @RequestParam("id") long id) throws Exception {
		ModelAndView mav = new ModelAndView("user/update");

		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);

		// get user
		User user = this.userService.get(id);

		Map<String, Object> map = companyService.getMenu(user.getCompany_seq());

		mav.addObject("company_name", map.get("type"));

		mav.addObject("user", user);

		return mav;
	}

	/**
	 * 일반 사용자 수정
	 */
	@RequestMapping(value = "/member/user/update.json", method = RequestMethod.POST)
	public ModelAndView update(@RequestBody User user, SessionStatus sessionStatus) {

		this.userService.update(user);

		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/member/user/detail.htm?id=" + user.getId());

		return mav;
	}

	/**
	 * 일반 사용자 상세
	 */
	@RequestMapping("/member/user/detail.htm")
	public ModelAndView detail(HttpSession session,
							   @RequestParam(value = "page", required = false) String page,
							   @RequestParam(value = "searchType", required = false) String searchType,
							   @RequestParam(value = "searchValue", required = false) String searchValue,
							   @RequestParam("id") long id) throws Exception {
		ModelAndView mav = new ModelAndView("user/detail");

		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);

		// get user
		User user = this.userService.get(id);
		if (user != null) {

			Map<String, Object> map = companyService.getMenu(user.getCompany_seq());

			mav.addObject("company_name", map.get("type"));

			mav.addObject("user", user);
		}

		return mav;
	}

	/**
	 * 일반 사용자 삭제
	 */
	@RequestMapping("/member/user/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected) {
		String[] userIds = selected.split(";");
		int deleteCount = 0;
		for (String id : userIds) {
			Long user_seq = Long.valueOf(id);
			User user = this.userService.get(user_seq);
			if (user != null) {
				deleteCount = this.userService.delete(user_seq);
			} else {
				TraceLog.info("fail to delete. does not exist id [id:%s]", id);
			}
		}

		return (deleteCount > 0);
	}
}
