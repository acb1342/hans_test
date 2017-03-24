<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title> Notice </title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		
		$('#save').click(function(e) {	
			if(confirm("등록하시겠습니까?")) page_move('/board/notice/create.htm');
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/notice/search.htm');
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
			<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>

			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">작성자 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" name="adminId" readonly="readonly" class="form-control col-md-7 col-xs-12" value="${userId}">
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">작성일 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="${date?string('yyyy.MM.dd')}">
             	</div>
			</div>

			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">제목 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" class="form-control col-md-7 col-xs-12" name="title">
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">내용 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" class="form-control col-md-7 col-xs-12" name="contents">
             	</div>
			</div>

			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">공개여부 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<div class="btn-group" data-toggle="buttons">
						<label style="width:50%" class="btn btn-default" data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
							<input type="radio" name="displayYn" value="N"> &nbsp;비공개&nbsp;
						</label>
						<label style="width:50%" class="btn btn-default" data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
							<input type="radio" name="displayYn" value="Y"> &nbsp;공개&nbsp;
						</label>
					</div>
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