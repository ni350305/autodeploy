<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>

<div id="addtask_dialog" style="display: none">
	<div class="items dialog_add_task">
		<div class="item">
			<span class="red">*</span>任务别名： <br /> <input type="text" style="width:380px"
				name="taskname" />
		</div>
		<div class="item taskdetail_container">
			<div class="taskdetailtitle">
				<span class="red">*</span>任务详情：
			</div>
			<div style="position: relative;">
				<div class="taskdetail">
					<%-- 
					<p>
						<span class="button">^</span><span class="button">X</span><span>tomcat</span>
					</p>
					 --%>
				</div>
				<div class="choose_file_btn_container">
					<div class="i_back file_action"></div>
				</div>
				<div class="deployfile">
					<div class="red">部署文件</div>
					<div class="deployfilelist"
						style="height: 75px; overflow-y: auto;">
						<%-- 
							<p>
								<input type="checkbox" value="tomcat" /><label>tomcat</label>
							</p>
							--%>
					</div>
				</div>
				<div class="choose_commands_btn_container">
					<div class="i_back command_action"></div>
				</div>
				<div class="commands">
					<div class="red">命令</div>
					<div class="commandlist" style="height: 75px; overflow-y: auto;">
						<%--  
							<p>
								<input type="checkbox" value="启动tomcat" /><label>启动tomcat</label>
							</p>
							 --%>
					</div>
				</div>
			</div>
		</div>
		<div class="item">
			<span class="red">*</span>服务器组：<input serverGroupId="" style="width:300px"value="" readonly class="servergroup"><span
				class="button add_sever_group_btn">选择服务器组</span>
		</div>
		<div class="item">
			<span class="red">*</span>是否并行部署:<input name="isConcurentDeploy"
				type="radio" value="1" checked/>是<input name="isConcurentDeploy"
				type="radio" value="0" />否
		</div>
	</div>
</div>
