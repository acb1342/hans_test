<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/include/header.jspf" %>
<style type="text/css" media="all">
	@import url("/js/extjs/resources/css/ext-all.css");
	@import url("/js/extjs/resources/css/xtheme-gray.css");
</style>
<script src="/js/extjs/adapter/ext/ext-base.js" type="text/javascript"></script>
<script src="/js/extjs/ext-all.js" type="text/javascript"></script>
<script type="text/javascript">
	var menuTypes = [
		['DIRECTORY', 'DIRECTORY'],
		['LEAF', 'LEAF']
	];

	Ext.onReady(function() {
		Ext.QuickTips.init();

		var formPanel = new Ext.FormPanel({
			standardSubmit:true,
			labelWidth:75,
			frame:true,
			title:'Update Admin Menu',
			bodyStyle:'padding:5px 5px 0',
			width:545,
			defaults:{width:230},
			defaultType:'textfield',
			items:[{
				fieldLabel:'<fmt:message key="label.cmsMenu.name"/>',
				name:'title',
				value:'${cmsMenu.title}',
				allowBlank:false
			}, {
				id:'type',
				xtype:'combo',
				fieldLabel:'<fmt:message key="label.cmsMenu.type"/>',
				name:'type',
				value:'${cmsMenu.type}',
				triggerAction:'all',
				store:menuTypes,
				readOnly:true,
				editable:false,
				forceSelection:false,
				allowBlank:false
			}, {
				fieldLabel:'<fmt:message key="label.cmsMenu.url"/>',
				name:'url',
				value:'${cmsMenu.url}',
				allowBlank:true
			}, {
				fieldLabel:'<fmt:message key="label.common.description"/>',
				name:'description',
				value:'${cmsMenu.description}'
			}, {
				xtype:'hidden',
				name:'id',
				value:'${cmsMenu.id}',
				allowBlank:false
			}, {
				xtype:'hidden',
				name:'parentId',
				value:'${cmsMenu.parentId}',
				allowBlank:false
			}, {
				xtype:'hidden',
				name:'sort',
				value:'${cmsMenu.sort}',
				allowBlank:false
			}, {
				xtype:'hidden',
				name:'depth',
				value:'${cmsMenu.depth}',
				allowBlank:false
			}],
			buttons:[{
				text:'<fmt:message key="label.common.modify"/>',
				handler:function() {
					formPanel.getForm().getEl().dom.action = '/admin/menu/update.htm';
					formPanel.getForm().getEl().dom.method = 'POST';
					formPanel.getForm().submit();
				}
			}]
		});

		formPanel.render(document.body);
	});
</script>
<%@ include file="/include/footer.jspf" %>