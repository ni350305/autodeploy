package cn.com.easy.deploy.controller.servermanage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.login.AccountDTO;
import cn.com.easy.deploy.dto.servermanage.ServerGroupDTO;
import cn.com.easy.deploy.dto.servermanage.ServerRequestParamDTO;
import cn.com.easy.deploy.service.servermanageservice.servergroupmanage.IServerGroupManageService;
import cn.com.easy.deploy.utils.ResponseTypeOutputUtils;

/**
 * 服务器组管理控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/serverGroup")
public class ServerGroupManageController {
	
	private static Logger logger = LoggerFactory.getLogger(ServerGroupManageController.class);
	
	@Autowired
	private IServerGroupManageService serverGroupManageService;
	
	/**
	 * 获取服务器组列表
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getServerGroups")
	public void getServerGroups(ServerRequestParamDTO serverParamDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			AccountDTO accountDTO = (AccountDTO) request.getSession().getAttribute("accountDTO");
			if (accountDTO == null) {
				return;
			}
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(true, "", "success", serverGroupManageService.getServerGroupDtoByPage(accountDTO.getId(), serverParamDTO)));
		} catch (Exception e) {
			logger.error("获取服务器组异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "获取服务器组异常:" + e.getLocalizedMessage()));
		}
	}
	
	/**
	 * 根据group id获取 服务器
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getServersByGroupid/{groupid}")
	public void getServersByGroupid(@PathVariable Long groupid, ServerRequestParamDTO serverRequestParam, ModelMap model, HttpServletRequest request,
			HttpServletResponse response)
	{
	
		try {
			if (groupid == null) {
				return;
			}
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(true, "", "success", serverGroupManageService.getServerDtosByGroupid(groupid, serverRequestParam)));
		} catch (Exception e) {
			logger.error("获取服务器组异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "获取服务器组异常:" + e.getLocalizedMessage()));
		}
	}
	
	/**
	 * 添加服务器组
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addServerGroup")
	public void addServerGroup(ServerGroupDTO serverGroupDTO, String[] serverid, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			AccountDTO accountDTO = (AccountDTO) request.getSession().getAttribute("accountDTO");
			if (accountDTO == null) {
				return;
			}
			ResponseTypeOutputUtils.renderJson(response,
					MessageDTO.getMessage(true, "", "", serverGroupManageService.addServerGroup(accountDTO.getId(), serverGroupDTO, serverid)));
		} catch (Exception e) {
			logger.error("添加服务器组异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "添加服务器组异常:" + e.getLocalizedMessage()));
		}
	}
	
	/**
	 * 更新服务器组信息
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/updateServerGroup")
	public void updateServerGroup(ServerGroupDTO serverGroupDTO, String[] serverid, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			serverGroupManageService.updateServerGroup(serverGroupDTO);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "更新服务器组成功"));
		} catch (Exception e) {
			logger.error("添加服务器组异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "更新服务器组成功异常:" + e.getLocalizedMessage()));
		}
	}
	
	/**
	 * 删除服务器组
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteServerGroup/{serverGroupId}")
	public void deleteServerGroup(@PathVariable Long serverGroupId, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			serverGroupManageService.deleteServerGroup(serverGroupId);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "删除服务器成功"));
		} catch (Exception e) {
			logger.error("删除服务器组异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "删除服务器组异常:" + e.getLocalizedMessage()));
		}
	}
	
	/**
	 * 添加服务器 到服务器组
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/addServersToServerGroup")
	public void addServersToServerGroup(Long serverGroupId, String[] serverid, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			serverGroupManageService.addServersToServerGroup(serverGroupId, serverid);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "添加服务器到服务器组成功:" + serverGroupId));
		} catch (Exception e) {
			logger.error("添加服务器组异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "添加服务器到服务器组异常:" + e.getLocalizedMessage()));
		}
	}
	
	/**
	 * 从服务器组 删除服务器
	 * 
	 * @param model
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/deleteServersFromServerGroup")
	public void deleteServersFromServerGroup(Long serverGroupId, String[] serverid, ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			serverGroupManageService.deleteServersFromServerGroup(serverGroupId, serverid);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "从服务器组删除服务器成功:" + serverGroupId));
		} catch (Exception e) {
			logger.error("添加服务器组异常！", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "从服务器组删除服务器异常:" + e.getLocalizedMessage()));
		}
	}
}
