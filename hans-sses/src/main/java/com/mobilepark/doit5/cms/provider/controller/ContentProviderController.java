package com.mobilepark.doit5.cms.provider.controller;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.admin.service.AdminGroupService;
import com.mobilepark.doit5.admin.service.AdminService;
import com.mobilepark.doit5.client.model.Application;
import com.mobilepark.doit5.client.service.ApplicationService;
import com.mobilepark.doit5.common.Flag;
import com.mobilepark.doit5.common.UseFlag;
import com.mobilepark.doit5.provider.model.ContentProvider;
import com.mobilepark.doit5.provider.service.ContentProviderService;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.util.HexUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.provider.controller
 * @Filename     : ContentProviderController.java
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 *
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE               Description
 * =================================================================================
 *  1.0	   2014. 2. 25.       최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("contentProvider")
public class ContentProviderController {
	@Autowired
	private ContentProviderService contentProviderService;

	@Autowired
	private ApplicationService applicationService;

	@Autowired
	private AdminService cmsUserService;

	@Autowired
	private AdminGroupService cmsGroupService;

	/**
	 * 컨텐츠 제공자 검색
	 */
	@RequestMapping("/provider/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ContentProvider cp = null;
		List<ContentProvider> list = new ArrayList<ContentProvider>();
		ContentProvider condition = new ContentProvider();

		if (!"ADMIN".equalsIgnoreCase(user.getAdminGroup().getName())) {
			cp = this.contentProviderService.getById(user.getId());
			if (cp != null) {
				condition.setCpId(cp.getCpId());
				if (!EtcUtil.isBlank(searchValue)) {
					if ("cpName".equals(searchType)) {
						condition.setCpName(searchValue);
					}
				}
			}

		} else {
			if (!EtcUtil.isBlank(searchValue)) {
				if ("cpId".equals(searchType)) {
					condition.setCpId(searchValue);
				} else if ("cpName".equals(searchType)) {
					condition.setCpName(searchValue);
				}
			}
		}

		// 검색
		list = this.contentProviderService.search(condition, page, rowPerPage);
		PaginatedListImpl contentProviders = new PaginatedListImpl(list, page, this.contentProviderService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/provider/search");
		mav.addObject("contentProviders", contentProviders);

		return mav;
	}

	/**
	 * 컨텐츠 제공자 생성 폼
	 */
	@RequestMapping(value = "/provider/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("/provider/create");
		mav.addObject("contentProvider", new ContentProvider());

		return mav;
	}

	/**
	 * 컨텐츠 제공자 생성
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "/provider/create.htm", method = RequestMethod.POST)
	public ModelAndView create(ContentProvider contentProvider,
			@RequestParam("cpPasswd") String cpPasswd,
			SessionStatus sessionStatus,
			HttpSession session) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		contentProvider.setCpName(contentProvider.getCpId());
		contentProvider.setCpName(contentProvider.getCpName());
		String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, cpPasswd.getBytes("utf-8")));
		contentProvider.setCpPasswd(encPass);
		contentProvider.setPhone(contentProvider.getPhone());
		contentProvider.setEmail(contentProvider.getEmail());
		this.contentProviderService.create(contentProvider);

		sessionStatus.setComplete();

		// TODO
		// 그룹이 CP가 없는 경우 TBL_CMS_GROUP에 그룹 추가
		// TBL_CMS_USER에 CP User 추가
		AdminGroup cmsGroup = this.cmsGroupService.getByName("CP");
		if (cmsGroup == null) {
			cmsGroup = new AdminGroup();
			cmsGroup.setName("CP");
			cmsGroup.setDescription("CP");
			//cmsGroup.setFstCdt(new Date());
			this.cmsGroupService.create(cmsGroup);
		}

		Admin cmsUser = new Admin();
		cmsUser.setAdminGroup(cmsGroup);
		cmsUser.setId(contentProvider.getCpId());
		cmsUser.setName(contentProvider.getCpName());
		cmsUser.setMobile(contentProvider.getPhone());
		cmsUser.setEmail(contentProvider.getEmail());
		//cmsUser.setFstCdt(new Date());
		//cmsUser.setDefaultUserId(contentProvider.getCpId());
		//cmsUser.setDefaultUseFlag(Flag.Y);
		//cmsUser.setUseFlag(UseFlag.Y);
		//cmsUser.setPassword(encPass);
		this.cmsUserService.create(cmsUser);

		TraceLog.info("create contentProvider [id:%d] by user [id:%s]", contentProvider.getId(), user.getId());

		return new ModelAndView("redirect:/provider/detail.htm?id=" + contentProvider.getId());
	}

	/**
	 * 컨텐츠 제공자 상세
	 */
	@RequestMapping("/provider/detail.htm")
	public ModelAndView detail(@RequestParam("id") Integer id) throws Exception {
		ModelAndView mav = new ModelAndView("/provider/detail");

		ContentProvider contentProvider = this.contentProviderService.get(id);
		if (!EtcUtil.isNone(contentProvider)) {
			mav.addObject("contentProvider", contentProvider);
		}

		return mav;
	}

	/**
	 * 컨텐츠 제공자 수정 폼
	 */
	@RequestMapping(value = "/provider/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Integer id) {
		ContentProvider contentProvider = this.contentProviderService.get(id);

		ModelAndView mav = new ModelAndView("/provider/update");
		mav.addObject("contentProvider", contentProvider);

		return mav;
	}

	/**
	 * 컨텐츠 제공자 수정
	 * @throws UnsupportedEncodingException
	 * @throws NoSuchProviderException
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value = "/provider/update.htm", method = RequestMethod.POST)
	public ModelAndView update(
			@RequestParam("id") Integer id,
			@RequestParam("cpId") String cpId,
			@RequestParam("cpName") String cpName,
			@RequestParam("phone") String phone,
			@RequestParam("email") String email,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		ContentProvider contentProvider = this.contentProviderService.get(id);
		if (contentProvider != null) {
			contentProvider.setCpName(cpName);
			contentProvider.setPhone(phone);
			contentProvider.setEmail(email);
			this.contentProviderService.update(contentProvider);
		}
		sessionStatus.setComplete();

		// TODO
		// CP가 있는 경우 TBL_CMS_USER에서 정보 수정
		Admin cmsUser = this.cmsUserService.getById(cpId);
		if (cmsUser != null) {
			cmsUser.setName(cpName);
			cmsUser.setMobile(phone);
			cmsUser.setEmail(email);
			//cmsUser.setFinUdt(new Date());
			this.cmsUserService.update(cmsUser);
		}
		TraceLog.info("update contentProvider [id:%d] by user [id:%s]", contentProvider.getId(), user.getId());

		return new ModelAndView(new RedirectView("/provider/detail.htm?id=" + contentProvider.getId()));
	}

	@RequestMapping(value = "/provider/changePassword.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean changePassword(
			@RequestParam(value = "id", required = true) Integer id,
			@RequestParam(value = "password", required = true) String password) throws NoSuchAlgorithmException, NoSuchProviderException, UnsupportedEncodingException {
		ContentProvider contentProvider = this.contentProviderService.get(id);
		if (!StringUtils.isBlank(password)) {
			String encPass = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, password.getBytes("utf-8")));
			contentProvider.setCpPasswd(encPass);
			this.contentProviderService.update(contentProvider);

			// TODO
			// CP가 있는 경우 TBL_CMS_USER에서 정보 수정
			Admin cmsUser = this.cmsUserService.getById(contentProvider.getCpId());
			if (cmsUser != null) {
				cmsUser.setPasswd(encPass);
				//cmsUser.setFinUdt(new Date());
				this.cmsUserService.update(cmsUser);
			}
		}

		return true;
	}

	/**
	 * 컨텐츠 제공자 삭제
	 */
	@RequestMapping("/provider/delete.json")
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		int deleteCount = 0;
		String[] ids = selected.split(";");

		for (String id : ids) {
			Integer tmp = new Integer(id);
			ContentProvider contentProvider = this.contentProviderService.get(tmp);
			if (!EtcUtil.isNone(contentProvider)) {
				deleteCount += this.contentProviderService.delete(contentProvider);

				// TODO
				// TBL_CMS_USER에서 CP User 삭제
				Admin cmsUser = this.cmsUserService.getById(contentProvider.getCpId());
				if (cmsUser != null) {
					this.cmsUserService.delete(cmsUser);
				}

				TraceLog.info("delete contentProvider [id:%d] by user [id:%s]", tmp, user.getId());
			} else {
				TraceLog.info("no item to delete contentProvider [id:%d]", tmp);
			}
		}

		return (deleteCount > 0);
	}

	/**
	 * 사용자 존재여부 확인
	 */
	@RequestMapping(value = "/provider/checkCpId.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean checkCpId(@RequestParam(value = "cpId", required = false) String id) {
		Admin cmsUser = this.cmsUserService.get(id);
		ContentProvider cpUser = this.contentProviderService.getById(id);
		return (cmsUser == null && cpUser == null);
	}

	@RequestMapping(value = "/provider/checkUsed.json", method = RequestMethod.POST)
	@ResponseBody
	protected Map<Object, Object> checkUsed(
			@RequestParam(value = "cpid", required = false) Integer id,
			HttpSession session,
			HttpServletRequest request) throws Exception {
		Map<Object, Object> result = new HashMap<Object, Object>();
		Map<Object, Object> errors = new HashMap<Object, Object>();

		ContentProvider cp = this.contentProviderService.get(id);
		if (cp != null) {
			Application application = this.applicationService.getByCpId(cp.getCpId());
			if (application != null) {
				errors.put("reason", "isUsed");
				result.put("errors", errors);
				result.put("success", false);
				return result;
			}
		} else {
			errors.put("reason", "notExist");
			result.put("errors", errors);
			result.put("success", false);
			return result;
		}

		result.put("success", true);

		return result;
	}
}
