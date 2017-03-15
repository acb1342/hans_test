<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="u" uri="/WEB-INF/tlds/util.tld" %>
<%@ page import="com.uangel.platform.util.Env" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="title" content="uPUSH" />
<meta name="description" content="uPUSH" />
<meta name="keywords" content="" />
<meta name="language" content="ko" />

<title>Admin - SKT EVC</title>

<!-- Le styles -->
<link href="${contextPath}/editor/css/bootstrap-combined.min.css" rel="stylesheet">
<link href="${contextPath}/editor/css/layoutit.css" rel="stylesheet">
<link href="${contextPath}/js/jquery/alert/jquery.alerts.css" rel="stylesheet">

<link rel="shortcut icon" href="http://faviconist.com/icons/2d3196a422c2b68cb307c4d8a1958527/favicon.ico" />

<script type="text/javascript" src="${contextPath}/editor/js/jquery-2.0.0.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/bootstrap.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery-ui.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery.ui.touch-punch.min.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/jquery.htmlClean.js"></script>
<script type="text/javascript" src="${contextPath}/editor/ckeditor/ckeditor.js"></script>
<script type="text/javascript" src="${contextPath}/editor/ckeditor/config.js"></script>
<script type="text/javascript" src="${contextPath}/editor/js/scripts.js"></script>

<script type="text/javascript" src="${contextPath}/js/jquery/form/jquery.form.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/validator/jquery.validate.custom.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/validator/jquery.validate.ext.js"></script>
<script type="text/javascript" src="${contextPath}/js/jquery/alert/jquery.alerts.custom.js"></script>

<!-- custom js -->
<script type="text/javascript" src="${contextPath}/js/common.js"></script>
<script type="text/javascript" src="${contextPath}/js/design.js"></script>

<script type="text/javascript">
$(function() {
	
	CKEDITOR.on( 'dialogDefinition', function( ev ) {

	    var dialogName = ev.data.name;
	    var dialogDefinition = ev.data.definition;
	
	    // link 정보 수정
	    if ( dialogName == 'link' ) {
	    	// Get a reference to the "Link Info" tab.
	        var infoTab = dialogDefinition.getContents( 'info' );
	        var urlField = infoTab.get( 'url' );
	        urlField[ 'default' ] = 'www.ibk.com';
	        
            oldOnShow = dialogDefinition.onShow;
            dialogDefinition.onShow = function() {
                 oldOnShow.apply(this, arguments);
                 this.hidePage('target');
                 this.selectPage('upload');
                 return false;
            };
	    }

	    // image 정보 수정
        if ( dialogName == 'image') {
        	
            oldOnShow = dialogDefinition.onShow;
            dialogDefinition.onShow = function() {
                 oldOnShow.apply(this, arguments);
                 this.hidePage('Link');
                 this.selectPage('Upload');
                 return false;
            };
        }		    
	});		
		
	
	clearDemo();
		
		$(".demo").html('${editData}');
		
		changeYoutubeFlag($("#youtubeFlag option:selected").val());
		changeAudioFlag($("#audioFlag option:selected").val());
			
		// form 방식
		$('#save').click(function(e) {
			e.preventDefault();
			downloadLayoutSrc();
		
			var title = $('#title').val();
			var templateFlag = $('#templateFlag').val();
			var youtubeFlag = $('#youtubeFlag').val();	
			var youtubeUrl = $('#youtubeUrl').val();		

			var editData = $('.demo').html();
			var downloadData = $('#downloadModal textarea').val();
			$('#editData').val(editData);
			$('#downloadData').val(downloadData);

			if (youtubeFlag == 'Y') {
				var width = $('#youtubeWidth').val();
				var height = $('#youtubeHeight').val();
			    var tempDiv = document.createElement('div');
			    tempDiv.innerHTML = downloadData;
			    var CKEIframesL = tempDiv.getElementsByClassName("cke_iframe").length;
			    if (CKEIframesL > 0) {
			    	tempDiv.getElementsByClassName("cke_iframe")[0].outerHTML = youtubeInnerHTML(youtubeUrl, width, height);
			    }
				downloadData = tempDiv.innerHTML.toString(downloadData);
				$('#downloadData').val(downloadData);
			}
			
			$("#vForm").submit();
		});
	
		// 목록
		$("#cancel").click(function(e) {
			clearDemo();
			document.location = "/editor/search.htm";
		});
		
		// 유효성 검사
		$("#vForm").validate({
			onfocusout:false,
			onkeyup:false,
			rules:{
				title:"required"
			},
			messages:{
				title:{
					required:'<fmt:message key="validate.required"/>'
				}
			}
		});

		$("#audioFlag").change(function(){
			changeAudioFlag($("#audioFlag").val());
		});
		
		$("#youtubeFlag").change(function(){
			changeYoutubeFlag($("#youtubeFlag").val());
		});
	});

	function youtubeInnerHTML(url, width, height) {
		if (typeof url == 'undefined') return '';
		
		return '<iframe width="' + width + '" height="' + height + '" src="' + url + '" frameborder="0" allowfullscreen></iframe>';
	}

	function changeAudioFlag(flag) {
		if (flag == 'N') {
			$("input[name=audioUrl]").attr("readonly",true);
			$( ".hidableTrAudio").css( "display", "none" );
		} else if (flag == 'Y') {
			$("input[name=audioUrl]").attr("readonly",false);
			$( ".hidableTrAudio").css( "display", "table-row" );
		}
	}
	
	function changeYoutubeFlag(flag) {
		if (flag == 'N') {
			$("input[name=youtubeUrl]").attr("readonly",true);
			$("input[name=youtubeWidth]").attr("readonly",true);
			$("input[name=youtubeHeight]").attr("readonly",true);
			$( ".hidableTr").css( "display", "none" );
		} else if (flag == 'Y') {
			$("input[name=youtubeUrl]").attr("readonly",false);
			$("input[name=youtubeWidth]").attr("readonly",false);
			$("input[name=youtubeHeight]").attr("readonly",false);
			$( ".hidableTr").css( "display", "table-row" );
		}
	}
