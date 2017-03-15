package com.mobilepark.doit5.common.util;

import java.util.concurrent.atomic.AtomicInteger;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.util
 * @Filename     : KeyGenUtilz.java
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
public class KeyGenUtil {
	private static AtomicInteger SMS_KEY_GEN = new AtomicInteger(0);

	private static AtomicInteger GCM_KEY_GEN = new AtomicInteger(0);

	private static AtomicInteger PIN_KEY_GEN = new AtomicInteger(0);

	public static synchronized String getSMSSKTLUniqueTID() {
		String tid = null;
		String systemId = System.getProperty("system.id");
		int sequence = SMS_KEY_GEN.getAndIncrement();
		if (sequence == 999) {
			SMS_KEY_GEN.set(0);
		}

		/* 12자리 unique key */
		tid = new StringBuilder().append(systemId != null ? systemId : "T") // 1자리
				.append(Long.toString(System.currentTimeMillis() / 1000).substring(2)) // 8자리
				.append(String.format("%03d", sequence)) // 3자리
				.toString();

		return tid;
	}

	public static synchronized String getSMSSKBUniqueTID() {
		String tid = null;
		int sequence = SMS_KEY_GEN.getAndIncrement();
		if (sequence == 999) {
			SMS_KEY_GEN.set(0);
		}

		/* 20자리 unique key */
		tid = new StringBuilder().append("0000") // 4자리
				.append(Long.toString(System.currentTimeMillis())) // 13자리
				.append(String.format("%03d", sequence)) // 3자리
				.toString();

		return tid;
	}

	public static synchronized String getGCMUniqueTID() {
		String tid = null;
		int sequence = GCM_KEY_GEN.getAndIncrement();
		if (sequence == 9999999) {
			GCM_KEY_GEN.set(0);
		}

		/* 20자리 unique key */
		tid = new StringBuilder().append(Long.toString(System.currentTimeMillis())) // 13자리
				.append(String.format("%07d", sequence)) // 7자리
				.toString();

		return tid;
	}

	public static synchronized String getUniquePIN() {
		String pin = null;
		int sequence = PIN_KEY_GEN.getAndIncrement();
		if (sequence == 9999999) {
			PIN_KEY_GEN.set(0);
		}

		/* 20자리 unique key */
		pin = new StringBuilder().append(Long.toString(System.currentTimeMillis())) // 13자리
				.append(String.format("%07d", sequence)) // 7자리
				.toString();

		return pin;
	}

	public static void main(String[] args) throws Throwable {
		System.out.println(getUniquePIN());
	}
}
