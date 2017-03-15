package com.mobilepark.doit5.client.service;

import com.mobilepark.doit5.client.model.NsuAgentLib;
import org.springframework.web.multipart.MultipartFile;

import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.service
 * @Filename     : NsuAgentLibService.java
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
public interface NsuAgentLibService extends GenericService<NsuAgentLib, Integer> {

	void create(NsuAgentLib nsuAgentLib, MultipartFile agentFile);

	void update(NsuAgentLib nsuAgentLib, MultipartFile agentFile);

	void deployLastVersion(String os, String langCode);

	NsuAgentLib getAvailableLastNsu(String os, String langCode);

	NsuAgentLib get(String version, String os, String langCode);

	NsuAgentLib getAvailableLastNsu(String os);
}
