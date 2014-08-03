var deployProject = {
	init : function() {
		// 顶部tab页点击切换
		this.private_regist_tab_click_envent();
		// 注册序号按钮的点进击事件
		this.private_regist_index_button_event();
	},

	// 顶部tab页点击切换
	private_regist_tab_click_envent : function() {
		$h_item = $(".h_item");
		$h_item.click(function() {
			$h_item.removeClass("choosed");
			$(this).addClass("choosed");
			$(".c_item").removeClass("choosed");
			id = $(this).attr("id");
			$("#c_" + id).addClass("choosed");
		});
	},

	// 注册序号按钮的点进击事件
	private_regist_index_button_event : function() {
		$(".sub_item .index").live("click", function() {
			$(this).parent().parent().toggleClass("sub_item_auto_height");
		});
	}

}

$(document).ready(function() {
	deployProject.init();
	deployFile.init();
	deployCommand.init();
	deployTask.init();
	fileManage.init();
});