
package cn.com.easy.deploy.service.deployproject.deploycommand;

import java.util.List;

import cn.com.easy.deploy.dto.deployproject.DeployCommandDTO;
import cn.com.easy.deploy.entity.DeployCommandEntity;

import com.google.common.collect.Lists;

/**
 * 转换部署文件DTO
 * 
 * @author nibili
 * 
 */
public class TransDeployCommandDTO {
	
	/**
	 * 获取部署命令DTO
	 * 
	 * @param deployTaskEntitys
	 * @return
	 * @throws Exception
	 */
	public static List<DeployCommandDTO> getDeployCommandDTOS(List<DeployCommandEntity> deployCommandEntitys) throws Exception {
	
		List<DeployCommandDTO> dtoList = Lists.newArrayList();
		DeployCommandDTO dto = null;
		for (DeployCommandEntity deployCommandEntity : deployCommandEntitys) {
			dto = getDeployCommandDTO(deployCommandEntity);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	/**
	 * 获取部署命令DTO
	 * 
	 * @param deployCommandEntity
	 * @return
	 */
	public static DeployCommandDTO getDeployCommandDTO(DeployCommandEntity deployCommandEntity) {
	
		DeployCommandDTO dto = new DeployCommandDTO();
		dto.setId(deployCommandEntity.getId());
		dto.setName(deployCommandEntity.getName());
		dto.setCommand(deployCommandEntity.getCommand());
		return dto;
	}
}
