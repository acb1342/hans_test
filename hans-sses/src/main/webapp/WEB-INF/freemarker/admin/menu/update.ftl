<script type="text/javascript">
    $(function() {
        // 저장
        $('#menuSave').click(function() {
            var menuid = ${cmsMenu.id};
            var type = $("#type").val();
            var url = $("#url").val();
            var desc = $("#desc").val();

            $.ajax({
                url : "/admin/menu/func/update.htm",
                method : "POST",
                data : {
                    id: menuid,
                    type : type,
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

        $("#menuCancel").click(function(){
            var menuid = ${cmsMenu.id};
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
            <td style="width:20%"> ID </td>
            <td>
                <input type="text" name="id" readOnly="readonly" value="${cmsMenu.id}">
            </td>
        </tr>
        <tr>
            <td> NAME </td>
            <td>
                <input type="text" name="name" readOnly="readonly" value="${cmsMenu.title}">
            </td>
        </tr>
        <tr>
            <td> TYPE </td>
            <td>
                <select id="type">

                    <#list types as type>
                        <option value="${type}" <#if type == cmsMenu.type> selected = "selected"</#if>>${type}</option>
                    </#list>

                </select>
            </td>
        </tr>
        <tr>
            <td> URL </td>
            <td>
                <input type="text" id="url" style="width: 50%" value="<#if cmsMenu.url?exists>${cmsMenu.url}</#if>">
            </td>
        </tr>
        <tr>
            <td> DESCRIPTION </td>
            <td>
                <textarea style="width:300px;" rows="4" cols="10" id="desc"><#if cmsMenu.description?exists>${cmsMenu.description}</#if></textarea>
            </td>
        </tr>


        </tbody>
    </table>

    <div class="footer">
        <table style="width:100%">
            <tr>
                <td align="right">
                    <input class="btn btn-default" type="button" value='저장' id="menuSave"/>
                    <input class="btn btn-danger" type="button" value='취소' id="menuCancel"/>
                </td>
            </tr>
        </table>
    </div>
</div>