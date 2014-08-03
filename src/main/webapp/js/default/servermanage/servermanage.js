var servermanage = {
	init : function () {

		// tab切换
		this.private_regist_tab_click_event();

	},
	// tab切换
	private_regist_tab_click_event : function () {

		$(".tabheader .tab").click(function () {

			$(".tabheader .tab").removeClass("choosed");
			$(this).addClass("choosed");
			index = $(this).index();
			$(".container>div").hide();
			$(".container>div").eq(index).show();
		});
	}

}
$(document).ready(function () {

	servermanage.init();
});