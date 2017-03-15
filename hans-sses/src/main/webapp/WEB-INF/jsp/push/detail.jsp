<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.push.model.PushSvc" %>
<%@ page import="com.mobilepark.doit5.push.model.PushMdn" %>
<%@ page import="com.mobilepark.doit5.push.model.PushToken" %>
<%@ page import="com.mobilepark.doit5.push.model.PushButton" %>
<%@ page import="com.uangel.platform.util.Env" %>
<%@ include file="/include/header.jspf" %>
<style>
ul{
	list-style:none;
	padding-left:0px;
}
.notiTitleClass .ui-dialog-titlebar {
	background:white;
}
.notiTitleClass .ui-widget-header {
	border-color:white;
}
.notiTitleClass .ui-dialog-titlebar-close {
    background: none !important;
    border: none !important;
}
.notiTitleClass .ui-button-icon-only .ui-button-text, .ui-button-icons-only .ui-button-text {
    padding: inherit;
    text-indent: -9999999px;
}
</style>
<script type="text/javascript">
	var buttonCount = 0;
	var buttons = null;
	var buttonName = [];
	
	$(function () {
		getButtons();

		$("#previewNotiAndroidBtn").click(function() {
			previewLayerPopup("NOTI", 0, "previewNotiAndroid", 450, 325, 'center', false, false);
			$("#previewNotiAndroid").dialog("open");
		});

		$("#previewNotiIosBtn").click(function() {
			previewLayerPopup("NOTI", 0, "previewNotiIos", 450, 325, 'center', false, false);
			$("#previewNotiIos").dialog("open");
		});

		$("#previewPushAndroidBtn").click(function() {
			previewLayerPopup("PUSH", buttonCount, "previewPushAndroid", 450, 325, 'center', true, true);
			$("#previewPushAndroid").dialog("open");
		});
		
		$("#previewPushIosBtn").click(function() {
			previewLayerPopup("PUSH", buttonCount, "previewPushIos", 450, 325, 'center', true, true);
			$("#previewPushIos").dialog("open");
		});
	});

	// 수정
	function update() {
		document.location = "/push/update.htm?id=${pushSvc.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/push/delete.json', '${pushSvc.id}', function() { 
			search(); 
		});
	}

	function sendPush() {
		var sendType = '${pushSvc.sendType}';
		if (sendType == 'INSTANT') {
			jConfirm('<fmt:message key="statement.confirm.send"/>', 'Confirm', function(r) {
				if (r) {
					$.ajax({
						url:'/push/sendPush.json',
						type:"POST",
						data:{
							id:'${pushSvc.id}'
						},
						dataType:'json',
						success:function(isSuccess) {
							if (isSuccess) {
								jAlert('<fmt:message key="statement.send.success"/>','Alert', function() {
									search();
								});
							} else {
								jAlert('<fmt:message key="statement.send.fail"/>');
							}
						}
					});
				}
			});
		} else {
			jConfirm('<fmt:message key="statement.confirm.reservedSend"/>', 'Confirm', function(r) {
				if (r) {
					$.ajax({
						url:'/push/reservedSendPush.json',
						type:"POST",
						data:{
							id:'${pushSvc.id}'
						},
						dataType:'json',
						success:function(isSuccess) {
							if (isSuccess) {
								jAlert('<fmt:message key="statement.send.reservation"/>','Alert', function() {
									search();
								});
							} else {
								jAlert('<fmt:message key="statement.send.fail"/>');
							}
						}
					});
				}
			});
		}
	}

	// preview
	function preview(id) {
		var popupStatus = "width=325, height=450, toolbar=no, location=no, status=no, menubar=no, scrollbars=yes, resizable=yes ";
		window.open("/editor/popup/preview.htm?id=" + id, "uPUSH", popupStatus);
	}

	function getButtons() {
        $.ajax({
            url: '/push/getButtons.json',
            dataType: 'json',
            data: {
                'id' : '${pushSvc.id}'
            },
            success: function(list) {
                buttonCount = list.length;
                for(var i = 0; i < list.length; i++) {
                    buttons = list[i];
                    buttonName[i] = list[i].title;
                }
            }
        });
	}
	
	function previewLayerPopup(type, buttonNum, divId, height, width, position, resizable, scrollable) {
		if (type == 'PUSH') {	
			if (buttonNum > 0) {
				if (buttonNum == 1) {
					$("#" + divId).dialog({
						autoOpen:false,
						modal:true,
						height: height,
						width: width,
						position: position,
				        resizable: resizable,
				        scrollable: scrollable,
				        open: function(event, ui) { 
					        $(".ui-dialog-titlebar-close", ui.dialog | ui).show(); 
					    },
						buttons:[
							{
						        text: buttonName[0],
						        click: function () {
						        	$(this).dialog("close");
						        }
							}
						]
					});
				} else if (buttonNum == 2) {
					$("#" + divId).dialog({
						autoOpen:false,
						modal:true,
						height: height,
						width: width,
						position: position,
				        resizable: resizable,
				        scrollable: scrollable,
				        open: function(event, ui) { 
					        $(".ui-dialog-titlebar-close", ui.dialog | ui).show(); 
					        $('body').css('overflow','hidden');$('.ui-widget-overlay').css('width','100%');
					    },
					    close: function() {
				            $('body').css('overflow','auto'); 
				        },
						buttons:[
							{
						        text: buttonName[0],
						        click: function () {
						        	$(this).dialog("close");
						        },
						    }, 
						    {
						        text: buttonName[1],
						        click: function () {
						        	$(this).dialog("close");
								},
							}
						]
					});
				} else if (buttonNum == 3) {
					$("#" + divId).dialog({
						autoOpen:false,
						modal:true,
						height: height,
						width: width,
						position: position,
				        resizable: resizable,
				        scrollable: scrollable,
				        open: function(event, ui) { 
					        $(".ui-dialog-titlebar-close", ui.dialog | ui).show(); 
					    },
						buttons:[
							{
						        text: buttonName[0],
						        click: function () {
						        	$(this).dialog("close");
						        },
						    }, 
						    {
						        text: buttonName[1],
						        click: function () {
						        	$(this).dialog("close");
								},
						    },
						    {
						        text: buttonName[2],
						        click: function () {
						        	$(this).dialog("close");
								}
							}
						]
					});
				}
			} else {
				$("#" + divId).dialog({
					autoOpen:false,
					modal:true,
					height: height,
					width: width,
					position: position,
			        resizable: resizable,
			        scrollable: scrollable,
			        open: function(event, ui) { 
				        $(".ui-dialog-titlebar-close", ui.dialog | ui).show(); 
				    }
				});
			}
		} else if (type == 'NOTI'){
			$("#" + divId).dialog({
				autoOpen:false,
				dialogClass: 'notiTitleClass',
				modal:true,
				height: height,
				width: width,
				position: position,
		        resizable: resizable,
		        scrollable: scrollable,
		        open: function(event, ui) { 
			        $(".ui-dialog-titlebar-close", ui.dialog | ui).show(); 
			        $(".ui-dialog-titlebar-close", ui.dialog | ui).blur(); 
			    }
			});
		}
	}
	
	// 검색 페이지
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			document.location = "/push/search.htm?" + callbackUrl;
		} else {
			document.location = "/push/search.htm";
		}
	}
