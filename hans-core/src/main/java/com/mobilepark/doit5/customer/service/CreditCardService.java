package com.mobilepark.doit5.customer.service;

import java.util.Map;

import com.mobilepark.doit5.customer.model.CreditCard;
import com.uangel.platform.service.GenericService;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.pay.service
 * @Filename     : PayService.java
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

public interface CreditCardService extends GenericService<CreditCard, Long> {
	public Map<String, Object> getPostPayDetail(Long snId);
	
	public void insertPostPay(Map<String, Object> map, Long usid);
	
	public int updatePostPay(CreditCard creditCard);
	
}
