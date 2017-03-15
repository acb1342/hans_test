package com.mobilepark.doit5.customer.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.customer.model.RfidApplication;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : RfidApplicationService.java
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
public interface RfidApplicationService extends GenericService<RfidApplication, Long> {
	/**
	 * 사용자별 검색 건수
	 * 
	 * added by kodaji
	 * 2016.12.02
	 * 
	 * @param name
	 * @return
	 */
	public Long searchCountByMemberName(String name);
	
	/**
	 * 사용자별 검색 목록
	 * 
	 * added by kodaji
	 * 2016.12.02
	 * 
	 * @param name
	 * @param page
	 * @param rowPerPage
	 * @return
	 */
	public List<RfidApplication> searchByMemberName(String name, int page, int rowPerPage);
	public Map<String, Object> getRfidCard(Map<String, Object> param);
}
