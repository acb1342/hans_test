package com.mobilepark.doit5.client.dao;

import java.util.List;

import com.mobilepark.doit5.client.model.Application;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.dao
 * @Filename     : ApplicationDao.java
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
public interface ApplicationDao extends GenericDao<Application, Integer> {

	Application getByAppName(String appName);

	Application getByAppId(String appId);

	List<Application> getApps(String appId);

	Application getByCpId(String cpId);

	List<Application> getAppsByCpId(String cpId);

	Application getByCpIdAppId(String cpId, String appId);

	List<Application> searchByCondition(Application application, String cpId, String appId, String os);

	int searchCountByCondition(Application application, String cpId, String appId, String os);

	List<Application> getListByCpId(String cpId);

}
