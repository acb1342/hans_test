package com.mobilepark.doit5.customer.service;

import java.util.Map;

import com.mobilepark.doit5.customer.model.RfidCard;
import com.mobilepark.doit5.customer.model.RfidApplication;
import com.uangel.platform.service.GenericService;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : RfidCardService.java
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

public interface RfidCardService extends GenericService<RfidCard, Long> {
	public int updateLoseDt(Long snId);
	
	public Map<String, Object> validCardNo(RfidCard rfidCard);
	
	public RfidApplication insertCardReq(RfidApplication rfidApplication);
	
}
