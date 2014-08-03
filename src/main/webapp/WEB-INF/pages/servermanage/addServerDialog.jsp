<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%--添加部署文件 --%>
<iframe name="server_target" id="server_target" style="display: none">
</iframe>
<div id="add_server_dialog">
	<form method="POST" action="/server/addServer" name="frm"
		enctype="multipart/form-data" target="server_target"
		class="items dialog_add_file">
		<input type="hidden" name="id" value=""> <input type="file"
			name="filename" style="display: none" />
		<div class="item">
			<span class="red">*</span>别名： <input type="text" name="name"
				style="margin-left: 27px" />
		</div>
		<div class="item">
			<span class="red">*</span>ip： <input type="text" name="ip"
				style="margin-left: 40px" />
		</div>
		<div class="item">
			<span class="red">*</span>端口： <input type="text" name="port"
				style="margin-left: 27px" />
		</div>
		<div class="item">
			<span class="red">*</span>登录名： <input type="text" name="userName"
				style="margin-left: 15px" />
		</div>
		<div class="item">
			<span class="red">*</span>认证方式： <input type="radio" name="authType"
				value="0" />密码<input type="radio" name="authType" value="1" checked />密钥
		</div>
		<%-- 
		<div class="item">
			密码： <input type="password" name="password" style="margin-left: 30px" />
		</div>
		<div class="item">
			密钥文件： <input type="text" name="fileName" readonly
				style="margin-left: 5px" /> <span class="button choose_file_btn">选择文件</span>
		</div>
		--%>
		<div class="item">
			备注： <input type="text" name="comment" style="margin-left: 29px" />
		</div>
	</form>
</div>