package com.hans.sses.cms.member.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.hans.sses.member.model.Equipment;
import com.hans.sses.member.model.User;
import com.hans.sses.member.model.UserEq;
import com.hans.sses.member.service.UserEqService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;

@Controller
@SessionAttributes("member")
public class UserEqController {
	
	@Autowired
	private UserEqService userEqService;

	/**
	 * 장비 검색
	 */
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
		
		TraceLog.debug("******** SIZE : [%s] ********", list.size());
		for (UserEq t : list) {
		
		TraceLog.debug("[%s] - [%s] - [%s] - [%s]", t.getSeq(), t.getUserSeq(), t.getVolume(), t.getCompanySeq());
		
		/*Equipment test = t.getEquipment(); 
			TraceLog.debug("MacAddr : [%s] - EQUIP_NAME : [%s] - MANUFACTURER : [%s] - ETC : [%s] - MAKE_DATE : [%s] - ELECT_POWER : [%s]",
					test.getMacaddress(), test.getName(), test.getManufacturer(), test.getEtc(), test.getMake_date(), test.getElect_power());*/
		
		User test2 = t.getUser();
		TraceLog.debug("USER SEQ : [%s] - USER NAME : [%s] - COMPANY SEQ : [%s] - COMPANY NAME : [%s]",
				test2.getUser_seq(), test2.getUser_name(), test2.getCompany_seq(), test2.getCompany_name());
		
		}
		TraceLog.debug("*****************************");
		
		//List<> this.userEqService.getParentCompanyList();
		
		mav.addObject("userEqList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);

		return mav;
	}

	/**
	 * 사용자 생성 폼
	 */
	@RequestMapping(value = "/member/userEq/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("userEq/create");
		
		List<Map<String, Object>> parentCompanyList = this.userEqService.getCompanyList(null);
		mav.addObject("parentCompanyList",parentCompanyList);
		return mav;
	}

	/**
	 * 장비 생성
	 */
	@RequestMapping(value = "/member/userEq/create.htm", method = RequestMethod.POST)
	public ModelAndView create(@RequestBody Equipment equipment, SessionStatus sessionStatus) {

		//this.equipmentService.equipmentCreate(equipment);

		sessionStatus.setComplete();

		return new ModelAndView("redirect:/member/equipment/search.htm");
	}

	/**
	 *  장비 상세
	 *//*
	@RequestMapping(value = "/member/equipment/detail.htm", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam(value = "id", required = true) String equip_seq) {
		ModelAndView mav = new ModelAndView("equipment/detail");

		Equipment equipment = this.equipmentService.getDetail(equip_seq);

		mav.addObject("equipment", equipment);

		return mav;
	}

	*//**
	 * 장비 수정 폼
	 *//*
	@RequestMapping(value = "/member/equipment/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam(value = "id", required = true) String equip_seq) {
		ModelAndView mav = new ModelAndView("equipment/update");

		Equipment equipment = this.equipmentService.getDetail(equip_seq);

		mav.addObject("equipment", equipment);

		return mav;
	}

	*//**
	 * 장비 수정
	 *//*
	@RequestMapping(value = "/member/equipment/update.json", method = RequestMethod.POST)
	public ModelAndView update(@RequestBody Equipment equipment, SessionStatus sessionStatus) {
		
		this.equipmentService.equipmentUpdate(equipment);

		sessionStatus.setComplete();

		ModelAndView mav = new ModelAndView("redirect:/member/equipment/detail.htm?id=" + equipment.getEquip_seq());

		
		return mav;
	}

	*//**
	 * 장비 삭제
	 *//*
	@RequestMapping(value = "/member/equipment/delete.json", method = RequestMethod.POST)
	@ResponseBody
	public boolean delete(@RequestBody Equipment equipment) {
		int deleteCount = this.equipmentService.equipmentDelete(equipment.getEquip_seq());

		return (deleteCount > 0);
	}*/
	
	// 부서 셀렉트박스
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
	
	// 사용자 셀렉트박스
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
}