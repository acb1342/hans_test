package com.mobilepark.doit5.cms.admin.controller;

import java.util.*;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.mobilepark.doit5.admin.model.Menu;
import com.mobilepark.doit5.admin.model.MenuFunc;
import com.mobilepark.doit5.admin.service.MenuService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.EtcUtil;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : CmsMenuController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2014. 2. 6.      최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes({
	"menu", "menuFunc"
})
public class MenuController {
	@Autowired
	private MenuService menuService;

	/**
	 * 루트 메뉴 얻기
	 */
	@RequestMapping(value = "/admin/menu/getRootMenu.json")
	@ResponseBody
	public List<Map<String, Object>> getRootMenu() {
		return this.menuService.getRootMenu();
	}

    /**
     * 메뉴 삭제
     */
    @RequestMapping("/admin/menu/delete.json")
    @ResponseBody
    public String deleteMenu(@RequestParam("id") String menuId) {

    	int id = 0;
		if (isStringDouble(menuId)) id = Integer.parseInt(menuId);

        Map<String, Object> menu = this.menuService.getMenu(id);

        if (menu != null) {
            this.menuService.deleteMenu(id);
            TraceLog.info("delete cms menu [id:%d, url:%s]", menu.get("id"), menu.get("url"));
        } else {
            TraceLog.info("not exist delete cms menu [id:%d]", id);
        }

        return id+"";
    }

	/**
	 * 숫자 체크 함수
	 * @param s
	 * @return
	 */
	public static boolean isStringDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	/**
	 * 메뉴 상세
	 */
	@RequestMapping("/admin/menu/detail.htm")
	public ModelAndView detailMenu(@RequestParam("id") Integer id) throws Exception {
		Map<String, Object> menu = this.menuService.getMenu(id);
		ModelAndView mav = new ModelAndView("admin/menu/detail");
		if (menu != null) {
			List<Map<String, Object>> functions = this.menuService.getFuncList(Integer.parseInt(menu.get("id").toString()));
			if (functions != null) menu.put("functions", functions);
			mav.addObject("cmsMenu", menu);
		}

		return mav;
	}

	/**
	 * 메뉴 수정 폼 
	 */
	@RequestMapping(value = "/admin/menu/update.htm", method = RequestMethod.GET)
	public ModelAndView updateMenuForm(@RequestParam("id") Integer id) {
		Map<String, Object> cmsMenu = this.menuService.getMenu(id);

		List<String> types = new ArrayList<String>(2);
		types.add("DIRECTORY");
		types.add("LEAF");

		ModelAndView mav = new ModelAndView("admin/menu/update");
		mav.addObject("cmsMenu", cmsMenu);
		mav.addObject("types", types);
		return mav;
	}

	/**
	 * 메뉴 수정 
	 */
	@RequestMapping(value = "/admin/menu/update.htm", method = RequestMethod.POST)
	public ModelAndView updateMenu(Menu cmsMenu, SessionStatus sessionStatus) throws Exception {
		cmsMenu.setDescription(EtcUtil.replaceXSSChar(cmsMenu.getDescription()));

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", cmsMenu.getId());
		param.put("lstChDt", new Date());
		param.put("url", cmsMenu.getUrl());
		param.put("description", cmsMenu.getDescription());
		param.put("type", cmsMenu.getType());
		
		this.menuService.updateMenu(param);

		sessionStatus.setComplete();
		TraceLog.info("update cms menu [id:%d, url:%s]", cmsMenu.getId(), cmsMenu.getUrl());

		return detailMenu(cmsMenu.getId());
	}

	/**
	 * 메뉴 순서 이동
	 */
	@RequestMapping(value="/admin/menu/orderUpdate.json")
	@ResponseBody
	public List<Map<String, Object>> orderUpdate(@RequestBody List<Map<String, Object>> menuArr){

		int sort = 0;
		for(Map<String, Object> m : menuArr){

			String parentId = m.get("parent").toString();
			String id = m.get("id").toString();
			String title = m.get("text").toString();
			if("#".equals(parentId)) parentId = null;

			Map<String, Object> param = new HashMap<String, Object>();
			param.put("id", id);
			param.put("parentId", parentId);
			param.put("title", title);
			param.put("sort", sort++);
			param.put("fstRgDt", new Date());
			param.put("lstChDt", new Date());

			/**
			 * TODO insert or update
			 *  checkMenu select 함수로 변경가능
			 *  1,2depth 동시 생성시 parent id 적용 로직 추가예정
			 *  type 이 directory or leaf 결정 여부 체크 로직추가
			 */

			if("1".equals(parentId) || parentId==null){
				param.put("type", "DIRECTORY");
			}else{
				param.put("type", "LEAF");
			}

			int menuIdCnt = this.menuService.checkMenu(id);

			TraceLog.info("==menu check="+menuIdCnt);
			if(menuIdCnt == 0){
				TraceLog.info("==menu insert=");
				this.menuService.orderInsert(param);
			}else{
				TraceLog.info("==menu update=");
				this.menuService.orderUpdate(param);
			}

		}

		return getRootMenu();
	}

