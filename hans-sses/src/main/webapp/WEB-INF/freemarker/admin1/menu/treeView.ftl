<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>

<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/jstree/3.3.3/themes/default/style.min.css" />
<script src="//cdnjs.cloudflare.com/ajax/libs/jstree/3.3.3/jstree.min.js"></script>
<script type="text/javascript">
    $(function() {

        var node_data;

        $.getJSON("/admin1/menu/getRootMenu.json", function (data) {
            createJSTrees(data);
        });

        function createJSTrees(jsonData) {
            console.log(JSON.stringify(jsonData));
            $("#container").jstree({
                "core" : {
                    "data" : jsonData,
                    "check_callback":true
                },
                "plugins" : ["dnd","state","contextmenu"]
            }).on('changed.jstree', function (e, data) {
                node_data = data;
                var i, j, r = [];
                for(i = 0, j = data.selected.length; i < j; i++) {
                    r.push(data.instance.get_node(data.selected[i]).id);
                }
                $('#event_result').html('Selected: ' + r.join(', '));
            }).bind("dblclick.jstree", function (event) {
                console.log("dblclick.jstree");
                editNod();
            });
        }

        $("#save").click(function(){
            node_data = $("#container").jstree(true).get_json(null, {"flat":true});

            console.log(JSON.stringify(node_data));

            $.ajax({
                method:"POST",
                url : "/admin1/menu/orderUpdate.json",
                data : JSON.stringify(node_data),
                dataType : "json",
                contentType : "application/json; charset=UTF-8",
                success:function(menuData) {
                    console.log("Success to move node.");
                    $('#container').jstree(true).settings.core.data = menuData;
                    $('#container').jstree(true).refresh();
                },
                error:function() {
                    console.log("error to move node.");

                }
            });

        });

        $("#create").click(function(){
            var CurrentNode = $("#container").jstree("get_selected");
            $("#container").jstree('create_node', CurrentNode, { "data":"new_node" }, 'last');

        });

        $("#delete").click(function(){

            if(confirm("하위 메뉴도 함께 삭제됩니다. 삭제하시겠습니까")){
                var node_data = $("#container").jstree("get_selected");
                console.log("node_data=="+node_data[0]);
                $.ajax({
                    url : "/admin1/menu/delete.json",
                    data : {id:node_data[0]},
                    success:function(data) {
                        console.log("Success to delete node.", data);
                        var instance = $('#container').jstree(true);
                        instance.delete_node(instance.get_selected());
                    },
                    error:function() {
                        console.log("error to delete node.");

                    }
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

</script>
<div id="container">

</div>
<div>
    <button id="create">create</button>
    <button id="save">save</button>
    <button id="delete">delete</button>
</div>
<div id="event_result"></div>
