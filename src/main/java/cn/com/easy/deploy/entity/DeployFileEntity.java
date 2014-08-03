package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 部署文件实体
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_deploy_file")
public class DeployFileEntity extends BaseEntity {
	
	private static final long serialVersionUID = 6618037936129277777L;
	
	/** 部署别名 */
	private String name;
	
	/** 部署路径 */
	private String deployPath;
	
	/** 文件名 */
	private String fileName;
	
	/** 部署前是否清空文件夹 */
	private int isEmptyFolder;
	
	/** 项目id */
	private Long projectId;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getDeployPath() {
	
		return deployPath;
	}
	
	public void setDeployPath(String deployPath) {
	
		this.deployPath = deployPath;
	}
	
	public String getFileName() {
	
		return fileName;
	}
	
	public void setFileName(String fileName) {
	
		this.fileName = fileName;
	}
	
	public Long getProjectId() {
	
		return projectId;
	}
	
	public void setProjectId(Long projectId) {
	
		this.projectId = projectId;
	}

	public int getIsEmptyFolder() {
	
		return isEmptyFolder;
	}

	public void setIsEmptyFolder(int isEmptyFolder) {
	
		this.isEmptyFolder = isEmptyFolder;
	}
}
