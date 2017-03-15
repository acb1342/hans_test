package com.mobilepark.doit5.provider.service;

import com.mobilepark.doit5.provider.model.ContentProvider;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.service
 * @Filename     : ContentProviderService.java
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
public interface ContentProviderService extends GenericService<ContentProvider, Integer> {

	ContentProvider getById(String id);

}
