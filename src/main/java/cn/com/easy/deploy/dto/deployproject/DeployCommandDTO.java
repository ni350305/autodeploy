
package cn.com.easy.deploy.dto.deployproject;


/**
 * 启动/停止命令 表
 * 
 * @author nibili
 * 
 */
public class DeployCommandDTO {
	
	private Long id;
	
	// 名称
	private String name;
	
	// 命令
	private String command;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
	}
	
	public String getCommand() {
	
		return command;
	}
	
	public void setCommand(String command) {
	
		this.command = command;
	}
	
	public Long getId() {
	
		return id;
	}
	
	public void setId(Long id) {
	
		this.id = id;
	}
}
