package com.mobilepark.doit5.cms.member.controller;

import com.mobilepark.doit5.member.model.Equipment;
import com.mobilepark.doit5.member.service.EquipmentService;
import com.mobilepark.doit5.member.service.UserEqService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		List<Map<String, String>> list = this.userEqService.search(param);

		mav.addObject("userEqList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);

		return mav;
	}

	/**
	 * 사용자 생성 폼
	 *//*
	@RequestMapping(value = "/member/equipment/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("equipment/create");

		return mav;
	}

	*//**
	 * 장비 생성
	 *//*
	@RequestMapping(value = "/member/equipment/create.json", method = RequestMethod.POST)
	public ModelAndView create(@RequestBody Equipment equipment, SessionStatus sessionStatus) {

		TraceLog.debug("=============" + equipment.getMake_date());

		this.equipmentService.equipmentCreate(equipment);

		sessionStatus.setComplete();

		return new ModelAndView("redirect:/member/equipment/search.htm");
	}

	*//**
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
}