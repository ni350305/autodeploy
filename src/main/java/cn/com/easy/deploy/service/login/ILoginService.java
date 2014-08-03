package cn.com.easy.deploy.service.login;

import cn.com.easy.deploy.dto.login.AccountDTO;

/**
 * 登录服务接口
 * 
 * @author nibili
 * 
 */
public interface ILoginService {
	
	/**
	 * 验证账户
	 * 
	 * @param accoutDTO
	 * @return
	 */
	public boolean checkAccount(AccountDTO accoutDTO) throws Exception;
}
