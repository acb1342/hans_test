<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- include/header.jspf START --%>
<%@ page import="com.mobilepark.doit5.common.Flag" %>
<%@ page import="com.uangel.platform.util.DateUtil" %>
<%@ page import="com.uangel.platform.util.Env" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Calendar" %>
<%
	// 00 ~ 23 시
	String[] hourList = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", 
						 "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", 
						 "21", "22", "23"};
	
	// 1~4 주차
	Integer[] weekList = {1, 2, 3, 4};
	
	// 월~일 요일
	String[] dayList = { "일", "월", "화", "수", "목", "금", "토" };
	
	request.setAttribute("hourList", hourList);
	request.setAttribute("weekList", weekList);
	request.setAttribute("dayList", dayList);
%>
<%!
	// 년월주차 표시 포맷팅
	public String formatDate(String param) {
		String[] splitted = param.split("/");
		StringBuilder formatted = new StringBuilder();
		formatted.append(splitted[0] + "년 " + splitted[1] + "월 ");
		if (splitted.length >= 3) {
			formatted.append(splitted[2] + "주");
		}
		formatted.append("차");
		
		return formatted.toString();
	}
	
	// 요일 포맷팅
	public String formatDayOfWeek(Date contentReleaseDate) {
		if (contentReleaseDate == null) {
			return "";
		}
		
		String releaseDateStr = "";
		StringBuilder releaseDate = new StringBuilder(128);
		Calendar calendar = Calendar.getInstance();
		final String[] week = { "일", "월", "화", "수", "목", "금", "토" };
		
		calendar.setTime(contentReleaseDate);
		releaseDateStr = DateUtil.dateToString(contentReleaseDate, "yyyy.MM.dd");
		releaseDate.append(releaseDateStr + "(" + week[calendar.get(calendar.DAY_OF_WEEK) - 1] + ")");		
		
		return releaseDate.toString();
	}	
%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="locale" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="upush" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="d" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="u" uri="/WEB-INF/tlds/util.tld" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>Admin - SKT EVC</title>
	
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta http-equiv="Description" content="" />
	<meta http-equiv="Keywords" content="" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	
	<!-- fav and touch icons -->
	<link rel="shortcut icon" href="http://faviconist.com/icons/2d3196a422c2b68cb307c4d8a1958527/favicon.ico" />

	<!-- plugin css -->
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/libs/bootstrap/bootstrap.min.css">
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/libs/bootstrap/bootstrap-responsive.min.css">
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/libs/font-awesome/font-awesome.css">
	
	<!--
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/jquery/ui/1.10.4/themes/sunny/jquery-ui.css">
	-->
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/jquery/ui/1.10.4/themes/smoothness/jquery-ui.css">
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/jquery/alert/jquery.alerts.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/js/jquery/fancybox/jquery.fancybox.css"/>
	
	<!-- custom css -->
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/displaytag.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/alternative.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/common.css" media="all" />
	<link rel="stylesheet" type="text/css" href="${contextPath}/css/popup.css" media="all" />
	
	<!-- plugin js -->
	<script type="text/javascript" src="${contextPath}/js/jquery/jquery-1.7.2.custom.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/form/jquery.form.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/validator/jquery.validate.custom.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/validator/jquery.validate.ext.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/alert/jquery.alerts.custom.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/ui/1.10.4/jquery-ui.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/ui/1.10.4/jquery.ui.widget.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/ui/1.10.4/jquery.ui.mouse.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/ui/1.10.4/jquery.ui.resizable.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/ui/1.10.4/jquery.ui.button.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/ui/jquery.filestyle.js"></script>
	<script type="text/javascript" src="${contextPath}/js/jquery/fancybox/jquery.fancybox.js"></script>
	<script type="text/javascript" src="${contextPath}/js/libs/bootstrap/bootstrap-filestyle-0.1.0.js"></script>
	<script type="text/javascript" src="${contextPath}/js/libs/bootstrap/bootstrap-tooltip.js"></script>
	
	<!-- custom js -->
	<script type="text/javascript" src="${contextPath}/js/common.js"></script>
	<script type="text/javascript" src="${contextPath}/js/design.js"></script>
	
	<%@ include file="/include/admin.jspf" %>
