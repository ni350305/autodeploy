<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<div class="c_item" id="c_command">
	<div class="sub_item">
		<span class="button" id="addcommand">添加</span>
	</div>
	<div class="deploycommands">
		<c:forEach var="deployCommand" items="${project.deployCommands}"
			varStatus="status">
			<div class="sub_item command${deployCommand.id} <c:if test='${status.index==0 }'>sub_item_auto_height</c:if>">
				<div>
					<span class="button index">${status.index+1}</span>
				</div>
				<div class="sub_line_item">
					命令别名：<span class="command_name red">${deployCommand.name }</span>
				</div>
				<div class="sub_line_item">
					<p>
						命令路径：<span class="command_path red">${deployCommand.command}</span>
					</p>
				</div>
				<div class="sub_edit_item">
					<span class="button del_command_btn"
						commandid="${deployCommand.id}">删除</span><span
						class="button update_command_btn" commandid="${deployCommand.id}">修改</span>
				</div>
			</div>
		</c:forEach>
	</div>
	<%--部署命令模板 --%>
	<div class="newcommandplatform hidden">
		<div class="sub_item ">
			<div>
				<span class="button index">{status.index+1}</span>
			</div>
			<div class="sub_line_item">
				命令别名：<span class="command_name red">{deployCommand.name }</span>
			</div>
			<div class="sub_line_item">
				<p>
					命令路径：<span class="command_path red">{deployCommand.command}</span>
				</p>
			</div>
			<div class="sub_edit_item">
				<span class="button del_command_btn" commandid="{deployCommand.id}">删除</span><span
					class="button update_command_btn" commandid="{deployCommand.id}">修改</span>
			</div>
		</div>
	</div>
</div>