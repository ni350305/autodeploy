var deployTask = {
	$addtaskdialog : "",
	init : function() {
		// 执行部署文件
		this.private_execute_deploy_task();
		// 删除部署文件
		this.private_delete_deploy_task();
		// 修改任务按钮
		this.private_regist_update_task_btn_event();
		// 添加按钮事件
		this.private_regist_add_task_btn_event();
		// 初始化 添加或修改页面的一些事件
		Add_or_update_task.init();
	},
	// 修改任务按钮
	private_regist_update_task_btn_event : function() {
		$(".update_task_btn").live("click", function() {
			var taskId = $(this).attr("taskid");
			$.ajax({
				url : "/deployproject/getDeployTask/" + taskId,
				cache : false,
				dataType : "json",
				success : function(data) {
					if (data.isSuccess) {
						var taskDtoJson = data.object;
						Add_or_update_task.public_add_or_update_task(true, taskDtoJson);
					} else {
						confirm(data.message);
					}
				}
			});
		});
	},
	// 执行部署文件
	private_execute_deploy_task : function() {
		$(".excute").live(
				"click",
				function() {
					var taskid = $(this).attr("taskid");
					ui.confirm('提示', '确定执行任务？' + $(this).parent().parent().find(".task_name").html()).ok('确定').cancel('取消').effect('slide').closable().modal().show(
							function(ok) {
								if (ok) {
									var $executeiframe = $("<iframe style='height:350px;width:800px;overflow-y: auto;' src='/deployproject/executeTask/" + taskid + "?_="
											+ new Date().getTime() + "'></iframe");
									ui.confirm('任务正在执行', $executeiframe).ok('确定').cancel('取消').effect('slide').closable().modal().show();
								}
							});
				});
	},
	// 添加按钮事件
	private_regist_add_task_btn_event : function() {
		$("#addtask").live("click", function() {
			Add_or_update_task.public_add_or_update_task(false);
		});
	},
	// 删除部署文件
	private_delete_deploy_task : function() {
		$(".del_task_btn").live("click", function() {
			$del_btn = $(this);
			ui.confirm('提示', '确定删除任务？' + $del_btn.parent().parent().find(".task_name").html()).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok) {
				if (ok) {
					deployFile.waitui = ui.dialog('稍等...').modal().show();
					$.ajax({
						url : "/deployproject/deleteDeployTask/" + $del_btn.attr("taskid"),
						cache : false,
						dataType : "json",
						success : function(data) {
							if (data.isSuccess == true) {
								confirm("删除成功");
								$del_btn.parent().parent().remove();
								// deployCommandHelper.resetCommandIndex();
							} else {
								confirm(data.message);
							}
							deployFile.waitui.hide();
						}
					});
				}
			});
		});
	}
}
var Add_or_update_task = {
	init : function() {
		// 注册选择部署文件按钮
		this.private_regist_file_action_btn_event();
		// 注册选择命令按钮
		this.private_regist_command_action_btn_event();
		// 上移按钮
		this.private_regist_up_action_btn_event();
		// 详细任务项删除按钮
		this.private_regist_del_action_btn_event();
		// 选择服务器组按钮
		this.private_regis_add_server_group_btn_event();
	},
	// 添加或修改任务
	public_add_or_update_task : function(isUpdate, taskDtoJson) {
		var title = "添加任务";
		var requestUrl = "/deployproject/addDeployTask";
		$addtaskdialog = $($("#addtask_dialog").html());
		if (isUpdate == true) {
			title = "修改任务";
			requestUrl = "/deployproject/updateDeployTask";
			Add_or_update_task.private_set_upate_task_values($addtaskdialog, taskDtoJson);
		}
		// 读取所有部署文件
		Add_or_update_task.private_read_all_deploy_files($addtaskdialog);
		// 读取所有部署命令
		Add_or_update_task.private_read_all_deploy_commands($addtaskdialog);
		ui.confirm(title, $addtaskdialog).ok('确定').cancel('取消').effect('slide').closable().modal().show(
				function(ok) {
					if (ok) {
						var waitui = ui.dialog('稍等...').modal().show();
						var name = $addtaskdialog.find("input[name='taskname']").val();
						var taskdetail = $addtaskdialog.find(".taskdetail p");
						var serverGroupId = $addtaskdialog.find(".servergroup").attr("serverGroupId");
						if (name == "") {
							confirm("任务别名不能为空");
							return false;
						}
						if (taskdetail.length == 0) {
							confirm("任务详情不能为空");
							return false;
						}
						if (serverGroupId == "") {
							confirm("服务器组不能为空");
							return false;
						}
						var isConcurrence = $addtaskdialog.find("input[name='isConcurentDeploy']:radio:checked").val();
						var taskDetails = "";
						var len = taskdetail.length;
						for ( var i = 0; i < len; i++) {
							var detailId = $(taskdetail[i]).attr("pid");
							var deployType = $(taskdetail[i]).attr("deploytype");
							taskDetails = taskDetails + "taskDetails=" + detailId + "!" + deployType + "&"
						}
						var taskId = ""
						if (isUpdate == true) {
							taskId = "id=" + taskDtoJson.id;
						}
						$.ajax({
							url : requestUrl + "?" + taskDetails + taskId,
							cache : false,
							data : {
								projectId : projectId,
								name : name,
								isConcurrence : isConcurrence,
								serverGroupId : serverGroupId
							},
							dataType : "json",
							success : function(data) {
								if (data.isSuccess) {
									var taskDto = data.object;
									//
									var taskHtml = Add_or_update_task.private_get_task_html(taskDto.id, taskDto.name, $addtaskdialog.find(".servergroup").val(), isConcurrence,
											taskDto.deployTaskDetails);
									if (isUpdate == true) {
										$("#c_task .sub_item&." + taskDto.id).replaceWith(taskHtml);
									} else {
										$($("#c_task .sub_item")[0]).after(taskHtml);
									}
									$("#c_task .button.index").each(function(index){
										$(this).html(index+1);
									});
									//
									confirm("操作成功");
								} else {
									confirm(data.message);
								}
								waitui.hide();
							}
						});
					}
				});
	},
	// 修改任务时，设置已存在的值
	private_set_upate_task_values : function($updatetaskdialog, taskDtoJson) {
		$updatetaskdialog.find("input[name='taskname']").val(taskDtoJson.name);
		var $taskDetail = $updatetaskdialog.find(".taskdetail");
		var deployTaskDetailsJson = taskDtoJson.deployTaskDetails;
		var len = deployTaskDetailsJson.length;
		for ( var i = 0; i < len; i++) {
			var detailJson = deployTaskDetailsJson[i];
			$taskDetail.append('<p pid="' + detailJson.id + '" deploytype="' + detailJson.type
					+ '"><span class="button up_action">^</span><span class="button del_action">X</span><span>' + detailJson.name + '</span></p>');
		}
		var $servergroup = $updatetaskdialog.find(".servergroup");
		$servergroup.attr("servergroupid", taskDtoJson.serverGroupId);
		$servergroup.val(taskDtoJson.serverGroup.name);
		$updatetaskdialog.find("input[name='isConcurentDeploy'][value='" + taskDtoJson.isConcurrence + "']").attr("checked", "checked");
	},
	// 获取任务html
	private_get_task_html : function(taskid, taskName, serverGroupName, isConcurrence, taskdetailsJson) {
		var taskHtml = '<div class="sub_item ' + taskid + '">';
		taskHtml = taskHtml + '<div>';
		taskHtml = taskHtml + '<span class="button index">1</span>';
		taskHtml = taskHtml + '</div>';
		taskHtml = taskHtml + '<div class="sub_line_item">';
		taskHtml = taskHtml + '任务名：';
		taskHtml = taskHtml + '<san class="red task_name">' + taskName + '</san>';
		taskHtml = taskHtml + '</div>';
		taskHtml = taskHtml + '<div class="sub_line_item">';
		taskHtml = taskHtml + '<p>任务详情：</p>';
		taskHtml = taskHtml + '<div class="taskcontent">	';
		var len = taskdetailsJson.length;
		for ( var i = 0; i < len; i++) {
			var taskDetail = taskdetailsJson[i];
			if (taskDetail["type"] == 0) {
				taskHtml = taskHtml + '<p>';
				taskHtml = taskHtml + '<span class="red">' + (i + 1) + '、</span>';
				taskHtml = taskHtml + '文件别名：' + taskDetail["name"] + ' <br><span style="padding-left: 25px;">文件名：' + taskDetail["fileName"] + '</span>';
				taskHtml = taskHtml + '<br><span style="padding-left: 25px;">部署路径:' + taskDetail["deployPath"] + '</span>';
				taskHtml = taskHtml + '<br><span style="padding-left: 25px;">部署前是否清空原文件夹：<span class="isEmptyFolder red"';
				var isEmptyFolder = "否";
				if (taskDetail["isEmptyFolder"] == 1) {
					isEmptyFolder = "是";
				}
				taskHtml = taskHtml + ' isEmptyFolder="' + taskDetail["isEmptyFolder"] + '">' + isEmptyFolder + '</span></span>';
				taskHtml = taskHtml + '</p>';
			} else {
				taskHtml = taskHtml + '<p>';
				taskHtml = taskHtml + '<span class="red">' + (i + 1) + '、</span>';
				taskHtml = taskHtml + '命令别名：' + taskDetail["name"] + ' <br><span style="padding-left: 25px;">命令:' + taskDetail["command"] + '</span>';
				taskHtml = taskHtml + '</p>';
			}
		}
		taskHtml = taskHtml + '</div>';
		taskHtml = taskHtml + '</div>';
		taskHtml = taskHtml + '<div class="sub_line_item">';
		taskHtml = taskHtml + '服务器组：<span class="red">' + serverGroupName + '</span>';
		taskHtml = taskHtml + '</div>';
		taskHtml = taskHtml + '<div class="sub_line_item">';
		if (isConcurrence == 0) {
			isConcurrence = false;
		} else {
			isConcurrence = true;
		}
		taskHtml = taskHtml + '是否并行部署：<span class="red">' + isConcurrence + '</span>';
		taskHtml = taskHtml + '</div>	';
		taskHtml = taskHtml + '<div class="sub_edit_item">';
		taskHtml = taskHtml + '<span class="button excute" taskid="' + taskid + '">执行</span><span class="button del_task_btn" taskid="' + taskid
				+ '">删除</span><span class="button update_task_btn" taskid="' + taskid + '">修改</span>';
		taskHtml = taskHtml + '</div>';
		taskHtml = taskHtml + '</div>';
		return taskHtml;
	},
	// 读取所有部署文件
	private_read_all_deploy_files : function($addtaskdialog) {
		$.ajax({
			url : "/deployproject/getAllDeployFiles",
			cache : false,
			data : {
				projectId : projectId
			},
			dataType : "json",
			success : function(data) {
				if (data.isSuccess) {
					$fileList = $addtaskdialog.find(".deployfile .deployfilelist");
					for ( var i = 0; i < data.object.length; i++) {
						deployFileDTO = data.object[i];
						$fileList.append("<p><input type='checkbox' fileid='" + deployFileDTO.id + "' deploytype='0' name='" + deployFileDTO.name + "'/><label>"
								+ deployFileDTO.name + "</label></p>");
					}
				} else {
					confirm(data.message);
				}
			}
		});
	},
	// 读取所有部署命令
	private_read_all_deploy_commands : function($addtaskdialog) {
		$.ajax({
			url : "/deployproject/getAllDeployCommands",
			cache : false,
			data : {
				projectId : projectId
			},
			dataType : "json",
			success : function(data) {
				if (data.isSuccess) {
					$fileList = $addtaskdialog.find(".commands .commandlist");
					for ( var i = 0; i < data.object.length; i++) {
						deployCommandDTO = data.object[i];
						$fileList.append("<p><input type='checkbox' commandid='" + deployCommandDTO.id + "' deploytype='1' name='" + deployCommandDTO.name + "'/><label>"
								+ deployCommandDTO.name + "</label></p>");
					}
				} else {
					confirm(data.message);
				}
			}
		});
	},
	// 注册选择部署文件按钮
	private_regist_file_action_btn_event : function() {
		$(".file_action").live(
				"click",
				function() {
					$taskdetail = $addtaskdialog.find(".taskdetail");
					$inputs = $addtaskdialog.find(".deployfilelist input");
					$inputs.each(function() {
						if (this.checked == true) {
							$input = $(this);
							if ($taskdetail.find("p[pid='" + $input.attr("fileid") + "'][deploytype='" + $input.attr("deploytype") + "']").length == 0) {
								$taskdetail.append("<p pid='" + $input.attr("fileid") + "' deploytype='" + $input.attr("deploytype")
										+ "'><span class='button up_action'>^</span><span class='button del_action'>X</span><span>" + $input.attr("name") + "</span></p>");
							}
						}
					});
					$inputs.attr("checked", false);
				});
	},
	// 注册选择命令按钮
	private_regist_command_action_btn_event : function() {
		$(".command_action").live(
				"click",
				function() {
					$taskdetail = $addtaskdialog.find(".taskdetail");
					$inputs = $addtaskdialog.find(".commandlist input");
					$inputs.each(function() {
						if (this.checked == true) {
							$input = $(this);
							if ($taskdetail.find("p[pid='" + $input.attr("commandid") + "'][deploytype='" + $input.attr("deploytype") + "']").length == 0) {
								$taskdetail.append("<p pid='" + $input.attr("commandid") + "' deploytype='" + $input.attr("deploytype")
										+ "'><span class='button up_action'>^</span><span class='button del_action'>X</span><span>" + $input.attr("name") + "</span></p>");
							}
						}
					});
					$inputs.attr("checked", false);
				});
	},
	// 上移按钮
	private_regist_up_action_btn_event : function() {
		$(".up_action").live("click", function() {
			$p = $(this).parent();
			$p.insertBefore($p.prev());
		});
	},
	// 任务详细项删除按钮
	private_regist_del_action_btn_event : function() {
		$(".del_action").live("click", function() {
			$p = $(this).parent();
			$p.remove();
		});
	},
	// 选择服务器组按钮
	private_regis_add_server_group_btn_event : function() {
		$(".add_sever_group_btn").live("click", function() {
			$addServerGroupBtn = $(this);
			var $easygrid = $("<div style='width:580px;height:300px;'></div>");
			ui.confirm("选择服务器组", $easygrid).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok) {
				if (ok) {
					var checks = $easygrid.find(".rows .row .column.servergroupcheck input:checked");
					if (checks.length == 0) {
						ui.confirm('提示', '请选择服务器组').ok('确定').cancel('取消').effect('slide').closable().modal().show();
						return false;
					}
					if (checks.length > 1) {
						ui.confirm('提示', '目前只能选择一个服务器组').ok('确定').cancel('取消').effect('slide').closable().modal().show();
						return false;
					}
					$addServerGroupBtn.prev().attr("servergroupid", checks.val());
					$addServerGroupBtn.prev().val(checks.parent().nextAll(".name").html());
				}
			});
			$easygrid.easygrid({
				requestUrl : "/serverGroup/getServerGroups",// 获取数据的，请求url
				toolbar : {
					isShowSearchTool : true
				// 是否显示搜索框
				},
				rowHeight : 25,
				// 列定义"id":15,"ip":"123","name":"tomcat服务器","userName":"asdf","authType":0,"password":null,"comment":"asdfasdf111","fileName":null,"authTypeStr":"密码"
				columns : [ {
					field : "index",
					text : "序号",
					width : 50,
					isCheck : false,
					hidden : false,
					format : function(rowjson, index) {
						return index;
					}
				}, {
					field : "servergroupcheck",
					text : "",
					width : 50,
					valueField : "id",// checkbox值取哪一个json字段
					isCheck : true,
					hidden : false
				}, {
					field : "id",
					text : "id",
					width : 50,
					isCheck : false,
					hidden : true
				}, {
					field : "name",
					text : "别名",
					width : 350,
					text_align:"left",
					isCheck : false,
					hidden : false
				}, {
					field : "comment",
					text : "备注",
					width : 100,
					hidden : false
				} ]
			});
		});
	}
}
function confirm(msg) {
	ui.confirm('提示', msg).ok('确定').cancel('取消').effect('slide').closable().modal().show();
}