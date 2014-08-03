package cn.com.easy.test;

import java.io.File;

public class ReadSystemDirectory {
	
	public static void main(String[] args) {
	
		File[] roots = File.listRoots();
		for (File root : roots) {
			System.out.println(root.getAbsolutePath());
			try {
				// File[] files = root.listFiles();
				// for (File file : files) {
				// System.out.println(file.getAbsolutePath());
				// }
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}
