package cn.com.easy.deploy.dto.login;

/**
 * 用户登录信息
 * 
 * @author nibili
 * 
 */
public class AccountDTO {
	
	private String id;
	
	/** 用户名 */
	private String name;
	
	/** 密码 */
	private String passwd;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getPasswd() {
	
		return passwd;
	}
	
	public void setPasswd(String passwd) {
	
		this.passwd = passwd;
	}

	public String getId() {
	
		return id;
	}

	public void setId(String id) {
	
		this.id = id;
	}
}
