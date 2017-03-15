package com.mobilepark.doit5.elcg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : ChargerList.java
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
@Table(name = "TB_ELCG_CHARGER_LIST")
public class ChargerList extends AbstractModel<String> implements Serializable {

	private static final long serialVersionUID = 7032299069770423085L;

	@Id
	@Column(name = "CHARGER_ID", nullable = false)
	private String chargerId;
	
	@OneToOne
	@JoinColumn(name = "MODEL_ID", nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private ChargerModel model;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "MGMT_NO")
	private String mgmtNo;
	
	@Column(name = "SERIAL_NO")
	private String serialNo;
	
	@Column(name = "TP_PASSCODE")
	private String tpPasscode;
	
	@Column(name = "CAPACITY")
	private Long capacity;
	
	@Column(name = "CHARGE_RATE")
	private String chargeRate;
	
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
		return chargerId;
	}

	public String getChargerId() {
		return chargerId;
	}

	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}

	public ChargerModel getModel() {
		return model;
	}

	public void setModel(ChargerModel model) {
		this.model = model;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMgmtNo() {
		return mgmtNo;
	}

	public void setMgmtNo(String mgmtNo) {
		this.mgmtNo = mgmtNo;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getTpPasscode() {
		return tpPasscode;
	}

	public void setTpPasscode(String tpPasscode) {
		this.tpPasscode = tpPasscode;
	}

	public Long getCapacity() {
		return capacity;
	}

	public void setCapacity(Long capacity) {
		this.capacity = capacity;
	}

	public String getChargeRate() {
		return chargeRate;
	}

	public void setChargeRate(String chargeRate) {
		this.chargeRate = chargeRate;
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
