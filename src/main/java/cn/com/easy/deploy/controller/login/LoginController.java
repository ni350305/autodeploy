
package cn.com.easy.deploy.controller.login;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.easy.deploy.dto.login.AccountDTO;
import cn.com.easy.deploy.service.login.ILoginService;
import cn.com.easy.deploy.service.login.TomcatPortHelper;

/**
 * 登录控制器
 * 
 * @author nibili
 * 
 */
@Controller
@RequestMapping("/login")
public class LoginController {

	private static Logger logger = LoggerFactory.getLogger(LoginController.class);

	/** 用于读取tomcat端口 */
	@Autowired
	private TomcatPortHelper tomcatPortHelper;

	/** 登录服务类 */
	@Autowired
	private ILoginService loginService;


	/** 登录，首页 */
	@RequestMapping
	public String index(ModelMap model, HttpServletRequest request, HttpServletResponse response) {

		return "/login/login";
	}


	/**
	 * 获取服务器列表
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/check", method = { RequestMethod.POST })
	public String check(AccountDTO accountDTO, ModelMap model, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		try {
			// 验证账户
			boolean bl = loginService.checkAccount(accountDTO);
			if (bl == true) {
				request.getSession(true).setAttribute("accountDTO", accountDTO);
				String redirectUrl = "http://" + request.getServerName() + ":" + tomcatPortHelper.getTomcatPort()
						+ request.getContextPath() + "/";
				// logger.info(redirectUrl);
				// 跳转到系统首页
				return "redirect:" + redirectUrl;
			}
		} catch (Exception e) {
			logger.error("登录验证异常！", e);
		}
		model.addAttribute("msg", "内部异常，暂时无法登录!");
		return "/login/login";
	}


	/**
	 * 退出
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping(value = "/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {

		request.getSession().setAttribute("accountDTO", null);
		response.sendRedirect(request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath() + "/login");

	}
}
