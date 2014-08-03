package cn.com.easy.deploy.service.servermanageservice.servergroupmanage;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.dto.servermanage.ServerGroupDTO;
import cn.com.easy.deploy.dto.servermanage.ServerRequestParamDTO;
import cn.com.easy.deploy.entity.DeployTaskEntity;
import cn.com.easy.deploy.entity.ServerEntity;
import cn.com.easy.deploy.entity.ServerGroupEntity;
import cn.com.easy.deploy.entity.ServerGroupRelationEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;
import cn.com.easy.deploy.utils.Page;

import com.google.common.collect.Lists;

@Service
public class ServerGroupManageServiceImpl implements IServerGroupManageService {
	
	@Autowired
	private IBaseService baseService;
	
	@Override
	public Page<ServerGroupDTO> getServerGroupDtoByPage(String userId, ServerRequestParamDTO serverRequestParam) throws Exception {
	
		String jql = "from ServerGroupEntity where ";
		if (StringUtils.isNotBlank(serverRequestParam.getQuetyString())) {
			jql = jql + " (name like '%" + serverRequestParam.getQuetyString() + "%' or comment like '%" + serverRequestParam.getQuetyString() + "%') and";
		}
		jql = jql + " createBy='" + userId + "' order by lastModifyTime desc";
		Page<ServerGroupEntity> page = baseService.findByPage(jql, serverRequestParam);
		List<ServerGroupEntity> entityList = page.getResult();
		//
		Page<ServerGroupDTO> pageServerGroupDTO = new Page<ServerGroupDTO>(page);
		pageServerGroupDTO.setTotalItems(page.getTotalItems());
		if (CollectionUtils.isNotEmpty(entityList)) {
			List<ServerGroupDTO> dtoList = Lists.newArrayList();
			ServerGroupDTO serverDto;
			for (ServerGroupEntity entity : entityList) {
				serverDto = new ServerGroupDTO();
				BeanUtils.copyProperties(serverDto, entity);
				dtoList.add(serverDto);
			}
			pageServerGroupDTO.setResult(dtoList);
		}
		return pageServerGroupDTO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long addServerGroup(String userId, ServerGroupDTO serverGroupDTO, String[] serverIds) throws Exception {
	
		ServerGroupEntity serverGroupEntity = null;
		// 新增
		serverGroupEntity = new ServerGroupEntity();
		serverGroupEntity.setCreateBy(userId);
		serverGroupEntity.setName(serverGroupDTO.getName());
		serverGroupEntity.setComment(serverGroupDTO.getComment());
		baseService.save(serverGroupEntity);
		Long serverGroupId = serverGroupEntity.getId();
		// 添加服务器
		if (serverIds != null) {
			ServerGroupRelationEntity serverGroupRelationEntity;
			for (String serverId : serverIds) {
				serverGroupRelationEntity = new ServerGroupRelationEntity();
				serverGroupRelationEntity.setServerId(Long.valueOf(serverId));
				serverGroupRelationEntity.setServerGroupId(serverGroupId);
				baseService.save(serverGroupRelationEntity);
			}
		}
		return serverGroupId;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteServerGroup(Long id) throws Exception {
	
		List<DeployTaskEntity> list = baseService.find("from DeployTaskEntity where serverGroupId=" + id, DeployTaskEntity.class);
		if (CollectionUtils.isNotEmpty(list)) {
			throw new Exception("服务器组存在于其他服务器组里，不能删除");
		}
		baseService.excute("delete from ServerGroupRelationEntity where serverGroupId=" + id);
		baseService.delete(ServerGroupEntity.class, id);
	}
	
	@Override
	public Page<ServerDTO> getServerDtosByGroupid(Long groupid, ServerRequestParamDTO serverRequestParam) throws Exception {
	
		String sql = "from ServerEntity server where server.id in (select serverId from ServerGroupRelationEntity sgr where sgr.serverGroupId=" + groupid
				+ ") ";
		if (StringUtils.isNotBlank(serverRequestParam.getQuetyString())) {
			sql = sql + " and (ip like '%" + serverRequestParam.getQuetyString() + "%' or name like '%" + serverRequestParam.getQuetyString()
					+ "%' or comment like '%" + serverRequestParam.getQuetyString() + "%' ) ";
		}
		sql = sql + " order by ip ";
		Page<ServerEntity> serversPage = baseService.findByPage(sql, serverRequestParam);
		List<ServerEntity> servers = serversPage.getResult();
		Page<ServerDTO> serverDtosPage = new Page<ServerDTO>();
		BeanUtils.copyProperties(serverDtosPage, serversPage);
		if (CollectionUtils.isNotEmpty(servers)) {
			List<ServerDTO> serverDtos = Lists.newArrayList();
			ServerDTO serverDTO = null;
			for (ServerEntity serverEntity : servers) {
				serverDTO = new ServerDTO();
				BeanUtils.copyProperties(serverDTO, serverEntity);
				serverDtos.add(serverDTO);
			}
		}
		return serverDtosPage;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateServerGroup(ServerGroupDTO serverGroupDTO) throws Exception {
	
		ServerGroupEntity serverGroupEntity = baseService.findUniquePK(ServerGroupEntity.class, serverGroupDTO.getId());
		serverGroupEntity.setName(serverGroupDTO.getName());
		serverGroupEntity.setComment(serverGroupDTO.getComment());
		baseService.save(serverGroupEntity);
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void addServersToServerGroup(Long serverGroupId, String[] serverids) throws Exception {
	
		ServerGroupRelationEntity serverGroupRelationEntity;
		for (String serverId : serverids) {
			baseService.excute("delete from " + ServerGroupRelationEntity.class.getName() + " where serverGroupId=" + serverGroupId + " and serverId="
					+ serverId);
			serverGroupRelationEntity = new ServerGroupRelationEntity();
			serverGroupRelationEntity.setServerId(Long.valueOf(serverId));
			serverGroupRelationEntity.setServerGroupId(serverGroupId);
			baseService.save(serverGroupRelationEntity);
		}
		
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteServersFromServerGroup(Long serverGroupId, String[] serverids) throws Exception {
	
		for (String serverId : serverids) {
			baseService.excute("delete from " + ServerGroupRelationEntity.class.getName() + " where serverGroupId=" + serverGroupId + " and serverId="
					+ serverId);
		}
	}
	
}
