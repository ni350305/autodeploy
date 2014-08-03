package cn.com.easy.deploy.dto.deployproject;

/**
 * 部署任务详情
 * 
 * @author nibili
 * 
 */
public class DeployTaskDetailDTO {
	
	/** 实体的id */
	private Long id;
	
	// 部署类型(文件/命令)(""/"")
	private int type;
	
	// 别名
	private String name;
	
	// 命令
	private String command;
	
	// 部署路径
	private String deployPath;
	
	// 部署文件名
	private String fileName;
	
	/** 部署前是否清空文件夹 */
	private int isEmptyFolder;
	
	public int getType() {
	
		return type;
	}
	
	public void setType(int type) {
	
		this.type = type;
	}
	
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
	
	public String getDeployPath() {
	
		return deployPath;
	}
	
	public void setDeployPath(String deployPath) {
	
		this.deployPath = deployPath;
	}
	
	public String getFileName() {
	
		return fileName;
	}
	
	public void setFileName(String fileName) {
	
		this.fileName = fileName;
	}
	
	public Long getId() {
	
		return id;
	}
	
	public void setId(Long id) {
	
		this.id = id;
	}
	
	public int getIsEmptyFolder() {
	
		return isEmptyFolder;
	}

	public void setIsEmptyFolder(int isEmptyFolder) {
	
		this.isEmptyFolder = isEmptyFolder;
	}

	/**
	 * 部署类型
	 * 
	 * @author nibili
	 * 
	 */
	public static enum TYPE_DEPLOY {
		FILE("FILE"), COMMAND("COMMAND");
		
		private String value;
		
		// 构造方法
		private TYPE_DEPLOY(String value) {
		
			this.value = value;
		}
		
		public String getValue() {
		
			return value;
		}
		
		public void setValue(String value) {
		
			this.value = value;
		}
	}
	
}
