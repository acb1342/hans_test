package com.mobilepark.doit5.statistics.model;

public class StatusType {
	public final static byte STAND_BY_0         = 0x00; // 충전기 대기중(충전 가능 상태)
	public final static byte PLUG_IN            = 0x01; // 차량 플러그 장착
	public final static byte RFID_FOUND         = 0x03; // RFID 인식 (사용자 인증요청)
	public final static byte CHARGE_START       = 0x04; // 충전 시작
	public final static byte CHARGING           = 0x05; // 충전 중
	public final static byte CHARGE_END         = 0x06; // 충전 완료
	public final static byte PLUG_OUT           = 0x07; // 차량 플러그 탈거
	public final static byte DEVICE_ERROR       = 0x08; // 디바이스 고장
	public final static byte ETC_ERROR          = 0x09; // 기타 에러
	public final static byte STAND_BY_A         = 0x0A; // 충전 대기
	public final static byte NOT_CHARGE         = 0x0B; // 충전 불가(대기 중)
	public final static byte PULL_CHARGE        = 0x0C; // 충전기 강제 탈거
	
	public static String getDescription(byte type) {
		switch(type) {
			case STAND_BY_0         : return "충전기 대기중(충전 가능 상태)";
			case PLUG_IN            : return "차량 플러그 장착";
			case RFID_FOUND         : return "RFID 인식 (사용자 인증요청)";
			case CHARGE_START       : return "충전 시작";
			case CHARGING           : return "충전 중";
			case CHARGE_END         : return "충전 완료";
			case PLUG_OUT           : return "차량 플러그 탈거";
			case DEVICE_ERROR       : return "디바이스 고장";
			case ETC_ERROR          : return "기타 에러";
			case STAND_BY_A         : return "충전 대기";
			case NOT_CHARGE         : return "충전 불가(대기 중)";
			case PULL_CHARGE        : return "충전기 강제 탈거";
		}
		return "";
	}
}
