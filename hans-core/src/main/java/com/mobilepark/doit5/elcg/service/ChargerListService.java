package com.mobilepark.doit5.elcg.service;

import java.util.Map;

import com.mobilepark.doit5.elcg.model.ChargerList;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : ChargerListService.java
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
 * =================================================================================*/

public interface ChargerListService extends GenericService<ChargerList, String>{
	public Map<String, Object> getChargerInfo(String serialNo) throws Exception;
}
