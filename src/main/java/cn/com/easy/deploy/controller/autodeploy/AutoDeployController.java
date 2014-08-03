package cn.com.easy.deploy.controller.autodeploy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.deploy.dto.login.AccountDTO;
import cn.com.easy.deploy.service.autodeploy.IAutoDeployService;

/**
 * 批量部署首页
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/")
public class AutoDeployController {
	
	private static Logger logger = LoggerFactory.getLogger(AutoDeployController.class);
	
	@Autowired
	private IAutoDeployService autoDeployService;
	
	/**
	 * 首页
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {
	
		try {
			AccountDTO accountDTO = (AccountDTO) request.getSession().getAttribute("accountDTO");
			if (accountDTO == null) {
				return "";
			}
			model.addAttribute("projects", autoDeployService.getAllDeployProjects(accountDTO.getId()));
		} catch (Exception e) {
			logger.error("获取所有部署项目列表异常。", e);
		}
		return "/autodeploy/autodeploy";
	}
}
