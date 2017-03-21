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
		<form method="get" id="vForm" name="vForm" onsubmit="return false;">			 
			<input type="hidden" name="page" value="${page?if_exists}"/>
			<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
			<input type="hidden" name="id" value="${appVer.id}"/>
			
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<tbody>
					<tr>
						<td> OS </td>
						<td>
							<#if appVer.os == '301401'> ANDROID </#if>
							<#if appVer.os == '301402'> IOS </#if>
							<#if appVer.os == '301403'> PC </#if>
						</td>
						<td> Version </td>
						<td> ${appVer.ver} </td>
						<td> 필수 여부 </td>
						<td>
							<#if appVer.updateType == '605101'> 필수 </#if>
						 	<#if appVer.updateType == '605102'> 선택 </#if>
						 </td>
					</tr>
					
					<tr>
						<td> 등록일 </td>
						<td colspan="5"> ${appVer.fstRgDt} </td>
					</tr>
					
					<tr>
						<td style="width:20%" nowrap> 업데이트 내용 </td>
						<td style="width:80%"colspan="5"> <textarea readonly="readonly" style="min-height: 200px">${appVer.content?if_exists}</textarea> </td>
					</tr>
					
					<tr>
						<td> 배포 예정일시 </td>
						<td colspan="5"> </td>
					</tr>
					
					<tr>
						<td> 바이너리 파일(URL) </td>
						<td colspan="5"> <a href="${appVer.url?if_exists}" target="_blank">${appVer.url?if_exists}</a> </td>
					</tr>
				</tbody>
			</table>
			
		<div class="footer">
			<table style="width:100%">
				<tr>
					<td style="width:50%">
						<input id="cancle" type="button" value="목록"/>
					</td>
					<td style="width:50%" align="right">
						<input id="save" type="button" value="수정"/>
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>