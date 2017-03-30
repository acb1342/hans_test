package com.mobilepark.doit5.cms.customer.controller;

import com.mobilepark.doit5.admin.model.Equipment;
import com.mobilepark.doit5.admin.service.EquipmentService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.*;

@Controller
@SessionAttributes("member")
public class EquipmentController {
	@Autowired
	private EquipmentService equipmentService;

	/**
	 * 장비 검색
	 */
	@RequestMapping("/member/equipment/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", required = false, defaultValue = "1") String page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		ModelAndView mav = new ModelAndView("equipment/search");

		int pageNum = Integer.parseInt(page);
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		Map<String, Object> param = new HashMap<String, Object>();

		param.put("searchType", searchType);
		param.put("searchValue", searchValue);
		param.put("searchSelect", searchSelect);

		param.put("pageNum", pageNum);
		param.put("rowPerPage", rowPerPage);

		if (pageNum > 0) param.put("startRow", (pageNum - 1) * rowPerPage);

		int countAll = this.equipmentService.getCount(param);
		List<Map<String, String>> list = this.equipmentService.getList(param);

		mav.addObject("equipmentList", list);
		mav.addObject("countAll", countAll);
		mav.addObject("rowPerPage",rowPerPage);
		mav.addObject("rownum", countAll-((pageNum-1)*rowPerPage));
		mav.addObject("page", pageNum);

		return mav;
	}

	/**
	 * 사용자 생성 폼
	 */
	@RequestMapping(value = "/member/equipment/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("equipment/create");

		return mav;
	}

	/**
	 * 장비 생성
	 */
	@RequestMapping(value = "/member/equipment/create.json", method = RequestMethod.POST)
	public ModelAndView create(@RequestBody Equipment equipment, SessionStatus sessionStatus) {
		this.equipmentService.equipmentCreate(equipment);

		sessionStatus.setComplete();

		return new ModelAndView("redirect:/member/equipment/search.htm");
	}

	/**
	 *  장비 상세
	 */
	@RequestMapping(value = "/member/equipment/detail.htm", method = RequestMethod.GET)
	public ModelAndView detail(@RequestParam(value = "id", required = false) String equip_seq) throws Exception {
		ModelAndView mav = new ModelAndView("equipment/detail");

		Equipment equipment = this.equipmentService.getDetail(equip_seq);

		mav.addObject("equipment", equipment);

		return mav;
	}

	/**
	 * 장비 수정 폼
	 */
	@RequestMapping(value = "/member/equipment/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam(value = "id", required = false) String equip_seq) throws Exception {
		ModelAndView mav = new ModelAndView("equipment/update");

		Equipment equipment = this.equipmentService.getDetail(equip_seq);

		mav.addObject("equipment", equipment);

		return mav;
	}

	/**
	 * 장비 수정
	 */
	@RequestMapping(value = "/member/equipment/update.json", method = RequestMethod.POST)
	public ModelAndView update(@RequestParam Map<String, Object> params, SessionStatus sessionStatus) {
/*
		params.put("LstChDt", new Date());
		
		this.adminService.MemberUpdate(params);

		sessionStatus.setComplete();
*/
		ModelAndView mav = new ModelAndView("redirect:/member/equipment/detail.htm?equip_seq=" + params.get("equip_seq"));

		
		return mav;
	}

	/**
	 * 장비 삭제
	 */
	@RequestMapping("/member/equipment/delete.json")
	@ResponseBody
	public boolean delete(@RequestParam("id") String id) {
		int deleteCount = 0;
		/*
		Map<String, Object> memberDetail = this.adminService.getMemberDetail(id);
		if (memberDetail != null) {

			deleteCount = this.adminService.MemberDelete(id);

		} else {
			TraceLog.info("fail to delete. does not exist id [id:%s]", id);
		}
		*/
		return (deleteCount > 0);
	}
}