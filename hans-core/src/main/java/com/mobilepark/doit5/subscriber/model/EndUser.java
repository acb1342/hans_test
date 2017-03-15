package com.mobilepark.doit5.subscriber.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.model
 * @Filename     : EndUser.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "tbl_user", catalog = "upush")
public class EndUser extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "user_sn", unique = true, nullable = false)
	private Integer id;

	@Column(name = "app_id", nullable = false, length = 256)
	private String appId;

	@Column(name = "app_ver", length = 16)
	private String appVer;

	@Column(name = "push_token", length = 256)
	private String pushToken;

	@Column(name = "os", length = 16)
	private String os;

	@Column(name = "os_ver", length = 8)
	private String osVer;

	@Column(name = "lib_ver", length = 8)
	private String libVer;

	@Column(name = "device_id", nullable = false, length = 256)
	private String deviceId;

	@Column(name = "device_brand", length = 64)
	private String deviceBrand;

	@Column(name = "device_model", length = 64)
	private String deviceModel;

	@Column(name = "height")
	private Integer height;

	@Column(name = "width")
	private Integer width;

	@Column(name = "locale", length = 8)
	private String locale;

	@Column(name = "market", length = 64)
	private String market;

	@Column(name = "mdn", length = 32)
	private String mdn;

	@Column(name = "reg_time", length = 16)
	private String regTime;

	@Column(name = "web_ver", length = 8)
	private String webVer;

	@Column(name = "html5")
	private Integer html5;

	@Column(name = "carrier", length = 16)
	private String carrier;

	public EndUser() {
	}

	public EndUser(String appId, String appVer, String pushToken, String os, String osVer,
			String libVer, String deviceId, String deviceBrand, String deviceModel,
			Integer height, Integer width, String locale, String market, String mdn,
			String regTime, String webVer, Integer html5, String carrier) {
		this.appId = appId;
		this.appVer = appVer;
		this.pushToken = pushToken;
		this.os = os;
		this.osVer = osVer;
		this.libVer = libVer;
		this.deviceId = deviceId;
		this.deviceBrand = deviceBrand;
		this.deviceModel = deviceModel;
		this.height = height;
		this.width = width;
		this.locale = locale;
		this.market = market;
		this.mdn = mdn;
		this.regTime = regTime;
		this.webVer = webVer;
		this.html5 = html5;
		this.carrier = carrier;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppVer() {
		return this.appVer;
	}

	public void setAppVer(String appVer) {
		this.appVer = appVer;
	}

	public String getPushToken() {
		return this.pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getOsVer() {
		return this.osVer;
	}

	public void setOsVer(String osVer) {
		this.osVer = osVer;
	}

	public String getLibVer() {
		return this.libVer;
	}

	public void setLibVer(String libVer) {
		this.libVer = libVer;
	}

	public String getDeviceId() {
		return this.deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getDeviceBrand() {
		return this.deviceBrand;
	}

	public void setDeviceBrand(String deviceBrand) {
		this.deviceBrand = deviceBrand;
	}

	public String getDeviceModel() {
		return this.deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public String getLocale() {
		return this.locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getMarket() {
		return this.market;
	}

	public void setMarket(String market) {
		this.market = market;
	}

	public String getMdn() {
		return this.mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public String getRegTime() {
		return this.regTime;
	}

	public void setRegTime(String regTime) {
		this.regTime = regTime;
	}

	public String getWebVer() {
		return this.webVer;
	}

	public void setWebVer(String webVer) {
		this.webVer = webVer;
	}

	public Integer getHtml5() {
		return this.html5;
	}

	public void setHtml5(Integer html5) {
		this.html5 = html5;
	}

	public String getCarrier() {
		return this.carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}
}
