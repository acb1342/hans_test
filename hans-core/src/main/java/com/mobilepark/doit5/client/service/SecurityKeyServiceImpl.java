package com.mobilepark.doit5.client.service;

import java.io.File;
import java.util.Date;

import com.mobilepark.doit5.client.dao.SecurityKeyDao;
import com.mobilepark.doit5.client.model.SecurityKey;
import org.apache.commons.lang.SystemUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.collection.JsonObject;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.security.Base64;
import com.uangel.platform.security.SecurityTool;
import com.uangel.platform.service.AbstractGenericService;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.FileUtil;
import com.uangel.platform.util.UnixCrypt;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : SecurityKeyServiceImpl.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
@Transactional
public class SecurityKeyServiceImpl extends AbstractGenericService<SecurityKey, String> implements SecurityKeyService {
	public static final String DOWNLOAD_TOKEN = "DOWNLOAD_TOKEN";
	public static final String CLIENT_KEY = "CLIENT_KEY";
	public static final String USER_ID = "upush";

	@Autowired
	private SecurityKeyDao securityKeyDao;

	@Override
	protected GenericDao<SecurityKey, String> getGenericDao() {
		return this.securityKeyDao;
	}

	@Override
	public void updateSecurityKey() throws Exception {
		Boolean refreshFlag = Env.getBoolean("httpd.download.token.refreshFlag", false);

		/** 랜덤한 토큰값 생성 */
		String password = SecurityTool.getRandomAuthKey(10);
		TraceLog.info("new apache auth password [%s]", password);

		if (refreshFlag) {
			/** AuthType Basic으로 인증값 생성 */
			String userAuthData = USER_ID + ":" + password;
			String downloadAuthToken = new String(Base64.encode(userAuthData.getBytes()));
			String downloadToken = "Basic " + downloadAuthToken;

			/** 인증값 DB에 저장 */
			SecurityKey securityKey = this.get(DOWNLOAD_TOKEN);
			securityKey.setValue(downloadToken);
			securityKey.setModifyDate(new Date());

			this.update(securityKey);
			TraceLog.info("new download token [%s]", downloadToken);

			/** Apache user.passwd 파일 갱신한다 */
			String cryptPassword = UnixCrypt.crypt(password);
			this.writeApacheAuthUserFile(USER_ID + ":" + cryptPassword);

			/** 원본 패스워드 저장 */
			this.writeApacheAuthInfoFile(password, downloadToken);
		}
	}

	private void writeApacheAuthUserFile(String data) throws Exception {
		String authFilePath = Env.get("httpd.download.token.path", "user.passwd");
		File authFile = new File(authFilePath);
		FileUtil.writeStringToFile(authFile, data + SystemUtils.LINE_SEPARATOR, "UTF-8");
		TraceLog.info("write apache user password file [file:%s, data:%s]", authFile.getAbsolutePath(), data);
	}

	private void writeApacheAuthInfoFile(String password, String downloadToken) {
		try {
			String data = new StringBuilder()
					.append("password=").append(password).append("\n")
					.append("DOWNLOAD_TOKEN=").append(downloadToken).append("\n")
					.toString();
			String filePath = Env.get("httpd.download.authInfoFile");
			FileUtil.writeStringToFile(new File(filePath), data);

			TraceLog.info("write apache authinfo file [file:%s, data:%s]", filePath, data);
		} catch (Exception e) {
			TraceLog.error(e.getMessage(), e);
		}
	}

	@Override
	public JsonObject getSystemInspectionValues() {
		JsonObject value = new JsonObject();

		SecurityKey ynFilter = new SecurityKey();
		ynFilter.setId("SYS_INSPECTION_YN");
		value.set("yn", this.securityKeyDao.search(ynFilter).get(0).getValue());

		SecurityKey dateFilter = new SecurityKey();
		dateFilter.setId("SYS_INSPECTION_DATE");
		value.set("date", this.securityKeyDao.search(dateFilter).get(0).getValue());

		SecurityKey descFilter = new SecurityKey();
		descFilter.setId("SYS_INSPECTION_DESC");
		value.set("desc", this.securityKeyDao.search(descFilter).get(0).getValue());

		return value;
	}

	@Override
	public String getBeaconUuid() {
		SecurityKey filter = new SecurityKey();
		filter.setId("BEACON_DEFAULT_UUID");

		return this.securityKeyDao.search(filter).get(0).getValue();
	}
}
