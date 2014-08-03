package cn.com.easy.deploy.service.command;

import java.io.LineNumberReader;
import java.util.List;

import cn.com.easy.deploy.dto.deployproject.DeployTaskDTO;

/**
 * linux 命令接口
 * 
 * @author nibili
 * 
 */
public interface ICommand {
	
	/**
	 * 创建命令文件
	 * 
	 * @param DeployTaskDTO
	 * @return 命令文件路径
	 * @throws Exception
	 */
	public String createCommandFile(DeployTaskDTO dtployTaskDTO) throws Exception;
	
	/**
	 * 执行命令
	 * 
	 * @param command
	 *            命令
	 * @return
	 * @throws Exception
	 */
	public List<LineNumberReader> executeCommandFile(String command) throws Exception;
}
