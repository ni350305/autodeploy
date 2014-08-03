package cn.com.easy.test;

import cn.com.easy.deploy.utils.MD5Util;

public class PasswdCreater {
	
	public static void main(String[] args) throws Exception {
	
		String encodeString = MD5Util.encoderPwdByMd5("easy)%(@");
		System.out.print(encodeString + "  " + encodeString.length());
	}
}
