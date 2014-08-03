
package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 服务器与服务器组对应关系
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_server_group_relation")
public class ServerGroupRelationEntity extends BaseEntity {
	
	private static final long	serialVersionUID	= 6702260778063260255L;
	
	// 服务器组id
	private Long				serverGroupId;
	
	// 服务器id
	private Long				serverId;
	
	public Long getServerGroupId() {
	
		return serverGroupId;
	}
	
	public void setServerGroupId(Long serverGroupId) {
	
		this.serverGroupId = serverGroupId;
	}
	
	public Long getServerId() {
	
		return serverId;
	}
	
	public void setServerId(Long serverId) {
	
		this.serverId = serverId;
	}
}
