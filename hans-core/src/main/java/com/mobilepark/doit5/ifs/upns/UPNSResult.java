package com.mobilepark.doit5.ifs.upns;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upns
 * @Filename     : UPNSResult.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 16.      최초 버전
 * =================================================================================
 */
public enum UPNSResult {
	SUCCESS("200", "SUCCESS"),
	INTERNAL_ERROR("500", "INTERNAL_ERROR");

	private String resultCode;
	private String resultDesc;

	private UPNSResult(String resultCode, String resultDesc) {
		this.resultCode = resultCode;
		this.resultDesc = resultDesc;
	}

	public String getResultCode() {
		return this.resultCode;
	}

	public String getResultDesc() {
		return this.resultDesc;
	}
}
