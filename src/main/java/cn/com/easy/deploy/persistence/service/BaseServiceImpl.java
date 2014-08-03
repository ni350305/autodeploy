package cn.com.easy.deploy.persistence.service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import cn.com.easy.deploy.utils.Page;
import cn.com.easy.deploy.utils.PageRequest;

/**
 * 持久层服务
 * 
 * @author nibili
 * 
 */
@Service
public class BaseServiceImpl implements IBaseService {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public <T> List<T> findAll(Class<T> entity) {
	
		return entityManager.createQuery("from " + entity.getName(), entity).getResultList();
	}
	
	@Override
	public <T> List<T> find(String sql, Class<T> entity) {
	
		if (this.isContaisDeleteOrUpdate(sql)) {
			return null;
		}
		return entityManager.createQuery(sql, entity).getResultList();
	}
	
	@Override
	public <T> T findUniquePK(Class<T> entity, Object pk) {
	
		return entityManager.find(entity, pk);
	}
	
	@Override
	public <T> T save(T entity) {
	
		entityManager.persist(entity);
		return entity;
	}
	
	@Override
	public void delete(Object entity) {
	
		entityManager.remove(entity);
	}
	
	@Override
	public void update(Object entity) {
	
		entityManager.merge(entity);
	}
	
	@Override
	public int excute(String sql) {
	
		if (this.isContaisDeleteOrUpdate(sql)) {
			return 0;
		}
		return entityManager.createQuery(sql).executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> Page<T> findByPage(String sqlString, PageRequest pageRequest) {
	
		if (this.isContaisDeleteOrUpdate(sqlString)) {
			return null;
		}
		Validate.notNull(pageRequest, "page不能为空");
		Page<T> page = new Page<T>(pageRequest);
		//
		List<T> list = entityManager.createQuery(sqlString).setFirstResult(pageRequest.getOffset()).setMaxResults(pageRequest.getPageSize()).getResultList();
		page.setResult(list);
		//
		if (pageRequest.isCountTotal()) {
			Long totalItems = this.findUnique(this.prepareCountql(sqlString));
			page.setTotalItems(totalItems);
		}
		return page;
	}
	
	/**
	 * Prepare countql.
	 * 
	 * @param qlString
	 *            the ql string
	 * @return the string
	 */
	private String prepareCountql(String qlString) {
	
		String countHql = "select count (*) " + removeSelect(removeOrders(qlString));
		return countHql;
	}
	
	/**
	 * Removes the select.
	 * 
	 * @param ql
	 *            the ql
	 * @return the string
	 */
	private static String removeSelect(String ql) {
	
		int beginPos = ql.toLowerCase().indexOf("from");
		return ql.substring(beginPos);
	}
	
	/**
	 * Removes the orders.
	 * 
	 * @param ql
	 *            the hql
	 * @return the string
	 */
	private static String removeOrders(String ql) {
	
		Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(ql);
		StringBuffer sb = new StringBuffer();
		while (m.find()) {
			m.appendReplacement(sb, "");
		}
		m.appendTail(sb);
		return sb.toString();
	}
	
	@SuppressWarnings("unchecked")
	private <T> T findUnique(String sqlString) {
	
		return (T) entityManager.createQuery(sqlString).getSingleResult();
	}
	
	private boolean isContaisDeleteOrUpdate(String sql) {
	
		String temp = sql.substring(4);
		if (temp.matches(".*delete\\s*from.*") || temp.matches(".*update\\s*from.*")) {
			return true;
		}
		return false;
	}
	
	//
	// public static void main(String[] args) {
	//
	// System.out.print(isContaisDeleteOrUpdate("select * from (delete from a)(update from b)"));
	// }
	
	@Override
	public <T, ID extends Serializable> void delete(Class<T> entityClass, ID id) {
	
		T t = this.findUniquePK(entityClass, id);
		if (t != null) {
			this.delete(t);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T findUniqueByProperties(Class<T> entityClass, Map<String, Object> map) {
	
		String sql = "select t from " + entityClass.getName() + " t";
		if (map != null && map.keySet().size() != 0) {
			sql = sql + " where ";
			for (String key : map.keySet()) {
				sql = sql + " t." + key + "=:" + key + " and";
			}
			if (sql.lastIndexOf("and") != -1) {
				sql = sql.substring(0, sql.lastIndexOf("and"));
			}
		}
		
		Query query = entityManager.createQuery(sql);
		if (map != null && map.keySet().size() != 0) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				query.setParameter(entry.getKey(), entry.getValue());
			}
		}
		List<T> list = query.getResultList();
		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
}
