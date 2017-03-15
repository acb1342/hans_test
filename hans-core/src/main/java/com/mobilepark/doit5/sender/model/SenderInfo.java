package com.mobilepark.doit5.sender.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.model
 * @Filename     : SenderInfo.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "tbl_sender_info", catalog = "upush")
public class SenderInfo extends AbstractModel<String> {
	@Id
	@Column(name = "host_name", nullable = false, length = 32)
	private String id;

	@Column(name = "sender_name", length = 32)
	private String senderName;

	@Column(name = "sender_index")
	private Integer senderIndex;

	@Column(name = "sender_tid")
	private Integer senderTid;

	@Column(name = "sender_id", length = 32)
	private String senderId;

	@Column(name = "app_id", length = 256)
	private String appId;

	@Column(name = "ip", length = 32)
	private String ip;

	@Column(name = "port")
	private Integer port;

	@Column(name = "url", length = 64)
	private String url;

	@Column(name = "tps")
	private Integer tps;

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSenderName() {
		return this.senderName;
	}

	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}

	public Integer getSenderIndex() {
		return this.senderIndex;
	}

	public void setSenderIndex(Integer senderIndex) {
		this.senderIndex = senderIndex;
	}

	public Integer getSenderTid() {
		return this.senderTid;
	}

	public void setSenderTid(Integer senderTid) {
		this.senderTid = senderTid;
	}

	public String getSenderId() {
		return this.senderId;
	}

	public void setSenderId(String senderId) {
		this.senderId = senderId;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getIp() {
		return this.ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Integer getPort() {
		return this.port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getTps() {
		return this.tps;
	}

	public void setTps(Integer tps) {
		this.tps = tps;
	}
}
