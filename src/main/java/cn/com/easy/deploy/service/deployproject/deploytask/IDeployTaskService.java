
package cn.com.easy.deploy.service.deployproject.deploytask;

import java.io.LineNumberReader;
import java.util.List;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployTaskDTO;

/**
 * 部署任务接口
 * 
 * @author nibili
 * 
 */
public interface IDeployTaskService {

	/**
	 * 根据项目id，获取所有部署接口
	 * 
	 * @param projectId
	 * @return
	 */
	public List<DeployTaskDTO> getDeployTasksByProjectId(String projectId) throws Exception;

	/**
	 * 根据任务id，获取任务详情
	 * 
	 * @param taskId
	 * @return
	 */
	public DeployTaskDTO getDeployTasksByTaskId(String taskId) throws Exception;

	/**
	 * 
	 * 添加部署任务
	 * 
	 * @param projectId
	 * @param deployTaskDTO
	 * @param taskDetail
	 * @return
	 */
	public MessageDTO addDeployTask(String projectId, DeployTaskDTO deployTaskDTO, String[] taskDetail) throws Exception;

	/**
	 * 删除部署任务
	 * 
	 * @param deployFileId
	 * @return
	 * @throws Exception
	 */
	public MessageDTO deleteDeployTask(String deployTaskId) throws Exception;

	/**
	 * 修改部署任务
	 * 
	 * @param deployFileId
	 * @return
	 * @throws Exception
	 */
	public MessageDTO updateDeployTask(DeployTaskDTO deployTaskDTO, String[] taskDetails) throws Exception;

	/**
	 * 执行任务
	 * 
	 * @param taskId
	 * @return
	 * @throws Exception
	 */
	public List<LineNumberReader> excecutTask(Long taskId) throws Exception;
}
