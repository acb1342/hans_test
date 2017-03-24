<script type="text/javascript">
    $(function() {
        // 저장
        $('#funcSave').click(function() {
            var menuid = "${cmsMenuFunc.menuId}";
            var name = $("#name").val();
            var auth = $("#auth").val();
            var url = $("#url").val();
            var desc = $("#desc").val();

            $.ajax({
                url : "/admin/menu/func/create.htm",
                method : "POST",
                data : {
                    menuId: menuid,
                    name : name,
                    auth : auth,
                    url : url,
                    description : desc
                },
                success:function(data) {
                    console.log("Success to detail node.", data);
                    $(".rightMenu").html(data);
                },
                error:function() {
                    console.log("error to detail node.");

                }
            });
            //$("#uForm").submit();
        });

        $("#funcCancel").click(function(){
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
            <td> NAME </td>
            <td>
                <input type="text" id="name" value="">
            </td>
        </tr>
        <tr>
            <td> URL </td>
            <td>
                <input type="text" id="url" style="width: 50%" value="">
            </td>
        </tr>
        <tr>
            <td> AUTH </td>
            <td>
                <select id="auth">

                    <#list types as type>
                        <option value="${type}">${type}</option>
                    </#list>

                </select>
            </td>
        </tr>

        <tr>
            <td> DESCRIPTION </td>
            <td>
                <textarea style="width:300px;" rows="4" cols="10" id="desc"></textarea>
            </td>
        </tr>

        </tbody>
    </table>

    <div class="footer">
        <table style="width:100%">
            <tr>
                <td align="right">
                    <input class="btn btn-default" type="button" value='저장' id="funcSave"/>
                    <input class="btn btn-danger" type="button" value='취소' id="funcCancel"/>
                </td>
            </tr>
        </table>
    </div>
</div>