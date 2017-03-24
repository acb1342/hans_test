package com.mobilepark.doit5.admin.model;

import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.model
 * @Filename     : CmsMenuFunction.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 5.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TBL_MENU_FUNC")
public class MenuFunc extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "FUNC_SEQ", nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "MENU_SEQ", nullable = false)
	private Menu menu;
	
	@Transient
	private int menuId;
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public int getMenuId() {
		return this.menuId;
	}
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "URL")
	private String url;

	@Column(name = "AUTH")
	private String auth;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "REG_DATE")
	private Date fstRgDt;

	@Column(name = "MOD_DATE")
	private Date lstChDt;

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAuth() {
		return auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getFstRgDt() {
		return fstRgDt;
	}

	public void setFstRgDt(Date fstRgDt) {
		this.fstRgDt = fstRgDt;
	}

	public Date getLstChDt() {
		return lstChDt;
	}

	public void setLstChDt(Date lstChDt) {
		this.lstChDt = lstChDt;
	}
}
