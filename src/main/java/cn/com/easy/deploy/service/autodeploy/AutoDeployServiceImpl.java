package cn.com.easy.deploy.service.autodeploy;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.autodeploy.DeployProjectDTO;
import cn.com.easy.deploy.entity.DeployProjectEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;
import cn.com.easy.deploy.service.deployproject.deployproject.DeployProjectServiceImpl;

import com.google.common.collect.Lists;

/**
 * 自动部署首页服务
 * 
 * @author nibili
 * 
 */
@Service
public class AutoDeployServiceImpl implements IAutoDeployService {
	
	private static Logger logger = LoggerFactory.getLogger(DeployProjectServiceImpl.class);
	
	@Autowired
	private IBaseService baseServicce;
	
	@Override
	public List<DeployProjectDTO> getAllDeployProjects(String userId) throws Exception {
	
		// 获取所有实体
		List<DeployProjectEntity> list = baseServicce.find("from DeployProjectEntity where createBy='" + userId + "' order by lastModifyTime desc",
				DeployProjectEntity.class);
		// 转换到dto中
		List<DeployProjectDTO> dtoList = Lists.newArrayList();
		//
		DeployProjectDTO projectDTO;
		for (DeployProjectEntity project : list) {
			projectDTO = new DeployProjectDTO();
			BeanUtils.copyProperties(projectDTO, project);
			dtoList.add(projectDTO);
		}
		return dtoList;
	}
	
	/**
	 * 新建部署项目
	 */
	@Override
	@Transactional
	public MessageDTO addDeployProject(String userId, String projectName) {
	
		if (StringUtils.isBlank(projectName)) {
			return MessageDTO.getMessage(false, "", "项目名不能为空");
		}
		DeployProjectEntity project = new DeployProjectEntity();
		project.setCreateBy(userId);
		project.setName(projectName);
		try {
			baseServicce.save(project);
			return MessageDTO.getMessage(true, "", String.valueOf(project.getId()));
		} catch (Exception ex) {
			logger.error("保存新项目异常。", ex);
			return MessageDTO.getMessage(false, "", ex.getLocalizedMessage());
		}
		
	}
	
}
