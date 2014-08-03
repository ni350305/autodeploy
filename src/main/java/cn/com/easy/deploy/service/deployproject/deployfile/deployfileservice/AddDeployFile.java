
package cn.com.easy.deploy.service.deployproject.deployfile.deployfileservice;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.deployproject.DeployFileDTO;
import cn.com.easy.deploy.entity.DeployFileEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;

/**
 * 添加部署文件
 * 
 * @author nibili
 * 
 */
@Service
public class AddDeployFile {

	// private static Logger logger =
	// LoggerFactory.getLogger(AddDeployFile.class);

	@Value("${deploy.file.types}")
	private String fileTypes = "";

	// 文件类型
	private List<String> fileTypeList = null;

	// 存放上传文件的根路径
	@Value("${deploy.file.rootpath}")
	private String fileDeployRoot = "";

	@Autowired
	private IBaseService baseService;

	/**
	 * 添加部署文件
	 * 
	 * @param deployname
	 * @param deploypath
	 * @param inputStream
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public MessageDTO addDeployFile(String projectId, DeployFileDTO deployFileDTO, InputStream inputStream) throws Exception {

		if (this.checkFileType(deployFileDTO.getFileName()) == false) {
			return MessageDTO.getMessage(false, "", "不支持的文件类型!");
		}
		DeployFileEntity deployFileEntity = this.addDeployFile(projectId, deployFileDTO.getName(), deployFileDTO.getFileName(),
				deployFileDTO.getDeployPath(), deployFileDTO.getIsEmptyFolder());
		try {
			this.uploadFile(Long.valueOf(projectId), deployFileEntity.getId(), deployFileDTO.getFileName(), false, inputStream);
		} catch (Exception ex) {
			throw new RuntimeException("创建文件异常", ex);
		}
		return MessageDTO.getMessage(true, "", "添加成功", TransDeployFileDTO.getDeployFileDTO(deployFileEntity));

	}

	/**
	 * 检查文件后缀是否支持的文件类型
	 * 
	 * @param fileName
	 * @return
	 */
	private boolean checkFileType(String fileName) {

		String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (this.getFileTypeList().contains(fileType)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 添加部署文件数据库
	 * 
	 * @param filename
	 * @param deployname
	 * @param deploypath
	 * @return long deployFileId
	 */
	private DeployFileEntity addDeployFile(String projectId, String name, String filename, String deploypath, int isEmptyFolder) {

		DeployFileEntity deployFile = new DeployFileEntity();
		deployFile.setFileName(filename);
		deployFile.setName(name);
		deployFile.setDeployPath(deploypath);
		deployFile.setProjectId(Long.valueOf(projectId));
		deployFile.setIsEmptyFolder(isEmptyFolder);
		baseService.save(deployFile);
		return deployFile;
	}

	/**
	 * 
	 * 
	 * 上传文件
	 * 
	 * @param projectId
	 *            部署项目id
	 * @param deployFileId
	 *            部署文件id
	 * @param filename
	 * @param inputStream
	 * @throws IOException
	 */
	public void uploadFile(Long projectId, Long deployFileId, String filename, boolean isDeleteFile, InputStream inputStream)
			throws Exception {

		OutputStream output = null;
		try {
			// 主文件夹
			String path = this.fileDeployRoot + "/" + String.valueOf(projectId) + "/deployfile" + String.valueOf(deployFileId) + "/";
			// 删除部署文件文件夹
			if (isDeleteFile == true) {
				FileUtils.deleteQuietly(new File(path));
			}
			// 上传文件名 开头
			String fileNameStart = "file" + String.valueOf(deployFileId);
			// 上传文件名 全名
			String fileName = fileNameStart + filename.substring(filename.lastIndexOf("."));

			// 上传文件名 绝对路径
			String fileAbsolutePath = path + fileName;
			File file = new File(fileAbsolutePath);
			if (file.exists()) {
				FileUtils.deleteQuietly(file);
			} else {
				new File(path).mkdirs();
			}
			output = new FileOutputStream(file, false); // 是否追加
			IOUtils.copy(inputStream, output);
			output.flush();

			// 解压
			StringBuilder command = new StringBuilder();
			if (filename.endsWith(".war")) {
				command.append("jar -xvf ").append("../").append(fileName);
			} else {
				if (filename.endsWith(".zip")) {
					command.append("unzip ").append("../").append(fileName);
				} else {
					if (filename.endsWith(".gz") || filename.endsWith(".rar")) {
						command.append("tar -xvf ").append("../").append(fileName);
					}
				}
			}
			// 创建文件夹
			String tempFilePath = path + fileNameStart;
			new File(tempFilePath).mkdirs();

			// 创建可执行文件，（如果直接执行压缩命令，压缩文件内容为空)
			String tarFilePath = path + fileNameStart + "/tar.sh";
			FileUtils.write(new File(tarFilePath), command.toString(), false);
			// 让其有运行的权限
			Process p = Runtime.getRuntime().exec("chmod +x " + tarFilePath);
			InputStreamReader ir = new InputStreamReader(p.getInputStream());
			LineNumberReader input = new LineNumberReader(ir);
			while (input.readLine() != null) {
				//System.out.println(res);
			}
			// 执行linux命令,压缩
			p = Runtime.getRuntime().exec("./tar.sh", null, new File(path + fileNameStart));
			ir = new InputStreamReader(p.getInputStream());
			input = new LineNumberReader(ir);
			while (input.readLine() != null) {
				//System.out.println(res);
			}

		}
		finally {
			if (output != null) {
				output.close();
				inputStream.close();
			}
		}
	}

	private List<String> getFileTypeList() {

		if (fileTypeList == null) {
			return Arrays.asList(this.fileTypes.split(","));
		}
		return fileTypeList;
	}
}
