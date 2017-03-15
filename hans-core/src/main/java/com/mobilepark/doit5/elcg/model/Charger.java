package com.mobilepark.doit5.elcg.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : Charger.java
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
@Table(name = "TB_ELCG_CHARGER")
public class Charger extends AbstractModel<String> implements Serializable {
	
	private static final long serialVersionUID = 5242828967506770144L;

	@Id
	@Column(name = "CHARGER_ID")
	private String chargerId;
	
	@Column(name="MGMT_NO")
	private String mgmtNo;
	
	@OneToOne
	@JoinColumn(name = "CHARGER_ID", nullable=true)
	private ChargerList chargerList;
	
	@JsonBackReference
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "CHARGER_GROUP_ID")
	private ChargerGroup chargerGroup; 
	
	@Column(name = "CHARGER_GROUP_ID", insertable = false, updatable = false)
	private Long chargerGroupId;
	
	/*@Formula("(SELECT ecl.MGMT_NO FROM TB_ELCG_CHARGER_LIST ecl WHERE CHARGER_ID = ecl.CHARGER_ID)")
	private String mgmtNo;*/
	
	@Formula("(SELECT ecl.SERIAL_NO FROM TB_ELCG_CHARGER_LIST ecl WHERE CHARGER_ID = ecl.CHARGER_ID)")
	private String serialNo;
	
	@Formula("(SELECT eb.NAME FROM TB_ELCG_BD eb WHERE eb.BD_ID = any(SELECT ecg.BD_ID FROM TB_ELCG_CHARGER_GROUP ecg WHERE ecg.CHARGER_GROUP_ID = CHARGER_GROUP_ID))")
	private String bdName;
	
	@Formula("(SELECT eb.NAME FROM TB_ELCG_BD_GROUP eb WHERE eb.BD_GROUP_ID = any(SELECT ecg.BD_GROUP_ID FROM TB_ELCG_CHARGER_GROUP ecg WHERE ecg.CHARGER_GROUP_ID = CHARGER_GROUP_ID))")
	private String bdGroupName;
	
	@Formula("(SELECT ecm.MODEL FROM TB_ELCG_CHARGER_MODEL ecm WHERE ecm.MODEL_ID = (SELECT ecl.MODEL_ID FROM TB_ELCG_CHARGER_LIST ecl WHERE ecl.CHARGER_ID = CHARGER_ID))")
	private String modelName;
	
	@Formula("(SELECT eb.ADDR FROM TB_ELCG_BD eb WHERE eb.BD_ID = any(SELECT ecg.BD_ID FROM TB_ELCG_CHARGER_GROUP ecg WHERE ecg.CHARGER_GROUP_ID = CHARGER_GROUP_ID))")
	private String bdAddr;
	
	@Formula("(SELECT ma.NAME FROM TB_MGMT_ADMIN ma WHERE ma.ADMIN_ID = FST_RG_USID AND ma.ADMIN_GROUP_ID = 2)")
	private String wkName;
	
	@Formula("(SELECT si.WK_NAME FROM TB_STAT_INSTALLER si WHERE CHARGER_ID = si.CHARGER_ID LIMIT 1 )")
	private String wkNameOfChargerId;

	@Formula("(SELECT ma.NAME FROM TB_MGMT_ADMIN ma WHERE ma.ADMIN_ID = FST_RG_USID)")
	private String adminName;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CAPACITY")
	private Long capacity;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "CHARGE_RATE")
	private String chargeRate;
	
	@Column(name = "TP_PASSCODE")
	private String tpPasscode;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DETAIL_STATUS")
	private String detailStatus;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT")
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	@Transient
	String fromDate;
	
	@Transient
	String toDate;
	
	@Transient
	String isSelectBox;
	
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


	public ChargerGroup getChargerGroup() {
		return chargerGroup;
	}


	public void setChargerGroup(ChargerGroup chargerGroup) {
		this.chargerGroup = chargerGroup;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
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


	public void setChargeRate(String chargerRate) {
		this.chargeRate = chargerRate;
	}


	public String getTpPasscode() {
		return tpPasscode;
	}

	public void setTpPasscode(String tpPasscode) {
		this.tpPasscode = tpPasscode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDetailStatus() {
		return detailStatus;
	}


	public void setDetailStatus(String detailStatus) {
		this.detailStatus = detailStatus;
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

	public ChargerList getChargerList() {
		return chargerList;
	}


	public void setChargerList(ChargerList chargerList) {
		this.chargerList = chargerList;
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


	public String getBdName() {
		return bdName;
	}


	public void setBdName(String bdName) {
		this.bdName = bdName;
	}


	public String getBdAddr() {
		return bdAddr;
	}


	public void setBdAddr(String bdAddr) {
		this.bdAddr = bdAddr;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsSelectBox() {
		return isSelectBox;
	}

	public void setIsSelectBox(String isSelectBox) {
		this.isSelectBox = isSelectBox;
	}

	public String getFromDate() {
		return changeFormat(fromDate);
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		
		return changeFormat(toDate);
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	public String getWkNameOfChargerId() {
		return wkNameOfChargerId;
	}
	
	public void setWkNameOfChargerId(String wkNameOfChargerId) {
		this.wkNameOfChargerId = wkNameOfChargerId;
	}
	
	public String getAdminName() {
		return adminName;
	}
	
	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public Long getChargerGroupId() {
		return chargerGroupId;
	}

	public void setChargerGroupId(Long chargerGroupId) {
		this.chargerGroupId = chargerGroupId;
	}

	public String getWkName() {
		return wkName;
	}

	public void setWkName(String wkName) {
		this.wkName = wkName;
	}


	public String changeFormat(String date) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, 8);
			return date;
		}
		return null;
	}

}
