package com.mobilepark.doit5.customer.service;

import java.util.Map;

import com.mobilepark.doit5.customer.model.Point;
import com.uangel.platform.service.GenericService;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : PointService.java
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

public interface PointService extends GenericService<Point, Long> {
	public Map<String, Object> getPoint(Long usid);
	
	public void setPrePay(Map<String, Object> map, Long usid);
	
}
