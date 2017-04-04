package com.hans.sses.board.dao;

import java.util.List;

import com.hans.sses.board.model.BoadQna;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.dao
 * @Filename     : BoadQnaDao.java
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

public interface BoadQnaDao extends GenericDao<BoadQna, Long> {
	List<BoadQna> searchQnaAllKeys(BoadQna boadQna, String searchValue, int pageNum, int rowPerPage, String fromDate, String toDate);
	List<BoadQna> searchQna(BoadQna boadQna, int page, int rowPerPage, String sortCriterion, String sortDirection, String fromDate, String toDate);
}
