/*
 * 南京普天版权所有
 * File                		: BeanUtilsTest.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月13日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;

import com.lcn.study.jdbc.bean.Student;

/**
 * <code>BeanUtilsTest</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月13日
 * @see
 * @since		1.7
 */
public class BeanUtilsTest
{
	/*
	// 流水号
	private int flowId;
	// 考试类型
	private int type;
	// 身份证号码
	private String idCard;
	// 准考证号码
	private String examCard;
	// 学生名称
	private String studentName;
	// 学生地址
	private String location;
	// 考试分数
	private int grade;
	*/	
	@Test
	public void testSetProperty() throws Exception
	{
		Object obj = new Student();
		System.out.println(obj);
		
		BeanUtils.setProperty(obj, "flowId", 34);
		BeanUtils.setProperty(obj, "idCard", "234444");
		System.out.println(obj);
	}

	@Test
	public void testGetProperty() throws Exception
	{
		Object obj = new Student(1,4,"2323", "232332","李光毅","成都", 100);
		System.out.println(obj);
		
		Object name = BeanUtils.getProperty(obj, "studentName");
		System.out.println(name);
	}
}
