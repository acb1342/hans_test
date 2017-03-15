package com.mobilepark.doit5.client.dao;

import com.mobilepark.doit5.client.model.NsuAgentLib;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.dao
 * @Filename     : NsuAgentLibDao.java
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
public interface NsuAgentLibDao extends GenericDao<NsuAgentLib, Integer> {

	NsuAgentLib getAvailableLastNsu(String os);

	NsuAgentLib getAvailableLastNsu(String os, String locale);

	NsuAgentLib get(String version, String os, String langCode);
}
