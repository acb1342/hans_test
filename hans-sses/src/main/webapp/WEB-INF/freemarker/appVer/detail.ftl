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
		
	$('#cancle').click(function(e) {	
		var formData = $("#vForm").serialize();
			$.ajax({
				type	 :	"POST",
				url		 :	"/board/appVer/search.htm",
				data	 :	formData,
				success :	function(response){
					$("#content").html(response);
				},
				error : function(){
					console.log("error!!");
					//err_page();
					return false;
				}
			});
		});
		
		
	});
</script>

<style type="text/css">
    .table>tbody>tr>td01{vertical-align:middle; font-weight:bold; background:#f9f9f9;}
</style>
</head>
<body>

	<div class="x_content">
		<form method="get" id="vForm" name="vForm" onsubmit="return false;">			 
			<input type="hidden" name="page" value="${page?if_exists}"/>
			<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
			
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<tbody>
					<tr>
						<td class="td01"> OS </td>
						<td > ${appVer.os} </td>
						<td class="td01"> Version </td>
						<td> ${appVer.ver} </td>
						<td class="td01"> 필수 여부 </td>
						<td> ${appVer.updateType} </td>
					</tr>
					
					<tr>
						<td class="td01"> 등록일 </td>
						<td colspan="5"> ${appVer.fstRgDt} </td>
					</tr>
					
					<tr>
						<td nowrap> 업데이트 내용 </td>
						<td colspan="5"> <textarea readonly="readonly" style="min-height: 200px">${appVer.content}</textarea> </td>
					</tr>
					
					<tr>
						<td> 배포 예정일시 </td>
						<td colspan="5"> </td>
					</tr>
					
					<tr>
						<td> 바이너리 파일(URL) </td>
						<td colspan="5"> <a href="${appVer.url}" target="_blank">${appVer.url}</a> </td>
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
						<input id="update" type="button" value="수정"/>
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>