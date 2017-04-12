<script type="text/javascript">
	$(function() {
		setCompanySelect();
		
		$('#save').click(function(e) {	
			if (!valCheck()) return;
			if(confirm("등록하시겠습니까?")) {
				page_move('/member/userEq/create.htm','POST');
			}
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/member/userEq/search.htm','POST');
			else return;
		});
		
		$('#insertEquip').click(function(e) {
			if(confirm("장비 등록 페이지로 이동하시겠습니까?")) page_move('/member/equipment/create.htm','GET');
		});
		
	});
	
	// 페이지 이동
	function page_move(url, type) {
		var formData = $("#vForm").serialize();
		$.ajax({
			type	 :	type,
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
	
	// 셀렉트 박스 생성
	function setCompanySelect() {
		$('#companySelect').find("option").remove();
		$('#companySelect').append("<option value='' selected=''> === 부서명 === </option>");
		$('#userSelect').find("option").remove();
		$('#userSelect').append("<option value='' selected=''> === 사용자명 === </option>");
		
		if($('#parentCompanySelect').val() == '') return;
		$.ajax({
			type:'POST',
			url:'/member/userEq/setCompanySelect.json',
			data:{
				parentCompanySeq:$('#parentCompanySelect').val()					
			},
			success:function (data) {
					if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#companySelect').append("<option value='" + data[i].companySeq + "'>" + data[i].companyName + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	// 셀렉트 박스 생성
	function setUserSelect() {
		$('#userSelect').find("option").remove();
		$('#userSelect').append("<option value='' selected=''> === 사용자명 === </option>");
		
		if ($('#companySelect').val() == '') return;
		$.ajax({
			type:'POST',
			url:'/member/userEq/setUserSelect.json',
			data:{
				companySeq:$('#companySelect').val()					
			},
			success:function (data) {
					if(data.length > 0) {
						for(var i=0; i<data.length; i++) {
							$('#userSelect').append("<option value='" + data[i].userSeq + "'>" + data[i].userName + "</option>");
						}
					}
				},
			error: function(e) {
				alert("error!!!");
			}
		});
	}
	
	// 체크 값
	function getCheckedIds() {
		var ids = [];
		
		$("input:checkbox:checked").each(function() {
			ids.push($(this).val());
		});
		
		return ids.join(";");
	}
	
	function valCheck() {
		if( !validator.checkAll($("#vForm")) ) return;
		
		$('#ids').val(getCheckedIds());
		if (!$('#ids').val()) {
			alert("장비를 선택해주세요.");
			return;
		}
		
		return true;
	}
</script>

	<form id="vForm" name="vForm" onsubmit="return false;">
	<input type="hidden" name="page" value="${page?if_exists}"/>
	<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
	<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
	<input type="hidden" id="ids" name="ids" value=""/>
	
	<div class="wrap00">
		
		<table class="table table-hover">
			<tbody>
				<tr class="item">
					<td style="width:20%">회사명</td>
					<td colspan="2">
						<select class="form-control col-md-7 col-xs-12" id="parentCompanySelect" name="parentCompanySelect" onchange="setCompanySelect()" required="required">
							<option value="" selected=""> === 회사명 ===</option>
							<#list parentCompanyList as parentCompany>
								<option value="${parentCompany.companySeq}" selected>${parentCompany.companyName}</option>
							</#list>
						</select>
					</td>
				</tr>
				<tr class="item">
					<td>부서명</td>
					<td colspan="2">
						<select class="form-control col-md-7 col-xs-12" id="companySelect" name="companySelect" onchange="setUserSelect()" required="required">
							<option value="" selected=""> === 부서명 ===</option>
						</select>
					</td>
				</tr>
				<tr class="item">
					<td>사용자명</td>
					<td colspan="2">
						<select class="form-control col-md-7 col-xs-12" id="userSelect" name="userSelect" required="required">
							<option value="" selected=""> === 사용자명 ===</option>
						</select>
					</td>
				</tr>
				<tr>
					<td style="width:20%">장비 목록</td>
					<td>
						<#if equipList?has_content>
						<table class="table table-striped responsive-utilities jambo_table dataTable">
							<!-- <thead>
								<tr class="headings" role="row">
									<th></th>
									<th>장비명</th>
									<th>제조사</th>
									<th>제조일</th>
									<th></th>
								</tr>
							</thead> -->
							<tbody>
							<#list equipList as equip>
								<!-- <tr>
									<td style="width:10%">
										<input style="margin-left:10px" type="checkbox" value="${equip.macaddress?if_exists}"/>
									</td>
									<td style="width:20%">${equip.name?if_exists}</td>
									<td style="width:20%">${equip.manufacturer?if_exists}</td>
									<td style="width:20%">${equip.make_date?if_exists}</td>
									<td ></td>
								</tr> -->
								<tr>
									<td style="width:10%">
										<input style="margin-left:10px" type="checkbox" value="${equip.macaddress?if_exists}"/>
									</td>
									<td style="width:40%">${equip.name?if_exists}(${equip.macaddress?if_exists})</td>
									<td style="width:50%"></td>
								</tr>
							</#list>
							</tbody>
						</table>
						<#else>
							<b style="color:#c9302c">할당 가능 장비가 없습니다.</b>
							<button class="btn btn-dark" style="margin-left:3%;" type="button" id="insertEquip">장비등록</button>
						</#if>
					</td>
					<td style="width:30%"></td>
				</tr>
				<tr><td colspan="3"></td></tr>
			</tbody>
		</table>
		
		<div align="right">
			<#if equipList?has_content>
			<button class="btn btn-dark" type="button" id="save">등록</button>
			</#if>
			<button class="btn btn-danger" type="button" id="cancle">취소</button>
		</div>
		
	</div>
	
</form>