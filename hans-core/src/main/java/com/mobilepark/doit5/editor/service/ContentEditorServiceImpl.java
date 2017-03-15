package com.mobilepark.doit5.editor.service;

import com.mobilepark.doit5.editor.dao.ContentEditorDao;
import com.mobilepark.doit5.editor.model.ContentEditor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.uangel.platform.dao.GenericDao;
import com.uangel.platform.service.AbstractGenericService;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.editor.service
 * @Filename     : ContentEditorServiceImpl.java
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
@Transactional
public class ContentEditorServiceImpl extends AbstractGenericService<ContentEditor, Long> implements ContentEditorService {
	@Autowired
	private ContentEditorDao contentEditorDao;

	@Override
	protected GenericDao<ContentEditor, Long> getGenericDao() {
		return this.contentEditorDao;
	}
}
