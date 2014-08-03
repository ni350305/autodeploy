<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%--添加部署命令 --%>
<div id="addcommand_dialog" style="display: none">
	<div class="items dialog_add_command">
		<div class="item">
			<span class="red">*</span>
			命令别名：
			<input type="text" style="width: 400px" name="command_name" />
		</div>
		<div class="item">
			<span class="red">*</span>
			命令路径：
			<input type="text" style="width: 400px" name="command_path" />
		</div>
	</div>
</div>
