package com.mobilepark.doit5.editor.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mobilepark.doit5.common.PushType;
import com.mobilepark.doit5.common.UseFlag;
import com.mobilepark.doit5.common.YoutubeFlag;
import com.uangel.platform.collection.JsonObject;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.editor.model
 * @Filename     : ContentEditor.java
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
@Entity
@Table(name = "TBL_CONTENT_EDITOR", catalog = "upush")
public class ContentEditor extends AbstractModel<Long> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "PUSH_TYPE", length = 16)
	@Enumerated(EnumType.STRING)
	private PushType pushType;

	@Column(name = "EDIT_DATA")
	private String editData;

	@Column(name = "DOWNLOAD_DATA")
	private String downloadData;

	@Column(name = "AUDIO_FLAG")
	@Enumerated(EnumType.STRING)
	private YoutubeFlag audioFlag;

	@Column(name = "AUDIO_Url")
	private String audioUrl;

	@Column(name = "YOUTUBE_FLAG")
	@Enumerated(EnumType.STRING)
	private YoutubeFlag youtubeFlag;

	@Column(name = "YOUTUBE_Url")
	private String youtubeUrl;

	@Column(name = "YOUTUBE_WIDTH")
	private String youtubeWidth;

	@Column(name = "YOUTUBE_HEIGHT")
	private String youtubeHeight;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "MODIFY_DATE")
	private Date modifyDate;

	@Column(name = "TEMPLATE_FLAG")
	private String templateFlag;

	@Column(name = "USE_FLAG")
	@Enumerated(EnumType.STRING)
	private UseFlag useFlag;

	@Override
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public PushType getPushType() {
		return this.pushType;
	}

	public void setPushType(PushType pushType) {
		this.pushType = pushType;
	}

	public String getEditData() {
		return this.editData;
	}

	public void setEditData(String editData) {
		this.editData = editData;
	}

	public String getDownloadData() {
		return this.downloadData;
	}

	public void setDownloadData(String downloadData) {
		this.downloadData = downloadData;
	}

	public YoutubeFlag getAudioFlag() {
		return this.audioFlag;
	}

	public void setAudioFlag(YoutubeFlag audioFlag) {
		this.audioFlag = audioFlag;
	}

	public String getAudioUrl() {
		return this.audioUrl;
	}

	public void setAudioUrl(String audioUrl) {
		this.audioUrl = audioUrl;
	}

	public YoutubeFlag getYoutubeFlag() {
		return this.youtubeFlag;
	}

	public void setYoutubeFlag(YoutubeFlag youtubeFlag) {
		this.youtubeFlag = youtubeFlag;
	}

	public String getYoutubeUrl() {
		return this.youtubeUrl;
	}

	public void setYoutubeUrl(String youtubeUrl) {
		this.youtubeUrl = youtubeUrl;
	}

	public String getYoutubeWidth() {
		return this.youtubeWidth;
	}

	public void setYoutubeWidth(String youtubeWidth) {
		this.youtubeWidth = youtubeWidth;
	}

	public String getYoutubeHeight() {
		return this.youtubeHeight;
	}

	public void setYoutubeHeight(String youtubeHeight) {
		this.youtubeHeight = youtubeHeight;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getTemplateFlag() {
		return this.templateFlag;
	}

	public void setTemplateFlag(String templateFlag) {
		this.templateFlag = templateFlag;
	}

	public UseFlag getUseFlag() {
		return this.useFlag;
	}

	public void setUseFlag(UseFlag useFlag) {
		this.useFlag = useFlag;
	}

	public JsonObject toJsonObject() {
		JsonObject jo = new JsonObject();

		jo.set("id", this.id);
		jo.set("title", this.title);
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		jo.set("createDate", format.format(this.createDate));

		return jo;
	}
}
