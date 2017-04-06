package com.hans.sses.cms.member.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.hans.sses.member.model.Equipment;
import com.hans.sses.member.model.UserEq;
import com.hans.sses.member.service.UserEqService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

@Controller
@SessionAttributes("member")
public class UserEqController {
	
	@Autowired
	private UserEqService userEqService;

	/** 사용자 장비 검색 */
	@RequestMapping("/member/userEq/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("userEq/search");
		TraceLog.debug( " ******************************************* ");
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
										@RequestParam(value = "searchValue", required = false) String searchValue) {
		
		ModelAndView mav = new ModelAndView("userEq/create");
		
		if (StringUtils.isNotEmpty(page)) mav.addObject("page", page);
		if (StringUtils.isNotEmpty(searchType)) mav.addObject("searchType", searchType);
		if (StringUtils.isNotEmpty(searchValue)) mav.addObject("searchValue", searchValue);
		
		List<Map<String, Object>> parentCompanyList = this.userEqService.getCompanyList(null);
		mav.addObject("parentCompanyList",parentCompanyList);
		
		List<Equipment> equipList = this.userEqService.getEquipmentList();
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
	@RequestMapping(value = "/member/userEq/setCompanySelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> setCompanySelect(@RequestParam(value = "parentCompanySeq", required = true) String parentCompanySeq) {
										//throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		TraceLog.debug("parentCompanySeq : " + parentCompanySeq);
		if (StringUtils.isNotEmpty(parentCompanySeq)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("parentCompanySeq", parentCompanySeq);
			
			List<Map<String, Object>> list = this.userEqService.getCompanyList(param);
			if (list == null || list.size() == 0) return null;
		
			return list;
		}
		return null;
	}
	
	/** 사용자 셀렉트박스 */
	@RequestMapping(value = "/member/userEq/setUserSelect.json", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String, Object>> setUserSelect(@RequestParam(value = "companySeq", required = true) String companySeq) {
										//throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		TraceLog.debug("companySeq : " + companySeq);
		if (StringUtils.isNotEmpty(companySeq)) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("companySeq", companySeq);
			
			List<Map<String, Object>> list = this.userEqService.getUserList(param);
			if (list == null || list.size() == 0) return null;
		
			return list;
		}
		return null;
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