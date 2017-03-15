package com.mobilepark.doit5.client.service;

import java.io.File;

import com.mobilepark.doit5.client.dao.NsuAgentLibDao;
import com.mobilepark.doit5.client.model.NsuAgentLib;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.log.TraceLog;
import com.uangel.platform.service.AbstractGenericService;
import com.uangel.platform.util.Env;
import com.uangel.platform.util.FileUtil;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : NsuAgentLibServiceImpl.java
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
@Transactional
public class NsuAgentLibServiceImpl extends AbstractGenericService<NsuAgentLib, Integer> implements NsuAgentLibService {
	@Autowired
	private NsuAgentLibDao nsuAgentLibDao;

	@Override
	protected GenericDao<NsuAgentLib, Integer> getGenericDao() {
		return this.nsuAgentLibDao;
	}

	@Override
	public void create(NsuAgentLib nsuAgentLib, MultipartFile agentFile) {
		if (agentFile != null && !agentFile.isEmpty()) {
			this.saveNsuAgentFile(nsuAgentLib, agentFile);
		}
		super.create(nsuAgentLib);
	}

	@Override
	public void update(NsuAgentLib nsuAgentLib, MultipartFile agentFile) {
		if (agentFile != null && !agentFile.isEmpty()) {
			this.saveNsuAgentFile(nsuAgentLib, agentFile);
		}
		super.update(nsuAgentLib);
	}

	private void saveNsuAgentFile(NsuAgentLib nsuAgentLib, MultipartFile agentFile) {
		try {
			String uploadDir = Env.get("nsu.agent.uploadDir");
			FileUtil.makeDirectory(uploadDir);

			File nsuAgentFile = new File(uploadDir, agentFile.getOriginalFilename());
			agentFile.transferTo(nsuAgentFile);

			TraceLog.debug("path:%s, fileName:%s", nsuAgentFile.getPath(), nsuAgentFile.getName());
			nsuAgentLib.setFilePath(nsuAgentFile.getPath());

		} catch (Exception e) {
			TraceLog.error(e.getMessage(), e);
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public void deployLastVersion(String os, String langCode) {
		TraceLog.debug("os:%s, langCode:%s", os, langCode);

		NsuAgentLib nsuAgentLib = this.getAvailableLastNsu(os, langCode);
		String deployFilename = "";
		if ("ANDROID".equalsIgnoreCase(os)) {
			deployFilename = Env.get("nsu.android.deploy.filename");
		} else if ("IOS".equalsIgnoreCase(os)) {
			deployFilename = Env.get("nsu.ios.deploy.filename");
		}
		String srcDir = Env.get("nsu.agent.uploadDir");
		String destDir = Env.get("nsu.file.uploadDir");
		String fileName = nsuAgentLib.getFilePath();

		TraceLog.debug("fileName[%s] os[%s] langCode[%s]", fileName, os, langCode);

		try {
			if (StringUtils.isNotEmpty(fileName)) {
				File lastAgentFile = new File(fileName);
				File deployAgentFile = new File(destDir, deployFilename);
				FileUtil.copyFile(lastAgentFile, deployAgentFile);
				TraceLog.info("deploy last nsu file [os:%s, lang:%s, file:[%s:%s]",
						os, langCode, lastAgentFile.getAbsoluteFile(), deployAgentFile.getAbsolutePath());
			} else {
				String errorMsg = String.format("last nsu file is invalid!!! [file:%s, fileSize:%d]", fileName, fileName.length());
				throw new IllegalStateException(errorMsg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public NsuAgentLib getAvailableLastNsu(String os, String langCode) {
		return this.nsuAgentLibDao.getAvailableLastNsu(os, langCode);
	}

	@Override
	public NsuAgentLib get(String version, String os, String langCode) {
		return this.nsuAgentLibDao.get(version, os, langCode);
	}

	@Override
	public NsuAgentLib getAvailableLastNsu(String os) {
		return this.nsuAgentLibDao.getAvailableLastNsu(os);
	}
}
