package cn.com.easy.deploy.entity;

/**
 * 服务器认证类型
 * 
 * @author nibili
 * 
 */
public abstract class ServerAuthType {
	
	/**
	 * 密码认证
	 */
	public static int AUTH_TYPE_PASSWORD = 0;
	
	/**
	 * 密钥文件认证
	 */
	public static int AUTO_TYPE_PWD_FILE = 1;
}
