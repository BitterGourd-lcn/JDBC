/*
 * 南京普天版权所有
 * File                		: DBUtilTest.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月15日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.junit.Test;

import com.lcn.study.jdbc.bean.Customer;

/**
 * <code>DBUtilTest</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月15日
 * @see
 * @since		1.7
 */
public class DBUtilTest
{

	@Test
	public void testQueryRunnerUpdate()
	{
		Connection conn = null;
		String sql = "DELETE FROM customers WHERE ID=?";
		QueryRunner query = new QueryRunner();
		
		try
		{
			conn = JDBCTools.getConnection();
			
			query.update(conn, sql, 8);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			JDBCTools.releaseDatabase(null, null, conn);
		}
	}
	
	@Test
	public void testQueryRunnerQuery()
	{
		Connection conn = null;
		String sql = "SELECT * FROM customers";
		QueryRunner query = new QueryRunner();
		
		try
		{
			conn = JDBCTools.getConnection();
			
			ResultSetHandler<List<Customer>> rsh = new ResultSetHandler<List<Customer>>()
			{
				@Override
				public List<Customer> handle(ResultSet rs) throws SQLException
				{
					List<Customer> customers = new ArrayList<>();
					while(rs.next())
					{
						Customer customer = new Customer();
						customer.setId(rs.getInt("ID"));
						customer.setName(rs.getString("NAME"));
						customer.setEmail(rs.getString("EMAIL"));
						customer.setBirth(rs.getDate("BIRTH"));
						
						customers.add(customer);
					}
					
					return customers;
				}
			};
			
			System.out.println(query.query(conn, sql, rsh));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			JDBCTools.releaseDatabase(null, null, conn);
		}
	}
	
	@Test
	public void testBeanHandler()
	{
		Connection conn = null;
		String sql = "SELECT * FROM customers WHERE ID=?";
		QueryRunner query = new QueryRunner();
		
		try
		{
			conn = JDBCTools.getConnection();
			
			Customer customer = query.query(conn, sql, new BeanHandler<>(Customer.class), 1);
			System.out.println(customer);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			JDBCTools.releaseDatabase(null, null, conn);
		}
	}
	
	@Test
	public void testBeanListHandler()
	{
		Connection conn = null;
		String sql = "SELECT * FROM customers WHERE ID>?";
		QueryRunner query = new QueryRunner();
		
		try
		{
			conn = JDBCTools.getConnection();
			
			List<Customer> customers = query.query(conn, sql, new BeanListHandler<>(Customer.class),5);
			System.out.println(customers);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			JDBCTools.releaseDatabase(null, null, conn);
		}
	}	
	
	@Test
	public void testScalarHandler()
	{
		Connection conn = null;
		String sql = "SELECT NAME FROM customers WHERE ID=?";
		QueryRunner query = new QueryRunner();
		
		try
		{
			conn = JDBCTools.getConnection();
			
			String name = query.query(conn, sql, new ScalarHandler<String>(), 4);
			System.out.println(name);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			JDBCTools.releaseDatabase(null, null, conn);
		}
	}		
}
