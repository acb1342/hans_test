package com.mobilepark.doit5.common.push;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.push
 * @Filename     : InvalidPushTokenException.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 25.      최초 버전
 * =================================================================================
 */
public class InvalidPushTokenException extends Exception {
	private static final long serialVersionUID = 4563843693362174181L;

	public InvalidPushTokenException() {
		super();
	}

	public InvalidPushTokenException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public InvalidPushTokenException(String arg0) {
		super(arg0);
	}

	public InvalidPushTokenException(Throwable arg0) {
		super(arg0);
	}
}
