package com.mobilepark.doit5.customer.dao;

import com.mobilepark.doit5.customer.model.Favorites;
import com.uangel.platform.dao.GenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.dao
 * @Filename     : FavoritesDao.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 30.       최초 버전
 * =================================================================================
 */
public interface FavoritesDao extends GenericDao<Favorites, Long> {
	public Favorites insertFavorites(Favorites favorites);
	
	public int deleteFavorites(Long usid, Long bdId);
}
