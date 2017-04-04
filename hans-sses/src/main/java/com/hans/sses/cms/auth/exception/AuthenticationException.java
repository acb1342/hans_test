package com.hans.sses.cms.auth.exception;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth.exception
 * @Filename     : AuthenticationException.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
public class AuthenticationException extends Exception {
	private static final long serialVersionUID = 6843390258104640993L;

	public AuthenticationException(String msg) {
		super(msg);
	}

	public AuthenticationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
