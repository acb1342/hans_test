package com.mobilepark.doit5.board.service;

import java.util.List;

import com.mobilepark.doit5.board.model.BoadNotice;
import com.uangel.platform.service.GenericService;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service
 * @Filename     : BoadNoticeService.java
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


public interface BoadNoticeService extends GenericService<BoadNotice, Long> {
	
	public abstract List<BoadNotice> search(BoadNotice boadNotice, int page, int rowPerPage, String sortCriterion, String sortDirection, String fromDate, String toDate);
}
