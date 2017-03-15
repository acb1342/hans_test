package com.mobilepark.doit5.elcg.service;

import java.util.List;
import java.util.Map;

import com.mobilepark.doit5.elcg.model.BdGroup;
import com.uangel.platform.service.GenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.elcg.service
 * @Filename     : BdGroupService.java
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

public interface BdGroupService extends GenericService<BdGroup, Long>{
	public List<Object> getBdGroupList(String searchKeyword, Long usid);
	
	public List<Object> getFavoriteBdGroupList(Long usid);
	
	public Map<String, Object> getBdGroupDetail(Long bdGroupId, Long bdId);
	
	public List<BdGroup> searchBdGroupName(BdGroup bdGroup, String search);
}
