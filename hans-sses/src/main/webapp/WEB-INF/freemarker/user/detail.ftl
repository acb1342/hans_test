<script type="text/javascript">
$(function() {

    checkRadio();

	$('#save').click(function(e) {
		page_move('/member/user/update.htm');
	});
	
	$('#cancle').click(function(e) {
		page_move('/member/user/search.htm');
	});
		
	$('#delete').click(function(e) {
		if (confirm("삭제하시겠습니까?")) page_move('/member/user/delete.json');
		else return;
	});
	
});

// 로딩 시 라디오버튼 체크
function checkRadio() {
    var use_yn = $('#use_yn').val();
    if (use_yn == 'Y') $("#radioY").prop("class","iradio_flat-green checked");
    if (use_yn == 'N') $("#radioN").prop("class","iradio_flat-green checked");
}

// 페이지 이동
function page_move(url) {
	var formData = $("#vForm").serialize();
	$.ajax({
		type	 :	"GET",
		url		 :	url,
		data	 :	formData,
		success :	function(response){
			if (response == true) {
				page_move('/member/user/search.htm');
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
</script>

<form id="vForm" name="vForm">
	<input type="hidden" name="page" value="${page?if_exists}"/>
	<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
	<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
	<input type="hidden" name="id" value="${user.id?if_exists}"/>
    <input type="hidden" name="user_seq" value="${user.user_seq?if_exists}"/>

	
	<div class="wrap00" id="wrap00">
		
		<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
			<tbody>
				<tr>

				</tr>
				<tr>
					<td>조직</td><td>${company_name}, ${user.company_seq}</td>
				</tr>
				<tr>
					<td>생년월일</td><td>${user.birthday}</td>
				</tr>
				<tr>
					<td>사용여부</td>
					<td>
                        <input type="text" name="use_yn" id="use_yn" value="${user.use_yn}"/>
						<#if user.use_yn??>
							<#if user.use_yn == 'Y'>사용</#if>
							<#if user.use_yn == 'N'>사용안함</#if>
						</#if>

						<#--<#if user.use_yn == 'N'>
							<div class="iradio_flat-green checked" style="position: relative;" id="radioN">
								<input type="radio" class="flat" id="use_n" name="use_yn" value="N" checked style="position: absolute; opacity: 0;">
							</div>&nbsp;사용안함&nbsp;
						</#if>
						<#if user.use_yn == 'Y'>
							<div class="iradio_flat-green checked" style="position: relative;" id="radioY">
								<input type="radio" class="flat" id="use_y" name="use_yn" value="Y" checked style="position: absolute; opacity: 0;">
							</div>&nbsp;사용&nbsp;
						</#if>-->
					</td>
				</tr>
                <tr>
                    <td>사용자이름</td><td>${user.user_name}</td>
                </tr>
                <tr>
                    <td>위치</td><td>${user.location}</td>
                </tr>
                <tr>
                    <td>RSSI 설정값</td><td>${user.rssi_volume}</td>
                </tr>
                <tr>
                    <td>IP</td><td>${user.user_ip}</td>
                </tr>
                <tr>
                    <td>모드</td><td>${user.user_mode}</td>
                </tr>
			</tbody>
		</table>
		
		<div align="right">
			<button class="btn btn-default" type="button" id="cancle">목록</button>
			<button class="btn btn-dark" type="button" id="save">수정</button>
			<button class="btn btn-danger" type="button" id="delete">삭제</button>
		</div>
		
	</div>
</form>
	
