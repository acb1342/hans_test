package com.mobilepark.doit5.board.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : AppVer.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 14.       최초 버전
 * =================================================================================*/

@Entity
@Table(name = "TB_BOAD_APP_VER")
public class AppVer extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -6541974424429423841L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long snId;
	
	@Column(name = "OS")
	private String os;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT")
	private Date fstRgDt;
	
	@Column(name = "DEPLOY_HHMI")
	private String deployHhmi;
	
	@Column(name = "DEPLOY_YMD")
	private String deployYmd;
	
	@Column(name = "CONTENT")
	private String content;
	
	@Column(name = "UPDATE_TYPE")
	private String updateType;
	
	@Column(name = "URL")
	private String url;
	
	@Column(name = "VER")
	private String ver;
	
	@Column(name = "TARGET_TYPE")
	private String targetType;

	public Long getSnId() {
		return snId;
	}

	public void setSnId(Long snId) {
		this.snId = snId;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getLstChUsid() {
		return lstChUsid;
	}

	public void setLstChUsid(String lstChUsid) {
		this.lstChUsid = lstChUsid;
	}

	public Date getLstChDt() {
		return lstChDt;
	}

	public void setLstChDt(Date lstChDt) {
		this.lstChDt = lstChDt;
	}

	public String getFstRgUsid() {
		return fstRgUsid;
	}

	public void setFstRgUsid(String fstRgUsid) {
		this.fstRgUsid = fstRgUsid;
	}

	public Date getFstRgDt() {
		return fstRgDt;
	}

	public void setFstRgDt(Date fstRgDt) {
		this.fstRgDt = fstRgDt;
	}

	public String getDeployHhmi() {
		return deployHhmi;
	}

	public void setDeployHhmi(String deployHhmi) {
		this.deployHhmi = deployHhmi;
	}

	public String getDeployYmd() {
		return deployYmd;
	}

	public void setDeployYmd(String deployYmd) {
		this.deployYmd = deployYmd;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	@Override
	public Long getId() {
		return snId;
	}
	
	
}
