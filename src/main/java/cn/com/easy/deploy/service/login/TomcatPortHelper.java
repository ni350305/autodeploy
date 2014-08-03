package cn.com.easy.deploy.service.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TomcatPortHelper {
	
	@Value("${tomcat.port}")
	private String tomcatPort = "9999";
	
	public String getTomcatPort() {
	
		return tomcatPort;
	}
}
