var server = {
	init : function () {

		this.private_init_server_grid();
		// 添加,更新服务器按钮
		this.private_add_server_btn_event();
		// 删除服务器
		this.private_del_server_btn_event();
	},
	private_init_server_grid : function () {

		$("#server").easygrid(
				{
					requestUrl : "/server/getServers",// 获取数据的，请求url
					toolbar : {
						isShowSearchTool : true,// 是否显示搜索框
						items : [
							{
								html : "<span class='button add_server_btn'>添加服务器</span>"
							}
						]
					},
					rowHeight : 25,
					// 列定义"id":15,"ip":"123","name":"tomcat服务器","userName":"asdf","authType":0,"password":null,"comment":"asdfasdf111","fileName":null,"authTypeStr":"密码"
					columns : [
							{
								field : "index",
								text : "序号",
								width : 50,
								isCheck : false,
								hidden : false,
								format : function (rowjson , index) {

									return index;
								}
							},
							{
								field : "id",
								text : "id",
								width : 50,
								isCheck : false,
								hidden : true
							},
							{
								field : "ip",
								text : "ip",
								width : 100,
								isCheck : false,
								hidden : false
							},{
								field : "port",
								text : "port",
								width : 50,
								isCheck : false,
								hidden : false
							},
							{
								field : "name",
								text : "别名",
								width : 100,
								isCheck : false,
								hidden : false
							},
							{
								field : "userName",
								text : "登录名",
								width : 60,
								isCheck : false,
								hidden : false
							},
							{
								field : "authType",
								text : "认证方式",
								width : 60,
								isCheck : false,
								hidden : false,
								format : function (rowJson) {

									if (rowJson.authType == 0) {
										return "<span type='0'>密码</span>";
									} else {
										return "<span  type='1'>密钥</span>";
									}
								}
							},
							{
								field : "comment",
								text : "备注",
								width : 100,
								hidden : false
							},
							{
								field : "opration",
								text : "操作",
								width : 120,
								hidden : false,
								format : function (rowJson) {

									return '<div class="button update_server_btn" serverid="' + rowJson.id
											+ '">修改</div><div class="button del_server_btn" serverid="' + rowJson.id
											+ '">删除</div>';
								}
							}
					]
				});
	},
	// 删除服务器
	private_del_server_btn_event : function () {

		$(".del_server_btn").live(
				"click",
				function () {

					$del_btn = $(this);
					ui.confirm('提示', '确定删除服务器？' + $del_btn.parent().parent().find(".name").html()).ok('确定')
							.cancel('取消').effect('slide').closable().modal().show(function (ok) {

								if (ok) {
									var waitui = ui.dialog('稍等...').modal().show();
									$.ajax({
										url : "/server/deleteServer/" + $del_btn.attr("serverid"),
										cache : false,
										dataType : "json",
										success : function (data) {

											if (data.isSuccess == true) {
												confirm("删除成功");
												$del_btn.parent().parent().remove();
												$("#server").refreshEasygrid();
											} else {
												confirm(data.message);
											}
											waitui.hide();
										}
									});
								}
							});
				});
	},
	// 添加,更新 修改服务器按钮
	private_add_server_btn_event : function () {

		var waitui;
		$(".left .update_server_btn").live(
				"click",
				function () {

					var row = $(this).parent().parent();
					var $form = $($("#add_server_dialog").html());

					$form.find("input[name='id']").val($(this).attr("serverid"));
					$form.find("input[name='name']").val(row.find(".name").html());
					$form.find("input[name='ip']").val(row.find(".ip").html());
					$form.find("input[name='port']").val(row.find(".port").html());
					$form.find("input[name='userName']").val(row.find(".userName").html());
					$form.find("input[name='authType'][value='" + row.find(".authType span").attr("type") + "']").attr(
							"checked", "checked");
					$form.find("input[name='comment']").val(row.find(".comment").html());

					ui.confirm('更新服务器', $form).ok('确定').cancel('取消').effect('slide').closable().modal().show(
							function (ok) {

								if (ok) {
									if ($form.find("input[name='name']").val() == "") {
										confirm("别名不能为空");
										return false;
									}
									if ($form.find("input[name='ip']").val() == "") {
										confirm("ip不能为空");
										return false;
									}
									if ($form.find("input[name='port']").val() == "") {
										confirm("端口不能为空");
										return false;
									}
									if ($form.find("input[name='userName']").val() == "") {
										confirm("用户名不能为空");
										return false;
									}
									$form.submit();
									waitui = ui.dialog('稍等...').modal().show();
								}
							});
				});

		$(".left .add_server_btn").click(function () {

			var $form = $($("#add_server_dialog").html());
			ui.confirm('添加服务器', $form).ok('确定').cancel('取消').effect('slide').closable().modal().show(function (ok) {

				if (ok) {
					if ($form.find("input[name='name']").val() == "") {
						confirm("别名不能为空");
						return false;
					}
					if ($form.find("input[name='ip']").val() == "") {
						confirm("ip不能为空");
						return false;
					}
					if ($form.find("input[name='userName']").val() == "") {
						confirm("用户名不能为空");
						return false;
					}
					$form.submit();
					waitui = ui.dialog('稍等...').modal().show();
				}
			});
		});
		$("form .choose_file_btn").live("click", function () {

			$fileLabel = $(this).parent().parent().find("input[name='fileName']");// 文件名标签
			$file = $(this).parent().parent().find("input[type='file']");// 文件标签
			// 选择了文件后
			$file.change(function () {

				var filePath = $file.val();
				if (filePath) {
					$fileLabel.val(filePath);
				}
			});
			$file.trigger("click");
		});
		$("#server_target").load(function () {

			var contents = $(this).contents().get(0);
			var data = $(contents).find('body').text();
			try {
				data = eval("(" + data + ")");
				if (data.isSuccess == true) {
					confirm('操作成功');
					// 后台返回的id
					var id = data.object;
					$("#server").refreshEasygrid();
				} else {
					confirm(data.message);
				}
			} catch (e) {
				waitui.hide();
				alert(e);
			}
		});
	}
}

function confirm (msg) {

	ui.confirm('提示', msg).ok('确定').cancel('取消').effect('slide').closable().modal().show();
}
$(document).ready(function () {

	server.init();
});
