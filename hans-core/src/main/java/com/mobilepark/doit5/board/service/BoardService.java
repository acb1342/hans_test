package com.mobilepark.doit5.board.service;

import java.util.List;

import com.mobilepark.doit5.board.model.Board;
import com.uangel.platform.service.GenericService;
/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.service
 * @Filename     : BoardService.java
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

public interface BoardService extends GenericService<Board, Long> {

	public abstract List<Board> searchAll(Board board, String searchValue, int pageNum, int rowPerPage);

}
