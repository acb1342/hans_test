package com.mobilepark.doit5.customer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.model
 * @Filename     : RfidApplication.java
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
@Entity
@Table(name = "TB_CUST_RFID_APPLICATION")
public class RfidApplication extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 4360042465105793349L;

	/**
	 * TB_CUST_MEMBER 와 일 대 N
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "USID")
	private Member member;

	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "ZIPCODE")
	private String zipcode;
	
	@Column(name = "ADDR")
	private String addr;
	
	@Column(name = "CARD_NO")
	private String cardNo;
	
	@Column(name = "BODY")
	private String body;
	
	@Column(name = "RC_USID")
	private String rcUsid;
	
	@Column(name = "RC_DT")
	private Date rcDt;
	
	@Column(name = "OD_USID")
	private String odUsid;
	
	@Column(name = "OD_DT")
	private Date odDt;
	
	@Column(name = "WK_USID")
	private String wkUsid;
	
	@Column(name = "WK_DT")
	private Date wkDt;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT")
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;

	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	@OneToOne
	@JoinColumn(name = "SN_ID")
	private RfidCard rfidCard;
	
	@Transient
	private Date stDt;
	

	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getRcUsid() {
		return rcUsid;
	}

	public void setRcUsid(String rcUsid) {
		this.rcUsid = rcUsid;
	}

	public Date getRcDt() {
		return rcDt;
	}

	public void setRcDt(Date rcDt) {
		this.rcDt = rcDt;
	}

	public String getOdUsid() {
		return odUsid;
	}

	public void setOdUsid(String odUsid) {
		this.odUsid = odUsid;
	}

	public Date getOdDt() {
		return odDt;
	}

	public void setOdDt(Date odDt) {
		this.odDt = odDt;
	}

	public String getWkUsid() {
		return wkUsid;
	}

	public void setWkUsid(String wkUsid) {
		this.wkUsid = wkUsid;
	}

	public Date getWkDt() {
		return wkDt;
	}

	public void setWkDt(Date wkDt) {
		this.wkDt = wkDt;
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

	public RfidCard getRfidCard() {
		return rfidCard;
	}

	public void setRfidCard(RfidCard rfidCard) {
		this.rfidCard = rfidCard;
	}
	
	public Date getStDt() {
		return stDt;
	}
	
	public void setStDt(Date stDt) {
		this.stDt = stDt;
	}

}
