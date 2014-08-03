
package cn.com.easy.deploy.service.command;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;

import cn.com.easy.deploy.dto.deployproject.DeployTaskDTO;
import cn.com.easy.deploy.dto.deployproject.DeployTaskDetailDTO;
import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.entity.DeployType;
import cn.com.easy.deploy.utils.DateUtils;

/**
 * linux 命令接口实现
 * 
 * @author nibili
 * 
 */
@Service
public class CommandImpl implements ICommand {

	// private static Logger logger =
	// LoggerFactory.getLogger(CommandImpl.class);

	// 存放上传文件的根路径
	@Value("${deploy.file.rootpath}")
	private String fileDeployRoot = "";

	// 存放上传密钥文件的根路径
	@Value("${deploy.server.rootpath}")
	private String serverRoot;

	/** 同时部署到服务器的线程数 */
	@Value("${deploy.server.threads}")
	private int threads;

	/** 压缩解压的后缀 */
	private final String FILE_END_TAGS = ".tar.gz";

	/**
	 * 创建命令文件 [root@localhost ~]# cat test.sh <br/>
	 * 
	 * #!/bin/sh ips=( "192.168.1.2" "192.168.1.3" "192.168.1.4"); <br/>
	 * users=("root" "billy" "tom" "lilei"); <br/>
	 * ports=("1234" "9527" "3210" "2345"); <br/>
	 * authtypes=(0 1 1 0); <br/>
	 * ipsLength=${#ips[@]} <br/>
	 * for((i=0;i<ipsLength;i++)) <br/>
	 * do<br/>
	 * if [ ${authtypes[i]} -eq 0 ]; then <br/>
	 * echo "${users[i]}:${ports[i]}:${ips[i]} 密码认证" <br/>
	 * else<br/>
	 * echo ${users[i]}:${ports[i]}:${ips[i]} 密钥认证 <br/>
	 * fi <br/>
	 * done
	 * 
	 * @param deployFileDTO
	 * @return 命令
	 * @throws Exception
	 */
	@Override
	public String createCommandFile(DeployTaskDTO deployTaskDTO) throws Exception {

		StringBuilder commandSb = new StringBuilder();
		commandSb.append("#!/bin/sh\n");
		// 添加ip user port的数组
		this.appendIpsUsersPortsAuthtypesToCommandBuilder(commandSb, deployTaskDTO.getServers());
		commandSb.append("ipsLength=${#ips[@]}\n");
		commandSb.append("for((i=0;i<ipsLength;i++))\n");
		commandSb.append("do\n");
		if (deployTaskDTO.getIsConcurrence() == 1) {
			// 指定采用多线程
			commandSb.append("if [ 0 -eq 0 ];then\n");
			// 线程控制
			commandSb.append("if [ $(($i%").append(this.threads).append(")) -eq 0 ]; then\n");
			commandSb.append("wait\n");
			commandSb.append("fi\n");
		}
		// 部署命令和部署任务的添加
		this.apppendTasks(commandSb, deployTaskDTO);
		if (deployTaskDTO.getIsConcurrence() == 1) {
			commandSb.append("fi&\n");
		}
		commandSb.append("done\n");
		commandSb.append("wait\n");
		String folder = fileDeployRoot + deployTaskDTO.getProjectId() + "/deploytask" + deployTaskDTO.getId() + "/";
		File folderFile = new File(folder);
		if (folderFile.exists()) {
			FileUtils.deleteQuietly(folderFile);
		}
		String filePath = folder + DateUtils.getCurrentDateTime().getTime() + ".sh";
		FileUtils.write(new File(filePath), commandSb.toString(), "UTF-8");
		return filePath;
	}

