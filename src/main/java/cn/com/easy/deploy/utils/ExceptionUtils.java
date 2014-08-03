/*
 * Copyright (c) 2005-2012 www..com.cn All rights reserved
 * Info: ExceptionUtils.java 2012-2-2 10:46:20 $$
 */
package cn.com.easy.deploy.utils;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * The Class ExceptionUtils.
 *
 * @author 
 */
public abstract class ExceptionUtils {
	/**
	 * 将CheckedException转换为UnCheckedException.
	 *
	 * @param e the e
	 * @return the runtime exception
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		}
		return new RuntimeException(e.getMessage(), e);
	}

	/**
	 * 将ErrorStack转化为String.
	 *
	 * @param e the e
	 * @return the stack trace as string
	 */
	public static String getStackTraceAsString(Exception e) {
		StringWriter stringWriter = new StringWriter();
		e.printStackTrace(new PrintWriter(stringWriter));
		return stringWriter.toString();
	}
}
