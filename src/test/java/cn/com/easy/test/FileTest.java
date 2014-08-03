package cn.com.easy.test;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileTest {
	
	public static void main(String[] args) {
	
		String folderPath = "E:\\SSHstudy\\AutoDeplojyTemplate\\target\\classes";
		File folderFile = new File(folderPath);
		List<File> files = Arrays.asList(folderFile.listFiles());
		Collections.sort(files, new Comparator<File>() {
			
			@Override
			public int compare(File o1, File o2) {
			
				if (o1.isDirectory() && o2.isFile())
					return -1;
				if (o1.isFile() && o2.isDirectory())
					return 1;
				return o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase());
			}
		});
		for (File f : files) {
			System.out.println(f.getName() + " " + f.isDirectory());
		}
	}
	
}
