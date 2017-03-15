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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.mobilepark.doit5.provider.model.ContentProvider;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.model
 * @Filename     : Application.java
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
@Table(name = "tbl_app", catalog = "upush")
public class Application extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "app_sn", unique = true, nullable = false)
	private Integer id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cp_sn", nullable = false)
	private ContentProvider contentProvider;

	@Column(name = "cp_id", nullable = false, length = 32)
	private String cpId;

	@Column(name = "app_name", length = 256)
	private String appName;

	@Column(name = "pkg_id", nullable = false, length = 256)
	private String pkgId;

	@Column(name = "agent_id", nullable = false, length = 256)
	private String agentId;

	public Application() {
	}

	public Application(ContentProvider contentProvider, String cpId, String appName) {
		this.contentProvider = contentProvider;
		this.cpId = cpId;
		this.appName = appName;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ContentProvider getContentProvider() {
		return this.contentProvider;
	}

	public void setContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public String getCpId() {
		return this.cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getPkgId() {
		return this.pkgId;
	}

	public void setPkgId(String pkgId) {
		this.pkgId = pkgId;
	}

	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
}
