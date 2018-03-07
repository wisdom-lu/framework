package org.framework.h4.service;

import java.io.Serializable;
import java.util.List;

import org.framework.h4.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

/**
 * <p>Title: 业务层基接口</p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2012 Framework Systems Co., Ltd. All rights reserved.</p>
 *
 * <p>Company: Framework</p>
 *
 * @author Mou Lu
 * @Date：Feb 26, 2014 2:26:27 PM
 * @version 1.0
 */
public interface IService<T, Entity extends Serializable, PK extends Serializable>
		extends Serializable {

	/**
	 * Description:保存实体
	 * 
	 * @param entity
	 * @throws Exception  void    
	 * @throws
	 */
	public void save(Entity entity) throws Exception;
	
	/**
	 * Description:保存实体之后，返回持久太的实体
	 * 
	 * @param entity
	 * @return Entity
	 * @throws Exception     
	 */
	public Entity saveEntity(Entity entity) throws Exception;
	
	/**
	 * Description:保存或者更新实体
	 * 
	 * @param entity
	 * @throws Exception  void    
	 * @throws
	 */
	public void saveOrUpdate(Entity entity) throws Exception;
	
	/**
	 * Description:批量保存
	 * 
	 * @param listEntity
	 * @throws Exception  void    
	 * @throws
	 */
	public void saveBatch(List<Entity> listEntity) throws Exception;
	
	/**
	 * 根据主键查询实体 
	 * 
	 * @param id
	 * 			主键ID
	 * @return  Entity  
	 * 			返回实体  
	 * @throws 	Exception
	 */
	public Entity findByPK(PK id) throws Exception;

	
	/**
	 * 查询所有实体
	 * 
	 * @return List<Entity> 
	 * 			实体集合
	 * @throws Exception  List<Entity>    
	 */
	public List<Entity> findAll() throws Exception;

	
	/**
	 * 根据主键删除实体
	 * 
	 * @param id
	 * 			主键ID
	 * @throws  void    
	 * @throws Exception
	 */
	public void deleteByPk(PK id) throws Exception;
	
	/**
	 * 删除实体
	 * 
	 * @param Entity
	 *            实体
	 * @return void
	 * @throws Exception
	 */
	public void delete(Entity entity) throws Exception;
	
	/**
	 * 根据主键数组，批量删除实体
	 * 
	 * @param ids
	 * 			主键ID数组
	 * @throws  void    
	 * @throws Exception
	 */
	public void deleteByPk(PK[] ids) throws Exception;

	
	/**
	 * 根据主键更新实体
	 * 
	 * @param Entity
	 * 			更新对象
	 * @throws  void    
	 * @throws Exception
	 */
	public void update(Entity entity) throws Exception;
	
	/**
	 * Description:执行删除或者更新HQL
	 * 
	 * @param hql
	 * @throws Exception  void    
	 * @throws
	 */
	public boolean updateHQL(String hql) throws Exception;
	
	/**
	 * Description:执行删除或者更新HQL
	 * 
	 * @param hql Eg:update a set b=?,c=? where d=? and f=?
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return boolean
	 * @throws Exception     
	 */
	public boolean executeHQL(String hql,Object... params) throws Exception;
	
	/**
	 * Description:执行删除或者更新SQL
	 * 
	 * @param hql Eg:update a set b=?,c=? where d=? and f=?
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return boolean
	 * @throws Exception     
	 */
	public boolean executeSQL(String sql,Object... params) throws Exception;
	
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
	public PageBean<Entity> findByPager(Entity entity,PageBean<Entity> pageBean) throws Exception;
	
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
	public Entity findByPropertie(String propertie, Object value) throws Exception;
	
	
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
	public PageBean<Entity> findByCriteria(DetachedCriteria criterion,PageBean<Entity> pageBean) throws Exception;
	
	/**
	 * Description:条件+分页Count查询
	 * 
	 * @param DetachedCriteria
	 * @return int
	 * @throws Exception  
	 */
	public int findByCriteriaCount(DetachedCriteria criterion) throws Exception;
	
	/**
	 * Description:条件查询
	 * 
	 * @param DetachedCriteria
	 * @return List<Entity>
	 * @throws Exception 
	 */
	public List<Entity> findByCriteria(DetachedCriteria criterion) throws Exception;
	
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
	public List<Entity> findListByPropertie(String propertie,Object value) throws Exception;
	
	/**
	 * Description:调用有输入参数的存储过程
	 * 
	 * @param proc_name 
	 * 			存储过程名字
	 * @param obj
	 * 			输入参数的值
	 * @throws Exception  void    
	 */
	public void callProc(String proc_name,Object... obj) throws Exception;
	
	/**
	 * Description:根据sql 查询Count
	 * 
	 * @param sql
	 * @param obj
	 * @return
	 * @throws Exception  int    
	 */
	public Long findSqlCount(final String sql,final Object... obj) throws Exception;
	
	/**
	 * Description:根据sql 查询sql聚合函数
	 * 
	 * @param sql
	 * @param obj
	 * @return Object
	 * @throws Exception      
	 */
	public Object findSqlAggregate(final String sql,final Object... obj) throws Exception;
	
	/**
	 * Description:查询分页总数
	 * 
	 * @param entity
	 * @return
	 * @throws Exception  int    
	 */
	public int findCount(Entity entity) throws Exception;
	
	/**
	 * Description:根据sql查询出结果集
	 * 
	 * @return List<Object[]> 
	 * @throws Exception     
	 */
	public List<Object[]> findbySql(final String sql) throws Exception;
	
	/**
	 * Description:根据sql查询出结果集
	 * @param Sql Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return boolean
	 * @throws Exception      
	 * @throws Exception     
	 */
	public List<Object[]> findbySQL(String sql,Object... obj) throws Exception;
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return boolean
	 * @throws Exception      
	 * @throws Exception     
	 */
	public PageBean<Entity> findbyHQL(String hql,PageBean<Entity> pageBean,Object... obj) throws Exception;
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param SQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return Long
	 * @throws Exception      
	 */
	public Long findbySQLCount(String hql,Object... obj) throws Exception;
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param SQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<Entity>
	 * @throws Exception      
	 */
	public PageBean<Object[]> findbySQL(String hql,PageBean pageBean,Object... obj) throws Exception;
	
	/**
	 * Description:根据sql查询出结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return boolean
	 * @throws Exception      
	 * @throws Exception     
	 */
	public List<Entity> findbyHQL(String hql,Object... obj) throws Exception;
	
	/**
	 * Description:执行HQL查询
	 * 
	 * @param hql
	 * @return
	 * @throws Exception  List<Object[]>    
	 * @throws
	 */
	public List<Entity> findByHQL(final String hql) throws Exception;
	
	/**
	 * 查询所有实体
	 * 
	 * @return List<Entity> 
	 * 			实体集合
	 * @throws Exception  List<Entity>    
	 */
	public List<Entity> findAllCache() throws Exception;
	
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
	public PageBean<Entity> findByPagerCache(Entity entity,PageBean<Entity>  pageBean) throws Exception;
	
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
	public PageBean<Entity> findByCriteriaCache(DetachedCriteria criterion,PageBean<Entity> pageBean) throws Exception;
	
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
	public Entity findByPropertieCache(String propertie,Object value) throws Exception;
	
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
	public List<Entity> findListByPropertieCache(String propertie,Object value) throws Exception;
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<Entity>
	 * @throws Exception      
	 */
	public PageBean<Entity> findbyHQLCache(String hql,PageBean<Entity> pageBean,Object... obj) throws Exception;
	
	/**
	 * Description:根据sql查询出带分页结果集
	 * @param SQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return PageBean<Entity>
	 * @throws Exception      
	 */
	public PageBean<Object[]> findbySQLCache(String sql,PageBean pageBean,Object... obj) throws Exception;
	
	/**
	 * Description:条件查询
	 * 
	 * @param DetachedCriteria
	 * @return List<Entity>
	 * @throws Exception 
	 */
	public List<Entity> findByCriteriaCache(DetachedCriteria criterion) throws Exception;
	
	/**
	 * Description:根据sql查询出结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return List<Entity>
	 * @throws Exception      
	 */
	public List<Entity> findbyHQLCache(String hql,Object... obj) throws Exception;
	
	/**
	 * Description:根据sql查询出结果集
	 * @param HQL Eg:select * form  a where a.b=?,a.c=? 
	 * @param params 参数列表  几个问号，参数里面必须放几个值，如果没有占位符，此参数为null
	 * @return List<Entity>
	 * @throws Exception      
	 */
	public List<Object[]> findBySQLCache(String hql,Object... obj) throws Exception;
	
	/**
	 * Description: 得到离线查询对象
	 *
	 * @see org.framework.dao.IDao#getDetachedCriteria()
	 */
	public DetachedCriteria getDetachedCriteria();
	
}
