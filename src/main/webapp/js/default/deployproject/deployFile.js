var deployFile = {
	mainui : "",// 添加部署文件的弹出框
	waitui:"",// 等待提示
	$form:"",// 当前提交的from
	init : function()
	{
		// 注册部署文件 添加按钮事件
		this.private_regist_add_file_btn_event();
		// 添加时，选择文件按钮事件
		this.private_regist_choose_file_btn_event();
		// 添加文件，确定后的返回数据iframe接收事件
		this.private_regist_add_file_result_event();
		// 删除部署文件按钮事件
		this.private_regist_delete_file_btn_event();
		// 更新文件按钮
		this.private_update_file_btn_event();
		// 重新上传文件按钮
		this.private_reupload_file_btn_event();
	},
	
	// 注册部署文件 添加按钮事件
	private_regist_add_file_btn_event : function()
	{
		$("#addfile").live("click",function()
		{
// var html = $("#addfile_dialog").html();
			$form = $( $("#addfile_dialog").html());
			mainui = ui.confirm('添加部署文件', $form).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
			{
				if (ok) {
					deployFile.waitui = ui.dialog('稍等...').modal().show();
					if ($form.find("input[name='filename']").val() == '' || $form.find("input[name='fileName']").val() == '') {
						confirm('请选择文件');
						return false;
					}
					if ($form.find("input[name='name']").val() == '') {
						confirm('请输入 部署别名');
						return false;
					}
					if ($form.find("input[name='deployPath']").val() == '') {
						confirm('请输入 部署路径');
						return false;
					}
					$form.submit();
				}
			});

		});
	},
	// 添加时，选择文件按钮事件
	private_regist_choose_file_btn_event:function()
	{
		var $file;
		var $fileLabel;
		$("body .choose_file_btn").live("click", function()
		{
			$fileLabel = $(this).parent().parent().find("input[name='fileName']");// 文件名标签
			$file = $(this).parent().parent().find("input[type='file']");// 文件标签
			// 选择了文件后
			$file.change(function()
			{
				var filePath = $file.val();
				if (filePath) {
					var fileName = filePath;
					if (fileName.indexOf(".") == -1) {
						$file.val('');
						confirm('不支持的文件格式');
						return;
					}
					var endEx = fileName.replace(/.*\./g, "");
					if (endEx != 'war' && endEx != 'gz' && endEx != 'zip' && endEx != 'rar') {
						$file.val('');
						confirm('不支持的文件格式');
						return;
					}
					filePath = filePath.replace(/.*[\\|/]/g, "");
					$fileLabel.val(filePath);
				}
			});
			$file.trigger("click");
		});
	},	
		// 上传文件后的，目标信息接收iframe事件
	private_regist_add_file_result_event:function()
	{
		$("#filetarget").load(function()
		{
			var contents = $(this).contents().get(0);
			var data = $(contents).find('body').text();
			try{
				data = eval("(" + data + ")");
				if (data.isSuccess == true) {
					var ui = confirm('操作成功');
// window.setTimeout(function(){
// location.reload(true);
// },500);
					// 后台返回的部署文件对象
					deployFile = data.object;
					// 获取部署文件模板
					$newfilrplatform = $($(".newfileplatform").html());
					$newfilrplatform.addClass("file"+deployFile.id);
					$newfilrplatform.find(".file_name").html(deployFile.name);
					$newfilrplatform.find(".deploy_filename").html(deployFile.fileName);
					$newfilrplatform.find(".reupload_btn").attr("fileid",deployFile.id);
					$newfilrplatform.find(".deploy_filename").html(deployFile.fileName);
					$newfilrplatform.find(".deploy_path").html(deployFile.deployPath);
					$newfilrplatform.find(".del_file_btn").attr("fileid",deployFile.id);
					$newfilrplatform.find(".update_file_btn").attr("fileid",deployFile.id);
					$newfilrplatform.find(".manage_file_btn").attr("fileid",deployFile.id);					
					var isEmptyFolder = deployFile.isEmptyFolder;
					$newfilrplatform.find(".isEmptyFolder").attr("isEmptyFolder",isEmptyFolder);
					if(isEmptyFolder==0){
						$newfilrplatform.find(".isEmptyFolder").html("否");
					}else{
						$newfilrplatform.find(".isEmptyFolder").html("是");
					}				
					$(".deployfiles").prepend($newfilrplatform);
					deployFileHelper.resetFileIndex();
				} else {
					confirm(data.message);
				}
			}catch(e){
				deployFile.waitui.hide();
				alert(e);
			}
		});
	},
	// 删除部署文件按钮事件
	private_regist_delete_file_btn_event:function(){
			$(".del_file_btn").live("click",function(){
						$del_btn=$(this);
						ui.confirm('提示','确定删除部署文件？'+$del_btn.parent().parent().find(".file_name").html()).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
						{
							if (ok) {								
									deployFile.waitui = ui.dialog('稍等...').modal().show();
									fileid=$del_btn.attr("fileid");
									$.ajax({
										url : "/deployproject/deleteDeployFile/" + fileid,
										cache : false,
										dataType : "json",
										success : function(data)
										{
											if(data.isSuccess==true){
												confirm("删除成功");
												$del_btn.parent().parent().remove();	
												deployFileHelper.resetFileIndex();
											}else{
												confirm(data.message);
											}
											deployFile.waitui.hide();
										}
									});
								}
					});
			});
	},
	// 更新文件按钮
	private_update_file_btn_event:function(){
		$(".update_file_btn").live("click",function(){
			$form = $( $("#updatefile_dialog").html());
			deployFile.$form = $form;
			$form.find("input[name='id']").val($(this).attr("fileid"));
			$form.find("input[name='name']").val($(this).parent().parent().find(".file_name").html());
			$form.find("input[name='deployPath']").val($(this).parent().parent().find(".deploy_path").html());
			var isEmptyFolder = $(this).parent().parent().find(".isEmptyFolder").attr("isEmptyFolder");
			$form.find("input[name='isEmptyFolder'][value='"+isEmptyFolder+"']").attr("checked","checked");
			mainui = ui.confirm('修改部署文件', $form).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
			{
				if (ok) {
					deployFile.waitui = ui.dialog('稍等...').modal().show();
					if ($form.find("input[name='name']").val() == '') {
						confirm('请输入 部署别名');
						return false;
					}
					if ($form.find("input[name='deployPath']").val() == '') {
						confirm('请输入 部署路径');
						return false;
					}
					$form.submit();
				}
			});
		});
		// 目标信息接收iframe事件
		$("#updatefiletarget").load(function(){			
				var contents = $(this).contents().get(0);
				var data = $(contents).find('body').text();
				try{
					data = eval("(" + data + ")");
					if (data.isSuccess == true) {
						confirm('操作成功');
						$form = deployFile.$form;
						id = $form.find("input[name='id']").val();			
						$(".sub_item.file"+id).find(".file_name").html($form.find("input[name='name']").val());
						$(".sub_item.file"+id).find(".deploy_path").html($form.find("input[name='deployPath']").val());
						var isEmptyFolder = $(".sub_item.file"+id).find(".isEmptyFolder");
						var isEmptyFolderVal=$form.find("input[name='isEmptyFolder']:checked").val();
						isEmptyFolder.attr("isemptyfolder",isEmptyFolderVal);
						if(isEmptyFolderVal==0){
							isEmptyFolder.html("否");
						}else{
							isEmptyFolder.html("是");
						}
					} else {
						confirm(data.message);
					}
				}catch(e){
					deployFile.waitui.hide();
					alert(e);
				}
			});
	},
	// 重新上传文件按钮
	private_reupload_file_btn_event:function(){
		$(".reupload_btn").live("click",function(){
			$form = $( $("#reuploadfile_dialog").html());
			deployFile.$form = $form;
			// 传入 部署文件id
			$form.find("input[name='id']").val($(this).attr("fileid"));
			$form.find("input[name='fileName']").val($(this).parent().parent().find(".deploy_filename").html());
			mainui = ui.confirm('重新上传部署文件', $form).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
			{
				if (ok) {
					deployFile.waitui = ui.dialog('稍等...').modal().show();
					if ($form.find("input[name='filename']").val() == '' || $form.find("input[name='fileName']").val() == '') {
						confirm('请选择文件');
						return false;
					}
					$form.submit();
				}
			});
		});
		// 目标信息接收iframe事件
		$("#reuploadfiletarget").load(function(){			
				var contents = $(this).contents().get(0);
				var data = $(contents).find('body').text();
				try{
					data = eval("(" + data + ")");
					if (data.isSuccess == true) {
						confirm('操作成功');
						$form = deployFile.$form;
						id = $form.find("input[name='id']").val();			
						$(".sub_item.file"+id).find(".deploy_filename").html($form.find("input[name='fileName']").val());
						
					} else {
						confirm(data.message);
					}
				}catch(e){
					deployFile.waitui.hide();
					alert(e);
				}
			});
	}
}
function confirm(msg)
{
	return ui.confirm('提示', msg).ok('确定').cancel('取消').effect('slide').closable().modal().show();
}
var deployFileHelper={
	// 重新设置署文件所显示的序号
	resetFileIndex:function(){
		$(".deployfiles .index").each(function(i){
			$(this).html(i+1);
		});
	}
}
