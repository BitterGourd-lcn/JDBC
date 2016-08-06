/*
 * 南京普天版权所有
 * File                		: Student.java
 * Project            		: JDBC
 * Version            		: 1.0 
 * Creator					: JE-01-359
 * Creation date			: 2016年7月10日
 * Modifier					: 
 * Modification date		: 
 * JDK version				: 1.7
 */
package com.lcn.study.jdbc.bean;

/**
 * <code>Student</code> TODO(简单描述)
 * TODO(详细描述)
 *
 * @author		JE-01-359
 * @version		1 .0, 2016年7月10日
 * @see
 * @since		1.7
 */
public class Student
{
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
	
	
	public int getFlowId()
	{
		return flowId;
	}
	public void setFlowId(int flowId)
	{
		this.flowId = flowId;
	}
	
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	
	public String getIdCard()
	{
		return idCard;
	}
	public void setIdCard(String idCard)
	{
		this.idCard = idCard;
	}
	
	public String getExamCard()
	{
		return examCard;
	}
	public void setExamCard(String examCard)
	{
		this.examCard = examCard;
	}
	
	public String getStudentName()
	{
		return studentName;
	}
	public void setStudentName(String studentName)
	{
		this.studentName = studentName;
	}
	
	public String getLocation()
	{
		return location;
	}
	public void setLocation(String location)
	{
		this.location = location;
	}
	
	public int getGrade()
	{
		return grade;
	}
	public void setGrade(int grade)
	{
		this.grade = grade;
	}
	
	
	/**
	 * 构造器  Student.
	 * TODO(详细说明)
	 *
	 */
	public Student()
	{
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 构造器  Student.
	 * TODO(详细说明)
	 *
	 * @param flowId
	 * @param type
	 * @param idCard
	 * @param examCard
	 * @param studentName
	 * @param location
	 * @param grade
	 */
	public Student(int flowId, int type, String idCard, String examCard, String studentName, String location, int grade)
	{
		super();
		this.flowId = flowId;
		this.type = type;
		this.idCard = idCard;
		this.examCard = examCard;
		this.studentName = studentName;
		this.location = location;
		this.grade = grade;
	}
	
	
	@Override
	public String toString()
	{
		return "Student [flowId=" + flowId + ", type=" + type + ", idCard=" + idCard + ", examCard=" + examCard
				+ ", studentName=" + studentName + ", location=" + location + ", grade=" + grade + "]";
	}
}
