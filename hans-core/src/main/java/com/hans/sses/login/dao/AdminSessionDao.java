package com.hans.sses.login.dao;

import com.hans.sses.common.Channel;
import com.hans.sses.login.model.AdminSession;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.login.dao
 * @Filename     : LoginSessionDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 7.       최초 버전
 * =================================================================================
 */
public interface AdminSessionDao extends GenericDao<AdminSession, Long> {
	Integer clearSessionAll(Channel channel);
}
