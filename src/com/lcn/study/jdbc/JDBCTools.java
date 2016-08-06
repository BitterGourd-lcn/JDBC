/*
 * 南京普天版权所有
 * File                		: JDBCTools.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月7日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * <code>JDBCTools</code> 操作 JDBC 的工具类
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月7日
 * @see
 * @since		1.7
 */
public class JDBCTools
{
	// 从数据库连接池得到链接对象
	private static DataSource ds = null;
	
	static
	{
		ds = new ComboPooledDataSource("helloc3p0");
	}
	
	/*
	 * 获取数据库连接对象
	 */
	public static Connection getConnection() throws Exception
	{
		/*
		// 1.读取配置文件
		// 不以 / 开头：相对路径
		// 以 / 开头：src 所在根路径
		Properties db = new Properties();
		db.load(JDBCTools.class.getResourceAsStream("/db.properties"));

		String driverClass = db.getProperty("driver");
		String url = db.getProperty("url");
		String user = db.getProperty("user");
		String password = db.getProperty("password");
		
		// 2.注册数据库驱动
		// 对应驱动程序有静态代码块实现了注册到DriverManager中
		Class.forName(driverClass);
		
		// 3.获取数据库连接
		Connection conn = DriverManager.getConnection(url, user, password);

		return conn;
		*/
		return ds.getConnection();
	}
	
	/*
	 * 释放数据库资源
	 */
	public static void releaseDatabase(ResultSet rs, Statement st, Connection conn)
	{
		if (rs != null)
		{
			try
			{
				rs.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if (st != null)
		{
			try
			{
				st.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
		
		if (conn != null)
		{
			try
			{
				conn.close();
			} 
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}		
	}
	
	/*
	 * 执行更新、插入、删除 SQL 语句 Statement
	 */
	public static void update(String sql)
	{
		Connection conn = null;
		Statement st = null;
		
		try
		{
			// 1.得到 Connection 对象
			conn = getConnection();

			// 2.创建 Statement 对象
			st = conn.createStatement();
			
			// 3.执行 SQL 语句
			st.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 4.关闭数据库资源
			releaseDatabase(null, st, conn);
		}		
	}
	
	
	/*
	 * 执行更新、插入、删除 SQL 语句 PreparedStatement
	 */
	public static void update(String sql, Object ... args)	
	{
		Connection conn = null;
		PreparedStatement pst = null;
		
		try
		{
			// 1.得到 Connection 对象
			conn = getConnection();

			// 2.创建 PreparedStatement 对象
			pst = conn.prepareStatement(sql);
			for(int i=0; i<args.length; i++)
			{
				pst.setObject(i+1, args[i]);
			}
			
			// 3.执行 SQL 语句
			pst.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 4.关闭数据库资源
			releaseDatabase(null, pst, conn);
		}		
	}	
}
