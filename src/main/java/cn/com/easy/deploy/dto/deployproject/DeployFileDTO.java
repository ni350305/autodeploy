package cn.com.easy.deploy.dto.deployproject;

/**
 * 部署文件实体
 * 
 * @author nibili
 * 
 */
public class DeployFileDTO {
	
	private Long id;
	
	/** 部署别名 */
	private String name;
	
	/** 部署路径 */
	private String deployPath;
	
	/** 文件名 */
	private String fileName;
	
	/** 部署前是否清空文件夹 */
	private int isEmptyFolder;
	
	/** 是否删除原上传文件 */
	private boolean isDeleteFile = false;
	
	public String getName() {
	
		return name;
	}
	
	public void setName(String name) {
	
		this.name = name;
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
	
	public boolean getIsDeleteFile() {
	
		return isDeleteFile;
	}
	
	public void setIsDeleteFile(boolean isDeleteFile) {
	
		this.isDeleteFile = isDeleteFile;
	}
	
}
