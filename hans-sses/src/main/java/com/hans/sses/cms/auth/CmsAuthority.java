package com.hans.sses.cms.auth;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth
 * @Filename     : CmsAuthority.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2014. 2. 6.      최초 버전
 * =================================================================================
 */
public class CmsAuthority implements Authority {
	private String authLevel;
	private boolean isRead;
	private boolean isCreate;
	private boolean isUpdate;
	private boolean isDelete;
	private boolean isApprove;

	public CmsAuthority(String authLevel) {
		this.setAuthLevel(authLevel);
	}

	@Override
	public String getAuthLevel() {
		return this.authLevel;
	}

	@Override
	public void setAuthLevel(String authLevel) {
		this.authLevel = authLevel;

		this.isRead = this.isRead || (authLevel.indexOf("R") != -1);
		this.isCreate = this.isCreate || (authLevel.indexOf("C") != -1);
		this.isUpdate = this.isUpdate || (authLevel.indexOf("U") != -1);
		this.isDelete = this.isDelete || (authLevel.indexOf("D") != -1);
		this.isApprove = this.isApprove || (authLevel.indexOf("A") != -1);
	}

	@Override
	public boolean isRead() {
		return this.isRead;
	}

	@Override
	public boolean isCreate() {
		return this.isCreate;
	}

	@Override
	public boolean isUpdate() {
		return this.isUpdate;
	}

	@Override
	public boolean isDelete() {
		return this.isDelete;
	}

	@Override
	public boolean isApprove() {
		return this.isApprove;
	}

	@Override
	public boolean hasAuthority() {
		return false;
	}
}
