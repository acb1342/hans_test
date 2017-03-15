package com.mobilepark.doit5.board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.board.dao.BoardDao;
import com.mobilepark.doit5.board.model.Board;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : BoardServiceImpl.java
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

@Transactional
public class BoardServiceImpl extends AbstractGenericService<Board, Long> implements BoardService {
	
	@Autowired
	private BoardDao boardDao;

	@Override
	protected GenericDao<Board, Long> getGenericDao() {
		return this.boardDao;
	}

	@Override
	public List<Board> searchAll(Board board, String searchValue, int pageNum, int rowPerPage) {
		return this.boardDao.searchAll(board, searchValue, pageNum, rowPerPage);
	}

}
