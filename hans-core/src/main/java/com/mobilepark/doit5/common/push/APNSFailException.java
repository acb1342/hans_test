package com.mobilepark.doit5.common.push;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.push
 * @Filename     : APNSFailException.java
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
public class APNSFailException extends Exception {
	private static final long serialVersionUID = 6866413102571472920L;

	public APNSFailException() {
		super();
	}

	public APNSFailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public APNSFailException(String arg0) {
		super(arg0);
	}

	public APNSFailException(Throwable arg0) {
		super(arg0);
	}
}
