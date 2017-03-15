<script type="text/javascript">
	$(function() {
	});

	// 수정 페이지로 이동
	function update() {
		window.location.href = "/admin/operator/update.htm?id=${admin.id}";
	}

	// 삭제
	/* function confirmAndDelete() {
		deleteById('/admin/operator/delete.json', '${admin.id}', function() { 
			search();
		});
	} */
	
	function confirmAndDelete(id) {
		$.ajax({
			url:"/admin/operator/delete.json",
			type:"POST",
			data:{
				id:id
			},
			success:function(isDelete) {
				if (isDelete) {
					console.log("delete success")
					alert("삭제 성공")
					history_0();
				} else {
					console.log("delete fail")
					alert("삭제 실패");
				}
			}
		});
	}
	
	function deleteById(url, id, callback) {
		jConfirm('<fmt:message key="statement.confirm.delete"/>', 'Confirm', function(r) {
			if (r) {
				$.ajax({
					url:url,
					type:"POST",
					data:{
						id:id
					},
					dataType:'json',
					success:function(isDelete) {
						if (isDelete) {
							jAlert('<fmt:message key="statement.delete.success"/>', 'Alert', function() {
								callback();
							});
						} else {
							jAlert('<fmt:message key="statement.delete.fail"/>');
						}
					}
				});
			}
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
<div class="wrap00" id="wrap00" style="width:100%;">
	<!-- list _ start -->
	
	<div class="form-group">
		
		<label class="control-label col-md-2 col-sm-2 col-xs-12" for="last-name">ID</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.id}" readonly="readonly">
		</div>
				
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">이름</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.name}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">사용자 그룹</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.groupName}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">연락처</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.tel}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">휴대전화</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.mobile}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">이메일</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.email}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">회사 주소</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.area}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">등록일</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.fstRgDt?string("yyyy-MM-dd HH:mm")}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">계정 상태</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="<#if admin.validYn == 'Y'>사용중<#else>사용중지</#if>" readonly="readonly">
		</div>
	</div>
	
	<div class="ln_solid"></div>
		<div class="form-group">
		
		<div class="col-md-2" align="right">
			<button type="button" class="btn btn-success" onclick="javascript:history_0()">목록</button></div> 
		<div class="col-md-10" align="right">
			<button type="button" class="btn btn-success" onclick="javascript:page_move('/admin/operator/update.htm','${admin.id}')">수정</button>
			<button type="button" class="btn btn-danger" onclick="javascript:confirmAndDelete('${admin.id}')">삭제</button>
		</div> 

		
	
		</div>
		
	<!-- button _ end -->
</div>

</form>