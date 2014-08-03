package cn.com.easy.deploy.utils;

import java.security.MessageDigest;

public class MD5Util {
	
	public static final String encoderPwdByMd5(String s) throws Exception {
	
		char hexDigits[] = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		try {
			byte[] strTemp = s.getBytes("UTF-8");
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte byte0 = md[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf];
				str[k++] = hexDigits[byte0 & 0xf];
			}
			return new String(str);
		} catch (Exception e) {
			return null;
		}
	}
	
	public static void main(String[] args) throws Exception {
	
		System.out.println(MD5Util.encoderPwdByMd5("billlyyyyyyyyyyyy"));
		System.out.println(MD5Util.encoderPwdByMd5("billlyy").length());
	}
}
