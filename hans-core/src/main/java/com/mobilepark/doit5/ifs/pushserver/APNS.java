package com.mobilepark.doit5.ifs.pushserver;

import java.util.LinkedList;
import java.util.ListIterator;

import com.mobilepark.doit5.common.util.IFLogger;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.feedback.AppleFeedbackServer;
import javapns.feedback.AppleFeedbackServerBasicImpl;
import javapns.feedback.FeedbackServiceManager;
import javapns.notification.AppleNotificationServer;
import javapns.notification.AppleNotificationServerBasicImpl;
import javapns.notification.Payload;
import javapns.notification.PayloadPerDevice;
import javapns.notification.PushNotificationManager;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;

import org.json.JSONException;

import com.uangel.platform.collection.BaseData;
import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.pushserver
 * @Filename     : APNS.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 10.      최초 버전
 * =================================================================================
 */
// reference to https://developer.apple.com/devcenter/ios/index.action
// https://developer.apple.com/ios/manage/overview/index.action
public class APNS {
	private static String APNS_KEYSTORE = Env.get("apns.keystore.path", "/home/upush/data/apns/keystore/0_apns_key.p12");
	private static String APNS_KEY_PASSWORD = Env.get("apns.key.password", "uangel2012");
	private static boolean APNS_PRODUCTION_MODE = Env.getBoolean("apns.production.mode", true);

	private static IFLogger log = new IFLogger(IFLogger.APNS_PUSH_LOGGER_NAME, "[PUSH.APNS]");

	public static BaseData sendMessage(String deviceToken, String message) throws Exception {
		BaseData data = new BaseData();
		data.setString("ALERT", message);
		BaseData ret = sendNotification(APNS_KEYSTORE, APNS_KEY_PASSWORD, deviceToken, data);
		sendFeedback(APNS_KEYSTORE, APNS_KEY_PASSWORD);

		return ret;
	}

	public static BaseData sendMessage(String keyStore, String passwd, String deviceToken, BaseData data) throws Exception {
		BaseData ret = sendNotification(keyStore, passwd, deviceToken, data);
		sendFeedback(keyStore, passwd);

		return ret;
	}

	private static BaseData sendNotification(String keyStore, String passwd, String deviceToken, BaseData data) {
		PushNotificationManager pushManager = null;
		AppleNotificationServer server = null;
		PayloadPerDevice pDevice = null;
		PushedNotification notification = null;
		Payload payLoad = getPayloadByMap(data, 1);
		boolean ret = false;
		String errorMsg = null;

		log.info("start to send APNS request message...");

		try {
			pushManager = new PushNotificationManager();
			server = new AppleNotificationServerBasicImpl(keyStore, passwd, APNS_PRODUCTION_MODE);

			log.info("initialize push notification manager.");
			pushManager.initializeConnection(server);

			log.info("sending notification message...");
			pDevice = new PayloadPerDevice(payLoad, deviceToken);
			notification = pushManager.sendNotification(pDevice.getDevice(), pDevice.getPayload());

			if (notification != null) {
				ret = notification.isSuccessful();
				log.info("response from APNS server:\n" + notification.toString());
				if (notification.getResponse() != null) {
					int resCode = notification.getResponse().getStatus();
					log.info("response code: %d", resCode);
				}
				if (ret) {
					log.info("success: %s", deviceToken);
				} else {
					log.info("invalid device token: %s", deviceToken);
				}
			}
		} catch (Exception e) {
			errorMsg = e.getMessage();
			log.error(errorMsg);
			e.printStackTrace();
		} finally {
			if (pushManager != null) {
				try {
					pushManager.stopConnection();
				} catch (CommunicationException e) {
					e.printStackTrace();
				} catch (KeystoreException e) {
					e.printStackTrace();
				}
			}
		}

		BaseData response = new BaseData("APNS result");
		response.setBoolean("isSuccess", ret);
		response.setString("responseBody", (errorMsg != null ? errorMsg : (notification == null) ? null : notification.toString()));

		log.info(response.toString());

		return response;
	}

	private static void sendFeedback(String keyStore, String passwd) {
		FeedbackServiceManager feedbackManager = null;
		AppleFeedbackServer server = null;

		try {
			log.info("initialize feedback manager.");
			feedbackManager = new FeedbackServiceManager();
			server = new AppleFeedbackServerBasicImpl(keyStore, passwd, APNS_PRODUCTION_MODE);

			log.info("checking feedback...");
			LinkedList<Device> devices = feedbackManager.getDevices(server);

			if (devices.size() > 0) {
				ListIterator<Device> itr = devices.listIterator();
				while (itr.hasNext()) {
					Device device = itr.next();
					log.info("device token: %s", device.getToken());
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unused")
	private static Payload getPayLoad(String message, int badgeCount) {
		PushNotificationPayload payLoad = new PushNotificationPayload();

		try {
			payLoad.addAlert(message);
			if (badgeCount > 0) {
				payLoad.addBadge(badgeCount);
			}
			payLoad.addSound("default");
		} catch (JSONException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		log.info("payload:" + payLoad.toString());

		return payLoad;
	}

	public static PushNotificationPayload getPayloadByMap(BaseData data, int badgeCount) {
		PushNotificationPayload payLoad = PushNotificationPayload.complex();

		try {
			payLoad.addAlert(data.getString("ALERT"));
			if (badgeCount > 0) {
				payLoad.addBadge(badgeCount);
			}
			payLoad.addSound("default");

			for (Object key : data.keySet()) {
				if (key != null && !key.toString().equals("ALERT")) {
					payLoad.addCustomDictionary(key.toString(), data.get(key).toString());
				}
			}
		} catch (JSONException e) {
			log.error(e.getMessage());
			e.printStackTrace();
		}

		log.info("payload:\n" + payLoad.toString());

		return payLoad;
	}

	public static void main(String[] args) {
		String iPhoneTestToken = "f6ac02a24d32cb03ed6e800498d6fc6a2009deadd428120b273eb4e9216229c3";

		try {
			for (int i = 0; i < 1; i++) {
				System.out.println(sendMessage(iPhoneTestToken, "test"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
