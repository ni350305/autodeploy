package cn.com.easy.deploy.service.deployproject.deployfile;

import java.io.InputStream;
import java.util.List;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployFileDTO;

/**
 * 部署文件接品
 * 
 * @author nibili
 * 
 */
public interface IDeployFileService {
	
	/**
	 * 根据项目id，获取所有部署命令
	 * 
	 * @param projectId
	 * @return
	 */
	public List<DeployFileDTO> getDeployFilesByProjectId(String projectId) throws Exception;
	
	/**
	 * 添加部署文件
	 * 
	 * @param deployname
	 * @param deploypath
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	public MessageDTO addDeployFile(String projectId, DeployFileDTO deployFileDTO, InputStream inputStream);
	
	/**
	 * 删除部署文件
	 * 
	 * @param deployFileId
	 * @return
	 * @throws Exception
	 */
	public MessageDTO deleteDeployFile(String deployFileId) throws Exception;
	
	/**
	 * 修改部署文件
	 * 
	 * @param deployFileId
	 * @return
	 * @throws Exception
	 */
	public MessageDTO updateDeployFile(DeployFileDTO deployFileDTO) throws Exception;
	
	/**
	 * 重新上传部署文件
	 * 
	 * @param deployFileId
	 * @param deployFileName
	 * @param inputStream
	 * @return
	 */
	public MessageDTO reuploadDeployFile(String projectId, String deployFileId, String fileName, boolean isDeleteFile, InputStream inputStream)
			throws Exception;
	
}
