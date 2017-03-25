<script type="text/javascript">
	$(function() {
	});

	// 수정 페이지로 이동
	function update() {
		window.location.href = "/admin/code/update.htm?id=${admin.code}";
	}

	
	function confirmAndDelete(id) {
		$.ajax({
			url:"/admin/code/delete.json",
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
			location.href = "/admin/code/search.htm?" + callbackUrl;
		} else {
			location.href = "/admin/code/search.htm";
		}
	}

	function history_0(){
		//$("#content").load("/admin/operator/detail.htm?id="+id);
		
		var callbackUrl = '<%=request.getParameter("callbackUrl")%>';
		
		var url;
		if (callbackUrl != 'null') {
			url = "/admin/code/search.htm?" + callbackUrl;
		} else {
			url = "/admin/code/search.htm";
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
		
		<label class="control-label col-md-2 col-sm-2 col-xs-12" for="last-name">코드</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.code}" readonly="readonly">
		</div>
				
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">부모코드</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.parentsCode}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">코드명</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.codeName}" readonly="readonly">
		</div>
	</div>
	
	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">코드설명</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.codeDesc}" readonly="readonly">
		</div>
	</div>

	<div class="form-group">
		<label class="control-label col-md-2 col-sm-2 col-xs-12"  for="last-name">등록일</label>
		<div class="col-md-10 col-sm-6 col-xs-12">
			<input type="text" id="id" name="id" class="form-control" value="${admin.regDate?string("yyyy-MM-dd HH:mm")}" readonly="readonly">
		</div>
	</div>
	
	<div class="ln_solid"></div>
		<div class="form-group">
		
		<div class="col-md-2" align="right">
			<button type="button" class="btn btn-success" onclick="javascript:history_0()">목록</button></div> 
		<div class="col-md-10" align="right">
			<button type="button" class="btn btn-success" onclick="javascript:page_move('/admin/code/update.htm','${admin.code}')">수정</button>
			<button type="button" class="btn btn-danger" onclick="javascript:confirmAndDelete('${admin.code}')">삭제</button>
		</div> 

		
	
		</div>
		
	<!-- button _ end -->
</div>

</form>