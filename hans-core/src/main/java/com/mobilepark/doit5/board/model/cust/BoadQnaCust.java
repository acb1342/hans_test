package com.mobilepark.doit5.board.model.cust;


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

import com.uangel.platform.model.AbstractModel;


/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.board.model.cust
 * @Filename     : BoadQnaCust.java
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
@Table(name = "TB_BOAD_QNA_CUST")
public class BoadQnaCust extends AbstractModel<Long> implements Serializable{

	private static final long serialVersionUID = 5245913845175568766L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SN_ID", nullable = false)
	private Long snId;
	
	@Column(name = "QUESTION_ID")
	private Long questionId;
	
	// 유형 - 질문 602101 , 답변 602102
	@Column(name = "TYPE")
	private String type;
	
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "BODY")
	private String body;
	
	@Column(name = "OPEN_YN")
	private String openYn;

	@Column(name = "PASSWD")
	private String passwd;
	
	@Column(name = "READ_CNT")
	private Integer readCnt = 0;
	
	@Column(name = "ANSWER_YN")
	private String answerYn;
	
	// 답변상태 - 대기중 602201 , 완료 602202
	@Column(name = "ANSWER_STATUS")
	private String answerStatus;
	
	@Column(name = "PEN_NAME")
	private String penName;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;
	
	@Column(name = "FST_RG_DT")
	private Date fstRgDt;
	
	@Column(name = "LST_CH_USID")
	private String lstChUsid;
	
	@Column(name = "LST_CH_DT")
	private Date lstChDt;

	/* 
	 *  @Transient - 검색시 사용 
	 *		1. fromDate : 시작날짜  2. toDate : 끝
	 *		3. searchKey : 전체검색시 검색어  
	 */
	@Transient
	String fromDate;
	
	@Transient
	String toDate;
	
	@Transient
	String searchKey;
	
	// 사용자 구분
	@Formula("( SELECT a.ADMIN_GROUP_ID FROM TB_MGMT_ADMIN a WHERE FST_RG_USID = a.ADMIN_ID )")
	String writerType;

	// TID 일때
	@Formula("( SELECT count(*) FROM TB_CUST_MEMBER b WHERE FST_RG_USID = b.USID )")
	int TwriterType;
	
	public Long getSnId() {
		return snId;
	}

	public void setSnId(Long snId) {
		this.snId = snId;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
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

	public String getOpenYn() {
		return openYn;
	}

	public void setOpenYn(String openYn) {
		this.openYn = openYn;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public Integer getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(Integer readCnt) {
		this.readCnt = readCnt;
	}

	public String getAnswerYn() {
		return answerYn;
	}

	public void setAnswerYn(String answerYn) {
		this.answerYn = answerYn;
	}

	public String getAnswerStatus() {
		return answerStatus;
	}

	public void setAnswerStatus(String answerStatus) {
		this.answerStatus = answerStatus;
	}

	public String getPenName() {
		return penName;
	}

	public void setPenName(String penName) {
		this.penName = penName;
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

	@Override
	public Long getId() {
		return snId;
	}
	
	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(String searchKey) {
		this.searchKey = searchKey;
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
	
}