	/**
	 * 部署命令和部署任务的添加
	 * 
	 * @param commandSb
	 * @param deployTaskDTO
	 * @throws IOException
	 */
	private void apppendTasks(StringBuilder commandSb, DeployTaskDTO deployTaskDTO) throws IOException {

		StringBuilder passwdTaskSb = new StringBuilder();// 密码方式，任务命令
		StringBuilder keyFileSb = new StringBuilder();// 密钥方式，任务命令
		for (DeployTaskDetailDTO detailDTO : deployTaskDTO.getDeployTaskDetails()) {

			switch (detailDTO.getType()) {
				case DeployType.TYPE_COMMAND:
					// 命令类型！
					// 命令
					String command = detailDTO.getCommand();
					// 先进入文件夹，再执行命令
					String commandFolder = "";
					String commandEndStr = "";
					String tempFirstCommand = command.split(" ")[0];
					if (tempFirstCommand.indexOf("/") != -1) {
						commandFolder = tempFirstCommand.substring(0, tempFirstCommand.lastIndexOf("/"));
						commandEndStr = command.substring(tempFirstCommand.lastIndexOf("/"));
					}
					// -------------------------------密钥方式
					keyFileSb.append("#").append(detailDTO.getName()).append("\n");
					keyFileSb.append("echo \"执行命令:").append(detailDTO.getName()).append(" ${ips[i]}").append("\"\n");
					keyFileSb.append("ssh -p ${ports[i]} ${users[i]}@${ips[i]} -i ").append(serverRoot).append("${users[i]}${ips[i]} ")
							.append("\"").append("source /etc/profile;");
					if (tempFirstCommand.indexOf("/") != -1) {
						keyFileSb.append("chmod +x ").append(command.split(" ")[0]).append(";").append("cd ").append(commandFolder)
								.append(";");
						keyFileSb.append("." + commandEndStr);
					} else {
						keyFileSb.append(command);
					}
					keyFileSb.append("\" \n");
					// ------------------------------密码方式
					passwdTaskSb.append("#").append(detailDTO.getName()).append("\n");
					passwdTaskSb.append("echo \"执行命令:").append(detailDTO.getName()).append(" ${ips[i]}").append("\"\n");
					passwdTaskSb.append("sshpass -f ").append(serverRoot).append("${users[i]}${ips[i]} ")
							.append(" ssh -p ${ports[i]} ${users[i]}@${ips[i]} ").append("\"").append("source /etc/profile;");
					if (tempFirstCommand.indexOf("/") != -1) {
						passwdTaskSb.append("chmod +x ").append(command.split(" ")[0]).append(";").append("cd ").append(commandFolder)
								.append(";").append("." + commandEndStr);
					} else {
						passwdTaskSb.append(command);
					}
					passwdTaskSb.append("\" \n");
					continue;
				case DeployType.TYPE_FILE:
					// 文件类型！
					// 先压缩文件
					synchronized (detailDTO.getId()) {
						this.tarFile(deployTaskDTO.getProjectId(), detailDTO.getId());
					}
					// ------------------------密钥方式---------------------
					if (detailDTO.getIsEmptyFolder() == 1) {
						// 部署前是否移除，当前的远程文件夹
						keyFileSb.append("#先删除文件夹\n");
						keyFileSb.append("echo \"先删除文件夹 ${ips[i]}\"\n");
						keyFileSb.append("ssh -p ${ports[i]} ${users[i]}@${ips[i]} -i ").append(serverRoot).append("${users[i]}${ips[i]} ")
								.append("\"rm -rf ").append(detailDTO.getDeployPath()).append("\"\n");
					}
					keyFileSb.append("ssh -p ${ports[i]} ${users[i]}@${ips[i]} -i ").append(serverRoot).append("${users[i]}${ips[i]} ")
							.append("\"mkdir -p ").append(detailDTO.getDeployPath()).append("\"\n");
					keyFileSb.append("#部署文件到远程服务器\n");
					keyFileSb.append("echo \"部署文件: ").append(detailDTO.getFileName()).append(" 到远程服务器 ${ips[i]}\" \n");
					keyFileSb.append("scp -P ${ports[i]} -i ").append(serverRoot).append("${users[i]}${ips[i]} ")
							.append(this.fileDeployRoot).append(deployTaskDTO.getProjectId()).append("/deployfile")
							.append(detailDTO.getId()).append("/").append("file").append(String.valueOf(detailDTO.getId()))
							.append(this.FILE_END_TAGS).append(" ${users[i]}@${ips[i]}:").append(detailDTO.getDeployPath()).append("\n");
					keyFileSb.append("#解压文件\n");
					keyFileSb.append("echo \"解压文件 ").append(detailDTO.getFileName()).append(" ${ips[i]}\" \n");
					keyFileSb.append("ssh -p ${ports[i]} ${users[i]}@${ips[i]} -i ").append(serverRoot).append("${users[i]}${ips[i]} ")
							.append(" \"cd ").append(detailDTO.getDeployPath()).append("; ").append("source /etc/profile;");
					// if (detailDTO.getFileName().endsWith(".war")) {
					// keyFileSb.append("jar -xvf ").append("file").append(String.valueOf(detailDTO.getId())).append(this.FILE_END_TAGS);
					// } else {
					// if (detailDTO.getFileName().endsWith(".zip")) {
					// keyFileSb.append("unzip ").append("file").append(String.valueOf(detailDTO.getId())).append(this.FILE_END_TAGS);
					// } else {
					// if (detailDTO.getFileName().endsWith(".gz") ||
					// detailDTO.getFileName().endsWith(".rar")) {
					keyFileSb.append("tar -xvf ").append("file").append(String.valueOf(detailDTO.getId())).append(this.FILE_END_TAGS)
							.append(";");
					// }
					// }
					// }
					keyFileSb.append("rm -rf ").append("file").append(String.valueOf(detailDTO.getId())).append(this.FILE_END_TAGS);
					keyFileSb.append("\"\n");
					// ------------------------密码方式---------------------
					if (detailDTO.getIsEmptyFolder() == 1) {
						// 部署前是否移除，当前的远程文件夹
						passwdTaskSb.append("#先删除文件夹\n");
						passwdTaskSb.append("echo \"先删除文件夹 ${ips[i]}\"\n");
						passwdTaskSb.append("sshpass -f ").append(serverRoot).append("${users[i]}${ips[i]} ")
								.append(" ssh -p ${ports[i]} ${users[i]}@${ips[i]} ").append("\"").append("rm -rf ")
								.append(detailDTO.getDeployPath()).append("\"\n");
					}
					passwdTaskSb.append("sshpass -f ").append(serverRoot).append("${users[i]}${ips[i]} ")
							.append(" ssh -p ${ports[i]} ${users[i]}@${ips[i]} ").append("\"mkdir -p ").append(detailDTO.getDeployPath())
							.append("\"\n");
					passwdTaskSb.append("#部署文件到远程服务器\n");
					passwdTaskSb.append("echo \"部署文件: ").append(detailDTO.getFileName()).append(" 到远程服务器 ${ips[i]}\" \n");
					passwdTaskSb.append("sshpass -f ").append(serverRoot).append("${users[i]}${ips[i]} ").append(" scp -P ${ports[i]} ")
							.append(this.fileDeployRoot).append(deployTaskDTO.getProjectId()).append("/deployfile")
							.append(detailDTO.getId()).append("/").append("file").append(String.valueOf(detailDTO.getId()))
							.append(this.FILE_END_TAGS).append(" ${users[i]}@${ips[i]}:").append(detailDTO.getDeployPath()).append("\n");
					passwdTaskSb.append("#解压文件\n");
					passwdTaskSb.append("echo \"解压文件 ").append(detailDTO.getFileName()).append(" ${ips[i]}\" \n");
					passwdTaskSb.append("sshpass -f ").append(serverRoot).append("${users[i]}${ips[i]} ")
							.append(" ssh -p ${ports[i]} ${users[i]}@${ips[i]} ").append(" \"cd ").append(detailDTO.getDeployPath())
							.append("; ").append("source /etc/profile;");
					// if (detailDTO.getFileName().endsWith(".war")) {
					// passwdTaskSb.append("jar -xvf ").append("file").append(String.valueOf(detailDTO.getId()))
					// .append(this.FILE_END_TAGS);
					// } else {
					// if (detailDTO.getFileName().endsWith(".zip")) {
					// passwdTaskSb.append("unzip ").append("file").append(String.valueOf(detailDTO.getId()))
					// .append(this.FILE_END_TAGS);
					// } else {
					// if (detailDTO.getFileName().endsWith(".gz") ||
					// detailDTO.getFileName().endsWith(".rar")) {
					passwdTaskSb.append("tar -xvf ").append("file").append(String.valueOf(detailDTO.getId())).append(this.FILE_END_TAGS)
							.append(";");
					// }
					// }
					// }
					passwdTaskSb.append("rm -rf ").append("file").append(String.valueOf(detailDTO.getId())).append(this.FILE_END_TAGS);
					passwdTaskSb.append("\"\n");
					continue;
			}
		}
		// 命令类型
		commandSb.append("if [ ${authtypes[i]} -eq 1 ]; then\n");
		commandSb.append(keyFileSb);
		commandSb.append("else\n");
		commandSb.append(passwdTaskSb);
		commandSb.append("fi\n");
	}

