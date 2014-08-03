
package cn.com.easy.deploy.service.deployproject.deploycommand;

import java.util.List;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployCommandDTO;

/**
 * 部署命令服務接口
 * 
 * @author nibili
 * 
 */
public interface IDeployCommandService {
	
	/**
	 * 根据项目id，获取所有部署命令
	 * 
	 * @param projectId
	 * @return
	 */
	public List<DeployCommandDTO> getDeployCommandsByProjectId(String projectId) throws Exception;
	
	/**
	 * 添加部署命令
	 * 
	 * @param deployCommandDTO
	 * @return
	 * @throws Exception
	 */
	public MessageDTO addDeployCommand(String projectId, DeployCommandDTO deployCommandDTO) throws Exception;
	
	/**
	 * 删除部署命令
	 * 
	 * @param deployCommandId
	 *            部署命令id
	 * @return
	 * @throws Exception
	 */
	public MessageDTO deleteDeployCommand(String deployCommandId) throws Exception;
	
	/**
	 * 更新部署命令
	 * 
	 * @param deployCommandDTO
	 * @return
	 * @throws Exception
	 */
	public MessageDTO updateDeployCommand(DeployCommandDTO deployCommandDTO) throws Exception;
}
