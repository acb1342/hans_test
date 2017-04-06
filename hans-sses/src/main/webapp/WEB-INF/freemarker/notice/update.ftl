<script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap-wysiwyg/js/bootstrap-wysiwyg.min.js"></script>
<script src="/css/gentelella-master/vendors/google-code-prettify/src/prettify.js"></script>
<script src="/css/gentelella-master/vendors/jquery.hotkeys/jquery.hotkeys.js"></script>
<script type="text/javascript">
	$(function() {
		checkRadio();
		
		// radio toggle
		$('#radioY').click(function() {
			$("#radioY").prop("class","iradio_flat-green checked");
			$("#radioN").prop("class","iradio_flat-green");
			$("input:radio[id='displayY']").prop("checked", true);
			$("input:radio[id='displayN']").prop("checked", false);
		});
		
		$('#radioN').click(function() {
			$("#radioN").prop("class","iradio_flat-green checked");
			$("#radioY").prop("class","iradio_flat-green");
			$("input:radio[id='displayN']").prop("checked", true);
			$("input:radio[id='displayY']").prop("checked", false);
		});
		
		// editor
		$('#textEditor').wysiwyg();
		
		$(".dropdown-menu").click(function (e) {
    		e.stopPropagation();
		});
		
		$('#save').click(function(e) {
			if( !validator.checkAll($('#vForm')) ) return;
			if(confirm("수정하시겠습니까?")) {
				var contents = $('#textEditor').html();
				$('#contents').val(contents);
				page_move('/board/notice/update.htm');
			}
			else return;
		});
		
		$('#cancle').click(function(e) {	
			if(confirm("취소하시겟습니까?")) page_move('/board/notice/detail.htm');
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
	
	// 로딩 시 라디오버튼 체크
	function checkRadio() {
		var displayYn = $('#displayYn').val();
		if (displayYn == 'Y') $("#radioY").prop("class","iradio_flat-green checked");
		if (displayYn == 'N') $("#radioN").prop("class","iradio_flat-green checked");
	}
</script>

	<form id="vForm" name="vForm">
		<input type="hidden" name="page" value="${page?if_exists}"/>
		<input type="hidden" name="searchType" value="${searchType?if_exists}"/>
		<input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
		<input type="hidden" name="id" value="${notice.id?if_exists}"/>
		<input type="hidden" id="displayYn" value="${notice.displayYn?if_exists}"/>
		<input type="hidden" id="contents" name="contents" value=""/>
		
		<div class="wrap00">
			<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
				<tbody>
					<tr>
						<td style="width:20%">작성자</td>
						<td><input class="form-control col-md-7 col-xs-12" type="text" name="adminId" readonly="readonly" value="${userId}"></td>
					</tr>
					<tr>
						<td>작성일</td>
						<td><input class="form-control col-md-7 col-xs-12" type="text" readonly="readonly" value="${date?string('yyyy.MM.dd')}"></td>
					</tr>
					<tr class="item">
						<td>제목</td>
						<td><input class="form-control col-md-7 col-xs-12" type="text" id="vTitle" name="title" required="required" value="${notice.title?if_exists}"></td>
					</tr>
					<tr>
						<td>내용</td>
						<td>
							<div class="x_content" style="padding:0;">
	                  		<div class="btn-toolbar editor" data-role="editor-toolbar" data-target="#textEditor">
	                    			<div class="btn-group">
	                      			<a class="btn dropdown-toggle" data-toggle="dropdown" title="Font" aria-expanded="false"><i class="fa fa-font"></i><b class="caret"></b></a>
	                      			<ul class="dropdown-menu">
	                      			</ul>
	                    			</div>
	
									<div class="btn-group">
										<a class="btn dropdown-toggle" data-toggle="dropdown" title="Font Size" aria-expanded="false"><i class="fa fa-text-height"></i>&nbsp;<b class="caret"></b></a>
										<ul class="dropdown-menu">
											<li>
												<a data-edit="fontSize 5" class="fs-Five">
													<p style="font-size:17px">Huge</p>
											  	</a>
											</li>
											<li>
												<a data-edit="fontSize 3" class="">
											    	<p style="font-size:14px">Normal</p>
											  	</a>
											</li>
											<li>
										  		<a data-edit="fontSize 1">
										    		<p style="font-size:11px">Small</p>
										    	</a>
										  </li>
										</ul>
									</div>
			
									<div class="btn-group">
									  <a class="btn" data-edit="bold" title="Bold (Ctrl/Cmd+B)"><i class="fa fa-bold"></i></a>
									  <a class="btn" data-edit="italic" title="Italic (Ctrl/Cmd+I)"><i class="fa fa-italic"></i></a>
									  <a class="btn" data-edit="strikethrough" title="Strikethrough"><i class="fa fa-strikethrough"></i></a>
									  <a class="btn" data-edit="underline" title="Underline (Ctrl/Cmd+U)"><i class="fa fa-underline"></i></a>
									</div>
									
									<div class="btn-group">
									  <a class="btn" data-edit="insertunorderedlist" title="Bullet list"><i class="fa fa-list-ul"></i></a>
									  <a class="btn" data-edit="insertorderedlist" title="Number list"><i class="fa fa-list-ol"></i></a>
									  <a class="btn" data-edit="outdent" title="Reduce indent (Shift+Tab)"><i class="fa fa-dedent"></i></a>
									  <a class="btn" data-edit="indent" title="Indent (Tab)"><i class="fa fa-indent"></i></a>
									</div>
				
									<div class="btn-group">
									  <a class="btn" data-edit="justifyleft" title="Align Left (Ctrl/Cmd+L)"><i class="fa fa-align-left"></i></a>
									  <a class="btn" data-edit="justifycenter" title="Center (Ctrl/Cmd+E)"><i class="fa fa-align-center"></i></a>
									  <a class="btn" data-edit="justifyright" title="Align Right (Ctrl/Cmd+R)"><i class="fa fa-align-right"></i></a>
									  <a class="btn" data-edit="justifyfull" title="Justify (Ctrl/Cmd+J)"><i class="fa fa-align-justify"></i></a>
									</div>
									
									<div class="btn-group">
									  <a class="btn dropdown-toggle" data-toggle="dropdown" title="Hyperlink"><i class="fa fa-link"></i></a>
									  <div class="dropdown-menu input-append">
									    <input class="span2" placeholder="URL" type="text" data-edit="createLink">
									    <button class="btn" type="button">Add</button>
									  </div>
									  <a class="btn" data-edit="unlink" title="Remove Hyperlink"><i class="fa fa-cut"></i></a>
									</div>
									
									<div class="btn-group" >
									  <a class="btn" title="Insert picture (or just drag &amp; drop)" id="pictureBtn"><i class="fa fa-picture-o"></i></a>
									  <input type="file" data-role="magic-overlay" data-target="#pictureBtn" data-edit="insertImage">
									</div>
									
									<div class="btn-group">
									  <a class="btn" data-edit="undo" title="Undo (Ctrl/Cmd+Z)"><i class="fa fa-undo"></i></a>
									  <a class="btn" data-edit="redo" title="Redo (Ctrl/Cmd+Y)"><i class="fa fa-repeat"></i></a>
									</div>
								</div>
	                  		<div id="textEditor" class="editor-wrapper placeholderText" contenteditable="true">${notice.contents?if_exists}</div>
                		</div>
						</td>
					</tr>
					<tr>
						<td>공개여부</td>
						<td>
							<div class="iradio_flat-green" style="position: relative;" id="radioN">
								<input type="radio" class="flat" id="displayN" name="displayYn" value="N" style="position: absolute; opacity: 0;">
							</div>&nbsp;비공개&nbsp;
							<div class="iradio_flat-green" style="position: relative;" id="radioY">
								<input type="radio" class="flat" id="displayY" name="displayYn" value="Y" style="position: absolute; opacity: 0;">
							</div>&nbsp;공개&nbsp;
						</td>
					</tr>
				</tbody>
			</table>

			<div align="right">
				<button class="btn btn-dark" type="button" id="save">저장</button>
				<button class="btn btn-danger" type="button" id="cancle">취소</button>
			</div>
			
		</div>
	</form>
	