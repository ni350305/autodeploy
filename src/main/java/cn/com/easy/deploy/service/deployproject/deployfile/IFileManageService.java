
package cn.com.easy.deploy.service.deployproject.deployfile;

import java.io.InputStream;
import java.util.List;

import cn.com.easy.deploy.dto.deployproject.FileDTO;

/**
 * 文件管理服务
 * 
 * @author nibili
 * 
 */
public interface IFileManageService {

	/**
	 * 
	 * 获取文件列表
	 * 
	 * @param projectId
	 *            项目id
	 * @param fileId
	 * @param parentFolderPath
	 * @return
	 * @throws Exception
	 */
	public List<FileDTO> getFiles(String projectId, String fileId, String parentFolderPath) throws Exception;


	/**
	 * 删除文件
	 * 
	 * @param projectId
	 * @param fileId
	 * @param paths
	 * @throws Exception
	 */
	public void delFiles(String projectId, String fileId, String[] paths) throws Exception;


	/**
	 * 下载文件
	 * 
	 * @param projectId
	 * @param fileId
	 * @param paths
	 * @throws Exception
	 */
	public InputStream downloadFile(String projectId, String fileId, String path) throws Exception;


	/**
	 * 重命名文件
	 * 
	 * @param projectId
	 * @param fileId
	 * @param path
	 * @param newName
	 * @throws Exception
	 */
	public void renameFile(String projectId, String fileId, String path, String newName) throws Exception;


	/**
	 * 新建文件夹
	 * 
	 * @param projectId
	 * @param fileId
	 * @param path
	 * @param folderName
	 * @throws Exception
	 */
	public void createFolder(String projectId, String fileId, String path, String folderName) throws Exception;


	/**
	 * 上传文件
	 * 
	 * @param projectId
	 * @param fileId
	 * @param path
	 * @param fileName
	 * @param inputStream
	 * @throws Exception
	 */
	public void uploadFile(String projectId, String deployFileId, String path, String fileName, InputStream inputStream)
			throws Exception;

}
