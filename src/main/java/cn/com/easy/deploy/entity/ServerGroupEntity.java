
package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 服务器组实体
 * @author nibili
 *
 */
@Entity
@Table(name = "easy_server_group")
public class ServerGroupEntity extends BaseEntity {
	
	private static final long	serialVersionUID	= -948287884646145655L;
	
	// 服务器组名
	private String				name;
	
	// 备注
	private String				comment;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getComment() {
	
		return comment;
	}
	
	public void setComment(String comment) {
	
		this.comment = comment;
	}
}
