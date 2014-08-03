var deployCommand = {
	init : function()
	{
		// 注册部署命令 添加按钮事件
		this.private_regist_add_command_btn_event();
		// 删除部署命令按钮事件
		this.private_regist_delete_file_btn_event();
		// 更新部署命令按钮事件
		this.private_update_file_btn_event();
	},
	// 注册启动/停止 添加 按钮事件
	private_regist_add_command_btn_event : function()
	{
		$("#addcommand").live("click", function()
		{
			$div = $($("#addcommand_dialog").html());
			ui.confirm('添加命令项', $div).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
			{
				if (ok) {
					waitui = ui.dialog('稍等...').modal().show();
					if ($div.find("input[name='command_name']").val() == '') {
						confirm('请输入 命令别名');
						return false;
					}
					if ($div.find("input[name='command_path']").val() == '') {
						confirm('请输入 命令');
						return false;
					}
					$.ajax({
						url : "/deployproject/addDeployCommand",
						cache : false,
						data : {
							projectId : projectId,
							name : $div.find("input[name='command_name']").val(),
							command : $div.find("input[name='command_path']").val()
						},
						dataType : "json",
						success : function(data)
						{
							if (data.isSuccess) {
								confirm("部署命令添加成功!");
								// window.setTimeout(function()
								// {
								// location.reload(true);
								// }, 500);
								// 后台返回的部署命令对象
								deployCommand = data.object;
								// 获取部署文件模板
								$newcommandplatform = $($(".newcommandplatform").html());
								$newcommandplatform.addClass("command" + deployCommand.id);
								$newcommandplatform.find(".command_name").html(deployCommand.name);
								$newcommandplatform.find(".command_path").html(deployCommand.command);
								$newcommandplatform.find(".del_command_btn").attr("commandid", deployCommand.id);
								$newcommandplatform.find(".update_command_btn").attr("commandid", deployCommand.id);
								$(".deploycommands").prepend($newcommandplatform);
								deployCommandHelper.resetCommandIndex();
							} else {
								confirm(data.message);
							}
							waitui.hide(1500);
						}
					});
				}
			});
		});
	},
	// 删除部署命令按钮事件
	private_regist_delete_file_btn_event : function()
	{
		$(".del_command_btn").live("click", function()
		{
			$del_btn = $(this);

			ui.confirm('提示', '确定删除命令？' + $del_btn.parent().parent().find(".command_name").html()).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
			{
				if (ok) {
					deployFile.waitui = ui.dialog('稍等...').modal().show();
					$.ajax({
						url : "/deployproject/deleteDeployCommand/" + $del_btn.attr("commandid"),
						cache : false,
						dataType : "json",
						success : function(data)
						{
							if (data.isSuccess == true) {
								confirm("删除成功");
								$del_btn.parent().parent().remove();
								deployCommandHelper.resetCommandIndex();
							} else {
								confirm(data.message);
							}
							deployFile.waitui.hide();
						}
					});
				}
			});
		});
	},
	// 更新部署命令按钮事件
	private_update_file_btn_event : function()
	{
		$(".update_command_btn").live("click", function()
		{
			commandId = $(this).attr("commandid");
			$div = $($("#addcommand_dialog").html());
			$div.find("input[name='command_name']").val($(this).parent().parent().find(".command_name").html());
			$div.find("input[name='command_path']").val($(this).parent().parent().find(".command_path").html());
			ui.confirm('修改部署命令', $div).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
			{
				if (ok) {
					deployFile.waitui = ui.dialog('稍等...').modal().show();
					if ($div.find("input[name='command_name']").val() == '') {
						confirm('请输入 部署别名');
						return false;
					}
					if ($div.find("input[name='command_path']").val() == '') {
						confirm('请输入 部署路径');
						return false;
					}
					$.ajax({
						url : "/deployproject/updateDeployCommand",
						cache : false,
						data : {
							projectId : projectId,
							id : commandId,
							name : $div.find("input[name='command_name']").val(),
							command : $div.find("input[name='command_path']").val()
						},
						dataType : "json",
						success : function(data)
						{
							if (data.isSuccess) {
								// alert($div.find("input[name='command_name']").val());
								$(".sub_item.command" + commandId).find(".command_name").html($div.find("input[name='command_name']").val());
								$(".sub_item.command" + commandId).find(".command_path").html($div.find("input[name='command_path']").val());
								confirm("部署命令修改成功!");
							} else {
								confirm(data.message);
							}
							deployFile.waitui.hide(1500);
						}
					});
				}
			});
		});
	}
}
var deployCommandHelper = {
	// 重新设置部署命令所显示的序号
	resetCommandIndex : function()
	{
		$(".deploycommands .index").each(function(i)
		{
			$(this).html(i + 1);
		});
	}
}