<!DOCTYPE html>
<html lang="en">
<head>
    <script src="/css/gentelella-master/vendors/jquery/dist/jquery.min.js"></script>
	<link rel="stylesheet" href="/css/jstree/themes/default/style.min.css" />
	<script type="text/javascript" src="/js/jstree.min.js"></script>

	<style type="text/css">
		#container {border:1px solid #dcdcdc;padding:8px;width:50%;}
		.buttonMenu {margin-top: 10px;margin-left: 15px;}
	</style>

	<script type="text/javascript">
		$(function() {

			var node_data;

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
//					.on('changed.jstree', function (e, data) {
//					node_data = data;
//					var i, j, id, title;
//					console.log(node_data.toString());
//					for(i = 0, j = data.selected.length; i < j; i++) {
//					    id = data.selected[i];
//					    title = data.instance.get_node(data.selected[i]).text;
//						//r.push(data.instance.get_node(data.selected[i]).id);
//					}
//
//					if(id!=undefined){
//					    detailNod(id);
//					}
//					$('#event_result').html('Selected: ' + r.join(', '));
//				})
				.bind("dblclick.jstree", function (event) {
					console.log("dblclick.jstree");
					editNod();
				});
			}

			$("#save").click(function(){
				node_data = $("#container").jstree(true).get_json(null, {"flat":true});

				console.log(JSON.stringify(node_data));

				$.ajax({
					method:"POST",
					url : "/member/company/orderUpdate.json",
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
				console.log(CurrentNode.length);
				if(CurrentNode.length==0){
				    CurrentNode = "#";
				}
				$("#container").jstree('create_node', CurrentNode, { "data":"new_node" }, 'last');

			});

			$("#delete").click(function(){

				if(confirm("하위 메뉴도 함께 삭제됩니다. 삭제하시겠습니까")){
					var node_data = $("#container").jstree("get_selected");
					//console.log("node_data=="+node_data[0]);
					$.ajax({
						url : "/member/company/delete.json",
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

//		function detailNod(id){
//
//            $.ajax({
//                url : "/member/company/detail.json",
//                data : {id:id},
//                success:function(data) {
//                    console.log("Success to detail node.", data);
//                    $(".rightMenu").html(data);
//                },
//                error:function() {
//                    console.log("error to detail node.");
//
//                }
//            });
//
//		}

	</script>
</head>
<body>
<div class="x_content">

	<h4>조직 트리</h4>
	<div id="container"></div>
	<div class="buttonMenu">
		<button id="create">create</button>
		<button id="save">save</button>
		<button id="delete">delete</button>
	</div>

</div>
</body>
</html>