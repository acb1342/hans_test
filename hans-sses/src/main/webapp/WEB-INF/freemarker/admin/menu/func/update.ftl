<script type="text/javascript">
    $(function() {
        // 저장
        $('#menuFuncSave').click(function() {
            var id = ${cmsMenuFunc.id};
            var menuId = ${cmsMenuFunc.menuId};
            var name = $("#funcName").val();
            var auth = $("#funcAuth").val();
            var url = $("#funcUrl").val();
            var desc = $("#funcDesc").val();

            $.ajax({
                url : "/admin/menu/func/update.htm",
                method : "POST",
                data : {
                    id: id,
                    menuId : menuId,
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
        });

    });

</script>

<div class="x_content">
    <table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
        <tbody>

        <tr>
            <td style="width:20%"> NAME </td>
            <td>
                <input type="text" id="funcName" value="${cmsMenuFunc.name}">
            </td>
        </tr>
        <tr>
            <td> URL </td>
            <td>
                <input type="text" id="funcUrl" style="width: 50%" value="<#if cmsMenuFunc.url?exists>${cmsMenuFunc.url}</#if>">
            </td>
        </tr>
        <tr>
            <td> AUTH </td>
            <td>
                <select id="funcAuth">

                    <#list types as type>
                        <option value="${type}" <#if type == cmsMenuFunc.auth> selected = "selected"</#if>>${type}</option>
                    </#list>

                </select>
            </td>
        </tr>
        <tr>
            <td> DESCRIPTION </td>
            <td>
                <textarea style="width:300px;" rows="4" cols="10" id="funcDesc"><#if cmsMenuFunc.description?exists>${cmsMenuFunc.description}</#if></textarea>
            </td>
        </tr>


        </tbody>
    </table>

    <div class="footer">
        <table style="width:100%">
            <tr>
                <td align="right">
                    <input class="btn btn-default" type="button" value='저장' id="menuFuncSave" data-dismiss="modal"/>
                    <input class="btn btn-danger" type="button" value='취소' data-dismiss="modal"/>
                </td>
            </tr>
        </table>
    </div>
</div>