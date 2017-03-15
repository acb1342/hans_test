package com.mobilepark.doit5.customer.service;

import java.util.Date;

import com.mobilepark.doit5.customer.model.Member;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.mobilepark.doit5.customer.dao.FavoritesDao;
import com.mobilepark.doit5.customer.model.Favorites;
import com.mobilepark.doit5.elcg.model.Bd;
import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.service
 * @Filename     : FavoritesServiceImpl.java
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
@Transactional
public class FavoritesServiceImpl extends AbstractGenericService<Favorites, Long> implements FavoritesService {

	@Autowired
	private FavoritesDao favoritesDao;
	
	@Override
	protected GenericDao<Favorites, Long> getGenericDao() {
		return favoritesDao;
	}

	@Override
	public void setFavorites(String setYn, Long bdId, Long usid) {
		if (StringUtils.equals(setYn, "Y")) {
			Bd bd = new Bd();
			bd.setBdId(bdId);
			
			Member member = new Member();
			member.setId(usid);
			
			Favorites favorites = new Favorites();
			favorites.setMember(member);
			favorites.setBd(bd);
			favorites.setFstRgUsid(usid.toString());
			favorites.setFstRgDt(new Date());
			favorites.setLstChUsid(usid.toString());
			favorites.setLstChDt(new Date());
			
			favoritesDao.insertFavorites(favorites);
		} else {
			favoritesDao.deleteFavorites(usid, bdId);
		}
	}
}
