package com.mobilepark.doit5.member.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_USER_EQUIPMENT")
public class UserEq {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SEQ")
	private Integer seq;
	
	@Column(name = "IDENTITY_CODE")
	private String identityCode;
	
	@Column(name = "EQUIP_SEQ")
	private Integer equipSeq;
	
	@Column(name = "VOLUME")
	private Integer volume;
	
	@Column(name = "REG_DATE")
	private Date regDate;
	
	@Column(name = "MOD_DATE")
	private Date modDate;

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public String getIdentityCode() {
		return identityCode;
	}

	public void setIdentityCode(String identityCode) {
		this.identityCode = identityCode;
	}

	public Integer getEquipSeq() {
		return equipSeq;
	}

	public void setEquipSeq(Integer equipSeq) {
		this.equipSeq = equipSeq;
	}

	public Integer getVolume() {
		return volume;
	}

	public void setVolume(Integer volume) {
		this.volume = volume;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

}