	/**
	 * 
	 * 压缩文件
	 * 
	 * @param projectId
	 * @param deployFileId
	 * @param filename
	 * @throws IOException
	 */
	private void tarFile(Long projectId, Long deployFileId) throws IOException {

		// 主文件夹
		String path = this.fileDeployRoot + "/" + String.valueOf(projectId) + "/deployfile" + String.valueOf(deployFileId) + "/";
		// 上传文件名 开头
		String fileNameStart = "file" + String.valueOf(deployFileId);
		// 上传文件名 全名
		String fileName = fileNameStart + this.FILE_END_TAGS;
		// 创建可执行文件，（如果直接执行压缩命令，压缩文件内容为空)
		String tarFilePath = path + fileNameStart + "/tar.sh";
		FileUtils.write(new File(tarFilePath), "tar -zcf " + fileName + " *", false);
		// 让其有运行的权限
		Process p = Runtime.getRuntime().exec("chmod +x " + tarFilePath);
		while (p.getInputStream().read(new byte[255]) != -1) {

		}
		// 执行linux命令,压缩
		p = Runtime.getRuntime().exec("./tar.sh", null, new File(path + fileNameStart));
		while (p.getInputStream().read(new byte[255]) != -1) {

		}
		// 执行linux命令,移到外层文件夹
		Runtime.getRuntime().exec("mv " + fileName + " ../", null, new File(path + fileNameStart));
		while (p.getInputStream().read(new byte[255]) != -1) {

		}
	}

