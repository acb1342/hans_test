package com.mobilepark.doit5.client.dao;

import java.util.List;

import com.mobilepark.doit5.client.model.ApplicationList;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.dao
 * @Filename     : ApplicationListDao.java
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
public interface ApplicationListDao extends GenericDao<ApplicationList, Integer> {

	List<ApplicationList> getAppListByOs(String os);

	List<ApplicationList> searchListByCondition(ApplicationList applicationList, String cpId, int page, int rowPerPage);

	int searchCountByCondition(ApplicationList applicationList, String cpId);

	List<ApplicationList> getListByAppId(String appId);

}
