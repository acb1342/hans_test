package com.mobilepark.doit5.client.model;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mobilepark.doit5.common.Flag;
import com.mobilepark.doit5.provider.model.ContentProvider;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.provider.model
 * @Filename     : Agent.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 2.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "tbl_agent", catalog = "upush")
public class Agent extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "agent_sn", unique = true, nullable = false)
	private Integer agentSn;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cp_sn", nullable = false)
	private ContentProvider contentProvider;

	@Column(name = "agent_id", length = 16)
	private String agentId;

	@Column(name = "agent_type", length = 2)
	private String agentType;

	@Column(name = "used_yn", length = 2)
	@Enumerated(EnumType.STRING)
	private Flag usedYn;

	@Column(name = "ip_check_yn", length = 2)
	@Enumerated(EnumType.STRING)
	private Flag ipCheckYn;

	@Column(name = "status_check_yn", length = 2)
	@Enumerated(EnumType.STRING)
	private Flag statusCheckYn;

	@Column(name = "noti_sms_yn", length = 2)
	@Enumerated(EnumType.STRING)
	private Flag notiSmsYn;

	@Column(name = "noti_sms_mdn", length = 32)
	private String notiSmsMdn;

	@Column(name = "reg_date", length = 16)
	private String regDate;

	@Column(name = "update_date", length = 16)
	private String updateDate;

	public Agent() {
	}

	public Agent(ContentProvider contentProvider, String agentId, String agentType,
			Flag usedYn, Flag ipCheckYn, Flag statusCheckYn, Flag notiSmsYn,
			String notiSmsMdn, String regDate, String updateDate) {
		this.contentProvider = contentProvider;
		this.agentId = agentId;
		this.agentType = agentType;
		this.usedYn = usedYn;
		this.ipCheckYn = ipCheckYn;
		this.statusCheckYn = statusCheckYn;
		this.notiSmsYn = notiSmsYn;
		this.notiSmsMdn = notiSmsMdn;
		this.regDate = regDate;
		this.updateDate = updateDate;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.agentSn;
	}

	public void setId(Integer agentSn) {
		this.agentSn = agentSn;
	}

	public Integer getAgentSn() {
		return this.agentSn;
	}

	public void setAgentSn(Integer agentSn) {
		this.agentSn = agentSn;
	}

	public ContentProvider getContentProvider() {
		return this.contentProvider;
	}

	public void setContentProvider(ContentProvider contentProvider) {
		this.contentProvider = contentProvider;
	}

	public String getAgentId() {
		return this.agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getAgentType() {
		return this.agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public Flag getUsedYn() {
		return this.usedYn;
	}

	public void setUsedYn(Flag usedYn) {
		this.usedYn = usedYn;
	}

	public Flag getIpCheckYn() {
		return this.ipCheckYn;
	}

	public void setIpCheckYn(Flag ipCheckYn) {
		this.ipCheckYn = ipCheckYn;
	}

	public Flag getStatusCheckYn() {
		return this.statusCheckYn;
	}

	public void setStatusCheckYn(Flag statusCheckYn) {
		this.statusCheckYn = statusCheckYn;
	}

	public Flag getNotiSmsYn() {
		return this.notiSmsYn;
	}

	public void setNotiSmsYn(Flag notiSmsYn) {
		this.notiSmsYn = notiSmsYn;
	}

	public String getNotiSmsMdn() {
		return this.notiSmsMdn;
	}

	public void setNotiSmsMdn(String notiSmsMdn) {
		this.notiSmsMdn = notiSmsMdn;
	}

	public String getRegDate() {
		return this.regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUpdateDate() {
		return this.updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}
}
