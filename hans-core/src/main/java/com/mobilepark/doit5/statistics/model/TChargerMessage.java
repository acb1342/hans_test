package com.mobilepark.doit5.statistics.model;

public class TChargerMessage {

	private String chargerId;
	private String content;
	
	private String serialNumber;
	private byte chargerStatus;
	private int watt;
	private String userId;
	private byte userType;
	
	private String cmd;

	public TChargerMessage() {
	}
	
	public TChargerMessage(String content) {
		this.content = content;
	}
	
	public String getChargerId() {
		return chargerId;
	}
	
	public void setChargerId(String chargerId) {
		this.chargerId = chargerId;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public byte getChargerStatus() {
		return chargerStatus;
	}

	public void setChargerStatus(byte chargerStatus) {
		this.chargerStatus = chargerStatus;
	}

	public int getWatt() {
		return watt;
	}

	public void setWatt(int watt) {
		this.watt = watt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public byte getUserType() {
		return userType;
	}

	public void setUserType(byte userType) {
		this.userType = userType;
	}

	public String getCmd() {
		return cmd;
	}

	public void setCmd(String cmd) {
		this.cmd = cmd;
	}

	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append("[" + chargerId + "]");
		strBuf.append("[" + serialNumber + "]");
		strBuf.append("[" + StatusType.getDescription(chargerStatus) + "]");
		strBuf.append("[" + watt + "]");
		strBuf.append("[" + UserType.getDescription(userType) + "]");
		strBuf.append("[" + (userId == null ? "" : userId) + "]");
		
		return strBuf.toString();
	}
}
