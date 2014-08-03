
package cn.com.easy.deploy.dto.servermanage;

/**
 * 服务器组实体
 * 
 * @author nibili
 * 
 */
public class ServerGroupDTO {

	private Long id;

	// 服务器组名
	private String name;

	// 备注
	private String comment;

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

	public Long getId() {

		return id;
	}

	public void setId(Long id) {

		this.id = id;
	}
}
