package com.hans.sses.cms.auth.exception;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth.exception
 * @Filename     : InvalidPasswordException.java
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
public class InvalidPasswordException extends AuthenticationException {
	private static final long serialVersionUID = -7941144557812256867L;

	public InvalidPasswordException(String msg) {
		super(msg);
	}

	public InvalidPasswordException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
