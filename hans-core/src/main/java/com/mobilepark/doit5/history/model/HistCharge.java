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
 * @Filename     : HistCharge.java
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
@Table(name = "TB_HIST_CHARGE")
public class HistCharge extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -5004337749794017641L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long id;

	@Column(name = "FST_RG_DT")
	private Date fstRgDt;

	@Column(name = "USID")
	private Long usid;
	
	@Column(name = "CUST_NAME")
	private String custName;

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
	
	@Column(name = "CHARGE_RATE")
	private String chargeRate;

	@Column(name = "CERT_TYPE")
	private String certType;
	
	@Column(name = "CERT_CARD_NO")
	private String certCardNo;
	
	@Column(name = "CHARGE_AMT")
	private Double chargeAmt;
	
	@Column(name = "START_DT")
	private Date startDt;
	
	@Column(name = "END_DT")
	private Date endDt;
	
	@Column(name = "CHARGE_TIME")
	private Long chargeTime;
	
	@Column(name = "CHARGE_FEE")
	private Double chargeFee;
	
	@Column(name = "PAYMENT_YN")
	private String paymentYn;
	
	@Column(name = "PAYMENT_DT")
	private String paymentDt;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getCustName() {
		return custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
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

	public String getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertCardNo() {
		return certCardNo;
	}

	public void setCertCardNo(String certCardNo) {
		this.certCardNo = certCardNo;
	}

	public Double getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Double chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Date getEndDt() {
		return endDt;
	}

	public void setEndDt(Date endDt) {
		this.endDt = endDt;
	}

	public Long getChargeTime() {
		return chargeTime;
	}

	public void setChargeTime(Long chargeTime) {
		this.chargeTime = chargeTime;
	}

	public Double getChargeFee() {
		return chargeFee;
	}

	public void setChargeFee(Double chargeFee) {
		this.chargeFee = chargeFee;
	}

	public String getPaymentYn() {
		return paymentYn;
	}

	public void setPaymentYn(String paymentYn) {
		this.paymentYn = paymentYn;
	}

	public String getPaymentDt() {
		return paymentDt;
	}

	public void setPaymentDt(String paymentDt) {
		this.paymentDt = paymentDt;
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
