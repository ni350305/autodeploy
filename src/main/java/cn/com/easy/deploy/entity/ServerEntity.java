package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 服务器表
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_server")
public class ServerEntity extends BaseEntity {
	
	private static final long serialVersionUID = -5863028911735673571L;
	
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
	
	// 备注
	private String comment;
	
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
	
	public int getPort() {
	
		return port;
	}
	
	public void setPort(int port) {
	
		this.port = port;
	}
}
