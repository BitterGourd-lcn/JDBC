/*
 * 南京普天版权所有
 * File                		: JDBCTest.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月6日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc;


import java.sql.SQLException;
import java.beans.PropertyVetoException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.Properties;
import java.util.Scanner;

import javax.sql.DataSource;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Blob;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.Types;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.junit.Test;

import com.lcn.study.jdbc.bean.Customer;
import com.lcn.study.jdbc.bean.Student;
import com.mchange.v2.c3p0.ComboPooledDataSource;


/**
 * <code>JDBCTest</code> TODO(简单描述)  TODO(详细描述)
 *
 *  @author JE-01-359  @version 1 .0, 2016年7月6日  @see  @since 1.7
 */
public class JDBCTest
{
	
	/*
	 * 调用存储过程或函数
	 */
	@Test
	public void testCallableStatement()
	{
		Connection conn = null;
		CallableStatement cst = null;
		String sql = "{? = call 函数名(?, ?)}";
		
		try
		{
			// 得到连接
			conn = JDBCTools.getConnection();
			
			// 创建存储过程话函数
			cst = conn.prepareCall(sql);
			
			// 参数操作(注册输出参数、赋值输入参数)
			cst.registerOutParameter(1, Types.NUMERIC);
			cst.setInt(2, 40);
			cst.registerOutParameter(3, Types.NUMERIC);
			
			// 执行存储过程话函数
			cst.execute();
			
			// 得到输出参数
			double sum = cst.getDouble(1);
			long count = cst.getLong(3);
			
			System.out.println("sum：" + sum + ", count：" + count);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCTools.releaseDatabase(null, cst, conn);
		}
	}
	
