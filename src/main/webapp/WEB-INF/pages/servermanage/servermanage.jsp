<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>服务器管理</title>
<link rel="stylesheet" type="text/css" href="${cssBase}/reset.css">
<link rel="stylesheet" type="text/css"
	href="${cssBase}/plugin/uikit.css">
<link rel="stylesheet" type="text/css"
	href="${cssBase}/plugin/easygrid.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/icon.css">
<link rel="stylesheet" type="text/css"
	href="${cssBase}/servermanage/servermanage.css">
<style type="text/css">
</style>
<script type="text/javascript" src="${jqueryBase}"></script>
<script type="text/javascript" src="${jsBase }/plugin/uikit.js"></script>
<script type="text/javascript" src="${jsBase }/plugin/easygrid.js"></script>
<script type="text/javascript"
	src="${jsBase }/servermanage/servermanage.js"></script>
<script type="text/javascript" src="${jsBase }/servermanage/server.js"></script>
<script type="text/javascript"
	src="${jsBase }/servermanage/serverGroup.js"></script>
</head>
<body>
	<div class="layout">
		<div class="tabheader">
			<div class="tabs">
				<div class="tab choosed">所有服务器</div>
				<div class="tab">所有服务器组</div>
			</div>
		</div>
		<div class="container">
			<div class="left" id="server"></div>
			<div class="right hidden" id="serverGroup"></div>
		</div>
	</div>
	<div name="dialogs" class="hidden">
		<%@ include file="addServerDialog.jsp"%>
		<%@ include file="addServerGroupDialog.jsp"%>
	</div>
</body>