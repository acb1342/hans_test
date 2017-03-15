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
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : ChargerIftmp.java
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
@Table(name = "TB_ELCG_CHARGER_IFTMP")
public class ChargerIftmp extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 954517017871448568L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long snId;

	@Column(name = "CHARGER_ID")
	private String chargerId;
	
	@OneToOne
	@JoinColumn(name = "CHARGER_ID", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Charger charger;
	
	@Column(name = "TRANS_TYPE")
	private String transType;
	
	@Column(name = "USID")
	private Long usid;
	
	@Column(name = "CMD")
	private String cmd;
	
	@Column(name = "MSG")
	private String msg;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "FST_RG_USID", nullable = false)
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT", nullable = false)
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	@Transient
	String fromDate;
	
	@Transient
	String toDate;
	
	@Override
	public Long getId() {
		return snId;
	}

	public Long getSnId() {
		return snId;
	}

	public Charger getCharger() {
		return charger;
	}

	public void setCharger(Charger charger) {
		this.charger = charger;
	}

	public String getTransType() {
		return transType;
	}

	public void setTransType(String transType) {
		this.transType = transType;
	}

	public Long getUsid() {
		return usid;
	}

	public void setUsid(Long usid) {
		this.usid = usid;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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

	public void setSnId(Long snId) {
		this.snId = snId;
	}

	public String getFromDate() {
		return changeFormat(fromDate, 12);
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return changeFormat(toDate, 12);
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getChargerId() {
		return chargerId;
	}

	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}

	public String changeFormat(String date, int strNumber) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, strNumber);
			TraceLog.debug("date format : " + date);
			return date;
		}
		return null;
	}
}
