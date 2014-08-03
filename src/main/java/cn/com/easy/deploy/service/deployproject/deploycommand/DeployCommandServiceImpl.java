
package cn.com.easy.deploy.service.deployproject.deploycommand;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployCommandDTO;
import cn.com.easy.deploy.entity.DeployCommandEntity;
import cn.com.easy.deploy.entity.DeployTaskFileCommandRelationEntity;
import cn.com.easy.deploy.entity.DeployType;
import cn.com.easy.deploy.persistence.service.IBaseService;

/**
 * 部署命令服務接口实现
 * 
 * @author nibili
 * 
 */
@Service
public class DeployCommandServiceImpl implements IDeployCommandService {
	
	@Autowired
	private IBaseService baseService;
	
	@Override
	@Transactional
	public MessageDTO addDeployCommand(String projectId, DeployCommandDTO deployCommandDTO) throws Exception {
	
		DeployCommandEntity deployCommandEntity = new DeployCommandEntity();
		BeanUtils.copyProperties(deployCommandEntity, deployCommandDTO);
		deployCommandEntity.setProjectId(Long.valueOf(projectId));
		baseService.save(deployCommandEntity);
		return MessageDTO.getMessage(true, "", "添加部署命令成功", TransDeployCommandDTO.getDeployCommandDTO(deployCommandEntity));
	}
	
	@Override
	@Transactional
	public MessageDTO deleteDeployCommand(String deployCommandId) throws Exception {
	
		List<DeployTaskFileCommandRelationEntity> list = baseService.find("from DeployTaskFileCommandRelationEntity r where r.deployFileOrCommandId=" + deployCommandId
				+ " and r.deployType=" + DeployType.TYPE_COMMAND, DeployTaskFileCommandRelationEntity.class);
		if (CollectionUtils.isEmpty(list)) {
			DeployCommandEntity deployCommandEntity = baseService.findUniquePK(DeployCommandEntity.class, Long.valueOf(deployCommandId));
			if (deployCommandEntity == null) {
				return MessageDTO.getMessage(false, "", "部署命令已不存在!");
			} else {
				baseService.delete(deployCommandEntity);
				return MessageDTO.getMessage(true, "", "删除部署命令成功!");
			}
		} else {
			return MessageDTO.getMessage(false, "", "删除失败，部署命令存在于其他部署任务中，不能删除!");
		}
	}
	
	@Override
	@Transactional
	public MessageDTO updateDeployCommand(DeployCommandDTO deployCommandDTO) throws Exception {
	
		DeployCommandEntity deployFileEntity = baseService.findUniquePK(DeployCommandEntity.class, Long.valueOf(deployCommandDTO.getId()));
		BeanUtils.copyProperties(deployFileEntity, deployCommandDTO);
		deployFileEntity.setId(deployCommandDTO.getId());
		baseService.update(deployFileEntity);
		return MessageDTO.getMessage(true, "", "更新部署命令成功!");
	}
	
	@Override
	public List<DeployCommandDTO> getDeployCommandsByProjectId(String projectId) throws Exception {
	
		List<DeployCommandEntity> list = baseService.find("from DeployCommandEntity where projectId="+projectId, DeployCommandEntity.class);
		return TransDeployCommandDTO.getDeployCommandDTOS(list);
	}
	
}
