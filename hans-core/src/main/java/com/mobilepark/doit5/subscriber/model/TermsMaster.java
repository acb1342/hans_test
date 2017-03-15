package com.mobilepark.doit5.subscriber.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.mobilepark.doit5.common.DeviceType;
import com.mobilepark.doit5.common.Status;
import com.mobilepark.doit5.common.TermsType;
import com.mobilepark.doit5.common.UseFlag;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.subscriber.model
 * @Filename     : TermsMaster.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 2. 6.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TBL_TERMS_MASTER", catalog = "upush")
public class TermsMaster extends AbstractModel<TermsMaster.TermsId> {
	@Embeddable
	public static class TermsId implements Serializable {
		private static final long serialVersionUID = -6746835941188811720L;

		@Column(name = "DEVICE_TYPE")
		@Enumerated(EnumType.STRING)
		private DeviceType deviceType;

		@Column(name = "TERMS_TYPE")
		@Enumerated(EnumType.STRING)
		private TermsType termsType;

		@Column(name = "TERMS_VER_ID")
		private String termsVerId;

		public TermsId() {
		}

		public TermsId(DeviceType deviceType, TermsType termsType, String termsVerId) {
			this.deviceType = deviceType;
			this.termsType = termsType;
			this.termsVerId = termsVerId;
		}

		public void setDeviceType(DeviceType deviceType) {
			this.deviceType = deviceType;
		}

		public void setTermsType(TermsType termsType) {
			this.termsType = termsType;
		}

		public void setTermsVerId(String termsVerId) {
			this.termsVerId = termsVerId;
		}

		public DeviceType getDeviceType() {
			return this.deviceType;
		}

		public TermsType getTermsType() {
			return this.termsType;
		}

		public String getTermsVerId() {
			return this.termsVerId;
		}

		@Override
		public boolean equals(Object o) {
			if (o != null && o instanceof TermsId) {
				TermsId that = (TermsId) o;
				return this.deviceType.equals(that.deviceType) &&
					this.termsType.equals(that.termsType) &&
					this.termsVerId.equals(that.termsVerId);
			} else {
				return false;
			}
		}

		@Override
		public int hashCode() {
			return this.deviceType.hashCode() + this.termsType.hashCode() + this.termsVerId.hashCode();
		}
	}

	@EmbeddedId
	private TermsId id;

	@Column(name = "TERMS_FILE_PATH")
	private String termsFilePath;

	@Column(name = "TERMS_CONTENT", columnDefinition = "mysql->mediumtext")
	private String termsContent;

	@Column(name = "TERMS_SUM_BEFORE", columnDefinition = "mysql->mediumtext")
	private String termsSumBefore;

	@Column(name = "TERMS_SUM_AFTER", columnDefinition = "mysql->mediumtext")
	private String termsSumAfter;

	@Column(name = "TERMS_EXPLAIN")
	private String termsExplain;

	@Column(name = "APPLY_START_YMD")
	private Date applyStartYmd;

	@Column(name = "EFFECTIVE_YMD")
	private Date effectiveYmd;

	@Column(name = "STATUS")
	@Enumerated(EnumType.STRING)
	private Status status;

	@Column(name = "CREATE_DATE")
	private Date createDate;

	@Column(name = "MODIFY_DATE")
	private Date modifyDate;

	@Column(name = "USE_FLAG")
	@Enumerated(EnumType.STRING)
	private UseFlag useFlag;

	public TermsMaster() {
	}

	public TermsMaster(DeviceType deviceType, TermsType termsType, String termsVerId) {
		super();
		this.id = new TermsId(deviceType, termsType, termsVerId);
	}

	@Override
	public TermsId getId() {
		return this.id;
	}

	public void setId(TermsId id) {
		this.id = id;
	}

	public void setId(DeviceType deviceType, TermsType termsType, String termsVerId) {
		this.id = new TermsId(deviceType, termsType, termsVerId);
	}

	public void setVersion(String version) {
		if (this.id == null) {
			this.id = new TermsId();
		}

		this.id.setTermsVerId(version);
	}

	public String getVersion() {
		String version = null;
		if (this.id != null) {
			version = this.id.getTermsVerId();
		}

		return version;
	}

	public void setDeviceType(DeviceType deviceType) {
		if (this.id == null) {
			this.id = new TermsId();
		}

		this.id.setDeviceType(deviceType);
	}

	public DeviceType getDeviceType() {
		DeviceType deviceType = null;
		if (this.id != null) {
			deviceType = this.id.getDeviceType();
		}

		return deviceType;
	}

	public void setTermsType(TermsType termsType) {
		if (this.id == null) {
			this.id = new TermsId();
		}

		this.id.setTermsType(termsType);
	}

	public TermsType getTermsType() {
		TermsType termsType = null;
		if (this.id != null) {
			termsType = this.id.getTermsType();
		}

		return termsType;
	}

	public String getTermsContent() {
		return this.termsContent;
	}

	public void setTermsContent(String termsContent) {
		this.termsContent = termsContent;
	}

	public String getTermsSumBefore() {
		return this.termsSumBefore;
	}

	public void setTermsSumBefore(String termsSumBefore) {
		this.termsSumBefore = termsSumBefore;
	}

	public String getTermsSumAfter() {
		return this.termsSumAfter;
	}

	public void setTermsSumAfter(String termsSumAfter) {
		this.termsSumAfter = termsSumAfter;
	}

	public String getTermsFilePath() {
		return this.termsFilePath;
	}

	public void setTermsFilePath(String termsFilePath) {
		this.termsFilePath = termsFilePath;
	}

	public String getTermsExplain() {
		return this.termsExplain;
	}

	public void setTermsExplain(String termsExplain) {
		this.termsExplain = termsExplain;
	}

	public Date getApplyStartYmd() {
		return this.applyStartYmd;
	}

	public void setApplyStartYmd(Date applyStartYmd) {
		this.applyStartYmd = applyStartYmd;
	}

	public Date getEffectiveYmd() {
		return this.effectiveYmd;
	}

	public void setEffectiveYmd(Date effectiveYmd) {
		this.effectiveYmd = effectiveYmd;
	}

	public Status getStatus() {
		return this.status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public UseFlag getUseFlag() {
		return this.useFlag;
	}

	public void setUseFlag(UseFlag useFlag) {
		this.useFlag = useFlag;
	}

	@Transient
	private boolean isLast = false;

	public boolean getIsLast() {
		return this.isLast;
	}

	public void setIsLast(boolean isLast) {
		this.isLast = isLast;
	}
}
