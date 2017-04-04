package com.hans.sses.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

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
public class AdminGroupAuth extends AbstractModel<AdminGroupAuth.ID> {
	public static class ID implements Serializable {
		private static final long serialVersionUID = -8380663686839294095L;

		private int adminGroupSeq;

		private int menuSeq;

		public ID() {
			super();
		}

		public ID(int adminGroupSeq, int menuSeq) {
			this.adminGroupSeq = adminGroupSeq;
			this.menuSeq = menuSeq;
		}

		public int getAdminGroupSeq() {
			return this.adminGroupSeq;
		}

		public int getMenuSeq() {
			return this.menuSeq;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && this.getClass().equals(obj.getClass())) {
				ID id = (ID) obj;
				return (this.adminGroupSeq == id.adminGroupSeq && this.menuSeq == id.menuSeq);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return this.adminGroupSeq + this.menuSeq;
		}
	}

	public AdminGroupAuth() {
		super();
	}

	public AdminGroupAuth(int groupSeq, int menuSeq) {
		super();
		this.id = new ID(groupSeq, menuSeq);
	}

	public AdminGroupAuth(int groupSeq, int menuSeq, String auth) {
		super();
		this.id = new ID(groupSeq, menuSeq);
		this.auth = auth;
	}

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "adminGroupSeq", column = @Column(name = "ADMIN_GROUP_SEQ")),
		@AttributeOverride(name = "menuSeq", column = @Column(name = "MENU_SEQ"))
	})
	private ID id;

	@Column(name= "MENU_SEQ", insertable = false, updatable = false)
	private Integer menuSeq;
	
	@Column(name = "ADMIN_GROUP_SEQ", insertable = false, updatable = false)
	private Integer adminGroupSeq;
	
	@Column(name = "AUTH", nullable = false)
	private String auth;
	
	@Column(name = "REG_DATE")
	private Date regDate;

	@Column(name = "MOD_DATE")
	private Date modDate;

	@Override
	public ID getId() {
		return this.id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Integer getGroupSeq() {
		if (this.id != null) {
			return this.id.getAdminGroupSeq();
		} else {
			return this.adminGroupSeq;
		}
	}

	public Integer getMenuSeq() {
		if (this.id != null) {
			return this.id.getMenuSeq();
		} else {
			return this.menuSeq;
		}
	}

	public String getAuth() {
		return this.auth;
	}

	public void setAuth(String authLevel) {
		this.auth = authLevel;
	}

	public String getAuthString() {
		String authString = null;
		if (this.auth == null || this.auth.indexOf("N") > -1 || this.auth.length() == 0) {
			authString = "No auth";
		} else {
			if (this.auth.indexOf("R") > -1) {
				authString = "Read";
			}
			if (this.auth.indexOf("C") > -1) {
				if (authString != null) {
					authString += "|";
				}
				authString += "Create";

			}
			if (this.auth.indexOf("U") > -1) {
				if (authString != null) {
					authString += "|";
				}
				authString += "Update";

			}
			if (this.auth.indexOf("D") > -1) {
				if (authString != null) {
					authString += "|";
				}
				authString += "Delete";
			}
			if (this.auth.indexOf("A") > -1) {
				if (authString != null) {
					authString += "|";
				}
				authString += "Approve";
			}
		}
		return authString;
	}

	public Integer getAdminGroupSeq() {
		return adminGroupSeq;
	}

	public void setAdminGroupSeq(Integer adminGroupSeq) {
		this.adminGroupSeq = adminGroupSeq;
	}

	public void setMenuSeq(Integer menuSeq) {
		this.menuSeq = menuSeq;
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
