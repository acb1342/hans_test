package com.mobilepark.doit5.client.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.model
 * @Filename     : ApplicationList.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 2.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "tbl_app_list", catalog = "upush")
public class ApplicationList extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "app_list_sn", unique = true, nullable = false)
	private Integer id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "app_sn", nullable = false)
	private Application application;

	@Column(name = "os", length = 16)
	private String os;

	@Column(name = "app_id", length = 256)
	private String appId;

	@Column(name = "app_key", length = 128)
	private String appKey;

	@Column(name = "app_ver", length = 16)
	private String appVer;

	@Column(name = "cert_path", length = 128)
	private String certPath;

	@Column(name = "key_path", length = 128)
	private String keyPath;

	@Column(name = "route_seq", length = 32)
	private String routeSeq;

	@Column(name = "sender_index")
	private Integer senderIndex;

	@Transient
	private String cpId;

	public ApplicationList() {
	}

	public ApplicationList(Application application, String os, String appId, String appKey,
			String appVer, String certPath, String keyPath, String routeSeq, Integer senderIndex) {
		this.application = application;
		this.os = os;
		this.appId = appId;
		this.appKey = appKey;
		this.appVer = appVer;
		this.certPath = certPath;
		this.keyPath = keyPath;
		this.routeSeq = routeSeq;
		this.senderIndex = senderIndex;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Application getApplication() {
		return this.application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppKey() {
		return this.appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppVer() {
		return this.appVer;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	public String getCertPath() {
		return this.certPath;
	}

	public void setCertPath(String certPath) {
		this.certPath = certPath;
	}

	public String getKeyPath() {
		return this.keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public String getRouteSeq() {
		return this.routeSeq;
	}

	public void setRouteSeq(String routeSeq) {
		this.routeSeq = routeSeq;
	}

	public Integer getSenderIndex() {
		return this.senderIndex;
	}

	public void setSenderIndex(Integer senderIndex) {
		this.senderIndex = senderIndex;
	}

	public String getCpId() {
		return this.cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}
}
