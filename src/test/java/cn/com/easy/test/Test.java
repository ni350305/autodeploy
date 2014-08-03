package cn.com.easy.test;

public class Test {
	
	public static void main(String[] args) {
		String str ="My name is %s %s";
		System.out.println(String.format(str, "billy","ni"));
	}
}
