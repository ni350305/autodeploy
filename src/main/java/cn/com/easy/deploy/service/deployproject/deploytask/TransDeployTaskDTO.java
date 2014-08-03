package cn.com.easy.deploy.service.deployproject.deploytask;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;

import cn.com.easy.deploy.dto.deployproject.DeployTaskDTO;
import cn.com.easy.deploy.dto.deployproject.DeployTaskDetailDTO;
import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.dto.servermanage.ServerGroupDTO;
import cn.com.easy.deploy.entity.DeployCommandEntity;
import cn.com.easy.deploy.entity.DeployFileEntity;
import cn.com.easy.deploy.entity.DeployTaskEntity;
import cn.com.easy.deploy.entity.DeployTaskFileCommandRelationEntity;
import cn.com.easy.deploy.entity.DeployType;
import cn.com.easy.deploy.entity.ServerEntity;
import cn.com.easy.deploy.entity.ServerGroupEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;
import cn.com.easy.deploy.utils.SpringContextHolder;

import com.google.common.collect.Lists;

/**
 * 转换部署文件DTO
 * 
 * @author nibili
 * 
 */
public class TransDeployTaskDTO {
	
	private static IBaseService baseService = SpringContextHolder.getBean(IBaseService.class);
	
	/**
	 * 获取部署任务DTO
	 * 
	 * @param deployTaskEntitys
	 * @return
	 * @throws Exception
	 */
	public static List<DeployTaskDTO> getDeployTaskDTOS(List<DeployTaskEntity> deployTaskEntitys) throws Exception {
	
		List<DeployTaskDTO> dtoList = Lists.newArrayList();
		DeployTaskDTO dto = null;
		for (DeployTaskEntity deployTaskEntity : deployTaskEntitys) {
			dto = getDeployTaskDto(deployTaskEntity);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	/**
	 * 获取部署任务DTO
	 * 
	 * @param DeployTaskEntity
	 * @return
	 * @throws Exception
	 */
	public static DeployTaskDTO getDeployTaskDto(DeployTaskEntity deployTaskEntity) throws Exception {
	
		DeployTaskDTO dto = new DeployTaskDTO();
		BeanUtils.copyProperties(dto, deployTaskEntity);
		// 转换服务器组
		transServer(dto);
		// 转换详细任务
		transTaskDetail(dto);
		return dto;
	}
	
	/**
	 * 1、转换服务器组
	 * 
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 */
	private static void transServer(DeployTaskDTO deployTaskDTO) throws Exception {
	
		ServerGroupEntity serverGroup = baseService.findUniquePK(ServerGroupEntity.class, deployTaskDTO.getServerGroupId());
		if (serverGroup != null) {
			ServerGroupDTO serverGroupDTO = new ServerGroupDTO();
			BeanUtils.copyProperties(serverGroupDTO, serverGroup);
			deployTaskDTO.setServerGroup(serverGroupDTO);
			List<ServerEntity> servers = baseService.find("from ServerEntity where id in" + " (select serverId from ServerGroupRelationEntity where serverGroupId ="
					+ serverGroup.getId() + ") order by ip", ServerEntity.class);
			List<ServerDTO> serverDtos = Lists.newArrayList();
			ServerDTO serverDTO;
			for (ServerEntity serverEntity : servers) {
				serverDTO = new ServerDTO();
				BeanUtils.copyProperties(serverDTO, serverEntity);
				serverDtos.add(serverDTO);
			}
			deployTaskDTO.setServers(serverDtos);
		}
	}
	
	/**
	 * 2、 转换详细任务
	 * 
	 * @param deployTaskDTO
	 * @throws Exception
	 */
	public static void transTaskDetail(DeployTaskDTO deployTaskDTO) throws Exception {
	
		// 取关系实体集合
		List<DeployTaskFileCommandRelationEntity> deployTaskFileCommandRelations = baseService.find(
				"from " + DeployTaskFileCommandRelationEntity.class.getName() + " f where f.deployTaskId =" + deployTaskDTO.getId() + " order by deployIndex",
				DeployTaskFileCommandRelationEntity.class);
		// 集合必须不为空
		if (CollectionUtils.isEmpty(deployTaskFileCommandRelations) == false) {
			Object o = null;// 实体
			DeployTaskDetailDTO taskDetailDTO;
			List<DeployTaskDetailDTO> list = Lists.newArrayList();
			for (DeployTaskFileCommandRelationEntity entity : deployTaskFileCommandRelations) {
				o = null;
				// 判断是部署文件、还是部署命令
				if (entity.getDeployType() == DeployType.TYPE_FILE) {
					o = baseService.findUniquePK(DeployFileEntity.class, entity.getDeployFileOrCommandId());
				} else {
					if (entity.getDeployType() == DeployType.TYPE_COMMAND) {
						o = baseService.findUniquePK(DeployCommandEntity.class, entity.getDeployFileOrCommandId());
					}
				}
				// 取得的实体转化为dto
				if (o != null) {
					taskDetailDTO = new DeployTaskDetailDTO();
					taskDetailDTO.setType(entity.getDeployType());
					BeanUtils.copyProperties(taskDetailDTO, o);
					list.add(taskDetailDTO);
				}
			}
			deployTaskDTO.setDeployTaskDetails(list);
		}
	}
}
