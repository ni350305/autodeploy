package cn.com.easy.deploy.controller.autodeploy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.deploy.dto.login.AccountDTO;
import cn.com.easy.deploy.service.autodeploy.IAutoDeployService;
import cn.com.easy.deploy.utils.ResponseTypeOutputUtils;

/**
 * 部署项目 控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/project")
public class ProjectController {
	
	@Autowired
	private IAutoDeployService autoDeployService;
	
	/**
	 * 添加部署项目
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/{projectName}")
	public void addProject(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String projectName) {
	
		AccountDTO accountDTO = (AccountDTO) request.getSession().getAttribute("accountDTO");
		if (accountDTO == null) {
			return;
		}
		ResponseTypeOutputUtils.renderJson(response, autoDeployService.addDeployProject(accountDTO.getId(), projectName));
	}
}
