package com.mobilepark.doit5.provider.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.model
 * @Filename     : ContentProvider.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 2.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "tbl_cp", catalog = "upush")
public class ContentProvider extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "cp_sn", unique = true, nullable = false)
	private Integer id;

	@Column(name = "cp_id", length = 32)
	private String cpId;

	@Column(name = "cp_passwd", length = 32)
	private String cpPasswd;

	@Column(name = "cp_name", length = 256)
	private String cpName;

	@Column(name = "cp_phone", length = 32)
	private String phone;

	@Column(name = "cp_email", length = 128)
	private String email;

	public ContentProvider() {
	}

	public ContentProvider(String cpId, String cpPasswd, String cpName) {
		this.cpId = cpId;
		this.cpPasswd = cpPasswd;
		this.cpName = cpName;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCpId() {
		return this.cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getCpPasswd() {
		return this.cpPasswd;
	}

	public void setCpPasswd(String cpPasswd) {
		this.cpPasswd = cpPasswd;
	}

	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
