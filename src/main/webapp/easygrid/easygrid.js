/**
 * result example: { "isSuccess":true, "title":"", "message":"success",
 * "object":{ "pageNo":1, "pageSize":25, "countTotal":true, "result":[{
 * "id":15,"ip":"123","name":"tomcat服务器","userName":"asdf","authType":0,"password":null,"comment":"asdfasdf111","fileName":null,"authTypeStr":"密码" }, {
 * "id":20,"ip":"192.168.1.1","name":"tomcat","userName":"root","authType":0,"password":null,"comment":"11111111","fileName":null,"authTypeStr":"密码"
 * }], "totalItems":15, "totalPages":1, "lastPage":true, "nextPage":1,
 * "firstPage":true, "prePage":1, "offset":0 } }
 * 
 */

(function($) {

	/**
	 * 请求表格数据参数
	 */
	function RequestParam() {

	}
	RequestParam.prototype = {
		quetyString : "",// 搜索词
		pageNo : 1,
		pageSize : 50,
		totalPages : 1
	// 总页数
	}
	/**
	 * 分页数据对象
	 */
	function PageParam() {

	}
	PageParam.prototype = {
		pageNo : 0,// 当前第几页
		pageSize : 0,// 每页数量
		totalItems : 0,// 总行数
		totalPages : 0,// 总页数
		offset : 0,// 开始序号
		resultLength : 0
	// 结果集大小
	}

	/**
	 * 添加行,
	 * 
	 * @param rowsJson
	 *            :[{ }]
	 */
	$.fn.addEasygridRows = function(rowsJson) {

		$this = $(this);
		var $rows = $this.find(".body .rows");
		// 取出option
		var easyGridOption = $this.data("easyGridOption");
		// 填充行
		EasyGridHelper.fillRows($rows, easyGridOption, rowsJson);
	}
	/**
	 * 刷新表格
	 */
	$.fn.refreshEasygrid = function() {

		$this = $(this);
		// 取出requestParam(请求参数)
		var requestParam = $this.data("requestParam");
		requestParam.pageNo = 1;
		// 取出option
		var easyGridOption = $this.data("easyGridOption");
		EasyGridHelper.getDataFromServer($this.find(".body"), $this
				.find(".footer"), easyGridOption, requestParam);
	}
	/**
	 * 注册表格入口方法
	 */
	$.fn.easygrid = function(EasyGridOption) {

		$this = $(this);
		EasyGridHelper.initGrid($this);

		var $toolbar = $this.find(".toolbar");
		var $header = $this.find(".header");
		var $body = $this.find(".body");
		var $footer = $this.find(".footer");

		EasyGridHelper.initToolbar($toolbar, EasyGridOption.toolbar);
		EasyGridHelper.initHeaders($this, $header, EasyGridOption.columns);
		EasyGridHelper.initFooter($footer, new PageParam());

		// 定时更新表格的宽高
		EasyGridHelper.resetWH($this);
		setInterval(function() {

			EasyGridHelper.resetWH($this);
		}, 500);

		var requestParam = new RequestParam();
		// 储存请求对象
		$this.data("requestParam", requestParam);
		$this.data("easyGridOption", EasyGridOption);
		// 先搜索一次
		EasyGridHelper.getDataFromServer($body, $footer, EasyGridOption,
				requestParam);
		// 搜索按钮事件
		EasyGridHelper.regist_search_btn_event($toolbar, $body, $footer,
				EasyGridOption, requestParam);
		// 每页数量变化事件
		EasyGridHelper.regist_page_size_change_event(
				$footer.find(".page_size"), $body, $footer, EasyGridOption,
				requestParam);
		// 上一页，下一页事件，页码,刷新 输入框
		EasyGridHelper.regist_pre_next_page_btn_event(
				$footer.find(".pre_page"), $footer.find(".next_page"), $footer
						.find(".now_page input"),
				$footer.find(".refresh_page"), $body, $footer, EasyGridOption,
				requestParam);
		// header checkbox点击事件
		EasyGridHelper.regist_header_check_box_clieck_event(
				$body.find(".rows"), $header);
	};

	var EasyGridHelper = {
		// 初始化表格
		initGrid : function($container) {

			$container.append("<div class='easygrid'></div>");
			$easygrid = $container.find(".easygrid");
			$easygrid.append("<div class='toolbar'></div>");
			$easygrid
					.append("<div class='body'><div class='wait hidden' style='display: none;'><span class='wait_gif'></span></div><div class='header'></div><div class='rows'></div></div>");
			$easygrid.append("<div class='footer'></div>");
		},

		// 初始化工具条
		initToolbar : function($toolbar, toolbars) {

			try {
				if (toolbars.isShowSearchTool == true) {
					$toolbar
							.append("<div style='float:left;'><input type='text' class='search_input' placeholder='输入'><span class='button search_server_btn'>搜索</span></div>");
				}
				for ( var i in toolbars.items) {
					$toolbar.append("<div style='float:left;'>"
							+ toolbars.items[i].html + "</div>");
				}
			} catch (e) {
				alert(e);
			}
		},

		// 初始化header
		initHeaders : function($containet, $header, columns) {

			if (columns == null || columns == undefined || columns.length == 0) {
				return;
			}
			var headerWidth = 0;
			for ( var i in columns) {
				var column = columns[i];
				var $field = $("<div class='" + column.field + "'>"
						+ column.text + "</div>");
				if (column.hidden != undefined && column.hidden == true) {
					$field.css("display", "none");
				}
				if (column.width != undefined) {
					$field.css("width", column.width);
				}
				// 是否是checkbox类型
				if (column.isCheck != undefined && column.isCheck == true) {
					$field.html("");
					$field
							.html("<input class='easygridcheck' type='checkbox'>");
					$field.addClass("checkfield");
				}
				// 添加字段
				$header.append($field);
				// 添加宽度
				if (column.width == undefined) {
					headerWidth = headerWidth + 100;
				} else {
					headerWidth = headerWidth + column.width;
				}
			}
			headerWidth = headerWidth + columns.length;
			if (headerWidth < $containet.width()) {
				headerWidth = $containet.width();
			}
			$header.width(headerWidth);

		},
		// 初始化footer
		initFooter : function($footer, PageParam) {

			if ($footer.html() == "") {
				$footer
						.append("<div class='page_size_container'><select class='page_size'>"
								+ "<option value='50'>50</option><option value='100'>100</option><option value='200'>200</option><option value='500'>500</option></select></div>");
				$footer
						.append("<div class='total'>总数:<span class='totalItems'>"
								+ PageParam.totalItems
								+ "</span>(<span class='offset'>"
								+ PageParam.offset
								+ "</span>-<span class='endIndex'>"
								+ (PageParam.offset + PageParam.resultLength)
								+ "</span>)</div>");
				$footer.append("<div class='pre_page button'>&lt; 上一页</div>");
				$footer
						.append("<div class='now_page'><input type='text' value='"
								+ PageParam.pageNo
								+ "'> of <span class='totalPages'>"
								+ PageParam.totalPages + "</span></div>");
				$footer.append("<div class='next_page button'>下一页 &gt;</div>");
				$footer.append("<div class='refresh_page button'>刷新</div>");
				$footer.append("<div class='footer_message'></div>");
			} else {
				$footer.find(".totalItems").html(PageParam.totalItems);
				$footer.find(".offset").html(PageParam.offset);
				$footer.find(".endIndex").html(
						PageParam.offset + PageParam.resultLength);
				$footer.find(".totalPages").html(PageParam.totalPages);
				$footer.find(".now_page>input").val(PageParam.pageNo);
			}
		},
		// 设置表格各层次的高宽
		resetWH : function($container) {

			$easygrid = $container.find(".easygrid");
			$easygrid.width($container.innerWidth());
			$easygrid.find(".toolbar").width($easygrid.innerWidth());

			var headerWidth = 0;
			$easygrid.find(".header div:visible").each(function() {

				headerWidth = headerWidth + $(this).width();
			});
			headerWidth = headerWidth
					+ $easygrid.find(".header div:visible").length;
			if (headerWidth < $container.width()) {
				headerWidth = $container.width();
			}
			$easygrid.find(".header").width(headerWidth);
			$easygrid.find(".rows").width(headerWidth);

			$easygrid.find(".footer").width($easygrid.innerWidth());
			$easygrid.height($container.innerHeight());

			var extenalHeight = 0;
			if ($easygrid.find(".header").width() > $easygrid.width()) {
				extenalHeight = 20;
			}
			$easygrid.find(".rows").height(
					$easygrid.innerHeight()
							- $easygrid.find(".toolbar").innerHeight()
							- $easygrid.find(".header").innerHeight()
							- $easygrid.find(".footer").innerHeight()
							- extenalHeight);

		},
		// 获取数据
		getDataFromServer : function($body, $footer, easyGridOption,
				serversRequestParam) {

			$body.find(".wait").show();
			if (serversRequestParam.pageNo <= 0) {
				serversRequestParam.pageNo = 1;
				$body.find(".wait").hide();
				return;
			}
			if (serversRequestParam.pageNo > serversRequestParam.totalPages) {
				serversRequestParam.pageNo = serversRequestParam.totalPages;
				$body.find(".wait").hide();
				return;
			}

			$.ajax({
				url : easyGridOption.requestUrl,
				cache : false,
				data : serversRequestParam,
				dataType : "json",
				success : function(data) {

					if (data.isSuccess == true) {

						// 行对象置空
						var $rows = $body.find(".rows");
						$rows.html("");
						$footer.find(".footer_message").html(data.message);
						var pageParam = new PageParam();

						var page = data.object;
						// 总行数
						if (page.totalItems < 0) {
							page.totalItems = 0;
						}
						pageParam.totalItems = page.totalItems;
						// 总页数
						if (page.totalPages == 0) {
							page.totalPages = 1;
						}
						serversRequestParam.totalPages = page.totalPages;// 总页数
						pageParam.totalPages = page.totalPages;// 总页数
						pageParam.pageNo = page.pageNo;// 当前第几页
						pageParam.pageSize = page.pageSize;// 每页数量
						pageParam.offset = page.offset;// 开始序号
						// 结果集大小
						if (page.result != null) {
							pageParam.resultLength = page.result.length;
						}
						// 初始化footer
						EasyGridHelper.initFooter($footer, pageParam);
						// 填充行
						EasyGridHelper.fillRows($rows, easyGridOption,
								page.result);

					} else {
						$footer.find(".footer_message").html(data.message);
					}
					$body.find(".wait").hide();
				}
			});
		},
		// 填充表格
		fillRows : function($rows, easyGridOption, results) {

			var rowHeight = "25px";
			if (easyGridOption.rowHeight != undefined) {
				rowHeight = easyGridOption.rowHeight + "px";
			}
			// 遍历结果集
			for ( var i in results) {
				var result = results[i];
				var $row = $("<div class='row' style='height:" + rowHeight
						+ "'></div>");
				// 遍历定义的字段
				for ( var j in easyGridOption.columns) {
					var column = easyGridOption.columns[j];
					var fieldValue = result[column.field];
					if (fieldValue == undefined) {
						fieldValue = "";
					}
					var $column = $("<div class='column " + column.field
							+ "' title='" + fieldValue + "'>" + fieldValue
							+ "</div>")
					// 高
					$column.height(rowHeight);
					// 宽
					if (column.width != undefined) {
						$column.width(column.width);
					}
					// 靠左，靠右，或者靠中,默认靠中
					if (column.text_align != undefined) {
						$column.css("text-align", column.text_align)
					}
					// 是否隐藏
					if (column.hidden != undefined && column.hidden == true) {
						$column.hide();
					}

					// 是否是checkbox类型
					if (column.isCheck != undefined && column.isCheck == true) {
						$column.html("");
						var value = "";
						if (column.valueField != undefined
								&& column.valueField != "") {
							value = result[column.valueField];
						}
						$column
								.append($("<input class='easygridcheck'  type='checkbox' value='"
										+ value + "'>"))
					} else {
						// 格式化
						if (column.format != undefined) {
							var str = column.format(result, $rows
									.children(".row").length + 1);
							$column.html(str);
							$column.attr("title", "");
						}
					}

					$row.append($column);
				}
				$row.width($rows.prev(".header").width());
				$rows.width($rows.prev(".header").width());
				$rows.append($row);
			}
			// 偶数行着色
			$rows.find(".row").removeClass("rowodd");
			$rows.find(".row:odd").addClass("rowodd");

		},
		// 注册搜索按钮事件
		regist_search_btn_event : function($toolbar, $body, $footer,
				easyGridOption, requestParam) {

			$toolbar.find(".search_server_btn").click(
					function() {

						var s_val = encodeURIComponent($toolbar.find(
								".search_input").val());
						if (s_val != requestParam.quetyString) {
							requestParam.quetyString = s_val;
							EasyGridHelper.getDataFromServer($body, $footer,
									easyGridOption, requestParam);
						}
					});
		},
		// 注册每页数量变化
		regist_page_size_change_event : function($page_size, $body, $footer,
				easyGridOption, requestParam) {

			// alert($page_size.attr("class"));
			$page_size.change(function() {

				requestParam.pageSize = $(this).val();
				EasyGridHelper.getDataFromServer($body, $footer,
						easyGridOption, requestParam);
			});
		},
		// 上一页，下一页, 页码回车，刷新按钮，事件
		regist_pre_next_page_btn_event : function($pre_btn, $next_btn,
				$now_page, $refresh_page, $body, $footer, easyGridOption,
				requestParam) {

			$pre_btn.click(function() {

				requestParam.pageNo = requestParam.pageNo - 1;
				EasyGridHelper.getDataFromServer($body, $footer,
						easyGridOption, requestParam);
			});
			$next_btn.click(function() {

				requestParam.pageNo = requestParam.pageNo + 1;
				EasyGridHelper.getDataFromServer($body, $footer,
						easyGridOption, requestParam);
			});
			$now_page.bind('keypress', function(event) {

				if (event.keyCode == "13") {
					var pageNo = $(this).val();
					if ((/^\d*\d$/.test(pageNo))) {
						if (pageNo > requestParam.totalPages) {
							$(this).val(1);
							return;
						}
						requestParam.pageNo = pageNo;
						EasyGridHelper.getDataFromServer($body, $footer,
								easyGridOption, requestParam);
					} else {
						alert("请输入正确的页码！");
						return false;
					}
				}
			});
			$refresh_page.click(function() {

				EasyGridHelper.getDataFromServer($body, $footer,
						easyGridOption, requestParam);
			});
		},
		// header 中的checkbox被选中时的事件
		regist_header_check_box_clieck_event : function($rows, $header) {

			$header
					.find(".checkfield")
					.click(
							function() {

								var index = $(this).index();
								var checked = $(this).children("input").attr(
										"checked");
								if (checked == undefined) {
									checked = false;
								} else {
									checked = true;
								}
								$rows
										.find(".row")
										.each(
												function() {

													$row = $(this);
													$row.children("div").eq(
															index).children(
															"input").attr(
															"checked", checked);
												});
							});
		}
	}

})(jQuery);
// $("#grid")
// .easygrid(
// {
// requestUrl : "/server/getServers",// 获取数据的，请求url
// toolbar : {
// isShowSearchTool : true,// 是否显示搜索框
// items : [
// {
// html : "<span class='button add_server_btn'>添加服务器</span>"
// }
// ]
// },
// rowHeight : 25,
// //
// 列定义"id":15,"ip":"123","name":"tomcat服务器","userName":"asdf","authType":0,"password":null,"comment":"asdfasdf111","fileName":null,"authTypeStr":"密码"
// columns : [
// {
// field : "index",
// text : "序号",
// width : 50,
// isCheck : false,
// hidden : false,
// format : function (rowjson , index) {
//
// return index;
// }
// },
// {
// field : "servercheck",
// text : "",
// width : 50,
// valueField : "id",// checkbox值取哪一个json字段
// isCheck : true,
// hidden : false
// },
// {
// field : "id",
// text : "id",
// width : 50,
// text_align:"left",
// isCheck : false,
// hidden : false
// },
// {
// field : "ip",
// text : "ip",
// width : 100,
// isCheck : false,
// hidden : false
// },
// {
// field : "name",
// text : "name",
// width : 100,
// isCheck : false,
// hidden : false
// },
// {
// field : "userName",
// text : "用户名",
// width : 100,
// isCheck : false,
// hidden : false
// },
// {
// field : "authType",
// text : "认证方式",
// width : 100,
// isCheck : false,
// hidden : false,
// format : function (rowJson) {
//
// if (rowJson.authType == 0) {
// return "密码";
// } else {
// return "密钥";
// }
// }
// },
// {
// field : "comment",
// text : "备注",
// width : 100,
// hidden : false
// },
// {
// field : "opration",
// text : "操作",
// width : 120,
// hidden : false,
// format : function (rowJson) {
//
// return '<div class="opration"><div class="button update_server_btn"
// serverid="22">修改</div><div class="button del_server_btn"
// serverid="22">删除</div></div>';
// }
// }
// ]
// });
// $(".button").click(function () {
//
// $("#grid").addEasygridRows([
// {
// "id" : 2,
// "ip" : "192.168.1.100",
// "name" : "服务器100",
// "userName" : "root",
// "authType" : 1,
// "password" : null,
// "comment" : "",
// "fileName" : null,
// "authTypeStr" : "密钥"
// }, {
// "id" : 1,
// "ip" : "192.168.1.103",
// "name" : "服务器103",
// "userName" : "root",
// "authType" : 0,
// "password" : null,
// "comment" : "",
// "fileName" : null,
// "authTypeStr" : "密码"
// }, {
// "id" : 19,
// "ip" : "192.168.1.109",
// "name" : "tomcat服务器",
// "userName" : "jgthjughuj",
// "authType" : 1,
// "password" : null,
// "comment" : "11111111",
// "fileName" : null,
// "authTypeStr" : "密钥"
// }, {
// "id" : 25,
// "ip" : "192.168.1.110",
// "name" : "tomcat服务器",
// "userName" : "root",
// "authType" : 1,
// "password" : null,
// "comment" : "",
// "fileName" : null,
// "authTypeStr" : "密钥"
// }
// ]);
//
// })

