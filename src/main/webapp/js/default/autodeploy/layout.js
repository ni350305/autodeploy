var layout = {
	init : function() {
		// 定时调整布局
		this.private_interval_reset_layout();
		// 操作面板的一些事件
		// this.private_regist_opration_event();
	},
	// 定时调整布局
	private_interval_reset_layout : function() {
		layout.private_reset_all();
		window.setInterval(function() {
			layout.private_reset_all();
		}, 500);
	},
	private_reset_all : function() {
		this.private_reset_height();
		this.private_reset_width();
		this.private_reset_right_width();
		// this.private_reset_opration_position();
	},
	private_reset_height : function() {
		$(".layout").height($(window).height() - 15);
	},
	private_reset_width : function() {

	},
	private_reset_right_width : function() {
		$(".layout .right").width($(".layout").width() - $(".layout .left").width() - 7);
	}
//	,
	// 操作面板的位置
//	private_reset_opration_position : function() {
//		$(".left .opration").css("top",
//				$(".left").height() / 2 - $(".left .opration").height() / 2 - 5);
//	},
	// 操作面板的一些事件
//	private_regist_opration_event : function() {
//		$opration = $(".left .opration");
//		var left = $opration.css("left");
//		var opacity = $opration.css("opacity");
//		// 鼠标划过“操作”标签事件
//		$(".left .opration .label").hover(function() {
//			$opration.stop();
//			$opration.animate({
//				"left" : 0,
//				"opacity" : 1
//			}, 200);
//		}, function() {
//		});
//		$opration.hover(function() {
//		}, function() {
//			$opration.stop();
//			$opration.animate({
//				"left" : left,
//				"opacity" : opacity
//			}, 200);
//		});
//	}
}
$(document).ready(function() {
	layout.init();
});
