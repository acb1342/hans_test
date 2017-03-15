package com.mobilepark.doit5.editor.dao;

import com.mobilepark.doit5.common.UseFlag;
import org.hibernate.Criteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.mobilepark.doit5.editor.model.ContentEditor;
import com.uangel.platform.dao.HibernateGenericDao;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.editor.dao
 * @Filename     : ContentEditorDaoHibernate.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 5. 26.      최초 버전
 * =================================================================================
 */
public class ContentEditorDaoHibernate extends HibernateGenericDao<ContentEditor, Long> implements ContentEditorDao {
	@Override
	protected Criteria getCriteria(ContentEditor entity) {
		Criteria criteria = this.getCurrentSession().createCriteria(ContentEditor.class);

		if (entity != null) {
			Example example = Example.create(entity)
					.enableLike(MatchMode.ANYWHERE)
					.excludeProperty("createDate")
					.excludeProperty("modifyDate");

			criteria.add(example)
					.addOrder(Order.desc("id"));

			criteria.add(Restrictions.eq("useFlag", UseFlag.Y));
		}

		return criteria;
	}
}
