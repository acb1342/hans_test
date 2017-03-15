package com.mobilepark.doit5.board.model;

import java.sql.Timestamp;

import org.springframework.web.multipart.MultipartFile;

public class BoardFota {

	private int pageNum;
	private int startRow;
	private int rowPerPage;
	private MultipartFile fotaFile;
	
	private int snId;
	private String fileName;
	private String content;
	private String ver;
	private String url;
	private String fstRgUsid;
	private Timestamp fstRgDt;
	private String latChUsid;
	private Timestamp latChUsDt;
	
	public int getSnId() {
		return snId;
	}
	public void setSnId(int snId) {
		this.snId = snId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getVer() {
		return ver;
	}
	public void setVer(String ver) {
		this.ver = ver;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFstRgUsid() {
		return fstRgUsid;
	}
	public void setFstRgUsid(String fstRgUsid) {
		this.fstRgUsid = fstRgUsid;
	}
	public String getLatChUsid() {
		return latChUsid;
	}
	public void setLatChUsid(String latChUsid) {
		this.latChUsid = latChUsid;
	}
	public int getRowPerPage() {
		return rowPerPage;
	}
	public void setRowPerPage(int rowPerPage) {
		this.rowPerPage = rowPerPage;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public MultipartFile getFotaFile() {
		return fotaFile;
	}
	public void setFotaFile(MultipartFile fotaFile) {
		this.fotaFile = fotaFile;
	}
	public Timestamp getFstRgDt() {
		return fstRgDt;
	}
	public void setFstRgDt(Timestamp fstRgDt) {
		this.fstRgDt = fstRgDt;
	}
	public Timestamp getLatChUsDt() {
		return latChUsDt;
	}
	public void setLatChUsDt(Timestamp latChUsDt) {
		this.latChUsDt = latChUsDt;
	}
	
	
}
