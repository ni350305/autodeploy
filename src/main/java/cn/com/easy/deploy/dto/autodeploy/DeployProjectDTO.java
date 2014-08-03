
package cn.com.easy.deploy.dto.autodeploy;

/**
 * 部署项目
 * 
 * @author nibili
 * 
 */
public class DeployProjectDTO {
	
	private Long id;
	
	// 项目名
	private String name;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}

	public Long getId() {
	
		return id;
	}

	public void setId(Long id) {
	
		this.id = id;
	}
}
