
package cn.com.easy.deploy.service.deployproject.deployproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.deploy.dto.deployproject.DeployProjectDTO;
import cn.com.easy.deploy.service.deployproject.deploycommand.IDeployCommandService;
import cn.com.easy.deploy.service.deployproject.deployfile.IDeployFileService;
import cn.com.easy.deploy.service.deployproject.deploytask.IDeployTaskService;

/**
 * 项目任务接口实现
 * 
 * @author nibili
 * 
 */
@Service
public class DeployProjectServiceImpl implements IDeployProjectService {
	
	@Autowired
	private IDeployFileService deployFileService;
	
	@Autowired
	private IDeployCommandService deployCommandService;
	
	@Autowired
	private IDeployTaskService deployTaskService;
	
	/**
	 * 获取署项目 DTO
	 * 
	 * @param projectId
	 *            项目 id
	 * @return
	 * @throws Exception
	 */
	@Override
	public DeployProjectDTO getDeployProjectDTO(String projectId) throws Exception {
	
		// 转换实体到DTO
		DeployProjectDTO projectDTO = new DeployProjectDTO();
		projectDTO.setId(projectId);
		//projectDTO.setName(name);
		projectDTO.setDeployFiles(deployFileService.getDeployFilesByProjectId(projectId));
		projectDTO.setDeployCommands(deployCommandService.getDeployCommandsByProjectId(projectId));
		projectDTO.setDeployTasks(deployTaskService.getDeployTasksByProjectId(projectId));
		return projectDTO;
	}
	
}
