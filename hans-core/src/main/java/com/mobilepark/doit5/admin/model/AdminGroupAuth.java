package com.mobilepark.doit5.admin.model;

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
@Table(name = "TB_MGMT_ADMIN_GROUPAUTH")
public class AdminGroupAuth extends AbstractModel<AdminGroupAuth.ID> {
	public static class ID implements Serializable {
		private static final long serialVersionUID = -8380663686839294095L;

		private int adminGroupId;

		private int menuId;

		public ID() {
			super();
		}

		public ID(int adminGroupId, int menuId) {
			this.adminGroupId = adminGroupId;
			this.menuId = menuId;
		}

		public int getAdminGroupId() {
			return this.adminGroupId;
		}

		public int getMenuId() {
			return this.menuId;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj != null && this.getClass().equals(obj.getClass())) {
				ID id = (ID) obj;
				return (this.adminGroupId == id.adminGroupId && this.menuId == id.menuId);
			}

			return false;
		}

		@Override
		public int hashCode() {
			return this.adminGroupId + this.menuId;
		}
	}

	public AdminGroupAuth() {
		super();
	}

	public AdminGroupAuth(int groupId, int menuId) {
		super();
		this.id = new ID(groupId, menuId);
	}

	public AdminGroupAuth(int groupId, int menuId, String auth) {
		super();
		this.id = new ID(groupId, menuId);
		this.auth = auth;
	}

	@EmbeddedId
	@AttributeOverrides({
		@AttributeOverride(name = "adminGroupId", column = @Column(name = "ADMIN_GROUP_ID")),
		@AttributeOverride(name = "menuId", column = @Column(name = "MENU_ID"))
	})
	private ID id;

	@Column(name= "MENU_ID", insertable = false, updatable = false)
	private Integer menuId;
	@Column(name = "ADMIN_GROUP_ID", insertable = false, updatable = false)
	private Integer adminGroupId;
	
	@Column(name = "AUTH", nullable = false)
	private String auth;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;

	@Column(name = "FST_RG_DT")
	private Date fstRgDt;

	@Column(name = "LST_CH_USID")
	private String lstChUsid;

	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	@Override
	public ID getId() {
		return this.id;
	}

	public void setId(ID id) {
		this.id = id;
	}

	public Integer getGroupId() {
		if (this.id != null) {
			return this.id.getAdminGroupId();
		} else {
			return this.adminGroupId;
		}
	}

	public Integer getMenuId() {
		if (this.id != null) {
			return this.id.getMenuId();
		} else {
			return this.menuId;
		}
	}

	public String getAuth() {
		return this.auth;
	}

	public void setAuth(String authLevel) {
		this.auth = authLevel;
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

	public Integer getAdminGroupId() {
		return adminGroupId;
	}

	public void setAdminGroupId(Integer adminGroupId) {
		this.adminGroupId = adminGroupId;
	}

	public void setMenuId(Integer menuId) {
		this.menuId = menuId;
	}
	
}
