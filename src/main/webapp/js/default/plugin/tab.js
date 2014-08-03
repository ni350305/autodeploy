/**
 * TabView 配置参数
 * 
 * @required rightMenu.js
 * @return
 */
var TabOption = function() {
};
/**
 * TabView 配置参数
 */
TabOption.prototype = {
	container_id : '',
	containerId : 'tab_menu',// 容器ID,
	pageid : 'page',// pageId 页面 pageID
	cid : 'tab_po',// 指定tab的id
	position : top,
	rightMenu : true,
	// tab位置，可为top和bottom，默认为top
	action : function(e, p) {

	}
};
/**
 * Tab Item 配置参数
 * 
 * @return
 */
var TabItemOption = function() {
}
/**
 * Tab Item 配置参数
 */
TabItemOption.prototype = {
	id : 'tab_',// tabId
	title : '',// tab标题
	url : '',// 该tab链接的URL
	content : '',
	isClosed : true
// 该tab是否可以关闭
}
function showItem(arg) {
	alert($(arg).html());
}
/**
 * @param {}
 *            option option 可选参数 containerId tab 容器ID pageid pageId 页面 pageID
 *            cid cid tab ID
 */
function TabView(option) {

	var tab_context = {
		current : null,
		current_index : 0,
		current_page : null
	};
	var op = new TabOption();
	$.extend(op, option);

	// 添加需要的相关的div
	function appendRequiredDivs() {
		$("#" + op.container_id).append("<div id='blank' style='height: 2px'></div>");
		$("#" + op.container_id).append("<div id='tab_menu'></div>");
		$("#" + op.container_id).append("<div id='page'></div>");
		$("#" + op.container_id).append(
				"<input type='hidden' id='hiddentitles' name='hiddentitles' value=''/>");
	}
	appendRequiredDivs();

	var bottom = op.position == "bottom" ? "_bottom" : "";
	this.id = op.cid;
	this.pid = op.pageid;
	this.tabs = null;
	this.tabContainer = null;

	var tabTemplate = '<table class="tab_item"  id="{0}" border="0" cellpadding="0" cellspacing="0"><tr>'
			+ '<td class="tab_item1"></td>'
			+ '<td class="tab_item2 tab_title">{1}</td>'
			+ '<td class="tab_item2"><div class="tab_close">X</div></td>'
			+ '<td class="tab_item3"></td>' + '</tr></table>';

	var tabContainerTemplate = '<div class="benma_ui_tab" id="{0}"><div class="tab_hr"></div></div>';
	var page = '<iframe id="{0}" frameborder="0" width="100%" height="100%" src="{1}"></iframe>';
	var content = '<div id="{0}" width="100%" height="100%" src="{1}"></div>';
	if (op.position == "bottom") {
		tabTemplate = '<table class="tab_item_bottom"  id="{0}" border="0" cellpadding="0" cellspacing="0"><tr>'
				+ '<td class="tab_item1_bottom"></td>'
				+ '<td class="tab_item2_bottom tab_title">{1}</td>'
				+ '<td class="tab_item2_bottom"><div class="tab_close tab_close_bottom"></div></td>'
				+ '<td class="tab_item3_bottom"></td>' + '</tr></table>';
		tabContainerTemplate = '<div class="benma_ui_tab benma_ui_tab_bottom" id="{0}"><div class="tab_hr tab_hr_bottom"></div></div>';
	}
	$("#" + op.containerId).append(tabContainerTemplate.replace("{0}", this.id));
	function initTab(el) {
		var theTab = $(el);
		var tab_item1 = $(theTab).find(".tab_item1" + bottom);
		var tab_item2 = $(theTab).find(".tab_item2" + bottom);
		var tab_item3 = $(theTab).find(".tab_item3" + bottom);
		if (tab_context.current == null || tab_context.current != this) {
			$(theTab).mouseover(function() {
				tab_item1.addClass("tab_item1_mouseover" + bottom);
				tab_item2.addClass("tab_item2_mouseover" + bottom);
				tab_item3.addClass("tab_item3_mouseover" + bottom);
			}).mouseout(function() {
				tab_item1.removeClass("tab_item1_mouseover" + bottom);
				tab_item2.removeClass("tab_item2_mouseover" + bottom);
				tab_item3.removeClass("tab_item3_mouseover" + bottom);
			}).click(
					function() {
						if (tab_context.current != null) {
							$(tab_context.current).find(".tab_item1" + bottom)
									.removeClass("tab_item1_selected" + bottom);
							$(tab_context.current).find(".tab_item2" + bottom)
									.removeClass("tab_item2_selected" + bottom);
							$(tab_context.current).find(".tab_item3" + bottom)
									.removeClass("tab_item3_selected" + bottom);
							$(tab_context.current).find(".tab_close").addClass(
									"tab_close_noselected");
						}
						tab_item1.addClass("tab_item1_selected" + bottom);
						tab_item2.addClass("tab_item2_selected" + bottom);
						tab_item3.addClass("tab_item3_selected" + bottom);
						tab_context.current = this;
						$(tab_context.current).find(".tab_close").removeClass(
								"tab_close_noselected");
						activate($(this).attr("id"), false);
					});
			var tab_close = $(theTab).find(".tab_close").mouseover(function() {
				$(this).addClass("tab_close_mouseover");
			}).mouseout(function() {
				$(this).removeClass("tab_close_mouseover");
			}).click(function() {
				close(theTab.attr("id"));
			});
		}
	}

	function activate(id, isAdd) {
		if (isAdd) {
			$("#" + id).trigger("click");
		}
		if (tab_context.current_page) {
			tab_context.current_page.hide();
		}
		tab_context.current_page = $("#page_" + id);
		tab_context.current_page.show();
		op.action($("#" + id), tab_context.current_page);
	}
	function close(id) {

		var close_page = $("#page_" + id);
		var close_tab = $("#" + id);
		if ($(tab_context.current).attr("id") == close_tab.attr("id")) {
			var next = close_tab.next();
			if (next.attr("id")) {
				activate(next.attr("id"), true);
			} else {
				var pre = close_tab.prev();
				if (pre.attr("id")) {
					activate(pre.attr("id"), true);
				}
			}
		} else {
		}
		// -------------------------------------------
		var isexistval = $("#hiddentitles").val();
		var newisexitval = "";
		var tempthis = "";
		isexistval = isexistval.split(",");
		$(isexistval).each(function() {
			tempthis = this;
			// alert(tempthis);
			if (tempthis != "") {
				var tempindex = tempthis.indexOf(id);
				if (tempindex == -1) {
					newisexitval = newisexitval + tempthis + ",";
				}
			}
		});
		$("#hiddentitles").val(newisexitval);
		// -----------------------------------------
		close_page.find("iframe").remove();
		close_page.remove();
		close_tab.remove();
	}

	// 增加鼠标右键功能
	// add by billy
	function addRightMenu(jqueryObj, isClosed) {
		if (op.rightMenu == true) {
			var menu = ui.menu();
			if (isClosed) {
				menu.add('关闭当前', function() {
					close(jqueryObj.attr("id"));
				});
			}
			menu.add('关闭其他页', function() {

				var nowId = jqueryObj.attr("id");
				$("#" + op.containerId + " .tab_item").each(function() {
					if ($(this).attr("id") != nowId) {
						if ($(this).attr("isClose") == "true") {
							close($(this).attr("id"));
						}
					}
				});

			}).add('关闭所有', function() {
				$("#" + op.containerId + " .tab_item").each(function() {
					if ($(this).attr("isClose") == "true") {
						close($(this).attr("id"));
					}
				});
			});
			// oncontextmenu = function(e){
			jqueryObj[0].oncontextmenu = function(e) {
				e.preventDefault();
				menu.moveTo(e.pageX, e.pageY).show();
			};
		}
	}

	// 初始化显区域的高度
	this.resizePageHeight = function() {
		var bodyHeight = $("#" + op.container_id).height();
		var pageHeight = bodyHeight - $("#blank").outerHeight()
				- $("#" + op.containerId).outerHeight();
		$("#" + op.pageid).height(pageHeight);
	}
	//定时刷 新显区域的高度
	a = this;
	window.setInterval(function() {
		a.resizePageHeight();
	}, 500);
	this.init = function() {
		this.tabContainer = $("#" + this.id);
		// alert(this.id);
		this.tabs = this.tabContainer.find(".tab_item" + bottom);
		this.tabs.each(function() {
			initTab(this);
		});
	}
	this.add = function(option) {
		var op1 = new TabItemOption();
		$.extend(op1, option);
		if (op1.title.length > 10) {
			op1.title = op1.title.substring(0, 10);
		}
		if (op1.title.length < 4) {
			op1.title = "&nbsp;&nbsp;" + op1.title + "&nbsp;";
		}
		var pageHtml = page.replace("{0}", "page_" + op1.id).replace("{1}", op1.url);
		var contentHtml = content.replace("{0}", "page_" + op1.id)
				.replace("{1}", op1.url);
		// content优先显示
		if (op1.content != '') {
			contentHtml = $(contentHtml);
			contentHtml.append(op1.content);
			$("#" + this.pid).append(contentHtml);
		} else {
			$("#" + this.pid).append(pageHtml);
		}

		var html = tabTemplate.replace("{0}", op1.id).replace("{1}", op1.title);
		// alert(html);
		this.tabContainer.append(html);
		// alert($("#" + this.id).html());
		initTab($("#" + op1.id));
		if (!op1.isClosed) {
			// 不能关闭
			$("#" + op1.id).find(".tab_close").hide();
			$("#" + op1.id).attr("isClose", "false");
		} else {
			// 能关闭
			$("#" + op1.id).attr("isClose", "true");
		}
		// this.init();
		this.activate(op1.id);
		// 添加右键菜单
		addRightMenu($("#" + op1.id), op1.isClosed);
		// 初始化显区域的高度
		this.resizePageHeight();
	}
	this.update = function(option) {
		var id = option.id;
		// alert(option.url);
		$("#" + id).find(".tab_title").html(option.title);
		$("#" + id).trigger("click");
		// $("#page_" + id).find("iframe").attr("src", option.url);
		$("#page_" + id).attr("src", option.url);
		// document.getElementById()
	}
	this.activate = function(id) {
		// $("#" + id).trigger("click");
		// activate(id, true);
		$("#" + id).trigger("click");
	}
	this.close = function(id) {
		close(id);
	}
	this.init();
}