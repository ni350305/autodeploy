<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../../commons/taglibs.jsp"%>
<link rel="stylesheet" type="text/css" href="/css/default/plugin/tab/tab.css">
<script type="text/javascript" src="tab.js">
	
</script>
<div class="mini-layout-body" id="mini-layout-body"></div>
<script type="text/javascript">
	$(document).ready(function() {

		var tab = null;
		tab = new TabView({
			container_id : 'mini-layout-body',
			position : 'top'
		});
		tab.add({
			id : 'tab1_id_index1',
			title : "工作台",
			url : "../frame/main_temp.html",
			isClosed : false
		});
		tab.add({
			id : 'tab1_id_index2',
			title : "工作台",
			url : "../frame/main_temp.html",
			isClosed : true
		});
	});
</script>