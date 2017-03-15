package com.mobilepark.doit5.ifs.upns;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize.Inclusion;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upns
 * @Filename     : UPNSMessage.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 16.      최초 버전
 * =================================================================================
 */
@SuppressWarnings("deprecation")
@JsonSerialize(include = Inclusion.NON_NULL)
@JsonPropertyOrder({
	"route", "target", "messageType", "message", "image", "cpId", "appName", "appMngNum",
	"send", "reply", "timeToLive", "dryRun", "sound", "alert", "payload"
})
public class UPNSMessage {
	private Set<String> route;
	private String target;
	private String messageType;
	private String message;
	private Set<String> image;
	private String cpId;
	private String appPkgName;
	private String appCode;
	private String senderNo;
	private String replyNo;
	private Integer timeToLive;
	private Boolean dryRun;
	private String sound;
	private String alert;
	private String payload;

	private String resultCode;
	private String resultMsg;

	public Set<String> getRoute() {
		return this.route;
	}

	public void setRoute(Set<String> route) {
		this.route = route;
	}

	public String getTarget() {
		return this.target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

	public String getMessageType() {
		return this.messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getMessage() {
		return this.message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Set<String> getImage() {
		return this.image;
	}

	public void setImage(Set<String> image) {
		this.image = image;
	}

	public String getCpId() {
		return this.cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	@JsonProperty("appName")
	public String getAppPkgName() {
		return this.appPkgName;
	}

	@JsonProperty("appName")
	public void setAppPkgName(String appPkgName) {
		this.appPkgName = appPkgName;
	}

	@JsonProperty("appMngNum")
	public String getAppCode() {
		return this.appCode;
	}

	@JsonProperty("appMngNum")
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	@JsonProperty("send")
	public String getSenderNo() {
		return this.senderNo;
	}

	@JsonProperty("send")
	public void setSenderNo(String senderNo) {
		this.senderNo = senderNo;
	}

	@JsonProperty("reply")
	public String getReplyNo() {
		return this.replyNo;
	}

	@JsonProperty("reply")
	public void setReplyNo(String replyNo) {
		this.replyNo = replyNo;
	}

	public Integer getTimeToLive() {
		return this.timeToLive;
	}

	public void setTimeToLive(Integer timeToLive) {
		this.timeToLive = timeToLive;
	}

	public Boolean isDryRun() {
		return this.dryRun;
	}

	public void setDryRun(Boolean dryRun) {
		this.dryRun = dryRun;
	}

	public String getSound() {
		return this.sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String getAlert() {
		return this.alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public String getPayload() {
		return this.payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public String getResultCode() {
		return this.resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	@JsonIgnore
	public String getResultMsg() {
		return this.resultMsg;
	}

	@JsonIgnore
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	@Override
	public String toString() {
		StringWriter sw = new StringWriter();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			//objectMapper.configure(Feature.INDENT_OUTPUT, true);
			objectMapper.writeValue(sw, this);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sw.toString();
	}
}
