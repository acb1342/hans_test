package com.hans.sses.board.model.cust;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.model
 * @Filename     : BoadNoticeCust.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 14.       최초 버전
 * =================================================================================*/


@Entity
@Table(name = "TB_BOAD_NOTICE_CUST")
public class BoadNoticeCust extends AbstractModel<Long> implements Serializable{
	
	private static final long serialVersionUID = -6341261367446533093L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID")
	private Long snId;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "BODY")
	private String body;
	
	@Column(name = "READ_CNT")
	private Integer readCnt = 0;
	
	@Column(name = "DISPLAY_YN")
	private String displayYn;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT")
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	@Override
	public Long getId() {
		return snId;
	}

	public Long getSnId() {
		return snId;
	}

	public void setSnId(Long snId) {
		this.snId = snId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	public String getDisplayYn() {
		return displayYn;
	}

	public void setDisplayYn(String displayYn) {
		this.displayYn = displayYn;
	}

	public String getFstRgUsid() {
		return fstRgUsid;
	}

	public void setFstRgUsid(String fstRgUsid) {
		this.fstRgUsid = fstRgUsid;
	}

	public Date getFstRgDt() {
		return fstRgDt;
	}

	public void setFstRgDt(Date fstRgDt) {
		this.fstRgDt = fstRgDt;
	}

	public String getLstChUsid() {
		return lstChUsid;
	}

	public void setLstChUsid(String lstChUsid) {
		this.lstChUsid = lstChUsid;
	}

	public Date getLstChDt() {
		return lstChDt;
	}

	public void setLstChDt(Date lstChDt) {
		this.lstChDt = lstChDt;
	}
	
}
