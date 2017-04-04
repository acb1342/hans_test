package com.hans.sses.common.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.DateUtil;
import com.uangel.platform.util.TimeUtil;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.util
 * @Filename     : PartitionUtilz.java
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
public class PartitionUtil {
	public static List<Integer> getListByMonthRange(Date fromDate, Date toDate) {
		return getListByMonthRange(DateUtil.dateToString(fromDate, "yyyyMMdd"), DateUtil.dateToString(toDate, "yyyyMMdd"));
	}

	public static List<Integer> getListByMonthRange(String fromDate, String toDate) {
		Integer[] test = {
			1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12
		};
		int startMonth = TimeUtil.getMonth(TimeUtil.getTickFrom8StrFormat(fromDate.replaceAll("/|-", "").substring(0, 8)));
		Collections.rotate(Arrays.asList(test), (1 - startMonth));
		List<Integer> partitionList = Arrays.asList(Arrays.copyOfRange(test, 0, monthDiff(fromDate, toDate)));

		TraceLog.debug("list of partitions are %s", partitionList);

		return partitionList;
	}

	public static int monthDiff(String fromDate, String toDate) {
		int difference = 0;

		long time1Millis = TimeUtil.getTickFrom8StrFormat(fromDate.replaceAll("/|-", "").substring(0, 6) + "01");
		long time2Millis = TimeUtil.getTickFrom8StrFormat(toDate.replaceAll("/|-", "").substring(0, 6) + "31");

		double d1 = ((double) time1Millis) / (1000 * 60 * 60 * 24);
		double d2 = ((double) time2Millis) / (1000 * 60 * 60 * 24);

		difference = (int) Math.floor(Math.abs((d2 - d1) / 31));

		TraceLog.debug("difference between two partitions is %d", difference);

		return difference >= 12 ? 12 : (difference % 12) + 1;
	}

	public static void main(String[] args) {
		System.out.println(getListByMonthRange("2013/02/28", "2013/03/01"));
		System.out.println(getListByMonthRange("2013/05/01", "2014/03/31"));
		System.out.println(getListByMonthRange("2013/01/01", "2013/11/31"));
		System.out.println(getListByMonthRange("2013/02/01", "2014/10/30"));
	}
}
