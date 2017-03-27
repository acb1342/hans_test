<script type="text/javascript">
    $(function() {
        // 저장
        $('#menuFuncUpdate').click(function() {
            var id = "${cmsMenuFunc.id}";

            $.ajax({
                url : "/admin/menu/func/update.htm",
                method : "GET",
                data : {
                    id: id
                },
                success:function(data) {
                    console.log("Success to detail node.", data);
                    $(".footer").html(data);
                },
                error:function() {
                    console.log("error to detail node.");

                }
            });
            //$("#uForm").submit();
        });

        $("#menuFuncDelete").click(function(){
            if(confirm("삭제하시겠습니까")){
                var id = "${cmsMenuFunc.id}";
                $.ajax({
                    url : "/admin/menu/func/delete.json",
                    data : {id:id},
                    success:function(data) {
                        console.log("Success to detail node.", data);
                        $(".rightMenu").html(data);
                    },
                    error:function() {
                        console.log("error to detail node.");

                    }
                });
            }
        });

        $("#menuFunclist").click(function(){
            var menuid = "${cmsMenuFunc.menuId}";
            $.ajax({
                url : "/admin/menu/detail",
                data : {id:menuid},
                success:function(data) {
                    console.log("Success to detail node.", data);
                    $(".rightMenu").html(data);
                },
                error:function() {
                    console.log("error to detail node.");

                }
            });
        });

    });

</script>
<div class="x_content">
    <table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
        <tbody>

        <tr>
            <td style="width:20%"> NAME </td>
            <td>
                ${cmsMenuFunc.name}
            </td>
        </tr>
        <tr>
            <td> URL </td>
            <td>
                ${cmsMenuFunc.url}
            </td>
        </tr>
        <tr>
            <td> AUTH </td>
            <td>
               ${cmsMenuFunc.auth}
            </td>
        </tr>

        <tr>
            <td> DESCRIPTION </td>
            <td>
                ${cmsMenuFunc.description}
            </td>
        </tr>

        </tbody>
    </table>

    <div class="footer">
        <table style="width:100%">
            <tr>
                <td align="right">
                    <input class="btn btn-dark" type="button" value='수정' id="menuFuncUpdate"/>
                    <input class="btn btn-danger" type="button" value='삭제' id="menuFuncDelete"/>
                    <input class="btn btn-default" type="button" value='목록' id="menuFunclist"/>
                </td>
            </tr>
        </table>
    </div>
</div>