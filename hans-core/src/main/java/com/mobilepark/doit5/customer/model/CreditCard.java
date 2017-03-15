package com.mobilepark.doit5.customer.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.model
 * @Filename     : CreditCard.java
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
@Table(name = "TB_CUST_CREDIT_CARD")
public class CreditCard extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = 4214612598641566107L;

	/**
	 * TB_CUST_MEMBER 와 일대일
	 */
	@Id
	@Column(name = "USID")
	private Long id;
	
	@Column(name = "CREDIT_CARD_NO")
	private String creditCardNo;
	
	/*@Column(name = "VALIDITY")
	private String validity;
	
	@Column(name = "CVC")
	private String cvc;
	
	@Column(name = "PASSWD")
	private String passwd;*/
	
	@Column(name = "VALID_YN")
	private String validYn;
	
	@Column(name = "PAYMENT_DAY")
	private String paymentDay;
	
	@Column(name = "BILLKEY")
	private String billkey;
	
	@Column(name = "COMPANY")
	private String company;
	
	@Formula("(SELECT CASE ccc.COMPANY WHEN  '01' THEN '하나 (구 외환)' WHEN '03' THEN '롯데' WHEN '04' THEN '현대' WHEN '06' THEN '국민' "
			+ "WHEN '11' THEN 'BC' WHEN '12' THEN '삼성' WHEN '14' THEN '신한' WHEN '15' THEN '한미' WHEN '16' THEN 'NH' WHEN '17' THEN '하나 SK' WHEN '21' THEN '해외비자' "
			+ "WHEN '22' THEN '해외마스터' WHEN '23' THEN 'JCB' WHEN '24' THEN '해외아멕스' WHEN '25' THEN '해외다이너스' END "
			+ "FROM TB_CUST_CREDIT_CARD ccc "
			+ "WHERE COMPANY = ccc.COMPANY AND ccc.USID = USID)")
	private String companyNm;
	
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
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCreditCardNo() {
		return creditCardNo;
	}

	public void setCreditCardNo(String creditCardNo) {
		this.creditCardNo = creditCardNo;
	}

	/*public String getValidity() {
		return validity;
	}

	public void setValidity(String validity) {
		this.validity = validity;
	}

	public String getCvc() {
		return cvc;
	}

	public void setCvc(String cvc) {
		this.cvc = cvc;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}*/

	public String getValidYn() {
		return validYn;
	}

	public void setValidYn(String validYn) {
		this.validYn = validYn;
	}

	public String getPaymentDay() {
		return paymentDay;
	}

	public void setPaymentDay(String paymentDay) {
		this.paymentDay = paymentDay;
	}

	public String getBillkey() {
		return billkey;
	}

	public void setBillkey(String billkey) {
		this.billkey = billkey;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
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
