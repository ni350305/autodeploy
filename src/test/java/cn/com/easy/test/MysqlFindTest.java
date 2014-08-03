
package cn.com.easy.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.com.easy.deploy.entity.DeployTaskFileCommandRelationEntity;
import cn.com.easy.deploy.persistence.service.IBaseService;

public class MysqlFindTest {
	
	public static void main(String[] args) {
	
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("application-autodeploy.xml");
		IBaseService baseService = applicationContext.getBean(IBaseService.class);
		List<DeployTaskFileCommandRelationEntity> deployTaskFileCommandRelations = baseService.find("from " + DeployTaskFileCommandRelationEntity.class.getName()
				+ " f where f.deployTaskId =1 order by index", DeployTaskFileCommandRelationEntity.class);
		for (DeployTaskFileCommandRelationEntity o : deployTaskFileCommandRelations) {
			System.out.println(o.getLastModifyTime());
		}
	}
}
