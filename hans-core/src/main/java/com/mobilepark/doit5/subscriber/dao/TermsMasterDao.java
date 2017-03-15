package com.mobilepark.doit5.subscriber.dao;

import java.util.List;

import com.mobilepark.doit5.common.DeviceType;
import com.mobilepark.doit5.common.TermsType;
import com.mobilepark.doit5.subscriber.model.TermsMaster;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.dao
 * @Filename     : TermsMasterDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
public interface TermsMasterDao extends GenericDao<TermsMaster, TermsMaster.TermsId> {
	TermsMaster get(DeviceType deviceType, TermsType termsType, String termsVerId);

	TermsMaster getLastTerms(DeviceType deviceType, TermsType termsType, String termsVerId);

	List<TermsMaster> searchByType(DeviceType deviceType, TermsType termsType);

	int delete(String version, DeviceType deviceType, TermsType type);

	String maxVersion(DeviceType deviceType, TermsType type, String today);

	List<TermsMaster> searchByTerms(TermsMaster termsMaster, int page, int rowPerPage);

	long countByTerms(TermsMaster termsMaster);
}
