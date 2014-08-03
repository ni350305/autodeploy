package cn.com.easy.deploy.service.servermanageservice.servermanage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.easy.deploy.dto.MessageDTO;
import cn.com.easy.deploy.dto.servermanage.ServerDTO;
import cn.com.easy.deploy.dto.servermanage.ServerRequestParamDTO;
import cn.com.easy.deploy.entity.ServerAuthType;
import cn.com.easy.deploy.entity.ServerEntity;
import cn.com.easy.deploy.entity.ServerGroupRelationEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;
import cn.com.easy.deploy.utils.Page;

import com.google.common.collect.Lists;

@Service
public class ServerManageServiceImpl implements IServerManageService {
	
	@Autowired
	private IBaseService baseService;
	
	// 存放上传密钥文件的根路径
	@Value("${deploy.server.rootpath}")
	private String serverFileRoot = "";
	
	@Override
	public Page<ServerDTO> getServerDtoByPage(ServerRequestParamDTO serverRequestParam) throws Exception {
	
		String jql = "from ServerEntity order by ip asc";
		if (StringUtils.isNotBlank(serverRequestParam.getQuetyString())) {
			jql = "from ServerEntity where ip like '%" + serverRequestParam.getQuetyString() + "%' or name like '%" + serverRequestParam.getQuetyString()
					+ "%' or comment like '%" + serverRequestParam.getQuetyString() + "%' order by ip asc";
		}
		Page<ServerEntity> page = baseService.findByPage(jql, serverRequestParam);
		List<ServerEntity> entityList = page.getResult();
		//
		Page<ServerDTO> pageServerDTO = new Page<ServerDTO>(page);
		pageServerDTO.setTotalItems(page.getTotalItems());
		if (CollectionUtils.isNotEmpty(entityList)) {
			List<ServerDTO> dtoList = Lists.newArrayList();
			ServerDTO serverDto;
			for (ServerEntity entity : entityList) {
				serverDto = new ServerDTO();
				BeanUtils.copyProperties(serverDto, entity);
				dtoList.add(serverDto);
			}
			pageServerDTO.setResult(dtoList);
		}
		return pageServerDTO;
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Long addServer(ServerDTO serverDTO, InputStream inputStream) throws Exception {
	
		ServerEntity serverEntity = null;
		if (serverDTO.getId() != null) {
			serverEntity = baseService.findUniquePK(ServerEntity.class, serverDTO.getId());
		} else {
			serverEntity = new ServerEntity();
		}
		serverEntity.setAuthType(serverDTO.getAuthType());
		serverEntity.setComment(String.valueOf(serverDTO.getComment()));
		serverEntity.setIp(serverDTO.getIp());
		serverEntity.setName(serverDTO.getName());
		serverEntity.setUserName(serverDTO.getUserName());
		serverEntity.setPort(serverDTO.getPort());
		// BeanUtils.copyProperties(serverEntity, serverDTO);
		serverEntity = baseService.save(serverEntity);
		try {
			if (inputStream != null && serverEntity.getAuthType() == ServerAuthType.AUTO_TYPE_PWD_FILE) {
				this.saveFile(serverEntity.getUserName(), serverEntity.getIp(), inputStream);
			} else {
				if (serverEntity.getAuthType() == ServerAuthType.AUTH_TYPE_PASSWORD && StringUtils.isNotBlank(serverDTO.getPassword())) {
					this.savePwd(serverEntity.getUserName(), serverEntity.getIp(), serverDTO.getPassword());
				}
			}
		} catch (Exception ex) {
			throw new Exception("创建密钥文件异常", ex);
		}
		return serverEntity.getId();
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void updateServer(ServerDTO serverDTO, InputStream inputStream) throws Exception {
	
		if (serverDTO.getId() == null) {
			return;
		}
		ServerEntity serverEntity = baseService.findUniquePK(ServerEntity.class, serverDTO.getId());
		if (serverEntity == null) {
			return;
		}
		Long tempId = serverEntity.getId();
		BeanUtils.copyProperties(serverEntity, serverDTO);
		serverEntity.setId(tempId);
		serverEntity = baseService.save(serverEntity);
		try {
			if (inputStream != null) {
				this.saveFile(serverEntity.getUserName(), serverEntity.getIp(), inputStream);
			}
		} catch (Exception ex) {
			throw new Exception("创建密钥文件异常", ex);
		}
	}
	
	/**
	 * 保存密码
	 * 
	 * @param serverId
	 * @param password
	 * @throws IOException
	 */
	private void savePwd(String user, String ip, String password) throws IOException {
	
		String path = this.serverFileRoot;
		String filepath = path + user + ip;
		File file = new File(filepath);
		if (file.exists()) {
			FileUtils.deleteQuietly(file);
		} else {
			new File(path).mkdirs();
		}
		FileUtils.write(file, password, "UTF-8");
		
	}
	
	/**
	 * 写入密钥文件
	 * 
	 * @param serverId
	 * @param ip
	 * @param inputStream
	 * @throws Exception
	 */
	private void saveFile(String user, String ip, InputStream inputStream) throws Exception {
	
		OutputStream output = null;
		try {
			String path = this.serverFileRoot;
			String filepath = path + user + ip;
			File file = new File(filepath);
			if (file.exists()) {
				FileUtils.deleteQuietly(file);
			} else {
				new File(path).mkdirs();
			}
			output = new FileOutputStream(file, false); // 是否追加
			IOUtils.copy(inputStream, output);
		} finally {
			if (output != null) {
				output.flush();
				output.close();
				inputStream.close();
			}
		}
	}
	
	@Override
	@Transactional(rollbackFor = Exception.class)
	public MessageDTO deleteServer(Long id) throws Exception {
	
		List<ServerGroupRelationEntity> list = baseService.find("from ServerGroupRelationEntity where serverId=" + id, ServerGroupRelationEntity.class);
		if (CollectionUtils.isNotEmpty(list)) {
			return MessageDTO.getMessage(false, "", "服务器存在于其他服务器组里，不能删除");
		}
		baseService.delete(ServerEntity.class, id);
		return MessageDTO.getMessage(true, "", "删除成功!");
	}
}