</script>
</head>
<body class="edit">
<form method="post" id="vForm" name="vForm" action="/editor/create.htm">
	
	<input type="hidden" id="editData" name="editData" value=""/>
	<input type="hidden" id="downloadData" name="downloadData" value=""/>

<div class="wrap00">
	<div class="section1">
		<table style="width:100%">
			<tr class="line-top"><td colspan="6"/></tr>
			<!-- Title -->
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.title"/><font color="#FF0000">*</font>
				</td>
				<td colspan="6" class="td02">
					<input type="text" id="title" name="title" value="${contentEditor.title}" style="width:500px;">
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.template"/><font color="#FF0000">*</font>
				</td>
				<td colspan="3" class="td02">
					<select name="templateFlag" id="templateFlag">
					<option value="N"><fmt:message key="label.contentEditor.templateFlag.N"/></option>
					<option value="Y"><fmt:message key="label.contentEditor.templateFlag.Y"/></option>
					</select>			
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>
			<tr>
				<!-- Push Type -->
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.pushType"/><font color="#FF0000">*</font>
				</td>
				<td colspan="3" class="td02">
					<select name="pushType">
					<c:forEach items="${pushTypes}" var="pushTypeEnum">
						<option value="${pushTypeEnum}" ${contentEditor.pushType == pushTypeEnum ? "selected='selected'":""}>
							${pushTypeEnum}
						</option>
					</c:forEach>
					</select>	
				</td>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.youtubeFlag"/><font color="#FF0000">*</font>
				</td>
				<td width="290px" class="td02">
					<select name="youtubeFlag" id="youtubeFlag">
					<c:forEach items="${youtubeFlags}" var="youtubeFlagEnum">
						<option value="${youtubeFlagEnum}" ${contentEditor.youtubeFlag == youtubeFlagEnum ? "selected='selected'":""}>
							${youtubeFlagEnum}
						</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>	
			<tr class="hidableTr">
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.youtubeUrl"/>
				</td>
				<td colspan="6" class="td02">
					<input type="text" id="youtubeUrl" name="youtubeUrl" value="${contentEditor.youtubeUrl}" style="width:98%;">
					<font color="#FF0000"> Embed URL</font>
				</td>
			</tr>
			<tr class="line-dot hidableTr"><td colspan="6"/></tr>	
			<!-- Youtube Width/Height -->
			<tr class="hidableTr">
				<!-- Width -->
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.youtubeWidth"/>
				</td>
				<td colspan="3" class="td02">
					<input type="text" name="youtubeWidth" id="youtubeWidth" value="${contentEditor.youtubeWidth}" style="width:100px;">
					<font color="#FF0000"> ex) px or % : 300 or 100%</font>
				</td>
				<!-- Height -->
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.youtubeHeight"/>
				</td>
				<td colspan="6" class="td02">
					<input type="text" name="youtubeHeight" id="youtubeHeight" value="${contentEditor.youtubeHeight}" style="width:100px;">
					<font color="#FF0000"> ex) px or % : 300 or 100%</font>
				</td>
			</tr>
			<tr class="line-dot hidableTr"><td colspan="6"/></tr>
			
			<tr>
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.audioFlag"/><font color="#FF0000">*</font>
				</td>
			
				<td colspan="2" class="td02">
					<select name="audioFlag" id="audioFlag">
					<c:forEach items="${audioFlags}" var="audioFlagEnum">
						<option value="${audioFlagEnum}" ${contentEditor.audioFlag == audioFlagEnum ? "selected='selected'":""}>
							${audioFlagEnum}
						</option>
					</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>	
			<tr class="hidableTrAudio">
				<td height="25" class="td01">
					<span class="bul_dot1">◆</span>
					<fmt:message key="label.contentEditor.audioUrl"/>
				</td>
			
				<td colspan="2" class="td02">
					<input type="text" id="audioUrl" name="audioUrl" value="${contentEditor.audioUrl}" style="width:99%;">
					<font color="#FF0000"> URL (mp3)</font>
				</td>
			</tr>
			<tr class="line-dot hidableTrAudio"><td colspan="6"/></tr>	
			
			<tr>
				<td colspan="6" class="td03">
					<fmt:message key="label.contentEditor.editingOrder"/><br>
					&nbsp; <fmt:message key="label.contentEditor.editingOrder.1"/><font color="#FF0000">(<fmt:message key="label.common.dragMode"/>)</font><br>
					&nbsp; <fmt:message key="label.contentEditor.editingOrder.2"/><font color="#FF0000">(<fmt:message key="label.common.editMode"/>)</font><br>
					&nbsp; <fmt:message key="label.contentEditor.editingOrder.3"/><font color="#FF0000">(<fmt:message key="label.common.editMode"/>)</font>
				</td>
			</tr>
			<tr class="line-dot"><td colspan="6"/></tr>				
		</table>
	</div>
