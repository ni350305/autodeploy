<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/default/plugin/rightMenu/rightMenu.css">
<script type="text/javascript" src="rightMenu.js">
	
</script>
<div id="menu">右键菜单</div>
<script>
	var menu = ui.menu().add('Add item').add('Edit item', function() {
		console.log('edit');
	}).add('Remove item', function() {
		console.log('remove');
	}).add('Remove "Add item"', function() {
		menu.remove('Add item').remove('Remove "Add item"');
	});

	menu.on('Add item', function() {
		console.log('added an item');
	});

	//  oncontextmenu = function(e){
	$("#menu")[0].oncontextmenu = function(e) {
		e.preventDefault();
		menu.moveTo(e.pageX, e.pageY).show();
	};
</script>