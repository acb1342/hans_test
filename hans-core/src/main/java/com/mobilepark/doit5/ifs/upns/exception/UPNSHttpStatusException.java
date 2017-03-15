package com.mobilepark.doit5.ifs.upns.exception;

import org.springframework.http.HttpStatus;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upns.exception
 * @Filename     : UPNSHttpStatusException.java
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
public class UPNSHttpStatusException extends UPNSInterfaceException {
	private static final long serialVersionUID = 7790487053928758836L;

	private final HttpStatus httpStatus;

	public UPNSHttpStatusException(HttpStatus httpStatus, String msg) {
		super(msg);
		this.httpStatus = httpStatus;
	}

	public UPNSHttpStatusException(HttpStatus httpStatus, String msg, Throwable cause) {
		super(msg, cause);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus() {
		return this.httpStatus;
	}
}
