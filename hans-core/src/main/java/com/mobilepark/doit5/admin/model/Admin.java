package com.mobilepark.doit5.admin.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Formula;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.model
 * @Filename     : Admin.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2016 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 30.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TB_MGMT_ADMIN")
public class Admin extends AbstractModel<String> implements Serializable {

	private static final long serialVersionUID = 3423112126233058739L;

	@Id
	@Column(name = "ADMIN_ID")
	private String id;

	@ManyToOne
	@JoinColumn(name = "ADMIN_GROUP_ID")
	private AdminGroup adminGroup;

	@Column(name = "PASSWD")
	private String passwd;

	@Column(name = "NAME")
	private String name;

	@Column(name = "GRADE")
	private Integer grade;

	@Column(name = "AREA")
	private String area;
	
	@Column(name = "MOBILE")
	private String mobile;

	@Column(name = "TEL")
	private String tel;

	@Column(name = "EMAIL")
	private String email;
	
	@Column(name = "PUSH_TOKEN")
	private String pushToken;

	@Column(name = "VALID_YN")
	private String validYn;
	
	@Column(name = "PW_ERR_CNT")
	private Integer pwErrCnt;
	
	@Column(name = "FST_RG_USID", updatable=false)
	private String fstRgUsid;

	@Column(name = "FST_RG_DT", updatable=false)
	private Date fstRgDt;

	@Column(name = "LST_CH_USID")
	private String lstChUsid;

	@Column(name = "LST_CH_DT")
	private Date lstChDt;
	
	@Transient
	private Integer groupId;
	
	// 전체 작업 건수
	@Formula("(SELECT count(*) FROM VI_ELCG_CUST_CENTER view WHERE view.WK_USID = ADMIN_ID)")
	private int allWkCnt;
	
	// 미처리 작업 건수
	@Formula("(SELECT count(*) FROM VI_ELCG_CUST_CENTER view WHERE view.WK_USID = ADMIN_ID AND view.STATUS != '407103' AND view.STATUS != '409103')")
	private int noCompleWkCnt;
	
	@Override
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public AdminGroup getAdminGroup() {
		return adminGroup;
	}

	public void setAdminGroup(AdminGroup adminGroup) {
		this.adminGroup = adminGroup;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getGrade() {
		return grade;
	}

	public void setGrade(Integer grade) {
		this.grade = grade;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPushToken() {
		return pushToken;
	}

	public void setPushToken(String pushToken) {
		this.pushToken = pushToken;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getValidYn() {
		return validYn;
	}

	public void setValidYn(String validYn) {
		this.validYn = validYn;
	}

	public Integer getPwErrCnt() {
		return pwErrCnt;
	}

	public void setPwErrCnt(Integer pwErrCnt) {
		this.pwErrCnt = pwErrCnt;
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

	public int getAllWkCnt() {
		return allWkCnt;
	}

	public void setAllWkCnt(int allWkCnt) {
		this.allWkCnt = allWkCnt;
	}

	public int getNoCompleWkCnt() {
		return noCompleWkCnt;
	}

	public void setNoCompleWkCnt(int noCompleWkCnt) {
		this.noCompleWkCnt = noCompleWkCnt;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	
}
