/**
 * @license Copyright (c) 2003-2015, CKSource - Frederico Knabben. All rights reserved.
 * For licensing, see LICENSE.md or http://ckeditor.com/license
 */

CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	config.language = 'ko';
	config.uiColor = '#990000';

	config.removePlugins = 'resize';
	config.resize_enabled = false;
	
	config.font_defaultLabel = '맑은 고딕';
	config.font_names = '맑은 고딕; 돋움; 굴림; 궁서; 바탕; ' +  CKEDITOR.config.font_names;
	
	config.filebrowserBrowseUrl			= '/ckfinder/ckfinder.html';
	config.filebrowserImageBrowseUrl	= '/ckfinder/ckfinder.html?type=Image';
	
	config.filebrowserFlashBrowseUrl	= '/ckfinder/ckfinder.html?type=Flash';
	config.filebrowserUploadUrl			= '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Files';
	config.filebrowserImageUploadUrl	= '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Images';
	config.filebrowserFlashUploadUrl	= '/ckfinder/core/connector/java/connector.java?command=QuickUpload&type=Flash';

	config.removeButtons = 'Print,Templates,About,NewPage,Save,Language';
	
	config.extraPlugins = 'audio';

	config.toolbar = [
		['Source','-','Cut','Copy','Paste','PasteText','PasteFromWord','Undo','Redo','SelectAll','RemoveFormat'],
		'/',
		['Styles','Format','Font','FontSize','TextColor','BGColor','Maximize','ShowBlocks'],
		'/',
		['Bold','Italic','Underline','Strike','Subscript','Superscript'],
		['JustifyLeft','JustifyCenter','JustifyRight','JustifyBlock'],
		['NumberedList','BulletedList','Outdent','Indent','Blockquote','CreateDiv'],
		['Image','Table','HorizontalRule','Smiley','SpecialChar','PageBreak']
	];
	
};
 