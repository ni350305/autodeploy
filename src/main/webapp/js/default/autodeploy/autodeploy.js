var autodeplay = {
	tab : "",// tab控件
	init : function()
	{
		// 初始化tab
		this.private_init_tab_view();
		// 注册left面板中的项点击事件
		this.private_regist_item_click_event();
	},
	// 初始化tab
	private_init_tab_view : function()
	{
		autodeplay.tab = new TabView({
			container_id : 'right-content',
		});
		this.private_add_tab("welcome", "首页", "<div style='font-size:20px;padding:20px'>欢迎使用  应用自动批量部署平台</div>", "", false);
	},
	// 注册left面板中的项点击事件
	private_regist_item_click_event : function()
	{
		// 已有项目点击
		$(".left .items .item a").live("click", function()
		{
			$this = $(this);
			id = $this.attr("t_id");
			if ($("#t_" + id).length <= 0) {
				autodeplay.private_add_tab("t_" + id, $this.html(), "", $this.attr("href"), true);
			} else {
				autodeplay.tab.activate("t_" + id);
			}
		});
		// 新建项目
		$("#newdeployproject").live(
				"click",
				function()
				{
					var $dialogDiv = $("<div id='dlg" + new Date().getMilliseconds() + "'>" + $("#addproject_dialog").html() + "</div>");
					var newDeployDialog = ui.confirm('新建部署项目', $dialogDiv).ok('确定').cancel('取消').effect('slide').closable().modal().show(
							function(ok)
							{
								if (ok) {
									// 部署项目名
									var value = $dialogDiv.find("input[name='projectname']").val();
									if ($.trim(value) == '') {
										waitui = ui.confirm("提示", "项目名不能为空").modal().show();
									} else {
										waitui = ui.dialog('稍等...').modal().show();
										$.ajax({
											url : "/project/" + encodeURIComponent(value),
											cache : false,
											dataType : "json",
											success : function(data)
											{
												if (data.isSuccess == true) {
													ui.confirm("提示", "添加成功").ok('确定').cancel('取消').modal().show();
													$("<div class='item'><a href='/deployproject/project/" + data.message + "' t_id='" + data.message + "'onclick='return false;'>"
																	+ value + "</a></div>").insertAfter(".layout .left .projectitle");
												} else {
													ui.confirm("提示", data.message).ok('确定').cancel('取消').modal().show();
												}
												waitui.hide();
											},
											error : function()
											{
												alert("异常，请重新提交！");
												waitui.hide();
											}
										});
									}
								}
							});
				});

	},
	// 添加tab
	private_add_tab : function(id, title, content, url, close_able)
	{
		autodeplay.tab.add({
			id : id,
			title : title,
			content : content,
			url : url,
			isClosed : close_able
		});
	}

}
$(document).ready(function()
{
	autodeplay.init();
});