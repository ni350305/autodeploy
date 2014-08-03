/*
 * Copyright (c) 2005-2012 www.easy.com.cn All rights reserved
 * Info:easy-core BaseEntity.java 2012-2-10 13:12:40 easy$$
 */

package cn.com.easy.deploy.persistence;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import cn.com.easy.deploy.persistence.listener.BaseEntityListener;

/**
 * 统一定义id的entity基类.
 * 
 * @author nibili
 * 
 */
@MappedSuperclass
@EntityListeners(value = { BaseEntityListener.class })
@org.hibernate.annotations.Entity(dynamicInsert = true, dynamicUpdate = true)
public abstract class BaseEntity implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 45586345374901436L;
	
	/** The id. */
	protected Long id; // 实体主键
	
	/** The version. */
	protected Long version; // 实体版本
	
	/** The create time. */
	protected Date createTime; // 创建时间
	
	/** The create by. */
	protected String createBy; // 创建人
	
	/** The last modify time. */
	protected Date lastModifyTime; // 最后修改时间
	
	/** The last modify by. */
	protected String lastModifyBy; // 最后修改人
	
	/**
	 * Gets the id.
	 * 
	 * @param <T>
	 *            the generic type
	 * @return the id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Long getId() {
	
		return id;
	}
	
	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(Long id) {
	
		this.id = id;
	}
	
	/**
	 * Gets the version.
	 * 
	 * @return the version
	 */
	@Version
	public Long getVersion() {
	
		return version;
	}
	
	/**
	 * Sets the version.
	 * 
	 * @param version
	 *            the new version
	 */
	public void setVersion(Long version) {
	
		this.version = version;
	}
	
	/**
	 * Gets the creates the time.
	 * 
	 * @return the creates the time
	 */
	public Date getCreateTime() {
	
		return createTime;
	}
	
	/**
	 * Sets the creates the time.
	 * 
	 * @param createTime
	 *            the new creates the time
	 */
	public void setCreateTime(Date createTime) {
	
		this.createTime = createTime;
	}
	
	/**
	 * Gets the creates the by.
	 * 
	 * @return the creates the by
	 */
	public String getCreateBy() {
	
		return createBy;
	}
	
	/**
	 * Sets the creates the by.
	 * 
	 * @param createBy
	 *            the new creates the by
	 */
	public void setCreateBy(String createBy) {
	
		this.createBy = createBy;
	}
	
	/**
	 * Gets the last modify time.
	 * 
	 * @return the last modify time
	 */
	public Date getLastModifyTime() {
	
		return lastModifyTime;
	}
	
	/**
	 * Sets the last modify time.
	 * 
	 * @param lastModifyTime
	 *            the new last modify time
	 */
	public void setLastModifyTime(Date lastModifyTime) {
	
		this.lastModifyTime = lastModifyTime;
	}
	
	/**
	 * Gets the last modify by.
	 * 
	 * @return the last modify by
	 */
	public String getLastModifyBy() {
	
		return lastModifyBy;
	}
	
	/**
	 * Sets the last modify by.
	 * 
	 * @param lastModifyBy
	 *            the new last modify by
	 */
	public void setLastModifyBy(String lastModifyBy) {
	
		this.lastModifyBy = lastModifyBy;
	}
	
	@Override
	public int hashCode() {
	
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
	
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseEntity other = (BaseEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
