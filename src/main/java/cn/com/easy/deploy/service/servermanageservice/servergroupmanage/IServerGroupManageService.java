package cn.com.easy.deploy.service.servermanageservice.servergroupmanage;

import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.dto.servermanage.ServerGroupDTO;
import cn.com.easy.deploy.dto.servermanage.ServerRequestParamDTO;
import cn.com.easy.deploy.utils.Page;

/**
 * 服务器组服务接口
 * 
 * @author nibili
 * 
 */
public interface IServerGroupManageService {
	
	/**
	 * 分页获取服务器组DTO
	 * 
	 * @param serverRequestParam
	 * @return
	 * @throws Exception
	 */
	public Page<ServerGroupDTO> getServerGroupDtoByPage(String userId, ServerRequestParamDTO serverRequestParam) throws Exception;
	
	/**
	 * 根据服务器组id，分页获取服务器集合
	 * 
	 * @param serverRequestParam
	 * @return
	 * @throws Exception
	 */
	public Page<ServerDTO> getServerDtosByGroupid(Long groupid, ServerRequestParamDTO serverRequestParam) throws Exception;
	
	/**
	 * 
	 * 添加服务器组,返回实体id
	 * 
	 * @param serverDTO
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public Long addServerGroup(String userId, ServerGroupDTO serverGroupDTO, String[] serverIds) throws Exception;
	
	/**
	 * 
	 * 更新服务器组信息,返回实体id
	 * 
	 * @param serverDTO
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public void updateServerGroup(ServerGroupDTO serverGroupDTO) throws Exception;
	
	/**
	 * 
	 * 添加服务器到服务器组
	 * 
	 * @param serverGroupId
	 * @param serverids
	 * @throws Exception
	 */
	public void addServersToServerGroup(Long serverGroupId, String[] serverids) throws Exception;
	
	/**
	 * 
	 * 从服务器组 删除服务器
	 * 
	 * @param serverGroupId
	 * @param serverids
	 * @throws Exception
	 */
	public void deleteServersFromServerGroup(Long serverGroupId, String[] serverids) throws Exception;
	
	/**
	 * 
	 * 
	 * 删除服务器组,返回实体id
	 * 
	 * @param id
	 *            服务器id
	 * @return
	 * @throws Exception
	 */
	public void deleteServerGroup(Long id) throws Exception;
	
}
