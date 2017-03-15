package com.mobilepark.doit5.push.service;

import com.mobilepark.doit5.push.model.PushSvc;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.service
 * @Filename     : PushSvcService.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
public interface PushSvcService extends GenericService<PushSvc, Integer> {

	int pushReservedSend() throws Exception;

}
