var fileManage = {
	$rMenu : "",
	$filetree : "",
	deployFileId : "",
	init : function() {
		this.$rMenu = $("#rMenu");
		// 管理文件按钮
		this.private_regist_manage_file_btn_event();
		// 右键菜单点击的相关事件
		this.private_regist_right_menu_click_event();
		// 删除选中的文件
		this.private_delete_choosed_files();
	},
	// 管理文件按钮
	private_regist_manage_file_btn_event : function() {
		// 管理文件按钮
		$(".manage_file_btn").live("click", function() {
			// 设置文件id
			fileManage.deployFileId = $(this).attr("fileid");
			var setting = {
				check : {
					enable : true
				},
				callback : {
					onRightClick : OnRightClick
				},
				async : {
					enable : true,
					url : "/filemanage/getfiles",
					autoParam : [ "path" ],
					otherParam : {
						"projectId" : projectId,
						"deployFileId" : fileManage.deployFileId
					}
				}
			};
			function filter(treeId, parentNode, childNodes) {
				if (!childNodes)
					return null;
				for ( var i = 0, l = childNodes.length; i < l; i++) {
					childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
				}
				return childNodes;
			}
			var $filemanagedialogdiv = $("<div><div style='height: 30px;line-height: 30px;''><span class='button del_choosed_files'>删除选中文件(目前只能批量删除选中的文件)</span></div></div>");
			$filetree = $("<ul id='fileTree' class='ztree' style='height: 350px;width:300px;overflow-y: auto;'></ul>")
			var $filetreepanel = $filetree;
			$filemanagedialogdiv.prepend($filetree);
			$filemanagedialogdiv.append(fileManage.$rMenu);
			var fileManageFile = ui.confirm('管理文件', $filemanagedialogdiv).hidefooter().effect('slide').closable().modal().show(function(ok) {
				$filetreepanel.remove();
			});
			$.fn.zTree.init($filetree, setting);
			$filetree = $.fn.zTree.getZTreeObj("fileTree");
		});
		// 右键菜单
		function OnRightClick(event, treeId, treeNode) {
			if (!treeNode && event.target.tagName.toLowerCase() != "button" && $(event.target).parents("a").length == 0) {
				$filetree.cancelSelectedNode();
				// fileManage.private_show_right_menu(event.clientX,
				// event.clientY);
			} else if (treeNode && !treeNode.noR) {
				$filetree.selectNode(treeNode);
				fileManage.private_show_right_menu(treeNode, event.clientX, event.clientY);
			}
		}
	},
	// 显示右键菜单
	private_show_right_menu : function(treeNode, x, y) {
		var $thisMenu = fileManage.$rMenu;
		if (treeNode.isParent == false) {
			$thisMenu.find("#m_update_file").hide();
			$thisMenu.find("#m_new_folder").hide();
			$thisMenu.find("#m_update_folder").hide();
			$thisMenu.find("#m_download").show();
		} else {
			$thisMenu.find("#m_update_file").show();
			$thisMenu.find("#m_new_folder").show();
			$thisMenu.find("#m_update_folder").show();
			$thisMenu.find("#m_download").hide();
		}
		if (treeNode.path == "/") {
			$thisMenu.find("#m_rename").hide();
		} else {
			$thisMenu.find("#m_rename").show();
		}
		$thisMenu.css({
			"top" : y + "px",
			"left" : x + "px"
		});
		$thisMenu.show();
		$("body").bind("mousedown", onBodyMouseDown);
		// body点击隐藏右键
		function onBodyMouseDown(event) {
			if (!(event.target.id == "rMenu" || $(event.target).parents("#rMenu").length > 0)) {
				$thisMenu.hide();
			}
		}
	},
	// 隐藏右键菜单
	private_hide_right_menu : function() {
		if (fileManage.$rMenu)
			fileManage.$rMenu.css({
				"display" : "none"
			});
		$("body").unbind("mousedown");
	},
	// 右键 菜单相关
	private_regist_right_menu_click_event : function() {
		// 右键菜单中的项选中事件
		$("#rMenu li").live("click", function() {
			var $li = $(this);
			// 删除
			if ($li.attr("id") == "m_del") {
				del();
			}
			// 重命名
			if ($li.attr("id") == "m_rename") {
				rename();
			}
			// 上传文件
			if ($li.attr("id") == "m_update_file") {
				upload();
			}
			// 新建文件夹
			if ($li.attr("id") == "m_new_folder") {
				newfolder();
			}
			// 新建文件夹
			if ($li.attr("id") == "m_download") {
				download();
			}
			// 上传文件夹
			if ($li.attr("id") == "m_update_folder") {
				uploadFolder();
			}
			fileManage.private_hide_right_menu();
		});
		{
			var folderUploadSelectNode;
			// 上传文件夹
			function uploadFolder() {
				var stopUploadFolder = false;
				var $uploaddiv = $("<div><div id='uploadfolderprocess_dialog'style='height: 300px;width:380px;overflow-y: auto;position:relative' ><div id='uploadfolderprocess'></div></div>"
						+ "<div><button class='button' style='float: right; height: 30px; ' id='btnStop' onclick=''>取消上传</button>"
						+ "<button class='button' style='float: right; height: 30px; ' id='btnChooseFolder' onclick=''>选择文件夹</button>"
						+ "<button class='button' style='float: right; height: 30px; ' id='uploadedCount' onclick=''>0/0</button>"
						+ "<input type='file' name='file_input[]' id='file_input' webkitdirectory='' directory='' multiple mozdirectory='' class='hidden' >" + "</div></div>");
				var $uploadFolderUi = ui.confirm('上传文件夹 ', $uploaddiv).hidefooter().effect('slide').closable().modal().show(function(ok) {
				});
				// 取消按钮
				$uploaddiv.find("#btnStop").click(function() {
					stopUploadFolder = true;
				});
				// 绑定选择文件夹按钮
				$uploaddiv.find("#btnChooseFolder").click(function() {
					$("#file_input").trigger("click");
				});
				// 绑定文件夹选择变化事件
				var isUploadWork = false;// 是否正在执行上传操作
				$uploaddiv.find("#file_input").bind("change", function(e) {
					folderUploadSelectNode = $filetree.getSelectedNodes()[0];
					var event = e || window.event;
					var files = event.target.files || event.dataTransfer.files;
					// for ( var i = 0, file; i < files.length; i++) {
					// file = files[i]
					// if (file.name != ".") {
					// ajaxUpload(file);
					// }
					// }
					var filesLength = files.length;
					var i = 0, file;
					var k = 0;// 上传的文件个数
					// 定时上传，50ms，扫描一次
					var intervalId = setInterval(function() {
						if (isUploadWork == false) {
							isUploadWork = true;
							if (i < files.length) {
								file = files[i]
								if (file.name != ".") {
									ajaxUpload(file);
									k++;
								} else {
									isUploadWork = false;
								}
							}
							i++;
							$uploaddiv.find("#uploadedCount").html(i + "/" + filesLength);
							$uploaddiv.find("#uploadfolderprocess_dialog").scrollTop(k * 53);
						}
						if (i == filesLength || stopUploadFolder == true) {
							window.clearInterval(intervalId);
							// 刷新树
							$filetree.reAsyncChildNodes(folderUploadSelectNode, "refresh");
						}
					}, 50);

				});
				function ajaxUpload(file) {
					var formData = new FormData();
					formData.append("projectId", projectId);
					formData.append("deployFileId", fileManage.deployFileId);
					formData.append("path", encodeURIComponent(folderUploadSelectNode.path) + "/" + file.webkitRelativePath.substring(0, file.webkitRelativePath.lastIndexOf("/")));
					formData.append("filename", file);
					var $fileHtml = $('<div class="progressWrapper" id="SWFUpload_1_0" style="opacity: 1;"><div class="progressContainer">'
							+ '<a class="progressCancel" href="#" style="visibility: visible;"> </a>' + '<div class="progressName">' + file.webkitRelativePath + '</div>'
							+ '<div class="progressBarStatus">正在上传。。。</div>' + '<div class="progressBarInProgress" style="width: 50%;"></div>' + '</div></div>');
					$uploaddiv.find("#uploadfolderprocess").append($fileHtml);
					$.ajax({
						// async : false,
						type : 'POST',
						url : '/filemanage/uploadFile/',
						data : formData,
						/**
						 * 必须false才会自动加上正确的Content-Type
						 */
						contentType : false,
						/**
						 * 必须false才会避开jQuery对 formdata 的默认处理 XMLHttpRequest会对
						 * formdata 进行正确的处理
						 */
						processData : false,
						success : function(data) {
							if (data.isSuccess == true) {
								$fileHtml.find(".progressBarStatus").html("上传完成。");
								$fileHtml.find(".progressBarInProgress").css({
									"width" : "100%"
								});
							} else {
								$fileHtml.find(".progressBarStatus").html("上传失败:" + data.message);
								$fileHtml.find(".progressBarInProgress").css({
									"width" : "0%"
								});
							}
							isUploadWork = false;
						}
					});
				}
			}
		}
		{
			// 下载
			function download() {
				selectNode = $filetree.getSelectedNodes()[0];
				window.open("/filemanage/downloadFile?projectId=" + projectId + "&deployFileId=" + fileManage.deployFileId + "&path=" + encodeURIComponent(selectNode.path));
			}
		}
		{
			// 上传文件
			var $uploaddiv;
			var selectNode;
			function upload() {
				selectNode = $filetree.getSelectedNodes()[0];
				$uploaddiv = $("<div><div id='uploadprocess_dialog'style='height: 300px;width:380px;overflow-y: auto;position:relative' ><div id='uploadprocess'></div></div>"
						+ "<div><button class='button' style='float: right; height: 30px; width: 70px;' id='btnCancel1' onclick='cancelQueue(upload1);'>取消上传</button>"
						+ "<span style='float: right; height: 30px; width: 70px;margin: 5px;'><button style='float: right; height: 30px; width: 70px;' id='update_file_item'>选择文件</button></span></div></div>");
				ui.confirm('上传文件 ', $uploaddiv).hidefooter().effect('slide').closable().modal().show(function(ok) {
				});
				initSWFUpload();
			}
			// 初始化SWFUpload
			function initSWFUpload() {
				// 在window的载入时初始化swfupload对象
				upload1 = new SWFUpload({
					// 提交路径
					// 窗体的 打开与url无关
					upload_url : "/filemanage/uploadFile",
					// 向后台传递额外的参数
					// 提交到服务器的参数信息，这样就添加了一个param参数，值是uploadParams在服务器端用request.getParameter(“param”)就可以拿到值
					post_params : {
						"path" : encodeURIComponent(selectNode.path),
						"projectId" : projectId,
						"deployFileId" : fileManage.deployFileId
					},
					// 上传文件的名称
					file_post_name : "filename",
					file_size_limit : "102400", // 100MB
					file_types : "*.*",
					file_types_description : "All Files",
					file_upload_limit : "200",
					file_queue_limit : "0",
					// 事件处理
					file_dialog_start_handler : fileDialogStart,
					file_queued_handler : fileQueued,
					file_queue_error_handler : fileQueueError,
					file_dialog_complete_handler : fileDialogComplete,
					upload_start_handler : uploadStart,
					upload_progress_handler : uploadProgress,
					upload_error_handler : uploadError,
					upload_success_handler : upload_success_handler,
					upload_complete_handler : uploadComplete,
					// 按钮的处理
					button_image_url : "/images/default/plugin/swfupload/btn.png",
					// button_text : "上传文件",
					// button_text_style : "font: 12px/1.14 '微软雅黑', '宋体', Arial,
					// sans-serif, Verdana, Tahoma;",
					button_placeholder_id : "update_file_item",
					button_width : 70,
					button_height : 31,
					// Flash Settings
					flash_url : "/js/default/plugin/swfupload/swfupload.swf",
					custom_settings : {
						progressTarget : "uploadprocess",
						cancelButtonId : "btnCancel1"
					},
					// Debug Settings
					debug : false
				});
				k = 1;
			}
			// 文件上传成功
			var k = 1;// 上传成功的文件数
			var tempk = 1;
			var intervalId = "";
			function upload_success_handler(file, serverData, responseReceived) {
				// alert(eval("("+serverData+")").object);
				var progress = new FileProgress(file, this.customSettings.progressTarget);
				progress.setStatus("上传完成。");
				$("#uploadprocess_dialog").scrollTop(k * 53);
				k++;
				if (intervalId == "") {
					intervalId = window.setInterval(showalert, 2000);
				}
			}
			function showalert() {
				if (tempk == k) {
					// 刷新树，销毁定时器
					$filetree.reAsyncChildNodes(selectNode, "refresh");
					window.clearInterval(intervalId);
					intervalId = "";
				} else {
					tempk = k;
				}
			}
		}
		// 新建文件夹
		function newfolder() {
			var nodes = $filetree.getSelectedNodes();
			var len = nodes.length;
			if (len != 0) {
				var $newfolderInput = $("<input name='rename' style='width:200px'>");
				ui.confirm('输入文件夹名', $newfolderInput).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok) {
					if (ok) {
						if ($newfolderInput.val() == '') {
							confirm("文件名不能为空");
							return false;
						} else {
							var waitui = ui.dialog('稍等...').modal().show();
							$.ajax({
								url : "/filemanage/createFolder/",
								data : {
									"path" : encodeURIComponent(nodes[0].path),
									"projectId" : projectId,
									"folderName" : encodeURIComponent($newfolderInput.val()),
									"deployFileId" : fileManage.deployFileId
								},
								cache : false,
								dataType : "json",
								success : function(data) {
									if (data.isSuccess == true) {
										$filetree.reAsyncChildNodes(nodes[0], "refresh");
										waitui.hide();
									} else {
										confirm(data.message);
									}
								}
							});
						}
					}
				});
			}
		}
		// 重命名
		function rename() {
			var nodes = $filetree.getSelectedNodes();
			var len = nodes.length;
			if (len != 0) {
				var $renameInput = $("<input name='rename' style='width:200px'>");
				$renameInput.val(nodes[0].name);
				ui.confirm('输入新的名称', $renameInput).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok) {
					if (ok) {
						if ($renameInput.val() == '') {
							confirm("文件名不能为空");
							return false;
						} else {
							var waitui = ui.dialog('稍等...').modal().show();
							$.ajax({
								url : "/filemanage/renamefile/",
								data : {
									"path" : encodeURIComponent(nodes[0].path),
									"projectId" : projectId,
									"newName" : encodeURIComponent($renameInput.val()),
									"deployFileId" : fileManage.deployFileId
								},
								cache : false,
								dataType : "json",
								success : function(data) {
									if (data.isSuccess == true) {
										nodes[0].name = $renameInput.val();
										var path = nodes[0].path;
										nodes[0].path = path.substring(0, path.lastIndexOf("/") + 1) + $renameInput.val();
										$filetree.updateNode(nodes[0]);
										$filetree.reAsyncChildNodes(nodes[0], "refresh");
										waitui.hide();
									} else {
										confirm(data.message);
									}
								}
							});
							// alert($renameInput.val());
						}
					}
				});
			}
		}
		// 删除处理
		function del() {
			var nodes = $filetree.getSelectedNodes();
			var len = nodes.length;
			if (len != 0) {
				ui.confirm('提示', '确认删除?' + nodes[0].name).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok) {
					if (ok) {
						var waitui = ui.dialog('稍等...').modal().show();
						$.ajax({
							url : "/filemanage/delfiles/",
							data : {
								"path" : encodeURIComponent(nodes[0].path),
								"projectId" : projectId,
								"deployFileId" : fileManage.deployFileId
							},
							cache : false,
							dataType : "json",
							success : function(data) {
								if (data.isSuccess == true) {
									var parentNode = nodes[0].getParentNode();
									if (nodes[0].path == "/") {
										$filetree.removeChildNodes(nodes[0]);
										parentNode = nodes[0];
									} else {
										$filetree.removeNode(nodes[0]);
									}
									parentNode.isParent = true;
									$filetree.updateNode(parentNode);
									waitui.hide();
								} else {
									confirm('异常：' + data.message);
								}
							}
						});
					}
				});
			}
		}
		// // 上传文件
		// var waitui;
		// var selectNode;
		// function upload(){
		// var nodes = $filetree.getSelectedNodes();
		// var len = nodes.length;
		// if (len != 0) {
		// selectNode = nodes[0];
		// var $form = $( $("#file_manage_upload_dialog").html());
		// mainui = ui.confirm('上传文件',
		// $form).ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok)
		// {
		// if (ok) {
		// waitui = ui.dialog('稍等...').modal().show();
		// if ($form.find("input[name='filename']").val() == '' ||
		// $form.find("input[name='fileName']").val() == '') {
		// confirm('请选择文件');
		// return false;
		// }
		// $form.find("input[name='path']").val(nodes[0].path);
		// $form.find("input[name='deployFileId']").val(fileManage.deployFileId);
		// $form.submit();
		// }
		// });
		// }
		// }
		// $(".file_manage_choose_file_btn").live("click",function(){
		// $fileLabel =
		// $(this).parent().parent().find("input[name='fileName']");// 文件名标签
		// $file = $(this).parent().parent().find("input[type='file']");// 文件标签
		// // 选择了文件后
		// $file.change(function()
		// {
		// var filePath = $file.val();
		// if (filePath) {
		// filePath = filePath.replace(/.*[\\|/]/g, "");
		// $fileLabel.val(filePath);
		// }
		// });
		// $file.trigger("click");
		// });
		// 目标信息接收iframe事件
		// $("#file_manage_updatefiletarget").load(function(){
		// var contents = $(this).contents().get(0);
		// var data = $(contents).find('body').text();
		// try{
		// data = eval("(" + data + ")");
		// if (data.isSuccess == true) {
		// $filetree.reAsyncChildNodes(selectNode, "refresh");
		// confirm('上传成功！');
		// } else {
		// confirm(data.message);
		// }
		// }catch(e){
		// waitui.hide();
		// alert(e);
		// }
		// });
	},
	// 删除选中的文件
	private_delete_choosed_files : function() {
		$(".del_choosed_files").live("click", function() {
			var nodes = $filetree.getCheckedNodes(true);
			var len = nodes.length;
			if (len != 0) {
				//
				var willDelNodes = new Array();
				// 文件path参数
				var paths = "";
				// 文件批量 20个删除一次
				var pathsArray = new Array();
				for ( var i = 0; i < len; i++) {
					var node = nodes[i];
					// 不是文件夹才处理
					if (node.isParent == false) {
						paths = paths + "path=" + encodeURIComponent(node.path) + "&";
						willDelNodes.push(node);
						if (i % 10 == 0 || i + 1 == len) {
							pathsArray.push(paths);
							paths = "";
						}
					}
				}
				if (pathsArray == 0) {
					return;
				}
				ui.confirm('提示', '确认删除选中文件?').ok('确定').cancel('取消').effect('slide').closable().modal().show(function(ok) {
					if (ok) {
						var waitui = ui.dialog('稍等...').modal().show();
						for ( var i = 0; i < pathsArray.length; i++) {
							var paths = pathsArray[i];
							$.ajax({
								async : false,
								url : "/filemanage/delfiles/?" + paths,
								data : {
									"projectId" : projectId,
									"deployFileId" : fileManage.deployFileId
								},
								cache : false,
								dataType : "json",
								success : function(data) {
									if (data.isSuccess == true) {
										var len = willDelNodes.length;
										for ( var i = 0; i < len; i++) {
											var node = willDelNodes[i].getParentNode();
											$filetree.removeNode(willDelNodes[i]);
											if (node != undefined && node != null) {
												node.isParent = true;
												$filetree.updateNode(node);
											}
										}
										waitui.hide();
									} else {
										confirm('异常：' + data.message);
									}
								}
							});
						}
					}
				});
			}
		});
	}
}
var upload1;