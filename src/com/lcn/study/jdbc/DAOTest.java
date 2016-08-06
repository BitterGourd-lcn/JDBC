/*
 * 南京普天版权所有
 * File                		: DAOTest.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月13日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc;

import java.util.List;

import org.junit.Test;

import com.lcn.study.jdbc.bean.Customer;
import com.lcn.study.jdbc.dao.DAO;

/**
 * <code>DAOTest</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月13日
 * @see
 * @since		1.7
 */
public class DAOTest
{
	DAO dao = new DAO();
	
	/**
	 * Test method for {@link com.lcn.study.jdbc.dao.DAO#update(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testUpdate()
	{
		String sql = "INSERT INTO customers(NAME,EMAIL,BIRTH) VALUES(?,?,?)";
		dao.update(sql, "李清馨", "", "2016-11-22");
	}

	/**
	 * Test method for {@link com.lcn.study.jdbc.dao.DAO#get(java.lang.Class, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testGet()
	{
		String sql = "SELECT id, name, birth, email FROM customers WHERE id=?";
		Customer customer = dao.get(Customer.class, sql, 4);
		
		System.out.println(customer);
	}

	/**
	 * Test method for {@link com.lcn.study.jdbc.dao.DAO#getForList(java.lang.Class, java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testGetForList()
	{
		String sql = "SELECT id, name, birth, email FROM customers";
		List<Customer> customers = dao.getForList(Customer.class, sql);
		
		System.out.println(customers);		
	}

	/**
	 * Test method for {@link com.lcn.study.jdbc.dao.DAO#getForValue(java.lang.String, java.lang.Object[])}.
	 */
	@Test
	public void testGetForValue()
	{
		String sql;
		sql = "SELECT count(*) FROM customers";
		long count = dao.getForValue(sql);
		System.out.println(count);	
		
		sql = "SELECT max(id) FROM customers";
		int grade = dao.getForValue(sql);
		System.out.println(grade);	
	}

}
