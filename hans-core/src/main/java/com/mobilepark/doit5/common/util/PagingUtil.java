package com.mobilepark.doit5.common.util;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.util
 * @Filename     : PagingUtilz.java
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
public class PagingUtil {
	public static int getPage(Object page) {
		int pageNum = 1;
		try {
			pageNum = Integer.parseInt((String) page);
		} catch (Exception e) {
			pageNum = 1;
		}
		return pageNum;
	}
}
