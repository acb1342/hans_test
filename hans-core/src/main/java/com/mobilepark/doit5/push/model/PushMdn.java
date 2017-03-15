package com.mobilepark.doit5.push.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.model
 * @Filename     : PushMdn.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TBL_MDN", catalog = "upush")
public class PushMdn extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@ManyToOne
	@JoinColumn(name = "PUSH_ID", nullable = false)
	private PushSvc pushSvc;

	@Column(name = "MDN", length = 32)
	private String mdn;

	public PushMdn() {
		this(null, null, null);
	}

	public PushMdn(Integer id) {
		this(id, null, null);
	}

	public PushMdn(Integer id, PushSvc pushSvc) {
		this(id, pushSvc, null);
	}

	public PushMdn(PushSvc pushSvc, String mdn) {
		this(null, pushSvc, mdn);
	}

	public PushMdn(Integer id, PushSvc pushSvc, String mdn) {
		super();
		this.id = id;
		this.pushSvc = pushSvc;
		this.mdn = mdn;
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMdn() {
		return this.mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public PushSvc getPushSvc() {
		return this.pushSvc;
	}

	public void setPushSvc(PushSvc pushSvc) {
		this.pushSvc = pushSvc;
	}
}
