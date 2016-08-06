/*
 * 南京普天版权所有
 * File                		: TransactionTest.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月14日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

/**
 * <code>TransactionTest</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月14日
 * @see
 * @since		1.7
 */
public class TransactionTest
{
	/*
	 * 测试事务隔离级别
	 * Connection.setTransactionIslation()设置隔离级别
	 * Connection.TRANSACTION_NONE					无事务
	 * Connection.TRANSACTION_READ_COMMITTED		读已提交数据
	 * Connection.TRANSACTION_READ_UNCOMMITTED		读未提交数据
	 * Connection.TRANSACTION_REPEATABLE_READ		可重复读（默认）
	 * Connection.TRANSACTION_SERIALIZABLE			串行化读
	 */
	@Test
	public void testReansactionIslationUpdate()
	{
		Connection conn = null;

		try
		{
			// 创建连接
			conn = JDBCTools.getConnection();;
			
			// 开始事务:取消自动提交
			conn.setAutoCommit(false);
			
			// Tom
			String sql = "UPDATE users SET balance=balance-500 WHERE id=?";
			update(conn, sql, 1);
			
			// 提交事务
			conn.commit();
		}
		catch (Exception e)
		{
			try
			{
				// 回滚事务
				conn.rollback();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			// 关闭连接
			JDBCTools.releaseDatabase(null, null, conn);		
		}
	}
	
	@Test
	public void testReansactionIslationRead()
	{
		String sql = "SELECT balance FROM users WHERE id=?";
		
		int balance = getForValue(sql, 1);
		
		System.out.println("balance：" + balance);
	}
	
	
	/*
	 * Tom给Jerry汇款500元
	 */
	@Test
	public void testTransaction() throws Exception
	{
		String sql = "";
		Connection conn = null;
		
		try
		{
			// 创建连接
			conn = JDBCTools.getConnection();;
			
			// 开始事务:取消自动提交
			conn.setAutoCommit(false);
			
			// Tom
			sql = "UPDATE users SET balance=balance-500 WHERE id=?";
			update(conn, sql, 1);
			
			// break transaction
			int i = 10 / 0;
			System.out.println(i);
			
			// Jerry
			sql = "UPDATE users SET balance=balance+500 WHERE id=?";
			update(conn, sql, 2);
			
			// 提交事务
			conn.commit();
		}
		catch (Exception e)
		{
			try
			{
				// 回滚事务
				conn.rollback();
			}
			catch (Exception ex)
			{
				ex.printStackTrace();
			}
			e.printStackTrace();
		}
		finally
		{
			// 关闭连接
			JDBCTools.releaseDatabase(null, null, conn);		
		}
		
		/*
		DAO dao = new DAO();
		
		// Tom
		sql = "UPDATE users SET balance=balance-500 WHERE id=?";
		dao.update(sql, 1);
		
		// break transaction
		int i = 10 / 0;
		System.out.println(i);
		
		// Jerry
		sql = "UPDATE users SET balance=balance+500 WHERE id=?";
		dao.update(sql, 2);
		*/
	}

	
	private void update(Connection conn, String sql, Object ... args)
	{
		PreparedStatement pst = null;
		
		try
		{
			// 创建 PreparedStatement 对象
			pst = conn.prepareStatement(sql);
			for(int i=0; i<args.length; i++)
			{
				pst.setObject(i+1, args[i]);
			}
			
			// 执行 SQL 语句
			pst.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	// 查询一个字段或一个统计值
	private <E> E getForValue(String sql, Object ... args)
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		E entiry = null;
		
		try
		{
			// 1.得到  Connection 对象
			conn = JDBCTools.getConnection();
			conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			
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
