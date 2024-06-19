package P2;

/**
 * 表示社交网络图中“人”的类
 * 不可变
 */
public class Person 
{
	private final String name;//人的名字
	
    // Abstraction function:
    //   AF(name) =	名为name的一个人
    // Representation invariant:
	//	 姓名为String类型
	//	 不可为空串
    
    // Safety from rep exposure:
    //   所有的字段均为private final
	// 	 所有字段均为不可变类型：String
    
    // constructor
	/**
	 * Person类的构造方法
	 * @param s 人的名字，不能为空
	 * @return 表示此人的Person类型对象
	 */
	public Person(String s)
	{
		this.name = s;
		checkRep();
	}
	
	//checkRep
	private void checkRep()
	{
		//检验是否为空字符串
		assert !this.name.isEmpty();
	}
	
	/**
	 * 获取该人的名字
	 * @return 该人的名字
	 */
	public String getName()
	{
		checkRep();
		return this.name;
	}

}
