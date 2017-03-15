package com.mobilepark.doit5.push.dao;

import java.util.List;

import com.mobilepark.doit5.push.model.PushSvc;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.dao
 * @Filename     : PushSvcDao.java
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
public interface PushSvcDao extends GenericDao<PushSvc, Integer> {

	List<PushSvc> searchReservedSendList();

}
