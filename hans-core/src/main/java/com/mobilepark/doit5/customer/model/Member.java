package com.mobilepark.doit5.customer.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.customer.model
 * @Filename     : Member.java
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
@Table(name = "TB_CUST_MEMBER")
public class Member extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -3762991393513494613L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USID", nullable = false)
	private Long id;
	
	@Column(name = "NAME")
	private String name;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "PAYMENT_PLAN")
	private String paymentPlan;
	
	@Column(name = "PAYMENT_METHOD")
	private String paymentMethod;
	
	@Column(name = "OS")
	private String os;
	
	@Column(name = "PUSH_OS")
	private String pushOs;
	
	@Column(name = "DEVICE_ID")
	private String deviceId;
	
	@Column(name = "MDN")
	private String mdn;
	
	@Column(name = "TEL")
	private String tel;
	
	@Column(name = "EMAIL_ADDR")
	private String emailAddr;
	
	@Column(name = "CERT_TOKEN")
	private String certToken;
	
	@Column(name = "PUSH_TOKEN")
	private String pushToken;
	
	@Column(name = "TID_TOKEN")
	private String tidToken;
	
	@Column(name = "CHNL_SUBS_DATE")
	private String chnlSubsDate;
	
	@Column(name = "PW_MOD_DATE")
	private String pwModDate;
	
	@Column(name = "SUB")
	private String sub;
	
	@Column(name = "EXP")
	private Integer exp;
	
	@Column(name = "IAT")
	private Integer iat;
	
	@Column(name = "IST")
	private String ist;
	
	@Column(name = "SUBS_ID")
	private String subsId;
	
	@Column(name = "BIRTH")
	private String birth;
	
	@Column(name = "SEX")
	private String sex;
	
	@Column(name = "NAME_VRFD")
	private String nameVrfd = "Y";
	
	@Column(name = "RCPT_AGR")
	private String rcptAgr = "Y";
	
	@Column(name = "RCPT_AGR_DATE")
	private String rcptAgrDate;
	
	@Column(name = "FST_RG_USID")
	private String fstRgUsid;

	@Column(name = "FST_RG_DT")
	private Date fstRgDt;

	@Column(name = "LST_CH_USID")
	private String lstChUsid;

	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentPlan() {
		return paymentPlan;
	}

	public void setPaymentPlan(String paymentPlan) {
		this.paymentPlan = paymentPlan;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getPushOs() {
		return pushOs;
	}

	public void setPushOs(String pushOs) {
		this.pushOs = pushOs;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getCertToken() {
		return certToken;
	}

	public void setCertToken(String certToken) {
		this.certToken = certToken;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public String getTidToken() {
		return tidToken;
	}

	public void setTidToken(String tidToken) {
		this.tidToken = tidToken;
	}

	public String getMdn() {
		return mdn;
	}

	public void setMdn(String mdn) {
		this.mdn = mdn;
	}

	public String getEmailAddr() {
		return emailAddr;
	}

	public void setEmailAddr(String emailAddr) {
		this.emailAddr = emailAddr;
	}

	public String getChnlSubsDate() {
		return chnlSubsDate;
	}

	public void setChnlSubsDate(String chnlSubsDate) {
		this.chnlSubsDate = chnlSubsDate;
	}

	public String getPwModDate() {
		return pwModDate;
	}

	public void setPwModDate(String pwModDate) {
		this.pwModDate = pwModDate;
	}

	public String getSub() {
		return sub;
	}

	public void setSub(String sub) {
		this.sub = sub;
	}

	public Integer getExp() {
		return exp;
	}

	public void setExp(Integer exp) {
		this.exp = exp;
	}

	public Integer getIat() {
		return iat;
	}

	public void setIat(Integer iat) {
		this.iat = iat;
	}

	public String getIst() {
		return ist;
	}

	public void setIst(String ist) {
		this.ist = ist;
	}

	public String getSubsId() {
		return subsId;
	}

	public void setSubsId(String subsId) {
		this.subsId = subsId;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getNameVrfd() {
		return nameVrfd;
	}

	public void setNameVrfd(String nameVrfd) {
		this.nameVrfd = nameVrfd;
	}

	public String getRcptAgr() {
		return rcptAgr;
	}

	public void setRcptAgr(String rcptAgr) {
		this.rcptAgr = rcptAgr;
	}

	public String getRcptAgrDate() {
		return rcptAgrDate;
	}

	public void setRcptAgrDate(String rcptAgrDate) {
		this.rcptAgrDate = rcptAgrDate;
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
