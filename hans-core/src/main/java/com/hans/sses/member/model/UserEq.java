package com.hans.sses.member.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "TBL_USER_EQUIPMENT")
public class UserEq {
	
    @Id
    @Column(name = "SEQ")
    private int seq;
    
    @Column(name = "USER_SEQ")
    private int userSeq;
    
    @Column(name = "EQUIP_SEQ")
    private int equipSeq;
    
    @Column(name = "VOLUME")
    private int volume;

    @Column(name = "REG_DATE", updatable=false)
    private Date regDate;

    @Column(name = "MOD_DATE")
    private Date modDate;

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

	public int getEquipSeq() {
		return equipSeq;
	}

	public void setEquipSeq(int equipSeq) {
		this.equipSeq = equipSeq;
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
   
}