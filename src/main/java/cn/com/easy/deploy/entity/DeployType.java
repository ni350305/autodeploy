package cn.com.easy.deploy.entity;


/**
 * 部署类型
 * @author nibili
 *
 */
public abstract class DeployType {

	/** 文件 */
	public final static int TYPE_FILE = 0;
	
	/** 命令 */
	public final static int TYPE_COMMAND = 1;
	
	/** 任务 */
	public final static int TYPE_TASK = 2;
}
