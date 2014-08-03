package cn.com.easy.deploy.persistence.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.com.easy.deploy.utils.Page;
import cn.com.easy.deploy.utils.PageRequest;

/**
 * 持久层服务接口
 * 
 * @author nibili
 * 
 */
public interface IBaseService {
	
	/**
	 * 获取所有实体
	 * 
	 * @param entiy
	 * @return
	 */
	public <T> List<T> findAll(Class<T> entity);
	
	/**
	 * 根据sql获取实体集合
	 * 
	 * @param sql
	 * @param entiy
	 * @return
	 */
	public <T> List<T> find(String sql, Class<T> entity);
	
	/**
	 * 根据sql获取实体
	 * 
	 * @param sql
	 * @param entiy
	 * @return
	 */
	public <T> T findUniquePK(Class<T> entity, Object pk);
	
	/**
	 * 根据多个参数获取实体
	 * 
	 * @param sql
	 * @param entiy
	 * @return
	 */
	public <T> T findUniqueByProperties(Class<T> entity, Map<String,Object> map);
	
	/**
	 * 保存实体
	 * 
	 * @param entity
	 * @return
	 */
	public <T> T save(T entity);
	
	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	public void delete(Object entity);
	
	/**
	 * 删除实体
	 * 
	 * @param entity
	 */
	public <T, ID extends Serializable> void delete(Class<T> entityClass, ID id);
	
	/**
	 * 更新实体
	 * 
	 * @param entity
	 */
	public void update(Object entity);
	
	/**
	 * 执行update、delete等sql语句
	 * 
	 * @param sql
	 */
	public int excute(String sql);
	
	/**
	 * 分页获取数据
	 * 
	 * @param sql
	 * @param pageRequest
	 * @param entity
	 * @return
	 */
	public <T> Page<T> findByPage(String sql, PageRequest pageRequest);
}
