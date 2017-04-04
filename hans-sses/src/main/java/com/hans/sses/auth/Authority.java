package com.hans.sses.auth;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth
 * @Filename     : Authority.java
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
public interface Authority {
	public String getAuthLevel();

	public void setAuthLevel(String authLevel);

	public boolean isRead();

	public boolean isCreate();

	public boolean isUpdate();

	public boolean isDelete();

	public boolean isApprove();

	public boolean hasAuthority();
}
