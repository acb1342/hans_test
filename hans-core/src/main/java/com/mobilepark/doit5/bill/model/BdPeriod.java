package com.mobilepark.doit5.bill.model;

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
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.bill.model
 * @Filename     : BdCalcday.java
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
@Table(name = "TB_BILL_BD_PERIOD")
public class BdPeriod extends AbstractModel<Long> implements Serializable {
	
	private static final long serialVersionUID = -2044361489497645407L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long snId;
	
	@Column(name = "BD_ID")
	private Long bdId;
	
	@Column(name = "START_YMD")
	private String startYmd;
	
	@Column(name = "END_YMD")
	private String endYmd;
	
	@Column(name = "METER_YMD")
	private String meterYmd;
	
	@Column(name = "PERIOD_YMD")
	private String periodYmd;
	
	@Column(name = "PERIOD_DAY")
	private String periodDay;
	
	@Column(name = "FST_RG_USID", nullable = false)
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT", nullable = false)
	private Date fstRgDt;
	
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

	public Long getBdId() {
		return bdId;
	}

	public void setBdId(Long bdId) {
		this.bdId = bdId;
	}

	public String getStartYmd() {
		return startYmd;
	}

	public void setStartYmd(String startYmd) {
		this.startYmd = startYmd;
	}

	public String getEndYmd() {
		return endYmd;
	}

	public void setEndYmd(String endYmd) {
		this.endYmd = endYmd;
	}

	public String getMeterYmd() {
		return meterYmd;
	}

	public void setMeterYmd(String meterYmd) {
		this.meterYmd = meterYmd;
	}

	public String getPeriodYmd() {
		return periodYmd;
	}

	public void setPeriodYmd(String periodYmd) {
		this.periodYmd = periodYmd;
	}

	public String getPeriodDay() {
		return periodDay;
	}

	public void setPeriodDay(String periodDay) {
		this.periodDay = periodDay;
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
