
package cn.com.easy.deploy.service.deployproject.deploytask;

import java.io.LineNumberReader;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployTaskDTO;
import cn.com.easy.deploy.entity.DeployTaskEntity;
import cn.com.easy.deploy.entity.DeployTaskFileCommandRelationEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;
import cn.com.easy.deploy.service.command.ICommand;

@Service
public class DeployTaskServiceImpl implements IDeployTaskService {

	private static Logger logger = LoggerFactory.getLogger(DeployTaskServiceImpl.class);

	@Autowired
	private ICommand commandService;

	@Autowired
	private IBaseService baseService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageDTO addDeployTask(String projectId, DeployTaskDTO deployTaskDTO, String[] taskDetails) throws Exception {

		return MessageDTO.getMessage(true, "", "添加部署任务异常!请重试成功！", this.saveDeployTask(deployTaskDTO, taskDetails, Long.valueOf(projectId)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageDTO deleteDeployTask(String deployTaskId) throws Exception {

		// 任务下有其他 文件 和 命令,
		// 先删除 关系表中的 文件和命令
		baseService.excute("delete from DeployTaskFileCommandRelationEntity r where r.deployTaskId=" + deployTaskId);
		baseService.excute("delete from DeployTaskEntity t where t.id=" + deployTaskId);
		return MessageDTO.getMessage(true, "", "删除部署任务成功!");

	}

	@Override
	public List<DeployTaskDTO> getDeployTasksByProjectId(String projectId) throws Exception {

		List<DeployTaskEntity> list = baseService.find("from DeployTaskEntity where projectId=" + projectId + " order by createTime desc",
				DeployTaskEntity.class);
		return TransDeployTaskDTO.getDeployTaskDTOS(list);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageDTO updateDeployTask(DeployTaskDTO deployTaskDTO, String[] taskDetails) throws Exception {

		return MessageDTO.getMessage(true, "", "添加部署任务异常!请重试成功！", this.saveDeployTask(deployTaskDTO, taskDetails, null));
	}

	// 保存部署任务
	private DeployTaskDTO saveDeployTask(DeployTaskDTO deployTaskDTO, String[] taskDetails, Long projectId) throws Exception {

		DeployTaskEntity deployTaskEntity;
		if (deployTaskDTO.getId() == null) {
			// 添加
			deployTaskEntity = new DeployTaskEntity();
			deployTaskEntity.setProjectId(projectId);
		} else {
			// 更新
			// 先删除原来的部署 详情
			baseService.excute("delete from DeployTaskFileCommandRelationEntity where deployTaskId=" + deployTaskDTO.getId());
			deployTaskEntity = baseService.findUniquePK(DeployTaskEntity.class, deployTaskDTO.getId());
		}
		deployTaskEntity.setName(deployTaskDTO.getName());
		deployTaskEntity.setIsConcurrence(deployTaskDTO.getIsConcurrence());
		deployTaskEntity.setServerGroupId(deployTaskDTO.getServerGroupId());
		baseService.save(deployTaskEntity);
		DeployTaskFileCommandRelationEntity deployTaskFileCommandRelationEntity;
		String[] temp;
		String id;
		String type;
		for (int i = 0; i < taskDetails.length; i++) {
			temp = taskDetails[i].split("!");
			id = temp[0];
			type = temp[1];
			deployTaskFileCommandRelationEntity = new DeployTaskFileCommandRelationEntity();
			deployTaskFileCommandRelationEntity.setDeployTaskId(deployTaskEntity.getId());
			deployTaskFileCommandRelationEntity.setDeployFileOrCommandId(Long.valueOf(id));
			deployTaskFileCommandRelationEntity.setDeployType(Integer.valueOf(type));
			deployTaskFileCommandRelationEntity.setDeployIndex(i);
			baseService.save(deployTaskFileCommandRelationEntity);
		}
		// 查找详细任务
		deployTaskDTO.setId(deployTaskEntity.getId());
		TransDeployTaskDTO.transTaskDetail(deployTaskDTO);
		return deployTaskDTO;
	}

	@Override
	public DeployTaskDTO getDeployTasksByTaskId(String taskId) throws Exception {

		DeployTaskEntity deployTaskEntity = baseService.findUniquePK(DeployTaskEntity.class, Long.valueOf(taskId));
		return TransDeployTaskDTO.getDeployTaskDto(deployTaskEntity);
	}

	@Override
	public List<LineNumberReader> excecutTask(Long taskId) throws Exception {

		DeployTaskEntity deployTaskEntity = baseService.findUniquePK(DeployTaskEntity.class, Long.valueOf(taskId));
		DeployTaskDTO deployTaskDto = TransDeployTaskDTO.getDeployTaskDto(deployTaskEntity);
		String filePath = commandService.createCommandFile(deployTaskDto);
		logger.info(filePath);
		commandService.executeCommandFile("chmod +x " + filePath);
		Thread.sleep(1000);
		return commandService.executeCommandFile(filePath);
	}
}
