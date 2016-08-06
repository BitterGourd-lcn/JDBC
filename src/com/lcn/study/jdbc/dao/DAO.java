/*
 * 南京普天版权所有
 * File                		: DAO.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月13日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc.dao;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import com.lcn.study.jdbc.JDBCTools;

/**
 * <code>DAO</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月13日
 * @see
 * @since		1.7
 */
public class DAO
{
	// 插入、更新、删除操作
	public void update(String sql, Object ... args)
	{
		Connection conn = null;
		PreparedStatement pst = null;
		
		try
		{
			// 1.得到 Connection 对象
			conn = JDBCTools.getConnection();

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
			JDBCTools.releaseDatabase(null, pst, conn);
		}
	}
	
	// 查询一条记录，返回对应的对象
	public <T> T get(Class<T> clazz, String sql, Object ... args)
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		T entiry = null;
		
		try
		{
			// 1.得到  Connection 对象
			conn = JDBCTools.getConnection();
			
			// 2.创建 PreparedStatement 对象
			pst = conn.prepareStatement(sql);
			for(int i=0; i<args.length; i++)
			{
				pst.setObject(i+1, args[i]);
			}
			
			// 3.执行 SQL 语句，得到 ResultSet 对象
			rs = pst.executeQuery();
			
			// 4.封装 Student 对象
			if(rs.next())
			{
				// 利用反射创建对象
				entiry = clazz.newInstance();
				
				// 通过解析 SQL 语句来判断选择了哪些列
				// 得到元数据信息
				ResultSetMetaData rsmd = rs.getMetaData();
				// 得到列名和列值结合
				String colName;
				Object colValue;
				for(int i=0; i<rsmd.getColumnCount(); i++)
				{
					colName = rsmd.getColumnLabel(i+1);
					colValue = rs.getObject(i+1);
//					System.out.println("colName：" + colName + ", colValue：" + colValue);
					
					BeanUtils.setProperty(entiry, colName, colValue);
//					Field field = clazz.getDeclaredField(colName);
//					field.setAccessible(true);
//					field.set(entiry, colValue);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			// 5.释放数据库资源
			JDBCTools.releaseDatabase(rs, pst, conn);
		}
				
		return entiry;
	}
	
	// 查询多条记录，返回对应的对象集合
	public <T> List<T> getForList(Class<T> clazz, String sql, Object ... args)
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<T> list = new ArrayList<>();
		
		try
		{
			// 1.得到  Connection 对象
			conn = JDBCTools.getConnection();
			
			// 2.创建 PreparedStatement 对象
			pst = conn.prepareStatement(sql);
			for(int i=0; i<args.length; i++)
			{
				pst.setObject(i+1, args[i]);
			}
			
			// 3.执行 SQL 语句，得到 ResultSet 对象
			rs = pst.executeQuery();
			
			// 4.封装 Student 对象
			while(rs.next())
			{
				// 利用反射创建对象
				T entiry = clazz.newInstance();
				
				// 通过解析 SQL 语句来判断选择了哪些列
				// 得到元数据信息
				ResultSetMetaData rsmd = rs.getMetaData();
				// 得到列名和列值结合
				String colName;
				Object colValue;
				for(int i=0; i<rsmd.getColumnCount(); i++)
				{
					colName = rsmd.getColumnLabel(i+1);
					colValue = rs.getObject(i+1);
//					System.out.println("colName：" + colName + ", colValue：" + colValue);
					
					BeanUtils.setProperty(entiry, colName, colValue);
//					Field field = clazz.getDeclaredField(colName);
//					field.setAccessible(true);
//					field.set(entiry, colValue);
				}
				
				// 加入集合
				list.add(entiry);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			// 5.释放数据库资源
			JDBCTools.releaseDatabase(rs, pst, conn);
		}
				
		return list;
	}
	
	// 查询一个字段或一个统计值
	public <E> E getForValue(String sql, Object ... args)
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		E entiry = null;
		
		try
		{
			// 1.得到  Connection 对象
			conn = JDBCTools.getConnection();
			
			// 2.创建 PreparedStatement 对象
			pst = conn.prepareStatement(sql);
			for(int i=0; i<args.length; i++)
			{
				pst.setObject(i+1, args[i]);
			}
			
			// 3.执行 SQL 语句，得到 ResultSet 对象
			rs = pst.executeQuery();
			
			// 4.封装 Student 对象
			if(rs.next())
			{
				entiry = (E) rs.getObject(1);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			// 5.释放数据库资源
			JDBCTools.releaseDatabase(rs, pst, conn);
		}
				
		return entiry;
	}
}
