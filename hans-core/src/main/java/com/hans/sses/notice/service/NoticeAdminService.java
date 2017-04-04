package com.hans.sses.notice.service;

import java.util.Map;

import com.hans.sses.notice.model.NoticeAdmin;
import com.uangel.platform.service.GenericService;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : NoticeAdminService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE             Description
 * =================================================================================
 *  1.0	   2016. 11. 14.      최초 버전
 * =================================================================================
 */

public interface NoticeAdminService extends GenericService<NoticeAdmin, Long> {
	public Map<String, Object> getNoticeList(Integer page, Integer size);
	
	public Map<String, Object> getNoticeDetail(Long snId);
}
