<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.push.model.PushButton" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	//var cpid = '${pushSvc.cpId}';
	$(function() {
		getApps($("#os option:selected").val());
		changeSendReqType($("#sendReqType option:selected").val());
		changeSendType($("#sendType option:selected").val());
		changeSendMsgType($(':radio[name="sendMsgType"]:checked').val());

		// 저장
		$('#save').click(function(e) {
			e.preventDefault();
			$("#vForm").submit();
		});

		$("#reservedSendTime").datepicker({
			dateFormat:'yy/mm/dd'
		});

		// 유효성 검사
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				cpSn:"required",
				sendReqType:"required",
				sendType:"required",
				appListId:"required"
			},
			messages:{
				cpSn:{
					required:'<fmt:message key="validate.required"/>'
				},
				sendReqType:{
					required:'<fmt:message key="validate.required"/>'
				},
				sendType:{
					required:'<fmt:message key="validate.required"/>'
				},
				appListId:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});

		$("#os").change(function(){
			getApps($("#os").val());
		});
				  
		$("#sendReqType").change(function(){
			changeSendReqType($("#sendReqType").val());
		});

		$("#sendType").change(function(){
			changeSendType($("#sendType").val());
		});

		$("input[name=sendMsgType]:radio").click(function() {
			changeSendMsgType($(':radio[name="sendMsgType"]:checked').val());
		});
	});

	// 이전 페이지
	function cancel() {
		history.back();
	}

	function getApps(os) {
		var cpId = "${pushSvc.cpId}";
        $.ajax({
            url: '/push/getApps.json',
            dataType: 'json',
            data: {'os' : os,
                   'cpid' : 0,
                   'cpId' : cpId,
                   'mode' : 'U'},
            success: function(appsList) {
                var $appSel = $("#appId");
                $appSel.html("");
                for(var i = 0; i < appsList.length; i++) {
                    var apps = appsList[i];
                    //var list = Object.getOwnPropertyNames ( apps );
                    $appSel.append('<option value=' + apps.id + ' ' + ("${appName}" == apps.appName ? selected="selected" : "") + '>' + apps.appName + '</option>');
                }
            }
        });
    }
    
	function removeTr(target) {
		 $(target).parent().fadeOut("fast", function() {
			 $(target).parent().remove();
		 });
	}

	function removeFile(target) {
		 $(target).parent().fadeOut("fast", function() {
			 $(target).parent().parent().remove();
		 });
	}

	function removeAll(target) {
		$("#"+target).find('tr').fadeOut("fast", function() {
			$("#"+target).find('tr').remove();
		 });
	}

	var popupStatus = "width=1010, height=500, scrollbars=yes, toolbar=no, location=no, status=no, menubar=no";

	function selectMdn() {
		window.open("/push/popup/selectMdn.htm", "search", popupStatus);
	}

	function selectPushToken() {
		window.open("/push/popup/selectPushToken.htm", "search", popupStatus);
	}
	
	function selectButton() {
		window.open("/push/popup/selectButton.htm", "search", popupStatus);
	}
	
	function duplicationCheck(id, target) {
		var form = document.getElementById('vForm');
		var data = null;
		if (target == "mdn") {
			data = form.elements['mdnIds'];
		} else if (target == "pushToken") {
			data = form.elements['pushTokenIds'];
		} else if (target == "pushButton") {
			data = form.elements['pushButtonIds'];
		}

		if (data != null) {
			if (data.length == null) {
				if (data.value == id) return false;
			}

			for (var i = 0; i < data.length; i++) {
				if (data[i].value == id) return false;
			}
		}

		return true;
	}

	function selectPushBody() {
		window.open("/push/popup/selectPushBody.htm", "search", popupStatus);
	}

	function setPushBody(id) {
		// id 값이 숫자가 아닌경우 리턴
		if(isNaN(parseInt(id))) return;

		var url = '<%=Env.get("message.popup.url")%>';
		$.getJSON("/editor/getPushBody.json?editorId=" + id, function(push) {
			var editorId= id;
			$("#pushs tbody").html(
				"<tr>" +
		 	 		"<input type='hidden' name='groupId' value='" + editorId + "'>" +
		 	 		"<input type='hidden' name='pushBody' value='" + url + "?id=" + id + "'>" +
		 	 		"<td>"+ push.title + "</td>" +
				"</tr>"
			);
		});
	}

	function changeSendReqType(sendReqType) {
		if (sendReqType == 'APP_ID') {
			$("#selectMdnButton").attr("disabled", true);
			$("#removeAllMdnButton").attr("disabled", true);
			$("#selectPushTokenButton").attr("disabled", true);
			$("#removeAllPushTokenButton").attr("disabled", true);
			
			$( ".hidableTr1").css( "display", "none" );
			$( ".hidableTr2").css( "display", "none" );
		} else if (sendReqType == 'MDN') {
			$("#selectMdnButton").attr("disabled", false);
			$("#removeAllMdnButton").attr("disabled", false);
			$("#selectPushTokenButton").attr("disabled", true);
			$("#removeAllPushTokenButton").attr("disabled", true);

			$( ".hidableTr1").css( "display", "table-row" );
			$( ".hidableTr2").css( "display", "none" );
		}  else if (sendReqType == 'PUSH_TOKEN') {
			$("#selectMdnButton").attr("disabled", true);
			$("#removeAllMdnButton").attr("disabled", true);
			$("#selectPushTokenButton").attr("disabled", false);
			$("#removeAllPushTokenButton").attr("disabled", false);

			$( ".hidableTr1").css( "display", "none" );
			$( ".hidableTr2").css( "display", "table-row" );
		} else {
			$("#selectMdnButton").attr("disabled", true);
			$("#removeAllMdnButton").attr("disabled", true);
			$("#selectPushTokenButton").attr("disabled", true);
			$("#removeAllPushTokenButton").attr("disabled", true);

			$( ".hidableTr1").css( "display", "table-row" );
			$( ".hidableTr2").css( "display", "table-row" );
		}
	}

	function changeSendType(sendType) {
		if (sendType == 'INSTANT') {
			$("input[name=reservedSendTime]").attr("readonly",true);
			$("select[name=hour]").attr("disabled",true);
			$("select[name=minute]").attr("disabled",true);
			$( ".hidableTr3").css( "display", "none" );
		} else if (sendType == 'RESERVATION') {
			$("input[name=reservedSendTime]").attr("readonly",false);
			$("select[name=hour]").attr("disabled",false);
			$("select[name=minute]").attr("disabled",false);
			$( ".hidableTr3").css( "display", "table-row" );
		}
	}

	function changeSendMsgType(sendMsgType) {
		if (sendMsgType == 'NOTI') {
			$( ".hidableTr4").css( "display", "table-row" );
			$( ".hidableTr5").css( "display", "none" );
		} else if (sendMsgType == 'PUSH') {
			$( ".hidableTr4").css( "display", "none" );
			$( ".hidableTr5").css( "display", "table-row" );
		}  else if (sendMsgType == 'BOTH') {
			$( ".hidableTr4").css( "display", "table-row" );
			$( ".hidableTr5").css( "display", "table-row" );
		}
	}
