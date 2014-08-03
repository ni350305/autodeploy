
package cn.com.easy.deploy.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import cn.com.easy.deploy.persistence.BaseEntity;

/**
 * 部署项目表
 * 
 * @author nibili
 * 
 */
@Entity
@Table(name = "easy_deploy_project")
public class DeployProjectEntity extends BaseEntity {
	
	private static final long serialVersionUID = -5828386991326968587L;
	
	// 项目名
	private String name;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
}
