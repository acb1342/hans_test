package com.mobilepark.doit5.statistics.model;

public class MgmtCmdType {
	public final static String EXT_DEV_MGMT   	= "extDevMgmt"; 	// 충전기 연동 규격 메시지
	public final static String DEV_RESET   		= "DevReset"; 		// 충전기 Reset
	public final static String REP_PER_CHANGE   = "RepPerChange"; 	// 충전기 Uplink 메시지 전송주기 설정
	
	public static String getDescription(String status) {
		switch(status) {
			case EXT_DEV_MGMT	: return "충전기 연동 규격 메시지";
			case DEV_RESET 		: return "충전기 Reset";
			case REP_PER_CHANGE	: return "충전기 Uplink 메시지 전송주기 설정";
		}
		return "";
	}
}
