<script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<link rel="stylesheet" href="/css/jstree/themes/default/style.min.css" />
<script type="text/javascript" src="/js/jstree.min.js"></script>

<style type="text/css">
    #container {border:1px solid #dcdcdc;padding:8px;}
    .buttonMenu {margin-top: 10px;margin-left: 15px;}
</style>

<script type="text/javascript">
	$(function() {

        checkRadio();

        $('#radioY').click(function() {
            $("#radioY").prop("class","iradio_flat-green checked");
            $("#radioN").prop("class","iradio_flat-green");
            $("input:radio[id='use_y']").prop("checked", true);
            $("input:radio[id='use_n']").prop("checked", false);
        });

        $('#radioN').click(function() {
            $("#radioN").prop("class","iradio_flat-green checked");
            $("#radioY").prop("class","iradio_flat-green");
            $("input:radio[id='use_n']").prop("checked", true);
            $("input:radio[id='use_y']").prop("checked", false);
        });

		// 저장
		$('#save').click(function(e) {
            if ($('#company_seq').val() == '') {
                alert("조직을 선택해 주세요.");
                return;
            }

            if ($('#user_name').val() == '') {
                alert("사용자 이름을 입력해 주세요.");
                return;
            }

            var birthday_d = $('#birthday').val().replace(/\-/g, "");

			var formData = {
                company_seq : $('#company_seq').val(),
                birthday : birthday_d,
                use_yn : $('#use_yn').val(),
                user_name : $('#user_name').val(),
				location : $('#location').val(),
                rssi_volume : $('#rssi_volume').val(),
                user_ip : $('#user_ip').val(),
				user_mode : $('#user_mode').val()
			};

			var url = "/member/user/update.json";

			$.ajax({
				type		: "POST",
				url			: url,
				contentType	: "application/json",
				data		: JSON.stringify(formData),
				success : function(response){
					$("#content").html(response);
				},
				error : function(){
					console.log("error!!");
					return false;
				}
			});
		});

		// 이전 페이지로 이동
		$('#cancel').click(function(e) {			
			var formData = $("#vForm").serialize();
			if(confirm("취소 하시겠습니까?")) {
				$.ajax({
					type	 :	"POST",
					url		 :	"/member/user/search.htm",
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

        $('#birthday').daterangepicker({
            singleDatePicker: true,
            singleClasses: "picker_3",
            locale : {
                direction: "kr",
                format: "YYYY-MM-DD"
            }
        }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });


        $('#companySelectBtn').click(function(e) {

            $("#myModal").modal();
            $("#myModalLabel").html("사용자 조직 선택");

            $.getJSON("/member/company/getTree.json", function (data) {
                createJSTrees(data);
            });

            function createJSTrees(jsonData) {
                $("#container").jstree({
                    "core" : {
                        "data" : jsonData,
                        "check_callback":true
                    },
                    "plugins" : ["dnd","state","contextmenu"]
                })
				.on('changed.jstree', function (e, data) {
					node_data = data;
					var i, j, id, title;
					for(i = 0, j = data.selected.length; i < j; i++) {
						id = data.selected[i];
						//r.push(data.instance.get_node(data.selected[i]).id);
						title = data.instance.get_node(data.selected[i]).text;
					}
					console.log(id + "||" + title);
                    $('#company_name').val(title);
                    $('#company_seq').val(id);
					//if(id!=undefined){
					//    detailNod(id);
					//}
					//$('#event_result').html('Selected: ' + r.join(', '));
				})
				.bind("dblclick.jstree", function (event) {
					console.log("dblclick.jstree");
					editNod();
				});
            }

        });

	});//function

    function editNod(){

        var inst = $.jstree.reference("#container");
        var node = $("#container").jstree("get_selected");

        var old = node.text; // trim right spaces

        inst.edit(node, null, function(node, success, cancelled) {
            if (!success || cancelled) return;
            if (node.text.replace(/\s+$/, '')==old) return;
            // all good, your rename code here
        });
    }

    // 로딩 시 라디오버튼 체크
    function checkRadio() {
        var use_yn = $('#use_yn').val();
        if (use_yn == 'Y') $("#radioY").prop("class","iradio_flat-green checked");
        if (use_yn == 'N') $("#radioN").prop("class","iradio_flat-green checked");
    }


</script>
<form method="post" id="vForm" name="vForm">

    <input type="hidden" name="page" value="${page?if_exists}"/>
    <input type="hidden" name="searchType" value="${searchType?if_exists}"/>
    <input type="hidden" name="searchValue" value="${searchValue?if_exists}"/>
    <input type="hidden" name="id" value="${user.id?if_exists}"/>
    <input type="hidden" name="user_seq" value="${user.user_seq?if_exists}"/>
    <input type="hidden" name="use_yn" id="use_yn" value="${user.use_yn?if_exists}"/>

<div class="wrap00">
	<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
		<tbody>
		<tr>
			<td>조직</td>
			<#--<td><input type="text" id="company_seq" name="company_seq"></td>-->
            <td><input type="text" id="company_name" name="company_name">
				<input type="text" id="company_seq" name="company_seq">
                <input type="button" id="companySelectBtn" value='조직선택'/>
			</td>
		</tr>
        <tr>
            <td>생년월일</td>
            <td><input type="text" id="birthday" readonly></td>
        </tr>
        <tr>
            <td>사용여부</td>
            <td>
                <div class="iradio_flat-green" style="position: relative;" id="radioN">
                    <input type="radio" class="flat" id="use_n" name="use_yn" value="N" style="position: absolute; opacity: 0;">
                </div>&nbsp;사용안함&nbsp;
                <div class="iradio_flat-green" style="position: relative;" id="radioY">
                    <input type="radio" class="flat" id="use_y" name="use_yn" value="Y" style="position: absolute; opacity: 0;">
                </div>&nbsp;사용&nbsp;
            </td>

        </tr>
        <tr>
            <td>사용자이름</td>
            <td><input type="text" id="user_name" name="user_name" value="${user.user_name}"></td>
        </tr>
        <tr>
            <td>위치</td>
            <td><input type="text" id="location" name="location" value="${user.location}"></td>
        </tr>
        <tr>
            <td>RSSI 설정값</td>
            <td><input type="text" id="rssi_volume" name="rssi_volume" value="${user.rssi_volume}"></td>
        </tr>
        <tr>
            <td>IP</td>
            <td><input type="text" id="user_ip" name="user_ip" value="${user.user_ip}"></td>
        </tr>
        <tr>
            <td>모드</td>
            <td><input type="text" id="user_mode" name="user_mode" value="${user.user_mode}"></td>
        </tr>
       <#-- <tr>
            <td>작성일</td>
            <td><input class="form-control col-md-7 col-xs-12" type="text" readonly="readonly" value="${user.reg_date?string('yyyy.MM.dd')}"></td>
        </tr>
        <tr>
            <td>수정일</td>
            <td><input class="form-control col-md-7 col-xs-12" type="text" readonly="readonly" value="${user.mod_date?string('yyyy.MM.dd')}"></td>
        </tr>-->
		</tbody>
	</table>

	<div class="col-md-12 col-sm-6 col-xs-12" align="right">
		<button type="button" class="btn btn-dark" id="save">추가</button>
		<button type="button" class="btn btn-default" id="cancel">취소</button>
	</div>
</div>
</form>


<div class="modal fade bs-example-modal-md in" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;" id="myModal">
    <div class="modal-dialog modal-md">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">Modal title</h4>
            </div>
            <div class="modal-body" id="container">

			</div>

            <div class="modal-footer">
			<#--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
                    <#--<button type="button" class="btn btn-primary">Save changes</button>-->
            </div>

        </div>
    </div>
</div>


