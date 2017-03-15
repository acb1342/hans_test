package com.mobilepark.doit5.ifs.pushserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLEncoder;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSession;

import com.mobilepark.doit5.common.util.IFLogger;
import com.mobilepark.doit5.common.util.KeyGenUtil;
import com.uangel.platform.collection.BaseData;
import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.pushserver
 * @Filename     : GCM.java
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
// reference to http://developer.android.com/google/gcm/gs.html
public class GCM {
	private static String GCM_SEND_URL = Env.get("gcm.send.url", "https://android.googleapis.com/gcm/send");
	private static String GCM_CRM_API_KEY = Env.get("gcm.api.key", "AIzaSyDCg4np1XNRRfsI-1sLpgKX_V6ETLJLKew");

	private static IFLogger log = new IFLogger(IFLogger.GCM_PUSH_LOGGER_NAME, "[PUSH.GCM]");

	/**
	 * with SA GCM API key, Type0 
	 */
	public static BaseData sendMessageType0(String deviceToken, String message) throws Exception {
		String collapseKey = KeyGenUtil.getGCMUniqueTID() + String.valueOf(Math.random() % 100 + 1);

		StringBuilder buf = new StringBuilder();
		buf.append("&data.ALERT").append("=").append((URLEncoder.encode(message, "UTF-8")));

		return sendNotification(GCM_CRM_API_KEY, collapseKey, deviceToken, buf.toString());
	}

	/**
	 * with SA GCM API key, Type1 
	 */
	public static BaseData sendMessageType1(String deviceToken, String sysOrgin, String senderPhoneNo, long tid, String data) throws Exception {
		String collapseKey = KeyGenUtil.getGCMUniqueTID() + "_" + tid;

		StringBuilder buf = new StringBuilder();
		buf.append("&data.SYSORG").append("=").append((URLEncoder.encode(sysOrgin, "UTF-8")));
		buf.append("&data.SENDERNO").append("=").append(senderPhoneNo);
		buf.append("&data.TID").append("=").append(tid);
		buf.append("&data.DATA").append("=").append((URLEncoder.encode(data, "UTF-8")));

		return sendNotification(GCM_CRM_API_KEY, collapseKey, deviceToken, buf.toString());
	}

	/**
	 * with LIB GCM API key, Type1 
	 */
	public static BaseData sendMessageType1(String gcmApiKey, String deviceToken, String sysOrgin, String senderPhoneNo, long tid, String data) throws Exception {
		String collapseKey = KeyGenUtil.getGCMUniqueTID() + "_" + tid;

		StringBuilder buf = new StringBuilder();
		buf.append("&data.SYSORG").append("=").append((URLEncoder.encode(sysOrgin, "UTF-8")));
		buf.append("&data.SENDERNO").append("=").append(senderPhoneNo);
		buf.append("&data.TID").append("=").append(tid);
		buf.append("&data.DATA").append("=").append((URLEncoder.encode(data, "UTF-8")));

		return sendNotification(gcmApiKey, collapseKey, deviceToken, buf.toString());
	}

	/**
	 * with LIB GCM API key
	 */
	public static BaseData sendMessage(String gcmApiKey, String deviceToken, long tid, BaseData data) throws Exception {
		String collapseKey = KeyGenUtil.getGCMUniqueTID() + "_" + tid;

		StringBuilder buf = new StringBuilder();
		for (Object key : data.keySet()) {
			buf.append("&data." + key.toString()).append("=").append((URLEncoder.encode(data.get(key).toString(), "UTF-8")));
		}

		return sendNotification(gcmApiKey, collapseKey, deviceToken, buf.toString());
	}

	private static BaseData sendNotification(String apiKey, String collapseKey, String registrationId, String message)
			throws Exception {
		log.info("start to send GCM request message...");

		HttpsURLConnection.setDefaultHostnameVerifier(new FakeHostnameVerifier());
		URL url = new URL(GCM_SEND_URL);
		HttpsURLConnection request = (HttpsURLConnection) url.openConnection();
		request.setDoOutput(true);
		request.setDoInput(true);

		StringBuilder buf = new StringBuilder();
		buf.append("registration_id").append("=").append((URLEncoder.encode(registrationId, "UTF-8")));
		buf.append("&collapse_key").append("=").append((URLEncoder.encode(collapseKey, "UTF-8")));
		buf.append("&delay_while_idle").append("=").append("0");
		buf.append(message);

		log.info(buf.toString().replaceAll("&", "\n&"));

		request.setRequestMethod("POST");
		request.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		request.setRequestProperty("Content-Length", buf.toString().getBytes().length + "");
		request.setRequestProperty("Authorization", "key=" + apiKey);

		OutputStreamWriter post = new OutputStreamWriter(request.getOutputStream());
		post.write(buf.toString());
		post.flush();

		BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
		buf = new StringBuilder();
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			buf.append(inputLine);
		}
		post.close();
		in.close();

		BaseData response = new BaseData("GCM result");
		response.setBoolean("isSuccess", (request.getResponseCode() == 200 && buf.toString().startsWith("id=")));
		response.setInt("statusCode", request.getResponseCode());
		response.setString("statusMsg", request.getResponseMessage());
		response.setString("responseBody", buf.toString());

		log.info(response.toString());

		log.info("end to get GCM response message...");

		return response;
	}

	private static class FakeHostnameVerifier implements HostnameVerifier {
		@Override
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	}

	public static void main(String[] args) {
		String androidTestToken = "APA91bFKwTvcdvxu0qdFfE_kXXVa4wn4b-BKMMLnHelsTuADjlU8tOrijO0gZ_xSyz7z9OUHAOp3AlGHapcVGtUs03vukiSxStFk8dwNaGmpKqdQH_gSFVtB3hzajLw1JQcVbRMBWr1FWePpYfu5onhwu9BZhyJX4w";

		try {
			for (int i = 0; i < 1; i++) {
				System.out.println(sendMessageType0(androidTestToken, "test"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
