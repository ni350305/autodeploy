package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 启动/停止命令 表
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_deploy_command")
public class DeployCommandEntity extends BaseEntity {
	
	private static final long serialVersionUID = 8240233285450145497L;
	
	/** 名称 */
	private String name;
	
	/** 命令 */
	private String command;
	
	/** 项目id */
	private Long projectId;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getCommand() {
	
		return command;
	}
	
	public void setCommand(String command) {
	
		this.command = command;
	}
	
	public Long getProjectId() {
	
		return projectId;
	}
	
	public void setProjectId(Long projectId) {
	
		this.projectId = projectId;
	}
}
