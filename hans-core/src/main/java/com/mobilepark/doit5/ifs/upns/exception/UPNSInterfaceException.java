package com.mobilepark.doit5.ifs.upns.exception;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upns.exception
 * @Filename     : UPNSInterfaceException.java
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
public class UPNSInterfaceException extends RuntimeException {
	private static final long serialVersionUID = 707684620248783512L;

	public UPNSInterfaceException(String msg) {
		super(msg);
	}

	public UPNSInterfaceException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