</script>
<spring:hasBindErrors name="pushSvc"/>
<form method="post" id="vForm" name="vForm" action="/push/update.htm">
<div class="wrap00">
	<!-- input _ start -->
	<table style="width:100%">
		<tr class="line-top"><td colspan="6"/></tr>
		<tr>
			<!-- ID -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.requestId"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				${pushSvc.id}
				<input type="hidden" name="id" value="${pushSvc.id}">
			</td>
			<!-- ContentProvider -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.provider.cpName"/><font color="#FF0000">*</font>
			</td>
			<td width="290px" class="td02">
				<input type="text" name="cpSn" readOnly="readonly" value="${pushSvc.cpName}">
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<!-- OS -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.os"/>
			</td>
			<td colspan="2" class="td02">
				<select id="os" name="os">
					<c:if test="${pushSvc.os == 'android'}">
						<option value="android" selected>android</option>
						<option value="ios">ios</option>
						<option value="both">both</option>
					</c:if>
					<c:if test="${pushSvc.os == 'ios'}">
						<option value="android">android</option>
						<option value="ios" selected>ios</option>
						<option value="both">both</option>
					</c:if>
					<c:if test="${pushSvc.os == 'both'}">
						<option value="android">android</option>
						<option value="ios">ios</option>
						<option value="both" selected>both</option>
					</c:if>
				</select>
			</td>
			<!-- APP -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.application.appName"/><font color="#FF0000">*</font>
			</td>
			<td width="290px" class="td02">
				<select id="appId" name="appId">
				</select>			
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<tr>
			<!-- Send Request Type -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.sendReqType"/><font color="#FF0000">*</font>
			</td>
			<td colspan="2" class="td02">
				<select id="sendReqType" name="sendReqType">
					<c:if test="${pushSvc.sendReqType == 'MDN'}">
						<option value="MDN" selected>MDN</option>
						<!-- <option value="PUSH_TOKEN">Push Token</option>-->
						<option value="APP_ID">App ID</option>
					</c:if>
					<!-- 
					<c:if test="${pushSvc.sendReqType == 'PUSH_TOKEN'}">
						<option value="MDN">MDN</option>
						<option value="PUSH_TOKEN" selected>Push Token</option>
						<option value="APP_ID">App ID</option>
					</c:if>
					-->
					<c:if test="${pushSvc.sendReqType == 'APP_ID'}">
						<option value="MDN">MDN</option>
						<!-- <option value="PUSH_TOKEN">Push Token</option>-->
						<option value="APP_ID" selected>App ID</option>
					</c:if>
				</select>
			</td>
			<!-- Send Type -->
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.sendType"/>
			</td>
			<td width="290px" class="td02">
				<select id="sendType" name="sendType">
				<c:forEach items="${sendTypes}" var="sendTypeEnum">
					<option value="${sendTypeEnum}" ${pushSvc.sendType == sendTypeEnum ? "selected='selected'":""}>
						${sendTypeEnum}
					</option>
				</c:forEach>
				</select>	
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>
		<!-- MDN -->
		<tr class="hidableTr1">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.mdn"/>
			</td>
			<td colspan="6" class="td02">
				<input type="button" id="selectMdnButton" onclick="javascript:selectMdn();" value="<fmt:message key='label.common.add'/>" style="width: 80px;"/>
				<input type="button" id="removeAllMdnButton" onclick="javascript:removeAll('mdns');" value="<fmt:message key='label.common.removeAll'/>" style="width: 80px;"/>
				<table id="mdns" border="0">
					<tr>
					<c:forEach items="${pushSvc.pushMdns}" var="pushMdn">
						<tr>
							<td>
							<input type='hidden' name='pushmdnIds' id='pushmdnIds' value='${pushMdn.id}'>${pushMdn.mdn}
							<input type='hidden' name='mdns' id='mdns' value='${pushMdn.mdn}'>
							</td>
							<td onclick='removeTr(this)'>
								<img src='/images/button/btn_remove.png' width='20' height='20'>
							</td>
						</tr>
					</c:forEach>
					</tr>
				</table>
			</td>
		</tr>
		<tr class="line-dot hidableTr1"><td colspan="6"/></tr>
		<!-- Push Token -->
		<tr class="hidableTr2">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushToken"/>
			</td>
			<td colspan="6" class="td02">
				<input type="button" id="selectPushTokenButton" onclick="javascript:selectPushToken();" value="<fmt:message key='label.common.add'/>" style="width: 80px;"/>
				<input type="button" id="removeAllPushTokenButton" onclick="javascript:removeAll('pushTokens');" value="<fmt:message key='label.common.removeAll'/>" style="width: 80px;"/>
				<table id="pushTokens" border="0">
					<tr>
					<c:forEach items="${pushSvc.pushTokens}" var="pushToken">
						<tr>
							<td>
								<input type='hidden' name='tokenIds' id='tokenIds' value='${pushToken.id}'>${pushToken.pushToken}
								<input type='hidden' name='pushTokens' id='pushTokens' value='${pushToken.pushToken}'>
							</td>
							<td onclick='removeTr(this)'>
								<img src='/images/button/btn_remove.png' width='20' height='20'>
							</td>
						</tr>
					</c:forEach>
					</tr>
				</table>
			</td>
		</tr>
		<tr class="line-dot hidableTr2"><td colspan="6"/></tr>	
		<!-- 예약 전송 시간 -->
		<tr class="hidableTr3">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.reservedSendTime"/>
			</td>
			<td colspan="6" class="td02">
				<input type="text" name="reservedSendTime" id="reservedSendTime" value="<fmt:formatDate value="${pushSvc.reservedSendTime}" pattern="yyyy/MM/dd" />">
				<select name="hour" id="hour">
					<c:forEach var = "hours" begin="0" end="23" step="1">
						<option value="${hours}" <c:if test="${hours == hour}">selected</c:if>>${hours}</option>
					</c:forEach>
				</select>
				<select name="minute" id="minute">
					<c:forEach var = "minutes" begin="0" end="59" step="5">
						<option value="${minutes}" <c:if test="${minutes == minute}">selected</c:if>>${minutes}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="line-dot hidableTr3"><td colspan="6"/></tr>
		<!-- SendMsgType: Noti only, Push only, Noti/Push Both -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.sendMsgType"/>
			</td>
			<td colspan="6" class="td02">
				<input type="radio" id="sendMsgType" name="sendMsgType" value="BOTH" style="width:20px;" <c:if test="${pushSvc.sendMsgType == 'BOTH' }">checked="checked"</c:if>>Both (Noti+Push)
				<input type="radio" id="sendMsgType" name="sendMsgType" value="NOTI" style="width:20px;" <c:if test="${pushSvc.sendMsgType == 'NOTI' }">checked="checked"</c:if>>Noti Only
				<input type="radio" id="sendMsgType" name="sendMsgType" value="PUSH" style="width:20px;" <c:if test="${pushSvc.sendMsgType == 'PUSH' }">checked="checked"</c:if>>Push Only
			</td>
		</tr>
		<tr class="line-dot"><td colspan="6"/></tr>		
		<!-- Noti Title -->
		<tr class="hidableTr4">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.notiTitle"/>
			</td>
			<td colspan="6" class="td02">
				<input type="text" id="notiTitle" name="notiTitle" value="${pushSvc.notiTitle}" style="width:99%;">
			</td>
		</tr>
		<tr class="line-dot hidableTr4"><td colspan="6"/></tr>
		<!-- Noti Message -->
		<tr class="hidableTr4">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.notiMsg"/>
			</td>
			<td colspan="6" class="td02">
				<input type="text" id="notiMsg" name="notiMsg" value="${pushSvc.notiMsg}" style="width:99%;">
			</td>
		</tr>
		<tr class="line-dot hidableTr4"><td colspan="6"/></tr>
		<!-- Push Type 
		<tr class="hidableTr5">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushType"/>
			</td>
			<td colspan="6" class="td02">
				<select id="pushType" name="pushType">
				<c:forEach items="${pushTypes}" var="pushTypeEnum">
					<option value="${pushTypeEnum}" ${pushSvc.pushType == pushTypeEnum ? "selected='selected'":""}>
						${pushTypeEnum}
					</option>
				</c:forEach>
				</select>			
			</td>
		</tr>
		<tr class="line-dot hidableTr5"><td colspan="6"/></tr>
		-->
		<!-- Push Title -->
		<tr class="hidableTr5">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushTitle"/>
			</td>
			<td colspan="6" class="td02">
				<input type="text" id="pushTitle" name="pushTitle" value="${pushSvc.pushTitle}" style="width:99%;">
			</td>
		</tr>
		<tr class="line-dot hidableTr5"><td colspan="6"/></tr>
		<!-- Push Body -->
		<tr class="hidableTr5">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.pushBody"/>
			</td>
			<td colspan="6" class="td02">
				<table id="pushs" style="margin: 5px 0pt;" class="simple">
					<tbody>
						<tr>
							<input type='hidden' name='groupId' id='groupId' value='${pushSvc.groupId}'>
							<td>
							<input type='hidden' name='pushBody' id='pushBody' value='${pushSvc.pushBody}'>
							${pushSvc.description}
							</td>
						</tr>
					</tbody>
				</table>
				<input type="button" onclick="selectPushBody()" value="<fmt:message key='label.common.select'/>" style="margin-top:5px; width: 80px;" />
			</td>
		</tr>
		<tr class="line-dot hidableTr5"><td colspan="6"/></tr>
		<!-- Push Button -->
		<tr class="hidableTr5">
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.button"/>
			</td>
			<td colspan="6" class="td02">
				<table id="buttons" class="simple">
				<thead>
					<tr>
						<th style="width:10%;"><fmt:message key="label.common.id"/></th>
						<th style="width:10%;"><fmt:message key="label.pushButton.position"/></th>
						<th style="width:20%;"><fmt:message key="label.pushButton.title"/></th>
						<th style="width:10%;"><fmt:message key="label.pushButton.type"/></th>
						<th style="width:40%;"><fmt:message key="label.pushButton.action"/></th>
						<th style="width:10%;"><fmt:message key="label.common.remove"/></th>
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
						<td>
							<input type='hidden' name='pushButtonIds' id='pushButtonIds' value='${pushButton.id}'>
							<%=pb.getId()%>
						</td>
						<td><%=pb.getPosition()%></td>
						<td><%=pb.getTitle()%></td>
						<td><%=pb.getType()%></td>
						<td><%=pb.getAction()%></td>
						<td><input type="button" value="<fmt:message key="label.common.remove"/>" onclick="javascript:removeFile(this);" style="width:70px;"/></td>
					</tr>
					</c:forEach>
					</c:if>
				</tbody>
				</table>
				<input type="button" onclick="javascript:selectButton();" value="<fmt:message key='label.common.add'/>" style="margin-top:5px; width: 80px;" />
			</td>
		</tr>
		<tr class="line-dot hidableTr5"><td colspan="6"/></tr>
		<!-- status -->
		<tr>
			<td height="25" class="td01">
				<span class="bul_dot1">◆</span>
				<fmt:message key="label.pushSvc.status"/>
			</td>
			<td colspan="6" class="td02">
				<select id="status" name="status">
					<c:if test="${pushSvc.status == 'NOTCOMPLETE'}">
						<option value="NOTCOMPLETE" selected>Not Completed</option>
						<option value="COMPLETE">Completed</option>
					</c:if>
					<c:if test="${pushSvc.status == 'COMPLETE'}">
						<option value="NOTCOMPLETE">Not Completed</option>
						<option value="COMPLETE" selected>Completed</option>
					</c:if>
				</select>
			</td>
		</tr>
		<tr class="line-bottom"><td colspan="6"/></tr>
	</table>
	<!-- input _ end -->

	<div class="line-clear"></div>
	
	<!-- button _ start -->
	<div class="footer">
		<table style="width:100%">
			<tr>
				<td width="50%" height="30"></td>
				<td width="50%" align="right">	
				<c:if test="${authority.update}">
					<input type="button" id="save" value='<fmt:message key="label.common.save"/>'/>
				</c:if>
				<c:if test="${authority.delete}">
					<input type="button" value='<fmt:message key="label.common.cancel"/>' onclick="javascript:cancel()"/>
				</c:if>	
				</td>
			</tr>
		</table>
	</div>				
	<!-- button _ end -->
</div>
</form>
<%@ include file="/include/footer.jspf" %>