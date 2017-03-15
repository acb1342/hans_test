package com.mobilepark.doit5.client.model;

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

import com.mobilepark.doit5.common.Flag;
import com.mobilepark.doit5.common.Status;
import com.mobilepark.doit5.common.UseFlag;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.model
 * @Filename     : NsuAppsLib.java
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
@Table(name = "TBL_NSU_APPS_LIB", catalog = "upush")
public class NsuAppsLib extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name = "`VERSION`", nullable = false, length = 128)
	private String version;

	@Column(name = "LANG_CODE", nullable = false, length = 8)
	private String langCode;

	@Column(name = "YN_MANDATORY", nullable = false)
	@Enumerated(EnumType.STRING)
	private Flag ynMandatory;

	@Column(name = "STATUS", nullable = false)
	@Enumerated(EnumType.STRING)
	private Status status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "APPLY_START_TIME", length = 19)
	private Date applyStartTime;

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

	public NsuAppsLib() {
	}

	public NsuAppsLib(String langCode, Flag ynMandatory, Status status, String createUser, Date createDate, UseFlag useFlag) {
		this.langCode = langCode;
		this.ynMandatory = ynMandatory;
		this.status = status;
		this.createUser = createUser;
		this.createDate = createDate;
		this.useFlag = useFlag;
	}

	public NsuAppsLib(String langCode, Flag ynMandatory, Status status, Date applyStartTime, String createUser, Date createDate, Date modifyDate, UseFlag useFlag) {
		this.langCode = langCode;
		this.ynMandatory = ynMandatory;
		this.status = status;
		this.applyStartTime = applyStartTime;
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

	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getLangCode() {
		return this.langCode;
	}

	public void setLangCode(String langCode) {
		this.langCode = langCode;
	}

	public Flag getYnMandatory() {
		return this.ynMandatory;
	}

	public void setYnMandatory(Flag ynMandatory) {
		this.ynMandatory = ynMandatory;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getApplyStartTime() {
		return this.applyStartTime;
	}

	public void setApplyStartTime(Date applyStartTime) {
		this.applyStartTime = applyStartTime;
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
