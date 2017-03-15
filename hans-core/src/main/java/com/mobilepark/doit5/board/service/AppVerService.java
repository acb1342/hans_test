package com.mobilepark.doit5.board.service;

import java.util.List;
import java.util.Map;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service
 * @Filename     : AppVerService.java
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


public interface AppVerService {
	int count(Map<String, Object> param);
	
	List<Map<String, Object>> search(Map<String, Object> param);
	
	Map<String, Object> get(Long id);
	
	void create(Map<String, Object> param);
	
	void update(Map<String, Object> param);
	
	int delete(Long id);
	
	Map<String, Object> getAppVer_api(String ver, String clientType, String targetType);
	
}
