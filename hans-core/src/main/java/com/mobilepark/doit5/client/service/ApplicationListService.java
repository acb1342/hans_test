package com.mobilepark.doit5.client.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mobilepark.doit5.client.model.ApplicationList;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : ApplicationListService.java
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
public interface ApplicationListService extends GenericService<ApplicationList, Integer> {
	public String saveFileInTheDirectory(String baseDir, String saveFileName, MultipartFile file) throws IOException;

	List<ApplicationList> getAppListByOs(String os);

	public List<ApplicationList> searchListByCondition(ApplicationList condition, String cpId, int page, int rowPerPage);

	public int searchCountByCondition(ApplicationList condition, String cpId);

	public List<ApplicationList> getListByAppId(String pkgId);
}
