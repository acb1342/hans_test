package com.mobilepark.doit5.ifs.upush;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mobilepark.doit5.common.util.IFLogger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uangel.platform.collection.BaseData;
import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upush
 * @Filename     : UPUSH.java
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
public class UPUSH {
	private static String UPUSH_SEND_URL = Env.get("upush.url", "http://192.168.1.129:7001/if/upush_recv_msg.xcg?op=create");

	private static IFLogger log = new IFLogger(IFLogger.UPUSH_PUSH_LOGGER_NAME, "[PUSH.UPUSH]");

	public static BaseData sendNotification(String type, String os, String appId, String groupId, String mdn, String pushToken, String title, String message)
			throws Exception {
		log.info("start to send UPUSH request message...");

		URL url = new URL(UPUSH_SEND_URL);
		URLConnection request = url.openConnection();
		request.setDoOutput(true);
		request.setDoInput(true);

		StringBuilder buf = new StringBuilder();
		buf.append("type").append("=").append((URLEncoder.encode(type, "UTF-8")));
		buf.append("&app_id").append("=").append((URLEncoder.encode(appId, "UTF-8")));
		buf.append("&group_id").append("=").append((URLEncoder.encode(groupId, "UTF-8")));
		if ("0".equals(type)) {
			buf.append("&mdn").append("=").append((URLEncoder.encode(mdn, "UTF-8")));
		} else if ("1".equals(type)) {
			buf.append("&os").append("=").append((URLEncoder.encode(os, "UTF-8")));
			buf.append("&push_token").append("=").append((URLEncoder.encode(pushToken, "UTF-8")));
		} else if ("2".equals(type)) {
			buf.append("&os").append("=").append((URLEncoder.encode(os, "UTF-8")));
		}
		buf.append("&title").append("=").append((URLEncoder.encode(title, "UTF-8")));
		// buf.append("&message").append("=").append((URLEncoder.encode(message, "UTF-8"))); //org
		buf.append("&message").append("=").append((URLEncoder.encode(message, "UTF-8").replaceAll("%3C%5C", "%3C")));

		log.info(buf.toString().replaceAll("&", "\n&"));

		// request.setRequestProperty("Content-Type", "application/json");
		request.setRequestProperty("Content-Length", buf.toString().getBytes().length + "");

		try {
			OutputStreamWriter post = new OutputStreamWriter(request.getOutputStream());
			post.write(buf.toString());
			// System.out.println(buf.toString());
			post.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(request.getInputStream()));
			buf = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buf.append(inputLine);
			}
			post.close();
			in.close();
		} catch (java.net.ConnectException e) {
			log.info("connection to the PUSH server refused.");

			BaseData response = new BaseData("UPUSH result");
			// response.setString("responseBody", buf.toString());
			response.setInt("err_code", 408);
			response.setString("err_msg", "Connection to the Push-Server refused.");

			return response;
		}

		String resultString = buf.toString();
		ObjectMapper mapper = new ObjectMapper();
		JsonNode resultObject = mapper.readTree(resultString);
		int resultStatus = Integer.parseInt(resultObject.get("err_code").toString());
		String resultMsg = resultObject.get("err_msg").toString();
		BaseData response = new BaseData("UPUSH result");
		// response.setString("responseBody", buf.toString());
		response.setInt("err_code", resultStatus);
		response.setString("err_msg", resultMsg);

		// System.out.println(response.toString()); // DEBUG
		log.info(response.toString());

		log.info("end to get UPUSH response message...");

		return response;
	}

	public static void sendNotification2(String type, String os, String appId, String groupId, String mdn, String pushToken, String title, String message) {

		try {
			URL url = new URL(UPUSH_SEND_URL);

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			conn.setRequestMethod("POST");

			conn.setDoOutput(true);
			conn.setDoInput(true);

			StringBuilder buf = new StringBuilder();
			buf.append("type").append("=").append((URLEncoder.encode(type, "UTF-8")));
			buf.append("&app_id").append("=").append((URLEncoder.encode(appId, "UTF-8")));
			buf.append("&group_id").append("=").append((URLEncoder.encode(groupId, "UTF-8")));
			if ("0".equals(type)) {
				buf.append("&mdn").append("=").append((URLEncoder.encode(mdn, "UTF-8")));
			} else if ("1".equals(type)) {
				buf.append("&os").append("=").append((URLEncoder.encode(os, "UTF-8")));
				buf.append("&push_token").append("=").append((URLEncoder.encode(pushToken, "UTF-8")));
			} else if ("2".equals(type)) {
				buf.append("&os").append("=").append((URLEncoder.encode(os, "UTF-8")));
			}
			buf.append("&title").append("=").append((URLEncoder.encode(title, "UTF-8")));
			buf.append("&message").append("=").append((URLEncoder.encode(message, "UTF-8")));

			log.info(buf.toString().replaceAll("&", "\n&"));

			// conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			conn.setRequestProperty("Content-Length", buf.toString().getBytes().length + "");

			OutputStreamWriter post = new OutputStreamWriter(conn.getOutputStream());
			post.write(buf.toString());
			post.flush();

			BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			buf = new StringBuilder();
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				buf.append(inputLine);
			}
			post.close();
			in.close();

			int responseCode = conn.getResponseCode();
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Response Code : " + responseCode);
			System.out.println(buf.toString());

			String resultString = buf.toString();

			ObjectMapper mapper = new ObjectMapper();
			JsonNode resultObject = mapper.readTree(resultString);
			int resultStatus = Integer.parseInt(resultObject.get("err_code").toString());
			String resultMsg = resultObject.get("err_msg").toString();

			System.out.println("err_code:" + resultStatus + " " + "err_msg:" + resultMsg);

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		String type = "0"; // 전송요청타입 - 0: MDN, 1: PUSH, 2: 일괄전송
		String appId = "com.uangel.upush";
		Set<String> mdn = new HashSet<String>(0);
		mdn.add("821012345678");
		mdn.add("821098765432");
		mdn.add("821022223333");
		String groupId = "1234";
		String title = "[U-PUSH] UPUSH 테스트 메시지";
		String message = "[U-PUSH] 알림메시지가 도착했습니다.";
		StringBuilder buf = new StringBuilder();
		String mdnStr = "";
		Iterator it = mdn.iterator();
		String os = "";
		String pushToken = "";

		// noti json
		JSONObject notiJson = new JSONObject();
		notiJson.put("title", "noti_title");
		notiJson.put("message", "noti_message");
		notiJson.put("image", "noti_image");

		// button1 json
		JSONObject button1Json = new JSONObject();
		button1Json.put("position", "left");
		button1Json.put("title", "button1");
		button1Json.put("type", "none");
		button1Json.put("action", "none");
		button1Json.put("color", "#ffffff");

		// button2 json
		JSONObject button2Json = new JSONObject();
		button2Json.put("position", "right");
		button2Json.put("title", "button2");
		button2Json.put("type", "url");
		button2Json.put("action", "url");
		button2Json.put("color", "#ffffff");

		// button json array
		JSONArray buttonJsonArray = new JSONArray();
		buttonJsonArray.put(0, button1Json);
		buttonJsonArray.put(1, button2Json);

		System.out.println("buttonJsonArray: " + buttonJsonArray.toString());

		// push json
		JSONObject pushJson = new JSONObject();
		pushJson.put("type", "push_type");
		pushJson.put("title", "push_title");
		pushJson.put("body", "push_body");
		pushJson.put("button", buttonJsonArray);

		// message json
		JSONObject messageJson = new JSONObject();
		messageJson.put("pkgname", "com.uangel.upushtest");
		messageJson.put("noti", notiJson);
		messageJson.put("push", pushJson);

		String str = "테스트!@#"; // "\\";
		StringBuffer sb = new StringBuffer();

		for (int j = 0; j < str.length(); j++) {
			if (Character.isLetterOrDigit(str.charAt(j))) {
				sb.append(str.charAt(j));
			}
		}
		System.out.println("특수문자: " + sb.toString());

		System.out.println("messageJson: " + messageJson.toString()); // .replaceAll("\\\"", "\""));

		while (it.hasNext()) {
			String value = (String) it.next();

			System.out.println("Value: " + value);
		}

		String elements[] = {
			"821012345678", "821098765432", "821022223333"
		};
		Set<String> set = new HashSet<String>(Arrays.asList(elements));

		Object[] arrObj = set.toArray();

		for (Object element : arrObj) {
			System.out.println(element);
		}

		for (int i = 0; i < mdn.size(); i++) {
			if (mdn.size() > 1) {
				mdnStr = "\"" + elements[i] + "\"";
				buf.append(mdnStr);
				if (i == mdn.size() - 1) {
					break;
				}
				buf.append(",");
			}
		}
		if (mdn.size() > 1) {
			mdnStr = "[" + buf.toString() + "]";
		}

		System.out.println("mdnStr: " + mdnStr);
		System.out.println("set: " + set);

		BaseData response = sendNotification(type, os, appId, groupId, mdnStr, pushToken, title, messageJson.toString());
		System.out.println("");
		System.out.println(">>>>>> Response Message");
		System.out.println(response.toString());
	}
}
