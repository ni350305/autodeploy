
package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 部署任务
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_deploy_task")
public class DeployTaskEntity extends BaseEntity {
	
	private static final long serialVersionUID = -7518677951917682503L;
	
	// 别名
	private String name;
	
	// 是否并行部署
	private int isConcurrence;
	
	// 服务器组id
	private Long serverGroupId;
	
	/** 项目id*/
	private Long projectId;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public int getIsConcurrence() {
	
		return isConcurrence;
	}
	
	public void setIsConcurrence(int isConcurrence) {
	
		this.isConcurrence = isConcurrence;
	}
	
	public Long getServerGroupId() {
	
		return serverGroupId;
	}
	
	public void setServerGroupId(Long serverGroupId) {
	
		this.serverGroupId = serverGroupId;
	}
	
	// 是否并行部署
	@Transient
	public boolean getIsConcurDeploy() {
	
		if (this.isConcurrence == 0) {
			return false;
		}
		return true;
	}

	public Long getProjectId() {
	
		return projectId;
	}

	public void setProjectId(Long projectId) {
	
		this.projectId = projectId;
	}
	
}
