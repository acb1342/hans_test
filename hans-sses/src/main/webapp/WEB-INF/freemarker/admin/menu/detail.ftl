<script type="text/javascript">
	// 기능 생성 페이지로 이동
	function createCmsMenuFunction() {
		var menuid = ${cmsMenu.id};

        $.ajax({
            url : "/admin/menu/function/create.htm",
            data : {menuId:menuid},
            success:function(data) {
                console.log("Success to detail node.", data);
                $(".rightMenu").html(data);
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
            url : "/admin/menu/func/update.htm",
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

</script>

<div class="x_content">
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
                    <input class="btn btn-default" type="button" value='추가' onclick="createCmsMenuFunction()"/>
                    <input class="btn btn-danger" type="button" value='수정' onclick="javascript:update()"/>
                </td>
            </tr>
        </table>

        <div class="footer">
		<#if cmsMenu.functions?exists && cmsMenu.functions?size &gt; 0>
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
                            <input type="button" class="btn btn-default" value='상세' onclick="javascript:page_move('/board/appVer/detail.htm','${menuList.id}');"/>
                            <input type="button" class="btn btn-default" value='수정' onclick="javascript:page_move('/board/appVer/update.htm','${menuList.id}');"/>
                        </td>
                    </tr>
					</#list>
                </tbody>
            </table>
		</#if>
        </div>
    </form>
</div>
