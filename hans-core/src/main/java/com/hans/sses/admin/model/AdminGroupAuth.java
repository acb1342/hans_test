package com.hans.sses.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.model
 * @Filename     : AdminGroupAuth.java
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
@Table(name = "TBL_ADMIN_GROUPAUTH")
public class AdminGroupAuth extends AbstractModel<Integer> implements Serializable {
	@Id
	@Column(name = "SEQ", nullable = false)
	private int id;

	@Column(name= "MENU_SEQ")
	private int menuSeq;
	
	@Column(name = "ADMIN_GROUP_SEQ")
	private int adminGroupSeq;
	
	@Column(name = "AUTH", nullable = false)
	private String auth;
	
	@Column(name = "REG_DATE")
	private Date regDate;

	@Column(name = "MOD_DATE")
	private Date modDate;

	public Integer getId() { return this.id;}
	public void setId(int id) { this.id = id; }

	public Integer getMenuSeq() { return menuSeq; }

	public void setMenuSeq(int menuSeq) { this.menuSeq = menuSeq; }

	public String getAuth() {
		return this.auth;
	}

	public void setAuth(String auth) {
		this.auth = auth;
	}

	public Integer getAdminGroupSeq() {
		return adminGroupSeq;
	}

	public void setAdminGroupSeq(Integer adminGroupSeq) {
		this.adminGroupSeq = adminGroupSeq;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

}
