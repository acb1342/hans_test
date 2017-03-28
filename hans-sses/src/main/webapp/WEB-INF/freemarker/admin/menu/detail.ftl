
<script type="text/javascript">

    // 기능 생성 페이지로 이동
	function createCmsMenuFunction() {
		var menuid = ${cmsMenu.id};

        $.ajax({
            url : "/admin/menu/func/create.htm",
            data : {menuId:menuid},
            success:function(data) {
                console.log("Success to detail node.", data);
                $(".modal-body").html(data);
                $("#myModalLabel").html("메뉴 기능 생성");

            },
            error:function() {
                console.log("error to detail node.");

            }
        });

		//document.location = "/admin/menu/function/create.htm?menuId=${cmsMenu.id}";
	}

	// 메뉴 업데이트 페이지로 이동
	function update() {
        var menuid = ${cmsMenu.id};

        $.ajax({
            url : "/admin/menu/update.htm",
            data : {id:menuid},
            success:function(data) {
                console.log("Success to detail node.", data);
                $(".rightMenu").html(data);
            },
            error:function() {
                console.log("error to detail node.");

            }
        });

		//location.href = "/admin/menu/update.htm?id=${cmsMenu.id}";
	}

	function goFuncMenu(type, id){
	    var url = "";
	    var menuTitle = "";
	    if(type=="detail"){
	        url = "/admin/menu/func/detail.htm";
	        menuTitle = "메뉴 기능 상세";
        }else{
	        url ="/admin/menu/func/update.htm";
	        menuTitle = "메뉴 기능 수정";
        }

        $.ajax({
            url : url,
            data : {id:id},
            success:function(data) {
                console.log("Success to detail node.", data);
                $(".modal-body").html(data);

                $("#myModalLabel").html(menuTitle);
            },
            error:function() {
                console.log("error to detail node.");

            }
        });
    }

</script>

<div class="x_content">
    <h4>메뉴 설명</h4>
    <form method="get" id="vForm" name="vForm" onsubmit="return false;">
        <table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
            <tbody>
            <tr>
                <td style="width:20%"> ID </td>
                <td>
				${cmsMenu.id}
                </td>
            </tr>
            <tr>
                <td> NAME </td>
                <td>
				<#if cmsMenu.title?exists>${cmsMenu.title}</#if>
                </td>
            </tr>
            <tr>
                <td> TYPE </td>
                <td>
				<#if cmsMenu.type?exists>${cmsMenu.type}</#if>
                </td>
            </tr>
            <tr>
                <td> URL </td>
                <td>
				<#if cmsMenu.url?exists>${cmsMenu.url}</#if>
                </td>
            </tr>
            <tr>
                <td> DESCRIPTION </td>
                <td>
				<#if cmsMenu.description?exists>${cmsMenu.description}</#if>
                </td>
            </tr>

            <tr>
                <td> 등록일 </td>
                <td> ${cmsMenu.fstRgDt} </td>
            </tr>

            <tr>
                <td> 수정일 </td>
                <td><#if cmsMenu.lstChDt?exists>${cmsMenu.lstChDt}</#if></td>
            </tr>

            </tbody>
        </table>

        <table style="width:100%">
            <tr>
                <td align="right">
                    <input class="btn btn-default" type="button" value='추가' onclick="createCmsMenuFunction()" data-toggle="modal" data-target=".bs-example-modal-md"/>
                    <input class="btn btn-default" type="button" value='수정' onclick="javascript:update()"/>
                </td>
            </tr>
        </table>

        <div class="footer">
		<#if cmsMenu.functions?exists && cmsMenu.functions?size &gt; 0>
            <h4>메뉴 기능 리스트</h4>
            <table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
                <thead>
                <tr class="headings" role="row">
                    <th>NAME</th>
                    <th>URL</th>
                    <th>AUTH</th>

                    <th>&nbsp;</th>
                </tr>
                </thead>
                <tbody role="alert" aria-live="polite" aria-relevant="all">
					<#list cmsMenu.functions as menuList>
                    <tr class="even pointer" style="height:1px;">

                        <td style="width:5%; text-align:center;">
						${menuList.name}
                        </td>
                        <td style="width:15%;">${menuList.url}</td>
                        <td style="width:10%;">${menuList.auth}</td>

                        <td style="width:15%;">
                            <input type="button" class="btn btn-default" value='상세' onclick="javascript:goFuncMenu('detail','${menuList.id}');" data-toggle="modal" data-target=".bs-example-modal-md" />
                            <input type="button" class="btn btn-default" value='수정' onclick="javascript:goFuncMenu('update','${menuList.id}');" data-toggle="modal" data-target=".bs-example-modal-md"/>
                        </td>
                    </tr>
					</#list>
                </tbody>
            </table>
		</#if>
        </div>
    </form>

    <div class="modal fade bs-example-modal-md in" tabindex="-1" role="dialog" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-md">
            <div class="modal-content">

                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">Modal title</h4>
                </div>
                <div class="modal-body">
                    <#--<h4>Text in a modal</h4>-->
                    <#--<p>Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Vivamus sagittis lacus vel augue laoreet rutrum faucibus dolor auctor.</p>-->
                    <#--<p>Aenean lacinia bibendum nulla sed consectetur. Praesent commodo cursus magna, vel scelerisque nisl consectetur et. Donec sed odio dui. Donec ullamcorper nulla non metus auctor fringilla.</p>-->
                </div>

                <div class="modal-footer">
                    <#--<button type="button" class="btn btn-default" data-dismiss="modal">Close</button>-->
                    <#--<button type="button" class="btn btn-primary">Save changes</button>-->
                </div>

            </div>
        </div>
    </div>
</div>

