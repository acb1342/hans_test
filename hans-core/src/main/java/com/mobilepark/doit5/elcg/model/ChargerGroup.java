package com.mobilepark.doit5.elcg.model;

import java.io.Serializable;
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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Formula;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.model
 * @Filename     : ChargerGroup.java
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
@Table(name = "TB_ELCG_CHARGER_GROUP")
public class ChargerGroup extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 6966185402084351008L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CHARGER_GROUP_ID", nullable = false)
	private Long chargerGroupId;
	
	@JsonBackReference
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumn(name = "BD_ID")
	private Bd bd;
	
	@Column(name = "BD_ID", insertable = false, updatable = false)
	private Long bdId;
	
	@Column(name = "BD_GROUP_ID")
	private Long bdGroupId;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "CAPACITY")
	private Long capacity;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "DESCRIPTION")
	private String description;
	
	@Column(name = "FST_RG_USID", nullable = false)
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT", nullable = false)
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	@OneToMany(cascade = { CascadeType.ALL }, orphanRemoval = true)
	@JoinColumn(name = "CHARGER_GROUP_ID", updatable = false)
	private List<Charger> chargerList = new ArrayList<Charger>();
	
	@Transient
	int chargerSize;
	
	//@Transient
	//private Long chargerCount;
	
	@Override
	public Long getId() {
		return chargerGroupId;
	}


	public Long getChargerGroupId() {
		return chargerGroupId;
	}


	public void setChargerGroupId(Long chargerGroupId) {
		this.chargerGroupId = chargerGroupId;
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


	public List<Charger> getChargerList() {
		return chargerList;
	}


	public void setChargerList(List<Charger> chargerList) {
		this.chargerList = chargerList;
	}

	
	public Long getBdGroupId() {
		return bdGroupId;
	}


	public void setBdGroupId(Long bdGroupId) {
		this.bdGroupId = bdGroupId;
	}


	public Bd getBd() {
		return bd;
	}


	public void setBd(Bd bd) {
		this.bd = bd;
	}

	public Long getBdId() {
		return bdId;
	}

	public void setBdId(Long bdId) {
		this.bdId = bdId;
	}


	public int getChargerSize() {
		int count = 0;
		for(Charger charger : this.chargerList) {
			if(charger == null) continue;
			count++;
		}
		return count;
	}
	
	public void setChargerSize() {
		this.chargerSize = getChargerSize();
	}
	
//	public long getChargerCount() {
//		return chargerCount;
//	}
//
//	public void setChargerCount(long chargerCount) {
//		this.chargerCount = chargerCount;
//	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Formula("(SELECT ebg.NAME FROM TB_ELCG_BD_GROUP ebg WHERE BD_GROUP_ID = ebg.BD_GROUP_ID)")
	private String bdGroupName;
}
