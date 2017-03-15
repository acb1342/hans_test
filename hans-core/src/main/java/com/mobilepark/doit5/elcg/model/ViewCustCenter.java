package com.mobilepark.doit5.elcg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : ViewCustCenter.java
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
@Table(name = "VI_ELCG_CUST_CENTER")
public class ViewCustCenter extends AbstractModel<Long> implements Serializable{

	private static final long serialVersionUID = -6199229517493584331L;	
	
	@Id
	@Column(name = "SN_ID")
	private Long snId;
	
	@Id
	@Column(name = "WK_TYPE")
	private String wkType;
	
	@Column(name = "BD_GROUP_ID")
	private Long bdGroupId;
	
	@Column(name = "BD_GROUP_NAME")
	private String bdGroupName;
	
	@Column(name = "BD_ID")
	private Long bdId;
	
	@Column(name = "BD_NAME")
	private String bdName;
	
	@Column(name = "BROKEN_TYPE")
	private String brokenType;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "BODY")
	private String body;
	
	@Column(name = "RESULT_REPORT")
	private String resultReport;
	
	@Column(name = "RC_USID")
	private String rcUsid;
	
	@Column(name = "RC_DT")
	private Date rcDt;
	
	@Column(name = "OD_USID")
	private String odUsid;
	
	@Column(name = "OD_DT")
	private Date odDt;
	
	@Column(name = "WK_USID")
	private String wkUsid;
	
	@Column(name = "WK_NAME")
	private String wkName;
	
	@Column(name = "WK_DT")
	private Date wkDt;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT")
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	// status.subString(4,6)
	@Formula("( SELECT substring(STATUS, 4, 6))")
	String subStatus;
	
	@Override
	public Long getId() {
		return snId;
	}

	public String getWkType() {
		return wkType;
	}

	public void setWkType(String wkType) {
		if(wkType.equals("1")) wkType = "802101";
		if(wkType.equals("2")) wkType = "802102";
	
		this.wkType = wkType;
	}

	public Long getSnId() {
		return snId;
	}

	public void setSnId(Long snId) {
		this.snId = snId;
	}

	public Long getBdGroupId() {
		return bdGroupId;
	}

	public void setBdGroupId(Long bdGroupId) {
		this.bdGroupId = bdGroupId;
	}

	public String getBdGroupName() {
		return bdGroupName;
	}

	public void setBdGroupName(String bdGroupName) {
		this.bdGroupName = bdGroupName;
	}

	public Long getBdId() {
		return bdId;
	}

	public void setBdId(Long bdId) {
		this.bdId = bdId;
	}

	public String getBdName() {
		return bdName;
	}

	public void setBdName(String bdName) {
		this.bdName = bdName;
	}

	public String getBrokenType() {
		return brokenType;
	}

	public void setBrokenType(String brokenType) {
		this.brokenType = brokenType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getResultReport() {
		return resultReport;
	}

	public void setResultReport(String resultReport) {
		this.resultReport = resultReport;
	}

	public String getRcUsid() {
		return rcUsid;
	}

	public void setRcUsid(String rcUsid) {
		this.rcUsid = rcUsid;
	}

	public Date getRcDt() {
		return rcDt;
	}

	public void setRcDt(Date rcDt) {
		this.rcDt = rcDt;
	}

	public String getOdUsid() {
		return odUsid;
	}

	public void setOdUsid(String odUsid) {
		this.odUsid = odUsid;
	}

	public Date getOdDt() {
		return odDt;
	}

	public void setOdDt(Date odDt) {
		this.odDt = odDt;
	}

	public String getWkUsid() {
		return wkUsid;
	}

	public void setWkUsid(String wkUsid) {
		this.wkUsid = wkUsid;
	}

	public String getWkName() {
		return wkName;
	}

	public void setWkName(String wkName) {
		this.wkName = wkName;
	}

	public Date getWkDt() {
		return wkDt;
	}

	public void setWkDt(Date wkDt) {
		this.wkDt = wkDt;
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
	
}
