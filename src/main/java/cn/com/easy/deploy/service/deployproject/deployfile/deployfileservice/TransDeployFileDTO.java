package cn.com.easy.deploy.service.deployproject.deployfile.deployfileservice;

import java.util.List;

import cn.com.easy.deploy.dto.deployproject.DeployFileDTO;
import cn.com.easy.deploy.entity.DeployFileEntity;

import com.google.common.collect.Lists;

/**
 * 转换部署文件DTO
 * 
 * @author nibili
 * 
 */
public class TransDeployFileDTO {
	
	/**
	 * 获取部署文件DTO
	 * 
	 * @param deployTaskEntitys
	 * @return
	 * @throws Exception
	 */
	public static List<DeployFileDTO> getDeployFileDTOS(List<DeployFileEntity> deployFileEntitys) throws Exception {
	
		List<DeployFileDTO> dtoList = Lists.newArrayList();
		DeployFileDTO dto = null;
		for (DeployFileEntity deployFileEntity : deployFileEntitys) {
			dto = getDeployFileDTO(deployFileEntity);
			dtoList.add(dto);
		}
		return dtoList;
	}
	
	/**
	 * 获取部署文件DTO
	 * 
	 * @param deployFileEntity
	 * @return
	 */
	public static DeployFileDTO getDeployFileDTO(DeployFileEntity deployFileEntity) {
	
		DeployFileDTO dto = new DeployFileDTO();
		dto.setId(deployFileEntity.getId());
		dto.setName(deployFileEntity.getName());
		dto.setDeployPath(deployFileEntity.getDeployPath());
		dto.setFileName(deployFileEntity.getFileName());
		dto.setIsEmptyFolder(deployFileEntity.getIsEmptyFolder());
		return dto;
	}
}
