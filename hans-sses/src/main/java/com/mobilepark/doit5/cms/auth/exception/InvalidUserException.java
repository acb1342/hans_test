package com.mobilepark.doit5.cms.auth.exception;

/*==================================================================================
 * @Project      : upush-admin
 * @Package      : com.skt.svc.cms.auth.exception
 * @Filename     : InvalidUserException.java
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
public class InvalidUserException extends AuthenticationException {
	private static final long serialVersionUID = 1808607798923645074L;

	public InvalidUserException(String msg) {
		super(msg);
	}

	public InvalidUserException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
