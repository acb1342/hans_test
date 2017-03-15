package com.mobilepark.doit5.subscriber.service;

import java.util.List;

import com.mobilepark.doit5.subscriber.model.EndUser;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.service
 * @Filename     : EndUserService.java
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
public interface EndUserService extends GenericService<EndUser, Integer> {

	int getCountByAppId(String appId);

	List<EndUser> searchByCondition(String type, String mdn, String pushToken, int pageNum, int rowPerPage);

	int searchCountByCondition(String type, String mdn, String pushToken);

}
