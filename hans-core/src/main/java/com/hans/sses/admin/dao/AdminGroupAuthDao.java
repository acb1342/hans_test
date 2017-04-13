package com.hans.sses.admin.dao;

import java.util.List;
import java.util.Map;

import com.hans.sses.admin.model.AdminGroupAuth;
import com.uangel.platform.dao.GenericDao;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : CmsGroupAuthDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 5.      최초 버전
 * =================================================================================
 */
@Repository
@Transactional(value = "dataSourceTransactionManager")
public interface AdminGroupAuthDao {

	abstract public List<Map<String, Object>> searchGroupAuth(@Param("groupId") Integer groupId);

	AdminGroupAuth get(Integer groupId, Integer menuId);

}
