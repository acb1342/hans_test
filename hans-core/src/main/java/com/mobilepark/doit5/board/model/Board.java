package com.mobilepark.doit5.board.model;

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
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.admin.model
 * @Filename     : Board.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2016. 11. 14.       최초 버전
 * =================================================================================
 */

@Entity
@Table(name = "TBL_BOARD")
public class Board extends AbstractModel<Long> implements Serializable {

		private static final long serialVersionUID = -8286183249134867745L;

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@Column(name = "sequence", nullable = false)
		private Long sequence;

		@Column(name = "user_id")
		private String user_id;

		@Column(name = "title")
		private String title;

		@Column(name = "content")
		private String content;
		
		@Column(name = "status")
		private String status;
		
		@Column(name = "board_type")
		private String board_type;
		
		@Column(name = "hit")
		private Long hit;
		
		@Column(name = "popup")
		private String popup;
		
		@Column(name = "create_time")
		private Date create_time;

		@Column(name = "update_time")
		private Date update_time;

		public Long getSequence() {
			return sequence;
		}

		public void setSequence(Long sequence) {
			this.sequence = sequence;
		}

		public String getUser_id() {
			return user_id;
		}

		public void setUser_id(String user_id) {
			this.user_id = user_id;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}

		public String getBoard_type() {
			return board_type;
		}

		public void setBoard_type(String board_type) {
			this.board_type = board_type;
		}

		public Long getHit() {
			return hit;
		}

		public void setHit(Long hit) {
			this.hit = hit;
		}

		public String getPopup() {
			return popup;
		}

		public void setPopup(String popup) {
			this.popup = popup;
		}

		public Date getCreate_time() {
			return create_time;
		}

		public void setCreate_time(Date create_time) {
			this.create_time = create_time;
		}

		public Date getUpdate_time() {
			return update_time;
		}

		public void setUpdate_time(Date update_time) {
			this.update_time = update_time;
		}

		@Override
		public Long getId() {
			return sequence;
		}

	
		
		

		
		
	
}
