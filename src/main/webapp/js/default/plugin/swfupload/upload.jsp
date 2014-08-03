<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<link href="<%=basePath%>css/default/plugin/swfupload.css"
	rel="stylesheet" type="text/css" />
<script type="text/javascript"
	src="<%=basePath%>js/default/plugin/swfupload/swfupload.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/default/plugin/swfupload/swfupload.queue.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/default/plugin/swfupload/fileprogress.js"></script>
<script type="text/javascript"
	src="<%=basePath%>js/default/plugin/swfupload/handlers.js"></script>


<script type="text/javascript">
		var upload1, upload2;

		window.onload = function() {
			
		//在window的载入时初始化swfupload对象
			upload1 = new SWFUpload({

				//提交路径
				//窗体的 打开与url无关
				upload_url: "upload.action",
				//向后台传递额外的参数
				//提交到服务器的参数信息，这样就添加了一个param参数，值是uploadParams在服务器端用request.getParameter(“param”)就可以拿到值
				post_params: {"name" : "kaobian"},
				//上传文件的名称
				file_post_name: "file",
				
				file_size_limit : "102400",	// 100MB
				file_types : "*.*",
				file_types_description : "All Files",
				file_upload_limit : "10",
				file_queue_limit : "0",

				// 事件处理
				file_dialog_start_handler : fileDialogStart,
				file_queued_handler : fileQueued,
				file_queue_error_handler : fileQueueError,
				file_dialog_complete_handler : fileDialogComplete,
				upload_start_handler : uploadStart,
				upload_progress_handler : uploadProgress,
				upload_error_handler : uploadError,
				upload_success_handler : uploadSuccess,
				upload_complete_handler : uploadComplete,

				// 按钮的处理
			//	button_image_url:"/images/default/plugin/swfupload/XPButtonUploadText_61x22.png",
				button_text:"上传文件",
				button_text_style:"font: 12px/1.14 '微软雅黑', '宋体', Arial, sans-serif, Verdana, Tahoma;",
				button_placeholder_id : "spanButtonPlaceholder1",
				button_width: 61,
				button_height: 22,
				
				// Flash Settings
				flash_url : "/js/default/plugin/swfupload/swfupload.swf",
				
				custom_settings : {
					progressTarget : "fsUploadProgress1",
					cancelButtonId : "btnCancel1"
				},
				// Debug Settings
				debug: false
			});
	     }
	    function completeEvent(){
	    	alert("complete");
	    }
	</script>
</head>

<body>
	<div id="content">
<div id="dialog" class="confirmation slide closable modal"
		style="z-index: 9999;">
		<div class="content">
			<a href="javascript:void(0)" onclick="$('#processdialog').hide()"
				class="close">×</a>
			<div id="fsUploadProgress1"></div>
		</div>
		<div class="actions">
			<button class="ok main" id="btnCancel1"
				onclick="cancelQueue(upload1);">取消上传</button>
			<button class="ok main" onclick="$('#processdialog').hide()">确定</button>
		</div>
	</div>
		<form action="upload.action" method="post" name="thisform"
			enctype="multipart/form-data">
			<table>
				<tr valign="top">
					<td>
						<div> 
							<div style="padding-left: 5px;">
								<span id="spanButtonPlaceholder1"></span> <input id="btnCancel1"
									type="button" value="Cancel Uploads"
									onclick="cancelQueue(upload1);"  
									style="margin-left: 2px; height: 22px; font-size: 8pt;" /> <br />
							</div>
						</div>
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
