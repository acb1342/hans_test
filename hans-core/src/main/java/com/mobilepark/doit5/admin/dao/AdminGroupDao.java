package com.mobilepark.doit5.admin.dao;

import com.mobilepark.doit5.admin.model.AdminGroup;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : AdminGroupDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 22.      최초 버전
 * =================================================================================
 */
public interface AdminGroupDao extends GenericDao<AdminGroup, Integer> {

	AdminGroup getByName(String name);
}