</div>
</form>

<div class="wrap00">
	<div class="section2">
		<!--  container -->
		<div class="">
			<!--  Update Mode, Drag Mode  -->
			<div class="line-clear"></div>
			<div class="btn-group" data-toggle="buttons-radio">
				<button type="button" class="btn" id="edit-mode"><i class="icon-edit icon-white"></i> <fmt:message key="label.common.editMode"/></button>
				<button type="button" class="btn active" id="drag-mode"><i class="icon-move icon-white"></i> <fmt:message key="label.common.dragMode"/></button>
			</div>
			<div class="row-fluid">
				<!--/span-->
				<div class="span10 demo" style="width:724px;">
				</div>
				<!--/span-->
				<div class="span2">
					<div class="sidebar-nav">
						<ul class="nav nav-list accordion-group">

							<!--  화면 구성 -->
							<li class="nav-header">
								<i class="icon-plus icon-black"></i> <fmt:message key="label.contentEditor.screenConfig"/> (%)
							</li>
							
							<li class="rows" id="estRows">
								
								<div class="lyrow">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<div class="preview"><input type="text" value="100" class="form-control"></div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span12 column"></div>
										</div>
									</div>
								</div>
								
								<div class="lyrow">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<div class="preview"><input type="text" value="50  50" class="form-control"></div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span6 column"></div>
											<div class="span6 column"></div>
										</div>
									</div>
								</div>
								
								<div class="lyrow">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<div class="preview"><input type="text" value="66  33" class="form-control"></div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span8 column"></div>
											<div class="span4 column"></div>
										</div>
									</div>
								</div>

								<div class="lyrow">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<div class="preview"><input type="text" value="33  66" class="form-control"></div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span4 column"></div>
											<div class="span8 column"></div>
										</div>
									</div>
								</div>
								
								<div class="lyrow">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<div class="preview"><input type="text" value="33  33  33" class="form-control"></div>
									<div class="view">
										<div class="row-fluid clearfix">
											<div class="span4 column"></div>
											<div class="span4 column"></div>
											<div class="span4 column"></div>
										</div>
									</div>
								</div>
							</li>
						</ul>
						
						<!-- 입력 구성 부분 -->
						<ul class="nav nav-list accordion-group">
							<li class="nav-header">
								<i class="icon-plus icon-black"></i> <fmt:message key="label.contentEditor.inputConfig"/>
								<div class="pull-right popover-info">
									<i class="glyphicon glyphicon-question-sign "></i> 
									<div class="popover fade right">
										<div class="arrow"></div>
										<h3 class="popover-title">Help</h3>
										<div class="popover-content">Drag &amp; Drop the elements inside the columns where you want to insert it. And from there, you can configure the style of that element. If you need more info please visit <a target="_blank" href="http://getbootstrap.com/css">Base CSS.</a></div>
									</div>
								</div>
							</li>
							<li class="boxes" id="elmBase">
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.titleFormat"/></div>
									<div class="view">
										<div contenteditable="true">
										<h3>h3. <fmt:message key="label.contentEditor.titleContent"/></h3>
										</div>
									</div>
								</div>
								
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.paragraphFormat"/></div>
									<div class="view">
										<div contenteditable="true">
										<p>Lorem ipsum dolor sit amet, <strong>consectetur adipiscing elit</strong>. Aliquam eget sapien sapien. Curabitur in metus urna. In hac habitasse platea dictumst. Phasellus eu sem sapien, sed vestibulum velit. Nam purus nibh, lacinia non faucibus et, pharetra in dolor. Sed iaculis posuere diam ut cursus. <em>Morbi commodo sodales nisi id sodales. Proin consectetur, nisi id commodo imperdiet, metus nunc consequat lectus, id bibendum diam velit et dui.</em> Proin massa magna, vulputate nec bibendum nec, posuere nec lacus. <small>Aliquam mi erat, aliquam vel luctus eu, pharetra quis elit. Nulla euismod ultrices massa, et feugiat ipsum consequat eu. </small></p>
										</div>
									</div>
								</div>

								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.tableFormat"/></div>
									<div class="view">
										<div contenteditable="true">
										<table class="table">
											<thead>
												<tr class="success">
													<th>#</th>
													<th>Currency</th>
													<th>Buy</th>
													<th>Sell</th>
												</tr>
											</thead>
											<tbody>
												<tr class="warning">
													<td>1</td>
													<td>USA Dollar</td>
													<td>1,117.21(1.75%) </td>
													<td>1,078.79(1.75%)</td>
												</tr>
												<tr>
													<td>2</td>
													<td>Japan 100Yen</td>
													<td>942.31(1.75%) </td>
													<td>909.91(1.75%) </td>
												</tr>
												<tr class="warning">
													<td>3</td>
													<td>EU Euro</td>
													<td>1,274.40 (1.75%)</td>
													<td>1,230.58 (1.75%)</td>
												</tr>
											</tbody>
										</table>
										</div>
									</div>
								</div>

								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.imageFormat"/></div>
									<div class="view">
										<div contenteditable="true">
										<img alt="140x140" src="http://lorempixel.com/140/140/">
										</div>
									</div>
								</div>
								
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.listFormat.circle"/></div>
									<div class="view">
										<div contenteditable="true">
										<ul>
											<li>list 1</li>
											<li>list 2</li>
											<li>list 3</li>
											<li>list 4</li>
										</ul>
										</div>
									</div>
								</div>
								
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.listFormat.number"/></div>
									<div class="view">
										<div contenteditable="true">
										<ol>
											<li>list number 1</li>
											<li>list number 2</li>
											<li>list number 3</li>
											<li>list number 4</li>
										</ol>
										</div>
									</div>
								</div>	
								
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
										<!-- <button type="button" class="btn btn-xs btn-info" data-target="#editorModal" role="button" data-toggle="modal">edit</button> -->
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.addressFormat"/></div>
									<div class="view">
										<address contenteditable="true">
											<strong>참! 좋은 기업 IBK 기업은행</strong><br>
												서울특별시 중구 을지로 79(을지로 2가)<br>
											<abbr title="Phone">고객센터 : </abbr> 1566-2566, 1588-2588<br>
											<abbr title="Phone">서민금융종합지원센터 : </abbr> 1644-0533
										</address>
									</div>
								</div>
																					
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div class="preview"><fmt:message key="label.contentEditor.descriptionFormat"/></div>
									<div class="view">
										<div contenteditable="true">
										<dl>
											<dt>IBK ONE앱 통장</dt>
											<dd>IBK기업은행의 스마트폰 전용 입출금식 통장! </dd>
											<dt>IBK 서민섬김통장</dt>
											<dd>소액 예금에도 1인당 5천만원까지 금리를 우대하는 상품  </dd>
											<dt>참! 좋은 약속카드</dt>
											<dd>은행이 모으고 보태서 연 1회 목돈으로 최대 50만원 캐시백 혜택</dd>
										</dl>
										</div>
									</div>
								</div>							
							
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div id="audio" class="preview">Audio</div>
									<div class="view">
										<div contenteditable="true">
										<%--
											<img alt="" src="<%=Env.get("iamge.url")%>/playsel_bt_n%402x.png" />
						 				--%>
						 					<audio controls="" preload="" loop="">
												<source src="http://192.168.1.94:10695/brtim/content_dn/10/00/00/03/42/0000034276.mp3" type="audio/mp3"/>
											</audio>
										</div>
									</div>
								</div>
								
								<div class="box box-element">
									<a href="#close" class="remove label label-important"><i class="icon-remove icon-white"></i> <fmt:message key="label.contentEditor.remove"/></a>
									<span class="drag label label-info"><i class="icon-move icon-white"></i> <fmt:message key="label.contentEditor.drag"/></span>
									<span class="configuration">
									</span>
									<div id="youtube" class="preview">Youtube</div>
									<div class="view">
										<div contenteditable="true">
	​										<iframe width="560" height="315" src="" frameborder="0" allowfullscreen></iframe>
										</div>
									</div>
								</div>						
							</li>
						</ul>
					</div>
				</div>
				<div id="download-layout">
					<div class="container"></div>
				</div>
			</div>
			
			<!--/row-->
			<script type="text/javascript">
				$(document).ready(function() {});
			</script>
		</div>
		<!--/.fluid-container-->
	
		<div class="modal hide fade" role="dialog" id="editorModal">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3><fmt:message key="label.common.editor"/></h3>
			</div>
			<div class="modal-body">
				<p>
					<textarea id="contenteditor"></textarea>
				</p>
			</div>
			<div class="modal-footer"> 
			<a id="savecontent" class="btn" data-dismiss="modal"><fmt:message key="label.common.save"/></a> 
			<a class="btn" data-dismiss="modal"><fmt:message key="label.common.close"/></a> 
			</div>
		</div>
		
		<div class="modal hide fade" role="dialog" id="downloadModal">
			<div class="modal-header">
				<a class="close" data-dismiss="modal">×</a>
				<h3>Download</h3>
			</div>
			<div class="modal-body">
				<p>copy and paste it to your code.</p>
				<div class="btn-group">
					<button type="button" id="fluidPage" class="active btn btn-info"><i class="icon-fullscreen icon-white"></i> with fluid page</button>
					<button type="button" id="fixedPage" class="btn btn-info"><i class="icon-screenshot icon-white"></i> with fixed page</button>
				</div>
				<br>
				<br>
				<p>
					<textarea></textarea>
				</p>
			</div>
			<div class="modal-footer"> <a class="btn" data-dismiss="modal">Close</a> </div>
		</div>
	</div>
