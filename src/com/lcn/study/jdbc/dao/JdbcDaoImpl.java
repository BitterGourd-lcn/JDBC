/*
 * 南京普天版权所有
 * File                		: JdbcDaoImpl.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月16日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

/**
 * <code>JdbcDaoImpl</code> TODO(简单描述)
 * 使用QueryRunner提供具体实现
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月16日
 * @see
 * @since		1.7
 */
public class JdbcDaoImpl<T> implements IDao<T>
{
	private QueryRunner queryRunner = null;
	private Class<T> type;
	
	
	public JdbcDaoImpl()
	{
		this.queryRunner = new QueryRunner();

        Type genType = this.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        this.type = (Class<T>) params[0];
	}
	
	
	@Override
	public void update(Connection conn, String sql, Object... args) throws Exception
	{
		queryRunner.update(conn, sql, args);
	}

	@Override
	public T getObject(Connection conn, String sql, Object... args) throws Exception
	{
		return queryRunner.query(conn, sql, new BeanHandler<>(type), args);
	}


	@Override
	public List<T> getList(Connection conn, String sql, Object... args) throws Exception
	{
		return queryRunner.query(conn, sql, new BeanListHandler<>(type), args);
	}

	@Override
	public <E> E getValue(Connection conn, String sql, Object... args) throws Exception
	{
		return queryRunner.query(conn, sql, new ScalarHandler<E>(), args);
	}

	@Override
	public void batch(Connection conn, String sql, Object[]... args) throws Exception
	{
		// TODO Auto-generated method stub
		
	}

}
