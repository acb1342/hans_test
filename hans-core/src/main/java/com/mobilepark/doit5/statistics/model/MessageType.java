package com.mobilepark.doit5.statistics.model;

public class MessageType {
	// 충전기 -> 서버
	public final static byte SERIAL_NUMBER  = (byte) 0xB0; // 충전기 Serial Number
	public final static byte CHARGER_STATUS = (byte) 0xC0; // 충전기 상태 정보
	public final static byte TOTAL_WATT     = (byte) 0xC3; // 현재 충전의 총 충전
	public final static byte USER_ID        = (byte) 0xC4; // 사용자 ID (RFID / NFC)
	public final static byte USER_TYPE      = (byte) 0xC5; // 사용자 ID가 RFID 인지 NFC 인지 구분자
	
	public static String getDescription(byte type) {
		switch(type) {
			case SERIAL_NUMBER      : return "충전기 Serial Number";
			case CHARGER_STATUS     : return "충전기 상태 정보";
			case TOTAL_WATT         : return "현재 충전의 총 충전";
			case USER_ID            : return "사용자 ID (RFID / NFC)";
			case USER_TYPE          : return "사용자 ID가 RFID 인지 NFC 인지 구분자";
		}
		return "";
	}
}