	/*
	 * 使用C3P0数据库连接池
	 * 1.加入DBCP的jar包
	 * 2.依赖于Mchange的jar包
	 */
	@Test
	public void testC3P0Factroy() throws Exception
	{
		// 通过工厂创建连接池
		DataSource ds = new ComboPooledDataSource("helloc3p0");
		
		// 得到连接
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());	
	}
	
	@Test
	public void testC3P0() throws Exception
	{
		// 创建C3P0数据源
		ComboPooledDataSource ds = new ComboPooledDataSource();
		
		// 设置数据库数据
		ds.setDriverClass("com.mysql.jdbc.Driver");        
		ds.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test");
		ds.setUser("root");                                  
		ds.setPassword("*768007*");
		
		// 设置连接池属性
		ds.setInitialPoolSize(2);					// 初始化连接个数
		ds.setMinPoolSize(2);						// 设置最小连接数
		ds.setMaxPoolSize(5); 						// 设置最大连接数，为-1，不限制
		ds.setCheckoutTimeout(1000);				// 等待为其分配连接的最大毫秒数，为0，死等
		
		// 得到连接
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
	}
	
	/*
	 * 使用DBCP数据库连接池
	 * 1.加入DBCP的jar包
	 * 2.依赖于Pool的jar包
	 */
	@Test
	public void testDBCPFactory() throws Exception
	{
		// 通过工厂创建连接池
		Properties properties = new Properties();
//		InputStream in = JDBCTest.class.getResourceAsStream("/dbcp.properties");
		InputStream in = JDBCTest.class.getClassLoader().getResourceAsStream("dbcp.properties");
		properties.load(in);
		DataSource ds = BasicDataSourceFactory.createDataSource(properties);
		
		// 得到连接
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());	
	}
	
	@Test
	public void testDBCP() throws Exception
	{
		// 创建DBCP数据源
		BasicDataSource ds = new BasicDataSource();
		
		// 设置数据库数据
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUrl("jdbc:mysql://127.0.0.1:3306/test");
		ds.setUsername("root");
		ds.setPassword("*768007*");
		
		// 设置连接池属性
		ds.setInitialSize(2);						// 初始化连接个数
		ds.setMaxTotal(5); 							// 设置可同时分配的有效最大连接数，为-1，不限制
		ds.setMinIdle(0);							// 设置保持的最少空闲连接数，为0，不限制
		ds.setMaxWaitMillis(1000*5);				// 等待为其分配连接的最大毫秒数，为-1，死等；0，立即
		
		// 得到连接
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
		System.out.println(ds.getConnection());
	}
	
	@Test
	public void testGetBlob()
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "";
		Blob data = null;
		InputStream in = null;
		OutputStream out = null;		
		
		try
		{
			conn = JDBCTools.getConnection();
			
			sql = "SELECT  * FROM customers WHERE ID=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, 9);

			rs = pst.executeQuery();
	
			if (rs.next())
			{
				data = rs.getBlob("PICTURE");
			}
			
			in = data.getBinaryStream();
			out = new FileOutputStream(new File("C:\\Users\\JE-01-359\\Desktop\\test.jpg"));
			
			byte[] buf = new byte[1024];
            int len = 0;
			while((len = in.read(buf)) != -1)
			{
				out.write(buf, 0, len);
			}
			
			System.out.println(data);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (out!=null)
			{
				try
				{
					out.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			if (in!=null)
			{
				try
				{
					in.close();
				}
				catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
			JDBCTools.releaseDatabase(rs, pst, conn);
		}			
	}
	
	/*
	 * 插入大数据Blob（比如图片），必须使用 PreparedStatement
	 * MySQL的四种BLOB类型
	 *	类型       		大小(单位：字节)
	 *	TinyBlob 		最大 255
	 *	Blob 			最大 65K
	 *	MediumBlob 		最大 16M
	 *	LongBlob 		最大 4G
	 */
	@Test
	public void testSetBlob()
	{
		Connection conn = null;
		PreparedStatement pst = null;
		String sql = "";
		
		try
		{
			conn = JDBCTools.getConnection();

			sql = "INSERT INTO customers(NAME,EMAIL,BIRTH,PICTURE) VALUES(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, "猪八戒");
			pst.setString(2, "pig@189.cn");
			pst.setDate(3, new java.sql.Date(new Date().getTime()));
			pst.setBlob(4, this.getClass().getResourceAsStream("/pig.jpg"));
			
			pst.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			JDBCTools.releaseDatabase(null, pst, conn);
		}		
	}
	
	/*
	 * 得到自动生成的主键值
	 */
	@Test
	public void testGetKeyValue()
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		String sql = "INSERT INTO customers(NAME,EMAIL,BIRTH) VALUES(?,?,?)";
		int keyValue = 0;
		
		try
		{
			conn = JDBCTools.getConnection();
			pst = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			pst.setString(1, "孙悟空");
			pst.setString(2, "suwukong@189.cn");
			pst.setDate(3, new java.sql.Date(new Date().getTime()));;

			pst.executeUpdate();
			
			rs = pst.getGeneratedKeys();		// 主键值在这里
			if (rs.next())
			{
				keyValue = rs.getInt(1);
			}

			System.out.println("主键值为：" + keyValue);
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
	
	
	/*
	 * 一.Driver是个接口：数据库厂商必须提供实现的接口，能从其中获取数据库连接
	 */
	@Test
	public void testDriver() throws SQLException
	{
		// 1.获取特定数据库的驱动
		Driver driver = new com.mysql.jdbc.Driver();

		// 2.配置参数
		// 连接字符串
		// jdbc:musql://localhost:3306/test
		// 协议：子协议：//主机：端口号/数据库名
		String url = "jdbc:mysql://127.0.0.1:3306/test";

		// 连接参数(用户名、密码)
		Properties info = new Properties();
		info.put("user", "root");
		info.put("password", "*768007*");

		// 3.获取数据库连接
		Connection connect = driver.connect(url, info);

		System.out.println("connect：" + connect);
	}

	@Test
	public void testGetConnection() throws Exception
	{
		System.out.println("connect：" + getConnection1());
	}

	public Connection getConnection1() throws Exception
	{
		// 1.读取配置文件
		// 不以 / 开头：相对路径
		// 以 / 开头：src 所在根路径
		Properties db = new Properties();
		db.load(getClass().getResourceAsStream("/db.properties"));

		String driverClass = db.getProperty("driver");
		String url = db.getProperty("url");
		String user = db.getProperty("user");
		String password = db.getProperty("password");

		// 1.获取特定数据库的驱动
		Driver driver = (Driver) Class.forName(driverClass).newInstance();

		// 2.配置参数
		Properties info = new Properties();
		info.put("user", user);
		info.put("password", password);

		// 3.获取数据库连接
		Connection connect = driver.connect(url, info);

		return connect;
	}

	/*
	 * 二.DriverManager是个驱动管理类：数据库厂商必须提供实现的接口，能从其中获取数据库连接
	 */
	@Test
	public void testDriverManager() throws IOException, ClassNotFoundException, SQLException
	{
		// 1.读取配置文件
		// 不以 / 开头：相对路径
		// 以 / 开头：src 所在根路径
		Properties db = new Properties();
		db.load(getClass().getResourceAsStream("/db.properties"));

		String driverClass = db.getProperty("driver"); // 驱动全类名
		String url = db.getProperty("url"); // 连接字符串
		String user = db.getProperty("user"); // 用户名
		String password = db.getProperty("password"); // 密码

		// 2.注册数据库驱动
		// 对应驱动程序有静态代码块实现了注册到DriverManager中
		Class.forName(driverClass);

		// 3.获取数据库连接
		Connection connect = DriverManager.getConnection(url, user, password);

		System.out.println("connect：" + connect);
	}

	/*
	 * 三.Statement： 向指定的数据表中插入数据
	 */
	@Test
	public void testStatement()
	{
		Connection conn = null;
		Statement st = null;

		try
		{
			// 1.得到数据库连接
			conn = JDBCTools.getConnection();

			// 2.准备插入SQL语句
			String sql = "INSERT INTO customers(NAME, EMAIL, BIRTH) " + 
							"VALUES('顾娟', 'gj@gmail.com', '1980-05-21')";

			// 3.执行插入
			// a.获取操作SQL语句的Statement对象
			st = conn.createStatement();
			// b.Statement对象执行插入
			st.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 4.关闭Statement对象
			try
			{
				if (st != null)
				{
					st.close();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			// 5.关闭连接
			try
			{
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}

	public void update1(String sql)
	{
		Connection conn = null;
		Statement statement = null;

		try
		{
			// 1.得到数据库连接
			conn = JDBCTools.getConnection();

			// 3.执行插入
			// a.获取操作SQL语句的Statement对象
			statement = conn.createStatement();
			// b.Statement对象执行插入
			statement.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 4.关闭Statement对象
			try
			{
				if (statement != null)
				{
					statement.close();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}

			// 5.关闭连接
			try
			{
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}


	/*
	 * 四.ResultSet：结果集对象，查询数据
	 */
	@Test
	public void testResultSet()
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		
		try
		{
			// 1.得到数据库连接
			conn = JDBCTools.getConnection();

			// 2.获取操作SQL语句的Statement对象
			st = conn.createStatement();		
			
			// 3.准备插入SQL语句
			String sql = "SELECT * FROM customers";

			// 4.Statement对象执行得到结果集
			rs = st.executeQuery(sql);
			
			// 5.处理结果集
			while (rs.next())
			{
				int id = rs.getInt("ID");
				String name =rs.getString("NAME");
				String email = rs.getString("EMAIL");
				Date birth = rs.getDate("BIRTH");
				
				System.out.println("ID：" + id);
				System.out.println("NAME：" + name);
				System.out.println("EMAIL：" + email);
				System.out.println("BIRTH：" + birth);
				
				System.out.println();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			// 6.关闭资源
			JDBCTools.releaseDatabase(rs, st, conn);
		}
	}
	
	/*
	 * 五.面向对象
	 */
	@Test
	public void testAddNewStudent()
	{
		// 1.获取从控制台输入的 Student
		Student student = getStudentFromConsole();
		
		// 2.调用 addNewStudent 方法执行插入操作
		addNewStudent(student);
	}
	
	@Test
	public void testGetStudent()
	{
		// 1.从控制台得到查询的类型（身份证、准考证）
		int searchType = getSearchTypeFromConsole();
		
		// 2.查询学生信息
		Student student = searchStudent(searchType);
		
		// 3.打印学生信息
		printStudent(student);
	}


	private Student getStudentFromConsole()
	{
		Scanner scanner = new Scanner(System.in);
		Student student = new Student();
		
		System.out.print("FlowId：");
		student.setFlowId(scanner.nextInt());
		System.out.print("Type：");
		student.setType(scanner.nextInt());
		System.out.print("IdCard：");
		student.setIdCard(scanner.next());
		System.out.print("ExamCard：");
		student.setExamCard(scanner.next());
		System.out.print("StudentName：");
		student.setStudentName(scanner.next());
		System.out.print("Location：");
		student.setLocation(scanner.next());
		System.out.print("Grade：");
		student.setGrade(scanner.nextInt());
		
		return student;
	}

	private void addNewStudent(Student student)
	{	
		// 1,准备一条 SQL 语句
		String sql = "INSERT INTO examstudent VALUES(" +
				student.getFlowId() + ", " + 
				student.getType() + ", '" + 
				student.getIdCard() + "', '" + 
				student.getExamCard() + "', '" + 
				student.getStudentName() + "', '" + 
				student.getLocation() + "', " + 
				student.getGrade() + ")";
		
		// 2.插入数据库
		JDBCTools.update(sql);
	}

	private int getSearchTypeFromConsole()
	{
		Scanner scanner = new Scanner(System.in);
		int searchType = 0;
		
		while(searchType!=1 && searchType!=2)
		{
			System.out.print("请输入查询类型(1:身份证  2.准考证)：");
			searchType = scanner.nextInt();
		}
		
		return searchType;
	}
	
	private Student searchStudent(int searchType)
	{
		Scanner scanner = new Scanner(System.in);
		String condition = "";

		// 1.根据查询类别，输入对应信息
		if (searchType == 1)
		{
			System.out.print("请输入学生的身份证号码：");
			condition = "IDCard = '" + scanner.next() + "'";
		}
		else
		{
			System.out.print("请输入学生的准考证号码：");
			condition = "ExamCard = '" + scanner.next() + "'";
		}
		
		// 2.得到对应 SQL 语句
		String sql = "SELECT * FROM examstudent WHERE " + condition;
		
		// 3.执行查询
		Student student = getStudent(sql);
		
		// 4.将查询结果封装为 Student 对象
		
		return student;
	}
	
	private Student getStudent(String sql)
	{
		Connection conn = null;
		Statement st = null;
		ResultSet rs = null;
		Student student = null;
		
		try
		{
			// 1.得到  Connection 对象
			conn = JDBCTools.getConnection();
			
			// 2.创建 Statement 对象
			st = conn.createStatement();
			
			// 3.执行 SQL 语句，得到 ResultSet 对象
			rs = st.executeQuery(sql);
			
			// 4.封装 Student 对象
			if(rs.next())
			{
				student = new Student();
				
				student.setFlowId(rs.getInt("FlowId"));
				student.setType(rs.getInt("Type"));
				student.setIdCard(rs.getString("IDCard"));
				student.setExamCard(rs.getString("ExamCard"));
				student.setStudentName(rs.getString("StudentName"));
				student.setLocation(rs.getString("Location"));
				student.setGrade(rs.getInt("Grade"));
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			// 5.释放数据库资源
			JDBCTools.releaseDatabase(rs, st, conn);
		}
		
		return student;
	}
	
	private void printStudent(Student student)
	{
		if (student == null)
		{
			System.out.println("查无此人!!");
			return;
		}
		
		System.out.println("查到学生为：" + student.getStudentName());
		System.out.println(student);
	}
	
	
	/*
	 * 六.PreparedStatement
	 */
	@Test
	public void testPreparedStatement()
	{
		Connection conn = null;
		PreparedStatement pst = null;
		
		String sql = "INSERT INTO customers(NAME, EMAIL, BIRTH) VALUES(?,?,?)";
		
		try
		{
			// 1.得到  Connection 对象
			conn = JDBCTools.getConnection();
			
			// 2.创建 PreparedStatement 对象
			pst = conn.prepareStatement(sql);
			pst.setString(1, "李光毅");
			pst.setString(2, "");
			pst.setDate(3, new java.sql.Date(new Date().getTime()));
			
			// 3.执行 SQL 语句
			pst.executeUpdate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally 
		{
			// 5.释放数据库资源
			JDBCTools.releaseDatabase(null, pst, conn);
		}		
	}
	
	/*
	 * 七.PreparedStatement
	 */	
	@Test
	public void testGet()
	{
		String sql = "";
		
		sql ="SELECT id,name,email,birth FROM customers " + 
			 "WHERE ID=?";
		Customer customer = get(Customer.class, sql, 3);
		System.out.println(customer);
		
		sql ="SELECT flowId,type,idCard,examCard,studentName,location,grade FROM examstudent " + 
			 "WHERE FlowID=?";
		Student student = get(Student.class, sql, 10);
		System.out.println(student);	
	}	
	
	public Student getStudent(String sql, Object ... args)
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Student student = null;
		
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
				student = new Student();
				
				student.setFlowId(rs.getInt(1));
				student.setType(rs.getInt(2));
				student.setIdCard(rs.getString(3));
				student.setExamCard(rs.getString(4));
				student.setStudentName(rs.getString(5));
				student.setLocation(rs.getString(6));
				student.setGrade(rs.getInt(7));
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
		
		return student;
	}
	
	public Customer getCustomer(String sql, Object ... args)
	{
		Connection conn = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Customer customer = null;
		
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
				customer = new Customer();
				
				customer.setId(rs.getInt(1));
				customer.setName(rs.getString(2));
				customer.setEmail(rs.getString(3));
				customer.setBirth(new Date(rs.getDate(4).getTime()));
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
		
		return customer;
	}
	
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
					System.out.println("colName：" + colName + ", colValue：" + colValue);
					
					Field field = clazz.getDeclaredField(colName);
					field.setAccessible(true);
					field.set(entiry, colValue);
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
}
