<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/taglibs.jsp"%>
<%--重新上传部署文件 信息--%>
<iframe name="reuploadfiletarget" id="reuploadfiletarget" style="display: none"> </iframe>
<div id="reuploadfile_dialog" style="display: none">
	<form action="/deployproject/reuploadDeployFile" method="POST" name="frm" enctype="multipart/form-data" target="reuploadfiletarget"
		class="items dialog_add_file">
		<input type="hidden" name="projectId" value="${project.id}"> <input type="hidden" name="id" value=""> <input type="file" name="filename"
			style="display: none" />
		<div class="item">
			<span class="red">*</span> 文 件 名 ： <input type="text" name="fileName" readonly style="margin-left: 2px; width: 250px" /> <span
				class="button choose_file_btn">选择文件</span>
		</div>
		<div class="item">
			<span class="red">*</span> 是否删除原文件： <input type="radio" name="isDeleteFile" value="1" /> 是（原文件将被删除） <input type="radio" name="isDeleteFile" value="0"
				checked /> 否（原文件中的内容不会删除)
		</div>
	</form>
</div>
<%--更新部署文件 信息--%>
<iframe name="updatefiletarget" id="updatefiletarget" style="display: none"> </iframe>
<div id="updatefile_dialog" style="display: none">
	<form method="get" action="/deployproject/updateDeployFile" name="frm" target="updatefiletarget" class="items dialog_add_file">
		<input type="hidden" name="projectId" value="${project.id}"> <input type="hidden" name="id" value="">
		<div class="item">
			<span class="red">*</span> 部署别名： <input type="text" name="name" style="width: 200px;" />
		</div>
		<div class="item">
			<span class="red">*</span> 部署路径： <input type="text" name="deployPath" style="width: 200px;" />
		</div>
		<div class="item">
			<span class="red">*</span> 部署前是否清空原文件夹： <input type="radio" name="isEmptyFolder" value="0" /> 否 <input type="radio" name="isEmptyFolder" value="1" checked />
			是
		</div>
	</form>
</div>
<%--添加部署文件 --%>
<iframe name="filetarget" id="filetarget" style="display: none"> </iframe>
<div id="addfile_dialog" style="display: none">
	<form method="POST" action="/deployproject/addDeployFile" name="frm" enctype="multipart/form-data" target="filetarget" class="items dialog_add_file">
		<input type="hidden" name="projectId" value="${project.id}"> <input type="file" name="filename" style="display: none" />
		<div class="item">
			<span class="red">*</span> 部署别名： <input type="text" name="name" />
		</div>
		<div class="item">
			<span class="red">*</span> 文 件 名 ： <input type="text" name="fileName" readonly style="margin-left: 2px;" /> <span class="button choose_file_btn">选择文件</span>
		</div>
		<div class="item">
			<span class="red">*</span> 部署路径： <input type="text" name="deployPath" />
		</div>
		<div class="item">
			<span class="red">*</span> 部署前是否清空原文件夹： <input type="radio" name="isEmptyFolder" value="0" /> 否 <input type="radio" name="isEmptyFolder" value="1" checked />
			是
		</div>
	</form>
</div>
<%--右键菜单 --%>
<div id="rMenu" class="rMenu">
	<ul>
		<li id="m_update_file">上传文件</li>
		<li id="m_update_folder">上传文件夹</li>
		<li id="m_new_folder">新建文件夹</li>
		<li id="m_rename">重命名</li>
		<li id="m_download">下载</li>
		<li id="m_del">删除</li>
	</ul>
</div>

<%--管理文件中的上传文件窗口 --%>
<iframe name="file_manage_updatefiletarget" id="file_manage_updatefiletarget" style="display: none"> </iframe>
<div id="file_manage_upload_dialog" style="display: none">
	<form method="POST" action="/filemanage/uploadFile" name="frmm" enctype="multipart/form-data" target="file_manage_updatefiletarget"
		class="items dialog_add_file">
		<input type="hidden" name="path"> <input type="hidden" name="deployFileId"> <input type="hidden" name="projectId" value="${project.id}">
		<input type="file" name="filename" style="display: none" />
		<div class="item">
			<span class="red">*</span> 文 件 名 ： <input type="text" name="fileName" readonly style="margin-left: 2px;" /> <span class="button file_manage_choose_file_btn">选择文件</span>
		</div>
	</form>
</div>