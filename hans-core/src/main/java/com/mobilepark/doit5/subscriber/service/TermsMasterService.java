package com.mobilepark.doit5.subscriber.service;

import java.util.List;

import com.mobilepark.doit5.common.DeviceType;
import com.mobilepark.doit5.common.TermsType;
import com.mobilepark.doit5.subscriber.model.TermsMaster;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.service
 * @Filename     : TermsMasterService.java
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
public interface TermsMasterService extends GenericService<TermsMaster, TermsMaster.TermsId> {
	TermsMaster getLastTerms(DeviceType deviceType, TermsType termsType);

	List<TermsMaster> searchByType(DeviceType deviceType, TermsType termsType);

	List<TermsMaster> searchByTerms(TermsMaster termsMaster, int page, int rowPerPage);

	long countByTerms(TermsMaster termsMaster);

	TermsMaster getLastTerms(DeviceType deviceType, TermsType termsType, String termsVerId);

	TermsMaster get(DeviceType deviceType, TermsType type, String version);

	int delete(DeviceType deviceType, TermsType type, String version);
}
