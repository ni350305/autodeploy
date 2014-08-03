package cn.com.easy.deploy.service.deployproject.deployfile.filemanageservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.easy.deploy.dto.deployproject.FileDTO;
import cn.com.easy.deploy.service.deployproject.deployfile.IFileManageService;

import com.google.common.collect.Lists;

/**
 * 文件管理服务
 * 
 * @author nibili
 * 
 */
@Service
public class FileManageServiceImpl implements IFileManageService {
	
	// 存放上传文件的根路径
	@Value("${deploy.file.rootpath}")
	private String fileDeployRoot = "";
	
	/** 主文件夹路径,{1}{2},projectId,deployFileId */
	private String getMainPath(String projectId, String deployFileId) {
	
		return String.format(this.fileDeployRoot + "/%s/deployfile%s/file%s", projectId, deployFileId, deployFileId);
	}
	
	/**
	 * 获取文件列表
	 * 
	 * @param fileId
	 *            文件id
	 * @param filePath
	 *            文件夹相对路径
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<FileDTO> getFiles(String projectId, String deployFileId, String parentFolderPath) throws Exception {
	
		// 主文件夹
		// String path = this.fileDeployRoot + String.valueOf(projectId) +
		// "/deployfile" + String.valueOf(deployFileId) + "/" + "file"
		// + String.valueOf(deployFileId);
		String path = this.getMainPath(String.valueOf(projectId), String.valueOf(deployFileId));
		// 文件夹
		String folderPath = path + "/" + parentFolderPath;
		folderPath = folderPath.replace("//", "/");
		File[] fileArr = new File(folderPath).listFiles();
		if (fileArr == null) {
			return new ArrayList<FileDTO>();
		}
		List<File> files = Arrays.asList(fileArr);
		// 排序
		if (CollectionUtils.isNotEmpty(files)) {
			Collections.sort(files, new Comparator<File>() {
				
				@Override
				public int compare(File o1, File o2) {
				
					if (o1.isDirectory() && o2.isFile())
						return -1;
					if (o1.isFile() && o2.isDirectory())
						return 1;
					return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
				}
			});
			return this.transFileDTO(files, parentFolderPath);
		} else {
			return null;
		}
	}
	
	/**
	 * 
	 * 转换为DTO
	 * 
	 * @param files
	 * @param parentFolderPath
	 *            主文件夹路径
	 * @return
	 */
	private List<FileDTO> transFileDTO(List<File> files, String parentFolderPath) {
	
		List<FileDTO> fileDTOList = Lists.newArrayList();
		FileDTO fileDTO;
		for (File file : files) {
			fileDTO = new FileDTO();
			fileDTO.setName(file.getName());
			fileDTO.setIsParent(file.isDirectory());
			fileDTO.setPath(parentFolderPath + "/" + file.getName());
			fileDTOList.add(fileDTO);
		}
		return fileDTOList;
	}
	
	@Override
	public void delFiles(String projectId, String deployFileId, String[] paths) throws Exception {
	
		// 主文件夹
		// String mainPath = this.fileDeployRoot + String.valueOf(projectId) +
		// "/deployfile" + String.valueOf(fileId) + "/" + "file" +
		// String.valueOf(fileId);
		String mainPathFolder = this.getMainPath(String.valueOf(projectId), String.valueOf(deployFileId));
		for (String path : paths) {
			path = java.net.URLDecoder.decode(path, "UTF-8");
			String filePath = mainPathFolder + "/" + path;
			File file = new File(filePath);
			if (file.exists()) {
				FileUtils.deleteQuietly(file);
			}
		}
	}
	
	@Override
	public void renameFile(String projectId, String deployFileId, String path, String newName) throws Exception {
	
		String mainPathFolder = this.getMainPath(String.valueOf(projectId), String.valueOf(deployFileId));
		String filePath = mainPathFolder + "/" + path;
		File file = new File(filePath);
		if (file.exists()) {
			String fileName = file.getName();
			// 新的文件路径
			String newFilePath = filePath.substring(0, filePath.lastIndexOf(fileName)) + newName;
			file.renameTo(new File(newFilePath));
		} else {
			throw new Exception("文件不存在!");
		}
	}
	
	@Override
	public void uploadFile(String projectId, String deployFileId, String path, String fileName, InputStream inputStream) throws Exception {
	
		String mainPathFolder = this.getMainPath(String.valueOf(projectId), String.valueOf(deployFileId));
		String folderPath = mainPathFolder + "/" + path;
		if (new File(folderPath).exists() == false) {
			new File(folderPath).mkdirs();
		}
		String filePath = folderPath + "/" + fileName;
		OutputStream output = null;
		try {
			output = new FileOutputStream(new File(filePath), false); // 是否追加
			IOUtils.copy(inputStream, output);
			output.flush();
		} finally {
			if (output != null) {
				output.close();
			}
		}
	}
	
	@Override
	public void createFolder(String projectId, String deployFileId, String path, String folderName) throws Exception {
	
		String mainPathFolder = this.getMainPath(String.valueOf(projectId), String.valueOf(deployFileId));
		String filePath = mainPathFolder + "/" + path + "/" + folderName;
		File file = new File(filePath);
		if (file.exists()) {
			throw new Exception("文件夹重名!");
		} else {
			file.mkdir();
		}
		
	}
	
	@Override
	public InputStream downloadFile(String projectId, String deployFileId, String path) throws Exception {
	
		String mainPathFolder = this.getMainPath(String.valueOf(projectId), String.valueOf(deployFileId));
		String filePath = mainPathFolder + "/" + path;
		File file = new File(filePath);
		if (file.exists() && file.isFile()) {
			return new FileInputStream(file);
		} else {
			return null;
		}
		
	}
}
