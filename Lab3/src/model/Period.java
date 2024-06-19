package model;


/**
 * 时间段的ADT
 * 不可变
 */
public class Period 
{
	private long start;
	private long end;
	
    // Abstraction function:
    //   AF(start,end) =	时间段，从start到end
    // Representation invariant:
    //   end>start>=0
    
    // Safety from rep exposure:
    //   所有的字段均为private
	//	 只提供观察器方法和构造方法
    
    // constructor
	//要求end>start>=0
	public Period(long start,long end)
	{
		this.start = start;
		this.end = end;
		checkRep();
	}
	
    // checkRep
	private void checkRep() 
	{
		assert this.start >= 0;
		assert this.end > this.start;
	}
	
	/**
	 * 获取起始时间
	 * @return 起始时间
	 */
	public long getStart()
	{
		checkRep();
		return this.start;
	}
	
	/**
	 * 获取终止时间
	 * @return 终止时间
	 */
	public long getEnd()
	{
		checkRep();
		return this.end;
	}
	
	//重写equals方法
	@Override public boolean equals(Object obj)
	{
    	if(!(obj instanceof Period))
    	{
    		return false;
    	}
    	else 
    	{
    		Period other = (Period) obj;
    		return this.start == other.getStart() &&
    				this.end == other.getEnd();
		}
	}
	

	@Override
	public String toString()
	{
		return "["+start+","+end+"]";
	}
	
}
