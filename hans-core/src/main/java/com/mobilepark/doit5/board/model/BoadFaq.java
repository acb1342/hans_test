package com.mobilepark.doit5.board.model;

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
 * @Filename     : BoadFaq.java
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
@Table(name = "TB_BOAD_FAQ")
public class BoadFaq extends AbstractModel<Long> implements Serializable{

	private static final long serialVersionUID = 4455669219545812857L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long sn_id;

	@Column(name = "CATEGORY")
	private String category;
	
	@Column(name = "QUESTION")
	private String question;
	
	@Column(name = "ANSWER")
	private String answer;
	
	@Column(name = "READ_CNT", columnDefinition = "int default 0")
	private Integer read_cnt;
	
	@Column(name = "DISPLAY_YN")
	private String disply_yn;
	
	@Column(name = "CUST_YN")
	private String cust_yn;
	
	@Column(name = "OWNER_YN")
	private String owner_yn;
	
	@Column(name = "INSTALLER_YN")
	private String installer_yn;
	
	@Column(name = "COUNSELOR_YN")
	private String counselor_yn;
	
	@Column(name = "FST_RG_USID", nullable = false)
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT", nullable = false)
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	@Override
	public Long getId() {
		return sn_id;
	}

	public Long getSn_id() {
		return sn_id;
	}

	public void setSn_id(Long sn_id) {
		this.sn_id = sn_id;
	}

	public String getCategory() {
		return category;
	}

	//전체,회원가입,회원인증,충전,요금 및 결제,기타
	public void setCategory(String category) {
		switch (Integer.parseInt(category)) {
		case 1 : this.category = "601101"; break;
		case 2 : this.category = "601102";	break;
		case 3 : this.category = "601103"; break;
		case 4 : this.category = "601104";	break;
		case 5 : this.category = "601105"; break;
		}
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public Integer getRead_cnt() {
		return read_cnt;
	}

	public void setRead_cnt(Integer read_cnt) {
		this.read_cnt = read_cnt;
	}

	public String getDisply_yn() {
		return disply_yn;
	}

	public void setDisply_yn(String disply_yn) {
		this.disply_yn = disply_yn;
	}

	public String getCust_yn() {
		return cust_yn;
	}

	public void setCust_yn(String cust_yn) {
		this.cust_yn = cust_yn;
	}

	public String getOwner_yn() {
		return owner_yn;
	}

	public void setOwner_yn(String owner_yn) {
		this.owner_yn = owner_yn;
	}

	public String getInstaller_yn() {
		return installer_yn;
	}

	public void setInstaller_yn(String installer_yn) {
		this.installer_yn = installer_yn;
	}

	public String getCounselor_yn() {
		return counselor_yn;
	}

	public void setCounselor_yn(String counselor_yn) {
		this.counselor_yn = counselor_yn;
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
	
	// 등록 시
	public void setYN(String groups) {
		this.cust_yn = "N";
		this.owner_yn = "N";
		this.installer_yn = "N";
		this.counselor_yn = "N";
	
		String[] groupArr = groups.split(";");
		for (String group : groupArr) {
			switch(Integer.parseInt(group)) {
			case 0 : this.cust_yn = "Y";		break;
			case 1 : this.owner_yn = "Y";		break;
			case 2 : this.installer_yn = "Y";	break;
			}
		}
		
	}
	
	// 검색시 
	public void setDisplayWho(String displayWho) {
		this.cust_yn = "N";
		this.owner_yn = "N";
		this.installer_yn = "N";
		this.counselor_yn = "N";
		
		switch(Integer.parseInt(displayWho)) {
		case 0 : this.cust_yn = "Y";		break;
		case 1 : this.owner_yn = "Y";		break;
		case 2 : this.installer_yn = "Y";	break;
		}
	}
	
}
