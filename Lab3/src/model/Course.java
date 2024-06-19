package model;

/**
 * 课程类
 * 不可变
 */
public class Course 
{
	private long ID;
	private String courseName;
	private String teacherName;
	private String position;
	
    // Abstraction function:
    //   AF(ID,courseName,teacherName,poisition) = 一门课程{ID，课程名称，教师名字，地点}
    // Representation invariant:
    //   对字段没有要求
    
    // Safety from rep exposure:
    //   所有的字段均为private
	//	 只提供观察器方法和构造方法
	//   String和long类型，不可变
	
	//构造方法
	/**
	 * 课程类构造方法
	 * @param ID 课程ID
	 * @param courseName 课程名称
	 * @param teacherName 教师名字
	 * @param position 上课地点
	 */
	public Course(long ID,String courseName,String teacherName,String position)
	{
		this.ID = ID;
		this.courseName = courseName;
		this.teacherName = teacherName;
		this.position = position;
	}
	
	//checkRep
	//没什么要求，所以为空
	private void checkRep() 
	{

	}
	
	/**
	 * 获取课程ID
	 * @return 课程ID
	 */
	public long getID()
	{
		checkRep();
		return ID;
	}
	
	/**
	 * 获取课程名称
	 * @return 课程名称
	 */
	public String getCourseName()
	{
		checkRep();
		return courseName;
	}
	
	/**
	 * 获取教师名字
	 * @return 教师名字
	 */
	public String getTeacherName()
	{
		checkRep();
		return teacherName;
	}
	
	/**
	 * 获取上课地点
	 * @return 上课地点
	 */
	public String getPosition()
	{
		checkRep();
		return position;
	}
	
	@Override
	public boolean equals(Object obj)
	{
    	if(!(obj instanceof Course))
    	{
    		return false;
    	}
    	else 
    	{
    		Course other = (Course) obj;
    		return this.ID == other.getID() &&
    				this.courseName.equals(other.getCourseName())&&
    				this.teacherName.equals(other.getTeacherName())&&
    				this.position.equals(other.getPosition());
		}
	}
}
