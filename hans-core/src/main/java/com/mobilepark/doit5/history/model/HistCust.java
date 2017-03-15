package com.mobilepark.doit5.history.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.history.model
 * @Filename     : HistCust.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 12. 05.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TB_HIST_CUST")
public class HistCust extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -7983960410995770826L;

	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long snId;

	@Column(name = "FST_RG_DT")
	private Date fstRgDt;

	@Column(name = "USID")
	private Long usid;
	
	@Column(name = "ACCESS_DEVICE")
	private String accessDevice;

	@Column(name = "LOG_TYPE")
	private String logType;

	@Column(name = "RESULT_CODE")
	private String resultCode;

	@Column(name = "CERT_CARD_NO")
	private String certCardNo;
	
	@Column(name = "CHARGER_ID")
	private String chargerId;
	
	@Column(name = "ADD_INFO")
	private String addInfo;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	
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

	public Date getFstRgDt() {
		return fstRgDt;
	}

	public void setFstRgDt(Date fstRgDt) {
		this.fstRgDt = fstRgDt;
	}

	public Long getUsid() {
		return usid;
	}

	public void setUsid(Long usid) {
		this.usid = usid;
	}

	public String getAccessDevice() {
		return accessDevice;
	}

	public void setAccessDevice(String accessDevice) {
		this.accessDevice = accessDevice;
	}

	public String getLogType() {
		return logType;
	}

	public void setLogType(String logType) {
		this.logType = logType;
	}

	public String getResultCode() {
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getCertCardNo() {
		return certCardNo;
	}

	public void setCertCardNo(String certCardNo) {
		this.certCardNo = certCardNo;
	}

	public String getChargerId() {
		return chargerId;
	}

	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}

	public String getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	public String getFstRgUsid() {
		return fstRgUsid;
	}

	public void setFstRgUsid(String fstRgUsid) {
		this.fstRgUsid = fstRgUsid;
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
