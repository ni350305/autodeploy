
package cn.com.easy.deploy.dto;

/**
 * 消息DTO
 * 
 * @author nibili
 * 
 */
public class MessageDTO {
	
	// 是否成功
	private boolean isSuccess;
	
	// 标题
	private String title;
	
	// 消息
	private String message;
	
	// 附加消息实体
	private Object object;
	
	/**
	 * 获取 消息dto
	 * 
	 * @param isSuccess
	 * @param title
	 * @param message
	 * @return
	 */
	public static MessageDTO getMessage(boolean isSuccess, String title, String message) {
	
		MessageDTO msg = new MessageDTO();
		msg.setIsSuccess(isSuccess);
		msg.setTitle(title);
		msg.setMessage(message);
		return msg;
	}
	
	/**
	 * 获取 消息dto
	 * 
	 * @param isSuccess
	 * @param title
	 * @param message
	 * @return
	 */
	public static MessageDTO getMessage(boolean isSuccess, String title, String message, Object object) {
	
		MessageDTO msg = new MessageDTO();
		msg.setIsSuccess(isSuccess);
		msg.setTitle(title);
		msg.setMessage(message);
		msg.setObject(object);
		return msg;
	}
	
	public boolean getIsSuccess() {
	
		return isSuccess;
	}
	
	public void setIsSuccess(boolean isSuccess) {
	
		this.isSuccess = isSuccess;
	}
	
	public String getTitle() {
	
		return title;
	}
	
	public void setTitle(String title) {
	
		this.title = title;
	}
	
	public String getMessage() {
	
		return message;
	}
	
	public void setMessage(String message) {
	
		this.message = message;
	}
	
	public Object getObject() {
	
		return object;
	}
	
	public void setObject(Object object) {
	
		this.object = object;
	}
}
