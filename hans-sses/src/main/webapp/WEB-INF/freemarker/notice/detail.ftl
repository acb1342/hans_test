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
			page_move('/board/notice/update.htm');
		});
		
		$('#cancle').click(function(e) {
			page_move('/board/notice/search.htm');
		});
			
		
		// 페이지 이동
		function page_move(url, id) {
			var formData = $("#vForm").serialize() + "&id=" + id;
			$.ajax({
				type	 :	"GET",
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
			
	});
</script>
</head>
<body>
	<div class="x_content">
		<form method="get" id="vForm" name="vForm" class="form-horizontal form-label-left">
			<input type="hidden" name="page" value="${page?if_exists}"/>
			<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
			<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
			<input type="hidden" name="id" value="${notice.id}"/>

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
					<input type="text" class="form-control col-md-7 col-xs-12" name="title" readonly="readonly" value="${notice.title}">
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">내용 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" class="form-control col-md-7 col-xs-12" name="contents" readonly="readonly" value="${notice.contents}">
             	</div>
			</div>

			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">공개여부 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<div id="gender" class="btn-group" data-toggle="buttons">
						<#if displayYn == 'N'> 
							<label style="width:50%" class="btn btn-default" data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
								<input type="radio" name="displayYn" value="N"> &nbsp;비공개&nbsp;
							</label>
						</#if>
						<#if displayYn == 'Y'>
						<label style="width:50%" class="btn btn-default" data-toggle-class="btn-primary" data-toggle-passive-class="btn-default">
							<input type="radio" name="displayYn" value="Y"> &nbsp;공개&nbsp;
						</label>
						</#if>
					</div>
				</div>
			</div>

			<div class="ln_solid"></div>
			<div class="form-group">
				<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
				<button style="float:right" class="btn btn-dark" type="button" id="save">저장</button>
					<button style="float:right" class="btn btn-danger" type="button" id="cancle">취소</button>
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>