package com.mobilepark.doit5.content.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.mobilepark.doit5.common.MessageEventType;
import com.mobilepark.doit5.common.MessageType;
import com.mobilepark.doit5.common.UseFlag;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.content.model
 * @Filename     : ContentMsgEvent.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TBL_CONTENT_MSG_EVENT", catalog = "upush")
public class ContentMsgEvent extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name = "MESSAGE_EVENT_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private MessageEventType messageEventType;

	@Column(name = "TARGET_SERVICE_ID")
	private Integer targetServiceId;

	@Column(name = "TARGET_SHOP_ID")
	private Integer targetShopId;

	@Column(name = "TARGET_ZONE_ID")
	private Integer targetZoneId;

	@Column(name = "MESSAGE_TYPE", nullable = false)
	@Enumerated(EnumType.STRING)
	private MessageType messageType;

	@Column(name = "MESSAGE_ID")
	private Long messageId;

	@Column(name = "SP_LINK", length = 512)
	private String spLink;

	@Column(name = "SP_CONTENT_ID", length = 16)
	private String spContentId;

	@Column(name = "START_TIME_DAILY")
	private Short startTimeDaily;

	@Column(name = "END_TIME_DAILY")
	private Short endTimeDaily;

	@Column(name = "DESCRIPTION", length = 16777215)
	private String description;

	@Column(name = "CREATE_USER", nullable = false, length = 32)
	private String createUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", nullable = false, length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 19)
	private Date modifyDate;

	@Column(name = "USE_FLAG", nullable = false)
	@Enumerated(EnumType.STRING)
	private UseFlag useFlag;

	public ContentMsgEvent() {
	}

	public ContentMsgEvent(MessageEventType messageEventType, MessageType messageType, String createUser, Date createDate, UseFlag useFlag) {
		this.messageEventType = messageEventType;
		this.messageType = messageType;
		this.createUser = createUser;
		this.createDate = createDate;
		this.useFlag = useFlag;
	}

	public ContentMsgEvent(MessageEventType messageEventType, Integer targetServiceId, Integer targetShopId, Integer targetZoneId, MessageType messageType,
			Long messageId, String spLink, String spContentId, Short startTimeDaily, Short endTimeDaily, String description,
			String createUser, Date createDate, Date modifyDate, UseFlag useFlag) {
		this.messageEventType = messageEventType;
		this.targetServiceId = targetServiceId;
		this.targetShopId = targetShopId;
		this.targetZoneId = targetZoneId;
		this.messageType = messageType;
		this.messageId = messageId;
		this.spLink = spLink;
		this.spContentId = spContentId;
		this.startTimeDaily = startTimeDaily;
		this.endTimeDaily = endTimeDaily;
		this.description = description;
		this.createUser = createUser;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.useFlag = useFlag;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public MessageEventType getMessageEventType() {
		return this.messageEventType;
	}

	public void setMessageEventType(MessageEventType messageEventType) {
		this.messageEventType = messageEventType;
	}

	public Integer getTargetServiceId() {
		return this.targetServiceId;
	}

	public void setTargetServiceId(Integer targetServiceId) {
		this.targetServiceId = targetServiceId;
	}

	public Integer getTargetShopId() {
		return this.targetShopId;
	}

	public void setTargetShopId(Integer targetShopId) {
		this.targetShopId = targetShopId;
	}

	public Integer getTargetZoneId() {
		return this.targetZoneId;
	}

	public void setTargetZoneId(Integer targetZoneId) {
		this.targetZoneId = targetZoneId;
	}

	public MessageType getMessageType() {
		return this.messageType;
	}

	public void setMessageType(MessageType messageType) {
		this.messageType = messageType;
	}

	public Long getMessageId() {
		return this.messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	public String getSpLink() {
		return this.spLink;
	}

	public void setSpLink(String spLink) {
		this.spLink = spLink;
	}

	public String getSpContentId() {
		return this.spContentId;
	}

	public void setSpContentId(String spContentId) {
		this.spContentId = spContentId;
	}

	public Short getStartTimeDaily() {
		return this.startTimeDaily;
	}

	public void setStartTimeDaily(Short startTimeDaily) {
		this.startTimeDaily = startTimeDaily;
	}

	public Short getEndTimeDaily() {
		return this.endTimeDaily;
	}

	public void setEndTimeDaily(Short endTimeDaily) {
		this.endTimeDaily = endTimeDaily;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
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

	public UseFlag getUseFlag() {
		return this.useFlag;
	}

	public void setUseFlag(UseFlag useFlag) {
		this.useFlag = useFlag;
	}
}
