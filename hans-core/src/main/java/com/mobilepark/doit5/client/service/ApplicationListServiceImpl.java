package com.mobilepark.doit5.client.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.mobilepark.doit5.client.model.ApplicationList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.mobilepark.doit5.client.dao.ApplicationListDao;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
import com.uangel.platform.util.FileUtil;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.service
 * @Filename     : ApplicationListServiceImpl.java
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
public class ApplicationListServiceImpl extends AbstractGenericService<ApplicationList, Integer> implements ApplicationListService {
	@Autowired
	private ApplicationListDao applicationListDao;

	@Override
	protected GenericDao<ApplicationList, Integer> getGenericDao() {
		return this.applicationListDao;
	}

	@Override
	public List<ApplicationList> getAppListByOs(String os) {
		return this.applicationListDao.getAppListByOs(os);
	}

	@Override
	public String saveFileInTheDirectory(String baseDir, String saveFileName, MultipartFile file) throws IOException {

		// TraceLog.info("base dir = [%s]", baseDir);
		// TraceLog.info("file name  = [%s]", file.getOriginalFilename());

		FileUtil.makeDirectory(baseDir);

		File savedFile = new File(baseDir, saveFileName);
		file.transferTo(savedFile);

		// TraceLog.info("save path  = [%s]", savedFile.getPath());

		return savedFile.getPath();
	}

	@Override
	public List<ApplicationList> searchListByCondition(ApplicationList applicationList, String cpId, int page, int rowPerPage) {
		return this.applicationListDao.searchListByCondition(applicationList, cpId, page, rowPerPage);
	}

	@Override
	public int searchCountByCondition(ApplicationList applicationList, String cpId) {
		return this.applicationListDao.searchCountByCondition(applicationList, cpId);
	}

	@Override
	public List<ApplicationList> getListByAppId(String pkgId) {
		return this.applicationListDao.getListByAppId(pkgId);
	}
}
