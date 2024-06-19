package model;

/**
 * 员工类
 * 不可变
 */
public class Employee 
{
	private String name;
	private String position;
	private String phoneNumber;
	
    // Abstraction function:
    //   AF(name,position,phoneNumber) = 一个职员{姓名，职位，电话号码}
    // Representation invariant:
    //   对各个字段均没有要求
    
    // Safety from rep exposure:
    //   所有的字段均为private
	//	 只提供观察器方法和构造方法
	//   String类型，不可变
	
	//构造方法
	/**
	 * 员工类构造方法
	 * @param name 员工名称
	 * @param position 员工地址
 	 * @param phoneNumber 员工电话号码
	 */
	public Employee(String name,String position,String phoneNumber)
	{
		this.name = name;
		this.position = position;
		this.phoneNumber = phoneNumber;
	}
	
	//checkRep
	//没有限制，为空
	private void checkRep() 
	{
		
	}
	
	/**
	 * 获取员工名字
	 * @return 员工名字
	 */
	public String getName()
	{
		checkRep();
		return name;
	}
	
	/**
	 * 获取员工职位
	 * @return 员工职位
	 */
	public String getPosition()
	{
		checkRep();
		return position;
	}
	
	/**
	 * 获取员工电话号码
	 * @return 员工电话号码
	 */
	public String getPhoneNumber()
	{
		checkRep();
		return phoneNumber;
	}
	
	@Override
	public boolean equals(Object obj)
	{
    	if(!(obj instanceof Employee))
    	{
    		return false;
    	}
    	else 
    	{
    		Employee other = (Employee) obj;
    		return this.name.equals(other.getName()) &&
    				this.position.equals(other.getPosition())&&
    				this.phoneNumber.equals(other.getPhoneNumber());
		}
	}
}
