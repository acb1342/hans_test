package com.hans.sses.common.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.DigestTool;
import com.uangel.platform.util.EtcUtil;
import com.uangel.platform.util.HexUtil;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.util
 * @Filename     : MDNUtilz.java
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
public class MDNUtil {
	public static String getLast4Digit(String mdn) {
		return mdn.substring(mdn.length() - 4, mdn.length());
	}

	public static String getMD5Str(String mdn) {
		String hexValue = null;

		try {
			hexValue = HexUtil.toHexString(DigestTool.getMessageDigest(DigestTool.DIGEST_MD5, mdn.getBytes("utf-8")));
		} catch (NoSuchAlgorithmException e) {
			TraceLog.printStackTrace(e);
		} catch (NoSuchProviderException e) {
			TraceLog.printStackTrace(e);
		} catch (UnsupportedEncodingException e) {
			TraceLog.printStackTrace(e);
		}

		return hexValue;
	}

	public static String getDashFormatted(String mdn) {
		if (mdn.length() == 11) {
			return EtcUtil.setFormatmask("###-####-####", mdn);
		} else if (mdn.length() == 10) {
			return EtcUtil.setFormatmask("###-###-####", mdn);
		} else {
			return mdn;
		}
	}

	public static String getAsteriskMasked(String mdn) {
		if (mdn.length() == 11) {
			return mdn.substring(0, 3) + "-" + mdn.substring(mdn.length() - 8, mdn.length() - 6) + "**" + "-" + mdn.substring(mdn.length() - 4, mdn.length() - 1) + "*";
		} else if (mdn.length() == 10) {
			return mdn.substring(0, 3) + "-" + mdn.substring(mdn.length() - 7, mdn.length() - 5) + "*" + "-" + mdn.substring(mdn.length() - 4, mdn.length() - 1) + "*";
		} else {
			return mdn;
		}
	}

	public static boolean isMobileNo(String phoneNo) {
		if (phoneNo == null) {
			return false;
		}

		String regex = "(^010|^011|^016|^017|^018|^019)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(phoneNo);

		while (matcher.find()) {
			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		System.out.println("01034963258/" + getMD5Str("01034963258"));
		System.out.println("01038093990/" + getMD5Str("01038093990"));
		System.out.println("01039441192/" + getMD5Str("01039441192"));
		System.out.println("01040258428/" + getMD5Str("01040258428"));
		System.out.println("01040815225/" + getMD5Str("01040815225"));
		System.out.println("01087103379/" + getMD5Str("01087103379"));
		System.out.println("01099547726/" + getMD5Str("01099547726"));
		System.out.println(getAsteriskMasked("01012345678"));
		System.out.println(getAsteriskMasked("0101234567"));
		System.out.println(isMobileNo("01011112222"));
		System.out.println(isMobileNo("0211112222"));
	}
}
