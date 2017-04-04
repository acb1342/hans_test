package com.hans.sses.notice.model;

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
 * @Project        : evc-core
 * @Package      : com.mobilepark.doit5.notice.model
 * @Filename     : NoticeCust.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 23.       최초 버전
 * =================================================================================
 */

@Entity
@Table(name = "TB_BOAD_NOTICE_CUST")
public class NoticeCust extends AbstractModel<Long> implements Serializable {


	private static final long serialVersionUID = -1504436918525650632L;

	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "SN_ID", nullable = false)
		private Long snId;

		@Column(name = "TITLE")
		private String title;

		@Column(name = "BODY")
		private String body;
		
		@Column(name = "READ_CNT")
		private String readCnt;
		
		@Column(name = "DISPLAY_YN")
		private String displayYn;
		
		@Column(name = "LST_CH_USID")
		private String lstChUsid;
		
		@Column(name = "LST_CH_DT")
		private Date lstChDt;
		
		@Column(name = "FST_RG_USID")
		private String fstRgUsid;
		
		@Column(name = "FST_RG_DT")
		private Date fstRgDt;

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

		public String getReadCnt() {
			return readCnt;
		}

		public void setReadCnt(String readCnt) {
			this.readCnt = readCnt;
		}

		public String getDisplayYn() {
			return displayYn;
		}

		public void setDisplayYn(String displayYn) {
			this.displayYn = displayYn;
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

		@Override
		public Long getId() {
			return snId;
		}
}
