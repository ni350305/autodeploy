
package cn.com.easy.deploy.controller.deployproject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.com.easy.deploy.service.deployproject.deployproject.IDeployProjectService;

/**
 * 批量部署项目首页
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/deployproject")
public class DeployProjectController {
	
	// private static Logger logger =
	// LoggerFactory.getLogger(DeployProjectController.class);
	
	@Autowired
	private IDeployProjectService deployProjectService;
	
	/**
	 * 项目页面
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/project/{id}")
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response, @PathVariable String id) throws Exception {
	
		if (StringUtils.isBlank(id) || NumberUtils.isDigits(id)==false) {
			return "";
		}
		model.addAttribute("project", deployProjectService.getDeployProjectDTO(id));
		return "/deployproject/deployproject";
	}
	
}
