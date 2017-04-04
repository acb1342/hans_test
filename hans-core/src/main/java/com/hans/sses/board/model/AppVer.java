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
@Table(name = "TBL_VERSION")
public class AppVer extends AbstractModel<Long> implements Serializable {

	private static final long serialVersionUID = -6541974424429423841L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "VERSION_SEQ", nullable = false)
	private Long versionSeq;
	
	@Column(name = "OS")
	private String os;
	
	@Column(name = "VER")
	private String ver;
	
	@Column(name = "REG_DATE")
	private Date regDate;
	
	@Column(name = "TARGET_TYPE")
	private String targetType;
	
	@Column(name = "URL")
	private String url;

	@Column(name = "UPDATE_TYPE")
	private String updateType;
	
	@Column(name = "CONTENT")
	private String content;

	@Column(name = "DEPLOY_YMD")
	private String deployYmd;
	
	@Column(name = "DEPLOY_HHMI")
	private String deployHhmi;
	
	@Column(name = "MOD_DATE")
	private Date modDate;

	public Long getVersionSeq() {
		return versionSeq;
	}

	public void setVersionSeq(Long versionSeq) {
		this.versionSeq = versionSeq;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getVer() {
		return ver;
	}

	public void setVer(String ver) {
		this.ver = ver;
	}

	public Date getRegDate() {
		return regDate;
	}

	public void setRegDate(Date regDate) {
		this.regDate = regDate;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpdateType() {
		return updateType;
	}

	public void setUpdateType(String updateType) {
		this.updateType = updateType;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getDeployYmd() {
		return deployYmd;
	}

	public void setDeployYmd(String deployYmd) {
		this.deployYmd = deployYmd;
	}

	public String getDeployHhmi() {
		return deployHhmi;
	}

	public void setDeployHhmi(String deployHhmi) {
		this.deployHhmi = deployHhmi;
	}

	public Date getModDate() {
		return modDate;
	}

	public void setModDate(Date modDate) {
		this.modDate = modDate;
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
