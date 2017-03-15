package com.mobilepark.doit5.common.push;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.json.JSONException;

import com.google.android.gcm.server.Constants;
import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Message.Builder;
import com.google.android.gcm.server.MulticastResult;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.google.gson.JsonObject;
import com.uangel.platform.log.TraceLog;

import javapns.Push;
import javapns.communication.exceptions.CommunicationException;
import javapns.communication.exceptions.KeystoreException;
import javapns.devices.Device;
import javapns.notification.PushNotificationPayload;
import javapns.notification.PushedNotification;
import javapns.notification.PushedNotifications;

public class PushMessage {
	
	private static final Executor GCM_THREAD_POOL = Executors.newFixedThreadPool(5);
	private static final int GCM_MULTICAST_SIZE = 1000;
	
	private static String gcmApiKey;
	private static Sender gcmSender;
	
	private static String apnsKeyFilePath;
	private static String apnsKeyFilePassword;
	private static boolean apnsIsProduction;

	public int osType;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// 타로카드 for kakao 용 api key
		String gcmApiKey = "AIzaSyBa5Ae1xWGQdPdinAbRDVxAq2YtMyxE9-U";
		String apnsKeyFilePath = "C:\\tmp\\kakaoTarotAPNS.p12";
		String apnsKeyFilePassword = "kakaotarotcard2013";
		boolean apnsIsProduction = false;
		
		PushMessage push = new PushMessage(gcmApiKey, apnsKeyFilePath, apnsKeyFilePassword, apnsIsProduction);
		
