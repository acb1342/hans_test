package com.hans.sses.notice.dao;

import java.util.Map;

import com.hans.sses.notice.model.NoticeAdmin;
import com.uangel.platform.dao.GenericDao;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : NoticeAdminDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 21.      최초 버전
 * =================================================================================
 */
public interface NoticeAdminDao extends GenericDao<NoticeAdmin, Long>{
	public Map<String, Object> selectNoticeList(Integer page, Integer size);
	
	public Map<String, Object> selectNoticeDetail(Long snId);
}
