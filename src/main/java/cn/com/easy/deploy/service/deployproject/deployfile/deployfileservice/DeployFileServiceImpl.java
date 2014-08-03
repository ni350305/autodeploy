package cn.com.easy.deploy.service.deployproject.deployfile.deployfileservice;

import java.io.InputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployFileDTO;
import cn.com.easy.deploy.entity.DeployFileEntity;
import cn.com.easy.deploy.entity.DeployTaskFileCommandRelationEntity;
import cn.com.easy.deploy.entity.DeployType;
import cn.com.easy.deploy.persistence.service.IBaseService;
import cn.com.easy.deploy.service.deployproject.deployfile.IDeployFileService;

@Service
public class DeployFileServiceImpl implements IDeployFileService {
	
	private static Logger logger = LoggerFactory.getLogger(DeployFileServiceImpl.class);
	
	@Autowired
	private AddDeployFile addDeployFile;
	
	@Autowired
	private IBaseService baseService;
	
	@Override
	public MessageDTO addDeployFile(String projectId, DeployFileDTO deployFileDTO, InputStream inputStream) {
	
		try {
			return addDeployFile.addDeployFile(projectId, deployFileDTO, inputStream);
		} catch (Exception ex) {
			logger.error("添加部署文件异常", ex);
			return MessageDTO.getMessage(false, "", "添加部署文件异常!请重试！");
		}
	}
	
	@Override
	@Transactional
	public MessageDTO deleteDeployFile(String deployFileId) throws Exception {
	
		List<DeployTaskFileCommandRelationEntity> list = baseService.find("from DeployTaskFileCommandRelationEntity r where r.deployFileOrCommandId="
				+ deployFileId + " and r.deployType=" + DeployType.TYPE_FILE, DeployTaskFileCommandRelationEntity.class);
		if (CollectionUtils.isEmpty(list)) {
			DeployFileEntity deployFileEntity = baseService.findUniquePK(DeployFileEntity.class, Long.valueOf(deployFileId));
			if (deployFileEntity == null) {
				return MessageDTO.getMessage(false, "", "部署文件已不存在!");
			} else {
				baseService.delete(deployFileEntity);
				return MessageDTO.getMessage(true, "", "删除部署文件成功!");
			}
		} else {
			return MessageDTO.getMessage(false, "", "删除失败，部署文件存在于其他部署任务中，不能删除!");
		}
		
	}
	
	@Override
	@Transactional
	public MessageDTO updateDeployFile(DeployFileDTO deployFileDTO) throws Exception {
	
		DeployFileEntity deployFileEntity = baseService.findUniquePK(DeployFileEntity.class, Long.valueOf(deployFileDTO.getId()));
		deployFileEntity.setName(deployFileDTO.getName());
		deployFileEntity.setDeployPath(deployFileDTO.getDeployPath());
		deployFileEntity.setIsEmptyFolder(deployFileDTO.getIsEmptyFolder());
		baseService.update(deployFileEntity);
		return MessageDTO.getMessage(true, "", "更新部署文件成功!");
	}
	
	@Override
	@Transactional
	public MessageDTO reuploadDeployFile(String projectId, String deployFileId, String fileName, boolean isDeleteFile, InputStream inputStream)
			throws Exception
	{
	
		DeployFileEntity deployFileEntity = baseService.findUniquePK(DeployFileEntity.class, Long.valueOf(deployFileId));
		deployFileEntity.setFileName(fileName);
		baseService.update(deployFileEntity);
		try {
			addDeployFile.uploadFile(Long.valueOf(projectId), Long.valueOf(deployFileId), fileName, isDeleteFile, inputStream);
		} catch (Exception e) {
			throw new RuntimeException("重新上传文件异常", e);
		}
		return MessageDTO.getMessage(true, "", "重新上传部署文件成功!");
	}
	
	@Override
	public List<DeployFileDTO> getDeployFilesByProjectId(String projectId) throws Exception {
	
		List<DeployFileEntity> list = baseService.find("from DeployFileEntity where projectId=" + projectId + " order by lastModifyTime desc",
				DeployFileEntity.class);
		return TransDeployFileDTO.getDeployFileDTOS(list);
	}
}
