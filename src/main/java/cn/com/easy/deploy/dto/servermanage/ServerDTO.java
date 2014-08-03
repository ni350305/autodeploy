package cn.com.easy.deploy.dto.servermanage;

/**
 * 服务器表
 * 
 * @author nibili
 * 
 */
public class ServerDTO {
	
	private Long id;
	
	// 服务器ip
	private String ip;
	
	/** 端口 */
	private int port;
	
	// 服务器别名
	private String name;
	
	// 服务器登录用户名
	private String userName;
	
	// 服务器证方式（密码、密钥)
	private int authType;
	
	// 密码
	private String password;
	
	// 备注
	private String comment;
	
	// 文件名
	private String fileName;
	
	public String getIp() {
	
		return ip;
	}
	
	public void setIp(String ip) {
	
		this.ip = ip;
	}
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getUserName() {
	
		return userName;
	}
	
	public void setUserName(String userName) {
	
		this.userName = userName;
	}
	
	public int getAuthType() {
	
		return authType;
	}
	
	public void setAuthType(int authType) {
	
		this.authType = authType;
	}
	
	public String getComment() {
	
		return comment;
	}
	
	public void setComment(String comment) {
	
		this.comment = comment;
	}
	
	public Long getId() {
	
		return id;
	}
	
	public void setId(Long id) {
	
		this.id = id;
	}
	
	/**
	 * 服务器证方式（密码、密钥)
	 * 
	 * @return
	 */
	public String getAuthTypeStr() {
	
		if (this.authType == 0) {
			return "密码";
		} else {
			return "密钥";
		}
	}
	
	public String getPassword() {
	
		return password;
	}
	
	public void setPassword(String password) {
	
		this.password = password;
	}
	
	public String getFileName() {
	
		return fileName;
	}
	
	public void setFileName(String fileName) {
	
		this.fileName = fileName;
	}
	
	public int getPort() {
	
		return port;
	}
	
	public void setPort(int port) {
	
		this.port = port;
	}
}
