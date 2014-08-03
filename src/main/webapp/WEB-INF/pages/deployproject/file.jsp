<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<div class="c_item choosed" id="c_file">
	<div class="sub_item" style="height: auto">
		<p>
			<span class="red"> 部署文件别名：</span>给部署文件一个别名，用于配置任务
		</p>
		<p>
			<span class="red"> 部署文件名：</span>部署文件要上传，目前只支持 /.war /.zip /.tar.gz
			/.rar 格式的部署文件，任务执行时，文件将会被解压到“部署路径”下，<br />如：文件是“tomcat.tar.gz”
			，部署路径是“/home/project/autodeploytemplate/tomcat”，则“tomcat.tar.gz”中的内容会被放入到"tomcat"目录下，
			如最终结果为"*/tomcat/bin"，“*/tomcat/catlina"...<br /> <span class="red">*</span>重新上传后，原部署文件将会被覆盖。
		</p>
		<p>
			<span class="red">部署文件路径：</span>是要部署到其他服务器上的路径，如：“/home/project/autodeploytemplate/tomcat”
		</p>
		<p>
			<span class="red">部署前是否清空原文件夹：</span>是要部署到其他服务器上的文件夹，“是”，则部署时，会先清清远程服务器的相应文件，然后再部署.
		</p>

	</div>
	<div class="sub_item">
		<span class="button" id="addfile">添加</span>
	</div>
	<div class="deployfiles">
		<c:forEach var="deployFile" items="${project.deployFiles}"
			varStatus="status">
			<div class="sub_item file${deployFile.id} <c:if test='${status.index==0 }'>sub_item_auto_height</c:if>">
				<div>
					<span class="button index">${status.index+1}</span>
				</div>
				<div class="sub_line_item">
					部署文件别名：<span class="file_name red">${deployFile.name}</span>
				</div>
				<div class="sub_line_item">
					部署文件名：<span class="deploy_filename red">${deployFile.fileName}</span><span
						class="button reupload_btn" fileid="${deployFile.id}">重新上传</span>
				</div>
				<div class="sub_line_item">
					部署文件路径：<span class="deploy_path red">${deployFile.deployPath}</span>
				</div>
				<div class="sub_line_item">
					<c:set var="isEmptyFolder" value="是"></c:set>
					<c:if test="${deployFile.isEmptyFolder==0}">
						<c:set var="isEmptyFolder" value="否"></c:set>
					</c:if>
					部署前是否清空原文件夹：<span class="isEmptyFolder red"
						isEmptyFolder="${deployFile.isEmptyFolder}">${isEmptyFolder}</span>
				</div>
				<div class="sub_edit_item">
					<span class="button manage_file_btn" fileid="${deployFile.id}">管理文件</span>
					<span class="button del_file_btn" fileid="${deployFile.id}">删除</span><span
						class="button update_file_btn" fileid="${deployFile.id}">修改</span>
				</div>
			</div>
		</c:forEach>
	</div>
	<%-- 部署文件模板--%>
	<div class="newfileplatform hidden">
		<div class="sub_item">
			<div>
				<span class="button index"></span>
			</div>
			<div class="sub_line_item">
				部署文件别名：<span class="file_name red"></span>
			</div>
			<div class="sub_line_item">
				部署文件名：<span class="deploy_filename red"></span><span
					class="button reupload_btn" fileid="">重新上传</span>
			</div>
			<div class="sub_line_item">
				部署文件路径:<span class="deploy_path red"></span>
			</div>
			<div class="sub_line_item">
				部署前是否清空原文件夹：<span class="isEmptyFolder red" isEmptyFolder=""></span>
			</div>
			<div class="sub_edit_item">
				<span class="button manage_file_btn" fileid="${deployFile.id}">管理文件</span>
				<span class="button del_file_btn" fileid="${deployFile.id}">删除</span><span
					class="button update_file_btn" fileid="${deployFile.id}">修改</span>
			</div>
		</div>
	</div>
</div>