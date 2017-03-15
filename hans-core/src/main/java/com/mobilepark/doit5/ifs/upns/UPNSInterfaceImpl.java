package com.mobilepark.doit5.ifs.upns;

import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//import org.codehaus.jackson.map.ObjectMapper;
//import org.codehaus.jackson.map.SerializationConfig.Feature;
import com.mobilepark.doit5.ifs.upns.exception.UPNSInterfaceException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobilepark.doit5.ifs.upns.exception.UPNSHttpStatusException;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.util.DateUtil;
import com.uangel.platform.util.Env;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.ifs.upns
 * @Filename     : UPNSInterfaceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 16.      최초 버전
 * =================================================================================
 */
@SuppressWarnings({
	"rawtypes", "unchecked", "unused"
})
public class UPNSInterfaceImpl implements UPNSInterface {
	private ObjectMapper objectMapper = new ObjectMapper();

	private RestTemplate restTemplate;

	private boolean indentJson = false;

	public void setObjectMapper(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public void setIndentJson(boolean isIndentJson) {
		this.indentJson = isIndentJson;
	}

	@Override
	public UPNSMessage sendNotification(UPNSMessage upnsMessage) {
		return this.post(Env.get("upns.url"), upnsMessage);
	}

	private String newMessageId() {
		String channelCode = Env.get("upns.channelCode", "");
		String requestTime = DateUtil.dateToString(new Date(), "yyyyMMddHHmmssSSS");
		int randomNum = (new SecureRandom().nextInt() % 10);

		return String.format("%s%s%d", channelCode, requestTime, randomNum);
	}

	private UPNSMessage post(String url, final UPNSMessage request) {
		UPNSRequestCallback requestCallback = new UPNSRequestCallback(request);
		UPNSMessage response = null;
		HttpHeaders headers = null;

		try {
			HttpMessageConverterExtractor responseExtractor = new HttpMessageConverterExtractor(UPNSMessage.class, this.restTemplate.getMessageConverters());
			response = (UPNSMessage) this.restTemplate.execute(url, HttpMethod.POST, requestCallback, responseExtractor);
			headers = requestCallback.getHeaders();

			UPNSLog.request(HttpMethod.POST.toString(), url, headers, request);
			UPNSLog.response(response.toString());
		} catch (HttpStatusCodeException e) {
			UPNSLog.request(HttpMethod.POST.toString(), url, headers, request);
			UPNSLog.response(e.getResponseBodyAsString());
			String errMsg = String.format("UPNS I/F HTTP STATUS ERROR. [%s]", e.getMessage());
			throw new UPNSHttpStatusException(e.getStatusCode(), errMsg, e);
		} catch (Exception e) {
			UPNSLog.request(HttpMethod.POST.toString(), url, headers, request);
			TraceLog.printStackTrace(e);
			String errMsg = String.format("UPNS I/F ERROR. [%s]", e.getMessage());
			throw new UPNSInterfaceException(errMsg, e);
		}

		return response;
	}

	private final class UPNSRequestCallback implements RequestCallback {
		private final UPNSMessage message;

		private ClientHttpRequest clientHttpRequest;

		private UPNSRequestCallback(UPNSMessage message) {
			this.message = message;
		}

		@Override
		public void doWithRequest(ClientHttpRequest clientHttpRequest) throws IOException {
			this.clientHttpRequest = clientHttpRequest;
			if (this.message != null) {
				try {
					HttpHeaders headers = clientHttpRequest.getHeaders();
					// headers.setContentType(MediaType.APPLICATION_JSON);
					headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
					headers.set("charset", "utf-8");
					List<MediaType> acceptableMediaTypes = new ArrayList<MediaType>();
					acceptableMediaTypes.add(MediaType.APPLICATION_JSON);
					headers.setAccept(acceptableMediaTypes);
					OutputStream os = clientHttpRequest.getBody();
					os.write(("data=" + this.message.toString()).getBytes("utf-8"));
					// UPNSInterfaceImpl.this.objectMapper.configure(Feature.INDENT_OUTPUT, UPNSInterfaceImpl.this.indentJson);
					// UPNSInterfaceImpl.this.objectMapper.writeValue(os, this.message);
				} catch (Exception e) {
					throw new UPNSInterfaceException(e.getMessage(), e);
				}
			}
		}

		@SuppressWarnings("all")
		public ClientHttpRequest getRequest() {
			return this.clientHttpRequest;
		}

		public HttpHeaders getHeaders() {
			if (this.clientHttpRequest != null) {
				return this.clientHttpRequest.getHeaders();
			}
			return null;
		}
	}

	public static void main(String[] args) throws Throwable {
		UPNSInterfaceImpl upns = new UPNSInterfaceImpl();
		upns.setRestTemplate(new RestTemplate());
		upns.setIndentJson(false);

		UPNSMessage parameter = new UPNSMessage();
		Set<String> route = new HashSet<String>(0);
		route.add("PRI");
		route.add("PUB"); // route.add("SMS");
		parameter.setRoute(route);
		parameter.setTarget("");
		parameter.setMessageType("RICH");
		parameter.setMessage("메시지");
		parameter.setCpId("testcp");
		parameter.setAppPkgName("U-PUSH");
		parameter.setAppCode("20140610134051");
		parameter.setSenderNo("01091717233");
		parameter.setReplyNo("01091717233");
		parameter.setTimeToLive(2419200);
		parameter.setDryRun(false);
		parameter.setSound("default");
		parameter.setAlert("[U-PUSH] 알림메시지가 도착했습니다.");
		parameter.setPayload("{\"zoneID\":\"2\",\"messageType\":\"CONTENT_EDITOR\",\"messageUrl\":\"http://192.168.1.145:5210/editor/popup/preview.htm?id=7\"}");

		ObjectMapper  objectMapper = new ObjectMapper();
		//objectMapper.configure(Feature.INDENT_OUTPUT, true);
		StringWriter sw = new StringWriter();
		objectMapper.writeValue(sw, parameter);

		System.out.println(sw.toString());
	}
}
