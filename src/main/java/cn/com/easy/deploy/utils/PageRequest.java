/*
 * Copyright (c) 2005-2012 www.easy.com.cn All rights reserved
 * Info:easy-commons PageRequest.java 2012-4-27 10:48:25 easy$$
 */
package cn.com.easy.deploy.utils;

import java.io.Serializable;

/**
 * The Class PageRequest.
 * 
 * @author easy
 */
public class PageRequest implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 9100768518781654128L;
	
	/** The page no. */
	protected int pageNo = 1;
	
	/** The page size. */
	protected int pageSize = 10;
	
	/** The count total. */
	protected boolean countTotal = true;
	
	/**
	 * Instantiates a new page request.
	 */
	public PageRequest() {
	
	}
	
	/**
	 * Instantiates a new page request.
	 * 
	 * @param pageNo
	 *            the page no
	 * @param pageSize
	 *            the page size
	 */
	public PageRequest(int pageNo, int pageSize) {
	
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}
	
	/**
	 * Gets the page no.
	 * 
	 * @return the page no
	 */
	public int getPageNo() {
	
		return pageNo;
	}
	
	/**
	 * Sets the page no.
	 * 
	 * @param pageNo
	 *            the new page no
	 */
	public void setPageNo(final int pageNo) {
	
		this.pageNo = pageNo;
		
		if (pageNo < 1) {
			this.pageNo = 1;
		}
	}
	
	/**
	 * Gets the page size.
	 * 
	 * @return the page size
	 */
	public int getPageSize() {
	
		return pageSize;
	}
	
	/**
	 * Sets the page size.
	 * 
	 * @param pageSize
	 *            the new page size
	 */
	public void setPageSize(final int pageSize) {
	
		this.pageSize = pageSize;
		
		if (pageSize < 1) {
			this.pageSize = 1;
		}
	}
	
	/**
	 * Checks if is count total.
	 * 
	 * @return true, if is count total
	 */
	public boolean isCountTotal() {
	
		return countTotal;
	}
	
	/**
	 * Sets the count total.
	 * 
	 * @param countTotal
	 *            the new count total
	 */
	public void setCountTotal(boolean countTotal) {
	
		this.countTotal = countTotal;
	}
	
	/**
	 * Gets the offset.
	 * 
	 * @return the offset
	 */
	public int getOffset() {
	
		return ((pageNo - 1) * pageSize);
	}
	
}
