package com.hans.sses.admin.model;

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

//import org.codehaus.jackson.annotate.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.model
 * @Filename     : Menu.java
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
@Table(name = "TBL_MENU")
public class Menu extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MENU_SEQ", nullable = false)
	private Integer id;

	@Column(name = "PARENT_SEQ")
	private Integer parentId;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "SORT")
	private Integer sort;

	@Column(name = "URL")
	private String url;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "REG_DATE")
	private Date fstRgDt;

	@Column(name = "MOD_DATE")
	private Date lstChDt;

	@OneToMany(mappedBy = "menu", cascade = {
		CascadeType.ALL
	}, orphanRemoval = true)
	private final List<MenuFunc> functions = new ArrayList<MenuFunc>();

	@Override
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
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

	@JsonIgnore
	public List<MenuFunc> getFunctions() {
		return functions;
	}

}
