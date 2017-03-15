package com.mobilepark.doit5.history.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.history.model.HistCharge;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.history.service
 * @Filename     : HistChargeService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
public interface HistChargeService extends GenericService<HistCharge, Long> {
	public Long searchCountByMonthly(String usid, String fromDate, String toDate);
	
	public List<Map<String, Object>> searchByMonthly(String usid, String fromDate, String toDate, int page, int rowPerPage);
}
