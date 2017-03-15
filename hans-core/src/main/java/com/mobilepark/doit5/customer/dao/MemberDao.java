package com.mobilepark.doit5.customer.dao;

import java.util.Map;

import com.mobilepark.doit5.customer.model.Member;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.dao
 * @Filename     : MemberDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 28.       최초 버전
 * =================================================================================
 */
public interface MemberDao extends GenericDao<Member, Long> {
	public Map<String, Object> selectUserDetail(Long usid);
	
	public int updatePayment(Member member);
	
	public int updateUserStatus(Member member);
	
	public Member selectUserBySktId(String sktId);
	
	public Member insertUser(Member member);
	
	public int updateUser(Member member);
}
