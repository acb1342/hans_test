package com.mobilepark.doit5.statistics.model;

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
 * @Package      : com.mobilepark.doit5.statistics.model
 * @Filename     : ChargeDay.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 12. 15.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TB_STAT_CHARGE_DAY")
public class ChargeDay extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -7088951588920450013L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long id;

	@Column(name = "CHARGE_YMD")
	private String chargeYmd;

	@Column(name = "BD_GROUP_ID")
	private Long bdGroupId;

	@Column(name = "BD_GROUP_NAME")
	private String bdGroupName;

	@Column(name = "BD_ID")
	private Long bdId;
	
	@Column(name = "BD_NAME")
	private String bdName;
	
	@Column(name = "CHARGER_GROUP_ID")
	private Long chargerGroupid;
	
	@Column(name = "CHARGER_GROUP_NAME")
	private String chargerGroupName;
	
	@Column(name = "CHARGER_ID")
	private String chargerId;
	
	@Column(name = "CHARGER_NAME")
	private String chargerName;
	
	@Column(name = "CHARGE_AMT")
	private Double chargeAmt;
	
	@Column(name = "CHARGE_TIME")
	private Long chargeTime;
	
	@Column(name = "CHARGE_CNT")
	private Long chargeCnt;
	
	@Column(name = "CHARGE_FEE")
	private Long chargeFee;

	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT")
	private Date fstRgDt;

	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getChargeYmd() {
		return chargeYmd;
	}

	public void setChargeYmd(String chargeYmd) {
		this.chargeYmd = chargeYmd;
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

	public Long getChargerGroupid() {
		return chargerGroupid;
	}

	public void setChargerGroupid(Long chargerGroupid) {
		this.chargerGroupid = chargerGroupid;
	}

	public String getChargerGroupName() {
		return chargerGroupName;
	}

	public void setChargerGroupName(String chargerGroupName) {
		this.chargerGroupName = chargerGroupName;
	}

	public String getChargerId() {
		return chargerId;
	}

	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}

	public String getChargerName() {
		return chargerName;
	}

	public void setChargerName(String chargerName) {
		this.chargerName = chargerName;
	}

	public Double getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Double chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Long getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Long chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Long getChargeCnt() {
		return chargeCnt;
	}

	public void setChargeCnt(Long chargeCnt) {
		this.chargeCnt = chargeCnt;
	}

	public Long getChargeFee() {
		return chargeFee;
	}

	public void setChargeFee(Long chargeFee) {
		this.chargeFee = chargeFee;
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
