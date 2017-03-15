package com.mobilepark.doit5.push.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.BatchSize;

import com.mobilepark.doit5.common.NotiIconType;
import com.mobilepark.doit5.common.PushSendType;
import com.mobilepark.doit5.common.PushType;
import com.mobilepark.doit5.common.UseFlag;
import com.uangel.platform.model.AbstractModel;

/*==================================================================================
 * @Project      : evc-core
 * @Package      : com.mobilepark.doit5.push.model
 * @Filename     : PushSvc.java
 * 
 * All rights reserved. No part of this work may be reproduced, stored in a
 * retrieval system, or transmitted by any means without prior written
 * permission of UANGEL Inc.
 * 
 * Copyright(c) 2014 UANGEL All rights reserved
 * =================================================================================
 *  No     DATE              Description
 * =================================================================================
 *  1.0	   2014. 6. 3.       최초 버전
 * =================================================================================
 */
@Entity
@Table(name = "TBL_PUSH_SERVICE", catalog = "upush")
public class PushSvc extends AbstractModel<Integer> {
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	private Integer id;

	@Column(name = "REQUEST_NO", nullable = false, length = 256)
	private String requestNo;

	@Column(name = "CP_ID", length = 32)
	private String cpId;

	@Column(name = "CP_NAME", length = 256)
	private String cpName;

	@Column(name = "SEND_REQ_TYPE", nullable = false, length = 16)
	private String sendReqType;

	@Column(name = "SEND_TYPE", length = 16)
	@Enumerated(EnumType.STRING)
	private PushSendType sendType;

	@Column(name = "OS", length = 16)
	private String os;

	@Column(name = "APP_ID", length = 256)
	private String appId;

	@Column(name = "APP_NAME", length = 256)
	private String appName;

	@Column(name = "GROUP_ID", length = 256)
	private String groupId;

	@OneToMany(mappedBy = "pushSvc", cascade = {
			CascadeType.ALL
	}, orphanRemoval = true)
	@BatchSize(size = 20)
	private final List<PushMdn> pushMdns = new ArrayList<PushMdn>();

	@OneToMany(mappedBy = "pushSvc", cascade = {
			CascadeType.ALL
	}, orphanRemoval = true)
	@BatchSize(size = 20)
	private final List<PushToken> pushTokens = new ArrayList<PushToken>();

	@Column(name = "SEND_MSG_TYPE", length = 16)
	private String sendMsgType;

	@Column(name = "NOTI_TITLE", length = 512)
	private String notiTitle;

	@Column(name = "NOTI_MSG", length = 512)
	private String notiMsg;

	@Column(name = "NOTI_IMG", length = 512)
	private String notiImg;

	@Column(name = "NOTI_ICON_TYPE", length = 16)
	@Enumerated(EnumType.STRING)
	private NotiIconType notiIconType;

	@Column(name = "PUSH_TYPE", length = 16)
	@Enumerated(EnumType.STRING)
	private PushType pushType;

	@Column(name = "PUSH_TITLE", length = 512)
	private String pushTitle;

	@Column(name = "PUSH_BODY", length = 512)
	private String pushBody;

	@Column(name = "PUSH_BODY_DATA", columnDefinition = "mysql->mediumtext")
	private String pushBodyData;

	@ManyToMany
	@JoinTable(name = "upush.TBL_PUSH_BUTTON_MAP",
			joinColumns = {
				@JoinColumn(name = "PUSH_ID")
			},
			inverseJoinColumns = {
				@JoinColumn(name = "BUTTON_ID")
			})
	@BatchSize(size = 20)
	private final List<PushButton> pushButtons = new ArrayList<PushButton>();

