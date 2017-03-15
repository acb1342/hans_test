<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.mobilepark.doit5.admin.model.AdminGroup" %>
<%@ include file="/include/header.jspf" %>
<script type="text/javascript">
	$(function() {
		$("#createMember").button().click(createMember);
	});

	// 수정 페이지로 이동
	function update() {
		window.location.href = "/admin/operator/update.htm?id=${admin.id}";
	}

	// 삭제
	function confirmAndDelete() {
		deleteById('/admin/operator/delete.json', '${admin.id}', function() { 
			search();
		});
	}

	// 검색 페이지로 이동
	function search() {
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		if (callbackUrl != 'null') {
			location.href = "/admin/operator/search.htm?" + callbackUrl;
		} else {
			location.href = "/admin/operator/search.htm";
		}
	}
	
	// 생성 페이지로 이동
	function createMember() {
		document.location = "/admin/operator/member/create.htm?defaultUserId=${admin.id}";
	}
	
	

	function history_0(){
		//$("#content").load("/admin/operator/detail.htm?id="+id);
		
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		
		var url;
		if (callbackUrl != 'null') {
			url = "/admin/operator/search.htm?" + callbackUrl;
		} else {
			url = "/admin/operator/search.htm";
		}
		console.log("url = "+ url);
		
		$.ajax({
			type : "GET",
			url : url,	
			success : function(response){
				$("#content").html(response);
			},
			error : function(){
				console.log("error!!");
				//err_page();
				return false;
			}
		});		
	}
	
	
	
	
</script>

<form  data-parsley-validate class="form-horizontal form-label-left">
<!-- <div class="wrap00" id="wrap00" style="width:100%;"> -->
<div class="wrap00" id="wrap00" style="width:100%;">
	<!-- list _ start -->
	
	<div class="form-group">
		<%-- <label class="control-label col-md-3 col-sm-3 col-xs-12" for="last-name">운영자 ID</label>
		<div class="col-md-6 col-sm-6 col-xs-12">
			<h5>${admin.id}</h5>
		</div> --%>
		
		<label class="control-label col-sm-2 col-xs-12" for="last-name">운영자 ID</label>
		<div class="col-sm-10 col-xs-12">
			<h5>${admin.id}</h5>
		</div>
		
		
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">운영자 이름</label>
		<div class="col-sm-10 col-xs-12">
			<h5>${admin.name}</h5>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">사용자 그룹</label>
		<div class="col-sm-10 col-xs-12">
			<h5>${admin.groupName}</h5>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">연락처</label>
		<div class="col-sm-10 col-xs-12">
			<h5>${admin.tel}</h5>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">휴대전화</label>
		<div class="col-sm-10 col-xs-12">
			<h5>${admin.mobile}</h5>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">이메일</label>
		<div class="col-sm-10 col-xs-12">
			<h5>${admin.email}</h5>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">회사 주소</label>
		<div class="col-sm-10 col-xs-12">
			<h5>${admin.area}</h5>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">등록일</label>
		<div class="col-sm-10 col-xs-12">
			<h5><fmt:formatDate value="${admin.fstRgDt}" pattern="yyyy-MM-dd HH:mm"/></h5>
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-sm-2 col-xs-12"  for="last-name">계정 상태</label>
		<div class="col-sm-10 col-xs-12">
			<h5>
				<c:choose><c:when test="${admin.validYn == 'Y'}">사용중</c:when><c:otherwise>사용중지</c:otherwise></c:choose>
			</h5>
		</div>
	</div>
	
	<div class="ln_solid"></div>
		<div class="form-group">
		<div class="col-sm-5 col-md-offset-1">
			<button type="button" class="btn btn-success" onclick="javascript:history_0()">목록</button></div> 
		<div class="col-sm-2 col-md-offset-4">
			<button type="button" class="btn btn-success" onclick="javascript:page_move('/admin/operator/update.htm','${admin.id}')">수정</button>
			<button type="button" class="btn btn-danger" onclick="javascript:confirmAndDelete()">삭제</button>
		</div> 

		
	
		</div>
		
	<!-- button _ end -->
</div>

</form>
<%@ include file="/include/footer.jspf" %>