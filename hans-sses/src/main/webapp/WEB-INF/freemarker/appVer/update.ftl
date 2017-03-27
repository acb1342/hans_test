<!DOCTYPE html>
<html lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>SSES</title>

<script type="text/javascript" src="/js/jquery/jquery-1.7.2.js"></script>
<script type="text/javascript" src="/js/jquery/alert/jquery.alerts.custom.js"></script>
<script type="text/javascript" src="/js/common.js"></script>
<script type="text/javascript">
	$(function() {
		
		$('#save').click(function(e) {	
			if(confirm("수정하시겠습니까?")) page_move('/board/appVer/update.htm');
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/appVer/detail.htm');
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
			<input type="hidden" name="id" value="${appVer.id}"/>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">OS *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<select class="form-control col-md-7 col-xs-12" name="os">
						<option value="301401" <#if appVer.os == '301401'> selected=""</#if>>ANDROID</option>
						<option value="301402" <#if appVer.os == '301402'> selected=""</#if>>IOS</option>
						<option value="301403" <#if appVer.os == '301403'> selected=""</#if>>PC</option>
					</select>
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">필수여부 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<select class="form-control col-md-7 col-xs-12" name="updateType">
						<option value="605101" <#if appVer.updateType == '605101'> selected=""</#if>>필수</option>
						<option value="605102" <#if appVer.updateType == '605102'> selected=""</#if>>선택</option>
					</select>
             	</div>
			</div>
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">Version *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" class="form-control col-md-7 col-xs-12" name="ver" value="${appVer.ver?if_exists}">
             	</div>
			</div>			
						
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">업데이트 내용 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<textarea class="form-control col-md-7 col-xs-12" name="content">${appVer.content?if_exists}</textarea>
             	</div>
			</div>		
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">배포 예정일시 *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<span>
						<input style="width:40%;" type="text" class="form-control col-md-7 col-xs-12" name="selDate">
						<select style="width:30%;" class="form-control col-md-7 col-xs-12" name="hour">
							<#list 0..23 as hour>
								<option value="${hour}" <#if appVer.beforeHour??><#if appVer.beforeHour == hour>selected</#if></#if>>${hour}시</option>
							</#list>
						</select>
						<select style="width:30%;" class="form-control col-md-7 col-xs-12" name="minute">
							<#list 0..50 as minute>
								<option value="${minute}" <#if appVer.beforeMinute??><#if appVer.beforeMinute == minute>selected</#if></#if>>${minute}분</option>
							</#list>
						</select>
					</span>
             	</div>
			</div>		
			
			<div class="form-group">
				<label class="control-label col-md-3 col-sm-3 col-xs-12">바이너리 파일(URL) *</label>
				<div class="col-md-6 col-sm-6 col-xs-12">
					<input type="text" class="form-control col-md-7 col-xs-12" name="url" value="${appVer.url?if_exists}">
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