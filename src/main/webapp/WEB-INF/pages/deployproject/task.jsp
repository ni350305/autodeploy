<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<div class="c_item" id="c_task">
	<div class="sub_item">
		<span class="button" id="addtask">添加</span>
	</div>
	<c:forEach var="deployTask" items="${project.deployTasks}"
		varStatus="status">
		<div class="sub_item ${deployTask.id} <c:if test='${status.index==0 }'>sub_item_auto_height</c:if>">
			<div>
				<span class="button index">${status.index+1}</span>
			</div>
			<div class="sub_line_item">
				任务名：
				<san class="red task_name">${deployTask.name}</san>
			</div>
			<div class="sub_line_item">
				<p>任务详情：</p>
				<div class="taskcontent">
					<c:forEach var="detail" items="${deployTask.deployTaskDetails}"
						varStatus="status">

						<c:choose>
							<c:when test="${detail.type==0 }">
								<%--文件  --%>
								<p>
									<span class="red">${status.index+1}、</span> 文件别名：${detail.name}
									<br /> <span style="padding-left: 25px;">文件名：${detail.fileName
										}</span> <br /> <span style="padding-left: 25px;">部署路径:${detail.deployPath}</span>
									<br />
									<c:set var="isEmptyFolder" value="是"></c:set>
									<c:if test="${detail.isEmptyFolder==0}">
										<c:set var="isEmptyFolder" value="否"></c:set>
									</c:if>
									<span style="padding-left: 25px;">部署前是否清空原文件夹：<span
										class="isEmptyFolder red"
										isEmptyFolder="${detail.isEmptyFolder}">${isEmptyFolder}</span></span>
								</p>
							</c:when>
							<c:otherwise>
								<%--命令 --%>
								<p>
									<span class="red">${status.index+1}、</span> 命令别名：${detail.name}<br />
									<span style="padding-left: 25px;">命令:${detail.command}</span>
								</p>
							</c:otherwise>
						</c:choose>

					</c:forEach>
				</div>
			</div>
			<div class="sub_line_item">
				服务器组：<span class="red">${deployTask.serverGroup.name}</span>
			</div>
			<div class="sub_line_item">
				是否并行部署：<span class="red">${deployTask.isConcurDeploy}</span>
			</div>
			<div class="sub_edit_item">
				<span class="button excute" taskid="${deployTask.id}">执行</span><span
					class="button del_task_btn" taskid="${deployTask.id}">删除</span><span
					class="button update_task_btn" taskid="${deployTask.id}">修改</span>
			</div>
		</div>
	</c:forEach>
</div>
