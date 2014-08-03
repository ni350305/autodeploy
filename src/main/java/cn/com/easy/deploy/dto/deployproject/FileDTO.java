
package cn.com.easy.deploy.dto.deployproject;

/**
 * 文件DTO
 * 
 * @author nibili
 * 
 */
public class FileDTO {

	/** 文件名 */
	private String name;

	/** 当前文件路径 */
	private String path;

	/** 是不是文件夹 */
	private boolean isParent;

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public boolean getIsParent() {

		return isParent;
	}

	public void setIsParent(boolean isParent) {

		this.isParent = isParent;
	}

	/**
	 * @return the path
	 */
	public String getPath() {

		return path;
	}

	/**
	 * @param path
	 *            the path to set
	 */
	public void setPath(String path) {

		this.path = path;
	}

}
