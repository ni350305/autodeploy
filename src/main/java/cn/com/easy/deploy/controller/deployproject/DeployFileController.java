package cn.com.easy.deploy.controller.deployproject;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployFileDTO;
import cn.com.easy.deploy.service.deployproject.deployfile.IDeployFileService;
import cn.com.easy.deploy.utils.ResponseTypeOutputUtils;

/**
 * 部署文件控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/deployproject")
public class DeployFileController {
	
	private static Logger logger = LoggerFactory.getLogger(DeployFileController.class);
	
	@Autowired
	private IDeployFileService deployFileService;
	
	/**
	 * 获取所有部署文件
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllDeployFiles", method = RequestMethod.GET)
	public void getAllDeployFiles(HttpServletRequest request, HttpServletResponse response, String projectId) {
	
		try {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "", deployFileService.getDeployFilesByProjectId(projectId)));
		} catch (Exception e) {
			logger.error("获取部署文件异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "获取部署文件异常!请重试！"));
		}
	}
	
	/**
	 * 
	 * 添加部署文件
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param deployFileDTO
	 * @param projectId
	 *            部署项目id
	 */
	@RequestMapping(value = "/addDeployFile", method = RequestMethod.POST)
	public void addDeployFile(HttpServletRequest request, HttpServletResponse response, DeployFileDTO deployFileDTO, String projectId) {
	
		if (this.check(response, deployFileDTO) == false) {
			return;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("filename");
		if (file != null) {
			// 文件的上传名称
			deployFileDTO.setFileName(file.getOriginalFilename());
			try {
				ResponseTypeOutputUtils.renderJson(response, deployFileService.addDeployFile(projectId, deployFileDTO, file.getInputStream()));
				return;
			} catch (IOException e) {
				logger.error("添加部署文件异常", e);
				ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "获取文件流异常!请重试！"));
				return;
			}
		} else {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "上传文件不能为空!"));
			return;
		}
	}
	
	/**
	 * 删除部署文件
	 * 
	 * @param request
	 * @param response
	 * @param deployFileId
	 */
	@RequestMapping("/deleteDeployFile/{deployFileId}")
	public void deleteDeployFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String deployFileId) {
	
		if (StringUtils.isBlank(deployFileId)) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署文件id不能为空！"));
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployFileService.deleteDeployFile(deployFileId));
		} catch (Exception e) {
			logger.error("删除部署文件异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "删除部署文件异常!请重试！"));
		}
	}
	
	/**
	 * 修改部署文件
	 * 
	 * @param request
	 * @param response
	 * @param deployFileId
	 */
	@RequestMapping("/updateDeployFile")
	public void updateDeployFile(HttpServletRequest request, HttpServletResponse response, DeployFileDTO deployFileDTO) {
	
		deployFileDTO.setFileName("none");
		if (this.check(response, deployFileDTO) == false) {
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployFileService.updateDeployFile(deployFileDTO));
		} catch (Exception e) {
			logger.error("修改部署文件异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "修改部署文件异常!请重试！"));
		}
	}
	
	/**
	 * 重新上传部署文件
	 * 
	 * @param request
	 * @param response
	 * @param deployFileId
	 */
	@RequestMapping(value = "/reuploadDeployFile", method = RequestMethod.POST)
	public void reuploadDeployFile(HttpServletRequest request, HttpServletResponse response, DeployFileDTO deployFileDTO, String projectId) {
	
		if (StringUtils.isBlank(deployFileDTO.getFileName())) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "文件名不能为空!"));
			return;
		}
		if (deployFileDTO.getId() == null) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署文件id不能为空!"));
			return;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		MultipartFile file = multipartRequest.getFile("filename");
		if (file != null) {
			// 文件的上传名称
			deployFileDTO.setFileName(file.getOriginalFilename());
			try {
				ResponseTypeOutputUtils.renderJson(
						response,
						deployFileService.reuploadDeployFile(projectId, String.valueOf(deployFileDTO.getId()), deployFileDTO.getFileName(),
								deployFileDTO.getIsDeleteFile(), file.getInputStream()));
				return;
			} catch (IOException e) {
				logger.error("重新上传部署文件异常", e);
				ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "重新上传,获取文件流异常!请重试！" + e.getLocalizedMessage()));
				return;
			} catch (Exception e) {
				logger.error("重新上传部署文件异常", e);
				ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "重新上传异常！" + e.getLocalizedMessage()));
				return;
			}
		} else {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "重新上传,上传文件不能为空!"));
		}
	}
	
	private boolean check(HttpServletResponse response, DeployFileDTO deployFileDTO) {
	
		if (StringUtils.isBlank(deployFileDTO.getName())) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署文件别名不能为空!"));
			return false;
		}
		if (StringUtils.isBlank(deployFileDTO.getFileName())) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署文件名不能为空!"));
			return false;
		}
		if (StringUtils.isBlank(deployFileDTO.getDeployPath())) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署路径不能为空!"));
			return false;
		}
		return true;
	}
}
