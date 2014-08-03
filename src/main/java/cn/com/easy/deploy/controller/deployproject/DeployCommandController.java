
package cn.com.easy.deploy.controller.deployproject;

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

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployCommandDTO;
import cn.com.easy.deploy.service.deployproject.deploycommand.IDeployCommandService;
import cn.com.easy.deploy.utils.ResponseTypeOutputUtils;

/**
 * 部署命令控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/deployproject")
public class DeployCommandController {
	
	private static Logger logger = LoggerFactory.getLogger(DeployCommandController.class);
	
	@Autowired
	private IDeployCommandService deployCommandService;
	/**
	 * 获取所有部署命令
	 * 
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "/getAllDeployCommands", method = RequestMethod.GET)
	public void getAllDeployFiles(HttpServletRequest request, HttpServletResponse response, String projectId) {
	
		try {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(true, "", "",deployCommandService.getDeployCommandsByProjectId(projectId)));
		} catch (Exception e) {
			logger.error("获取部署命令异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "获取部署命令异常!请重试！"));
		}
	}
	/**
	 * 添加部署命令
	 * 
	 * @param request
	 * @param response
	 * @param deployFileDTO
	 * @param projectId
	 */
	@RequestMapping(value = "/addDeployCommand")
	public void addDeployCommand(HttpServletRequest request, HttpServletResponse response, DeployCommandDTO deployCommandDTO, String projectId) {
	
		if (StringUtils.isBlank(projectId)) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "项目id不能为空！"));
			return;
		}
		if (this.check(response, deployCommandDTO) == false) {
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployCommandService.addDeployCommand(projectId, deployCommandDTO));
		} catch (Exception e) {
			logger.error("添加部署命令异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "添加部署命令异常!请重试！"));
		}
	}
	
	/**
	 * 删除部署命令
	 * 
	 * @param request
	 * @param response
	 * @param deployFileDTO
	 * @param projectId
	 */
	@RequestMapping(value = "/deleteDeployCommand/{deployCommandId}")
	public void deleteDeployCommand(HttpServletRequest request, HttpServletResponse response, @PathVariable String deployCommandId) {
	
		if (StringUtils.isBlank(deployCommandId)) {
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployCommandService.deleteDeployCommand(deployCommandId));
		} catch (Exception e) {
			logger.error("删除部署命令异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "删除部署命令异常!请重试！"));
		}
	}
	
	/**
	 * 更新部署命令
	 * 
	 * @param request
	 * @param response
	 * @param deployFileDTO
	 * @param projectId
	 */
	@RequestMapping(value = "/updateDeployCommand")
	public void updateDeployCommand(HttpServletRequest request, HttpServletResponse response, DeployCommandDTO deployCommandDTO) {
	
		if (deployCommandDTO.getId() == null) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署命令id不能为空！"));
			return;
		}
		if (this.check(response, deployCommandDTO) == false) {
			return;
		}
		try {
			ResponseTypeOutputUtils.renderJson(response, deployCommandService.updateDeployCommand(deployCommandDTO));
		} catch (Exception e) {
			logger.error("更新部署命令异常", e);
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "更新部署命令异常!请重试！"));
		}
	}
	
	private boolean check(HttpServletResponse response, DeployCommandDTO deployCommandDTO) {
	
		if (StringUtils.isBlank(deployCommandDTO.getName())) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署命令别名不能为空！"));
			return false;
		}
		if (StringUtils.isBlank(deployCommandDTO.getCommand())) {
			ResponseTypeOutputUtils.renderJson(response, MessageDTO.getMessage(false, "", "部署命令不能为空！"));
			return false;
		}
		return true;
	}
	
}
