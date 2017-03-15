package com.mobilepark.doit5.ifs.upns.exception;

import com.mobilepark.doit5.ifs.upns.UPNSMessage;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upns.exception
 * @Filename     : UPNSResultException.java
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
public class UPNSResultException extends UPNSInterfaceException {
	private static final long serialVersionUID = 7832869200604053682L;

	private UPNSMessage response;

	public UPNSResultException(UPNSMessage response) {
		super(response.getResultMsg());
		this.response = response;
	}

	public UPNSResultException(UPNSMessage response, String msg) {
		super(msg);
		this.response = response;
	}

	public UPNSResultException(UPNSMessage response, String msg, Throwable cause) {
		super(msg, cause);
		this.response = response;
	}

	public UPNSMessage getResponse() {
		return this.response;
	}

	public void setResponse(UPNSMessage response) {
		this.response = response;
	}

	public String getResultCode() {
		return this.response.getResultCode();
	}

	public String getReulstDesc() {
		return this.response.getResultMsg();
	}
}
