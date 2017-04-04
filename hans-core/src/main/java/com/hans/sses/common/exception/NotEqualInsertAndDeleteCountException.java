package com.hans.sses.common.exception;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.push
 * @Filename     : GCMFailException.java
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
public class NotEqualInsertAndDeleteCountException extends Exception {
	static final long serialVersionUID = 6578358836846989708L;

	public NotEqualInsertAndDeleteCountException() {
		super();
	}

	public NotEqualInsertAndDeleteCountException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public NotEqualInsertAndDeleteCountException(String arg0) {
		super(arg0);
	}

	public NotEqualInsertAndDeleteCountException(Throwable arg0) {
		super(arg0);
	}
}
