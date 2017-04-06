package com.hans.sses.member.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "TBL_USER_EQUIPMENT")
public class UserEq {

	@Id
	@Column(name = "SEQ")
	private int seq;

	@Column(name = "USER_SEQ")
	private int userSeq;

	@Column(name = "MACADDRESS")
	private String macAddress;

	@Column(name = "VOLUME")
	private int volume;

	@Column(name = "REG_DATE", updatable = false)
	private Date regDate;

	@Column(name = "MOD_DATE")
	private Date modDate;

	private String companySeq;
	
	private User user;

	@Transient
	private Equipment equipment;
	
	@Transient
	private List<Equipment> equipmentList;

	public int getSeq() {
		return seq;
	}

	public void setSeq(int seq) {
		this.seq = seq;
	}

	public int getUserSeq() {
		return userSeq;
	}

	public void setUserSeq(int userSeq) {
		this.userSeq = userSeq;
	}
	
	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
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
	
	public String getCompanySeq() {
		return companySeq;
	}

	public void setCompanySeq(String companySeq) {
		this.companySeq = companySeq;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Equipment getEquipment() {
		return equipment;
	}

	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}

	public List<Equipment> getEquipmentList() {
		return equipmentList;
	}

	public void setEquipmentList(List<Equipment> equipmentList) {
		this.equipmentList = equipmentList;
	}
	
}