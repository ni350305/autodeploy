package cn.com.easy.deploy.service.login;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.com.easy.deploy.dto.login.AccountDTO;
import cn.com.easy.deploy.entity.AccountEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;
import cn.com.easy.deploy.utils.MD5Util;

import com.google.common.collect.Maps;

@Service
public class LoginServiceImpl implements ILoginService {
	
	@Autowired
	IBaseService baseService;
	
	@Override
	public boolean checkAccount(AccountDTO accoutDTO) throws Exception {
	
		Map<String, Object> propertiesMap = Maps.newHashMap();
		propertiesMap.put("name", accoutDTO.getName());
		propertiesMap.put("passwd", MD5Util.encoderPwdByMd5(accoutDTO.getPasswd()));
		AccountEntity accountEntity = baseService.findUniqueByProperties(AccountEntity.class, propertiesMap);
		if (accountEntity != null && StringUtils.equals(accountEntity.getName(), accoutDTO.getName())
				&& StringUtils.equals(accountEntity.getPasswd(), MD5Util.encoderPwdByMd5(accoutDTO.getPasswd())))
		{
			accoutDTO.setId(String.valueOf(accountEntity.getId()));
			return true;
		}
		return false;
	}
}
