package com.mobilepark.doit5.elcg.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : Bd.java
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
@Table(name = "TB_ELCG_BD")
public class Bd extends AbstractModel<Long> implements Serializable {
	
	private static final long serialVersionUID = 3116423010086108908L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "BD_ID")
	private Long bdId;

	@JsonBackReference
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "BD_GROUP_ID")
	private BdGroup bdGroup;
	
	@OneToMany
	@JoinColumn(name = "BD_ID")
	@JsonIgnore
	private List<BrokenReport> brokenReportList;
	
	@OneToMany
	@JoinColumn(name = "BD_ID")
	@JsonIgnore
	private List<StationApplication> stationApplicationList;
	
	@Column(name = "ADMIN_ID")
	private String adminId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "ZIPCODE")
	private String zipcode;
	
	@Column(name = "ADDR")
	private String addr;
	
	@Column(name = "LATITUDE")
	private BigDecimal latitude;
	
	@Column(name = "LONGITUDE")
	private BigDecimal longitude;
	
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
	private String favoriteYn = "N";
	
	@JsonBackReference
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "BD_ID", updatable = false)
	private List<ChargerGroup> chargerGroupList = new ArrayList<ChargerGroup>();
	
	@Transient
	int chargerGroupSize;
	
	@Transient
	int chargerSize;
	
	// 최종 설치일
	@Transient
	String lstInsDate;
	
	// 최종 설치자
	@Transient
	String lstInstaller;
	
	@Override
	public Long getId() {	
		return bdId;
	}

	public Long getBdId() {
		return bdId;
	}

	public void setBdId(Long bdId) {
		this.bdId = bdId;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public BigDecimal getLatitude() {
		return latitude;
	}

	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}

	public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
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

	public BdGroup getBdGroup() {
		return bdGroup;
	}

	public void setBdGroup(BdGroup bdGroup) {
		this.bdGroup = bdGroup;
	}

	public String getFavoriteYn() {
		return favoriteYn;
	}

	public void setFavoriteYn(String favoriteYn) {
		this.favoriteYn = favoriteYn;
	}

	public List<ChargerGroup> getChargerGroupList() {
		return chargerGroupList;
	}

	public void setChargerGroupList(List<ChargerGroup> chargerGroupList) {
		this.chargerGroupList = chargerGroupList;
	}

	public int getChargerGroupSize() {
		return this.chargerGroupList.size();
	}
	
	public void setChargerGroupSize() {
		this.chargerGroupSize = getChargerGroupSize();
	}
	
	public int getChargerSize() {
		int count=0;
		for(ChargerGroup chargerGroup : this.chargerGroupList) {
			count += chargerGroup.getChargerSize();
		}
		return count;
	}
		
	public int setChargerSize() {
		return this.chargerSize = getChargerSize();
	}
	
	// 충전기 설치된 건물수
	public int getTotalBdCnt() {
		List<ChargerGroup> chargerGroups = this.chargerGroupList;
		List<Long> checkId = new ArrayList<Long>();
		
		if(chargerGroups.size() == 0 || chargerGroups == null) return 0;
		
		for(ChargerGroup chargerGroup : chargerGroups) {	
			
			if(checkId.size() != 0 && checkId.contains(chargerGroup.getId())) return 0;
			 
			if(checkId.size() == 0 || !checkId.contains(chargerGroup.getId())) {
				
				if(chargerGroup.getChargerList().size() > 0) {
				checkId.add(chargerGroup.getId());
				return 1;
				}
				
			}
		}
		
		return 0;
	}
	
	public List<BrokenReport> getBrokenReportList() {
		return brokenReportList;
	}

	public void setBrokenReportList(List<BrokenReport> brokenReportList) {
		this.brokenReportList = brokenReportList;
	}

	public List<StationApplication> getStationApplicationList() {
		return stationApplicationList;
	}

	public void setStationApplicationList(List<StationApplication> stationApplicationList) {
		this.stationApplicationList = stationApplicationList;
	}
	
	// 최종 설치 충전기 정보
	public void setLstInstall() {
		Long max = 0l;
		Long tmp;
		DateFormat form = new SimpleDateFormat("yyyyMMddHHmm");
		
		for (ChargerGroup chargerGroup : this.chargerGroupList) {
			
			if (chargerGroup == null || chargerGroup.getChargerList().size() < 1) continue;
			
			for (Charger charger : chargerGroup.getChargerList()) {
				
				if (charger == null) continue;
				
				tmp = Long.parseLong((changeFormat(form.format(charger.getFstRgDt()), 12)));
				if (max < tmp) {
					max = tmp;
					this.lstInstaller = charger.getWkName();
					this.lstInsDate = changeFormat(form.format(charger.getFstRgDt()), 12);
				}
			}
		}
	}
	
	public String getLstInsDate() {
		return lstInsDate;
	}

	public String getLstInstaller() {
		return lstInstaller;
	}

	private String changeFormat(String date, int strNumber) {
		if(StringUtils.isNotEmpty(date)) {
			String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
			date = date.replaceAll(match, "");
			date = date.replaceAll("\\s", "");
			date = date.substring(0, strNumber);
			
			return date;
		}
		return null;
	}
	
	@Formula("(SELECT COUNT(*) FROM TB_ELCG_CHARGER_GROUP ecg WHERE BD_ID = ecg.BD_ID)")
	private Integer chargerGroupCnt;
	
	@Formula("(SELECT COUNT(*) FROM TB_ELCG_CHARGER_GROUP ecg INNER JOIN TB_ELCG_CHARGER ec ON ec.CHARGER_GROUP_ID = ecg.CHARGER_GROUP_ID WHERE BD_ID = ecg.BD_ID)")
	private Integer chargerCnt;
	
	@Formula("(SELECT bsc.PERIOD_DAY FROM TB_BILL_BD_PERIOD bsc WHERE BD_ID = bsc.BD_ID ORDER BY FST_RG_DT DESC LIMIT 1)")
	private String periodDay;
	
	@Formula("(SELECT bsc.SN_ID FROM TB_BILL_BD_PERIOD bsc WHERE BD_ID = bsc.BD_ID ORDER BY FST_RG_DT DESC LIMIT 1)")
	private String periodSnId;
	
	@Formula("(SELECT ma.NAME FROM TB_MGMT_ADMIN ma WHERE ADMIN_ID = ma.ADMIN_ID)")
	private String adminName;
	
	
}
