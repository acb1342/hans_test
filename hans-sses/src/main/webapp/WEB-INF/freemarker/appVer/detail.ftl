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
		page_move('/board/appVer/update.htm');
	});
	
	$('#cancle').click(function(e) {
		page_move('/board/appVer/search.htm');
	});
	
	$('#delete').click(function(e) {
		if (confirm("삭제하시겠습니까?")) page_move('/board/appVer/delete.json');
		else return;
	});
		
	// 페이지 이동
	function page_move(url) {
		var formData = $("#vForm").serialize();
		$.ajax({
			type	 :	"GET",
			url		 :	url,
			data	 :	formData,
			success :	function(response){
				if (response == true) {
					page_move('/board/appVer/search.htm');
				}
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
		<form method="POST" id="vForm" name="vForm" class="form-horizontal form-label-left">			 
			<input type="hidden" name="page" value="${page?if_exists}"/>
			<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
			<input type="hidden" name="id" value="${appVer.id}"/>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">OS *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<#if appVer.os == '301401'> <#assign strOs="ANDROID"> </#if>
					<#if appVer.os == '301402'> <#assign strOs="IOS"> </#if>
					<#if appVer.os == '301403'> <#assign strOs="PC"> </#if>
					<input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="${strOs?if_exists}">
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">필수 여부 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<#if appVer.updateType == '605101'> <#assign strType="필수"> </#if>
				 	<#if appVer.updateType == '605102'> <#assign strType="선택"> </#if>
					<input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="${strType?if_exists}">
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">Version *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="${appVer.ver?if_exists}">
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">등록일 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="${appVer.regDate?string('yyyy.MM.dd')}">
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">업데이트 내용 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="textarea" class="form-control col-md-7 col-xs-12" name="content" readonly="readonly" value="${appVer.content?if_exists}">
             	</div>
			</div>	
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">배포 예정일시 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" readonly="readonly" class="form-control col-md-7 col-xs-12" value="${appVer.deployYmd?if_exists}">
             	</div>
			</div>
	
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">바이너리 파일(URL) *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<a class="form-control col-md-7 col-xs-12" href="${appVer.url?if_exists}" target="_blank">${appVer.url?if_exists}</a>
             	</div>
			</div>			
				
			<div class="ln_solid"></div>
			<div class="form-group">
				<div class="col-md-6 col-sm-6 col-xs-12 col-md-offset-3">
					<button style="float:right" class="btn btn-danger" type="button" id="delete">삭제</button>
					<button style="float:right" class="btn btn-dark" type="button" id="save">수정</button>
					<button style="float:left" class="btn btn-default" type="button" id="cancle">목록</button>
				</div>
			</div>
			
		</form>
	</div>
</body>
</html>