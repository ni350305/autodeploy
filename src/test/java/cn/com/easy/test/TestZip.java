package cn.com.easy.test;

import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class TestZip {
	
	public static void main(String[] args) {
	
		ZipInputStream in = null;
		try {
			// Open the ZIP file
			String inFilename = "E:\\SSHstudy\\AutoDeplojyTemplate\\target\\apache-tomcat-6.0.36.tar.gz";
//			String inFilename = "E:\\SSHstudy\\AutoDeplojyTemplate\\target\\AutoDeplojyTemplate.war";
//			String inFilename = "E:\\SSHstudy\\AutoDeplojyTemplate\\target\\AutoDeplojyTemplate-0.0.1.rar";
//			String inFilename = "E:\\SSHstudy\\AutoDeplojyTemplate\\target\\AutoDeplojyTemplate-0.0.1.zip";
			in = new ZipInputStream(new FileInputStream(inFilename));
			
			// Get the first entry
			ZipEntry entry = in.getNextEntry();
			
			// next entry
			while (entry != null) {
				System.err.println("文件名:" + entry.getName());
//				StringBuffer sb = new StringBuffer();
//				byte[] buf = new byte[1024];
//				int len;
//				while ((len = in.read(buf)) > 0) {
//					sb.append(new String(buf, 0, len));
//				}
//				System.out.println(sb.toString());
				entry = in.getNextEntry();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
