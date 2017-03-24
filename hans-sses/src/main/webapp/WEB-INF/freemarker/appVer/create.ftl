<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title> AppVer </title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		
		$('#save').click(function(e) {	
			if(confirm("등록하시겠습니까?")) page_move('/board/appVer/create.htm');
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/appVer/search.htm');
			else return;
		});
		
	});
	
	// 페이지 이동
	function page_move(url) {
		var formData = $("#vForm").serialize();
		$.ajax({
			type	 :	"POST",
			url		 :	url,
			data	 :	formData,
			success :	function(response){
				$("#content").html(response);
				window.scrollTo(0,0);
			},
			error : function(){
				console.log("error!!");
				//err_page();
				return false;
			}
		});
	}
</script>
</head>
<body>
	<div class="x_content">
		<form method="POST" id="vForm" name="vForm" class="form-horizontal form-label-left">
			<input type="hidden" name="page" value="${page?if_exists}"/>
			<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">OS *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<select class="form-control col-md-7 col-xs-12" name="os">
						<option value="301401">ANDROID</option>
						<option value="301402">IOS</option>
						<option value="301403">PC</option>
					</select>
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">필수여부 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<select class="form-control col-md-7 col-xs-12" name="updateType">
						<option value="605101">필수</option>
						<option value="605102">선택</option>
					</select>
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">Version *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" class="form-control col-md-7 col-xs-12" name="ver">
             	</div>
			</div>			
						
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">업데이트 내용 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<textarea class="form-control col-md-7 col-xs-12" name="content"></textarea>
             	</div>
			</div>		
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">배포 예정일시 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<span>
						<input style="width:40%;" type="text" class="form-control col-md-7 col-xs-12" name="selDate">
						<select style="width:30%;" class="form-control col-md-7 col-xs-12" name="hour">
							<#list 0..23 as hour>
								<option value="${hour}">${hour}시</option>
							</#list>
						</select>
						<select style="width:30%;" class="form-control col-md-7 col-xs-12" name="minute">
							<#list 0..50 as minute>
								<option value="${minute}">${minute}분</option>
							</#list>
						</select>
					</span>
             	</div>
			</div>		
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">바이너리 파일(URL) *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" class="form-control col-md-7 col-xs-12" name="url">
             	</div>
			</div>			
				
			<div class="ln_solid"></div>
			<div class="form-group">
				<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
					<button style="float:right" class="btn btn-danger" type="button" id="cancle">취소</button>
					<button style="float:right" class="btn btn-dark" type="button" id="save">저장</button>
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>