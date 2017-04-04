package com.hans.sses.cms.admin.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.hans.sses.admin.service.EnergyService;
import com.hans.sses.admin.model.Admin;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : SSES
 * @Package      : com.skt.svc.cms.admin.controller
 * @Filename     : EnergyController.java
 * @Description  : 에너지 관리
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 30.      최초 버전
 * =================================================================================
 */
@Controller
public class DashBoardController {

	
	@RequestMapping(value = "/admin/dashboard/mainView.htm", method = RequestMethod.GET)
	public ModelAndView mainForm() {
		ModelAndView mav = new ModelAndView("admin/dashboard/mainView");

		return mav;
	}

	
}
