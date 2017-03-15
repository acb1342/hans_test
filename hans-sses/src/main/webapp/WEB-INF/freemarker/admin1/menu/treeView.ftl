<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <script type="text/javascript" src="/js/jquery/jquery-1.7.2.custom.js"></script>
<script src="/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="/js/extjs/ext-all.js" type="text/javascript"></script>
<script type="text/javascript">
	var tree;

	// 메뉴를 트리로 생성
	function displayTree(rootNode) {

		tree = new Ext.tree.TreePanel({
			title:"SKT-EVC Menu Management",
			el:'tree-div',
			useArrows:true,
			autoScroll:true,
			animate:true,
			height:500,
			singleExpand:true,
			loader:{
				dataUrl:'/admin/menu/getChildMenus.json',
				createNode:function(menu) {
					var node = {
							id:menu.id,
							text:menu.title,
							url:menu.url,
							leaf:(menu.type === "LEAF")
					};
					return Ext.tree.TreeLoader.prototype.createNode.call(this, node);
				}
			},
			root:rootNode,
			//rootVisible:false,
			bbar:['->',{
				text:'Reload',
				handler:function() { reloadTree(); }
			}],

		});

		// render the tree
		tree.render();
		rootNode.expand(1);

		tree.on('click', onClick);


		function onClick(node, e) {
			parent.rightFrame.location = "/admin/menu/detail.htm?id=" + node.id;

			var nodeAttr = node.attributes;
			if (!nodeAttr.leaf) {
				loginSessionChk();
				node.toggle();
			}
		}

		function onContextMenu(node, event) {
			var menu = new Ext.menu.Menu();

			var menuIdx = 1;
			if (!node.isLeaf()) {
				menu.add({
					op:menuIdx++,
					text:'<fmt:message key="label.cmsMenu.createMenu"/>',
					handler:function() {
						parent.rightFrame.location = "/admin/menu/create.htm?parentId=" + node.id;
					},
					scope:this
				});
			}
			if (node.id != tree.root.id) {
				menu.add({
					op:menuIdx++,
					text:'<fmt:message key="label.cmsMenu.modifyMenu"/>',
					handler:function() {
						parent.rightFrame.location = "/admin/menu/update.htm?id=" + node.id;
					},
					scope:this
				});

				menu.add({
					op:menuIdx++,
					text:'<fmt:message key="label.cmsMenu.deleteMenu"/>',
					handler:function() {
						deleteById('/admin/menu/delete.json', node.id, function() {
							reloadTree();
						});
					},
					scope:this
				});
			}

			menu.showAt(event.getXY());
		}
	}

	function moveEvent(tree, node, oldParent, newParent, index) {
		index++;
		$.ajax('/admin/menu/move.json', {
			data:{
				id:node.id,
				oldParentMenuId:oldParent.id,
				newParentMenuId:newParent.id,
				index:index
			},
			dataType:'json',
			success:function(data, textStatus, jqXHR) {
				// console.log("Success to move node.", data);
			},
			error:function(jqXHR, textStatus, error) {
				jAlert("Fail to move node. ["+ textStatus +"]");
			}
		});
	}

	function reloadTree() {

		// clear tree-div inner HTML
		var treeElem = document.getElementById("tree-div");
		treeElem.innerHTML = "";

		$.getJSON("/admin/menu/getRootMenu.json", function(data) {


			var rootNode = new Ext.tree.AsyncTreeNode({
				text:data.title,
				draggable:false,
				id:data.id,
				terminal:'directory'
			});

			displayTree(rootNode);
		});
	}

	Ext.onReady(function() {
		//reloadTree();
	});
</script>
<div id="tree-div" style="width:280px; border:0px solid #c3daf9;" class="menuAdminTree"></div>
