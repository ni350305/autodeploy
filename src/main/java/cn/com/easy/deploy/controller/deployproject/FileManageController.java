
package cn.com.easy.deploy.controller.deployproject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.FileDTO;
import cn.com.easy.deploy.service.deployproject.deployfile.IFileManageService;
import cn.com.easy.deploy.utils.ResponseTypeOutputUtils;

/**
 * 文件管理控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/filemanage")
public class FileManageController {

	private static Logger logger = LoggerFactory.getLogger(FileManageController.class);

	@Autowired
	private IFileManageService fileManageService;


	/**
	 * 根据任务id ,获取 部署任务
	 * 
	 * @param request
	 * @param response
	 * @param deployTaskDTO
	 * @param projectId
	 * @param taskDetails
	 */
	@RequestMapping(value = "/getfiles")
	public void getFiles(HttpServletRequest request, HttpServletResponse response, String projectId,
			String deployFileId, String path) {

		try {
			if (StringUtils.isBlank(path)) {
				FileDTO fileDTO = new FileDTO();
				fileDTO.setIsParent(true);
				fileDTO.setName("/");
				fileDTO.setPath("/");
				ResponseTypeOutputUtils.renderJson(response, fileDTO);
				return;
			} else {
				path = java.net.URLDecoder.decode(path, "UTF-8");
				ResponseTypeOutputUtils.renderJson(response, fileManageService.getFiles(projectId, deployFileId, path));
			}
		} catch (Exception e) {
			logger.error("获取文件列表异常", e);
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(false, "", "获取文件列表异常!请重试！" + e.getLocalizedMessage()));
		}
	}


	/**
	 * 删除文件
	 * 
	 * @param request
	 * @param response
	 * @param deployTaskDTO
	 * @param projectId
	 * @param taskDetails
	 */
	@RequestMapping(value = "/delfiles")
	public void delFiles(HttpServletRequest request, HttpServletResponse response, String projectId,
			String deployFileId, String[] path) {

		try {
			if (path == null || (path != null && path.length == 0)) {
				return;
			}
			fileManageService.delFiles(projectId, deployFileId, path);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "删除文件成功！"));
		} catch (Exception e) {
			logger.error("删除文件异常", e);
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(false, "", "删除文件异常!请重试！" + e.getLocalizedMessage()));
		}
	}


	/**
	 * 重命名
	 * 
	 * @param request
	 * @param response
	 * @param deployTaskDTO
	 * @param projectId
	 * @param taskDetails
	 */
	@RequestMapping(value = "/renamefile")
	public void renameFile(HttpServletRequest request, HttpServletResponse response, String projectId,
			String deployFileId, String path, String newName) {

		try {
			if (StringUtils.isBlank(path) || StringUtils.isBlank(newName)) {
				return;
			}
			path = java.net.URLDecoder.decode(path, "UTF-8");
			newName = java.net.URLDecoder.decode(newName, "UTF-8");
			fileManageService.renameFile(projectId, deployFileId, path, newName);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "重命名文件成功！"));
		} catch (Exception e) {
			logger.error("重命名文件列表异常", e);
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(false, "", "重命名文件异常!请重试！" + e.getLocalizedMessage()));
		}
	}


	/**
	 * 上传文件
	 * 
	 * @param request
	 * @param response
	 * @param projectId
	 * @param deployFileId
	 * @param path
	 */
	@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
	public void uploadFile(HttpServletRequest request, HttpServletResponse response, String projectId,
			String deployFileId, String path) {

		try {
			if (StringUtils.isBlank(path) || StringUtils.isBlank(projectId) || StringUtils.isBlank(deployFileId)) {
				return;
			}

			path = java.net.URLDecoder.decode(path, "UTF-8");
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			MultipartFile file = multipartRequest.getFile("filename");
			String fileName = file.getOriginalFilename();
			System.out.println("上传文件:" + fileName);
			fileManageService.uploadFile(projectId, deployFileId, path, fileName, file.getInputStream());
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "上传文件成功！", fileName));
		} catch (Exception e) {
			logger.error("上传异常", e);
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(false, "", "上传文件异常!请重试！" + e.getLocalizedMessage()));
		}
	}


	/**
	 * 新建文件夹
	 * 
	 * @param request
	 * @param response
	 * @param projectId
	 * @param deployFileId
	 * @param path
	 * @param folderName
	 */
	@RequestMapping(value = "/createFolder")
	public void createFolder(HttpServletRequest request, HttpServletResponse response, String projectId,
			String deployFileId, String path, String folderName) {

		try {
			if (StringUtils.isBlank(path) || StringUtils.isBlank(folderName)) {
				return;
			}
			path = java.net.URLDecoder.decode(path, "UTF-8");
			folderName = java.net.URLDecoder.decode(folderName, "UTF-8");
			fileManageService.createFolder(projectId, deployFileId, path, folderName);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "新建文件夹成功！"));
		} catch (Exception e) {
			logger.error("新建文件夹异常", e);
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(false, "", "新建文件夹异常!请重试！" + e.getLocalizedMessage()));
		}
	}


	/**
	 * 下载
	 * 
	 * @param request
	 * @param response
	 * @param projectId
	 * @param deployFileId
	 * @param path
	 * @throws IOException
	 */
	@RequestMapping(value = "/downloadFile")
	public void downloadFile(HttpServletRequest request, HttpServletResponse response, String projectId,
			String deployFileId, String path) throws IOException {

		InputStream ins = null;
		OutputStream outs = null;
		try {
			if (StringUtils.isBlank(path) || StringUtils.isBlank(projectId) || StringUtils.isBlank(deployFileId)) {
				return;
			}
			path = java.net.URLDecoder.decode(path, "UTF-8");
			response.reset();
			response.setContentType("APPLICATION/OCTET-STREAM");
			String fileName = "";
			if (path.lastIndexOf("/") != -1) {
				fileName = response.encodeURL(path.substring(path.lastIndexOf("/")));// 转码
			} else {
				fileName = "file" + response.encodeURL(path.substring(path.lastIndexOf(".")));// 转码
			}
			response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			ins = fileManageService.downloadFile(projectId, deployFileId, path);
			outs = response.getOutputStream();
			IOUtils.copy(ins, outs);
			outs.flush();
		} catch (Exception e) {
			logger.error("下载异常", e);
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(false, "", "下载文件异常!请重试！" + e.getLocalizedMessage()));
		} finally {
			if (ins != null) {
				ins.close();
			}
			if (outs != null) {
				outs.close();
			}

		}
	}
}
