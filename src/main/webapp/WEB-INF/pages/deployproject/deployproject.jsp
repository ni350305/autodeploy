<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>部署任务</title>
<link rel="stylesheet" type="text/css" href="${cssBase}/plugin/swfupload.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/reset.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/plugin/easygrid.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/plugin/zTreeStyle.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/deployproject/deployproject.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/plugin/uikit.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/icon.css">
<style type="text/css">
</style>
<script type="text/javascript" src="${jqueryBase}"></script>
<script type="text/javascript" src="${jsBase }/plugin/uikit.js"></script>
<script type="text/javascript" src="${jsBase }/plugin/easygrid.js"></script>
<script type="text/javascript" src="${jsBase }/plugin/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript" src="${jsBase }/deployproject/deployFile.js"></script>
<script type="text/javascript" src="${jsBase }/deployproject/deployCommand.js"></script>
<script type="text/javascript" src="${jsBase }/deployproject/deployTask.js"></script>
<script type="text/javascript" src="${jsBase }/deployproject/fileManage.js"></script>
<script type="text/javascript" src="${jsBase }/deployproject/deployProject.js"></script>
<script type="text/javascript" src="${jsBase}/plugin/swfupload/swfupload.js"></script>
<script type="text/javascript" src="${jsBase}/plugin/swfupload/swfupload.queue.js"></script>
<script type="text/javascript" src="${jsBase}/plugin/swfupload/fileprogress.js"></script>
<script type="text/javascript" src="${jsBase}/plugin/swfupload/handlers.js"></script>
<script type="text/javascript">
	var projectId = "${project.id}";
</script>
</head>
<body>
	<div class="layout">
		<div class="header">
			<div class="items">
				<div class="h_item choosed" id="file">配置部署文件</div>
				<div class="h_item" id="command">配置启动/停止</div>
				<div class="h_item" id="task">配置任务</div>
			</div>
		</div>
		<div class="content">
			<%@ include file="file.jsp"%>
			<%@ include file="command.jsp"%>
			<%@ include file="task.jsp"%>
		</div>
	</div>
	<%@ include file="dialog.jsp"%>
</body>