/**
 * <div class="container" id="container"> <div class="easygrid"> <div
 * class="toolbar" style="width: 630px;"> <div style="float: left;"> <input
 * type="text" class="search_input" placeholder="输入 别名\ip"><span class="button
 * search_server_btn">搜索</span> </div> <div style="float: left;"> <span
 * class="button add_server_btn">添加服务器</span> </div> </div>
 * 
 * <div class="body" style="position: relative"> <div class="header"> <div
 * class="name">别名</div> <div class="ip">ip</div> <div class="user">登录名</div>
 * <div class="authType">验证方式</div> <div class="comment">备注</div> <div
 * class="opration">操作</div> </div> <div class="wait hidden" style="display:
 * none;"> <span class="wait_gif"></span> </div> <div class="rows">
 * 
 * <div class="row"> <div class="column name">tomcat</div> <div class="column
 * ip">192.168.1.255</div> <div class="column user">root</div> <div
 * class="column authType" authtype="0">密码</div> <div class="column
 * comment">11111111</div> </div> <div class="row"> <div class="column
 * name">tomcat服务器</div> <div class="column ip">192.168.1.254</div> <div
 * class="column user">root</div> <div class="column authType" authtype="0">密码</div>
 * <div class="column comment">11111111</div> </div> <div class="row"> <div
 * class="column name">tomcat服务器</div> <div class="column ip">192.168.1.254</div>
 * <div class="column user">root</div> <div class="column authType"
 * authtype="0">密码</div> <div class="column comment">11111111</div> </div>
 * <div class="row"> <div class="column name">tomcat服务器</div> <div
 * class="column ip">192.168.1.254</div> <div class="column user">root</div>
 * <div class="column authType" authtype="0">密码</div> <div class="column
 * comment">11111111</div> </div> <div class="row"> <div class="column
 * name">tomcat服务器</div> <div class="column ip">192.168.1.254</div> <div
 * class="column user">root</div> <div class="column authType" authtype="0">密码</div>
 * <div class="column comment">11111111</div> </div> <div class="row"> <div
 * class="column name">tomcat服务器</div> <div class="column ip">192.168.1.254</div>
 * <div class="column user">root</div> <div class="column authType"
 * authtype="0">密码</div> <div class="column comment">11111111</div> </div>
 * <div class="row"> <div class="column name">tomcat服务器</div> <div
 * class="column ip">192.168.1.254</div> <div class="column user">root</div>
 * <div class="column authType" authtype="0">密码</div> <div class="column
 * comment">11111111</div> </div> <div class="row"> <div class="column
 * name">tomcat服务器</div> <div class="column ip">192.168.1.254</div> <div
 * class="column user">root</div> <div class="column authType" authtype="0">密码</div>
 * <div class="column comment">11111111</div> </div> <div class="row"> <div
 * class="column name">tomcat服务器</div> <div class="column ip">192.168.1.254</div>
 * <div class="column user">root</div> <div class="column authType"
 * authtype="0">密码</div> <div class="column comment">11111111</div> </div>
 * <div class="row"> <div class="column name">tomcat服务器</div> <div
 * class="column ip">192.168.1.254</div> <div class="column user">root</div>
 * <div class="column authType" authtype="0">密码</div> <div class="column
 * comment">11111111</div> </div> <div class="row"> <div class="column
 * name">tomcat服务器</div> <div class="column ip">192.168.1.254</div> <div
 * class="column user">root</div> <div class="column authType" authtype="0">密码</div>
 * <div class="column comment">11111111</div> </div> <div class="row"> <div
 * class="column name">tomcat服务器</div> <div class="column ip">192.168.1.254</div>
 * <div class="column user">root</div> <div class="column authType"
 * authtype="0">密码</div> <div class="column comment">11111111</div> </div>
 * <div class="row"> <div class="column name">tomcat服务器</div> <div
 * class="column ip">192.168.1.254</div> <div class="column user">root</div>
 * <div class="column authType" authtype="0">密码</div> <div class="column
 * comment">11111111</div> </div> <div class="row"> <div class="column
 * name">tomcat服务器</div> <div class="column ip">192.168.1.254</div> <div
 * class="column user">root</div> <div class="column authType" authtype="0">密码</div>
 * <div class="column comment">11111111</div> </div> </div> </div> <div
 * class="footer"> <div class="page_size_container"> <select class="page_size"><option
 * value="25">25</option> <option value="50">50</option> <option
 * value="100">100</option></select> </div> <div class="total">总数:15(0-15)</div>
 * <div class="pre_page button">&lt; 上一页</div> <div class="now_page"> <input
 * type="text" style="width: 20px"> of 1 </div> <div class="next_page
 * button">下一页 &gt;</div> <div class="footer_message"></div> </div> </div>
 * </div>
 */
