package com.mobilepark.doit5.qna.dao;

import java.util.Map;

import com.mobilepark.doit5.qna.model.QnaAdmin;
import com.uangel.platform.dao.GenericDao;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.qna.dao
 * @Filename     : QnaAdminDao.java
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
public interface QnaAdminDao extends GenericDao<QnaAdmin, Long>{
	public Map<String, Object> selectQnaList(Integer page, Integer size, String openYn, String searchField, String searchKeyword);
	
	public Map<String, Object> selectQnaDetail(Long snId, String usid);
	
	public QnaAdmin insertQna(QnaAdmin qnaAdmin);
	
	public int updateQna(QnaAdmin qnaAdmin);
	
	public void deleteQna(Long snId);
}