	/**
	 * 添加ip user port的数组
	 * 
	 * @param commandSb
	 * @param servers
	 */
	private void appendIpsUsersPortsAuthtypesToCommandBuilder(StringBuilder commandSb, List<ServerDTO> servers) {

		// 添加 ips,users,ports
		StringBuilder ipsSb = new StringBuilder();
		StringBuilder usersSb = new StringBuilder();
		StringBuilder portsSb = new StringBuilder();
		StringBuilder authtypesSb = new StringBuilder();
		ipsSb.append("ips=(");
		usersSb.append("users=(");
		portsSb.append("ports=(");
		authtypesSb.append("authtypes=(");
		for (ServerDTO server : servers) {
			ipsSb.append(" \"").append(server.getIp()).append("\"");
			usersSb.append(" \"").append(server.getUserName()).append("\"");
			portsSb.append(" \"").append(server.getPort()).append("\"");
			authtypesSb.append(" \"").append(server.getAuthType()).append("\"");
		}
		ipsSb.append(");\n");
		usersSb.append(");\n");
		portsSb.append(");\n");
		authtypesSb.append(");\n");
		commandSb.append(ipsSb).append(usersSb).append(portsSb).append(authtypesSb);
	}

	/**
	 * 执行命令
	 * 
	 * @param command
	 *            命令
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<LineNumberReader> executeCommandFile(String command) throws Exception {

		if (command.contains("rm ") || command.contains("mv ")) {
			throw new Exception("命令不合法");
		}
		Process process = Runtime.getRuntime().exec(command);
		LineNumberReader errorStreamReader = new LineNumberReader(new InputStreamReader(process.getErrorStream()));
		LineNumberReader input = new LineNumberReader(new InputStreamReader(process.getInputStream()));
		List<LineNumberReader> readers = Lists.newArrayList();
		readers.add(input);
		readers.add(errorStreamReader);
		return readers;
	}
}
