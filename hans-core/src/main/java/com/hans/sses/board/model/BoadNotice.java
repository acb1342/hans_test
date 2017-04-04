package com.hans.sses.board.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

@Entity
@Table(name = "TBL_NOTICE")
public class BoadNotice implements Serializable{

	private static final long serialVersionUID = 4890070528911226825L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SEQ", nullable = false)
	private Long seq;

	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "CONTENTS")
	private String contents;
	
	@Column(name = "READ_CNT")
	private Long readCnt;
	
	@Column(name = "DISPLAY_YN")
	private String displayYn;
	
	@Column(name = "ADMIN_ID")
	private String adminId;
	
	@Column(name = "REG_DATE", nullable = false)
	private Date regDate;
	
	@Column(name = "MOD_DATE")
	private Date modDate;

	public Long getSeq() {
		return seq;
	}

	public void setSeq(Long seq) {
		this.seq = seq;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents() {
		return contents;
	}

	public void setContents(String contents) {
		this.contents = contents;
	}

	public Long getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(Long readCnt) {
		this.readCnt = readCnt;
	}

	public String getDisplayYn() {
		return displayYn;
	}

	public void setDisplayYn(String displayYn) {
		this.displayYn = displayYn;
	}

	public String getAdminId() {
		return adminId;
	}

	public void setAdminId(String adminId) {
		this.adminId = adminId;
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
