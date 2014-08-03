package cn.com.easy.deploy.dto.servermanage;

import java.io.UnsupportedEncodingException;

import cn.com.easy.deploy.utils.PageRequest;

/**
 * 请求参数
 * 
 * @author nibili
 * 
 */
public class ServerRequestParamDTO extends PageRequest {
	
	private static final long serialVersionUID = 2077500319851883261L;
	
	// 搜索词
	private String quetyString;
	
	public String getQuetyString() throws UnsupportedEncodingException {
	
		return java.net.URLDecoder.decode(quetyString, "UTF-8");
	}
	
	public void setQuetyString(String quetyString) {
	
		this.quetyString = quetyString;
	}
	
}
