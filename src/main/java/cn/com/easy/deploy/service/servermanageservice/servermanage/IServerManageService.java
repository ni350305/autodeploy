package cn.com.easy.deploy.service.servermanageservice.servermanage;

import java.io.InputStream;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.dto.servermanage.ServerRequestParamDTO;
import cn.com.easy.deploy.utils.Page;

/**
 * 服务器服务接口
 * 
 * @author nibili
 * 
 */
public interface IServerManageService {
	
	/**
	 * 分页获取服务器DTO
	 * 
	 * @param serverRequestParam
	 * @return
	 * @throws Exception
	 */
	public Page<ServerDTO> getServerDtoByPage(ServerRequestParamDTO serverRequestParam) throws Exception;
	
	/**
	 * 
	 * 添加服务器,返回实体id
	 * 
	 * @param serverDTO
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public Long addServer(ServerDTO serverDTO, InputStream inputStream) throws Exception;
	
	/**
	 * 
	 * 
	 * 删除服务器,返回实体id
	 * 
	 * @param id
	 *            服务器id
	 * @return
	 * @throws Exception
	 */
	public MessageDTO deleteServer(Long id) throws Exception;
	
	/**
	 * * 更新服务器
	 * 
	 * @param serverDTO
	 * @param inputStream
	 * @throws Exception
	 */
	public void updateServer(ServerDTO serverDTO, InputStream inputStream) throws Exception;
}
