package com.mobilepark.doit5.statistics.model;

public class UserType {
	public final static byte RFID      = 0x01; // RFID
	public final static byte NFC       = 0x02; // NFC
	
	public static String getDescription(byte userType) {
		switch(userType) {
			case RFID : return "RFID";
			case NFC  : return "NFC";
		}
		return "";
	}
}
