package com.mobilepark.doit5.common.push;

import com.mobilepark.doit5.ifs.pushserver.APNS;
import com.mobilepark.doit5.ifs.pushserver.GCM;
import com.uangel.platform.collection.BaseData;
import com.uangel.platform.log.TraceLog;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common.push
 * @Filename     : PushManager.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 25.      최초 버전
 * =================================================================================
 */
public class PushManager {
	private static String SYS_ORGIN = "UANGEL-CRM";
	private static String PUSH_MSG_FORMAT = "SYSORG=%s|SENDERNO=%s|TID=%d|DATA=%s";
	private static String APNS_ALERT = "[CRM] 새 알림이 있습니다";

	private String senderPhoneNo;
	private String receiverMobileToken;
	private String receiverPhoneNo;

	private Long msgId;
	private String payload;

	public PushManager(String senderPhoneNo, String receiverMobileToken, String receiverPhoneNo, Long msgId, String payload) {
		super();
		this.senderPhoneNo = senderPhoneNo;
		this.receiverMobileToken = receiverMobileToken;
		this.receiverPhoneNo = receiverPhoneNo;
		this.msgId = msgId;
		this.payload = payload;
	}

	public BaseData pushGCM(String partnerApiKey) throws InvalidPushTokenException, GCMFailException {
		BaseData ret = new BaseData();

		// GCM 로그
		String pushMessage = String.format(PUSH_MSG_FORMAT, SYS_ORGIN, this.senderPhoneNo, this.msgId, this.payload);
		TraceLog.info("%s->%s|GCM|push [pushMessage:%s, mobileToken:%s, apiKey:%s]", this.senderPhoneNo, this.receiverPhoneNo, pushMessage, this.receiverMobileToken, partnerApiKey);

		BaseData data = new BaseData();
		data.setString("SYSORG", SYS_ORGIN);
		data.setString("SENDERNO", this.senderPhoneNo);
		data.setString("TID", this.msgId.toString());
		data.setString("DATA", this.payload);

		try {
			ret = GCM.sendMessage(partnerApiKey, this.receiverMobileToken, this.msgId, data);
		} catch (Exception e) {
			TraceLog.printStackTrace(e);
			throw new GCMFailException();
		}

		if (!ret.getBoolean("isSuccess")) {
			if (ret.getString("responseBody").indexOf("InvalidRegistration") > -1) {
				throw new InvalidPushTokenException();
			} else {
				throw new GCMFailException();
			}
		}

		return ret;
	}

	public BaseData pushAPNS(String partnerKeyStore, String partnerKeyStorePasswd) throws InvalidPushTokenException, APNSFailException {
		BaseData ret = new BaseData();

		// APNS 로그
		String pushMessage = String.format(PUSH_MSG_FORMAT, SYS_ORGIN, this.senderPhoneNo, this.msgId, this.payload);
		TraceLog.info("%s->%s|APNS|push [pushMessage:%s, mobileToken:%s, apiKey:%s]", this.senderPhoneNo, this.receiverPhoneNo, pushMessage, this.receiverMobileToken, partnerKeyStore);

		BaseData data = new BaseData();
		data.setString("ALERT", APNS_ALERT);
		data.setString("SYSORG", SYS_ORGIN);
		data.setString("SENDERNO", this.senderPhoneNo);
		data.setString("TID", this.msgId.toString());
		data.setString("DATA", this.payload);

		try {
			ret = APNS.sendMessage(partnerKeyStore, partnerKeyStorePasswd, this.receiverMobileToken, data);
		} catch (Exception e) {
			TraceLog.printStackTrace(e);
			throw new APNSFailException();
		}

		if (!ret.getBoolean("isSuccess")) {
			if (ret.getString("responseBody").indexOf("InvalidDeviceTokenFormatException") > -1 ||
				ret.getString("responseBody").indexOf("Device Token has a length") > -1) {
				throw new InvalidPushTokenException();
			} else {
				throw new APNSFailException();
			}
		}

		return ret;
	}

	public String getSenderPhoneNo() {
		return this.senderPhoneNo;
	}

	public void setSenderPhoneNo(String senderPhoneNo) {
		this.senderPhoneNo = senderPhoneNo;
	}

	public String getReceiverMobileToken() {
		return this.receiverMobileToken;
	}

	public void setReceiverMobileToken(String receiverMobileToken) {
		this.receiverMobileToken = receiverMobileToken;
	}

	public String getReceiverPhoneNo() {
		return this.receiverPhoneNo;
	}

	public void setReceiverPhoneNo(String receiverPhoneNo) {
		this.receiverPhoneNo = receiverPhoneNo;
	}

	public Long getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getPayload() {
		return this.payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
}
