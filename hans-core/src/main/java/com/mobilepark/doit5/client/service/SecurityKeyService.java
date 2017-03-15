package com.mobilepark.doit5.client.service;

import com.mobilepark.doit5.client.model.SecurityKey;
import com.uangel.platform.collection.JsonObject;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : SecurityKeyService.java
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
public interface SecurityKeyService extends GenericService<SecurityKey, String> {
	void updateSecurityKey() throws Exception;

	JsonObject getSystemInspectionValues();

	String getBeaconUuid();
}
