package com.hans.sses.board.dao;

import java.util.List;

import com.hans.sses.board.model.Board;
import com.uangel.platform.dao.GenericDao;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.dao
 * @Filename     : BoardDao.java
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
public interface BoardDao extends GenericDao<Board, Long>{
	
	public abstract List<Board> searchAll(Board board, String searchValue, int pageNum, int rowPerPage);
}
