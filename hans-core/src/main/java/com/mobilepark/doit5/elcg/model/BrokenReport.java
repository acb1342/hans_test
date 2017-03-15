package com.mobilepark.doit5.elcg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.model
 * @Filename     : BrokenReport.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TB_ELCG_BROKEN_REPORT")
public class BrokenReport extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 358431352392436089L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long snId;

	@Column(name = "BD_GROUP_ID")
	private Long bdGroupId;
	
	@OneToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "BD_ID")
	private Bd bd;
	
	@Formula("CASE WHEN ISNULL(BD_ID) THEN BODY ELSE (SELECT bd.NAME FROM TB_ELCG_BD bd WHERE BD_ID = bd.BD_ID) END")
	private String title;
	
	@Formula("(SELECT ebg.NAME FROM TB_ELCG_BD_GROUP ebg WHERE ebg.BD_GROUP_ID = BD_GROUP_ID)")
	private String bdGroupName;

	@Formula("(SELECT ecg.NAME FROM TB_ELCG_CHARGER_GROUP ecg WHERE ecg.CHARGER_GROUP_ID = (SELECT ec.CHARGER_GROUP_ID FROM TB_ELCG_CHARGER ec WHERE ec.CHARGER_ID = CHARGER_ID))")
	private String chargerGroupNm;
	
	@Formula("(SELECT ec.MGMT_NO FROM TB_ELCG_CHARGER ec WHERE CHARGER_ID = ec.CHARGER_ID)")
	private String mgmtNo;
	
	@Formula("(SELECT ma.NAME FROM TB_MGMT_ADMIN ma WHERE ma.ADMIN_ID = WK_USID)")
	private String wkUsNm;
	
	@Formula("(SELECT eb.ADMIN_ID FROM TB_ELCG_BD eb WHERE eb.BD_ID = BD_ID)")
	private String adminId;
	
	@Column(name = "CHARGER_ID")
	private String chargerId;
	
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
	
	@Column(name = "CHAN")
	private String chan;

	@Override
	public Long getId() {
		return snId;
	}


	public Long getSnId() {
		return snId;
	}

	public void setSnId(Long snId) {
		this.snId = snId;
	}

	public String getChargerId() {
		return chargerId;
	}

	public Bd getBd() {
		return bd;
	}

	public void setBd(Bd bd) {
		this.bd = bd;
	}

	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
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

	public String getChan() {
		return chan;
	}

	public void setChan(String chan) {
		this.chan = chan;
	}

	public Long getBdGroupId() {
		return bdGroupId;
	}

	public void setBdGroupId(Long bdGroupId) {
		this.bdGroupId = bdGroupId;
	}


}
