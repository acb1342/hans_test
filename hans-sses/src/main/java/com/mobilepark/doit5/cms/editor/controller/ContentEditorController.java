package com.mobilepark.doit5.cms.editor.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.mobilepark.doit5.cms.SessionAttrName;
import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.mobilepark.doit5.admin.model.Admin;
import com.mobilepark.doit5.common.PushType;
import com.mobilepark.doit5.common.UseFlag;
import com.mobilepark.doit5.common.YoutubeFlag;
import com.mobilepark.doit5.editor.model.ContentEditor;
import com.mobilepark.doit5.editor.service.ContentEditorService;
import com.uangel.platform.collection.JsonObject;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.DateUtil;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.web.PaginatedListImpl;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.editor.controller
 * @Filename     : ContentEditorController.java
 *
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 *
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 5. 26.      최초 버전
 * =================================================================================
 */
@Controller
@SessionAttributes("contentEditor")
public class ContentEditorController {
	@Autowired
	private ContentEditorService contentEditorService;

	/*****************************************************************************
	 * 템플릿 처리
	 *****************************************************************************/
	@RequestMapping("/editor/template_search.htm")
	public ModelAndView template_search(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		List<ContentEditor> list = new ArrayList<ContentEditor>();
		ContentEditor condition = new ContentEditor();
		condition.setTemplateFlag("Y");
		condition.setUseFlag(UseFlag.Y);
		if (!EtcUtil.isBlank(searchValue)) {
			if ("title".equals(searchType)) {
				condition.setTitle(searchValue);
			}
		} else if (!EtcUtil.isBlank(searchSelect)) {
		}

		// 검색
		list = this.contentEditorService.search(condition, page, rowPerPage);
		PaginatedListImpl contentEditors = new PaginatedListImpl(list, page, this.contentEditorService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/editor/template_search");
		mav.addObject("contentEditors", contentEditors);

		return mav;
	}

	@RequestMapping(value = "/editor/template_update.htm", method = RequestMethod.GET)
	public ModelAndView updateTemplateForm(@RequestParam("id") Long id) {
		ContentEditor contentEditor = this.contentEditorService.get(id);

		ModelAndView mav = new ModelAndView("/editor/template_update");
		mav.addObject("pushTypes", PushType.values());
		mav.addObject("audioFlags", YoutubeFlag.values());
		mav.addObject("youtubeFlags", YoutubeFlag.values());
		mav.addObject("contentEditor", contentEditor);
		mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		mav.addObject("downloadData", StringEscapeUtils.escapeJavaScript(contentEditor.getDownloadData()));

		return mav;
	}

	@RequestMapping(value = "/editor/template_update.htm", method = RequestMethod.POST)
	public ModelAndView updateTemplate(@ModelAttribute("contentEditor") ContentEditor contentEditorParam,
			@RequestParam(value = "pushType", required = false) PushType pushType,
			@RequestParam(value = "audioUrl", required = false) String audioUrl,
			@RequestParam(value = "youtubeUrl", required = false) String youtubeUrl,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		ContentEditor contentEditor = this.contentEditorService.get(contentEditorParam.getId());
		if (contentEditor != null) {
			contentEditor.setTitle(contentEditorParam.getTitle());
			contentEditor.setPushType(pushType);
			contentEditor.setAudioFlag(contentEditorParam.getAudioFlag());
			contentEditor.setAudioUrl(contentEditorParam.getAudioUrl().trim());
			contentEditor.setEditData(contentEditorParam.getEditData());
			contentEditor.setDownloadData(contentEditorParam.getDownloadData());
			contentEditor.setYoutubeFlag(contentEditorParam.getYoutubeFlag());
			contentEditor.setYoutubeUrl(youtubeUrl.trim());
			contentEditor.setYoutubeWidth(contentEditorParam.getYoutubeWidth());
			contentEditor.setYoutubeHeight(contentEditorParam.getYoutubeHeight());
			contentEditor.setModifyDate(DateUtil.now());
			this.contentEditorService.update(contentEditor);

			Document doc;
			doc = Jsoup.parse(contentEditor.getDownloadData());

			if ("Y".equalsIgnoreCase(contentEditorParam.getAudioFlag().toString())) {
				Elements audios = doc.select("audio");
				for (Element audio : audios) {
					audio.attr("controls", "");
					audio.attr("preload", "");
					audio.attr("loop", "");
					audio.attr("style", "text-align: center; width: 100%;");
					contentEditor.setDownloadData(doc.toString());
				}
				Elements audioSrcs = doc.select("source");
				for (Element audioSrc : audioSrcs) {
					audioSrc.attr("src", audioUrl);
					audioSrc.attr("type", "audio/mp3");
					contentEditor.setDownloadData(doc.toString());
				}
			}

			contentEditor.setDownloadData(contentEditor.getDownloadData()
					.replaceAll("<html>", "").replaceAll("<head>", "")
					.replaceAll("</head>", "").replaceAll("<body>", "")
					.replaceAll("</body>", "").replaceAll("</html>", ""));
			this.contentEditorService.update(contentEditor);
		}
		sessionStatus.setComplete();

		TraceLog.info("update template contenteditor [id:%d] by user [id:%s]", contentEditorParam.getId(), user.getId());

		return new ModelAndView(new RedirectView("/editor/template_detail.htm?id=" + contentEditorParam.getId()));
	}

	@RequestMapping("/editor/template_detail.htm")
	public ModelAndView detailTemplate(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("/editor/template_detail");

		ContentEditor contentEditor = this.contentEditorService.get(id);
		if (!EtcUtil.isNone(contentEditor)) {
			mav.addObject("contentEditor", contentEditor);
			mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		}

		return mav;
	}

	/*****************************************************************************
	 * 템플릿 복사
	 *****************************************************************************/

	@RequestMapping({
				"/editor/copyTemplate.json", "/editor/copySimpleTemplate.json"
			})
			@ResponseBody
			public Boolean copyTemplate(@RequestParam("id") Long id,
					@RequestParam(value = "template", required = false) String template,
					HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		int copyCount = 0;

		ContentEditor contentEditor = this.contentEditorService.get(id);
		ContentEditor newContentEditor = new ContentEditor();

		newContentEditor.setTitle("[copy] " + contentEditor.getTitle());
		newContentEditor.setPushType(contentEditor.getPushType());
		newContentEditor.setTemplateFlag(template);
		newContentEditor.setEditData(contentEditor.getEditData());
		newContentEditor.setDownloadData(contentEditor.getDownloadData());
		newContentEditor.setYoutubeFlag(contentEditor.getYoutubeFlag());
		newContentEditor.setYoutubeUrl(contentEditor.getYoutubeUrl());
		newContentEditor.setYoutubeWidth(contentEditor.getYoutubeWidth());
		newContentEditor.setYoutubeHeight(contentEditor.getYoutubeHeight());
		newContentEditor.setUseFlag(UseFlag.Y);
		newContentEditor.setCreateDate(DateUtil.now());
		newContentEditor.setModifyDate(DateUtil.now());

		if (!EtcUtil.isNone(newContentEditor)) {
			this.contentEditorService.create(newContentEditor);
			copyCount = 1;
			TraceLog.info("copy contenteditor [id:%d] by user [id:%s]", id, user.getId());
		} else {
			copyCount = 0;
			TraceLog.info("no item to copy contenteditor [id:%d]", id);
		}

		return (copyCount > 0);
	}

	/*****************************************************************************
	 * 심플 템플릿 처리
	 *****************************************************************************/
	@RequestMapping("/editor/sTemplate_search.htm")
	public ModelAndView sTemplate_search(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		List<ContentEditor> list = new ArrayList<ContentEditor>();
		ContentEditor condition = new ContentEditor();
		condition.setTemplateFlag("Y");
		condition.setUseFlag(UseFlag.Y);
		if (!EtcUtil.isBlank(searchValue)) {
			if ("title".equals(searchType)) {
				condition.setTitle(searchValue);
			}
		} else if (!EtcUtil.isBlank(searchSelect)) {
		}

		// 검색
		list = this.contentEditorService.search(condition, page, rowPerPage);
		PaginatedListImpl contentEditors = new PaginatedListImpl(list, page, this.contentEditorService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/editor/sTemplate_search");
		mav.addObject("contentEditors", contentEditors);

		return mav;
	}

	@RequestMapping(value = "/editor/sTemplate_update.htm", method = RequestMethod.GET)
	public ModelAndView updateSimpleTemplateForm(@RequestParam("id") Long id) {

		ModelAndView mav = new ModelAndView("/editor/sTemplate_update");

		ContentEditor contentEditor = this.contentEditorService.get(id);

		mav.addObject("contentEditor", contentEditor);
		mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		mav.addObject("downloadData", StringEscapeUtils.escapeJavaScript(contentEditor.getDownloadData()));

		return mav;
	}

	@RequestMapping(value = "/editor/sTemplate_update.htm", method = RequestMethod.POST)
	public ModelAndView updateSimpleTemplate(@ModelAttribute("contentEditor") ContentEditor contentEditorParam,
			// @RequestParam(value = "pushType", required = false) PushType pushType,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		ContentEditor contentEditor = this.contentEditorService.get(contentEditorParam.getId());
		if (contentEditor != null) {
			contentEditor.setTitle(contentEditorParam.getTitle());
			// contentEditor.setPushType(pushType);
			contentEditor.setEditData(contentEditorParam.getEditData());
			contentEditor.setModifyDate(DateUtil.now());

			this.contentEditorService.update(contentEditor);
		}

		contentEditor.setDownloadData(contentEditor.getEditData());

		this.contentEditorService.update(contentEditor);

		sessionStatus.setComplete();

		TraceLog.info("update template contenteditor [id:%d] by user [id:%s]", contentEditorParam.getId(), user.getId());

		return new ModelAndView(new RedirectView("/editor/sTemplate_detail.htm?id=" + contentEditorParam.getId()));
	}

	@RequestMapping("/editor/sTemplate_detail.htm")
	public ModelAndView detailSimpleTemplate(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("/editor/sTemplate_detail");

		ContentEditor contentEditor = this.contentEditorService.get(id);
		if (!EtcUtil.isNone(contentEditor)) {
			mav.addObject("contentEditor", contentEditor);
			mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		}

		return mav;
	}

	@RequestMapping("/editor/popup/sTemplateList.htm")
	public ModelAndView sTemplate_list(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		List<ContentEditor> list = new ArrayList<ContentEditor>();
		ContentEditor condition = new ContentEditor();
		condition.setTemplateFlag("Y");
		condition.setUseFlag(UseFlag.Y);
		if (!EtcUtil.isBlank(searchValue)) {
			if ("title".equals(searchType)) {
				condition.setTitle(searchValue);
			}
		} else if (!EtcUtil.isBlank(searchSelect)) {
		}

		// 검색
		list = this.contentEditorService.search(condition, page, rowPerPage);
		PaginatedListImpl contentEditors = new PaginatedListImpl(list, page, this.contentEditorService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/editor/popup/sTemplateList");
		mav.addObject("contentEditors", contentEditors);

		return mav;
	}

	/*****************************************************************************
	 * 심플 컨텐츠 에디터
	 *****************************************************************************/
	@RequestMapping("/editor/simpleSearch.htm")
	public ModelAndView simpleSearch(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		List<ContentEditor> list = new ArrayList<ContentEditor>();
		ContentEditor condition = new ContentEditor();
		condition.setTemplateFlag("N");
		condition.setUseFlag(UseFlag.Y);
		if (!EtcUtil.isBlank(searchValue)) {
			if ("title".equals(searchType)) {
				condition.setTitle(searchValue);
			}
		} else if (!EtcUtil.isBlank(searchSelect)) {
		}

		// 검색
		list = this.contentEditorService.search(condition, page, rowPerPage);
		PaginatedListImpl contentEditors = new PaginatedListImpl(list, page, this.contentEditorService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/editor/simpleSearch");
		mav.addObject("contentEditors", contentEditors);

		return mav;
	}

	@RequestMapping(value = "/editor/simpleCreate.htm", method = RequestMethod.GET)
	public ModelAndView simpleCreateForm() {
		ModelAndView mav = new ModelAndView("/editor/simpleCreate");
		// mav.addObject("pushTypes", PushType.values());
		// mav.addObject("audioFlags", YoutubeFlag.values());
		// mav.addObject("youtubeFlags", YoutubeFlag.values());
		mav.addObject("contentEditor", new ContentEditor());

		return mav;
	}

	@RequestMapping(value = "/editor/simpleCreate.htm", method = RequestMethod.POST)
	public ModelAndView simpleCreateHtml(ContentEditor contentEditor,
			@RequestParam(value = "pushType", required = false) PushType pushType,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		contentEditor.setPushType(PushType.IMAGE);
		// contentEditor.setAudioFlag(contentEditor.getAudioFlag());
		// contentEditor.setAudioUrl(contentEditor.getAudioUrl().trim());
		// contentEditor.setYoutubeUrl(contentEditor.getYoutubeUrl().trim());
		// contentEditor.setYoutubeWidth(contentEditor.getYoutubeWidth());
		// contentEditor.setYoutubeHeight(contentEditor.getYoutubeHeight());

		contentEditor.setCreateDate(DateUtil.now());
		contentEditor.setModifyDate(DateUtil.now());

		contentEditor.setUseFlag(UseFlag.Y);
		this.contentEditorService.create(contentEditor);

		/* HTML 파서를 이용한 이미지 URL 추출 */
		/*
		Document doc;
		doc = Jsoup.parse(contentEditor.getDownloadData());
		if ("Y".equalsIgnoreCase(contentEditor.getAudioFlag().toString())) {
			Elements audios = doc.select("audio");
			for (Element audio : audios) {
				audio.attr("controls", "");
				audio.attr("preload", "");
				audio.attr("loop", "");
				audio.attr("style", "text-align: center; width: 100%;");
				contentEditor.setDownloadData(doc.toString());
			}
			Elements audioSrcs = doc.select("source");
			for (Element audioSrc : audioSrcs) {
				audioSrc.attr("src", contentEditor.getAudioUrl());
				audioSrc.attr("type", "audio/mp3");
				contentEditor.setDownloadData(doc.toString());
			}
		}
		contentEditor.setDownloadData(contentEditor.getDownloadData()
				.replaceAll("<html>", "").replaceAll("<head>", "")
				.replaceAll("</head>", "").replaceAll("<body>", "")
				.replaceAll("</body>", "").replaceAll("</html>", ""));
		this.contentEditorService.update(contentEditor);
		*/

		sessionStatus.setComplete();
		TraceLog.info("create contenteditor [id:%d] by user [id:%s]", contentEditor.getId(), user.getId());

		return new ModelAndView("redirect:/editor/simpleDetail.htm?id=" + contentEditor.getId());
	}

	@RequestMapping("/editor/simpleDetail.htm")
	public ModelAndView simpleDetail(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("/editor/simpleDetail");

		ContentEditor contentEditor = this.contentEditorService.get(id);
		if (!EtcUtil.isNone(contentEditor)) {
			mav.addObject("contentEditor", contentEditor);
			mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		}

		return mav;
	}

	@RequestMapping(value = "/editor/simpleUpdate.htm", method = RequestMethod.GET)
	public ModelAndView simpleUpdateForm(@RequestParam("id") Long id) {

		ModelAndView mav = new ModelAndView("/editor/simpleUpdate");

		ContentEditor contentEditor = this.contentEditorService.get(id);

		mav.addObject("contentEditor", contentEditor);
		mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		mav.addObject("downloadData", StringEscapeUtils.escapeJavaScript(contentEditor.getDownloadData()));

		return mav;
	}

	@RequestMapping(value = "/editor/simpleUpdate.htm", method = RequestMethod.POST)
	public ModelAndView simpleUpdate(@ModelAttribute("contentEditor") ContentEditor contentEditorParam,
			// @RequestParam(value = "pushType", required = false) PushType pushType,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		ContentEditor contentEditor = this.contentEditorService.get(contentEditorParam.getId());
		if (contentEditor != null) {
			contentEditor.setTitle(contentEditorParam.getTitle());
			// contentEditor.setPushType(pushType);
			contentEditor.setEditData(contentEditorParam.getEditData());
			contentEditor.setModifyDate(DateUtil.now());

			this.contentEditorService.update(contentEditor);
		}

		contentEditor.setDownloadData(contentEditor.getEditData());

		this.contentEditorService.update(contentEditor);

		sessionStatus.setComplete();

		TraceLog.info("update contenteditor [id:%d] by user [id:%s]", contentEditorParam.getId(), user.getId());

		return new ModelAndView(new RedirectView("/editor/simpleDetail.htm?id=" + contentEditorParam.getId()));
	}

	/*****************************************************************************
	 * 컨텐츠 에디
	 *****************************************************************************/

	@RequestMapping("/editor/search.htm")
	public ModelAndView search(HttpSession session,
			@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "searchType", required = false) String searchType,
			@RequestParam(value = "searchValue", required = false) String searchValue,
			@RequestParam(value = "searchSelect", required = false) String searchSelect) {
		int rowPerPage = Env.getInt("web.rowPerPage", 10);

		List<ContentEditor> list = new ArrayList<ContentEditor>();
		ContentEditor condition = new ContentEditor();
		condition.setTemplateFlag("N");
		condition.setUseFlag(UseFlag.Y);
		if (!EtcUtil.isBlank(searchValue)) {
			if ("title".equals(searchType)) {
				condition.setTitle(searchValue);
			}
		} else if (!EtcUtil.isBlank(searchSelect)) {
		}

		// 검색
		list = this.contentEditorService.search(condition, page, rowPerPage);
		PaginatedListImpl contentEditors = new PaginatedListImpl(list, page, this.contentEditorService.searchCount(condition), rowPerPage);

		ModelAndView mav = new ModelAndView("/editor/search");
		mav.addObject("contentEditors", contentEditors);

		return mav;
	}

	@RequestMapping(value = "/editor/create.htm", method = RequestMethod.GET)
	public ModelAndView createForm() {
		ModelAndView mav = new ModelAndView("/editor/create");
		mav.addObject("pushTypes", PushType.values());
		mav.addObject("audioFlags", YoutubeFlag.values());
		mav.addObject("youtubeFlags", YoutubeFlag.values());
		mav.addObject("contentEditor", new ContentEditor());

		return mav;
	}

	@RequestMapping(value = "/editor/create.htm", method = RequestMethod.POST)
	public ModelAndView createHtml(ContentEditor contentEditor,
			@RequestParam(value = "pushType", required = false) PushType pushType,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		contentEditor.setPushType(pushType);
		contentEditor.setAudioFlag(contentEditor.getAudioFlag());
		contentEditor.setAudioUrl(contentEditor.getAudioUrl().trim());
		contentEditor.setYoutubeUrl(contentEditor.getYoutubeUrl().trim());
		contentEditor.setYoutubeWidth(contentEditor.getYoutubeWidth());
		contentEditor.setYoutubeHeight(contentEditor.getYoutubeHeight());
		contentEditor.setCreateDate(DateUtil.now());
		contentEditor.setModifyDate(DateUtil.now());
		contentEditor.setUseFlag(UseFlag.Y);
		this.contentEditorService.create(contentEditor);

		/* HTML 파서를 이용한 이미지 URL 추출 */
		Document doc;
		doc = Jsoup.parse(contentEditor.getDownloadData());
		if ("Y".equalsIgnoreCase(contentEditor.getAudioFlag().toString())) {
			Elements audios = doc.select("audio");
			for (Element audio : audios) {
				audio.attr("controls", "");
				audio.attr("preload", "");
				audio.attr("loop", "");
				audio.attr("style", "text-align: center; width: 100%;");
				contentEditor.setDownloadData(doc.toString());
			}
			Elements audioSrcs = doc.select("source");
			for (Element audioSrc : audioSrcs) {
				audioSrc.attr("src", contentEditor.getAudioUrl());
				audioSrc.attr("type", "audio/mp3");
				contentEditor.setDownloadData(doc.toString());
			}
		}
		contentEditor.setDownloadData(contentEditor.getDownloadData()
				.replaceAll("<html>", "").replaceAll("<head>", "")
				.replaceAll("</head>", "").replaceAll("<body>", "")
				.replaceAll("</body>", "").replaceAll("</html>", ""));
		this.contentEditorService.update(contentEditor);

		sessionStatus.setComplete();
		TraceLog.info("create contenteditor [id:%d] by user [id:%s]", contentEditor.getId(), user.getId());

		return new ModelAndView("redirect:/editor/detail.htm?id=" + contentEditor.getId());
	}

	@RequestMapping(value = "/editor/create.json", method = RequestMethod.POST)
	@ResponseBody
	public Boolean createJson(@RequestParam("title") String title,
			@RequestParam("editData") String editData,
			@RequestParam("downloadData") String downloadData,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);
		ContentEditor contentEditor = new ContentEditor();

		contentEditor.setTitle(title);
		contentEditor.setEditData(editData);
		contentEditor.setDownloadData(downloadData);
		contentEditor.setCreateDate(DateUtil.now());
		contentEditor.setModifyDate(DateUtil.now());
		contentEditor.setUseFlag(UseFlag.Y);
		this.contentEditorService.create(contentEditor);

		TraceLog.info("create contenteditor [id:%d] by user [id:%s]", contentEditor.getId(), user.getId());

		return true;
	}

	@RequestMapping("/editor/detail.htm")
	public ModelAndView detail(@RequestParam("id") Long id) throws Exception {
		ModelAndView mav = new ModelAndView("/editor/detail");

		ContentEditor contentEditor = this.contentEditorService.get(id);
		if (!EtcUtil.isNone(contentEditor)) {
			mav.addObject("contentEditor", contentEditor);
			mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		}

		return mav;
	}

	@RequestMapping(value = "/editor/update.htm", method = RequestMethod.GET)
	public ModelAndView updateForm(@RequestParam("id") Long id) {

		ModelAndView mav = new ModelAndView("/editor/update");

		ContentEditor contentEditor = this.contentEditorService.get(id);

		mav.addObject("pushTypes", PushType.values());
		mav.addObject("audioFlags", YoutubeFlag.values());
		mav.addObject("youtubeFlags", YoutubeFlag.values());
		mav.addObject("contentEditor", contentEditor);
		mav.addObject("editData", StringEscapeUtils.escapeJavaScript(contentEditor.getEditData()));
		mav.addObject("downloadData", StringEscapeUtils.escapeJavaScript(contentEditor.getDownloadData()));

		return mav;
	}

	@RequestMapping(value = "/editor/update.htm", method = RequestMethod.POST)
	public ModelAndView update(@ModelAttribute("contentEditor") ContentEditor contentEditorParam,
			@RequestParam(value = "pushType", required = false) PushType pushType,
			@RequestParam(value = "audioUrl", required = false) String audioUrl,
			@RequestParam(value = "youtubeUrl", required = false) String youtubeUrl,
			HttpServletRequest request,
			SessionStatus sessionStatus,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		ContentEditor contentEditor = this.contentEditorService.get(contentEditorParam.getId());
		if (contentEditor != null) {
			contentEditor.setTitle(contentEditorParam.getTitle());
			contentEditor.setPushType(pushType);
			contentEditor.setAudioFlag(contentEditorParam.getAudioFlag());
			contentEditor.setAudioUrl(contentEditorParam.getAudioUrl().trim());
			contentEditor.setEditData(contentEditorParam.getEditData());
			contentEditor.setDownloadData(contentEditorParam.getDownloadData());
			contentEditor.setYoutubeFlag(contentEditorParam.getYoutubeFlag());
			contentEditor.setYoutubeUrl(youtubeUrl.trim());
			contentEditor.setYoutubeWidth(contentEditorParam.getYoutubeWidth());
			contentEditor.setYoutubeHeight(contentEditorParam.getYoutubeHeight());
			contentEditor.setModifyDate(DateUtil.now());
			this.contentEditorService.update(contentEditor);

			Document doc;
			doc = Jsoup.parse(contentEditor.getDownloadData());

			if ("Y".equalsIgnoreCase(contentEditorParam.getAudioFlag().toString())) {
				Elements audios = doc.select("audio");
				for (Element audio : audios) {
					audio.attr("controls", "");
					audio.attr("preload", "");
					audio.attr("loop", "");
					audio.attr("style", "text-align: center; width: 100%;");
					contentEditor.setDownloadData(doc.toString());
				}
				Elements audioSrcs = doc.select("source");
				for (Element audioSrc : audioSrcs) {
					audioSrc.attr("src", audioUrl);
					audioSrc.attr("type", "audio/mp3");
					contentEditor.setDownloadData(doc.toString());
				}
			}

			contentEditor.setDownloadData(contentEditor.getDownloadData()
					.replaceAll("<html>", "").replaceAll("<head>", "")
					.replaceAll("</head>", "").replaceAll("<body>", "")
					.replaceAll("</body>", "").replaceAll("</html>", ""));
			this.contentEditorService.update(contentEditor);
		}
		sessionStatus.setComplete();

		TraceLog.info("update contenteditor [id:%d] by user [id:%s]", contentEditorParam.getId(), user.getId());

		return new ModelAndView(new RedirectView("/editor/detail.htm?id=" + contentEditorParam.getId()));
	}

	/**
	 * 컨텐츠 에디팅 삭제
	 */
	@RequestMapping({
		"/editor/delete.json", "/editor/simpleDelete.json"
	})
	@ResponseBody
	public Boolean delete(@RequestParam("id") String selected,
			HttpSession session) {
		Admin user = (Admin) session.getAttribute(SessionAttrName.LOGIN_USER);

		int deleteCount = 0;
		String[] ids = selected.split(";");

		for (String id : ids) {
			Long tmp = new Long(id);
			ContentEditor contentEditor = this.contentEditorService.get(tmp);
			contentEditor.setUseFlag(UseFlag.N);
			if (!EtcUtil.isNone(contentEditor)) {
				deleteCount += this.contentEditorService.update(contentEditor);
				TraceLog.info("delete contenteditor [id:%d] by user [id:%s]", tmp, user.getId());
			} else {
				TraceLog.info("no item to delete contenteditor [id:%d]", tmp);
			}
		}

		return (deleteCount > 0);
	}

	/**
	 * 미리 보기
	 */
	@RequestMapping(value = "/editor/popup/preview.htm")
	public ModelAndView selectContent(@RequestParam(value = "id", required = false) Long id) {
		ModelAndView mav = new ModelAndView("/editor/popup/preview");
		if (!EtcUtil.isNone(id)) {
			ContentEditor contentEditor = this.contentEditorService.get(id);
			if (!EtcUtil.isNone(contentEditor)) {
				mav.addObject("downloadData", StringEscapeUtils.escapeJavaScript(contentEditor.getDownloadData()));
			}
		}

		return mav;
	}

	@RequestMapping(value = "/editor/getPushBody.json")
	@ResponseBody
	public JsonObject getUser(@RequestParam(value = "editorId") Long id) {
		JsonObject jsonOb = new JsonObject();
		ContentEditor contentEditor = this.contentEditorService.get(id);

		jsonOb.set("id", id);
		jsonOb.set("title", contentEditor.getTitle());
		jsonOb.set("data", contentEditor.getDownloadData());
		return jsonOb;
	}
}
