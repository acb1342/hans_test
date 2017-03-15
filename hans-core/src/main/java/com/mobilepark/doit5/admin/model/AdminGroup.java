package com.mobilepark.doit5.admin.model;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.model
 * @Filename     : AdminGroup.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 22.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TB_MGMT_ADMIN_GROUP")
public class AdminGroup extends AbstractModel<Integer> implements Serializable {

	private static final long serialVersionUID = 665684829828231205L;
	
	public static final String ADMIN_GROUP_NAME = "ADMIN";
	public static final String CP_GROUP_NAME = "CP";
	public static final String MCP_GROUP_NAME = "MCP";
	public static final String CP_MCP_GROUP_NAME = "MCP&CP";
	public static final String SELLER_GROUP_NAME = "SELLER";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ADMIN_GROUP_ID", nullable = false)
	private Integer id;

	@Column(name = "NAME")
	private String name;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "FST_RG_USID")
	private String fstRgUsid;

	@Column(name = "FST_RG_DT")
	private Date fstRgDt;

	@Column(name = "LST_CH_USID")
	private String lstChUsid;

	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	@OneToMany(cascade = { CascadeType.ALL }, mappedBy = "adminGroup", orphanRemoval = true)
	private List<Admin> users = new ArrayList<Admin>();

	@Override
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public List<Admin> getUsers() {
		return users;
	}

	public void setUsers(List<Admin> users) {
		this.users = users;
	}
}