</script>

<!-- select preview widget start -->
<div id="previewNotiAndroid" title="&nbsp;" style="display: none;">
	<ul><img src="/images/icons/app_noimg.png" style="left:1px; float:left; margin: 5px 10px 0px -25px;" alt="app_logo"></ul>
	<ul><strong>${pushSvc.notiTitle}</strong><br>
	${pushSvc.notiMsg}
	</ul>
</div>
<div id="previewNotiIos" title="&nbsp;" style="display: none;">
	<p><img src="/images/icons/app_noimg.png" style="width:18px; height:18px; left:1px; float:left; margin: 10px 10px 0px 0px;" alt="app_logo"></p>
	<p>${pushSvc.notiMsg}</p>
</div>
<div id="previewPushAndroid" 
     title='<c:if test="${pushSvc.pushTitle != null || pushSvc.pushTitle != ''}">${pushSvc.pushTitle}</c:if>
			<c:if test="${pushSvc.pushTitle == null || pushSvc.pushTitle == ''}">&nbsp;</c:if>' 
     style="display: none;">
	<!-- <iframe src='<%=Env.get("message.popup.url")%>?id=${pushSvc.groupId}' style="padding:10px; width:325px; height:450px; overflow:auto" scrolling="no" frameborder="0"></iframe>-->
	${pushSvc.pushBodyData}
