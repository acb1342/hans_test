package com.hans.sses.member.model;

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
 * @Project      : mobilepark admin
 * @Package      : com.mobilepark.doit5.member.model
 * @Filename     : User.java
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2017. 03. 29.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TBL_USER_INFO")
public class User extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -3762991393513494613L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_seq", nullable = false)
	private Long user_seq;
	
	@Column(name = "company_seq")
	private int company_seq;

	@Column(name = "company_name")
	private String company_name;
	
	@Column(name = "birthday")
	private String birthday;
	
	@Column(name = "use_yn")
	private String use_yn;
	
	@Column(name = "user_name")
	private String user_name;
	
	@Column(name = "location")
	private String location;

	@Column(name = "rssi_volume")
	private String rssi_volume;

	@Column(name = "user_ip")
	private String user_ip;
	
	@Column(name = "user_mode")
	private String user_mode;
	
	@Column(name = "reg_date")
	private Date reg_date;
	
	@Column(name = "mod_date")
	private Date mod_date;

	private String parentName;
	
	@Override
	public Long getId() {
		return user_seq;
	}

	public Long getUser_seq() {
		return user_seq;
	}

	public void setUser_seq(Long user_seq) {
		this.user_seq = user_seq;
	}

	public int getCompany_seq() {
		return company_seq;
	}

	public void setCompany_seq(int company_seq) {
		this.company_seq = company_seq;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String  birthday) {
		this.birthday = birthday;
	}

	public String getUse_yn() {
		return use_yn;
	}

	public void setUse_yn(String use_yn) {
		this.use_yn = use_yn;
	}

	public String getUser_name() {
		return user_name;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getRssi_volume() {
		return rssi_volume;
	}

	public void setRssi_volume(String rssi_volume) {
		this.rssi_volume = rssi_volume;
	}

	public String getUser_ip() {
		return user_ip;
	}

	public void setUser_ip(String user_ip) {
		this.user_ip = user_ip;
	}

	public String getUser_mode() {
		return user_mode;
	}

	public void setUser_mode(String user_mode) {
		this.user_mode = user_mode;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public Date getMod_date() {
		return mod_date;
	}

	public void setMod_date(Date mod_date) {
		this.mod_date = mod_date;
	}

	public String getCompany_name() {
		return company_name;
	}

	public void setCompany_name(String company_name) {
		this.company_name = company_name;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
