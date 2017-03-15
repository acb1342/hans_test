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
			var formData = $("#vForm").serialize();
			if(confirm("수정하시겠습니까?")) {
				$.ajax({
					type	 :	"POST",
					url		 :	"/board/appVer/update.htm",
					data	 :	formData,
					success :	function(response){
						alert("수정되었습니다.");
						$("#content").html(response);
					},
					error : function(){
						console.log("error!!");
						//err_page();
						return false;
					}
				});
			}
		});
		
		$('#cancle').click(function(e) {	
			var formData = $("#vForm").serialize();
			if(confirm("취소하시겟습니까?")) {
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
			}
		});
		
		
	});
</script>

<style type="text/css">
	.table>tbody>tr>td01{vertical-align:middle; font-weight:bold; background:#f9f9f9;}
</style>
</head>
<body>

	<div class="x_content">
		<form id="vForm" name="vForm">
			<input type="hidden" name="page" value="${page?if_exists}"/>
			<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
			<input type="hidden" name="id" value="${appVer.id}"/>
			<input type="hidden" name="targetType" value="${appVer.targetType}"/>
			
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<tbody>
					<tr>
						<td class="td01"> OS </td>
						<td>
							<select name="os">
								<option value="301401" <#if appVer.os == '301401'> selected=""</#if>>ANDROID</option>
								<option value="301402" <#if appVer.os == '301402'> selected=""</#if>>IOS</option>
								<option value="301403" <#if appVer.os == '301403'> selected=""</#if>>PC</option>
							</select>
						</td>
						<td class="td01"> Version </td>
						<td> <input type="text" name="ver" value="${appVer.ver}"/></td>
						<td class="td01"> 필수 여부 </td>
						<td>
							<select name="updateType">
								<option value="605101" <#if appVer.updateType == '605101'> selected=""</#if>>필수</option>
								<option value="605102" <#if appVer.updateType == '605102'> selected=""</#if>>선택</option>
							</select>
						</td>
					</tr>
					
					<tr>
						<td> 업데이트 내용 </td>
						<td colspan="5"> <textarea name="content" style="min-height: 200px">${appVer.content?if_exists}</textarea> </td>
					</tr>
					
					<tr>
						<td> 배포 예정일시 </td>
						<td colspan="5">
							<input type="text" name="selDate"/>
							<span>
								<select name="hour">
									<#list 0..23 as hour>
										<option value="${hour}" <#if appVer.beforeHour??><#if appVer.beforeHour == hour>selected</#if></#if>>${hour}시</option>
									</#list>
								</select>
							</span>
							<span>
								<select name="minute">
									<#list 0..50 as minute>
										<option value="${minute}" <#if appVer.beforeMinute??><#if appVer.beforeMinute == minute>selected</#if></#if>>${minute}분</option>
									</#list>
								</select>
							</span>
						</td>
					</tr>
					
					<tr>
						<td> 바이너리 파일(URL) </td>
						<td colspan="5"> <input name="url" type="text" value="${appVer.url}"/> </td>
					</tr>
				</tbody>
			</table>
			
		<div class="footer">
			<table style="width:100%">
				<tr>
					<td align="right">
						<input class="btn btn-default" type="button" id="save" value='저장'/>
						<input class="btn btn-default" type="button" id="cancle" value='취소'/>
					</td>
				</tr>
			</table>
		</div>
			
		</form>
	</div>
</body>
</html>