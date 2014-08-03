package cn.com.easy.deploy.controller.servermanage;

import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.dto.servermanage.ServerRequestParamDTO;
import cn.com.easy.deploy.entity.ServerAuthType;
import cn.com.easy.deploy.service.servermanageservice.servermanage.IServerManageService;
import cn.com.easy.deploy.utils.ResponseTypeOutputUtils;

/**
 * 服务器管理控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/server")
public class ServerManageController {
	
	private static Logger logger = LoggerFactory.getLogger(ServerManageController.class);
	
	@Autowired
	private IServerManageService serverManageService;
	
	@RequestMapping
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		return "/servermanage/servermanage";
	}
	
	/**
	 * 获取服务器列表
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getServers")
	public void getServers(ServerRequestParamDTO serverParamDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "success", serverManageService.getServerDtoByPage(serverParamDTO)));
		} catch (Exception e) {
			logger.error("获取服务器列表异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "获取服务器列表异常:" + e.getLocalizedMessage()));
		}
	}
	
	/**
	 * 添加服务器
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addServer", method = RequestMethod.POST)
	public void addServer(ServerDTO serverDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			InputStream inputStream = null;
			if (StringUtils.isNotBlank(serverDTO.getFileName()) && serverDTO.getAuthType() == ServerAuthType.AUTO_TYPE_PWD_FILE) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile file = multipartRequest.getFile("filename");
				if (file != null) {
					inputStream = file.getInputStream();
				}
			}
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "", serverManageService.addServer(serverDTO, inputStream)));
		} catch (Exception e) {
			logger.error("添加服务器列表异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "添加服务器列表异常:" + e.getLocalizedMessage()));
		}
	}
	/**
	 * 删除服务器
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteServer/{serverId}")
	public void deleteServer(@PathVariable Long serverId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			serverManageService.deleteServer(serverId);
			ResponseTypeOutputUtils.renderJson(response, serverManageService.deleteServer(serverId));
		} catch (Exception e) {
			logger.error("删除服务器列表异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "删除服务器列表异常:" + e.getLocalizedMessage()));
		}
	}
}
