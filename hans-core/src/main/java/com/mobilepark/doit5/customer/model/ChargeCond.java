package com.mobilepark.doit5.customer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.model
 * @Filename     : ChangeCond.java
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
@Table(name = "TB_CUST_CHARGE_COND")
public class ChargeCond extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 6267846608969019293L;

	/**
	 * TB_CUST_MEMBER 와 일대일
	 */
	@Id
	@Column(name = "USID")
	private Long id;
	
	@Column(name = "CHARGE_COND")
	private String chargeCond;
	
	@Column(name = "WATT")
	private Long watt;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;

	@Column(name = "FST_RG_DT")
	private Date fstRgDt;

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
