package com.mobilepark.doit5.route.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.client.model
 * @Filename     : RouteRule.java
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
@Table(name = "tbl_route_rule", catalog = "upush")
public class RouteRule extends AbstractModel<String> {
	@Id
	@Column(name = "route_seq", nullable = false, length = 32)
	private String id;

	@Column(name = "route_1", length = 8)
	private String route1;

	@Column(name = "route_2", length = 8)
	private String route2;

	@Column(name = "route_3", length = 8)
	private String route3;

	@Column(name = "change_date", length = 32)
	private String changeDate;

	@Column(name = "description", length = 32)
	private String description;

	@Override
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoute1() {
		return this.route1;
	}

	public void setRoute1(String route1) {
		this.route1 = route1;
	}

	public String getRoute2() {
		return this.route2;
	}

	public void setRoute2(String route2) {
		this.route2 = route2;
	}

	public String getRoute3() {
		return this.route3;
	}

	public void setRoute3(String route3) {
		this.route3 = route3;
	}

	public String getChangeDate() {
		return this.changeDate;
	}

	public void setChangeDate(String changeDate) {
		this.changeDate = changeDate;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
