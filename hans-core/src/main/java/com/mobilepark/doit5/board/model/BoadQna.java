package com.mobilepark.doit5.board.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Formula;

import com.uangel.platform.log.TraceLog;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.model
 * @Filename     : BoadQna.java
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
@Table(name = "TB_BOAD_QNA")
public class BoadQna extends AbstractModel<Long> implements Serializable{

	private static final long serialVersionUID = 4890070528911226825L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long sn_id;
	
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "OPEN_YN")
	private String open_yn;

	@Column(name = "READ_CNT")
	private Integer read_cnt;
	
	@Column(name = "ANSWER_YN")
	private String answer_yn;
	
	@Column(name = "PEN_NAME")
	private String pen_name;
	
	@Column(name = "CUST_YN")
	private String cust_yn;
	
	@Column(name = "OWNER_YN")
	private String owner_yn;
	
	@Column(name = "INSTALLER_YN")
	private String installer_yn;
	
	@Column(name = "COUNSELOR_YN")
	private String counselor_yn;
	
	@Column(name = "QUESTION_ID")
	private Long question_id;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "BODY")
	private String body;
	
	@Column(name = "PASSWD")
	private String passwd;
	
	@Column(name = "ANSWER_STATUS")
	private String answer_status;
	
	@Column(name = "FST_RG_USID", nullable = false)
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT", nullable = false)
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	// 사용자 구분
	@Formula("( SELECT a.ADMIN_GROUP_ID FROM TB_MGMT_ADMIN a WHERE FST_RG_USID = a.ADMIN_ID )")
	String writerType;

	// TID 일때
	@Formula("( SELECT count(*) FROM TB_CUST_MEMBER b WHERE FST_RG_USID = b.USID )")
	int TwriterType;
	
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

	public Long getQuestion_id() {
		return question_id;
	}

	public void setQuestion_id(Long question_id) {
		this.question_id = question_id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Integer getRead_cnt() {
		return read_cnt;
	}

	public void setRead_cnt(Integer read_cnt) {
		this.read_cnt = read_cnt;
	}

	public String getOpen_yn() {
		return open_yn;
	}

	public void setOpen_yn(String open_yn) {
		this.open_yn = open_yn;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getAnswer_yn() {
		return answer_yn;
	}

	public void setAnswer_yn(String answer_yn) {
		this.answer_yn = answer_yn;
	}

	public String getAnswer_status() {
		return answer_status;
	}

	public void setAnswer_status(String answer_status) {
		this.answer_status = answer_status;
	}

	public String getPen_name() {
		return pen_name;
	}

	public void setPen_name(String pen_name) {
		this.pen_name = pen_name;
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

	// return: 0 - Tid사용자 / 99 - 사용자 등록 정보 없음
	public String getWriterType() {
		if (StringUtils.isEmpty(writerType)) {
			if (TwriterType > 0) {
				return "0";
			}
			else return "99";
		}
		return writerType;
	}

	public void setWriterType(String writerType) {
		this.writerType = writerType;
	}
	
	public void setUserType(BoadQna question) {
		this.counselor_yn = "N";
		this.installer_yn = "N";
		this.owner_yn = "N";
		this.cust_yn = "N";
		if (StringUtils.equals(question.getCounselor_yn(), "Y")) this.counselor_yn = "Y";
		if (StringUtils.equals(question.getInstaller_yn(), "Y")) this.installer_yn = "Y";
		if (StringUtils.equals(question.getOwner_yn(), "Y")) this.owner_yn = "Y";
		if (StringUtils.equals(question.getCust_yn(), "Y")) this.cust_yn = "Y";
	}

	// 등록시 디폴트 셋
	public void setDefault() {
		this.read_cnt = 0;
		this.cust_yn = "N";
		this.installer_yn = "N";
		this.owner_yn = "N";
		this.counselor_yn = "N";
	}
}
