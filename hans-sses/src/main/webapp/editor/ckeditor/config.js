/**
 * @license Copyright (c) 2003-2013, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.html or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function(config) {
	config.language = 'ko';
	config.skin = 'bootstrapck';
	config.toolbarStartupExpanded = false;

	config.font_defaultLabel = '나눔고딕';
	config.font_names = '나눔고딕/NanumGothic;돋움/Dotum;AppleGothic/AppleGothic;sans-serif/sans-serif';
	config.fontSize_defaultLabel = '11px';
	config.fontSize_sizes = '8/8px;9/9px;10/10px;11/11px;12/12px;14/14px;16/16px;18/18px;20/20px;22/22px;24/24px;26/26px;28/28px;36/36px;48/48px;';
	config.removeButtons = 'Copy,Iframe,Flash,Smiley,PageBreak,ImageButton,Save,Print,Styles,About,Language,Anchor';
	config.format_tags = 'p;h1;h2;h3;pre';
	config.removeDialogTabs = 'image:advanced;link:advanced';
	
	config.filebrowserBrowseUrl = '/ckfinder/ckfinder.html';
	config.filebrowserImageBrowseUrl = '/ckfinder/ckfinder.html?type=Image';
	
	config.filebrowserFlashBrowseUrl = '/ckfinder/ckfinder.html?type=Flash';
	config.filebrowserUploadUrl = '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
	config.filebrowserImageUploadUrl = '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
	config.filebrowserFlashUploadUrl = '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';

	config.extraPlugins = 'divarea,iframe,youtube';
	config.allowedContent = true;
	config.extraAllowedContent = 'iframe[*]';
	
	//config.removePlugins = 'iframe';
	
	config.youtube_width = '640';
	config.youtube_height = '480';
	config.youtube_related = true;
	config.youtube_older = false;
	config.youtube_privacy = false;

	// Toolbar configuration generated automatically by the editor based on config.toolbarGroups.
	config.toolbar = [
	              	{ name: 'document', groups: [ 'mode', 'document', 'doctools' ], items: [ 'Source', 'Sourcedialog', '-', 'Save', 'NewPage', 'Preview', 'Print', '-', 'Templates' ] },
	              	{ name: 'clipboard', groups: [ 'clipboard', 'undo' ], items: [ 'Cut', 'Copy', 'Paste', 'PasteText', 'PasteFromWord', '-', 'Undo', 'Redo' ] },
	              	{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ], items: [ 'Find', 'Replace', '-', 'SelectAll', '-', 'Scayt' ] },
	              	{ name: 'forms', items: [ 'Form', 'Checkbox', 'Radio', 'TextField', 'Textarea', 'Select', 'Button', 'ImageButton', 'HiddenField' ] },
	              	'/',
	              	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ], items: [ 'Bold', 'Italic', 'Underline', 'Strike', 'Subscript', 'Superscript', '-', 'RemoveFormat' ] },
	              	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ], items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'Blockquote', 'CreateDiv', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock', '-', 'BidiLtr', 'BidiRtl', 'Language' ] },
	              	{ name: 'links', items: [ 'Link', 'Unlink', 'Anchor' ] },
	              	{ name: 'insert', items: [ 'Image', 'Flash', 'Table', 'HorizontalRule', 'Smiley', 'SpecialChar', 'PageBreak', 'Iframe', 'Slideshow', 'Youtube' ] },
	              	'/',
	              	{ name: 'styles', items: [ 'Styles', 'Format', 'Font', 'FontSize' ] },
	              	{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
	              	{ name: 'tools', items: [ 'Maximize', 'ShowBlocks' ] },
	              	{ name: 'others', items: [ '-', 'gg', 'savebtn' ] },
	              	{ name: 'about', items: [ 'About' ] }
	              ];

	// Toolbar groups configuration.
	config.toolbarGroups = [
				  	{ name: 'document', groups: [ 'mode', 'document', 'doctools' ] },
				  	{ name: 'clipboard', groups: [ 'clipboard', 'undo' ] },
				  	{ name: 'editing', groups: [ 'find', 'selection', 'spellchecker' ] },
				  	{ name: 'forms' },
				  	'/',
				  	{ name: 'basicstyles', groups: [ 'basicstyles', 'cleanup' ] },
				  	{ name: 'paragraph', groups: [ 'list', 'indent', 'blocks', 'align', 'bidi' ] },
				  	{ name: 'links' },
				  	{ name: 'insert' },
				  	'/',
				  	{ name: 'styles' },
				  	{ name: 'colors' },
				  	{ name: 'tools' },
				  	{ name: 'others' },
				  	{ name: 'about' }
				  ];
};
