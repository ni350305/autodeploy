package cn.com.easy.deploy.service.autodeploy;

import java.util.List;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.autodeploy.DeployProjectDTO;

/**
 * 自动部署首页服务
 * 
 * @author nibili
 * 
 */
public interface IAutoDeployService {
	
	/**
	 * 获取所有部署项目
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<DeployProjectDTO> getAllDeployProjects(String userId) throws Exception;
	
	/**
	 * 新建部署项目
	 * 
	 * @param projectName
	 * @return
	 */
	public MessageDTO addDeployProject(String userId, String projectName);
	
}
