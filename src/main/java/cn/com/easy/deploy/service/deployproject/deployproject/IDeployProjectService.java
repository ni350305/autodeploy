
package cn.com.easy.deploy.service.deployproject.deployproject;

import cn.com.easy.deploy.dto.deployproject.DeployProjectDTO;

/**
 * 部署项目接口
 * 
 * @author nibili
 * 
 */
public interface IDeployProjectService {
	
	/**
	 * 获取署项目 DTO
	 * 
	 * @param id
	 *            项目 id
	 * @return
	 */
	public DeployProjectDTO getDeployProjectDTO(String id) throws Exception;
	}
