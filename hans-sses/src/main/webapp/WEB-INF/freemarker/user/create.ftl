<script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap/dist/js/bootstrap.min.js"></script>
<script src="/css/gentelella-master/vendors/moment/min/moment.min.js"></script>
<script src="/css/gentelella-master/vendors/bootstrap-daterangepicker/daterangepicker.js"></script>
<link rel="stylesheet" href="/css/jstree/themes/default/style.min.css" />
<script type="text/javascript" src="/js/jstree.min.js"></script>
<style type="text/css">
   /* #container {border:1px solid #dcdcdc;padding:8px;}*/
</style>

<script type="text/javascript">
	$(function() {

        var value = $("input[name=use_yn]").val();
        fn_radioClicks(value);

        $("input[name=use_yn]").click(function() {  //click 함수
           var value = $(this).val();
            fn_radioClicks(value);
        });

		// 저장
		$('#save').click(function(e) {

            if ($('#department_seq').val() == '') {
                alert("조직을 선택해 주세요.");
                return;
            }

            if ($("input[name=use_yn]").val() == '') {
                alert("사용여부을 입력해 주세요.");
                return;
            }

            if ($('#user_name').val() == '') {
                alert("사용자 이름을 입력해 주세요.");
                return;
            }

            var birthday_d = $('#birthday').val().replace(/\-/g, "");

			var formData = {
                company_seq : $('#company_seq').val(),
                department_seq : $('#department_seq').val(),
                birthday : birthday_d,
                use_yn : $("input[name=use_yn]").val(),
                user_name : $('#user_name').val(),
				location : $('#location').val(),
                rssi_volume : $('#rssi_volume').val(),
                user_ip : $('#user_ip').val(),
				user_mode : $('#user_mode').val()
			};

			var url = "/member/user/create.json";

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
            showDropdowns: true,
            singleClasses: "picker_2",
            locale : {
                format: "YYYY-MM-DD",
                monthNames: ['1월', '2월', '3월', '4월', '5월', '6월', '7월', '8월', '9월', '10월', '11월', '12월'],
	            daysOfWeek: ['일','월', '화', '수', '목', '금', '토']
            }
        }, function(start, end, label) {
            console.log(start.toISOString(), end.toISOString(), label);
        });

        $('#companySelectBtn').click(function(e) {

            $("#myModal").modal('show').css({
                'margin-top' : function () {

                    var hgt = $(this).height()/2 + "px";
                    return "100px";
                }
            });
            $("#myModalLabel").html("사용자 조직 선택");

            $.getJSON("/member/company/getTree.json", function (data) {
                createJSTrees(data);
            });

            function createJSTrees(jsonData) {
                $("#container").jstree({
                    "core" : {
                        "data" : jsonData,
                        "check_callback":true,
                        "save_selected" : false
                    },
                    "plugins" : ["contextmenu"]
                })
				.on('select_node.jstree', function (e, data) {
										
					if(data.node.parents.length == 3){  // 부서 클릭시에만 값 입력되도록

					var i, j, department_id, company_id, title;
					for(i = 0, j = data.selected.length; i < j; i++) {
						department_id = data.selected[i];
						title = data.instance.get_node(data.selected[i]).text;
					}
					
					company_id = data.node.parent;

                    $('#company_name').val(title);
                    $('#department_seq').val(department_id);
                    $('#company_seq').val(company_id);
                    
                    $("#myModal").modal('hide');

					//if(id!=undefined){
					//    detailNod(id);
					//}
					//$('#event_result').html('Selected: ' + r.join(', '));
					
					}
					
				});
//				.bind("dblclick.jstree", function (event) {
//					console.log("dblclick.jstree");
//					editNod();
//				});



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

    function fn_radioClicks(value)
    {
        if (value == "Y") {
            $("#radioY").prop("class","iradio_flat-green checked");
            $("#radioN").prop("class","iradio_flat-green");
        } else if (value == "N") {
            $("#radioN").prop("class","iradio_flat-green checked");
            $("#radioY").prop("class","iradio_flat-green");
        }
    }
</script>
<form method="post" id="vForm" name="vForm">
<div class="wrap00">
	<table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
		<tbody>
		<tr>
			<td>조직</td>
			<#--<td><input type="text" id="company_seq" name="company_seq"></td>-->
            <td>
                <div class="col-md-5" style="display: inline-block;vertical-align: middle;padding-left: 0px">
                    <input type="text" id="company_name" name="company_name" class="form-control" readonly>
                    <input type="hidden" id="company_seq" name="company_seq" readonly>
                    <input type="hidden" id="department_seq" name="department_seq" readonly>


                </div>
                <div class="input-group-btn" style="display: inline-block;vertical-align: middle;">
                      <button type="button" class="btn btn-dark" id="companySelectBtn" >조직선택</button>
                </div>
			</td>
		</tr>
        <tr>
            <td>생년월일</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="birthday" readonly>

            </td>
        </tr>
        <tr>
            <td>사용여부</td>
            <td>
                <div class="iradio_flat-green" id="radioY">
                    <input type="radio" class="flat" name="use_yn" value="Y" style="position: absolute; opacity: 0;">
                </div>&nbsp;사용&nbsp;
                <div class="iradio_flat-green" id="radioN">
                    <input type="radio" class="flat" name="use_yn" value="N" style="position: absolute; opacity: 0;">
                </div>&nbsp;사용안함&nbsp;

            </td>

        </tr>
        <tr>
            <td>사용자이름</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="user_name" name="user_name" maxlength="64"></td>
        </tr>
        <tr>
            <td>위치</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="location" name="location"></td>
        </tr>
        <tr>
            <td>RSSI 설정값</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="rssi_volume" name="rssi_volume" maxlength="3"></td>
        </tr>
        <tr>
            <td>IP</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="user_ip" name="user_ip"></td>
        </tr>
        <tr>
            <td>모드</td>
            <td><input type="text" class="form-control col-md-7 col-xs-12" id="user_mode" name="user_mode"></td>
        </tr>
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


