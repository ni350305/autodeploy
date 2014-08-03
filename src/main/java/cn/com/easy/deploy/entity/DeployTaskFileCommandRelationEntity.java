
package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 部署任务与部署文件、部署命令关系表
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_deploy_task_file_command_relation")
public class DeployTaskFileCommandRelationEntity extends BaseEntity {
	
	private static final long serialVersionUID = -5624247069791579983L;
	
	// 部署任务id
	private Long deployTaskId;
	
	// 部署文件或者命令id
	private Long deployFileOrCommandId;
	
	// 部署类型（文件/命令)(0/1)
	private int deployType;
	
	// 序号
	private int deployIndex;
	
	public Long getDeployTaskId() {
	
		return deployTaskId;
	}
	
	public void setDeployTaskId(Long deployTaskId) {
	
		this.deployTaskId = deployTaskId;
	}
	
	public Long getDeployFileOrCommandId() {
	
		return deployFileOrCommandId;
	}
	
	public void setDeployFileOrCommandId(Long deployFileOrCommandId) {
	
		this.deployFileOrCommandId = deployFileOrCommandId;
	}
	
	public int getDeployType() {
	
		return deployType;
	}
	
	public void setDeployType(int deployType) {
	
		this.deployType = deployType;
	}

	public int getDeployIndex() {
	
		return deployIndex;
	}

	public void setDeployIndex(int deployIndex) {
	
		this.deployIndex = deployIndex;
	}
	
}
