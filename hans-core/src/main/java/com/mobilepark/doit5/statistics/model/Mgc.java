package com.mobilepark.doit5.statistics.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "mgc", namespace = "http://www.onem2m.org/xml/protocols")
@XmlAccessorType(XmlAccessType.FIELD)
public class Mgc {

	@XmlElement
	private String exe;
	
	@XmlElement
	private String exra;
	
	public String getExe() {
		return exe;
	}

	public void setExe(String exe) {
		this.exe = exe;
	}

	public String getExra() {
		return exra;
	}

	public void setExra(String exra) {
		this.exra = exra;
	}
}
