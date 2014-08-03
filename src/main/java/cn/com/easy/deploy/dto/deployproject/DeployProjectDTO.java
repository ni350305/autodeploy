
package cn.com.easy.deploy.dto.deployproject;

import java.util.List;

/**
 * 部署项目
 * 
 * @author nibili
 * 
 */
public class DeployProjectDTO {
	
	// 项目id
	private String id;
	
	// 项目名
	private String name;
	
	// 部署命令
	private List<DeployCommandDTO> deployCommands;
	
	// 部署文件
	private List<DeployFileDTO> deployFiles;
	
	// 部署任务
	private List<DeployTaskDTO> deployTasks;
	
	public List<DeployCommandDTO> getDeployCommands() {
	
		return deployCommands;
	}
	
	public void setDeployCommands(List<DeployCommandDTO> deployCommands) {
	
		this.deployCommands = deployCommands;
	}
	
	public List<DeployFileDTO> getDeployFiles() {
	
		return deployFiles;
	}
	
	public void setDeployFiles(List<DeployFileDTO> deployFiles) {
	
		this.deployFiles = deployFiles;
	}
	
	public List<DeployTaskDTO> getDeployTasks() {
	
		return deployTasks;
	}
	
	public void setDeployTasks(List<DeployTaskDTO> deployTasks) {
	
		this.deployTasks = deployTasks;
	}
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getId() {
	
		return id;
	}
	
	public void setId(String id) {
	
		this.id = id;
	}
	
}
