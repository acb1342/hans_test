package com.mobilepark.doit5.customer.dao;

import java.util.List;

import com.mobilepark.doit5.customer.model.RfidApplication;
import com.uangel.platform.dao.GenericDao;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.dao
 * @Filename     : RfidApplicationDao.java
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
public interface RfidApplicationDao extends GenericDao<RfidApplication, Long>{
	public RfidApplication insertCardReq(RfidApplication rfidApplication);
	
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
}
