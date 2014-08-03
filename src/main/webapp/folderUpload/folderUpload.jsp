
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312">
<title>文件夹上传</title>
<script type="text/javascript" src="http://localhost:80//js/jquery-1.7.1.js"></script>
</head>
<body>
	<input type="file" name="file_input[]" id="file_input" webkitdirectory="" directory="" multiple mozdirectory="" >
	<%--重新上传部署文件 信息--%>
	<iframe name="reuploadfiletarget" id="reuploadfiletarget" style="display: none"> </iframe>
	<div id="reuploadfile_dialog">
		<form action="/deployproject/reuploadDeployFile" method="POST" name="frm" enctype="multipart/form-data" target="reuploadfiletarget"
			class="items dialog_add_file">

			<div class="item">
				<span class="red">*</span> 文 件 名 ： <input type="text" readonly style="margin-left: 2px; width: 250px" /> <span class="button choose_file_btn">选择文件夹</span>
			</div>
		</form>
	</div>
</body>
</html>
<script>
	$("#file_input").change(function(e){
		var event = e || window.event;
		var files = event.target.files || event.dataTransfer.files;
		FileSelectHandler(files);
	});
	$(".choose_file_btn").click(function(){
		$("#file_input").trigger("click");
	});
	function FileSelectHandler(files){
		//alert(files.length); 
		// process all File objects
		for ( var i = 0, file; i < files.length; i++) {
			file = files[i]
			//if (file.name != ".") {
				console.log(i+" name:" + file.name);
				console.log("type:" + file.type);
				console.log("absolutePath:" + file.webkitRelativePath);
				//ajaxUpload(file);
			//}
		}
	}
	function ajaxUpload(file){
		var formData = new FormData();
		formData.append("projectId", 1);
		formData.append("deployFileId", 12);
		formData.append("path", "/css/" + file.webkitRelativePath.substring(0, file.webkitRelativePath.lastIndexOf("/")));
		formData.append("filename", file);
		$.ajax({
			async : false,
			type : 'POST',
			url : '/filemanage/uploadFile/',
			data : formData,
			/** 
			 *必须false才会自动加上正确的Content-Type 
			 */
			contentType : false,
			/** 
			 * 必须false才会避开jQuery对 formdata 的默认处理 
			 * XMLHttpRequest会对 formdata 进行正确的处理 
			 */
			processData : false
		}).then(function(){
			alert("成功");
		}, function(){
			//failCal  
		});
	}
</script>