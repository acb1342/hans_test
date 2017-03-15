package com.mobilepark.doit5.elcg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : ChargeStatus.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 12. 19.       최초 버전
 * =================================================================================*/

@Entity
@Table(name = "TB_ELCG_CHARGE_STATUS")
public class ChargeStatus extends AbstractModel<String> implements Serializable {
	
	private static final long serialVersionUID = 1845126336654770809L;
	
	@Id
	@Column(name = "CHARGER_ID")
	private String id;
	
	@Column(name = "USID")
	private Long usid;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "START_DT")
	private Date startDt;
	
	@Column(name = "CHARGE_AMT")
	private Double chargeAmt;
	
	@Column(name = "CHARGE_FEE")
	private Long chargeFee;
	
	@Column(name = "CHARGE_COND")
	private String chargeCond;
	
	@Column(name = "WATT")
	private Long watt;
	
	@Column(name = "FST_RG_USID", nullable = false)
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT", nullable = false)
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getUsid() {
		return usid;
	}

	public void setUsid(Long usid) {
		this.usid = usid;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getStartDt() {
		return startDt;
	}

	public void setStartDt(Date startDt) {
		this.startDt = startDt;
	}

	public Double getChargeAmt() {
		return chargeAmt;
	}

	public void setChargeAmt(Double chargeAmt) {
		this.chargeAmt = chargeAmt;
	}

	public Long getChargeFee() {
		return chargeFee;
	}

	public void setChargeFee(Long chargeFee) {
		this.chargeFee = chargeFee;
	}

	public String getChargeCond() {
		return chargeCond;
	}

	public void setChargeCond(String chargeCond) {
		this.chargeCond = chargeCond;
	}

	public Long getWatt() {
		return watt;
	}

	public void setWatt(Long watt) {
		this.watt = watt;
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