	/**
	 * 메뉴 메인 뷰
	 */
	@RequestMapping("/admin/menu/mainView.htm")
	public String mainView() throws Exception {
		return "admin/menu/mainView";
	}

	/**
	 * 메뉴에 기능 생성 폼
	 */
	@RequestMapping(value = "/admin/menu/func/create.htm", method = RequestMethod.GET)
	public ModelAndView createFunctionForm(@RequestParam("menuId") Integer menuId) {
		MenuFunc cmsMenuFunc = new MenuFunc();
		cmsMenuFunc.setMenuId(menuId);

		List<String> types = new ArrayList<String>(6);
		types.add("CREATE");
		types.add("READ");
		types.add("UPDATE");
		types.add("DELETE");
		types.add("APPROVE");
		types.add("ANY");

		ModelAndView mav = new ModelAndView("admin/menu/func/create");
		mav.addObject("types", types);
		mav.addObject("cmsMenuFunc", cmsMenuFunc);
		return mav;
	}

	/**
	 * 메뉴에 기능 생성
	 */
	@RequestMapping(value = "/admin/menu/func/create.htm", method = RequestMethod.POST)
	public ModelAndView createFunction(MenuFunc cmsMenuFunction, SessionStatus sessionStatus) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("menuId", cmsMenuFunction.getMenuId());
		param.put("name", cmsMenuFunction.getName());
		param.put("url", cmsMenuFunction.getUrl());
		param.put("auth", cmsMenuFunction.getAuth());
		param.put("description", cmsMenuFunction.getDescription());
		param.put("fstRgDt", new Date());
		this.menuService.createFunction(param);
		
		sessionStatus.setComplete();

		return detailMenu(cmsMenuFunction.getMenuId());
	}

	/**
	 * 메뉴에 기능 삭제
	 */
	@RequestMapping("/admin/menu/func/delete.json")
	@ResponseBody
	public ModelAndView deleteFunction(@RequestParam("id") Integer id) throws Exception {
		MenuFunc cmsMenuFunction = this.menuService.getFunctionMenu(id);

		if (cmsMenuFunction != null) {
			this.menuService.deleteFunction(id);
			TraceLog.info("delete cms menu function [id:%s, url:%s]", cmsMenuFunction.getId(),
					cmsMenuFunction.getUrl());
		}

		return detailMenu(cmsMenuFunction.getMenuId());
	}

	/**
	 * 메뉴에 기능 상세
	 */
	@RequestMapping("/admin/menu/func/detail.htm")
	public ModelAndView detailFunction(@RequestParam("id") Integer id) throws Exception {
		MenuFunc cmsMenuFunction = this.menuService.getFunctionMenu(id);
		ModelAndView mav = new ModelAndView("admin/menu/func/detail");
		mav.addObject("cmsMenuFunc", cmsMenuFunction);
		return mav;
	}

	/**
	 * 메뉴에 기능 수정 폼
	 */
	@RequestMapping(value = "/admin/menu/func/update.htm", method = RequestMethod.GET)
	public ModelAndView updateFunctionForm(@RequestParam("id") Integer id) {
		MenuFunc cmsMenuFunc = this.menuService.getFunctionMenu(id);

		List<String> types = new ArrayList<String>(6);
		types.add("CREATE");
		types.add("READ");
		types.add("UPDATE");
		types.add("DELETE");
		types.add("APPROVE");
		types.add("ANY");

		ModelAndView mav = new ModelAndView("admin/menu/func/update");
		mav.addObject("cmsMenuFunc", cmsMenuFunc);
		mav.addObject("types", types);
		return mav;
	}

	/**
	 * 메뉴에 기능 수정
	 */
	@RequestMapping(value = "/admin/menu/func/update.htm", method = RequestMethod.POST)
	public ModelAndView updateFunction(MenuFunc cmsMenuFunc, SessionStatus sessionStatus) throws Exception {

		cmsMenuFunc.setDescription(EtcUtil.replaceXSSChar(cmsMenuFunc.getDescription()));

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("id", cmsMenuFunc.getId());
		param.put("name", cmsMenuFunc.getName());
		param.put("description", cmsMenuFunc.getDescription());
		param.put("url", cmsMenuFunc.getUrl());
		param.put("auth", cmsMenuFunc.getAuth());
		param.put("lstChDt", new Date());

		this.menuService.updateMenuFunc(param);
		sessionStatus.setComplete();
		TraceLog.info("update cms menu function [id:%s, url:%s]", cmsMenuFunc.getId(), cmsMenuFunc.getUrl());


		//ModelAndView mav = new ModelAndView("redirect:/admin/menu/detail?id=" + cmsMenu.getId());
		return detailMenu(cmsMenuFunc.getMenuId());
	}
}
