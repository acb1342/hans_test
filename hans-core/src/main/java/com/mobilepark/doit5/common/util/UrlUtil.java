package com.mobilepark.doit5.common.util;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.util
 * @Filename     : UrlUtil.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 13.      최초 버전
 * =================================================================================
 */
public class UrlUtil {
	public static String appendQueryString(String target, String queryString) {
		String ret = target;

		if (target.contains("?")) {
			ret = ret + "&" + queryString;
		} else {
			ret = ret + "?" + queryString;
		}

		return ret;
	}

	public static void main(String[] args) {
		System.out.println(appendQueryString("http://www.test.com?gid=2", "id=1"));
		System.out.println(appendQueryString("http://www.test.com", "id=1"));
	}
}
