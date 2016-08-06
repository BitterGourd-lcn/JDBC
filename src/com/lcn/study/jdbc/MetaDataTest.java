/*
 * 南京普天版权所有
 * File                		: MetaDataTest.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月13日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import org.junit.Test;

/**
 * <code>MetaDataTest</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月13日
 * @see
 * @since		1.7
 */
public class MetaDataTest
{
	/*
	 * DatabaseMetaData 是描述数据库元数据的对象
	 * 可由 Connection 对象获取
	 */
	@Test
	public void testDatabaseMetaData()
	{
		Connection conn = null;
		DatabaseMetaData data = null;
		ResultSet rs = null;
		
		try
		{
			conn = JDBCTools.getConnection();
			
			data = conn.getMetaData();
			
			System.out.println("产品：" + data.getDatabaseProductName() + "(" + 
							   data.getDatabaseProductVersion()+ ") " +
							   data.getDatabaseMajorVersion() + "." + data.getDatabaseMinorVersion());
			System.out.println("连接字符串：" + data.getURL());
			System.out.println("用户名：" + data.getUserName());
			
			System.out.println("\n数据库：");
			rs = data.getCatalogs();
			while(rs.next())
			{
				System.out.println(rs.getObject(1));
			}

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally {
			JDBCTools.releaseDatabase(rs, null, conn);
		}
	}

	
	/*
	 * ResultSetMetaData 是描述结果集元数据的对象
	 * 可由 ResultSet 对象获取
	 */
	@Test
	public void testResultSetMetaData()
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		ResultSetMetaData rsmd = null;
		
		String sql = "";
		
		try
		{
			conn = JDBCTools.getConnection();
			
			sql = "SELECT * FROM customers";
			pst = conn.prepareStatement(sql);
			
			rs = pst.executeQuery();
			
			rsmd = rs.getMetaData();
			
			System.out.print("表名为：" + rsmd.getTableName(1));
			int colnum = rsmd.getColumnCount();
			System.out.println("列个数为：" + colnum);

			for(int i=1; i<=colnum; i++)
			{
				System.out.println(rsmd.getColumnName(i) + "\t" + 
								   rsmd.getColumnTypeName(i) + "("+ rsmd.getColumnDisplaySize(i) + ")\t" + 
								   rsmd.getColumnClassName(i));
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCTools.releaseDatabase(rs, pst, conn);
		}
	}
}
