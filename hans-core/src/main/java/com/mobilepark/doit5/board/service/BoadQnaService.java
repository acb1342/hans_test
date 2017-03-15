package com.mobilepark.doit5.board.service;

import java.util.List;

import com.mobilepark.doit5.board.model.BoadQna;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;

import com.uangel.platform.service.GenericService;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.service
 * @Filename     : BoadQnaService.java
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


public interface BoadQnaService extends GenericService<BoadQna, Long> {
	public abstract List<BoadQna> searchQnaAllKeys(BoadQna boadQna, String searchValue, int pageNum, int rowPerPage, String fromDate, String toDate);
	public abstract List<BoadQna> searchQna(BoadQna boadQna, int page, int rowPerPage, String sortCriterion, String sortDirection, String fromDate, String toDate); 
}
