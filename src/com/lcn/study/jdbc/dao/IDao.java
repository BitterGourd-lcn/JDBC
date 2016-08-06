/*
 * 南京普天版权所有
 * File                		: IDAO.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月16日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc.dao;

import java.sql.Connection;
import java.util.List;

/**
 * <code>IDAO</code> 访问结束接口
 * T DAO处理的实体类型
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月16日
 * @see
 * @since		1.7
 */
public interface IDao<T>
{
	// Insert、Update、Delete
	void update(Connection conn, String sql, Object ... args) throws Exception;
	
	// 返回1个对象
	T getObject(Connection conn, String sql, Object ... args) throws Exception;
	
	// 返回多个对象
	List<T> getList(Connection conn, String sql, Object ... args) throws Exception;
	
	// 返回一个值
	<E> E getValue(Connection conn, String sql, Object ... args) throws Exception;
	
	// 批量处理
	void batch(Connection conn, String sql, Object[] ... args) throws Exception;
}
