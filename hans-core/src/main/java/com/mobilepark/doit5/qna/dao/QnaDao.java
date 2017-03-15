package com.mobilepark.doit5.qna.dao;

import java.util.Map;

import com.mobilepark.doit5.qna.model.QnaCust;
import com.uangel.platform.dao.GenericDao;
/*==================================================================================
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.qna.dao
 * @Filename     : QnaDao.java
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
public interface QnaDao extends GenericDao<QnaCust, Long>{
	public Map<String, Object> selectQnaList(Integer page, Integer size, String openYn, String searchField, String searchKeyword, boolean isUser, boolean isOd, boolean isWk);
	
	public Map<String, Object> selectQnaDetail(Long snId, String usid);
	
	public QnaCust insertQna(QnaCust qnaCust);
	
	public int updateQna(QnaCust qnaCust);
	
	public void deleteQna(Long snId);
}
