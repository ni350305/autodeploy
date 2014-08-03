var serverGroup = {
	init : function(){
		// 初始化服务器组
		this.private_init_server_group_grid();
		// 添加服务器组按钮
		this.private_add_server_group_btn_event();
		// 更新服务器组按钮事件
		this.private_update_server_group_btn_event();
		// 删除服务器
		this.private_del_server_group_btn_event();
	},
	// 初始化服务器组
	private_init_server_group_grid : function(){
		$("#serverGroup").easygrid(
			{
				requestUrl : "/serverGroup/getServerGroups",// 获取数据的，请求url
				toolbar : {
					isShowSearchTool : true,// 是否显示搜索框
					items : [
						{
							html : "<span class='button add_server_group_btn'>添加服务器组</span>"
						} ]
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
							format : function(rowjson,index){
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
							field : "name",
							text : "别名",
							width : 400,
							text_align:"left",
							isCheck : false,
							hidden : false
						},
						{
							field : "comment",
							text : "备注",
							width : 120,
							hidden : false
						},
						{
							field : "opration",
							text : "操作",
							width : 120,
							hidden : false,
							format : function(rowJson){
								return '<div class="button update_server_group_btn" servergroupid="' + rowJson.id
									+ '">修改</div><div class="button del_server_group_btn" servergroupid="' + rowJson.id + '">删除</div>';
							}
						} ]
			});
	},
	// 删除服务器组
	private_del_server_group_btn_event : function(){
		$(".del_server_group_btn").live("click", function(){
			$del_btn = $(this);
			ui.confirm('提示', '确定删除服务器？' + $del_btn.parent().parent().find(".name").html()).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok){
				if (ok) {
					var waitui = ui.dialog('稍等...').modal().show();
					$.ajax({
						url : "/serverGroup/deleteServerGroup/" + $del_btn.attr("servergroupid"),
						cache : false,
						dataType : "json",
						success : function(data){
							if (data.isSuccess == true) {
								confirm("删除成功");
								$del_btn.parent().parent().remove();
								$("#serverGroup").refreshEasygrid();
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
	// 添加服务器组按钮事件
	private_add_server_group_btn_event : function(){
		$("#serverGroup .add_server_group_btn").live("click", function(){
			addServersController.private_add_or_update_servergroup(-1);
		});
	},
	// 更新服务器组按钮事件
	private_update_server_group_btn_event : function(){
		$("#serverGroup .update_server_group_btn").live("click", function(){
			var $row = $(this).parent().parent();
			addServersController.private_add_or_update_servergroup($(this).attr("servergroupid"), $row.find(".name").html(), $row.find(".comment").html());
		});
	}
}
var addServersController = {
	// 添加或更新服务器组方法
	private_add_or_update_servergroup : function(serverGroupId,name,comment){
		var dialogTitle = '添加服务器组 ';
		// 是否是更新服务器组
		var isUpdate = false;
		var localName = "";
		var localComment = "";
		if (serverGroupId != undefined && serverGroupId != -1) {
			isUpdate = true;
			localName = name;
			localComment = comment;
			dialogTitle = "修改服务器组 ";
		}
		var $easygrid = $("<div style=''></div>");
		$easygrid.append('<div class="item"><span class="red">*</span>别名： <input type="text" name="name" style="margin-left: 27px;width:500px"value="' + localName + '">'
			+ '备注： <input type="text" name="comment" style="margin-left: 29px;width:200px"value="' + localComment + '"></div>');
		$easygrid.append('<div style="width:900px;height:300px"><div id="choosedServerGrid" style="float:left;width:400px;height:300px;"><div>已选服务器</div></div>'
			+ '<div id="opration" style="float:left;width:70px;height:300px;"><span class="button addServersToGroupBtn"style="top:150px"> < 添加 </span>'
			+ '<br/><span class="button deleteServersFromGroupBtn"style="top:200px">  删除 > </span></div>'
			+ '<div id="toChoosedServerGrid"style="float:left;width:400px;height:300px;"><div>可选服务器</div></div></div>');
		ui.confirm(dialogTitle, $easygrid).ok('确定').cancel('取消').effect('slide').closable().modal().show(
			function(ok){
				if (ok) {
					var waitui = ui.dialog('稍等...').modal().show();
					//
					if ($easygrid.find("input[name='name']").val() == "") {
						confirm("别名不能为空");
						return false;
					}
					if (isUpdate == true) {
						addServersController.private_update_server_group_ajax(serverGroupId, $easygrid.find(".item input[name='name']").val(), $easygrid.find(
							".item input[name='comment']").val(), waitui);
					} else {
						var checkeds = $easygrid.find("#choosedServerGrid").find(".rows .row .column.servercheck input");
						var len = checkeds.length;
						var serverids = "";
						// 是更新服务器组，用异步添加
						for ( var i = 0; i < len; i++) {
							var $row = $(checkeds[i]).parent().parent();
							var id = $row.find(".id").html();
							serverids = serverids + "serverid=" + id + "&"
						}
						addServersController.private_add_server_group_ajax("", $easygrid.find(".item input[name='name']").val(), $easygrid.find(".item input[name='comment']")
							.val(), serverids, $("#serverGroup"), waitui);
					}
				}
			});
		// 初始化添加窗口表格
		addServersController.private_create_add_server_group_grid($easygrid.find("#choosedServerGrid"), serverGroupId, $easygrid.find("#toChoosedServerGrid"));
		// 从服务器组 添加和删除服务器 按钮事件
		addServersController.private_regist_add_delete_servers_from_group_btn($easygrid, isUpdate, serverGroupId);
	},
	// 从服务器组 添加和删除服务器 按钮事件
	private_regist_add_delete_servers_from_group_btn : function($easygrid,isUpdate,serverGroupId){
		// 添加按钮
		$(".addServersToGroupBtn").click(function(){
			var checkeds = $easygrid.find("#toChoosedServerGrid").find(".rows .row .column.servercheck input:checked");
			checkeds.attr("checked", false);
			var len = checkeds.length;
			{
				var waitui;
				if (isUpdate == true) {
					waitui = ui.dialog('稍等...').modal().show();
				}
				if (isUpdate == true) {
					var serverids = "";
					// 是更新服务器组，用异步添加
					for ( var i = 0; i < len; i++) {
						var $row = $(checkeds[i]).parent().parent();
						var id = $row.find(".id").html();
						serverids = serverids + "serverid=" + id + "&"
					}
					$.ajax({
						url : "/serverGroup/addServersToServerGroup/?" + serverids,
						cache : false,
						data : {
							serverGroupId : serverGroupId
						},
						dataType : "json",
						success : function(data){
							if (data.isSuccess == true) {} else {
								alert(+" 添加失败:" + data.message);
							}
							$easygrid.find("#choosedServerGrid").refreshEasygrid();
							waitui.hide();
						}
					});
				} else {
					for ( var i = 0; i < len; i++) {
						var $row = $(checkeds[i]).parent().parent();
						var id = $row.find(".id").html();
						var ip = $row.find(".ip").html();
						var name = $row.find(".name").html();
						var comment = $row.find(".comment").html();
						if ($easygrid.find("#choosedServerGrid .rows .row .id[title='" + id + "']").length == 0) {
							$easygrid.find("#choosedServerGrid").addEasygridRows([
								{
									id : id,
									ip : ip,
									name : name,
									comment : comment
								} ]);
						}
					}
				}
			}
		});
		// 删除 按钮
		$(".deleteServersFromGroupBtn").click(function(){
			if (isUpdate == true) {
				var waitui = ui.dialog('稍等...').modal().show();
				var checkeds = $easygrid.find("#choosedServerGrid").find(".rows .row .column.servercheck input:checked");
				var len = checkeds.length;
				var serverids = "";
				// 是更新服务器组，用异步添加
				for ( var i = 0; i < len; i++) {
					var $row = $(checkeds[i]).parent().parent();
					var id = $row.find(".id").html();
					serverids = serverids + "serverid=" + id + "&"
				}
				$.ajax({
					url : "/serverGroup/deleteServersFromServerGroup/?" + serverids,
					cache : false,
					data : {
						serverGroupId : serverGroupId
					},
					dataType : "json",
					success : function(data){
						if (data.isSuccess == true) {} else {
							alert(+" 删除失败:" + data.message);
						}
						$easygrid.find("#choosedServerGrid").refreshEasygrid();
						waitui.hide();
					}
				});
			} else {
				$easygrid.find("#choosedServerGrid").find(".rows .row .column.servercheck input:checked").parent().parent().remove();
			}
		});
	},
	// 更新server组的ajax请求
	private_update_server_group_ajax : function(id,name,comment,waitui){
		$.ajax({
			url : "/serverGroup/updateServerGroup/",
			cache : false,
			data : {
				id : id,
				name : name,
				comment : comment,
			},
			dataType : "json",
			success : function(data){
				if (data.isSuccess == true) {
					confirm("更新成功");
					$("#serverGroup").refreshEasygrid();
				} else {
					confirm(data.message);
				}
				waitui.hide();
			}
		});
	},
	// 添加server组的ajax请求
	private_add_server_group_ajax : function(id,name,comment,serverids,$grid,waitui){
		$.ajax({
			url : "/serverGroup/addServerGroup/?" + serverids,
			cache : false,
			data : {
				name : name,
				comment : comment,
			},
			dataType : "json",
			success : function(data){
				if (data.isSuccess == true) {
					confirm("添加成功");
					$grid.refreshEasygrid();
				} else {
					confirm(data.message);
				}
				waitui.hide();
			}
		});
	},
	// 生成添加窗口的grid
	private_create_add_server_group_grid : function($choosedGrid,groupid,$toChooseGrid){
		$choosedGrid.easygrid({
			requestUrl : "/serverGroup/getServersByGroupid/" + groupid,// 获取数据的，请求url
			toolbar : {
				isShowSearchTool : true
			// 是否显示搜索框
			},
			rowHeight : 25,
			// 列定义"id":15,"ip":"123","name":"tomcat服务器","userName":"asdf","authType":0,"password":null,"comment":"asdfasdf111","fileName":null,"authTypeStr":"密码"
			columns : [
					{
						field : "servercheck",
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
						field : "ip",
						text : "ip",
						width : 100,
						isCheck : false,
						hidden : false
					}, {
						field : "name",
						text : "别名",
						width : 100,
						isCheck : false,
						hidden : false
					}, {
						field : "comment",
						text : "备注",
						width : 100,
						hidden : false
					} ]
		});
		$toChooseGrid.easygrid({
			requestUrl : "/server/getServers",// 获取数据的，请求url
			toolbar : {
				isShowSearchTool : true
			// 是否显示搜索框
			},
			rowHeight : 25,
			// 列定义"id":15,"ip":"123","name":"tomcat服务器","userName":"asdf","authType":0,"password":null,"comment":"asdfasdf111","fileName":null,"authTypeStr":"密码"
			columns : [
					{
						field : "index",
						text : "序号",
						width : 40,
						isCheck : false,
						hidden : false,
						format : function(rowjson,index){
							return index;
						}
					}, {
						field : "servercheck",
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
						field : "ip",
						text : "ip",
						width : 100,
						isCheck : false,
						hidden : false
					}, {
						field : "name",
						text : "别名",
						width : 100,
						isCheck : false,
						hidden : false
					}, {
						field : "comment",
						text : "备注",
						width : 100,
						hidden : false
					} ]
		});
	}
}
function confirm(msg){
	ui.confirm('提示', msg).ok('确定').cancel('取消').effect('slide').closable().modal().show();
}
$(document).ready(function(){
	serverGroup.init();
});
