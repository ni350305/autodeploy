<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Linux应用自动批量部署平台</title>
<link rel="stylesheet" type="text/css" href="${cssBase}/reset.css">
<link rel="stylesheet" type="text/css"
	href="${cssBase}/autodeploy/layout.css">
<link rel="stylesheet" type="text/css"
	href="${cssBase}/autodeploy/autodeploy.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/plugin/tab.css">
<link rel="stylesheet" type="text/css"
	href="${cssBase}/plugin/rightMenu.css">
<link rel="stylesheet" type="text/css"
	href="${cssBase}/plugin/uikit.css">
<style type="text/css">
</style>
<script type="text/javascript" src="${jqueryBase}"></script>
<script type="text/javascript" src="${jsBase }/autodeploy/layout.js"></script>
<script type="text/javascript" src="${jsBase}/plugin/rightMenu.js"></script>
<script type="text/javascript" src="${jsBase}/plugin/tab.js"></script>
<script type="text/javascript" src="${jsBase }/plugin/uikit.js"></script>
<script type="text/javascript" src="${jsBase }/autodeploy/autodeploy.js"></script>
</head>
<body>
	<div class="layout">
		<div class="left">
			<div class="opration">
				<div class="label">操作</div>
				<ul>
					<li id="newdeploy">新建部署任务</li>
				</ul>
			</div>
			<div class="items">
				<div class="item title">
					<div class="title">操作</div>
				</div>
				<div class="item">
					<a href="/login/logout" >退出</a>
				</div>
				<div class="item">
					<span id="newdeployproject">新建部署项目</span>
				</div>
				<div class="item">
					<a href="/server/" t_id="serverindex" id="manageservers"
						onclick="return false;">管理服务器</a>

				</div>
				<div class="item title projectitle">
					<div class="title">部署任务</div>
				</div>

				<c:forEach var="project" items="${projects}">
					<div class="item">
						<a href="/deployproject/project/${project.id}"
							t_id="${project.id}" onclick="return false;">${project.name}</a>
					</div>
				</c:forEach>

			</div>
		</div>
		<div class="right" id="right-content"></div>
	</div>

	<div id="addproject_dialog" style="display: none">
		<div>
			项目名： <input type="text" name="projectname" />
		</div>
	</div>
</body>