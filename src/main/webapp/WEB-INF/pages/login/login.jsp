<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登录</title>
<link rel="stylesheet" type="text/css" href="${cssBase}/reset.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/icon.css">
<link rel="stylesheet" type="text/css" href="${cssBase}/login/login.css">
<style type="text/css">
</style>
<script type="text/javascript" src="${jqueryBase}"></script>
</head>
<body>
	<div class="layout">
		<div class="logindiv">
			<form action="/login/check" class="loginform" method="POST"
				name="frm">
				<div>
					<label>欢迎使用-请登录</label>
				</div>
				<div>
					<label>用户名：</label><input type="text" name="name">
				</div>
				<div>
					<label>密&nbsp;&nbsp;&nbsp;码：</label><input type="password"
						name="passwd">
				</div>
				<div>
					<input type="submit" class="button loginbtn" value="登 录"><input
						type="reset" class="button resetbtn" value="重 置">
				</div>
			</form>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(".layout").height($(window).height() - 25);
	window.setInterval(function(){
		$(".layout").height($(window).height() - 25);
	}, 500);
	var msg = "${msg}";
	if (msg != "") {
		alert(msg);
	}
</script>