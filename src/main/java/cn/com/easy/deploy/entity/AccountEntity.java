package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 用户表
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_account")
public class AccountEntity extends BaseEntity {
	
	private static final long serialVersionUID = 8269995216374444598L;
	
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
}
