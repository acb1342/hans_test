<script type="text/javascript">
    $(function() {
        // 저장
        $('#menuSave').click(function() {
            $("#uForm").submit();
        });

    });

    // 이전 페이지로 이동
    function cancel() {
        var menuid = ${cmsMenuFunc.id};
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
    }

</script>

<div class="x_content">
    <form method="post" id="uForm" name="uForm" action="/admin/menu/func/update.htm">
        <table class="table table-striped responsive-utilities jambo_table dataTable" aria-describedby="example_info">
            <tbody>
            <tr>
                <td style="width:20%"> ID </td>
                <td>
                    <input type="text" name="id" readOnly="readonly" value="${cmsMenuFunc.id}">
                </td>
            </tr>
            <tr>
                <td> NAME </td>
                <td>
                    <input type="text" name="name" readOnly="readonly" value="${cmsMenuFunc.title}">
                </td>
            </tr>
            <tr>
                <td> TYPE </td>
                <td>
                    <select name="type">

                        <#list types as type>
                            <option value="${type}" <#if type == cmsMenuFunc.type> selected = "selected"</#if>>${type}</option>
                        </#list>

                    </select>
                </td>
            </tr>
            <tr>
                <td> URL </td>
                <td>
                    <input type="text" name="url" style="width: 50%" value="<#if cmsMenuFunc.url?exists>${cmsMenuFunc.url}</#if>">
                </td>
            </tr>
            <tr>
                <td> DESCRIPTION </td>
                <td>
                    <textarea style="width:300px;" rows="4" cols="10" name="description"><#if cmsMenuFunc.description?exists>${cmsMenuFunc.description}</#if></textarea>
                </td>
            </tr>


            </tbody>
        </table>

        <div class="footer">
            <table style="width:100%">
                <tr>
                    <td align="right">
                        <input class="btn btn-default" type="button" value='저장' id="menuSave"/>
                        <input class="btn btn-danger" type="button" value='취소' onclick="javascript:cancel()"/>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>