</head>
<body class="set_menu_tree">
<%-- include/header.jspf   END --%>
<style type="text/css" media="all">
	@import url("/js/extjs/resources/css/ext-all.css");
	@import url("/js/extjs/resources/css/xtheme-gray.css");
	body { margin-left:0px; margin-top:0px; margin-right:0px; margin-bottom:0px; }
</style>
<script src="/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="/js/extjs/ext-all.js" type="text/javascript"></script>
<script type="text/javascript">
	var tree;

	// 메뉴를 트리로 생성
	function displayTree(rootNode) {
		tree = new Ext.tree.TreePanel({
			title:"SKT-EVC Menu Management",
			el:'tree-div',
			useArrows:true,
			autoScroll:true,
			animate:true,
			height:500,
			singleExpand:true,
			loader:{
				dataUrl:'/admin/menu/getChildMenus.json',
				createNode:function(menu) {
					var node = {
							id:menu.id,
							text:menu.title,
							url:menu.url,
							leaf:(menu.type === "LEAF")
					};
					return Ext.tree.TreeLoader.prototype.createNode.call(this, node);
				}
			},
			root:rootNode,
			//rootVisible:false,
			bbar:['->',{
				text:'Reload',
				handler:function() { reloadTree(); }
			}],
			<c:if test="${authority.update}">
				'enableDD':true
			</c:if>
		});

		// render the tree
		tree.render();
		rootNode.expand(1);

		tree.on('click', onClick);
	<c:if test="${authority.update}">
		tree.on('contextmenu', onContextMenu);
		tree.on('movenode', moveEvent);
	</c:if>

		function onClick(node, e) {
			parent.rightFrame.location = "/admin/menu/detail.htm?id=" + node.id;

			var nodeAttr = node.attributes;
			if (!nodeAttr.leaf) {
				loginSessionChk();
				node.toggle();
			}
		}

		function onContextMenu(node, event) {
			var menu = new Ext.menu.Menu();

			var menuIdx = 1;
			if (!node.isLeaf()) {
				menu.add({
					op:menuIdx++,
					text:'<fmt:message key="label.cmsMenu.createMenu"/>',
					handler:function() {
						parent.rightFrame.location = "/admin/menu/create.htm?parentId=" + node.id;
					},
					scope:this
				});
			}
			if (node.id != tree.root.id) {
				menu.add({
					op:menuIdx++,
					text:'<fmt:message key="label.cmsMenu.modifyMenu"/>',
					handler:function() {
						parent.rightFrame.location = "/admin/menu/update.htm?id=" + node.id;
					},
					scope:this
				});

				menu.add({
					op:menuIdx++,
					text:'<fmt:message key="label.cmsMenu.deleteMenu"/>',
					handler:function() {
						deleteById('/admin/menu/delete.json', node.id, function() {
							reloadTree();
						});
					},
					scope:this
				});
			}

			menu.showAt(event.getXY());
		}
	}

	function moveEvent(tree, node, oldParent, newParent, index) {
		index++;
		$.ajax('/admin/menu/move.json', {
			data:{
				id:node.id,
				oldParentMenuId:oldParent.id,
				newParentMenuId:newParent.id,
				index:index
			},
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				// console.log("Success to move node.", data);
			},
			error:function(jqXHR, textStatus, error) {
				jAlert("Fail to move node. ["+ textStatus +"]");
			}
		});
	}

	function reloadTree() {
		// clear tree-div inner HTML
		var treeElem = document.getElementById("tree-div");
		treeElem.innerHTML = "";

		$.getJSON("/admin/menu/getRootMenu.json", function(data) {
			var rootNode = new Ext.tree.AsyncTreeNode({
				text:data.title,
				draggable:false,
				id:data.id,
				terminal:'directory'
			});

			displayTree(rootNode);
		});
	}

	Ext.onReady(function() {
		reloadTree();
	});
</script>
<div id="tree-div" style="width:280px; border:0px solid #c3daf9;" class="menuAdminTree"></div>
<%@ include file="/include/footer.jspf" %>