</div>
	
	<!--  저장/취소 버튼 -->

	<div class="wrap00">
		<div class="line-bottom"></div>
		<div class="line-clear"></div>
		<div class="" style="float:left">
			<ul class="nav" id="menu-layoutit">
				<li class="divider-vertical"></li>
				<li>
				<c:if test="${authority.create}">
					<button class="btn btn-jquery" id="save"><fmt:message key="label.common.save"/></button>
				</c:if>
					<button class="btn btn-jquery" id="cancel"><fmt:message key="label.common.cancel"/></button>
				</li>
			</ul>
		</div>
		<div class="" style="float:right">
			<ul class="nav" id="menu-layoutit">
				<li class="divider-vertical"></li>
				<li>
					<button class="btn btn-jquery" id="preview"><i class="icon-eye-open icon-white"></i> <fmt:message key="label.common.preview"/></button>
					<button class="btn btn-jquery" id="undo" ><i class="icon-arrow-left icon-white"></i> <fmt:message key="label.common.undo"/></button>
					<button class="btn btn-jquery" id="redo" ><i class="icon-arrow-right icon-white"></i> <fmt:message key="label.common.redo"/></button>
					<button class="btn btn-jquery" id="clear"><i class="icon-repeat icon-white"></i> <fmt:message key="label.common.clear"/></button>
				</li>
			</ul>
		</div>
	</div>
</body>
</html>