</div>
<div id="previewPushIos" title="&nbsp;" style="display: none;">
	<p><img src="/images/icons/app_noimg.png" style="width:18px; height:18px; left:1px; float:left; margin: 10px 10px 0px 0px;" alt="app_logo"></p>
	<p>${pushSvc.pushTitle}</p>
</div>
<!-- select preview widget end -->
<spring:hasBindErrors name="pushSvc" />
<div class="wrap00">
<%
	PushSvc pushSvc = (PushSvc)request.getAttribute("pushSvc");
%>
	<!-- list _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<!-- ID -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.requestId"/>
			</td>
			<td colspan="2" class="td02">${pushSvc.id}</td>
			<!-- CP명 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpName"/>
			</td>
			<td width="290px" class="td02">${pushSvc.cpName} (${pushSvc.cpId})</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<!-- OS -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.os"/>
			</td>
			<td colspan="2" class="td02">${pushSvc.os}</td>
			<!-- APP Name -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.appName"/>
			</td>
			<td width="290px" class="td02">${pushSvc.appName}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- APP ID -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.applicationList.appId"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.appId}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Request No -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.requestNo"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.requestNo}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 설명 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.description"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.description}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<!-- 전송 요청 타입 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.sendReqType"/>
			</td>
			<td colspan="2" class="td02">${pushSvc.sendReqType}</td>
			<!-- 전송 타입 -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.sendType"/>
			</td>
			<td width="290px" class="td02">${pushSvc.sendType}
			<c:if test="${pushSvc.sendType=='RESERVATION'}">
			<font color="red">(${pushSvc.reservedSendTime})</font>
			</c:if>
			
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<c:if test="${pushSvc.sendReqType=='MDN'}">
		<!-- MDN -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.mdn"/>
			</td>
			<td colspan="6" class="td02" id="pushMdns">
				<c:if test="${pushSvc.pushMdns != null}">
				<c:forEach items="${pushSvc.pushMdns}" var="pushMdn" varStatus="status">
				<c:set value="${pushMdn}" var="pushMdn" scope="request" />
					<%
						PushMdn pm = (PushMdn)request.getAttribute("pushMdn");
						String mdn = "";
						if (pm != null) {
							mdn = pm.getMdn();
						}
					%>
					<%= mdn%>
				</c:forEach>
				</c:if>
			</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
		<c:if test="${pushSvc.sendReqType=='PUSH_TOKEN'}">
		<!-- Push Token -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushToken"/>
			</td>
			<td colspan="6" class="td02" id="pushTokens">
				<c:if test="${pushSvc.pushTokens != null}">
				<c:forEach items="${pushSvc.pushTokens}" var="pushToken" varStatus="status">
				<c:set value="${pushToken}" var="pushToken" scope="request" />
					<%
						PushToken pt = (PushToken)request.getAttribute("pushToken");
						String token = "";
						if (pt != null) {
							token = pt.getPushToken();
						}
					%>
					<%= token%>
				</c:forEach>
				</c:if>
			</td>	
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
		<!-- Send Msg Type -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.sendMsgType"/>
			</td>
			<td colspan="6" class="td02">
				<c:if test="${pushSvc.sendMsgType == 'BOTH' }">Both (Noti+Push)</c:if>
				<c:if test="${pushSvc.sendMsgType == 'NOTI' }">Noti Only</c:if>
				<c:if test="${pushSvc.sendMsgType == 'PUSH' }">Push Only</c:if>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<c:if test="${pushSvc.sendMsgType=='BOTH' || pushSvc.sendMsgType=='NOTI'}">
		<!-- Noti Title -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.notiTitle"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.notiTitle}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Noti Message -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.notiMsg"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.notiMsg}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
		<c:if test="${pushSvc.sendMsgType=='BOTH' || pushSvc.sendMsgType=='PUSH'}">
		<!-- Push Type -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushType"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.pushType}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Push Title -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushTitle"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.pushTitle}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Push Body -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushBody"/>
			</td>
			<td colspan="6" class="td02">
				${pushSvc.description}&nbsp;&nbsp;
				<input type="button" value='<fmt:message key="label.common.preview"/>' onclick="javascript:preview('${pushSvc.groupId}')"/>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- Push Button -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.button"/>
			</td>
			<td colspan="6" class="td02">
			<table id="pushButtons" style="margin: 5px 0pt;" class="simple">
				<thead>
					<tr>
						<th style="width:10%;"><fmt:message key="label.common.id"/></th>
						<th style="width:10%;"><fmt:message key="label.pushButton.position"/></th>
						<th style="width:25%;"><fmt:message key="label.pushButton.title"/></th>
						<th style="width:10%;"><fmt:message key="label.pushButton.type"/></th>
						<th style="width:45%;"><fmt:message key="label.pushButton.action"/></th>
					</tr>
				</thead>
				<tbody>
					<c:if test="${pushSvc.pushButtons != null}">
					<c:forEach items="${pushSvc.pushButtons}" var="pushButton" varStatus="status">
					<tr>
					<c:set value="${pushButton}" var="pushButton" scope="request" />
						<%
							PushButton pb = (PushButton)request.getAttribute("pushButton");
						%>
						<td><%=pb.getId()%></td>
						<td><%=pb.getPosition()%></td>
						<td><%=pb.getTitle()%></td>
						<td><%=pb.getType()%></td>
						<td><%=pb.getAction()%></td>
					</tr>
					</c:forEach>
					</c:if>
				</tbody>
			</table>
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		</c:if>
		<!-- 전송시간 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.sendTime"/>
			</td>
			<td colspan="6" class="td02">${pushSvc.sendTime}</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 상태 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.status"/>
			</td>
			<td colspan="6" class="td02">
				<c:if test="${pushSvc.status == 'NOTCOMPLETE'}"><font color="red">Not Completed</font></c:if>
				<c:if test="${pushSvc.status == 'COMPLETE'}">Completed</c:if>			
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- 생성일/수정일 -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.createDate"/>
			</td>
			<td colspan="2" class="td02">${pushSvc.createDate}</td>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.common.modifyDate"/>
			</td>
			<td width="290px" class="td02">${pushSvc.modifyDate}</td>
		</tr>
		<tr class="line-bottom"><td colspan="6"/></tr>
	</table>
	<!-- list _ start -->
	
	<div class="line-clear"></div>
	
	<!-- button _ start -->
	<div class="footer">	
		<table style="width:100%">
			<tr>
				<td width="70%" height="30">
					<input type="button" value='<fmt:message key="label.common.list"/>' onclick="javascript:search()"/>
				<c:if test="${pushSvc.sendMsgType != 'PUSH'}">
					<c:if test="${pushSvc.os == 'android' || pushSvc.os == 'both'}">
						<input type="button" id="previewNotiAndroidBtn" value='<fmt:message key="label.common.previewNoti.android"/>' />
					</c:if>
					<c:if test="${pushSvc.os == 'ios' || pushSvc.os == 'both'}">
						<input type="button" id="previewNotiIosBtn" value='<fmt:message key="label.common.previewNoti.ios"/>' />
					</c:if>
				</c:if>
				<c:if test="${pushSvc.sendMsgType != 'NOTI'}">
					<c:if test="${pushSvc.os == 'android' || pushSvc.os == 'both'}">
						<input type="button" id="previewPushAndroidBtn" value='<fmt:message key="label.common.previewPush.android"/>' />
					</c:if>
					<c:if test="${pushSvc.os == 'ios' || pushSvc.os == 'both'}">
						<input type="button" id="previewPushIosBtn" value='<fmt:message key="label.common.previewPush.ios"/>' />
					</c:if>
				</c:if>	
				</td>
				<td width="30%" align="right">
				<c:if test="${authority.update}">
					<input type="button" value='<fmt:message key="label.common.modify"/>' onclick="javascript:update()"/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.delete"/>' onclick="javascript:confirmAndDelete()"/>
				</c:if>
					<input type="button" value='<fmt:message key="label.common.send"/>' onclick="javascript:sendPush()"/>
				</td>
			</tr>
		</table>
	</div>
	<!-- button _ end -->
</div>
<%@ include file="/include/footer.jspf" %>