package com.hans.sses.common;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.common
 * @Filename     : Pagination.java
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
 * =================================================================================*/
public class Pagination {
	
	private int page;
    private int size;
  
    public Pagination(int page, int size) {
    	this.page = page;
        this.size = size;
    }
  
    public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
    
    public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getStartRow() {
    	return (page - 1) * size;
    }
}
