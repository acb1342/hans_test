package com.mobilepark.doit5.cms.client.controller;

import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.displaytag.pagination.PaginatedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mobilepark.doit5.client.model.NsuAgentLib;
import com.mobilepark.doit5.client.service.NsuAgentLibService;
import com.mobilepark.doit5.common.Flag;
import com.mobilepark.doit5.common.MobileType;
import com.mobilepark.doit5.common.ReleaseStatus;
import com.mobilepark.doit5.common.UseFlag;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.Env;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.client.controller
 * @Filename     : NsuAgentLibController.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2015. 1. 7.       Initial Coding & Update
 * =================================================================================
 */
@Controller
@SessionAttributes("nsuAgentLib")
public class NsuAgentLibController {
	@Autowired
	private NsuAgentLibService nsuAgentLibService;

	@RequestMapping(value = "/client/nsuAgentLib/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("client/nsuAgentLib/create");
		mav.addObject("nsuAgentLib", new NsuAgentLib());
		mav.addObject("ynMandatorys", Flag.values());
		mav.addObject("releaseStatusList", ReleaseStatus.values());
		return mav;
	}

	@RequestMapping(value = "/client/nsuAgentLib/create.htm", method = RequestMethod.POST)
	public ModelAndView create(
			@ModelAttribute("nsuAgentLib") NsuAgentLib nsuAgentLib,
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "ynMandatory", required = false) Flag ynMandatory,
			@RequestParam(value = "agentFile", required = false) MultipartFile agentFile,
			@RequestParam(value = "status", required = false) ReleaseStatus status,
			HttpSession session,
			SessionStatus sessionStatus) {

		String userId = (String) session.getAttribute(SessionAttrName.USER_ID);

		nsuAgentLib.setVersion(version);
		nsuAgentLib.setOs(os);
		nsuAgentLib.setYnMandatory(ynMandatory);
		nsuAgentLib.setStatus(status);
		nsuAgentLib.setCreateUser(userId);
		nsuAgentLib.setUseFlag(UseFlag.Y);
		nsuAgentLib.setCreateDate(new Date());
		nsuAgentLib.setModifyDate(new Date());

		this.nsuAgentLibService.create(nsuAgentLib, agentFile);
		sessionStatus.setComplete();
		TraceLog.info("Create Nsu Master [ID:%d, Version:%s, LangCode:%s]",
				nsuAgentLib.getId(), nsuAgentLib.getVersion(), nsuAgentLib.getLangCode());

		return new ModelAndView(new RedirectView("/client/nsuAgentLib/detail.htm?id=" + nsuAgentLib.getId()));
	}

	@RequestMapping("/client/nsuAgentLib/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected) {

		int deleteCount = 0;

		String[] ids = selected.split(";");

		for (String id2 : ids) {
			Integer id = new Integer(id2);
			NsuAgentLib nsuAgentLib = this.nsuAgentLibService.get(id);
			if (nsuAgentLib != null) {
				deleteCount += this.nsuAgentLibService.delete(id);
				TraceLog.info("Delete Agent Libr [ID:%d, Version:%s, LangCode:%s]",
						nsuAgentLib.getId(), nsuAgentLib.getVersion(), nsuAgentLib.getLangCode());
			} else {
				TraceLog.info("Delete Not Exist Agent Lib [ID:%d]", id);
			}
		}

		return (deleteCount > 0);
	}

	@RequestMapping("/client/nsuAgentLib/updateStatus.json")
	@ResponseBody
	public Boolean updateStatus(@RequestParam("id") String selected,
			@RequestParam("status") ReleaseStatus status) {

		int updateCount = 0;

		String[] ids = selected.split(";");

		for (String id2 : ids) {
			Integer id = Integer.parseInt(id2);
			NsuAgentLib nsuAgentLib = this.nsuAgentLibService.get(id);
			if (nsuAgentLib != null) {
				nsuAgentLib.setStatus(status);
				nsuAgentLib.setModifyDate(new Date());
				updateCount += this.nsuAgentLibService.update(nsuAgentLib);
				TraceLog.info("Update Nsu Master Status [ID:%d, Version:%s, LangCode:%s, Status:%s]",
						id, nsuAgentLib.getVersion(), nsuAgentLib.getLangCode(), status);
			} else {
				TraceLog.info("Update Not Exist Master [ID:%d]", id);
			}
		}

		return (updateCount > 0);
	}

	@RequestMapping("/client/nsuAgentLib/detail.htm")
	public ModelAndView detail(@RequestParam("id") Integer id) {

		NsuAgentLib nsuAgentLib = this.nsuAgentLibService.get(id);
		ModelAndView mav = new ModelAndView("client/nsuAgentLib/detail");
		if (nsuAgentLib != null) {
			mav.addObject("nsuAgentLib", nsuAgentLib);
		}

		return mav;
	}

	@RequestMapping("/client/nsuAgentLib/search.htm")
	public ModelAndView search(
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "status", required = false) ReleaseStatus status) {

		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		PaginatedList nsuAgentLibs = null;

		NsuAgentLib nsuAgentLib = new NsuAgentLib();
		if (StringUtils.isNotEmpty(version)) {
			nsuAgentLib.setVersion(version);
		}
		if (status != null) {
			nsuAgentLib.setStatus(status);
		}
		nsuAgentLib.setUseFlag(UseFlag.Y);

		List<NsuAgentLib> list = this.nsuAgentLibService.search(nsuAgentLib, page, rowPerPage);
		nsuAgentLibs = new PaginatedListImpl(list, page, this.nsuAgentLibService.searchCount(nsuAgentLib), rowPerPage);

		ModelAndView mav = new ModelAndView("client/nsuAgentLib/search");
		mav.addObject("iosLastVersion", this.nsuAgentLibService.getAvailableLastNsu("ios"));
		mav.addObject("androidLastVersion", this.nsuAgentLibService.getAvailableLastNsu("android"));
		mav.addObject("nsuAgentLibs", nsuAgentLibs);
		mav.addObject("statusList", ReleaseStatus.values());
		mav.addObject("version", version);
		mav.addObject("status", status);
		mav.addObject("osList", MobileType.values());
		return mav;
	}

	@RequestMapping(value = "/client/nsuAgentLib/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Integer id) {

		NsuAgentLib nsuAgentLib = this.nsuAgentLibService.get(id);

		ModelAndView mav = new ModelAndView("client/nsuAgentLib/update");
		mav.addObject("nsuAgentLib", nsuAgentLib);
		mav.addObject("ynMandatorys", Flag.values());
		mav.addObject("releaseStatusList", ReleaseStatus.values());
		return mav;
	}

	@RequestMapping(value = "/client/nsuAgentLib/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@ModelAttribute("nsuAgentLib") NsuAgentLib nsuAgentLib,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "langCode", required = false) String langCode,
			@RequestParam(value = "ynMandatory", required = false) Flag ynMandatory,
			@RequestParam(value = "agentFile", required = false) MultipartFile agentFile,
			@RequestParam(value = "status", required = false) ReleaseStatus status,
			SessionStatus sessionStatus) {

		nsuAgentLib.setOs(os);
		nsuAgentLib.setLangCode(langCode);
		nsuAgentLib.setYnMandatory(ynMandatory);
		nsuAgentLib.setStatus(status);
		nsuAgentLib.setModifyDate(new Date());

		this.nsuAgentLibService.update(nsuAgentLib, agentFile);
		sessionStatus.setComplete();
		TraceLog.info("Update Nsu Master [ID:%d, Version:%s, LangCode:%s]",
				nsuAgentLib.getId(), nsuAgentLib.getVersion(), nsuAgentLib.getLangCode());

		return new ModelAndView(new RedirectView("/client/nsuAgentLib/detail.htm?id=" + nsuAgentLib.getId()));
	}

	@RequestMapping(value = "/client/nsuAgentLib/checkVersion.json")
	@ResponseBody
	public Boolean checkVersion(
			@RequestParam(value = "version", required = false) String version,
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "langCode", required = false) String langCode) {

		TraceLog.debug("Version:%s, OS:%s, LangCode:%s", version, os, langCode);
		NsuAgentLib nsuAgentLib = this.nsuAgentLibService.get(version, os, langCode);
		boolean result = (nsuAgentLib == null);
		TraceLog.debug("Duplicate Check Nsu Agent Lib [Version:%s, OS:%s, LangCode:%s, Result:%s]",
				version, os, langCode, result);
		return result;
	}

	@RequestMapping(value = "/client/nsuAgentLib/deployLastVersion.json")
	@ResponseBody
	public Boolean deployLastVersion(
			@RequestParam(value = "os", required = false) String os,
			@RequestParam(value = "langCode", required = false) String langCode) {

		Boolean result = true;
		try {
			if (StringUtils.isEmpty(os)) {
				os = "android";
			}
			if (StringUtils.isEmpty(langCode)) {
				langCode = Locale.getDefault().getLanguage();
			}

			this.nsuAgentLibService.deployLastVersion(os, langCode);

		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}

		return result;
	}

}