	@Column(name = "DESCRIPTION", columnDefinition = "mysql->mediumtext")
	private String description;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SEND_TIME", length = 19)
	private Date sendTime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "RESERVED_SEND_TIME", length = 19)
	private Date reservedSendTime;

	@Column(name = "RESERVED_SEND_FLAG")
	@Enumerated(EnumType.STRING)
	private UseFlag reservedSendFlag;

	@Column(name = "STATUS", nullable = false, length = 16)
	private String status;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE", nullable = false, length = 19)
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFY_DATE", length = 19)
	private Date modifyDate;

	@Column(name = "USE_FLAG", nullable = false)
	@Enumerated(EnumType.STRING)
	private UseFlag useFlag;

	public PushSvc() {
	}

	@Override
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getRequestNo() {
		return this.requestNo;
	}

	public void setRequestNo(String requestNo) {
		this.requestNo = requestNo;
	}

	public String getCpId() {
		return this.cpId;
	}

	public void setCpId(String cpId) {
		this.cpId = cpId;
	}

	public String getCpName() {
		return this.cpName;
	}

	public void setCpName(String cpName) {
		this.cpName = cpName;
	}

	public String getSendReqType() {
		return this.sendReqType;
	}

	public void setSendReqType(String sendReqType) {
		this.sendReqType = sendReqType;
	}

	public PushSendType getSendType() {
		return this.sendType;
	}

	public void setSendType(PushSendType sendType) {
		this.sendType = sendType;
	}

	public Date getReservedSendTime() {
		return this.reservedSendTime;
	}

	public void setReservedSendTime(Date reservedSendTime) {
		this.reservedSendTime = reservedSendTime;
	}

	public UseFlag getReservedSendFlag() {
		return this.reservedSendFlag;
	}

	public void setReservedSendFlag(UseFlag reservedSendFlag) {
		this.reservedSendFlag = reservedSendFlag;
	}

	public String getOs() {
		return this.os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getGroupId() {
		return this.groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getSendMsgType() {
		return this.sendMsgType;
	}

	public void setSendMsgType(String sendMsgType) {
		this.sendMsgType = sendMsgType;
	}

	public String getNotiTitle() {
		return this.notiTitle;
	}

	public void setNotiTitle(String notiTitle) {
		this.notiTitle = notiTitle;
	}

	public String getNotiMsg() {
		return this.notiMsg;
	}

	public void setNotiMsg(String notiMsg) {
		this.notiMsg = notiMsg;
	}

	public String getNotiImg() {
		return this.notiImg;
	}

	public void setNotiImg(String notiImg) {
		this.notiImg = notiImg;
	}

	public NotiIconType getNotiIconType() {
		return this.notiIconType;
	}

	public void setNotiIconType(NotiIconType notiIconType) {
		this.notiIconType = notiIconType;
	}

	public PushType getPushType() {
		return this.pushType;
	}

	public void setPushType(PushType pushType) {
		this.pushType = pushType;
	}

	public String getPushTitle() {
		return this.pushTitle;
	}

	public void setPushTitle(String pushTitle) {
		this.pushTitle = pushTitle;
	}

	public String getPushBody() {
		return this.pushBody;
	}

	public void setPushBody(String pushBody) {
		this.pushBody = pushBody;
	}

	public String getPushBodyData() {
		return this.pushBodyData;
	}

	public void setPushBodyData(String pushBodyData) {
		this.pushBodyData = pushBodyData;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getSendTime() {
		return this.sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public UseFlag getUseFlag() {
		return this.useFlag;
	}

	public void setUseFlag(UseFlag useFlag) {
		this.useFlag = useFlag;
	}

	public List<PushMdn> getPushMdns() {
		return this.pushMdns;
	}

	public boolean addPushMdn(PushMdn pushMdn) {
		// pushMdn.setPushSvc(this);
		return this.pushMdns.add(pushMdn);
	}

	public boolean removePushMdn(PushMdn pushMdn) {
		pushMdn.setPushSvc(null);
		return this.pushMdns.remove(pushMdn);
	}

	public boolean removePushMdn(Integer id) {
		Iterator<PushMdn> iter = this.pushMdns.iterator();
		while (iter.hasNext()) {
			PushMdn element = iter.next();
			if (element.getId() != null && element.getId().equals(id)) {
				iter.remove();
				return true;
			}
		}
		return false;
	}

	public void removeAllPushMdn() {
		this.pushMdns.removeAll(this.pushMdns);
	}

	public List<PushToken> getPushTokens() {
		return this.pushTokens;
	}

	public boolean addPushToken(PushToken pushToken) {
		pushToken.setPushSvc(this);
		return this.pushTokens.add(pushToken);
	}

	public boolean removePushToken(PushToken pushToken) {
		pushToken.setPushSvc(null);
		return this.pushTokens.remove(pushToken);
	}

	public boolean removePushToken(Integer id) {
		Iterator<PushToken> iter = this.pushTokens.iterator();
		while (iter.hasNext()) {
			PushToken element = iter.next();
			if (element.getId() != null && element.getId().equals(id)) {
				iter.remove();
				return true;
			}
		}
		return false;
	}

	public void removeAllPushToken() {
		this.pushTokens.removeAll(this.pushTokens);
	}

	public List<PushButton> getPushButtons() {
		return this.pushButtons;
	}

	public boolean addPushButton(PushButton pushButton) {
		return this.pushButtons.add(pushButton);
	}

	public boolean removePushButton(PushButton pushButton) {
		return this.pushButtons.remove(pushButton);
	}

	public boolean removePushButton(long pushButtonId) {
		Iterator<PushButton> iter = this.pushButtons.iterator();
		while (iter.hasNext()) {
			PushButton pushButton = iter.next();
			if (pushButton.getId() != null && pushButton.getId().equals(pushButtonId)) {
				iter.remove();
				return true;
			}
		}

		return false;
	}

	public void removeAllPushButtons() {
		this.pushButtons.removeAll(this.pushButtons);
	}

}
