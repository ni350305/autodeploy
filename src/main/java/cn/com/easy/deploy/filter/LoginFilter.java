
package cn.com.easy.deploy.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.easy.deploy.dto.login.AccountDTO;

/**
 * 登录过滤器
 * 
 * @author nibili
 * 
 */
public class LoginFilter implements Filter {

	private static Logger logger = LoggerFactory.getLogger(LoginFilter.class);


	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

		// TODO Auto-generated method stub

	}


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		// 给浏览器传递一个代表会话id的，JSESSIONID
		Cookie cookie = new Cookie("JSESSIONID", ((HttpServletRequest) request).getSession().getId());
		cookie.setPath(((HttpServletRequest) request).getContextPath());
		((HttpServletResponse) response).addCookie(cookie);

		// 资源路径
		String requestURI = ((HttpServletRequest) request).getRequestURI();
		if (requestURI != null
				&& (requestURI.equals("/login") || requestURI.equals("/login/check") || requestURI.endsWith(".css")
						|| requestURI.endsWith(".js") || requestURI.indexOf("/image/") != -1
						|| requestURI.endsWith(".png") || requestURI.endsWith(".ico") || requestURI.endsWith(".jpg"))) {
			// 资源文件不进行过滤
			chain.doFilter(request, response);
		} else {
			try {
				HttpSession session = ((HttpServletRequest) request).getSession();
				Object o = session.getAttribute("accountDTO");
				if (o != null && StringUtils.isNotBlank(((AccountDTO) o).getName())
						&& StringUtils.isNotBlank(((AccountDTO) o).getPasswd())) {
					// 有用户信息，则继续请求资源链
					chain.doFilter(request, response);
				} else {
					// 还没有登录，跳转到登录页
					((HttpServletResponse) response).sendRedirect(request.getScheme() + "://" + request.getServerName()
							+ ":" + request.getServerPort() + ((HttpServletRequest) request).getContextPath()
							+ "/login");
				}
			} catch (Exception ex) {
				logger.error("", ex);
				// 异常也跳转到登录页
				((HttpServletResponse) response).sendRedirect(request.getScheme() + "://" + request.getServerName()
						+ ":" + request.getServerPort() + ((HttpServletRequest) request).getContextPath() + "/login");
			}

		}

	}


	@Override
	public void destroy() {

		// TODO Auto-generated method stub

	}

}
