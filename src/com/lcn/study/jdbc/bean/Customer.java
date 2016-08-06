/*
 * 南京普天版权所有
 * File                		: Customer.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月12日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc.bean;

import java.util.Date;

/**
 * <code>Customer</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月12日
 * @see
 * @since		1.7
 */
public class Customer
{
	private int id;
	private String name;
	private String email;
	private Date birth;
	
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	public void setName(String name)
	{
		this.name = name;
	}
	
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	
	public Date getBirth()
	{
		return birth;
	}
	public void setBirth(Date birth)
	{
		this.birth = birth;
	}
	
	
	/**
	 * 构造器  Customer.
	 * TODO(详细说明)
	 *
	 */
	public Customer()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 构造器  Customer.
	 * TODO(详细说明)
	 *
	 * @param id
	 * @param name
	 * @param email
	 * @param birth
	 */
	public Customer(int id, String name, String email, Date birth)
	{
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.birth = birth;
	}
	
	
	@Override
	public String toString()
	{
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", birth=" + birth + "]";
	}
}
