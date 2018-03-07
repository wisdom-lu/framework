/*  
 * @(#)GenericH4Dao.java V1.0 2013-11-14 上午10:04:58
 * @ org.framework.dao
 *
 * Copyright (c) 2013, Framework All rights reserved.
 * Framework PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package org.framework.h4.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.framework.h4.utils.GenericsUtils;
import org.framework.h4.utils.PageBean;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;

/**
 * <p>
 * Title: 基类DAO
 * </p>
 * 
 * <p>
 * Description:
 * </p>
 * 
 * <p>
 * Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights
 * reserved.
 * </p>
 * 
 * <p>
 * Company: Framework
 * </p>
 * 
 * @author Mou Lu
 * @Date：2013-11-18 下午02:52:07
 * @version 1.0
 */
public class GenericH4Dao<T extends Serializable, PK extends Serializable>
		 implements IDao<T, PK> {

	protected final Log _log = LogFactory.getLog(getClass());

	private Class entityClass;
	
	@Resource
	protected SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Class getEntityClass() {
		return GenericsUtils.getSuperClassGenricType(getClass());
	}

	// public GenericH4Dao() {
	// _entityClass = GenericsUtils.getSuperClassGenricType(getClass());
	// }
	
	/**
	 * Description:保存实体
	 * 
	 * @param entity
	 * @throws Exception void
	 * @throws
	 */
	public void save(T entity) throws Exception {
		this.sessionFactory.getCurrentSession().save(entity);
	}
	
	/**
	 * Description:保存实体之后，返回持久太的实体
	 * 
	 * @param entity
	 * @return Entity
	 * @throws Exception     
	 */
	public T saveEntity(T entity) throws Exception{
		this.sessionFactory.getCurrentSession().save(entity);
		return entity;
	}

	/**
	 * Description:保存或者更新实体
	 * 
	 * @param entity
	 * @throws Exception void
	 * @throws
	 */
	public void saveOrUpdate(T entity) throws Exception {
		this.sessionFactory.getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * Description:批量保存
	 * 
	 * @param listEntity
	 * @throws Exception void
	 * @throws
	 */
	public void saveBatch(List<T> listEntity) throws Exception {
		if(listEntity != null && listEntity.size() > 0){
			for (T entity : listEntity) {
				this.saveOrUpdate(entity);
			}
		}
	}
	
	/**
	 * 根据主键查询实体
	 * 
	 * @param id
	 *            主键ID
	 * @return Entity 返回实体
	 * @throws Exception
	 */
	public  T findByPK(PK id) throws Exception {
		return (T) this.sessionFactory.getCurrentSession().get(
				this.getEntityClass(), id);
	}

	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 *            主键ID
	 * @throws void
	 * @throws Exception
	 */
	public void deleteByPk(PK id) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(this.findByPK(id));
		//session.flush();
	}
	
	/**
	 * 删除实体
	 * 
	 * @param Entity
	 *            实体
	 * @return void
	 * @throws Exception
	 */
	public void delete(T entity) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.delete(entity);
		//session.flush();
	}

	/**
	 * 查询所有实体
	 * 
	 * @return List<Entity> 实体集合
	 * @throws Exception
	 *             List<Entity>
	 */
	public List<T> findAll() {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from "
				+ getEntityClass().getName());
		return (List<T>) query.list();
	}
	
	/**
	 * 查询所有实体
	 * 
	 * @return List<Entity> 
	 * 			实体集合
	 * @throws Exception  List<Entity>    
	 */
	public List<T> findAllCache() throws Exception{
		Query query = this.sessionFactory.getCurrentSession().createQuery("from "
				+ getEntityClass().getName());
		query.setCacheable(true);
		return (List<T>) query.list();
	}

	/**
	 * 根据主键更新实体
	 * 
	 * @param Entity
	 *            更新对象
	 * @throws void
	 * @throws Exception
	 */
	public void update(T entity) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		session.merge(entity);
		//session.flush();
	}
	
	/**
	 * Description:执行删除或者更新HQL
	 * 
	 * @param hql
	 * @throws Exception void
	 * @throws
	 */
	public boolean updateHQL(String hql) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		int result = query.executeUpdate();
		return result > 0 ? true : false;
	}

	
	/**
	 * Description:设置多个参数
	 * 
	 * @param query
	 * @param params  void    
	 * @throws
	 */
	protected Query setParams(Query query,Object...params){
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++) {
				query.setParameter(i, params[i]);
			}
		}
		return query;
	}
	
	/**
	 * Description:执行删除或者更新HQL
	 * 
	 * @param hql Eg:update a set b=?,c=? where d=? and f=?
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return boolean
	 * @throws Exception     
	 */
	public boolean executeHQL(String hql,Object... params) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createQuery(hql);
		query = this.setParams(query, params);
		int result = query.executeUpdate();
		return result > 0 ? true : false;
		
	}

	/**
	 * Description:执行删除或者更新SQL
	 * 
	 * @param hql Eg:update a set b=?,c=? where d=? and f=?
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return boolean
	 * @throws Exception     
	 */
	public boolean executeSQL(String sql,Object... params) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createSQLQuery(sql);
		query = this.setParams(query, params);
		int result = query.executeUpdate();
		return result > 0 ? true : false;
		
	}

	/**
	 * 根据条件+分页查询
	 * 
	 * @param entity
	 *            带有条件的实体
	 * @param pageBean
	 *            分页对象
	 * @return List<Entity> 分页查询出来的结果集
	 * @throws Exception
	 */
	public PageBean<T> findByPager(T entity,
			PageBean<T> pageBean) {
		Query query = this.sessionFactory.getCurrentSession().createQuery("from "
				+ getEntityClass().getName());
		query.setFirstResult((pageBean.getPageSize())
				* (pageBean.getPageNow() - 1));
		query.setMaxResults(pageBean.getPageSize());
		pageBean.setList(query.list());
		return pageBean;
	}
	
	/**
	 * 根据条件+分页查询
	 * 
	 * @param entity
	 * 			带有条件的实体
	 * @param pageBean
	 * 			分页对象
	 * @return List<Entity>
	 * 			分页查询出来的结果集
	 * @throws Exception
	 */
	public PageBean<T> findByPagerCache(T entity,
			PageBean<T> pageBean) throws Exception{
		Query query = this.sessionFactory.getCurrentSession().createQuery("from "
				+ getEntityClass().getName());
		query.setFirstResult((pageBean.getPageSize())
				* (pageBean.getPageNow() - 1));
		query.setMaxResults(pageBean.getPageSize());
		pageBean.setList(query.list());
		query.setCacheable(true);
		return pageBean;
	}

	/**
	 * Description:查询分页总数
	 * 
	 * @param entity
	 * @return
	 * @throws Exception int
	 */
	public int findCount(T entity) throws Exception {
		Query query = this.sessionFactory.getCurrentSession().createQuery("select count(*) from "
				+ getEntityClass().getName());
		Object obj = query.uniqueResult();
		return obj != null ? ((Long) obj).intValue() : 0;
	}

	/**
	 * Description:条件加分页查询
	 * 
	 * @param DetachedCriteria
	 *            查询条件
	 * @param PageBean
	 *            <Entity> 分页条件
	 * @return PageBean<Entity> 分页结果
	 * @throws Exception
	 */
	public PageBean<T> findByCriteria(DetachedCriteria criterion,
			PageBean<T> pageBean) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cria = criterion.getExecutableCriteria(session);
		cria.setFirstResult(pageBean.getPageSize()
				* (pageBean.getPageNow() - 1));
		cria.setMaxResults(pageBean.getPageSize());
		pageBean.setList(cria.list());
		return pageBean;
		
	}
	
	/**
	 * Description:条件加分页查询
	 * 
	 * @param DetachedCriteria
	 * 		     查询条件
	 * @param PageBean<Entity>
	 * 		     分页条件
	 * @return PageBean<Entity>
	 *        分页结果
	 * @throws Exception 
	 */
	public PageBean<T> findByCriteriaCache(DetachedCriteria criterion,
			PageBean<T> pageBean) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cria = criterion.getExecutableCriteria(session);
		cria.setCacheable(true);
		cria.setFirstResult(pageBean.getPageSize()
				* (pageBean.getPageNow() - 1));
		cria.setMaxResults(pageBean.getPageSize());
		pageBean.setList(cria.list());
		return pageBean;
	}

	/**
	 * Description:条件+分页Count查询
	 * 
	 * @param DetachedCriteria
	 * @return int
	 * @throws Exception
	 */
	public int findByCriteriaCount(DetachedCriteria criterion)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cria = criterion.getExecutableCriteria(session);
		cria.setFirstResult(0);// 为了排除DetachedCriteria自带的缓存机制，因此在查询Count的时候，从新设置
		cria.setMaxResults(1); // 因为Count只有一条数据，因此从0开始查询1条结束
		Object obj = cria.setProjection(Projections.rowCount())
				.uniqueResult();
		return obj == null ? 0 : Integer.parseInt(obj.toString());
	}

	/**
	 * Description:条件查询
	 * 
	 * @param DetachedCriteria
	 * @return List<Entity>
	 * @throws Exception
	 */
	public List<T> findByCriteria(DetachedCriteria criterion)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cria = criterion.getExecutableCriteria(session);
		return cria.list();
	}
	
	/**
	 * Description:条件查询
	 * 
	 * @param DetachedCriteria
	 * @return List<Entity>
	 * @throws Exception 
	 */
	public List<T> findByCriteriaCache(DetachedCriteria criterion) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Criteria cria = criterion.getExecutableCriteria(session);
		cria.setCacheable(true);
		return cria.list();
	}

	/**
	 * Description:根据属性查询对象实体
	 * 
	 * @param propertie
	 *            属性名字
	 * @param value
	 *            属性值
	 * @return Entity 返回实体
	 * @throws
	 */
	public T findByPropertie(String propertie,Object value)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "select obj from "
			+ getEntityClass().getName()
			+ " obj where obj." + propertie + " =:tmp ";
		
		Query query = session.createQuery(hql);
		query.setParameter("tmp", value);
		return (T) query.uniqueResult();
	}
	
	/**
	 * Description:根据属性查询对象实体
	 * 
	 * @param propertie
	 * 			属性名字
	 * @param value
	 * 			属性值
	 * @return  Entity  
	 * 			返回实体  
	 * @throws
	 */
	public T findByPropertieCache(String propertie,Object value) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "select obj from "
			+ getEntityClass().getName()
			+ " obj where obj." + propertie + " =:tmp ";
		
		Query query = session.createQuery(hql);
		query.setParameter("tmp", value);
		query.setCacheable(true);
		return (T) query.uniqueResult();
	}

	/**
	 * Description:根据属性查询对象实体集合
	 * 
	 * @param propertie
	 *            属性名字
	 * @param value
	 *            属性值
	 * @return List<Entity> 返回实体集合
	 * @throws
	 */
	public List<T> findListByPropertie(String propertie,
			Object value) throws Exception {

		Session session = this.sessionFactory.getCurrentSession();
		String hql = "select obj from "
			+ getEntityClass().getName()
			+ " obj where obj." + propertie + " =:tmp ";
		
		Query query = session.createQuery(hql);
		query.setParameter("tmp", value);
		return query.list();
	}
	
	/**
	 * Description:根据属性查询对象实体集合
	 * 
	 * @param propertie
	 * 			属性名字
	 * @param value
	 * 			属性值
	 * @return  List<Entity>  
	 * 			返回实体集合
	 * @throws
	 */
	public List<T> findListByPropertieCache(String propertie,Object value) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "select obj from "
			+ getEntityClass().getName()
			+ " obj where obj." + propertie + " =:tmp ";
		
		Query query = session.createQuery(hql);
		query.setParameter("tmp", value);
		query.setCacheable(true);
		return query.list();
	}

	/**
	 * Description:调用有输入参数的存储过程
	 * 
	 * @param proc_name
	 *            存储过程名字
	 * @param obj
	 *            输入参数的值
	 * @throws Exception void
	 */
	public void callProc(String procName, Object... obj)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		String hql = "CALL " + procName + "(";
		// 如果有参数则拼接参数
		if (obj != null && obj.length > 0) {
			for (int i = 0; i < obj.length; i++) {
				hql += "?,";
			}
			hql = hql.substring(0, hql.length() - 1);
		}
		hql += ")";
		Query query = session.createSQLQuery(hql);
		// 如果有参数则设置参数
		if (obj != null && obj.length > 0) {
			for (int i = 0; i < obj.length; i++) {
				query.setParameter(i, obj[i]);
			}
		}
		int i = query.executeUpdate();
	}

	/**
	 * Description:根据sql 查询Count
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception int
	 */
	public Long findSqlCount(String sql, Object... obj)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query = this.setParams(query, obj);
		Object retValue = query.uniqueResult();
		return retValue == null ? 0 : ((Long) retValue);
	
	}

	/**
	 * Description:根据sql 查询sql聚合函数
	 * 
	 * @param sql
	 * @param obj
	 * @return Object
	 * @throws Exception
	 */
	public Object findSqlAggregate(String sql, Object... obj)
			throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query = this.setParams(query, obj);
		return query.uniqueResult();
		
	}

	/**
	 * Description:根据sql查询出结果集
	 * 
	 * @return List<Object[]>
	 * @throws Exception
	 */
	public List<Object[]> findbySql(String sql) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		return query.list();
	}
	
	/**
	 * Description:根据sql查询出结果集
	 * @param Sql Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return List<Object[]>
	 * @throws Exception      
	 */
	public List<Object[]> findbySQL(String sql,Object... obj) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query = this.setParams(query, obj);
		return query.list();
	}
	
	/**
	 * Description:根据sql查询出结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return List<T>
	 * @throws Exception      
	 */
	public List<T> findbyHQL(String hql,Object... obj) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParams(query, obj);
		return query.list();
	}
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<T> 
	 * @throws Exception      
	 */
	public PageBean<T> findbyHQL(String hql,PageBean<T> pageBean,Object... obj) throws Exception{
		pageBean.setCount(this.findbyHQLCount(hql, obj));
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParams(query, obj);
		if(pageBean == null ){
			pageBean = new PageBean<T>();
		}
		query.setFirstResult(pageBean.getPageSize()
				* (pageBean.getPageNow() - 1));
		query.setMaxResults(pageBean.getPageSize());
		pageBean.setList(query.list());
		return pageBean;
	}
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<Entity>
	 * @throws Exception      
	 */
	public PageBean<T> findbyHQLCache(String hql,PageBean<T> pageBean,Object... obj) throws Exception{
		pageBean.setCount(this.findbyHQLCount(hql, obj));
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParams(query, obj);
		if(pageBean == null ){
			pageBean = new PageBean<T>();
		}
		
		query.setFirstResult(pageBean.getPageSize()
				* (pageBean.getPageNow() - 1));
		query.setMaxResults(pageBean.getPageSize());
		query.setCacheable(true);
		pageBean.setList(query.list());
		return pageBean;
	}
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param SQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<Entity>
	 * @throws Exception      
	 */
	public PageBean<Object[]> findbySQL(String sql,PageBean pageBean,Object... obj) throws Exception{
		pageBean.setCount(this.findbySQLCount(sql, obj));
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query = this.setParams(query, obj);
		if(pageBean == null ){
			pageBean = new PageBean<Object[]>();
		}
		query.setFirstResult(pageBean.getPageSize()
				* (pageBean.getPageNow() - 1));
		query.setMaxResults(pageBean.getPageSize());
		pageBean.setList(query.list());
		return pageBean;
	}
	
	/**
	 * Description:根据sql查询出带分页结果集并且带有聚合函数分组等
	 * @param SQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<Entity>
	 * @throws Exception      
	 */
	public PageBean<Object[]> findBySQLAggregate(String sql,PageBean pageBean,Object... obj) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query = this.setParams(query, obj);
		if(pageBean == null ){
			pageBean = new PageBean<Object[]>();
		}
		query.setFirstResult(pageBean.getPageSize()
				* (pageBean.getPageNow() - 1));
		query.setMaxResults(pageBean.getPageSize());
		pageBean.setList(query.list());
		return pageBean;
	}
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param SQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<Entity>
	 * @throws Exception      
	 */
	public PageBean<Object[]> findbySQLCache(String sql,PageBean pageBean,Object... obj) throws Exception{
		pageBean.setCount(this.findbySQLCount(sql, obj));
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(sql);
		query = this.setParams(query, obj);
		if(pageBean == null ){
			pageBean = new PageBean<Object[]>();
		}
		query.setFirstResult(pageBean.getPageSize()
				* (pageBean.getPageNow() - 1));
		query.setMaxResults(pageBean.getPageSize());
		query.setCacheable(true);
		pageBean.setList(query.list());
		return pageBean;
	}
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param SQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return Long
	 * @throws Exception      
	 */
	public Long findbySQLCount(String sql,Object... obj) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String tmp = "SELECT COUNT(*) FROM";
		String tmpHql = null;
		if(sql.contains("from")){
			tmpHql = sql.substring(sql.indexOf("from")+4);
			tmp += tmpHql;
		}else if(sql.contains("FROM")){
			tmpHql = sql.substring(sql.indexOf("FROM")+4);
			tmp += tmpHql;
		}else{
			throw new Exception("SQL is unlawfulness,The SQL must be have from or FROM");
		}
		Query query = session.createSQLQuery(tmp);
		query = this.setParams(query, obj);
		Object retValue = query.uniqueResult();
		return retValue == null ? 0 : ((BigInteger)retValue).longValue();
	}
	
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param HQL Eg:SELECT * FROM A where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return Long
	 * @throws Exception      
	 */
	public Long findbyHQLCount(String hql,Object... obj) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		String tmp = "SELECT COUNT(*) FROM";
		String tmpHql = null;
		if(hql.contains("from")){
			tmpHql = hql.substring(hql.indexOf("from")+4);
			tmp += tmpHql;
		}else if(hql.contains("FROM")){
			tmpHql = hql.substring(hql.indexOf("FROM")+4);
			tmp += tmpHql;
		}else{
			throw new Exception("SQL is unlawfulness,The SQL must be have from or FROM");
		}
		Query query = session.createQuery(tmp);
		query = this.setParams(query, obj);
		Object retValue = query.uniqueResult();
		return retValue == null ? 0 : ((Long) retValue);
	}

	/**
	 * Description:执行HQL查询
	 * 
	 * @param hql
	 * @return
	 * @throws Exception
	 *             List<Object[]>
	 * @throws
	 */
	public List<T> findByHQL(String hql) throws Exception {
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		return query.list();
	}
	
	/**
	 * Description:根据sql查询出结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return List<Entity>
	 * @throws Exception      
	 */
	public List<T> findbyHQLCache(String hql,Object... obj) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createQuery(hql);
		query = this.setParams(query, obj);
		query.setCacheable(true);
		return query.list();
	}
	
	/**
	 * Description:根据sql查询出结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return List<Object[]>
	 * @throws Exception      
	 */
	public List<Object[]> findBySQLCache(String hql,Object... obj) throws Exception{
		Session session = this.sessionFactory.getCurrentSession();
		Query query = session.createSQLQuery(hql);
		query = this.setParams(query, obj);
		query.setCacheable(true);
		return query.list();
	}


	/**
	 * Description: 得到离线查询对象
	 * 
	 * @see org.framework.dao.IDao#getDetachedCriteria()
	 */
	public DetachedCriteria getDetachedCriteria() {
		return DetachedCriteria.forClass(this.getEntityClass());
	}

}