		// 타로카드 for kakao 용 : 김정환 과장님 테스트 폰.
		String gcmToken = "dQ6TuBGCBU4:APA91bGxRh5EI7sXySMjLm8DSF4MAVYvsaNhShF0hNzGlT4RAY2xtuK6A4lT24Lh4k6iMHkYIgWdW0R7VS8m_kM7XCqHiUjsmrnV5MfBOoG8gTkt9OO4YiHlGRnAPHf5nbqfvSyH3qDK";
		boolean a = push.sendGcm("한글 보냅니다. ^^", gcmToken);
		System.out.println(a);
		//push.sendGcm("test", new ArrayList<String>());
		
		
		// 타로카드 for kakao 용 : 지연씨 테스트 폰.
		//String apnsToken = "79ac633463227808fd7d3517757569ced42ba9bd72f2de2c4500863057212fdd";
		//push.sendApns("테스트", apnsToken);
		//push.feedbackApns();
	}
	
	public PushMessage(String androidApiKey, String iosKeyFilePath, String iosKeyFilePassword, boolean iosKeyFileIsProduction) {
		gcmApiKey = androidApiKey;
		apnsKeyFilePath = iosKeyFilePath;
		apnsKeyFilePassword = iosKeyFilePassword;
		apnsIsProduction = iosKeyFileIsProduction;
		
		gcmSender = new Sender(gcmApiKey);
	}
	
	public boolean send(String os, String message, String deviceToken) {
		if("ANDROID".equalsIgnoreCase(os)) {
			return sendGcm(message, deviceToken);
		} else if("IOS".equalsIgnoreCase(os)) {
			return sendApns(message, deviceToken);
		}
		
		return false;
	}
	
	public boolean send(String os, String title, String message, String deviceToken) {
		if("ANDROID".equalsIgnoreCase(os)) {
			return sendGcm(title, message, deviceToken);
		} else if("IOS".equalsIgnoreCase(os)) {
			return sendApns(title, message, deviceToken);
		}
		
		return false;
	}
	
	// null : exception, can't send.
	// map size = 0 : success all.
	// map size > 0 : fail device token, reason.
	public Map<String, String> send(String os, String message, List<String> deviceTokenList) {
		if("ANDROID".equalsIgnoreCase(os)) {
			return sendGcm(message, deviceTokenList);
		} else if("IOS".equalsIgnoreCase(os)) {
			return sendApns(message, deviceTokenList);
		}
		
		return null;
	}
	
	public boolean send(String os, Map<String, String> messageMap, String deviceToken) {
		if("ANDROID".equalsIgnoreCase(os)) {
			return sendGcm(messageMap, deviceToken);
		} else if("IOS".equalsIgnoreCase(os)) {
			return sendApns(messageMap, deviceToken);
		}
		
		return false;
	}
	
	public Map<String, String> send(String os, Map<String, String> messageMap, List<String> deviceTokenList) {
		if("ANDROID".equalsIgnoreCase(os)) {
			return sendGcm(messageMap, deviceTokenList);
		} else if("IOS".equalsIgnoreCase(os)) {
			return sendApns(messageMap, deviceTokenList);
		}
		
		return null;
	}
		
	// send a single message using plain post
	public boolean sendGcm(String message, String deviceToken) {
		return sendGcm(getGcmMessage(message), deviceToken);
	}
	
	public boolean sendGcm(String title, String message, String deviceToken) {
		return sendGcm(getGcmMessage(title, message), deviceToken);
	}
	
	public boolean sendGcm(Map<String, String> messageMap, String deviceToken) {
		return sendGcm(getGcmMessage(messageMap), deviceToken);
	}
	
	public boolean sendGcm(Message gcmMessage, String deviceToken) {
		if(deviceToken == null || gcmMessage == null) return false;
		
		try {
			
			TraceLog.debug("gcmMessage : " + gcmMessage);
			Result result = gcmSender.send(gcmMessage, deviceToken, 5);
			TraceLog.debug("result : " + result);

			if(result.getMessageId() != null) return true;
	        else return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	// send a multicast message using JSON
    // must split in chunks of 1000 devices (GCM limit)
	public Map<String, String> sendGcm(String message, final List<String> deviceTokenList) {
		return sendGcm(getGcmMessage(message), deviceTokenList);
	}
	
	public Map<String, String> sendGcm(Map<String, String> messageMap, final List<String> deviceTokenList) {
		return sendGcm(getGcmMessage(messageMap), deviceTokenList);
	}
	
	public Map<String, String> sendGcm(Message gcmMessage, final List<String> deviceTokenList) {
		if(deviceTokenList == null || gcmMessage == null) return null;
		Map<String, String> failedDeviceTokenMap = new HashMap<String, String>();
		
		int counter = 0;
		List<String> partialDeviceTokenList = new ArrayList<String>(deviceTokenList.size());
		Map<String, String> partialSendResultMap;
        for (String device : deviceTokenList) {
        	counter++;
        	
        	partialDeviceTokenList.add(device);
			if (partialDeviceTokenList.size() == GCM_MULTICAST_SIZE || counter == deviceTokenList.size()) {
				partialSendResultMap = sendGcmAsync(gcmMessage, partialDeviceTokenList);
				partialDeviceTokenList.clear();
				
				if(partialSendResultMap != null) {
					failedDeviceTokenMap.putAll(partialSendResultMap);
				} else {
					// exception.
					//break;
					for(String token : partialDeviceTokenList) {
						failedDeviceTokenMap.put(token, "_GCM_ExceptionSendGCM");
					}
				}
			}
		}
        
        return failedDeviceTokenMap;
	}
	
	private Message getGcmMessage(String message) {
		if(message == null) return null;
		
		return new Message.Builder()
		.addData("title", "")
		.addData("body", message)
		.addData("badge", "1")
		.collapseKey(String.valueOf(System.currentTimeMillis()))
		.delayWhileIdle(true)
		.timeToLive(3)
		.build();
	}
	
	private Message getGcmMessage(String title, String message) {
		if(message == null) return null;
		
		return new Message.Builder()
				.addData("title", title)
				.addData("body", message)
				.addData("badge", "1")
				.collapseKey(String.valueOf(System.currentTimeMillis()))
				.delayWhileIdle(true)
				.timeToLive(3)
				.build();
	}
	
	private Message getGcmMessage(Map<String, String> messageMap) {
		if(messageMap == null) return null;
		
		Builder builder = new Message.Builder()
		.collapseKey(String.valueOf(System.currentTimeMillis()))
		.delayWhileIdle(true)
		.timeToLive(3);
		
		for (Map.Entry<String, String> entry : messageMap.entrySet()) {
			builder = builder.addData(entry.getKey(), entry.getValue());
		}
		
		return builder.build();
	}
	
	private Map<String, String> sendGcmAsync(final Message message, final List<String> deviceTokenList) {
		SendGcmRunner command = new SendGcmRunner(message, deviceTokenList);
		GCM_THREAD_POOL.execute(command);
		
		return command.getFailedDeviceTokenMap();
	}
	
	public boolean sendApns(String message, String deviceToken) {
		JsonObject joBody = new JsonObject();
		JsonObject joAps = new JsonObject();
		JsonObject joAlert = new JsonObject();
		
		joAlert.addProperty("title", "");
		joAlert.addProperty("body", message);
		
		joAps.add("alert", joAlert);
		joAps.addProperty("badge", "1");
		
		joBody.add("aps", joAps);
		
		try {
			return sendApns(PushNotificationPayload.fromJSON(joBody.toString()), deviceToken);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean sendApns(String title, String message, String deviceToken) {
		JsonObject joBody = new JsonObject();
		JsonObject joAps = new JsonObject();
		JsonObject joAlert = new JsonObject();
		
		joAlert.addProperty("title", title);
		joAlert.addProperty("body", message);
		
		joAps.add("alert", joAlert);
		joAps.addProperty("badge", "1");
		
		joBody.add("aps", joAps);
		
		try {
			return sendApns(PushNotificationPayload.fromJSON(joBody.toString()), deviceToken);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean sendApns(Map<String, String> map, String deviceToken) {
		return sendApns(getPayloadByMap(map), deviceToken);
	}
	
	private boolean sendApns(PushNotificationPayload payload, String deviceToken) {
		PushedNotifications noti = null;
		try {
			TraceLog.debug("noti start ================================================");
			noti = Push.payload(payload, apnsKeyFilePath, apnsKeyFilePassword, apnsIsProduction, deviceToken);
			TraceLog.debug("noti end ================================================");
		} catch (CommunicationException e) {
			TraceLog.printStackTrace(e);
		} catch (KeystoreException e) {
			TraceLog.printStackTrace(e);
		} catch (Exception e) {
			TraceLog.printStackTrace(e);
		}
		
		if (noti != null) {
			TraceLog.debug("response : " + noti.getSuccessfulNotifications());
			TraceLog.debug("response : " + noti.firstElement().isSuccessful());
			TraceLog.debug("response : " + noti.firstElement().getResponse());
			TraceLog.debug("response : " + noti.getFailedNotifications());
		}
		
		return noti.firstElement().isSuccessful();
	}
	
	public Map<String, String> sendApns(String message, List<String> deviceTokenList) {
		return sendApns(PushNotificationPayload.alert(message), deviceTokenList);
	}
	
	public Map<String, String> sendApns(Map<String, String> map, List<String> deviceTokenList) {
		return sendApns(getPayloadByMap(map), deviceTokenList);
	}
	
	private Map<String, String> sendApns(PushNotificationPayload payload, List<String> deviceTokenList) {
		PushedNotifications noti = null;
		Map<String, String> failedDeviceTokenMap = null;
		
		try {
			noti = Push.payload(payload, apnsKeyFilePath, apnsKeyFilePassword, apnsIsProduction, deviceTokenList);
			
			failedDeviceTokenMap = new HashMap<String, String>();
			if (noti == null) {
				for(String token : deviceTokenList) {
					failedDeviceTokenMap.put(token, "_APNS_FailedPushNotification");
				}
			} else if (noti.getSuccessfulNotifications().size() != noti.size()){
				System.out.println("fail try size : " + noti.size());
				System.out.println("fail notification size : " + noti.getSuccessfulNotifications().size());
				
				for (PushedNotification failedNoti : noti.getFailedNotifications()) {
					if(failedNoti.getResponse() != null) {
						failedDeviceTokenMap.put(failedNoti.getDevice().getToken(), "_APNS_" + failedNoti.getResponse().getMessage());
						
						System.out.println("fail message : " + failedNoti.getResponse().getMessage());
						failedNoti.getException().printStackTrace();
					} else {
						failedDeviceTokenMap.put(failedNoti.getDevice().getToken(), "_APNS_ResponseIsNull");
					}
				}
			}
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (KeystoreException e) {
			e.printStackTrace();
		}
		
		return failedDeviceTokenMap;
	}
	
	public List<String> feedbackApns() {
		List<String> inactiveDeviceList = null;
		
		try {
			// remove inactive devices from your own list of devices  
			List<Device> inactiveDevices = Push.feedback(apnsKeyFilePath, apnsKeyFilePassword, apnsIsProduction);
			inactiveDeviceList = new ArrayList<String>();
			for(Device device : inactiveDevices) {
				inactiveDeviceList.add(device.getToken());
			}
		} catch (CommunicationException e) {
			e.printStackTrace();
		} catch (KeystoreException e) {
			e.printStackTrace();
		}
		
		return inactiveDeviceList;
	}
	
	private PushNotificationPayload getPayloadByMap(Map<String, String> map) {
		PushNotificationPayload payload = PushNotificationPayload.complex();
		
		try {
			payload.addAlert(map.get("message"));
			payload.addBadge(1);
			payload.addSound("default");
			
			for (Map.Entry<String, String> entry : map.entrySet()) {
				payload.addCustomDictionary(entry.getKey(), entry.getValue());
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return payload;
	}
	
	class SendGcmRunner implements Runnable {
		final Message message;
		final List<String> deviceTokenList;
		Map<String, String> failedDeviceTokenMap = new HashMap<String, String>();
		
		public SendGcmRunner(Message m, List<String> l) {
			message = m;
			deviceTokenList = l;
		}
		
		public Map<String, String> getFailedDeviceTokenMap() {
			return failedDeviceTokenMap;
		}

		public void run() {
			MulticastResult multicastResult;
			try {
				multicastResult = gcmSender.send(message, deviceTokenList, 5);
			} catch (IOException e) {
				System.out.println("Error posting messages : " +  e.getMessage());
				failedDeviceTokenMap = null;
				return;
			}
			
			Result result;
			String deviceToken;
			String errorCodeName;
			String canonicalRegId;
			String messageId;
			List<Result> results = multicastResult.getResults();
			for (int i = 0; i < deviceTokenList.size(); i++) {
				deviceToken = deviceTokenList.get(i);
				result = results.get(i);
				messageId = result.getMessageId();
				if (messageId != null) {
					System.out.println("Succesfully sent message to device: " + deviceToken + "; messageId = " + messageId);
					canonicalRegId = result.getCanonicalRegistrationId();
					if (canonicalRegId != null) {
						// same device has more than on registration id: update it
						System.out.println("canonicalRegId " + canonicalRegId);
						failedDeviceTokenMap.put(deviceToken, "_GCM_canonical:" + canonicalRegId);
					}
				} else {
					errorCodeName = result.getErrorCodeName();
					failedDeviceTokenMap.put(deviceToken, "_GCM_" + errorCodeName);
					
					if (errorCodeName.equals(Constants.ERROR_NOT_REGISTERED)) {
						// application has been removed from device - unregister it
						System.out.println("Unregistered device: " + deviceToken);
					} else {
						System.out.println("Error sending message to " + deviceToken + ": " + errorCodeName);
					}
				}
			}
		}
	}

}
