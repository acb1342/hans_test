package com.mobilepark.doit5.customer.dao;

import java.util.Map;

import com.mobilepark.doit5.customer.model.RfidCard;
import com.uangel.platform.dao.GenericDao;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.customer.dao
 * @Filename     : RfidCardDao.java
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
public interface RfidCardDao extends GenericDao<RfidCard, Long>{
	public int updateLoseDt(Long snId);
	
	public Map<String, Object> isValidCardNo(RfidCard rfidCard);
	
	public Long isCardNo(RfidCard rfidCard);
	
	public Long isOverCardNo(RfidCard rfidCard);
}
