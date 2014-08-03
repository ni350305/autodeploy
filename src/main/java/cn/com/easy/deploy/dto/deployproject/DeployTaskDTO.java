package cn.com.easy.deploy.dto.deployproject;

import java.util.List;

import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.dto.servermanage.ServerGroupDTO;

/**
 * 部署任务
 * 
 * @author nibili
 * 
 */
public class DeployTaskDTO {
	
	private Long id;
	
	/** 所属项目id*/
	private Long projectId;
	
	// 别名
	private String name;
	
	// 是否并行部署
	private boolean isConcurDeploy;
	
	// 是否并行部署
	private int isConcurrence;
	
	// 服务器组id
	private Long serverGroupId;
	
	// 服务器组
	private ServerGroupDTO serverGroup;
	
	 // 服务器集合
	 private List<ServerDTO> servers;
	
	// 部署任务详情
	private List<DeployTaskDetailDTO> deployTaskDetails;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public Long getId() {
	
		return id;
	}
	
	public void setId(Long id) {
	
		this.id = id;
	}
	
	public boolean getIsConcurDeploy() {
	
		return isConcurDeploy;
	}
	
	public void setIsConcurDeploy(boolean isConcurDeploy) {
	
		this.isConcurDeploy = isConcurDeploy;
	}
	
	public List<DeployTaskDetailDTO> getDeployTaskDetails() {
	
		return deployTaskDetails;
	}
	
	public void setDeployTaskDetails(List<DeployTaskDetailDTO> deployTaskDetails) {
	
		this.deployTaskDetails = deployTaskDetails;
	}
	
	public Long getServerGroupId() {
	
		return serverGroupId;
	}
	
	public void setServerGroupId(Long serverGroupId) {
	
		this.serverGroupId = serverGroupId;
	}
	
	public ServerGroupDTO getServerGroup() {
	
		return serverGroup;
	}
	
	public void setServerGroup(ServerGroupDTO serverGroup) {
	
		this.serverGroup = serverGroup;
	}

	public int getIsConcurrence() {
	
		return isConcurrence;
	}

	public void setIsConcurrence(int isConcurrence) {
	
		this.isConcurrence = isConcurrence;
	}

	public List<ServerDTO> getServers() {
	
		return servers;
	}

	public void setServers(List<ServerDTO> servers) {
	
		this.servers = servers;
	}

	public Long getProjectId() {
	
		return projectId;
	}

	public void setProjectId(Long projectId) {
	
		this.projectId = projectId;
	}
	
}
